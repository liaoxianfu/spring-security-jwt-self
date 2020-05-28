## 说明
整合了Springboot+Mybatis+Jwt 实现RBAC以及crsf控制

## 使用
将resources/sql文件夹下的sql导入mysql数据库 
更改`application-dev.yml`下的数据库配置信息为自己的数据库连接信息。

运行项目即可

## 测试

默认提供了两用户 `admin` `demo` 密码均为`123456`

使用postman进行测试
![postman 测试](src/main/resource/img/login.png)
<img src="src/main/resource/img/login.png"/>

