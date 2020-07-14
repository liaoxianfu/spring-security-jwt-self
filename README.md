## 说明
整合了Springboot+Mybatis+Jwt 实现RBAC以及crsf控制

## 使用
将resources/sql文件夹下的sql导入mysql数据库 
更改`application-dev.yml`下的数据库配置信息为自己的数据库（mysql和redis）连接信息。


运行项目即可

## 测试

默认提供了两用户 `admin` `demo` 密码均为`123456`

使用postman进行测试
![postman 测试](https://github.com/liaoxianfu/spring-security-jwt-self/blob/master/src/main/resources/img/login.png)

拿到token后，在每次请求的时候需要在header上携带
jwt-token和 X-XSRF-TOKEN

**注意**
X-XSRF-TOKEN 需要每次都从cookie中获取

![test](https://github.com/liaoxianfu/spring-security-jwt-self/blob/master/src/main/resources/img/test.png)