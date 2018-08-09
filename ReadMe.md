### 业务描述
电子商务网站常见的一种营销手段，将少量商品(通常只有一件)以极低的价格，在特定时间点开始出售。比如一元钱的手机，5元钱的电脑等。商品价格诱人，而且数量有限。在秒杀活动开始之前买家进入网站，等到秒杀活动开始的一瞬间，点下购买按钮(在此购买按钮为灰色，不可点击)，抢购商品，活动结束后，卖家再通过查看订单进行发货，网站可以通过这种营销手段，制造轰动效应，起到推广的目的。

### 技术挑战
秒杀活动带来的并非访问用户是平时网站的数百倍甚至上千倍，网站如果为秒杀时的最高并发访问量进行设计部署，就需要比正常运营多的多的服务器，而这些服务器在绝大部分时候都是用不上的，浪费惊人。所有网站的秒杀业务一般不与正常网站交易业务共用服务器，也不集成于一个系统中，防止秒杀活动崩溃影响原网站的正常运营，必须设计部署专门的秒杀系统，进行专门应对。

#### 常见问题
高并发下应用，数据库负载：容易出现超卖/少卖 同一用户购买多件商品

服务器带宽：假设商品页面200K(大部分商品图片) 那么10000个用户同时访问那需要的网络和带宽就是(200K * 10000)2G

安全方面：通过机器恶意抢购、刷单

### 技术解决
#### 高可用
服务器集群+Nginx负载均衡提高吞吐量 横向扩展 应对更大的流量和并发只需增加服务器即可

#### 高并发、高性能
##### 前端
 - 浏览器缓存 设置HTTP头中的Cache-Control和Expires的属性，实现商品页面静态化，数据通过Json进行交互。
 - 减少Cookie传输 静态化资源(css、js、图片、文件)通过独立域名访问
 - CDN加速 部署多个节点 缓存静态资源
 - 方向代理配置缓存来加速 web 的请求响应速度
##### 服务
 - 分布式Session：用户登录生成Cookie 保存token 每次请求通过自定义参数解析器来获取token 去Redis缓存查找是否有相应用户信息
 - Redis预减库存：系统初始化时将商品的库存信息存在Redis缓存中，每次下单先通过Redis预先减去库存，同时设置内存标记判断库存是否为空 如果为空后面的请求直接忽略
 - 异步：通过RabbitMq消息队列进行流量削峰，先返回成功请求然后客户端浏览器每隔一段时间主动轮询调用查询接口是否秒杀成功(时间由并发量和用户体验来定)
 - 图形验证码：生成验证码 将用户ID和验证码作为key和value存储在Redis缓存中 用户下单时需校验验证码是否正确
 - 秒杀地址URL动态化：通过uuid随机生成path参数 并通过md5进行加密 
 - 接口限流：自定义注解 实现一段时间内限制请求次数 
 - 设置用户ID为唯一索引 避免同一用户购买多件商品 库存减少时添加判断where count > 0

#### 技术选型
技术 | 名称 | 官网
---|---|---
Spring Boot | Spring快速开发框架 | 	https://spring.io/projects/spring-boot
Redis | 分布式缓存数据库 | https://redis.io/
MyBatis | ORM框架 | http://www.mybatis.org/mybatis-3/zh/index.html
MyBatis Generator | 代码生成 | http://www.mybatis.org/generator/index.html
PageHelper | MyBatis物理分页插件 | http://git.oschina.net/free/Mybatis_PageHelper
druid | 数据库连接池 | https://github.com/alibaba/druid
Logback | 日志组件 | https://logback.qos.ch/
RabbitMq | 消息队列 | https://www.rabbitmq.com/
Maven | 项目构建管理 | http://maven.apache.org/
Git | 分布式版本控制 | https://git-scm.com/

#### 系统流程图


#### 相关界面


#### 启动
```
git clone git@github.com:zhonglunsheng/seckill.git
```
maven 导入项目

Redis服务安装：http://blog.lipop.club/archives/29.html

RabbitMq服务安装：http://blog.lipop.club/archives/13.html




