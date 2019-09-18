# working

#### 介绍
平时工作中其实有很多重复性的内容，比如搭建项目的时候需要搭建security、需要配置日志、需要处理报错等等。
working的目标就是将日常重复性的东西封装成starter，利用springboot的自动配置开箱即用。

#### 软件架构
软件架构说明


#### 安装教程



#### 使用说明


```yaml
working:
 spring:
  security:
   enable: false #是否开启working-spring-security
   login:
    enable: false #是否开启自定义login相关路径
    loginUrl: /login
    loginPage: /login/page
    successForwardUrl: /
    logoutUrl: /logout
    logoutSuccess: /logout/success
   type: rest
  web:
   debug: false  #开启请求Debug模式，展示部分请求debug信息
```