## 项目介绍
 + 该项目为学习微服务演示项目 
    + 注册中心（eureka）<br>
    ![注册中心](https://upload-images.jianshu.io/upload_images/5624515-914f6603e04417a2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/490/format/webp)
       + Eureka提供了服务注册和服务发现功能，管理分布式系统中的各种服务，比如注册、发现、熔断、负载均衡等<br>
       + 分布式系统中所有的服务都将注册到注册中心,所有的调用必须通过注册中心来调用
       + 每个服务注册到服务中心时只需提供一个注册名称,调用时将通过服务名称来调用
       + 保证服务的高可用性,可以提供几台相同的服务注册到服务中来做集群和负载均衡
    + 配置中心(configdashboard）<br>
      ![配置中心](https://upload-images.jianshu.io/upload_images/5624515-a1d180f31e9aa059.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/720/format/webp)
       + 某一个项目配置的变动都有可能导致依赖这个项目的一系列其他项目需要重启和更新 
       + 为配置中心的配置文件添加git托管
          + 修改配置文件 ---> 提交到git中心  --->配置中心更新配置并刷新所有读取这些配置的微服务
       + 消息总线：使用中间件来作为消息代理实现一个共用的消息系统，分布式中的其他服务都连接到消息系统，并且实现消息的生产、监听、和消费   
          + 使用 RabbitMQ来整合SpringCloudBus实现消息总线
     + API网关(gateway)     
       + api网关可以将多个微服务的调用逻辑进行聚合，减少客户端的请求次数，优化客户端的使用体验      
       + 主要功能：通过API网关调用OAuth2服务器来过滤用户的请求，保证对外服务的安全性   
     + OAuth2认证授权服务（authserver）<br>
      ![认证授权](https://upload-images.jianshu.io/upload_images/5624515-eca0e75f2e23651a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/661/format/webp)      
## 环境安装
  + [rabbitMq官网](https://www.rabbitmq.com/)
    + [rabbitMq管理界面](https://blog.csdn.net/yongche_shi/article/details/53319770)
  + mongodb
  + redis
  + natapp(内网穿透工具)

## 参考资料
  + 蓝士钦的springcloud系列：https://www.jianshu.com/u/082f73dac819
