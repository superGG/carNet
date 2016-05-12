package com.earl.carnet.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import com.earl.carnet.commons.util.SmsbaoHelper;
import com.earl.carnet.commons.vo.TcpMessage;
import com.earl.carnet.dao.Tem_CarDao;
import com.earl.carnet.domain.carnet.Message.Message;
import com.earl.carnet.domain.carnet.tem_car.Tem_Car;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.exception.DomainSecurityException;
import com.earl.carnet.service.MessageService;
import com.earl.carnet.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.dao.CarDao;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.service.CarService;
import com.earl.carnet.util.JPushForCar;
import com.earl.carnet.util.JPushForUser;

@Service("carService")
@Transactional
public class CarServiceImpl extends BaseServiceImpl<Car, Car>
        implements CarService {

    private static Logger logger = Logger.getLogger(CarServiceImpl.class);

    private final String TITLE = "车辆网";

    @Resource
    CarDao carDao;

    @Resource
    Tem_CarDao tem_carDao;

    @Resource
    UserService userService;

    @Resource
    MessageService messageService;

    @Resource
    JPushForUser jpushForUser;

    @Resource
    JPushForCar jpushForCar;

    @Override
    protected BaseDao<Car> getDao() {
        return carDao;
    }

    @Override
    public int update(Car car_update) {
        int update = getDao().updateByPrimaryKeySelective(car_update);
        return update;
    }

    @Override
    public Boolean updateCarByVin(Car model) throws Exception {
        Boolean result = false;
        Car car_update = new Car();
        car_update.setVin(model.getVin());
        List<Car> carList = getDao().searchQuery(car_update);
        if (carList.size() != 0) {
            Car model_data = carList.get(0);
            if (model_data.getUserId() == null) {
                return result;
            }
//            model.setId(model_data.getId());

            //监听更新内容，判断是否需要发送信息通知
            monitorCarLight(model, model_data);//车灯监听
            monitorAlarm(model, model_data);//汽车警报
            monitorEngineProperty(model, model_data);//发动机
            monitorMileage(model, model_data);//里程数
            monitorOil(model, model_data);//油量
            monitorState(model, model_data);//状态
            monitorTemperature(model, model_data);//温度
            monitorTransmission(model, model_data);//转速器
            monitorAccident(model, model_data); //事故监听

            //更新数据
            carDao.updateByPrimaryKeySelective(model);
//          int update = carDao.updateByNotSameParam(model,model_data);
            return true;
        }
        return result;
    }

    /**
     * 汽车警报器监听器
     *
     * @param model
     * @param model_data
     */
    private void monitorAlarm(Car model, Car model_data) {
        if (model.getCarAlarm() != model_data.getCarAlarm()) { //当与数据库不同时
            if (model.getCarAlarm() && model_data.getAlarmMessage()) {
                logger.info("汽车警报器响了");
                User user = userService.findOne(model_data.getUserId());
                String content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为" + model.getPlateNumber() + " 的车辆警报器响起，请注意查看。";
                sendMessageForUser(model_data.getUserId(), content);
            }
        }
    }

    /**
     * 汽车状态监听器
     *
     * @param model
     * @param model_data
     */
    private void monitorState(Car model, Car model_data) {
        if (model.getCarState() != model_data.getCarState()) { //当与数据库不同时
            if (model.getCarState() && model_data.getStateMessage()) {
                logger.info("汽车启动了");
                User user = userService.findOne(model_data.getUserId());
                String content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为" + model.getPlateNumber() + " 的车辆已经启动。";
                sendMessageForUser(model_data.getUserId(), content);
            }
        }
    }

    /**
     * 发动机监听器
     *
     * @param model
     * @param model_data
     */
    private void monitorEngineProperty(Car model, Car model_data) {
        if (model.getEngineProperty() != model_data.getEngineProperty()) { //当与数据库不同时
            if (!model.getEngineProperty() && model_data.getPropertyMessage()) {
                logger.info("发动机坏了");
                User user = userService.findOne(model_data.getUserId());
                String content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为" + model.getPlateNumber() + " 的车辆发动机出现故障，请注意查看。";
                sendMessageForUser(model_data.getUserId(), content);
            }
        }
    }

    /**
     * 转速器监听器
     *
     * @param model
     * @param model_data
     */
    private void monitorTransmission(Car model, Car model_data) {
        if (model.getTransmission() != model_data.getTransmission()) { //当与数据库不同时
            if (!model.getTransmission() && model_data.getPropertyMessage()) {     //转速器坏了
                User user = userService.findOne(model_data.getUserId());
                String content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为" + model.getPlateNumber() + " 的车辆转速器出现故障，请注意查看。";
                sendMessageForUser(model_data.getUserId(), content);
                logger.info("转速器坏了");
            }
        }
    }

    /**
     * 车灯监听器
     *
     * @param model
     * @param model_data
     */
    private void monitorCarLight(Car model, Car model_data) {
        if (model.getCarLight() != model_data.getCarLight()) { //当与数据库不同时
            if (!model.getCarLight() && model_data.getPropertyMessage()) {     // 车灯坏了
                User user = userService.findOne(model_data.getUserId());
                String content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为" + model.getPlateNumber() + " 的车辆车灯出现故障，请注意查看。";
                sendMessageForUser(model_data.getUserId(), content);
                logger.info("车灯坏了");
//                jpushForUser.sendPush_Alias(model.getUserId().toString(),"车灯坏了");
            }
        }
    }

    /**
     * 温度监听器
     *
     * @param model
     * @param model_data
     */
    private void monitorTemperature(Car model, Car model_data) {
        if (model.getTemperature() > model_data.getTemperature() && model.getTemperature() >= 100) { //当汽车当前水温>数据库水温，并且数据库水温>=100度
            if ((model.getTemperature() % 5 < model_data.getTemperature() % 5
                    || (model.getTemperature() > 100 && model.getTemperature() < 105))
                    && model_data.getPropertyMessage()) { //避免多次发送信息，每升高5个单位的温度就通知车主一次
                User user = userService.findOne(model_data.getUserId());
                String content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为" + model.getPlateNumber() + " 的车辆温度过高，请注意行驶。";
                sendMessageForUser(model_data.getUserId(), content);
                logger.info("汽车温度过高，需要降温");
            }
        }
    }

    /**
     * 油箱监听器
     *
     * @param model
     * @param model_data
     */
    private void monitorOil(Car model, Car model_data) {
        if (model.getOil() < model_data.getOil() && model.getOil() < model.getOilBox() * 0.2) { //当前油量<数据库油量 并且 数据库油量剩余不足20%
            if (model.getOil() % 5 < model_data.getOil() % 5 || (model.getOil() < model.getOilBox() * 0.2 && model.getOil() > model.getOilBox() * 0.15)) { //避免多次发送信息，每降低5个单位量的油量就通知车主一次
                User user = userService.findOne(model.getUserId());
                String content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为" + model.getPlateNumber() + " 的车辆油量不足20%，请及时加油。";
                sendMessageForUser(model.getUserId(), content);
                logger.info("汽车油量不足，请及时加油");
            }
        }
    }

    /**
     * 里程表监听器
     *
     * @param model
     * @param model_data
     */
    private void monitorMileage(Car model, Car model_data) {
        if ((model.getMileage() % 15000 < model_data.getMileage() % 15000) && model_data.getPropertyMessage()) {
            User user = userService.findOne(model.getUserId());
            String content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为" + model.getPlateNumber() + " 的车辆已经行驶超过15000公里，请及时对汽车进行检查维修。";
            sendMessageForUser(model.getUserId(), content);
            logger.info("汽车已行驶超过15000公里，请及时对汽车进行检查维修");
        }
    }

    /**
     * 事故监听器.
     *
     * @param model
     * @param model_data
     */
    private void monitorAccident(Car model, Car model_data) throws Exception {
        if (model.getCarLight() != model_data.getCarLight()  //TODO 如何判定为出事故
                && model.getTransmission() != model_data.getTransmission()
                && model.getEngineProperty() != model_data.getEngineProperty()
                && (model.getTemperature() > model_data.getTemperature() && model.getTemperature() > 100)) {
            User user = userService.findOne(model.getUserId());
            String content = "";
            //发送短信到亲人手机
            SmsbaoHelper.send(user.getRelatedPhone(), content);

        }
    }

    /**
     * 向用户发送信息.
     *
     * @param userId
     * @param content
     */
    private void sendMessageForUser(Long userId, String content) {
        Message message = new Message();
        message.setState(false);
        message.setUserId(userId);
        message.setContent(content);
        messageService.insertBackId(message);
        jpushForUser.sendPush_Alias(userId.toString(), message.getContent(), TITLE);
    }


    @Override
    public List<Car> getAllCarByUser(Long userId) {
        Car car = new Car();
        car.setUserId(userId);
        List<Car> carList = getDao().searchQuery(car);
        return carList;
    }

    @Override
    public int saveCar(Car car) {
        Car car_data = new Car();
        car_data.setVin(car.getVin());
        List<Car> carList = getDao().searchQuery(car_data);
        if (carList.size() == 0) {
            car.setAlarmMessage(true);
            car.setPropertyMessage(true);
            car.setStateMessage(true);
            return insertBackId(car);
        } else {
            throw new SecurityException("该车辆已经被注册");
        }
    }

    @Override
    public Tem_Car getCarByVin(String vin) {
        Tem_Car car_vin = new Tem_Car();
        car_vin.setVin(vin);
        List<Tem_Car> carList = tem_carDao.searchQuery(car_vin);
        if (carList.size() != 0) {
            return carList.get(0);
        } else {
            throw new SecurityException("无该车辆");
        }
    }

    @Override
    public Boolean updateCarState(Long id) {
        Boolean result = false;
        Car car = getDao().findOneById(id);
        TcpMessage tcpMessage = new TcpMessage();
        if (car.getCarState()) {
            tcpMessage.setMessagtype(1);//1为改变状态，2为改变警报
            tcpMessage.setMessage("false");
            jpushForCar.sendPush_Alias(car.getVin(), tcpMessage.toJson());
            result = true;
        } else {
            tcpMessage.setMessagtype(1);//1为改变状态，2为改变警报
            tcpMessage.setMessage("true");
            jpushForCar.sendPush_Alias(car.getVin(), tcpMessage.toJson());
            result = true;
        }
        return result;
    }

    @Override
    public Boolean updateCarAlarm(Long id) {
        Car car = getDao().findOneById(id);
        TcpMessage tcpMessage = new TcpMessage();
        if (car.getCarAlarm()) {
            tcpMessage.setMessagtype(2);//1为改变状态，2为改变警报
            tcpMessage.setMessage("true");
            jpushForCar.sendPush_Alias(car.getVin(), tcpMessage.toJson());
           return true;
        } else {
            throw new DomainSecurityException("该警报状态已经响起");
        }
    }

    @Override
    public void insertTem_Car(Tem_Car tem_car) {
        int tem_id = tem_carDao.insertBackId(tem_car);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                tem_carDao.delete(tem_id);
                logger.info("临时车辆信息已删除");
            }
        }, 60000);

    }


}
