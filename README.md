### 该项目基于[黄勇-轻量级分布式 RPC 框架](https://my.oschina.net/huangyong/blog/361751?p=1)。
#### 在此基础上，将客户端的访问方式转换成类似于dubbo服务的访问。用户只需将服务(service)注册到xml中(自定义标签)，即可通过直接注入方式(@Autowired)对service直接进行数据访问。         
原客户端访问接口方式：
```java
ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
RpcProxy rpcProxy = ctx.getBean(RpcProxy.class);
HelloService helloService = rpcProxy.create(HelloService.class);
String result = helloService.hello("World");
System.out.println(result);
```
转换后的客户端访问接口方式：
```java
ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
HelloService helloService = ctx.getBean(HelloService.class);
String str = helloService.hello("World"); // 直接使用，不需要create()创建
System.out.println(str);
```
将rpcProxy.create()的工作交由spring管理，用户只需在xml里注册如下：
```xml
<sijia3:service id="helloService" version="" class="com.sijia3.api.HelloService"/>
```