# language: zh-CN
@foo
功能:注册

#  中文实现
  @NeedBeforStep
  场景:注册用户成功

      当填写的登录账号为18719425973

      并且不存在重复注册的账号

      而且填写的密码是123456

      那么看到的结果是注册成功

  场景:注册用户失败

      当填写的登录账号为18719425973

      那么看到的结果是已存在该用户
