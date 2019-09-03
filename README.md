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
 security:
  enable: true       #是否开启working-spring-security
  type: rest         #security类型(MVC、rest)
  login: /login      #登录url
  login-page: /login #登录页面url
  #success-forward-url: / #登录成功重定向页面
  logout: /logout    #注销页面
  logout-success: /  #注销成功url
  mode: black        #投票模式：black黑名单模式，默认白名单模式
 web:
  debug: false       #开启请求Debug模式，展示部分请求debug信息
```