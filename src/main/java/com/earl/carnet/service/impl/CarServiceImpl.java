package com.earl.carnet.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.commons.util.EhCacheHelper;
import com.earl.carnet.commons.util.SmsbaoHelper;
import com.earl.carnet.commons.vo.TcpMessage;
import com.earl.carnet.dao.CarDao;
import com.earl.carnet.domain.carnet.Message.Message;
import com.earl.carnet.domain.carnet.car.Car;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.exception.DomainSecurityException;
import com.earl.carnet.service.CarService;
import com.earl.carnet.service.MessageService;
import com.earl.carnet.service.UserService;
import com.earl.carnet.util.AddressHelper;
import com.earl.carnet.util.JPushForCar;
import com.earl.carnet.util.JPushForUser;

@Service("carService")
@Transactional
public class CarServiceImpl extends BaseServiceImpl<Car, Car> implements
		CarService
{

	private static Logger logger = Logger.getLogger(CarServiceImpl.class);

	// 推送消息对象
	private TcpMessage tcpMessage;

	// 项目标题
	private final String TITLE = "车辆网";

	// 空提醒类型
	private final Integer NOTHING = 0;

	// 油量不足提醒类型
	private final Integer OIL = 1;

	// 汽车损坏提醒类型
	private final Integer REPAIR = 2;

	// 缓存车辆状态信息
	private static Cache CAR_CACHE = EhCacheHelper.getCacheManage().getCache(
			"car");

	// 临时车辆信息缓存
	private static Cache TEM_CAR = EhCacheHelper.getCacheManage().getCache(
			"tem_car");

	@Resource
	CarDao carDao;

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
			// model.setId(model_data.getId());

			// 监听更新内容，判断是否需要发送信息通知
			monitorCarLight(model, model_data);// 车灯监听
			monitorAlarm(model, model_data);// 汽车警报
			monitorEngineProperty(model, model_data);// 发动机
			monitorMileage(model, model_data);// 里程数
			monitorOil(model, model_data);// 油量
			monitorState(model, model_data);// 状态
			monitorTemperature(model, model_data);// 温度
			monitorTransmission(model, model_data);// 转速器
			monitorSRS(model, model_data);// 安全气囊
			// 更新数据
			carDao.updateByPrimaryKeySelective(model);
			// int update = carDao.updateByNotSameParam(model,model_data);
			return true;
		}
		return result;
	}

	private void monitorAlarm(Car model, Car model_data) {
		if (model.getCarAlarm() != model_data.getCarAlarm()) { // 当与数据库不同时
			if (model.getCarAlarm() && model_data.getAlarmMessage()) {
				logger.info("汽车警报器响了");
				User user = userService.findOne(model_data.getUserId());
				String content = null;
				if (model_data.getPlateNumber() != null) {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为"
							+ model_data.getPlateNumber() + " 的车辆警报器响起，请注意查看。";
				} else {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车架号为"
							+ model_data.getVin() + " 的车辆警报器响起，请注意查看。";
				}
				tcpMessage.setMessage(content);
				tcpMessage.setMessagtype(NOTHING);
				sendMessageForUser(model_data.getUserId(), tcpMessage.toJson());// 推送信息到用户
				Car_Cache(model_data, "alarm");// 缓存数据
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
		if (model.getCarState() != model_data.getCarState()) { // 当与数据库不同时
			if (model.getCarState() && model_data.getStateMessage()) {
				logger.info("汽车启动了");
				User user = userService.findOne(model_data.getUserId());
				String content = null;
				if (model_data.getPlateNumber() != null) {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为"
							+ model_data.getPlateNumber() + " 的车辆已经启动。";
				} else {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车架号为"
							+ model_data.getVin() + " 的车辆已经启动。";
				}
				tcpMessage.setMessage(content);
				tcpMessage.setMessagtype(NOTHING);
				sendMessageForUser(model_data.getUserId(), tcpMessage.toJson());// 推送信息到用户
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
		if (model.getEngineProperty() != model_data.getEngineProperty()) { // 当与数据库不同时
			if (!model.getEngineProperty() && model_data.getPropertyMessage()) {
				logger.info("发动机坏了");
				User user = userService.findOne(model_data.getUserId());
				String content = null;
				if (model_data.getPlateNumber() != null) {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为"
							+ model_data.getPlateNumber()
							+ " 的车辆发动机出现故障，请注意查看。";
				} else {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车架号为"
							+ model_data.getVin() + " 的车辆发动机出现故障，请注意查看。";
				}
				tcpMessage.setMessage(content);
				tcpMessage.setMessagtype(REPAIR);
				sendMessageForUser(model_data.getUserId(), tcpMessage.toJson());// 推送信息到用户
				Car_Cache(model_data, "engineProperty");// 缓存数据
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
		if (model.getTransmission() != model_data.getTransmission()) { // 当与数据库不同时
			if (!model.getTransmission() && model_data.getPropertyMessage()) { // 转速器坏了
				User user = userService.findOne(model_data.getUserId());
				String content = null;
				if (model_data.getPlateNumber() != null) {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为"
							+ model_data.getPlateNumber()
							+ " 的车辆转速器出现故障，请注意查看。";
				} else {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车架号为"
							+ model_data.getVin() + " 的车辆转速器出现故障，请注意查看。";
				}
				tcpMessage.setMessage(content);
				tcpMessage.setMessagtype(REPAIR);
				sendMessageForUser(model_data.getUserId(), tcpMessage.toJson());// 推送信息到用户
				Car_Cache(model_data, "transmission");// 缓存数据
				logger.info("转速器坏了");
			}
		}
	}

	/**
	 * SRS监听器
	 *
	 * @param model
	 * @param model_data
	 */
	private void monitorSRS(Car model, Car model_data) {
		if (model.getSRS() != model_data.getSRS()) { // 当与数据库不同时
			if (!model.getSRS()) { // 安全气囊启动
				User user = userService.findOne(model_data.getUserId());
				String content = null;
				if (model_data.getPlateNumber() != null) {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为"
							+ model_data.getPlateNumber()
							+ " 的车辆安全气囊出现故障，请注意查看。";
				} else {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车架号为"
							+ model_data.getVin() + " 的车辆安全气囊出现故障，请注意查看。";
				}
				sendMessageForUser(model_data.getUserId(), content);
				logger.info("安全气囊启动");
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
		if (model.getCarLight() != model_data.getCarLight()) { // 当与数据库不同时
			if (!model.getCarLight() && model_data.getPropertyMessage()) { // 车灯坏了
				User user = userService.findOne(model_data.getUserId());
				String content = null;
				if (model_data.getPlateNumber() != null) {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为"
							+ model_data.getPlateNumber() + " 的车辆车灯出现故障，请注意查看。";
				} else {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车架号为"
							+ model_data.getVin() + " 的车辆车灯出现故障，请注意查看。";
				}
				tcpMessage.setMessage(content);
				tcpMessage.setMessagtype(REPAIR);
				sendMessageForUser(model_data.getUserId(), tcpMessage.toJson());// 推送信息到用户
				Car_Cache(model_data, "carLight");// 缓存数据
				logger.info("车灯坏了");
				// jpushForUser.sendPush_Alias(model.getUserId().toString(),"车灯坏了");
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
		if (model.getTemperature() > model_data.getTemperature()
				&& model.getTemperature() >= 100) { // 当汽车当前水温>数据库水温，并且数据库水温>=100度
			if ((model.getTemperature() % 5 < model_data.getTemperature() % 5 || (model
					.getTemperature() > 100 && model.getTemperature() < 105))
					&& model_data.getPropertyMessage()) { // 避免多次发送信息，每升高5个单位的温度就通知车主一次
				User user = userService.findOne(model_data.getUserId());
				String content = null;
				if (model_data.getPlateNumber() != null) {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车牌号为"
							+ model_data.getPlateNumber() + " 的车辆温度过高，请注意行驶。";
				} else {
					content = "尊敬的" + user.getUsername() + ": 您好，您的车架号为"
							+ model_data.getVin() + " 的车辆温度过高，请注意行驶。";
				}
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
		if (model.getOil() < model_data.getOil()
				&& model.getOil() < model.getOilBox() * 0.2
				&& model.getCurrentCar()) { // 当前油量<数据库油量 并且 数据库油量剩余不足20%
			if (model.getOil() % 5 < model_data.getOil() % 5
					|| (model.getOil() < model.getOilBox() * 0.2 && model
							.getOil() > model.getOilBox() * 0.15)) { // 避免多次发送信息，每降低5个单位量的油量就通知车主一次
				User user = userService.findOne(model.getUserId());
				String content = "尊敬的" + user.getUsername()
						+ ": 您好，您当前的车辆 油量不足20%，请及时加油。";
				tcpMessage.setMessage(content);
				tcpMessage.setMessagtype(OIL);
				sendMessageForUser(model_data.getUserId(), tcpMessage.toJson());// 推送信息到用户
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
		if ((model.getMileage() % 15000 < model_data.getMileage() % 15000)
				&& model_data.getPropertyMessage() && model.getCurrentCar()) {
			User user = userService.findOne(model.getUserId());
			String content = "尊敬的" + user.getUsername()
					+ ": 您好，您当前的车辆已经行驶超过15000公里，请及时对汽车进行检查维修。";
			tcpMessage.setMessage(content);
			tcpMessage.setMessagtype(REPAIR);
			sendMessageForUser(model_data.getUserId(), tcpMessage.toJson());// 推送信息到用户
			logger.info("汽车已行驶超过15000公里，请及时对汽车进行检查维修");
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
		jpushForUser.sendPush_Alias(userId.toString(), message.getContent(),
				TITLE);
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
	public Boolean updateCarState(Long id) {
		Boolean result = false;
		Car car = getDao().findOneById(id);
		TcpMessage tcpMessage = new TcpMessage();
		if (car.getCarState()) {
			tcpMessage.setMessagtype(1);// 1为改变状态，2为改变警报
			tcpMessage.setMessage("false");
			jpushForCar.sendPush_Alias(car.getVin(), tcpMessage.toJson());
			result = true;
		} else {
			tcpMessage.setMessagtype(1);// 1为改变状态，2为改变警报
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
			tcpMessage.setMessagtype(2);// 1为改变状态，2为改变警报
			tcpMessage.setMessage("true");
			jpushForCar.sendPush_Alias(car.getVin(), tcpMessage.toJson());
			return true;
		} else {
			throw new DomainSecurityException("该警报状态已经响起");
		}
	}

	@Override
	public void insertTem_Car(Car tem_car) {
		// 先在数据库car中查询是否有该车架号vin的车辆
//		System.out.println("----------开始检车数据库car中是否存在车架号vin为：" + tem_car.getVin() + "的车辆");
		Car car_vin = new Car();
		car_vin.setVin(tem_car.getVin());
		List<Car> carList = getDao().searchAccurate(car_vin);
		if (carList.size() != 0)
			throw new DomainSecurityException("该车辆在数据库car中已存在");
//		System.out.println("-------检查数据库完毕");
		// Ehcache缓存临时车辆信息
		Element tem_car_element = TEM_CAR.get(tem_car.getVin());
//		System.out.println("-------开始检车缓存中是否存在");
		if (tem_car_element != null) {
			throw new DomainSecurityException("该车辆已存在");
		} else {
//			System.out.println("-------缓存中不存在");
//			System.out.println("-------开始给缓存中添加临时车辆信息");
			TEM_CAR.put(new Element(tem_car.getVin(), tem_car));
		}
	}

	@Override
	public Car getCarByVin(String vin) {
		// Ehcache缓存临时车辆信息
		Element tem_car = TEM_CAR.get(vin);
		if (tem_car != null) {
			return (Car) tem_car.getObjectValue();
		} else {
			throw new DomainSecurityException("无该车辆");
		}
	}

	/**
	 * 计算汽车10分钟内性能损坏数量.
	 *
	 * @param model
	 *            对象
	 * @param type
	 *            需要缓存车辆部件
	 */
	@SuppressWarnings("unchecked")
	public void Car_Cache(Car model, String type) {
		// TODO 未测试
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> typelist = new ArrayList<String>();
		Element car_element = CAR_CACHE.get(model.getVin());
		if (car_element != null) { //
			map = (Map<String, Object>) car_element.getObjectValue();
			Integer count = (Integer) map.get("count");
			typelist = (List<String>) map.get("type");
			if (!typelist.contains(type)) { // 如果该部件损坏没有缓存
				count++;
				if (count > 3) {
					sendMessage(model);// 发生事故通知车主的至亲
				} else {
					CAR_CACHE.remove(model.getVin()); // 清除旧缓存
					typelist.add(type);
					map.replace("count", count);
					map.replace("type", typelist);
					Element new_element = new Element(
							car_element.getObjectKey(), // 更新旧缓存的数据
							map, car_element.getVersion(),
							car_element.getCreationTime(),
							car_element.getLastAccessTime(),
							car_element.getLastUpdateTime(),
							car_element.getHitCount());
					CAR_CACHE.put(new_element);// 添加更新缓存
				}
			}
		} else { // 如果没有该车量的缓存数据
			typelist.add(type);
			map.put("count", 1);
			map.put("type", typelist);
			CAR_CACHE.put(new Element(model.getVin(), map));
		}
		// test
		Element test_element = CAR_CACHE.get(model.getVin());
		logger.info("----------test_element:"
				+ test_element.getObjectValue().toString());
	}

	/**
	 * 发送短信到至亲手机.
	 *
	 * @param model
	 */
	public void sendMessage(Car model) {
		User user = userService.findOne(model.getUserId());
		String address = AddressHelper.getAddress(model.getLat(),
				model.getLon());// 获取用处当前地址
		String message = "【车联网紧急通知】 您好，用户" + user.getUsername() + "在大约"
				+ address + "附近," + " 驾驶着车牌号为：" + model.getPlateNumber()
				+ " 的车辆多处发生故障，疑似发生事故，请及时联系车主确保安全。";
		try {
			int send = SmsbaoHelper.send(user.getRelatedPhone(), message);
			if (send != 0) {
				throw new DomainSecurityException("发送失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
