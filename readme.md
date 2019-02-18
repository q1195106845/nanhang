# 南航OA一键启动服务
### 实时更改用户密码
- 推：CmdController中有API接口支持主动推送用户信息进行更新和新增用户
- 拉：UserTask中会有定时任务每分钟主动获取南航方的用户信息进行操作,
访问南航的URL连接可以通过properties中进行修改

startVmClient 会产生2个文件 一个是vm启动的bat文件 
一个是 reg 添加potocl的文件