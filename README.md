# ApiStarter

#### 项目介绍
服务端基础通用框架提取,配以详细的说明文档,针对Restful风格API服务器,降低学习成本,提高开发效率.

#### 系统架构
本项目统一使用post请求访问接口,
使用AdapterController作为统一的api入口.
请求业务模块为service的类名,请求业务方法为service的方法名,
在AdapterController方法中,通过@PathVariable获取请求参数中的模块与业务方法,
通过ApplicationContext获取模块对象,使用反射method.invoke执行真正的调用方法.
在ResponseHandlerAspect与ApiRecordAspect两个切面中分别对请求结果包装以及请求访问日志记录,
使用JWT为登录用户颁发Token,在JwtTokenAspect切面中,对所有service中方法包含自定义注解@TokenValidate的方法进行Token验证.

在牺牲了一小部分反射带来的性能浪费的下,极大的简化了开发操作流程,使用户能够更快的进行服务端开发,专注业务实现.

![Image text](https://gitee.com/weixin54321a/ApiStarter/raw/master/resource/request.png)

#### 软件环境
    
技术|描述|
---|---|
SpringBoot|项目框架
JWT|Token管理工具
Mybatis|持久层框架
Druid|数据库连接池及监控
Mysql | 数据库 
Maven | 项目构建管理
logback | 日志管理

#### 相关插件
插件|描述|
---|---|
Lombok|简化getter/setter/logger等代码编写
Mybatis Generator|根据库表自动生成model,查询example,mapper,xml插件
Mybatis Generator plgins| 自定义Mybatis Generator插件,改变生成代码行为
PageHelper mybatis|分页插件

#### 目录结构描述

```shell
.
├── java java文件
    └── com
        └── framework
            ├── annotations 自定义注解
                └── TokenValidate.java Token验证注解,在service里需要登录才能访问的方法加上此标记
            ├── bean 数据封装
                ├── request 请求数据包装
                └── response 响应数据包装
            ├── conf 系统配置
                ├── aspect 切面配置
                    ├── ApiRecordAspect.java 请求日志切面,此处记录API请求响应以及相关数据
                    ├── JwtTokenAspect.java Token切面,此处对有TokenValidate标记的注解进行Token校验
                    └── ResponseHandlerAspect.java 响应处理切面,对系统api调用数据进行封装,异常解析包装处理
                ├── druid druid sql监控配置文件
                ├── filter 过滤器
                    └──  WebConfiguration.java web请求过滤器(*练习*)
                └── properties 配置文件
                    └── SystemProperties.java 系统自定义配置文件,在application*.yml配置
            ├── constant 系统常量
            ├── controller 控制器,程序请求统一入口
            ├── dao dao层
                └── mybatis mybatis相关
                    ├── generator mybatis generator 代码生成
                        ├── plugins mybatis generator 自定义插件,实现生成代码行为自定义
                        └── MybatisGenerator.java 根据数据库表生成java model与查询example的主类.
                    ├── mapper mybatis 生成的表对应的mapper类
                    └── model mybatis 生成的表对应的model
            ├── dto 数据封装简单对象
            ├── doc 自定义javaDoc插件,根据注释生成接口文档模板
            ├── exception 异常信息
                ├── handler 全局异常管理实现
                └── 其他自定义异常信息
            ├── linerunner 启动初始化业务相关操作,例如一些缓存,初始化管理员账号等,可在此操作
            ├── pay 支付相关
                ├── client 不同支付sdk client 统一单利初始化
                ├── controller 支付回调通知统一入口
                └── service 支付业务接口
                    └──impl 不同支付方式具体实现
            ├── serivce 业务接口 业务具体操作在此处进行
                └── impl 业务具体实现,对于需要Token验证的方法,加上自定义@TokenValidate注解
            ├── task 通过Scheduled注解实现cron表达式的定时任务   
            └── utils 工具类
                └── jwt Token具体实现
└── resources 配置文件资源
    ├── mybatis  mybatis相关配置
        ├── confug mybatis配置文件
        ├── generator generator代码生成策略,路径等相关配置文件
        ├── mapper generator生成mapper.xml文件
    ├── application.yml 各个环境相同配置文件
    ├── application-dev.yml 测试环境独有配置文件
    ├── application-prod.yml 生产环境独有配置文件
    └── logback-spring.xml logback日志配置文件,指定日志滚动策略,级别等.
```

#### 快速安装

1. 克隆项目到本地Ide
2. 安装数据库,更新application-dev/prod.yml数据源配置,导入sql文件
    (注:mysql建议版本5.+,如果更换版本请一并更换pom.xml文件中的驱动版本)
3. 配置Maven环境,拉取jar包
4. 执行Application类main函数,启动项目
5. 使用post请求接口,获得响应

#### 数据库监控访问后台

    ip:port/druid/index.html
    示例:
    
![Image text](https://gitee.com/weixin54321a/ApiStarter/raw/master/resource/druid.png)


#### 接口描述
    
    由于本项目是通过反射调用service实现restful风格的接口,而swagger是通过@ReqestMapping+swagger注解
    来定位接口的,所以集成swagger比较麻烦,还需要额外维护swagger,所以这里放弃使用了
    推荐一个使用免费的在线接口描述文档工具APIVIEW,官网地址https://www.apiview.com,基本满足小型项目的使用,
    另外也可以搭建confluence,搭建挺简单的,做好数据备份就可以了

#### 执行流程
##### 键入域名回车

##### 域名解析
    
    作用: DNS解析的作用是把域名解析成相应的IP地址，根据IP地址决定将报文发给谁。
	
	浏览器开始解析域名,即查找过程.
	浏览器自身DNS缓存(缓存一分钟,最大1000条左右)-->
	操作系统自身DNS缓存-->
	本机host文件映射-->
	Windows调用53端口发送UDP请求
	本地配置首选DNS服务器--> Root Server获取gTLD Server-->	
	gTLD Server获取Name Server-->
	Name Server获取子Name Server ... -->
	Name Server返回IP地址给本机 -->
	浏览器缓存结果 -->
	结束
	
##### 建立连接TCP/TP连接

	准备:服务器启动,监听9999端口
	客户端使用操作系统随机分配的端口号(1000~65535)与服务器的IP+端口号通过三次握手,建立连接(浏览器没有填写端口号默认为80)
	
	TCP标志位,有6种标示:
    SYN(synchronous建立联机)
    ACK(acknowledgement 确认)
    PSH(push传送)
    FIN(finish结束)
    RST(reset重置)
    URG(urgent紧急)
    Sequence number(顺序号码)
    Acknowledge number(确认号码)
	TCP状态标识:
	LISTEN - 侦听来自远方TCP端口的连接请求; 
    SYN-SENT -在发送连接请求后等待匹配的连接请求; 
    SYN-RECEIVED - 在收到和发送一个连接请求后等待对连接请求的确认; 
    ESTABLISHED- 代表一个打开的连接，数据可以传送给用户; 
    FIN-WAIT-1 - 等待远程TCP的连接中断请求，或先前的连接中断请求的确认;
    FIN-WAIT-2 - 从远程TCP等待连接中断请求; 
    CLOSE-WAIT - 等待从本地用户发来的连接中断请求; 
    CLOSING -等待远程TCP对连接中断的确认; 
    LAST-ACK - 等待原来发向远程TCP的连接中断请求的确认; 
    TIME-WAIT -等待足够的时间以确保远程TCP接收到连接中断请求的确认; 
    CLOSED - 没有任何连接状态;
	
	第一次握手：建立连接时，客户端发送syn包(syn=j ack=0 seq=x)到服务器，并进入SYN_SEND状态，等待服务器确认; 
	第二次握手：服务器收到syn包，必须确认客户的SYN(ack=j+1），同时自己也发送一个SYN包(syn=k），即SYN+ACK包，此时服务器进入SYN_RECV状态; 
	第三次握手：客户端收到服务器的SYN＋ACK包，向服务器发送确认包ACK(ack=k+1)，此包发送完毕，客户端和服务器进入ESTABLISHED状态，完成三次握手。
	至此浏览器随机端口与服务器端口的TCP/IP连接建立成功
	
	?:为何TCP建立连接需要三次握手,而不是两次,四次
	第一次握手： A给B打电话说，你可以听到我说话吗？
    第二次握手： B收到了A的信息，然后对A说： 我可以听得到你说话啊，你能听得到我说话吗？  
    第三次握手： A收到了B的信息，然后说可以的，我要给你发信息啦！ 
    在三次握手之后，A和B都能确定这么一件事： 我说的话，你能听到; 你说的话，我也能听到。 这样，就可以开始正常通信了。
    注意： HTTP是基于TCP协议的，所以每次都是客户端发送请求，服务器应答，但是TCP还可以给其他应用层提供服务，
    即可能A、B在建立链接之后，谁都可能先开始通信。
    如果两次，那么B无法确定B的信息A是否能收到，所以如果B先说话，可能后面的A都收不到，会出现问题 。
    如果四次，那么就造成了浪费，因为在三次结束之后，就已经可以保证A可以给B发信息，A可以收到B的信息; B可以给A发信息，B可以收到A的信息。
	
##### 浏览器发送数据

	浏览器按照Line,Header,data,的格式封装HTTP请求报文
	建立一个Socket对象
	把http请求报文转变成byet[]字节
	然后调用Socket.Sent()方法经TCP连接把这些数据打包发送到服务器
	
	HTTP协议格式如下:
	请求方法 URL 版本
	
	Headers(K-V类型)
	
	请求数据
	
	注:考虑请求数据包大小与实际网络带宽,延迟,网络传输丢包等对并发量影响.
		
##### 服务器接收数据

	数据经过TCP连接传输过来以后
	Coyote HTTP/1.1 Connector监听到请求,
	创建 Request 和 Response 对象分别用于和请求端交换数据，创建新线程并传递 Request 和 Response 对象此线程,调用HttpProcesser并分配此socket给HttpProcesser
	-->
	HttpProcesser执行run方法,调用process方法创建SocketIn/OutputStream对象,解析Http协议的Line,Header,data数据,包装request与response
	-->
	Connector把该请求交给它所在的Service的Engine来处理，并等待来自Engine的回应
	-->
	Engine匹配它所拥有的所有虚拟主机Host找到对应的Host
	-->
	Host匹配它所拥有的所有Context
	-->
	请求到达DispatcherServlet

##### 分发处理	

	DispatcherServlet收到请求后,作为统一访问点,委托给其他的解析器进行处理，进行全局的流程控制
	DispatcherServlet-->    通过getHandler方法,将请求包装为HandlerExecutionChain对象
	(包含一个Handler处理器对象、多个HandlerInterceptor拦截器对象）;
	DispatcherServlet-->    通过getHandlerAdapter方法，确定当前请求处理适配器。
	DispatcherServlet-->    通过HandlerExecutionChain.applyPreHandle(processedRequest, response)方法,对请求进行intercepter拦截器组的前置处理
	DispatcherServlet-->    HandlerAdapter.handle处理器功能处理方法的调用,并返回一个ModelAndView对象
	
	                        HandlerAdapter.handle-->    AbstractHandlerMethodAdapter.ModelAndView 
	                                                    --> RequestMappingHandlerAdapter.handleInternal-->
	                                                                                    .invokeHandlerMethod
	                                                                                    返回ModelAndView对象(包含模型数据、逻辑视图名)
	                                                    
	                                                    处理方法调用
	                                                    --> ServletInvocableHandlerMethod.invokeAndHandle
	                                                                                    .invokeForRequest
	                                                                                    .doInvoke
	                                                                                    最终getBridgedMethod().invoke(getBean(), args);完成方法调用.
	                                                                                    
	                                                        
	ModelAndView的逻辑视图名——> ViewResolver， ViewResolver将把逻辑视图名解析为具体的View，通过这种策略模式，很容易更换其他视图技术;
	View——>渲染，View会根据传进来的Model模型数据进行渲染，此处的Model实际是一个Map数据结构，因此很容易支持其他视图技术;
	DispatcherServlet-->    通过HandlerExecutionChain.applyPostHandle(processedRequest, response, mv);方法,对请求进行intercepter拦截器组的后置处理
	返回控制权给DispatcherServlet，由DispatcherServlet返回响应给用户
	

##### 二次分发
    
    在DispatcherServlet分发到AdapterController后,通过代理执行AdapterController.invokeApi方法,
    请求到达invokeApi方法,首先从请求路径中获取channel(请求渠道,用以初步验证请求),module(请求模块),method(请求方法),body(请求内容)映射为方法参数
    执行方法前会根据aspect包里面的配置切面,执行环绕通知的ResponseHandlerAspect,对请求结果进行渠道验证,异常包装.
    接着执行ApiRecordAspect,对请求进行记录并存入数据库,以便将来查看日志,记录数据包括,请求模块,方法,参数,时间,请求数据,响应数据等.
    
    经切面处理后,请求到达invokeApi方法体,在此方法中:
    根据module参数,通过ApplicationContext获取执行目标模块bean
    根据method参数,构造Method对象
    method.invoke(service, body)通过反射执行module对象的方法.

    请求到达service实现类具体方法,根据JwtTokenAspect定义的切点信息,对方法上带有@TokenValidate的方法调用进行Token验证

##### 业务处理
    
    如果业务方法包含@TokenValidate注解,请求经Token验证后到达service方法体,  
    如果业务方法不包含@TokenValidate注解,请求经过 method.invoke(service, body)直接到达service方法体,
    至此,请求开始执行业务逻辑.
    
##### mybatis缓存
    
    SESSION或STATEMENT作用域级别的缓存，默认是SESSION，BaseExecutor中根据MappedStatement的Id、SQL、
    参数值以及rowBound(边界)来构造CacheKey，并使用BaseExccutor中的localCache来维护此缓存。
    全局的二级缓存，通过CacheExecutor来实现，其委托TransactionalCacheManager来保存/获取缓存
    
    MyBatis自身提供了一个简易的数据源/连接池，在org.apache.ibatis.datasource下。主要实现类是PooledDataSource，包含了最大活动连接数、
    最大空闲连接数、最长取出时间(避免某个线程过度占用)、连接不够时的等待时间，虽然简单，却也体现了连接池的一般原理
    
    MyBatis对事务的处理相对简单，TransactionIsolationLevel中定义了几种隔离级别，并不支持内嵌事务这样较复杂的场景，
    同时由于其是持久层的缘故，所以真正在应用开发中会委托Spring来处理事务实现真正的与开发者隔离。

##### 数据持久化
    
    
##### 注入简述

	1. @Autowired
    @Autowired是spring自带的注解，通过‘AutowiredAnnotationBeanPostProcessor’ 类实现的依赖注入;
    @Autowired是根据类型进行自动装配的，如果需要按名称进行装配，则需要配合@Qualifier;
    @Autowired有个属性为required，可以配置为false，如果配置为false之后，当没有找到相应bean的时候，系统不会抛错;
    @Autowired可以作用在变量、setter方法、构造函数上。
    @Autowired可以对成员变量、方法以及构造函数进行注释，而 @Qualifier 的标注对象是成员变量、方法入参、构造函数入参。
    
    2. @Inject
    @Inject是JSR330 (Dependency Injection for Java)中的规范，需要导入javax.inject.Inject;实现注入。
    @Inject是根据类型进行自动装配的，如果需要按名称进行装配，则需要配合@Named;
    @Inject可以作用在变量、setter方法、构造函数上。
    @Named("XXX") 中的 XX是 Bean 的名称，所以 @Inject和 @Named结合使用时，自动注入的策略就从 byType 转变成 byName 了。
    
    3. @Resource
    @Resource是JSR250规范的实现，需要导入javax.annotation实现注入。
    @Resource是根据名称进行自动装配的，一般会指定一个name属性
    @Resource可以作用在变量、setter方法上。
    
    @Autowired是spring自带的，@Inject是JSR330规范实现的，@Resource是JSR250规范实现的，需要导入不同的包
    @Autowired、@Inject用法基本一样，不同的是@Autowired有一个request属性
    @Autowired、@Inject是默认按照类型匹配的，@Resource是按照名称匹配的
    @Autowired如果需要按照名称匹配需要和@Qualifier一起使用，@Inject和@Name一起使用
    
##### 类加载过程
	
	类加载:通过一个类的全限定名来获取描述此类的二进制字节流
	类加载器:实现类加载的代码模块儿.	
			Bootstrap ClassLoader根加载器(启动类加载器)	JAVA_HOME/lib中的jar
			-->扩展类加载器EXT ClassLoader	JAVA_HOME/lib\ext中的jar
			-->应用类加载器APP ClassLoader	classpath指定的类库
			-->自定义类加载器
	双亲委派模型:如果一个类加载器收到了加载类的请求,它首先不会自己去尝试加载这个类,而是把请求委派给父类去完成,
	每一个层次的类加载器都是如此,因此所有的类加载请求最终都应该传送到顶层的启动类加载器中,只有当父类加载器反馈自己无法完成这个加载请求时,
	子类加载器才会尝试自己去加载.
	
	加载:读取class文件字节流
	验证:验证魔数,版本号,元数据语义分析(例如类是否继承Object),字节验证(例如int参数在方法有没有按照Long来使用),
	    符号引用数据(例如符号引用的类是否是可以被继承的)
	准备:为类静态变量分配内存并设置类变量初始零值
	解析:符号引用转换为指针,句柄相对偏移量的直接引用
	初始化:赋初始值
	使用:调用构造方法第三次赋值
	卸载:虚拟机自行完成

##### 对象回收

	引用计数算法
	
	Java可达性分析算法-->强引用,软引用,弱引用,虚引用
		通过一系列GCRootS向下搜索,搜索路径称为引用链,当对象到任何GCRoots没有任何引用相连,即GCRoots到这个对象不可达,则证明这个对象是无用对象
	
	回收算法
		标记-清除算法:首先标记处需要回收的对象,标记完成后统一回收.
			问题:标记与清除过程效率不高,标记清除后产生大量不连续空间,导致分配大对象时找不到连续的可用空间导致提前触发一次垃圾回收

	复制算法:
		将内存分为大小相等的两部分，每次只使用其中的一部分，等这部分用完了，这时候就将这里面还能活下来的对象复制到另一部分内存中，然后把剩下部分全部清理掉。
		问题:只有一半能用--

	标记整理算法
		算法不直接对可回收对象进行清理，而是让所有可用的对象都向一端移动。然后直接清理掉边界意外的内存。
		问题:整理耗时

	分代收集算法


##### 线程同步原理

    参考链接http://www.hollischuang.com/archives/1876
    
    栈        线程独立    只存储基本类型与对象引用
    
    堆        线程共享    只存储对象
    方法区    线程共享    只存储类的静态变量(只包括基本类型与对象引用)
    
    
    为了协调对象多线程下访问状态,虚拟机给每个对象与类都分配了一个特权锁,
    同一时刻,只有一个线程可以拥有这个对象或类的使用权力.如果一个线程想获
    得某个对象或类的使用权,需要先向虚拟机申请.可能会很快或很慢获得锁,也
    可能永远不会获得锁,当线程不再需要锁的时候，他再把锁还给虚拟机。这时虚
    拟机就可以再把锁分配给其他申请锁的线程。
    
    注:类锁也是基于对象实现的,因为虚拟机加载该类的时候会给这个类生成一个
    java.lang.Class对象,即类对象.当你对一个类加锁时,其实是锁住了这个类的
    Class对象
    
    监视器
    虚拟机通过监视器确保同一时间只有一个线程在执行一段代码,当线程执行到监视器
    监视的代码块第一条指令时,线程必须获取被引用对象的锁.获取锁之前,线程是无法
    执行这段代码的,一旦获得锁,线程遍可以进入"被保护"的代码开始执行.当线程离开
    代码块时,会释放所关联对象的锁.
    
    多次加锁
    同一线程可以对同一对象进行多次枷锁,每个对象维护着一个被锁次数的计数器
    未被锁定的对象的该计数器为0,当一个线程获得锁后,计数器自增变为1
    当同一线程再次获得该对象的锁的时候,计数器再次自增.当同一线程释放
    锁的时候,计数器再自减.当计数器为0的时候,锁被释放,其他线程
    便可以获得锁.                    
  

##### 虚拟机参数简述:
    
    JVM命令支持广泛的选项，可分为以下类别：
    
    标准选项
    	-client -server(默认)
    
    非标准选项
    	-X 显示所有可用-X选项的帮助
    	
    高级运行时选项
    	-XX:ErrorFile=filename
    
    高级JIT编译器选项
    	-XX:+AggressiveOpts 允许jit性能优化
    
    高级服务性选项
    	-XX:+HeapDumpOnOutOfMemory堆栈信息
    	
    高级垃圾收集选项
    	-XX:+CMSClassUnloadingEnabled 使用CMS垃圾收集机制
    	
##### 虚拟机参数详细文档:
    
    JVM参数使用:
    java [ options ] classname [ args ]
    java [ options ] -jar filename [ args ]
    javaw [ options ] classname [ args ]
    javaw [ options ] -jar filename [ args ]
    
    java/javaw 命令行选项由空格分隔	class/jar名字 传递给main函数的参数
    
    描述:
    	用java命令启动程序.首先会启动java运行时环境(jre),然后加载指定的类,并调用该类的main方法.
    	该方法必须声明为public static void的,并且接受String数组作为参数.方法声明形式如:public static void main(String [] args)
    	java命令也可以用于加载有main方法或扩展的类来启动javaFX应用程序javafx.application.Application.在后一种情况下,启动器构造Application类的实例,调用init()方法,然后调用start(javafx.stage.Stage)方法
    	
    	默认情况下，第一个不属于java命令选项的参数是要调用的类的完全限定名。如果指定了-jar选项，其参数是包含应用程序的类和资源文件的jar文件的名称。启动类必须由源代码中的Main-class清单头指示。
    	JRE在三个位置中搜索启动类（和应用程序使用的其他类）：引导类路径、安装的扩展和用户的类路径。
    	将类文件名或JAR文件名后的参数传递给main（）方法.
    
    	javaw命令与java相同，只是在javaw中没有关联的控制台窗口(后台启动)。当不希望出现命令提示符窗口时使用javaw。但是，如果启动失败，javaw启动器将显示一个包含错误信息的对话框。	
    	
    java命令支持多种选项,可分为以下几类:
    	1标准选项
    	2非标准选项
    	3运行时选项
    	4JIT编译选项
    	5服务性选项
    	6垃圾收集选项
    
    jvm的所有实现都保证标准选项得到支持,他们用以常见的操作,比如检查jre版本,设置类路径,启用详细输出等等
    
    非标准选型是特定于Java HotSpot虚拟机的通用选项,所以不保证所有的jvm实现都支持他们,而且可能发生变化这些选项以-X开头
    
    不建议随意使用高级选项。这些是用于优化Java HotSpot虚拟机操作的特定区域的开发人员选项，这些区域通常具有特定的系统需求，并且可能需要对系统配置参数进行特权访问。它们也不能保证能被所有JVM实现所支持，并且是可以更改的。高级选项以-XX开头。
    
    文档末尾有一个名为Deprecated and Removed Options的部分,可以查看已弃用或删除的选项.
    
    布尔值选项用于启用默认禁用的功能，或禁用默认启用的功能。这些选项不需要参数。选项是使用加号（-XX:+OptionName）启用的，并使用减号（-XX:-OptionName）禁用。
    
    对于需要参数的选项，参数可以通过空格，冒号（:)或等号（=）与选项名称分隔，或者参数可以直接跟随选项（每个选项的确切语法不同） ）。如果你想指定字节大小，你可以使用任何后缀，或者使用后缀k或K千字节（KB），m或M兆字节（MB）g或G千兆字节（GB）。例如，设置大小为8 GB，您可以指定8g，8192m，8388608k，或8589934592作为参数。如果要指定百分比，请使用0到1之间的数字（例如，指定0.2525％）。
    
###### 一丶标准选项
        
    	这些是所有JVM实现所支持的最常用的选项。
    	1.-agentlib:libname[=options]
    		加载指定的本机代理库。在库名之后，可以使用一个逗号分隔的库选项列表（该库特有的选项列表）。
    		如果指定了选项-agentlib:foo，那么JVM会尝试在PATH系统变量指定的位置加载名为foo.dll的库。
    		下面的示例演示如何加载堆分析工具（HPROF）库，并每20ms获取一个样本CPU信息，堆栈深度为3：
    			-agentlib:hprof=cpu=samples,interval=20,depth=3
    		下面的示例演示如何加载Java Debug Wire Protocol (JDWP)库并侦听端口8000上的套接字连接，在主类加载之前挂起JVM：
    			-agentlib:jdwp=transport=dt_socket,server=y,address=8000
    		有关本机代理库的更多信息，请参考以下内容：
    			工具包的描述在:http://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html
    			在JVM Tools Interface指南中的代理命令行选项：http://docs.oracle.com/javase/8/docs/platform/jvmti/jvmti.html#starting
    			
    	2.-agentpath：pathname [= options ]
    		加载由绝对路径名指定的本机代理库。此选项等效于-agentlib，不同的是它使用库的完整路径和文件名。
    	
    	3.-client
    		选择Java HotSpot客户端VM。Java SE开发工具包（JDK）的64位版本目前忽略了这个选项，默认使用Server JVM。
    		有关默认JVM选择，请参阅服务器级机器检测: http://docs.oracle.com/javase/8/docs/technotes/guides/vm/server-class.html
    	
    	4.-Dproperty=value
    		设置系统属性值。属性变量是一个没有空格表示属性名称的字符串。值变量是表示属性值的字符串。如果值是有空格的字符串，则将其括在引号中（例如-Dfoo=“foo bar”）。
    		
    	5.-disableassertions[:[packagename]...|:classname]
    	  -da[:[packagename]...|:classname]
    		禁用断言。默认情况下，在所有包和类中禁用断言。
    		如果没有参数,-disableassertions（-da）将禁用所有包和类中的断言,如果指定了packagename,将禁用包下所有子类的断言,如果是...,将禁用当前目录的断言,如果是classname,将禁用指定类的断言.
    		-disableassertions（-da）选项适用于所有的类加载器和系统类,此规则有一个例外：如果选项没有参数，则它不适用于系统类。这使得在系统类之外的所有类中都可以轻松禁用断言。该-disablesystemassertions选项使你可以禁用所有系统类中的断言.
    		要在特定包或类中显式启用断言，请使用-enableassertions（-ea）选项。两个选项可以同时使用。例如，要MyClass在包com.wombat.fruitbat（包括子包）中启用断言但在类com.wombat.fruitbat.Brickbat中禁用的情况下运行应用程序，请使用以下命令：
    			java -ea：com.wombat.fruitbat ... -da：com.wombat.fruitbat.Brickbat MyClass
    	
    	6.-disablesystemassertions
    	  -dsa	
    		禁用所有系统类中的断言。
    	
    	7.-enableassertions [：[ packagename ] ... |：classname ]
    	  -ea [：[ packagename ] ... |：classname ]
    		启用断言。默认情况下，在所有包和类中禁用断言。
    		用法同上...
    	
    	9.-enablesystemassertions
    	  -esa
    		在所有系统类中启用断言。
    	
    	10.-help
    	   -?
    		不启用jvm的情况下显示java命令的使用信息.
    	
    	11.-jar 文件名
    		执行封装在JAR文件中的程序。需要在清单文件中包含Main-Class:classname启动类,定义与类public static void main(String[] args)相同.
    		有关JAR文件的更多信息，请参见以下资源:jar文件指南: http://docs.oracle.com/javase/8/docs/technotes/guides/jar/index.html
    											  JAR文件中的打包程序: http://docs.oracle.com/javase/tutorial/deployment/jar/index.html
        12.-javaagent:jarpath[=options]
    		加载指定的Java编程语言代理程序。有关检测Java应用程序的更多信息,请参阅:http://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html
    	
    	13.-jre-restrict-search/-no-jre-restrict-search
    		在版本搜索中包含/排除用户私有jar包
    	
    	14.-server
    		选择Java HotSpot Server VM。64位版本的JDK仅支持Server VM，因此在这种情况下，该选项是隐式的。
    		有关默认JVM选择，请参阅服务器级机器检测
    		http://docs.oracle.com/javase/8/docs/technotes/guides/vm/server-class.html
    	
    	15.-showversion
    		显示版本信息并继续执行应用程序。此选项等同于该-version选项，但后者指示JVM在显示版本信息后退出。
    
    	16.-splash：imgname
    		使用imgname指定的图像显示启动画面。例如，要在启动应用程序时显示目录中的splash.gif文件images，请使用以下选项：
    			-splash:images/splash.gif
    	
    	17.-verbose:class
    		显示有关每个已加载类的信息。
    	
    	18.-verbose：GC
    		显示垃圾回收（GC）事件的信息。
    	
    	19.-verbose：JNI
    		显示有关使用本机方法和其他Java Native Interface（JNI）活动的信息。
    			
    	20.-version
    		显示版本信息，然后退出。此选项等同于该-showversion选项，但后者在显示版本信息后不指示JVM退出。
    	
    	21.-version:release
    		指定jdk版本,不存在的话使用默认jdk,例如:
    			-version：“1.6.0_13 1.6 *＆1.6.0_10 +”
    	
    
###### 二丶非标准选项
    	这些选项是特定于Java HotSpot虚拟机的通用选项。
    	
    	1.-X
    		显示所有可用-X选项的帮助。
    	
    	2.-Xbatch ?
    		禁用后台编译。默认情况下，JVM将该方法作为后台任务编译，在解释器模式下运行该方法，直到后台编译完成。Xbatch标志将禁用后台编译，以便所有方法的编译作为前台任务继续进行，直到完成。
    		此选项相当于-XX:-BackgroundCompilation。
    		
    	3.-Xbootclasspath:path
    		用分号指定分隔的目录、JAR文件和ZIP存档的列表，以搜索引导类文件。它们用于替代JDK中包含的引导类文件。
    		不要部署使用此选项的应用程序来覆盖rt.jar中的类，因为这违反了JRE二进制代码许可证。
    	
    	4.-Xbootclasspath/a:path
    		指定由分号（;）分隔的目录，JAR文件和ZIP存档的列表，以附加到默认引导程序类路径的末尾。
    		不要部署使用此选项的应用程序覆盖类rt.jar，因为这违反了JRE二进制代码许可证。
    
    	5.-Xcheck：JNI
    		对Java Native Interface（JNI）函数执行其他检查。具体来说，它在处理JNI请求之前验证传递给JNI函数的参数和运行时环境数据。遇到的任何无效数据都表明本机代码存在问题，在这种情况下，JVM将以无法恢复的错误终止。使用此选项时，预计性能会下降。
    	
    	6.-Xcomp
    		强制第一次调用方法时编译。默认情况下，客户端VM(-Client)执行1000次解释方法调用，服务器VM(-Server)执行10000次解释方法调用来收集信息，以进行高效的编译。指定-Xcomp选项将禁用解释的方法调用，从而以效率为代价提高编译性能。
    		您还可以在使用-XX:CompileThreshold选项进行编译之前更改解释的方法调用的数量。
    	
    	7.-Xdebug
    		什么也没做。提供向后兼容性。
    	
    	8.-Xdiag
    		显示其他诊断消息。
    	
    	9.-Xfuture
    		启用严格的类文件格式检查，以强制类与文件格式规范紧密一致。鼓励开发人员在开发新代码时使用此标志，因为更严格的检查将成为未来版本中的默认值。
    	
    	10.-Xint
    		以仅解释模式运行应用程序。禁用对本机代码的编译，并且解释器将执行所有字节码。此时（JIT）编译器提供的性能优势在此模式下不存在。
    	
    	11.-Xinternalversion
    		显示比该-version选项更详细的JVM版本信息，然后退出。
    	
    	12.-Xloggc:filename
    		输出gc信息到文本文件,例如:
    			-Xloggc:garbage-collection.log
    	
    	13.-Xmaxjitcodesize=size
    		指定JIT编译代码的最大代码缓存大小（以字节为单位）。默认的最大代码缓存大小为240MB；如果使用选项-XX:-tiered compilation禁用分层编译，则默认大小为48MB：
    		例如:-Xmaxjitcodesize=240m
    		
    	14.-Xmixed
    		除了热方法之外，解释器执行所有字节码，热方法被编译为本机代码。
    	
    	15.-Xmnsize
    		设置新生代堆的初始和最大大小.新生代用于新对象。GC在该区域比在其他区域更频繁地进行。如果年轻一代的规模太小，那么将会频繁进行新生代垃圾收集。如果大小太大，则相当于执行了完整的垃圾收集，这可能需要很长时间才能完成。Oracle建议您将新生代的大小保持在整个堆大小的一半到四分之一之间。
    		以下示例显示如何使用各种单位将新生代的初始和最大大小设置为256 MB：
    		-Xmn256m
    		-Xmn262144k
    		-Xmn268435456
    		可以使用-XX:NewSize设置初始大小和-XX:MaxNewSize设置的最大尺寸来代替这个参数
    	
    	16.-Xmssize
    		设置堆的初始大小（以字节为单位）。该值必须是1024的倍数且大于1 MB。
    		以下示例显示如何使用各种单位将分配的内存大小设置为6 MB：
    		-Xms6291456
    		-Xms6144k
    		-Xms6m
    		如果没有设置,则堆大小为新生代与老年代的总和.
    		
    	17.-Xmxsize
    		最大堆内存.该值必须是1024的倍数且大于2 MB.根据系统配置在运行时选择默认值。对于服务器部署，-Xms与-Xmx经常设置为相同的值.
    		请参阅Java SE HotSpot虚拟机垃圾收集调优指南: http://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/index.html。
    	
    	18.-Xnoclassgc
    		禁用类的垃圾收集（GC）。这可以节省一些GC时间，从而缩短应用程序运行期间的中断。
    		-Xnoclassgc在启动时指定时，应用程序中的类对象将在GC期间保持不变，并始终被视为实时。这可能导致更多的内存被永久占用，如果不小心使用，将导致内存不足异常。
    	
    	19.-Xprof
    		分析运行的程序，并将分析数据发送到标准输出。此选项是作为一种实用程序提供的，在程序开发中很有用，不适用于生产系统。
    		
    	17.-Xrs
    		减少JVM对操作系统信号的使用。
    		通过关闭挂钩在关闭时运行用户清理代码（例如关闭数据库连接）来实现Java应用程序的有序关闭，即使JVM突然终止也是如此。
    		JVM监视控制台控制事件，以实现意外终止的关闭挂钩。具体而言，JVM将注册开始关闭的挂机处理，并返回一个控制台控制处理程序TRUE用于CTRL_C_EVENT，CTRL_CLOSE_EVENT，CTRL_LOGOFF_EVENT，和CTRL_SHUTDOWN_EVENT。
    		JVM使用类似的机制来实现转储线程堆栈的功能以进行调试。JVM用于CTRL_BREAK_EVENT执行线程转储。
    		如果JVM作为一个服务运行（例如，作为一个web服务器的servlet引擎），那么它可以接收CTRL_LOGOFF_EVENT，但是不应该启动关闭，因为操作系统实际上不会终止该进程。为了避免这种可能的干扰，可以使用-Xrs选项。使用-Xrs选项时，JVM不会安装控制台控制处理程序，这意味着它不会监视或处理CTRL_C_EVENT、CTRL_CLOSE_EVENT、CTRL_LOGOFF_EVENT或CTRL_SHUTDOWN_EVENT。
    		
    		指定-Xrs有两种结果：
    		Ctrl + Break线程转储不可用。
    		用户代码负责触发关闭挂钩运行，例如，在JVM终止时通过调用System.exit()来运行。	
    	
    	18.-Xshare:mode
    		设置类数据共享（CDS）模式。此选项的mode参数包括以下内容：
    			(1)auto
    				尽可能使用CDS。这是Java HotSpot 32位客户端VM的默认值。
    			(2)on
    				需要使用CDS。如果无法使用类数据共享，则打印错误消息并退出。
    			(3)off
    				不要使用CDS。这是Java HotSpot 32位Server VM，Java HotSpot 64位ClientVM和Java HotSpot 64位Server VM的默认值。
    			(4)dump
    				手动生成CDS存档。按照“设置类路径”中的说明指定应用程序类路径。
    				您应该使用每个新的JDK版本重新生成CDS存档。
    				
    	19.-XshowSettings:category
    		显示设置并继续,此选项category参数包括以下内容:
    			(1)all
    				显示所有类别的设置。默认值。
    			(2)local
    				显示与区域设置相关的设置。
    			(3)properties
    				显示与系统属性相关的设置。
    			(4)vm
    				显示JVM的设置。
    	
    	20.-Xsssize
    		设置线程堆栈大小（以字节为单位）,默认值取决于虚拟内存。此选项相当于-XX:ThreadStackSize。
    		
    	21.-Xverify:mode
    		设置字节码验证器的模式。字节码验证可确保正确形成类文件，并满足“Java虚拟机规范”中第4.10节“ class文件验证”中列出的约束。
    		不要关闭验证，因为这会减少Java提供的保护，并可能由于格式错误的类文件而导致问题。
    		此选项的可能模式参数包括以下内容：
    			(1)remote
    				验证引导类加载器未加载的所有字节码。如果未指定该-Xverify选项，则这是默认行为。
    			(2)all
    				启用所有字节码的验证。
    			(3)none
    				禁用所有字节码的验证。HotSpot不支持-Xverify:none选项。
    
    
###### 三丶运行时选项
    	这些选项控制Java HotSpot VM的运行时行为。
    
    	1.-XX：+ CheckEndorsedAndExtDirs
    	
    	2.-XX：+ DisableAttachMechanism
    		禁用工具附加到JVM的机制的选项。默认情况下，该选项被禁用，这意味着连接机制启用，您可以使用工具，例如jcmd，jstack，jmap，和jinfo。
    	
    	3.-XX：ErrorFile = filename
    		指定发生不可恢复的错误时写入错误数据的路径和文件名。默认情况下，此文件在当前工作目录中创建，并命名为hs_err_pidpid.log，其中pid是导致错误的进程的标识符。以下示例显示如何设置默认日志文件（请注意，进程的标识符指定为%p）：
    		
    		-XX：错误文件= / hs_err_pid％p.log
    		以下示例显示如何将错误日志文件设置为C:/log/java/java_error.log：
    		-XX：错误文件= C：/log/java/java_error.log
    		如果无法在指定目录中创建文件（由于空间不足，权限问题或其他问题），则会在操作系统的临时目录中创建该文件。临时目录由TMP环境变量的值指定; 如果未定义该TEMP环境变量，则使用环境变量的值。
    	
    	4.-XX：+ FailOverToOldVerifier
    	
    	5.-XX：+ FlightRecorder
    	  -XX：-FlightRecorder
    		允许在应用程序运行时使用Java Flight Recorder（JFR）(或者禁用)。这是一个商业功能，与-XX:+UnlockCommercialFeatures选项一起使用如下：
    		java -XX：+ UnlockCommercialFeatures -XX：+ FlightRecorder
    		可通过提供相应的jcmd诊断命令在正在运行的JVM中启用/禁用Java Flight Recorder 
    	
    	6.-XX：FlightRecorderOptions = parameter = value
    		设置控制JFR行为的参数。这是一个与-XX:+UnlockCommercialFeatures选项配合使用的商业功能。仅当启用JFR时（即-XX:+FlightRecorder指定选项），才能使用此选项。
    		以下列表包含所有可用的JFR参数：
    		defaultrecording={true|false}
    		disk={true|false}
    		dumponexit={true|false}
    		dumponexitpath=path
    		globalbuffersize=size
    		loglevel={quiet|error|warning|info|debug|trace}
    		maxage=time
    		maxchunksize=size
    		maxsize=size
    		repository=path
    		samplethreads={true|false}
    		settings=path
    		stackdepth=depth
    		threadbuffersize=size
    	
    	7.-XX:LargePageSizeInBytes=size
    		在Solaris上，设置用于Java堆的最大大小,。默认情况下，大小设置为0，这意味着JVM会自动设置堆大小.
    		示例:-XX:LargePageSizeInBytes=4m
    	
    	8.-XX:MaxDirectMemorySize=size
    		设置NIO直接缓冲区分配的最大总大小,默认情况下，大小设置为0，这意味着JVM会自动分配NIO直接缓冲区分配的大小。
    		示例:-XX:MaxDirectMemorySize=1m
    	
    	9.-XX:NativeMemoryTracking=mode
    		指定用于跟踪JVM本机内存使用情况的模式。mode可选值如下:
    			(1)off
    				不跟踪JVM本机内存使用情况。如果未指定-XX:NativeMemoryTracking选项时，默认不跟踪。
    			(2)summary
    				仅跟踪JVM子系统的内存使用情况，例如Java堆，类，代码和线程。
    			(3)detail
    				除了跟踪JVM子系统的内存使用情况外，还可以跟踪CallSite各个虚拟内存区域及其已提交区域的内存使用情况。
    	
    	10.-XX：ObjectAlignmentInBytes = alignment
    		设置Java对象的内存对齐方式（以字节为单位）。默认情况下，该值设置为8个字节。指定的值应为2的幂，并且必须在8和256（含）的范围内。此选项使使用具有较大Java堆大小的压缩指针成为可能。
    		堆大小限制（以字节为单位）计算如下：
    			4GB * ObjectAlignmentInBytes
    			注意：随着对齐值的增加，对象之间未使用的空间也会增加。因此，你可能没有意识到使用压缩指针的好处。
    
    	11.-XX:OnError=string
    		设置自定义命令或一系列以分号分隔的命令，以便在发生不可恢复的错误时运行。如果字符串包含空格，则必须用引号括起来。
    		以下示例显示了如何使用该-XX:OnError选项运行userdump.exe实用程序以在出现无法恢复的错误（%p指定当前进程）时获取崩溃转储：
    		-XX：OnError =“userdump.exe％p”
    		前面的示例假定userdump.exe在PATH环境变量中指定了该实用程序的路径。
    	
    	12.-XX：OnOutOfMemoryError = string
    		设置自定义命令或一系列以分号分隔的命令，以便在OutOfMemoryError首次引发异常时运行。如果字符串包含空格，则必须用引号括起来。有关命令字符串的示例，请参阅该-XX:OnError选项的说明。
    	
    	13.-XX：+PerfDataSaveToFile
    		如果启用，则在Java应用程序退出时保存jstat（1）二进制数据。此二进制数据保存在名为的文件中hsperfdata_<pid>，其中<pid>是您运行的Java应用程序的进程标识符。使用jstat如下显示包含在该文件中的性能数据：
    			jstat -class file：/// <path> / hsperfdata_ <pid> 
    			jstat -gc file：/// <path> / hsperfdata_ <pid>
    	
    	14.-XX：+ PrintCommandLineFlags
    		允许显示出VM初始化完毕后所有跟最初的默认值不同的参数及它们的值.默认情况下，禁用此选项，并且不打印标志。
    		
    	15.-XX：+ PrintNMTStatistics
    		当启用本机内存跟踪时，允许在JVM出口处打印收集的本机内存跟踪数据（请参阅参考资料-XX:NativeMemoryTracking）。默认情况下，禁用此选项并且不打印本机内存跟踪数据。
    	
    	16.-XX：+ RelaxAccessControlCheck
    		减少验证程序中访问控制检查的数量。默认情况下，此选项被禁用，对于具有最新字节码版本的类，它将被忽略（即，视为已禁用）。您可以为具有旧版字节码的类启用它。
    
    	17.-XX：+ ResourceManagement
    		在应用程序的运行时期间启用资源管理。
    		这是一项商业功能，需要您还指定-XX:+UnlockCommercialFeatures选项，如下所示：
    			java -XX:+UnlockCommercialFeatures -XX:+ResourceManagement
    	
    	18.-XX:ResourceManagementSampleInterval=value (milliseconds)
    		设置控制资源管理测量的采样间隔的参数，以毫秒为单位。
    		仅当启用资源管理（即-XX:+ResourceManagement指定选项）时，才能使用此选项。
    	
    	19.-XX：SharedArchiveFile = path
    		指定类数据共享（CDS）归档文件的路径和名称
    	
    	20.-XX：SharedClassListFile = file_name
    		指定包含要存储在类数据共享（CDS）存档中的类文件名称的文本文件。此文件包含每行一个类文件的全名,用斜杠/替换.
    		例如，指定类java.lang.Object和hello.Main，创建一个包含以下两行的文本文件：
    			java/lang/Object
    			hello/Main
    		在此文本文件中指定的类文件应包含应用程序常用的类。它们可以包括应用程序，扩展或引导类路径中的任何类。
    
    	21.-XX：+ ShowMessageBoxOnError
    		当JVM遇到无法恢复的错误时，允许显示对话框。这可以防止JVM退出并使进程保持活动状态，以便您可以将调试器附加到它以调查错误原因。默认情况下，禁用此选项。
    
    	22.-XX：StartFlightRecording = parameter = value
    		启动Java应用程序的JFR记录。这是一个与-XX:+UnlockCommercialFeatures选项配合使用的商业功能。此选项等同于JFR.start在运行时启动记录的诊断命令。您可以在开始JFR录制时设置以下参数：
    		compress={true|false}
    		defaultrecording={true|false}
    		delay=time
    		dumponexit={true|false}
    		duration=time
    		filename=path
    		name=identifier
    		maxage=time
    		maxsize=size
    		settings=path
    	
    	23.-XX:ThreadStackSize=size
    		设置线程栈大小,此选项相当于-Xss。
    
    	24.-XX：+ TraceClassLoading
    		允许在加载类时跟踪类。默认情况下，禁用此选项并且不跟踪类。
    
    	25.-XX：+ TraceClassLoadingPreorder
    		允许按引用顺序跟踪所有已加载的类。默认情况下，禁用此选项并且不跟踪类。
    	
    	26.-XX：+ TraceClassResolution
    		启用对常量池解析的跟踪。默认情况下，禁用此选项，并且不跟踪常数池解析
    	
    	27.-XX：+ TraceClassUnloading
    		允许在卸载类时跟踪类。默认情况下，禁用此选项并且不跟踪类。
    
    	28.-XX：+ TraceLoaderConstraints
    		允许跟踪加载器约束记录。默认情况下，禁用此选项并且不跟踪加载程序约束记录。
    	
    	29.-XX：+ UnlockCommercialFeatures
    		允许使用商业功能。Oracle Java SE Advanced或Oracle Java SE Suite软件包中包含商业功能，如Java SE产品页面中所定义http://www.oracle.com/technetwork/java/javase/terms/products/index.html
    		默认情况下，此选项被禁用，JVM在没有商业功能的情况下运行。一旦为JVM进程启用了它们，就无法禁用它们用于该进程。
    		如果未提供此选项，则仍可使用相应的jcmd诊断命令在正在运行的JVM中解锁商业功能。
    	
    	30.-XX：+ UseAppCDS
    		启用应用程序类数据共享（AppCDS）。要使用AppCDS，还必须指定选项的值-XX:SharedClassListFile和-XX:SharedArchiveFile两个CDS在转储时间（见选项-Xshare:dump）和应用程序运行时间。
    		这是一项商业功能，需要您同时指定-XX:+UnlockCommercialFeatures选项。这也是一个实验性的特征; 它可能在将来的版本中发生变化.
    
    	31.-XX：-UseBiasedLocking
    		禁用偏向锁的使用。在启用此标志的情况下，某些具有大量非竞争同步的应用程序可能会获得显著的加速效果，而具有某种锁定模式的应用程序可能会出现减速。有关有偏向锁技术的更多信息，请参见Java调优白皮书（http://www.oracle.com/technetwork/Java/Tuning-139912.html#section4.2.5）中的示例
    		默认情况下，启用此选项。
    		
    	32.	-XX：-UseCompressedOops
    		禁用压缩指针的使用。默认情况下，启用此选项，并在Java堆大小小于32 GB时使用压缩指针。启用此选项时，对象引用表示为32位偏移而不是64位指针，这通常会在运行Java堆大小小于32 GB的应用程序时提高性能。此选项仅适用于64位JVM。
    		当Java堆大小大于32GB时，也可以使用压缩指针。请参阅-XX:ObjectAlignmentInBytes选项。
    	
    	33.-XX：+ UseLargePages
    		允许使用大页面内存。默认情况下，禁用此选项并且不使用大页面内存。
    		有关更多信息，请参阅"https://docs.oracle.com/javase/8/docs/technotes/tools/windows/java.html#large_pages
    	
    	34.-XX：+ UseMembar
    		允许在线程状态转换上发布membars。默认情况下，在除ARM服务器之外的所有平台上都禁用此选项。（建议您不要在ARM服务器上禁用此选项。）
    
    	35.-XX：+ UsePerfData
    		启用该perfdata功能。默认情况下启用此选项以允许JVM监视和性能测试。禁用它会禁止创建hsperfdata_userid目录。要禁用该perfdata功能，请指定-XX:-UsePerfData。
    
    	36.-XX：+ AllowUserSignalHandlers
    		允许应用程序安装信号处理程序。默认情况下，禁用此选项，并且不允许应用程序安装信号处理程序。
    
    
###### 四丶JIT编译选项
    	这些选项控制Java HotSpot VM执行的动态即时（JIT）编译。
    
    	1.-XX：+ AggressiveOpts
    		允许使用积极的性能优化功能，这些功能有望在即将发布的版本中成为默认功能。默认情况下，禁用此选项并且不使用实验性能功能。
    	
    	2.-XX：AllocateInstancePrefetchLines = lines
    		设置在实例分配指针之前预取的行数。默认情况下，预取的行数设置为1：
    		-XX：AllocateInstancePrefetchLines = 1
    		只有Java HotSpot Server VM支持此选项。
    	
    	3.-XX：AllocatePrefetchDistance = size
    		设置对象分配的预取距离的大小（以字节为单位）。将从最后分配的对象的地址开始预取将要使用新对象的值写入的内存。每个Java线程都有自己的分配点。
    		负值表示基于平台选择预取距离。正值是预取的字节数。附加字母k或K表示千字节，m或M指示兆字节，g或G指示千兆字节。默认值设置为-1。
    		示例:-XX：AllocatePrefetchDistance = 1024
    		只有Java HotSpot Server VM支持此选项。
    	
    	4.-XX:AllocatePrefetchInstr=instruction
    		将预取指令设置为在分配指针之前预取。只有Java HotSpot Server VM支持此选项。可能的值为0到3.值后面的实际指令取决于平台。默认情况下，预取指令设置为0：
    		-XX：AllocatePrefetchInstr = 0
    		只有Java HotSpot Server VM支持此选项。
    
    	5.-XX：AllocatePrefetchLines = lines
    		使用编译代码中生成的预取指令设置在最后一次对象分配后要加载的高速缓存行数。如果最后分配的对象是实例，则默认值为1;如果是数组，则默认值为3。
    		以下示例显示如何将加载的缓存行数设置为5：
    			-XX：AllocatePrefetchLines = 5
    			只有Java HotSpot Server VM支持此选项。
    	
    	6.-XX：AllocatePrefetchStepSize = size
    		设置顺序预取指令的步长（以字节为单位）附加字母k或K表示千字节，m或M指示兆字节，g或G指示千兆字节。默认情况下，步长设置为16个字节：
    			-XX：AllocatePrefetchStepSize = 16
    			只有Java HotSpot Server VM支持此选项。
    		
    	7.-XX：AllocatePrefetchStyle = style
    		为预取指令设置生成的代码样式。样式参数是一个从0到3的整数。
    		(1)0
    			不要生成预取指令。
    		(2)1
    			每次分配后执行预取指令。这是默认参数。
    		(3)2
    			使用线程局部分配块（TLAB）指针来确定何时执行预取指令。
    		(4)3
    			在SPARC上使用BIS指令进行分配预取。
    		只有Java HotSpot Server VM支持此选项。
    	
    	8.-XX：+ BackgroundCompilation
    		启用后台编译。默认情况下启用此选项。要禁用后台编译，请指定-XX:-BackgroundCompilation（这相当于指定-Xbatch）。
    
    	9.-XX：CICompilerCount = threads
    		设置用于编译的编译器线程数。默认情况下，服务器JVM的线程数设置为2，客户端JVM的线程数设置为1，如果使用分层编译，则会扩展为核心数。以下示例显示如何将线程数设置为2：
    			-XX：CICompilerCount = 2
    	
    	10.-XX：CodeCacheMinimumFreeSpace = size
    		设置编译所需的最小可用空间（以字节为单位）。附加字母k或K表示千字节，m或M指示兆字节，g或G指示千兆字节。当剩余小于最小可用空间时，编译停止。默认情况下，此选项设置为500 KB。以下示例显示如何将最小可用空间设置为1024 MB：
    		-XX：CodeCacheMinimumFreeSpace =1024m
    		
    	11.-XX：CompileCommand = command，method [，option ]
    		指定要对方法执行的命令。例如，要排除编译类的indexOf()方法String，请使用以下命令：
    			-XX:CompileCommand=exclude,java/lang/String.indexOf
    		请注意，指定了完整的类名，包括由斜杠（/）分隔的所有包和子包。为了便于剪切和粘贴操作，还可以使用-XX:+PrintCompilation和-XX:+LogCompilation选项生成的方法名称格式：
    			-XX:CompileCommand=exclude,java.lang.String::indexOf
    		如果在没有签名的情况下指定方法，则该命令将应用于具有指定名称的所有方法。但是，您也可以在类文件格式中指定方法的签名。在这种情况下，您应该将参数括在引号中，否则shell会将分号视为命令end。例如，如果要仅排除编译类的indexOf(String)方法String，请使用以下命令：
    			-XX:CompileCommand="exclude,java/lang/String.indexOf,(Ljava/lang/String;)I"
    		您还可以使用星号（*）作为类和方法名称的通配符。例如，要排除indexOf()编译所有类中的所有方法，请使用以下命令：
    			-XX:CompileCommand=exclude,*.indexOf
    		逗号和句点是空格的别名，使得通过shell传递编译器命令更容易。您可以通过-XX:CompileCommand将参数括在引号中来将参数传递给使用空格作为分隔符：
    			-XX:CompileCommand="exclude java/lang/String indexOf"
    		
    		请注意，在使用-XX:CompileCommand选项解析在命令行上传递的命令之后，JIT编译器会从.hotspot_compiler文件中读取命令。您可以向此文件添加命令，也可以使用该-XX:CompileCommandFile选项指定其他文件。
    		要添加多个命令，请-XX:CompileCommand多次指定该选项，或使用换行符分隔符（\n）分隔每个参数。可以使用以下命令：
    		(1)break
    			在调试JVM时设置断点，以便在编译指定方法的开始时停止。
    		(2)compileonly
    			除了指定的方法之外，从编译中排除所有方法。作为替代方法，您可以使用该-XX:CompileOnly选项，该选项允许指定多种方法。
    		(3)dontinline
    			防止内联指定的方法。
    		(4)exclude
    			从编译中排除指定的方法。
    		(5)help
    			打印该-XX:CompileCommand选项的帮助消息。
    		(6)inline
    			尝试内联指定的方法。
    		(7)log
    			排除-XX:+LogCompilation除指定方法之外的所有方法的编译日志记录（带选项）。默认情况下，对所有已编译的方法执行日志记录。
    		(8)option
    			此命令可用于将JIT编译选项传递给指定的方法以代替最后一个参数（选项）。编译选项在方法名称后面的末尾设置。例如，要启用类方法的BlockLayoutByFrequency选项，请使用以下命令：append()StringBuffer
    			-XX:CompileCommand=option,java/lang/StringBuffer.append,BlockLayoutByFrequency
    			您可以指定多个编译选项，以逗号或空格分隔。
    		(9)print
    			在编译指定方法后打印生成的汇编代码。
    		(10)quite
    			不要打印编译命令。默认情况下，使用 - XX:CompileCommand选项指定的命令将被打印; 例如，如果从编译中排除类的indexOf()方法String，则以下内容将打印到标准输出：
    				CompilerOracle: exclude java/lang/String.indexOf
    			您可以通过-XX:CompileCommand=quiet在其他选项之前指定选项来抑制此操作-XX:CompileCommand。
    
    	12.-XX：CompileCommandFile = filename
    		设置从中读取JIT编译器命令的文件。默认情况下，该.hotspot_compiler文件用于存储JIT编译器执行的命令。
    		命令文件中的每一行代表一个命令，一个类名和一个使用该命令的方法名。例如，此行打印类的toString()方法的汇编代码String：
    			print java / lang / String toString
    		有关为JIT编译器指定要对方法执行的命令的更多信息，请参阅该-XX:CompileCommand选项。
    	
    	13.-XX:CompileOnly=methods
    		设置应限制编译的方法列表（以逗号分隔）。仅编译指定的方法。使用完整的类名（包括包和子包）指定每个方法。例如，为了仅编译length()所述的方法String类和size()所述的方法List类，使用以下：
    			-XX:CompileOnly=java/lang/String.length,java/util/List.size
    		请注意，指定了完整的类名，包括由斜杠（/）分隔的所有包和子包。为了便于剪切和粘贴操作，还可以使用-XX:+PrintCompilation和-XX:+LogCompilation选项生成的方法名称格式：
    			-XX:CompileOnly=java.lang.String::length,java.util.List::size
    		因为不支持通配符，只能指定类或包名称来编译该类或包中的所有方法，并且只指定在任何类中使用此名称编译方法的方法：
    			-XX:CompileOnly=java/lang/String
    			-XX:CompileOnly=java/lang
    			-XX:CompileOnly=.length
    
    	14.	-XX:CompileThreshold=invocations
    		设置编译前解释的方法调用的数量。默认情况下，在服务器JVM中，JIT编译器执行10,000次解释方法调用以收集有效编译的信息。对于客户端JVM，默认设置为1,500次调用。启用分层编译时，将忽略此选项; 看到选项-XX:+TieredCompilation。以下示例显示如何将解释的方法调用数设置为5,000：
    			-XX：CompileThreshold = 5000
    		您可以通过指定-Xcomp选项在编译之前完全禁用Java方法的解释。
    	
    	15.-XX：+ DoEscapeAnalysis
    		允许使用逃逸分析。默认情况下启用此选项。要禁用逃逸分析，请指定-XX:-DoEscapeAnalysis。只有Java HotSpot Server VM支持此选项。
            用途:
                1.对象内存由堆分配转为栈分配
                2.锁粗化
                    例如for循环中使用synchronized,会自动粗化到for循环外部
                3.锁消除
                    经逃逸分析以后发现对象不会再外部线程使用,就会消除synchronized关键字,例如方法中使用StringBuffer是自带synchronized的,如果没有返回sb,则会清楚synchronized关键字,减少同步开销.
                
    	16.-XX：InitialCodeCacheSize = size
    		设置初始代码高速缓存大小（以字节为单位）。附加字母k或K表示千字节，m或M指示兆字节，g或G指示千兆字节。默认值设置为500 KB。初始代码高速缓存大小应不小于系统的最小内存页大小。以下示例显示如何将初始代码高速缓存大小设置为32 KB：
    			-XX：InitialCodeCacheSize = 32K
    
    	17.-XX:+Inline
    		启用方法内联。默认情况下启用此选项以提高性能。要禁用方法内联，请指定-XX:-Inline。
    	
    	18.-XX：InlineSmallCode = size
    		设置应内联的已编译方法的最大代码大小（以字节为单位）。附加字母k或K表示千字节，m或M指示兆字节，g或G指示千兆字节。只有内联小于指定大小的编译方法才会被内联。默认情况下，最大代码大小设置为1000字节：
    			-XX：InlineSmallCode = 1000
    
    	19.-XX：+ LogCompilation
    		允许将编译活动记录到hotspot.log当前工作目录中指定的文件。您可以使用该-XX:LogFile选项指定其他日志文件路径和名称。
    		默认情况下，禁用此选项并且不记录编译活动。该-XX:+LogCompilation选项必须与-XX:UnlockDiagnosticVMOptions解锁诊断JVM选项的选项一起使用。
    		每次使用该-XX:+PrintCompilation选项编译方法时，都可以启用详细诊断输出，并在控制台上打印一条消息。
    		
    	20.-XX：MaxInlineSize = size
    		设置要内联的方法的最大字节码大小（以字节为单位）。附加字母k或K表示千字节，m或M指示兆字节，g或G指示千兆字节。默认情况下，最大字节码大小设置为35个字节：
    			-XX：MaxInlineSize = 35
    	
    	21.-XX:MaxNodeLimit=nodes
    		设置单个方法编译期间要使用的最大节点数。默认情况下，最大节点数设置为65,000：
    			-XX：MaxNodeLimit = 65000
    	
    	22.-XX：MaxTrivialSize = size
    		设置要内联的简单方法的最大字节码大小（以字节为单位）。附加字母k或K表示千字节，m或M指示兆字节，g或G指示千兆字节。默认情况下，一个简单方法的最大字节码大小设置为6个字节：
    			-XX：MaxTrivialSize = 6
    
    	23.-XX：+ OptimizeStringConcat
    		启用String串联操作的优化。默认情况下启用此选项。要禁用String串联操作的优化，请指定-XX:-OptimizeStringConcat。只有Java HotSpot Server VM支持此选项。
    	
    	24.-XX：+ PrintAssembly
    		通过使用外部disassembler.so库，可以为字节编码和本机方法打印汇编代码。这使您可以查看生成的代码，这可以帮助您诊断性能问题。
    		默认情况下，禁用此选项并且不打印汇编代码。该-XX:+PrintAssembly选项必须与-XX:UnlockDiagnosticVMOptions解锁诊断JVM选项的选项一起使用。	
    		
    	25.-XX：+ PrintCompilation
    		每次编译方法时，通过向控制台打印消息，从JVM启用详细诊断输出。这使您可以查看实际编译的方法。默认情况下，禁用此选项并且不打印诊断输出。
    		您还可以使用该-XX:+LogCompilation选项将编译活动记录到文件中。
    	
    	26.-XX：+ PrintInlining
    		允许打印内联决策。这使您可以查看哪些方法被内联。
    		默认情况下，禁用此选项并且不打印内联信息。该-XX:+PrintInlining选项必须与-XX:+UnlockDiagnosticVMOptions解锁诊断JVM选项的选项一起使用。
    
    	27.-XX：ReservedCodeCacheSize = size
    		设置JIT编译代码的最大代码缓存大小（以字节为单位）。附加字母k或K表示千字节，m或M指示兆字节，g或G指示千兆字节。默认的最大代码缓存大小为240 MB; 如果使用该选项禁用分层编译-XX:-TieredCompilation，则默认大小为48 MB。此选项的限制为2 GB; 否则，会产生错误。最大代码缓存大小不应小于初始代码缓存大小; 看到选项-XX:InitialCodeCacheSize。此选项相当于-Xmaxjitcodesize。
    	
    	28.-XX：RTMAbortRatio = abort_ratio
    		RTM中止比率指定为所有已执行RTM事务的百分比（％）。如果许多中止事务变得大于此比率，则编译后的代码将被去优化。-XX:+UseRTMDeopt启用该选项时将使用此比率。此选项的默认值为50.这意味着如果50％的所有事务都被中止，则编译后的代码将被去优化。
    
    	29.-XX：RTMRetryCount = number_of_retries
    		RTM锁定代码将在中止或忙碌时重试此选项指定的次数，然后再回退到正常锁定机制。此选项的默认值为5. -XX:UseRTMLocking必须启用该选项。
    	
    	30.-XX：-TieredCompilation
    		禁用分层编译。默认情况下，启用此选项。只有Java HotSpot Server VM支持此选项。
    	
    	31.-XX：+ UseAES
    		为Intel，AMD和SPARC硬件启用基于硬件的AES内在函数。Intel Westmere（2010及更新版本），AMD Bulldozer（2011及更新版本）以及SPARC（T4及更新版本）均为支持的硬件。UseAES与UseAESIntrinsics一起使用。
    		
    	32.-XX：+ UseAESIntrinsics
    		默认情况下启用UseAES和UseAESIntrinsics标志，仅支持Java HotSpot Server VM 32位和64位。要禁用基于硬件的AES内在函数，请指定-XX:-UseAES -XX:-UseAESIntrinsics。例如，要启用硬件AES，请使用以下标志：
    			-XX：+ UseAES -XX：+ UseAESIntrinsics
    		支持使用32位和64位的UseAES和UseAESIntrinsics标志-server选项来选择Java HotSpot Server VM。客户端VM不支持这些标志。	
    	
    	33.-XX：+ UseCodeCacheFlushing
    		在关闭编译器之前启用刷新代码缓存。默认情况下启用此选项。要在关闭编译器之前禁用刷新代码缓存，请指定-XX:-UseCodeCacheFlushing。
    
    	34.-XX：+ UseCondCardMark
    		允许在更新卡表之前检查该卡是否已经被标记。默认情况下，此选项是禁用的，并且只应在具有多个套接字的计算机上使用，这将提高严重依赖并发操作的Java应用程序的性能。只有Java HotSpot Server VM支持此选项。 HotSpot Server VM支持此选项。
    
    	35.-XX：+ UseRTMDeopt
    		根据中止率自动调谐RTM锁定。该比率由-XX:RTMAbortRatio选项指定。如果中止事务的数量超过中止率，则包含锁定的方法将被取消优化并重新编译，并将所有锁定为正常锁定。默认情况下禁用此选项。-XX:+UseRTMLocking必须启用该选项。
    
    	36.-XX：+ UseRTMLocking
    		为所有膨胀锁生成受限制的事务性内存（RTM）锁定代码，使用正常的锁定机制作为回退处理程序。默认情况下禁用此选项。与RTM相关的选项仅适用于支持事务同步扩展（TSX）的x86 CPU上的Java HotSpot Server VM。
    		RTM是英特尔TSX的一部分，它是x86指令集扩展，有助于创建多线程应用程序。RTM引入了新的指示XBEGIN，XABORT，XEND，和XTEST。该XBEGIN和XEND说明附上一组指令作为一个事务中运行。如果在运行事务时未发现冲突，则内存和寄存器修改将在XEND指令处一起提交。该XABORT指令可用于显式中止事务以及XEND检查是否在事务中运行一组指令的指令。
    		当另一个线程尝试访问同一事务时，对事务的锁定会膨胀，从而阻止最初未请求访问该事务的线程。RTM要求在事务中止或失败时指定后备操作集。RTM锁是一种委托给TSX系统的锁。
    		RTM提高了在关键区域中具有低冲突的高竞争锁的性能（这是不能同时由多个线程访问的代码）。RTM还提高了粗粒度锁定的性能，这在多线程应用程序中通常表现不佳。（粗粒度锁定是长时间保持锁定以最小化获取和释放锁定的开销的策略，而细粒度锁定是通过仅在必要时锁定并尽快解锁来尝试实现最大并行性的策略。此外，对于不同线程使用的轻度争用锁，RTM可以减少错误的缓存行共享，也称为缓存行乒乓。当来自不同处理器的多个线程访问不同的资源时会发生 但资源共享相同的缓存行。结果，处理器重复地使其他处理器的高速缓存行无效，这迫使它们从主存储器而不是它们的高速缓存读取
    		
    	37.-XX：+ UseSHA
    		为SPARC硬件启用SHA加密散列函数的基于硬件的内在函数。UseSHA与结合使用UseSHA1Intrinsics，UseSHA256Intrinsics和UseSHA512Intrinsics选项。
    		在UseSHA和UseSHA*Intrinsics标志默认情况下启用，并且仅适用于SPARC T4和新的Java HotSpot的服务器虚拟机的64位支持。
    		此功能仅在使用sun.security.provider.SunSHA操作的提供程序时适用。
    		要禁用所有基于硬件的SHA内在函数，请指定-XX:-UseSHA。要仅禁用特定的SHA内在函数，请使用相应的相应选项。例如：-XX:-UseSHA256Intrinsics。
    
    	38.-XX：+ UseSHA1Intrinsics
    		为SHA-1加密哈希函数启用内在函数。
    
    	39.-XX：+ UseSHA256Intrinsics
    		为SHA-224和SHA-256加密哈希函数启用内在函数。
    
    	40.-XX：+ UseSHA512Intrinsics
    		为SHA-384和SHA-512加密散列函数启用内在函数。
    
    	41.-XX：+ UseSuperWord
    		允许将标量操作转换为超级字操作。默认情况下启用此选项。要禁用将标量操作转换为超级字操作，请指定-XX:-UseSuperWord。只有Java HotSpot Server VM支持此选项。	
    
    
###### 五丶服务性选项
    	这些选项提供了收集系统信息和执行大量调试的功能。
    
    	1.-XX：+ HeapDumpOnOutOfMemoryError
    		在java.lang.OutOfMemoryError抛出异常时，通过使用堆分析器（HPROF）将Java堆转储到当前目录中的文件。您可以使用该-XX:HeapDumpPath选项显式设置堆转储文件路径和名称。默认情况下，禁用此选项，并在OutOfMemoryError抛出异常时不转储堆。
    
    	2.-XX：HeapDumpPath = path
    		设置-XX:+HeapDumpOnOutOfMemoryError选项设置时，设置用于写入堆分析器（HPROF）提供的堆转储的路径和文件名。默认情况下，该文件在当前工作目录中创建，并且名为java_pidpid.hprof，其中pid是导致错误的进程的标识符。以下示例显示如何显式设置默认文件（%p表示当前进程标识符）：
    			-XX：HeapDumpPath = / java_pid％p.hprof
    		以下示例显示如何将堆转储文件设置为C:/log/java/java_heapdump.log：
    			-XX：HeapDumpPath = C：/log/java/java_heapdump.log
    	
    	3.-XX：LogFile = path
    		设置写入日志数据的路径和文件名。默认情况下，该文件在当前工作目录中创建，并以其命名hotspot.log。
    		以下示例显示如何将日志文件设置为C:/log/java/hotspot.log：
    			-XX：日志文件= C：/log/java/hotspot.log
    	
    	4.-XX：+ PrintClassHistogram
    		允许在Control+Break事件后打印类实例直方图。默认情况下，禁用此选项。
    		设置此选项等同于运行jmap -histo命令或jcmd pid GC.class_histogram命令，其中pid是当前Java进程标识符。
    
    	5.-XX：+ PrintConcurrentLocks
    		java.util.concurrent在Control+Break事件发生后启用锁定打印。默认情况下，禁用此选项。
    		设置此选项等同于运行jstack -l命令或jcmd pid Thread.print -l命令，其中pid是当前Java进程标识符。
    	
    	6.-XX：+ UnlockDiagnosticVMOptions
    		解锁用于诊断JVM的选项。默认情况下，此选项已禁用，诊断选项不可用。
    
    
###### 六丶垃圾收集选项		
    	这些选项控制Java HotSpot VM如何执行垃圾收集（GC）。
    
    	1.-XX：+ AggressiveHeap
    		启用Java堆优化。根据计算机的配置（RAM和CPU），这会将各种参数设置为具有密集内存分配的长时间运行作业的最佳选择。默认情况下，禁用该选项并且不优化堆。
    	
    	2.-XX：+ AlwaysPreTouch
    		支持在JVM初始化期间访问Java堆上的每个页面。在进入main（）方法之前，这将获取内存中的所有页面。该选项可用于测试，以模拟所有虚拟内存映射到物理内存的长期运行的系统。默认情况下，该选项被禁用，并且所有页面都作为JVM堆空间填充提交。
    		
    	3.-XX:+CMSClassUnloadingEnabled
    		在使用并发标记-清除（CMS）垃圾回收器时启用类卸载。默认情况下启用此选项。要禁用CMS垃圾回收器的类卸载，请指定-XX:-CMSClassUnloadingEnabled。
    	
    	4.-XX:CMSExpAvgFactor=percent
    		设置在计算并发收集统计数据的指数平均值时用于对当前样本进行加权的时间百分比（0到100）。默认情况下，指数平均因子设置为25%。
    		下面的示例显示如何将因子设置为15%：
    			-XX:CMSExpAvgFactor=15
    	
    	5.-XX:CMSInitiatingOccupancyFraction=percent
    		设置启动CMS收集周期的旧代占用率（0到100）的百分比。默认值设置为-1。任何负值（包括默认值）都意味着-XX:CMSTriggerRatio用于定义初始占用率的值。(即老年代使用百分之多少时开始执行CMS收集)
    		以下示例显示如何将占用率设置为20％：
    			-XX：CMSInitiatingOccupancyFraction = 20
    	
    	6.-XX：+ CMSScavengeBeforeRemark
    		开启或关闭在CMS重新标记阶段之前的清除（YGC）尝试。默认情况下，禁用此选项。
    	
    	7.-XX:CMSTriggerRatio=percent
    		设置在-XX:MinHeapFreeRatioCMS收集周期开始之前分配的值所指定的值的百分比（0到100）。默认值设置为80％。
    		以下示例显示如何将占用率设置为75％：
    			-XX：CMSTriggerRatio = 75
    		
    	8.-XX：ConcGCThreads = threads
    		设置用于并发GC的线程数。默认值取决于JVM可用的CPU数。
    		例如，要将并发GC的线程数设置为2，请指定以下选项：
    			-XX：ConcGCThreads = 2
    	
    	9.-XX：+ DisableExplicitGC
    		启用禁用对System.gc()调用的处理的选项。默认情况下禁用此选项，这意味着将处理对System.gc（）的调用。如果禁用对System.gc（）调用的处理，JVM仍然在必要时执行GC.
    	
    	10.-XX：+ ExplicitGCInvokesConcurrent
    		允许使用System.gc()请求调用并发GC 。默认情况下禁用此选项，并且只能与该-XX:+UseConcMarkSweepGC选项一起启用。
    		
    	11.-XX：+ ExplicitGCInvokesConcurrentAndUnloadsClasses
    		通过System.gc()在并发GC周期期间使用请求和卸载类，可以调用并发GC。默认情况下禁用此选项，并且只能与该-XX:+UseConcMarkSweepGC选项一起启用。	
    	
    	12.-XX：G1HeapRegionSize = size
    		设置使用垃圾优先（G1）收集器时Java堆所细分的区域的大小。该值可以介于1 MB和32 MB之间。
    		示例:-XX：G1HeapRegionSize=16M
    	
    	13.-XX：+ G1PrintHeapRegions
    		允许打印有关哪些区域已分配以及哪些区域由G1收集器回收的信息。默认情况下，禁用此选项。
    
    	14.-XX:G1ReservePercent=percent
    		使用g1收集器时，设置保留java堆大小，防止晋升失败。范围是0到50.默认设置是10%。
    	
    	15.-XX：InitialHeapSize = size
    		初始化堆大小。
    		如果将此选项设置为0，则初始大小将设置为为旧代和年轻代分配的大小的总和。可以使用-XX:NewSize选项设置年轻代的堆大小。
    
    	16.-XX：InitialSurvivorRatio = ratio
    		设置幸存区的初始比例,如果使用追求吞吐量的垃圾收集器,比如使用-XX:+UseParallelGC或-XX:+UseParallelOldGC指令,那么应用会使用当前初始比例启动,并且在运行期间,根据应用的使用情况,自动调整幸存区的比例。如果自适应功能通过-XX:-UseAdaptiveSizePolicy关闭,那么应该使用-XX:SurvivorRatio选项指定整个应用运行期间的幸存区大小。
    		下面的公式可以根据年轻代的大小(Y)计算幸存区的初始大小(S),初始幸存区的比例(R)。
    		S=Y/(R+2)
    		公式中的2代表两个幸存区域。幸存区初始化比例的值越大,幸存区的空间大小越小。
    		幸存区比例的初始化默认值是8。如果年轻代的大小是2M,则初始化的幸存区大小是0.2M。
    	
    	17.-XX:InitiatingHeapOccupancyPercent=percent
    		设置堆占用多少百分比的时候,会启动并行GC循环。这个值用来设置当占用整个堆多少百分比的时候,垃圾收集器会触发一次并行GC循环。默认值是45%。0意味着一个不停歇的GC循环。
    	
    	18.-XX:MaxGCPauseMillis=time
    		设置最大GC暂停时间的目标（以毫秒为单位）。这是一个软目标，JVM将尽最大努力实现它。默认情况下，没有最大暂停时间值。
    
    	19.-XX：MaxHeapSize = size
    		设置堆分配的最大值,单位字节。这个值必须要大于2M。默认值是在运行时根据系统配置来定。作为生产服务的部署,通常-XX:InitialHeapSize和-XX:MaxHeapSize选项指定为相同的值。具体可以参考Java虚拟机垃圾回收优化指南。
    		该-XX:MaxHeapSize选项相当于-Xmx。
    
    	20.	-XX:MaxHeapFreeRatio=percent
    		设置可以为类元数据分配的最大本机内存量。默认情况下，大小不受限制。应用程序的元数据量取决于应用程序本身，其他正在运行的应用程序以及系统上可用的内存量。
    
    	21.-XX：MaxNewSize = size
    		设置新生代的最大大小
    	
    	22.-XX：MaxTenuringThreshold = threshold
    		设置自适应GC调整大小时使用的最大分级阈值。最大的值是15。并行（吞吐量）收集器的默认值为15，CMS收集器的默认值为6。
    		示例:-XX:MaxTenuringThreshold=10
    	
    	23.-XX:MetaspaceSize=size
    		设置分配的类元数据空间的大小，该空间将在第一次超出时触发垃圾回收。根据使用的元数据量，增加或减少垃圾收集的阈值。默认大小取决于平台。
    
    	24.-XX:MinHeapFreeRatio=percent
    		设置GC事件后允许的最小空闲堆空间百分比（0到100）。如果可用堆空间低于此值，则将扩展堆。默认情况下，此值设置为40％。
    		以下示例显示如何将最小可用堆比率设置为25％：
    			-XX：MinHeapFreeRatio = 25
    	
    	25.-XX：NewRatio = ratio
    		设置新生代老年代比率,默认2:1
    	
    	26.-XX：NewSize = size
    		设置新生代的初始大小.-XX:NewSize选项相当于-Xmn。
    	
    	27.-XX：ParallelGCThreads = threads
    		设置用于年轻和老年代的并行垃圾收集的线程数。默认值取决于JVM可用的CPU数。
    		例如，要将并行GC的线程数设置为2，请指定以下选项：
    			-XX：ParallelGCThreads = 2
    	
    	28.-XX：+ ParallelRefProcEnabled
    		启用并行引用处理。默认情况下，禁用此选项。
    	
    	29.-XX：+ PrintAdaptiveSizePolicy
    		启用有关自适应生成调整大小的信息的打印。默认情况下，禁用此选项。
    	
    	30.-XX：+ PrintGC
    		允许打印GC消息。默认情况下，禁用此选项。
    		
    	31-XX：+ PrintGCApplicationConcurrentTime
    		允许打印自上次暂停后经过的时间（例如，GC暂停）。默认情况下，禁用此选项。
    	
    	32-XX：+ PrintGCApplicationStoppedTime
    		允许打印暂停（例如，GC暂停）持续多长时间。默认情况下，禁用此选项。
    		
    	33.-XX：+ PrintGCDateStamps
    		允许打印GC日期戳。默认情况下，禁用此选项。
    		
    	34.-XX：+ PrintGCDetails
    		允许打印GC详细信息.默认关闭.
    	
    	35.-XX：+ PrintGCTaskTimeStamps
    		允许打印GC工作线程任务时间戳.默认关闭.
    	
    	36.-XX：+ PrintGCTimeStamps
    		允许打印GC时间戳.
    		
    	37.-XX：+ PrintStringDeduplicationStatistics
    		打印详细的重复数据。默认情况下，禁用此选项。查看-XX:+UseStringDeduplication选项。
    	
        38.-XX:+PrintTenuringDistribution
    		允许打印终身年龄信息。以下是输出的示例：
    			Desired survivor size 48286924 bytes, new threshold 10 (max 10)
    			- age 1: 28992024 bytes, 28992024 total
    			- age 2: 1366864 bytes, 30358888 total
    			- age 3: 1425912 bytes, 31784800 total
    			...
    		1岁对象是最年轻的幸存者（它们是在之前的清除之后创建的，在最近的清除中幸存下来，并从伊甸园迁移到幸存者空间）。2岁的物体在两次清除中幸存下来（在第二次清除期间，它们被从一个幸存者空间复制到下一个幸存者空间）。等等。
    		在前面的示例中，28 992 024个字节在一次清除中幸存，并从eden复制到幸存者空间，1 366 864个字节由2岁对象占用，等等。每行中的第三个值是年龄n的对象的累积大小或减。
    		默认情况下，禁用此选项。	
    		
    	39.-XX：+ ScavengeBeforeFullGC
    		在每个完整GC之前启用新生代的GC。默认情况下启用此选项。Oracle建议您不要禁用它，因为在完整GC之前清除新生代可以减少从老年代到新生代空间可到达的对象数。要在每个完整GC之前禁用年轻代的GC，请指定-XX:-ScavengeBeforeFullGC。	
    	
    	40.-XX:SoftRefLRUPolicyMSPerMB=time
    		设置软件可访问对象在上次引用后在堆上保持活动状态的时间（以毫秒为单位）。默认值是堆中每个可用兆字节的生存期的一秒。该-XX:SoftRefLRUPolicyMSPerMB选项接受整数值，表示每兆字节当前堆大小（对于Java HotSpot客户端VM）的毫秒数或最大可能堆大小（对于Java HotSpot Server VM）。这种差异意味着客户端VM倾向于刷新软引用而不是增加堆，而服务器VM倾向于增加堆而不是刷新软引用。在后一种情况下，-Xmx选项的值对软引用的垃圾收集速度有显着影响。
    		以下示例显示如何将值设置为2.5秒：
    			-XX：SoftRefLRUPolicyMSPerMB = 2500
    
    	41.-XX：StringDeduplicationAgeThreshold = threshold
    		String达到指定年龄的对象被视为重复数据删除的候选对象。对象的年龄是对垃圾收集存活多少次的度量。这有时被称为终身; 看到-XX:+PrintTenuringDistribution选项。请注意，String在达到此年龄之前提升到旧堆区域的对象始终被视为重复数据删除的候选对象。此选项的默认值为3。请参阅-XX:+UseStringDeduplication选项。
    	
    	42.-XX:SurvivorRatio=ratio
    		eden / survivor空间比率
    	
    	43.-XX:TargetSurvivorRatio=percent
    		一个计算期望存活大小Desired survivor size的参数.
    	
    	44.-XX：TLABSize = size
    		设置线程局部分配缓冲区（TLAB）的初始大小（以字节为单位）。附加字母k或K表示千字节，m或M指示兆字节，g或G指示千兆字节。如果此选项设置为0，则JVM会自动选择初始大小。
    
    	45.-XX：+ UseCMSInitiatingOccupancyOnly
    		允许使用占用值作为启动CMS收集器的唯一标准。默认情况下，此选项已禁用，可能会使用其他条件。
    
    	46.-XX：+ UseConcMarkSweepGC
    		允许为老年代使用CMS垃圾收集器。Oracle建议您在spam（-XX:+UseParallelGC）垃圾收集器无法满足应用程序延迟要求时使用CMS垃圾收集器。G1垃圾收集器（-XX:+UseG1GC）是另一种选择。
    		默认情况下，禁用此选项，并根据计算机的配置和JVM的类型自动选择收集器。启用此选项后，将-XX:+UseParNewGC自动设置该选项，您不应禁用该选项，因为JDK 8中已弃用以下选项组合：-XX:+UseConcMarkSweepGC -XX:-UseParNewGC。
    
    	47.-XX：+ UseG1GC
    		允许使用垃圾优先（G1）垃圾收集器。它是一个服务器式垃圾收集器，针对具有大量RAM的多处理器机器。它以高概率满足GC暂停时间目标，同时保持良好的吞吐量。G1收集器推荐用于需要大堆（大小约为6 GB或更大）且GC延迟要求有限的应用（稳定且可预测的暂停时间低于0.5秒）。
    		默认情况下，禁用此选项，并根据计算机的配置和JVM的类型自动选择收集器。
    	
    	48.-XX：+ UseGCOverheadLimit
    		允许使用策略来限制在OutOfMemoryError引发异常之前JVM在GC上花费的时间比例。默认情况下，此选项处于启用状态，OutOfMemoryError如果将超过98％的总时间花在垃圾回收上并且回收的堆少于2％，则并行GC将抛出该选项。当堆很小时，此功能可用于防止应用程序长时间运行，几乎没有进展。要禁用此选项，请指定-XX:-UseGCOverheadLimit。
    
    	49.-XX：+ UseNUMA
    		通过增加应用程序对低延迟内存的使用，在具有非均匀内存架构（NUMA）的计算机上实现应用程序的性能优化。默认情况下，此选项被禁用，并且不会对NUMA进行优化。该选项仅在使用并行垃圾收集器时可用（-XX:+UseParallelGC）。
    
    	50.-XX：+ UseParallelGC
    		允许使用并行清除垃圾收集器（也称为吞吐量收集器），通过利用多个处理器来提高应用程序的性能。
    		默认情况下，禁用此选项，并根据计算机的配置和JVM的类型自动选择收集器。如果已启用，则会-XX:+UseParallelOldGC自动启用该选项，除非您明确禁用它。
    	
    	51.-XX：+ UseParallelOldGC
    		允许将并行垃圾收集器用于完整的GC。默认情况下，禁用此选项。启用它会自动启用该-XX:+UseParallelGC选项。
    	
    	52.-XX：+ UseParNewGC
    		允许在新生代中使用并行线程进行收集。默认情况下，禁用此选项。设置-XX:+UseConcMarkSweepGC选项时会自动启用它。使用-XX:+UseParNewGC不带选项-XX:+UseConcMarkSweepGC的选择是在JDK 8弃用。
    		
    	53.-XX：+ UseSerialGC
    		允许使用串行垃圾收集器。对于不需要垃圾收集的任何特殊功能的小型和简单应用程序，这通常是最佳选择。默认情况下，禁用此选项，并根据计算机的配置和JVM的类型自动选择收集器。	
    	
    	54.-XX：+ UseStringDeduplication
    		启用字符串重复数据删除。默认情况下，禁用此选项。要使用此选项，必须启用垃圾优先（G1）垃圾收集器。请参阅-XX:+UseG1GC选项。
    		字符串重复数据删除String通过利用许多String对象相同的事实来减少Java堆上对象的内存占用。String相同的String对象可以指向并共享相同的字符数组，而不是每个对象指向其自己的字符数组。
    	
    	55.-XX：+ UseTLAB
    		允许在年轻代空间中使用线程局部分配块（TLAB）。默认情况下启用此选项。要禁用TLAB，请指定-XX:-UseTLAB。
    
        56.-XX：+ UseAdaptiveSizePolicy
           允许使用自适应生成大小。默认情况下启用此选项。要禁用自适应生成大小调整，请明确指定-XX:-UseAdaptiveSizePolicy和设置内存分配池的大小（请参阅-XX:SurvivorRatio选项）。
        
###### 七丶已弃用和已删除的选项
    	这些选项包含在之前的版本中，但后来被认为是不必要的。
    	
    	-Xincgc
    		启用增量垃圾收集。此选项在JDK 8中已弃用，无需替换。
    	-Xrun libname
    		加载指定的调试/分析库。此选项已被该选项取代-agentlib。
    	-XX：CMSIncrementalDutyCycle = 百分比
    		设置允许并发收集器运行的次要集合之间的时间百分比（0到100）。在弃用选项后，此选项在JDK 8中已弃用，没有替换-XX:+CMSIncrementalMode。
    	-XX：CMSIncrementalDutyCycleMin = 百分比
    		设置次要集合之间的时间百分比（0到100），它-XX:+CMSIncrementalPacing是启用时占空比的下限。在弃用选项后，此选项在JDK 8中已弃用，没有替换-XX:+CMSIncrementalMode。
    	-XX：+ CMSIncrementalMode
    		启用CMS收集器的增量模式。此选项在JDK 8中已弃用，没有替换，以及其他选项CMSIncremental。
    	-XX：CMSIncrementalOffset = 百分比
    		设置增量模式占空比在次要集合之间的时间段内向右移动的时间百分比（0到100）。在弃用选项后，此选项在JDK 8中已弃用，没有替换-XX:+CMSIncrementalMode。
    	-XX：+ CMSIncrementalPacing
    		根据JVM运行时收集的统计信息，启用增量模式占空比的自动调整。在弃用选项后，此选项在JDK 8中已弃用，没有替换-XX:+CMSIncrementalMode。
    	-XX：CMSIncrementalSafetyFactor = 百分比
    		设置计算占空比时用于添加保守性的时间百分比（0到100）。在弃用选项后，此选项在JDK 8中已弃用，没有替换-XX:+CMSIncrementalMode。
    	-XX：CMSInitiatingPermOccupancyFraction = 百分比
    		设置启动GC的永久生成占用率（0到100）的百分比。此选项在JDK 8中已弃用，无需替换。
    	-XX：MaxPermSize = size
    		设置最大永久生成空间大小（以字节为单位）。此选项在JDK 8中已弃用，并由该-XX:MaxMetaspaceSize选项取代。
    	-XX：PermSize = size
    		设置分配给永久生成的空间（以字节为单位），如果超出则会触发垃圾回收。此选项在JDK 8中已弃用，并被该-XX:MetaspaceSize选项取代。
    	-XX：+ UseSplitVerifier
    		允许拆分验证过程。默认情况下，此选项在先前版本中已启用，验证分为两个阶段：类型引用（由编译器执行）和类型检查（由JVM运行时执行）。此选项在JDK 8中已弃用，现在默认情况下会对验证进行拆分，而无法将其禁用。
    	-XX：+ UseStringCache
    		启用常用分配字符串的缓存。此选项已从JDK 8中删除，无需替换。
    
###### 八丶性能调优示例
    
    	示例1 - 调整更高的吞吐量
    		java -d64 -server -XX：+ AggressiveOpts -XX：+ UseLargePages -Xmn10g -Xms26g -Xmx26g

    	示例2 - 调整较低的响应时间
    		java -d64 -XX：+ UseG1GC -Xms26g Xmx26g -XX：MaxGCPauseMillis = 500 -XX：+ PrintGCTimeStamp

###### 九丶调优参考
        
           1丶常用参数说明
                -Xms    最小堆内存
                -Xmx    最大堆内存
                -XX:NewRatio=2  新生代老年代内存大小比例
                -XX:SurvivorRatio=8 sur区与整个年轻代的比例 Eden:from:to 8:1:1
                -XX:+UseAdaptiveSizePolicy 开启虚拟机内存比例自动调优(针对新生代中Eden:from:to调整)
                -XX:MaxMetaspaceSize=256M方法区最大值
           
           2丶日志诊断信息,有助于线上问题定位
                -XX:+PrintGCDetails
                -XX:+PrintGCDateStamps
                -Xloggc:./gc.log
                -XX:+PrintHeapAtGC
                -XX:+PrintTenuringDistribution
                -XX:+HeapDumpOnOutOfMemoryError
                -XX:HeapDumpPath=./dump.hprof
              
           3丶虚拟机GC选择
                1.  Serial garbage collector
                    启用参数
                        -XX:+UseSerialGC
                    应用场景
                        1、对性能要求不要高的简单客户端应用
                        2、堆内存设置不大（<200MB）
                        3、物理机器是单核CPU
                    不推荐，无法利用现代计算机的多核优势
                    
                2.  Parallel scavenge garbage collector
                    启用参数
                        -XX:+UseParallelGC
                        -XX:+UseParallelOldGC
                    应用场景
                        需要长期运行且对吞吐量有一定要求的后台应用服务器，
                        1、系统配置较高，通常情况下至少四核（以目前的硬件水平为准）。
                        2、对吞吐量要求较高，或需要达到一定的量。
                        3、应用程序运行时间较长。
                        4、应用程序使用的堆内存大小>1G
                    推荐
                    1. 综合性能良好，用于普通的应用服务配置。
                    2. 在测试过程中GC综合时间优于CMS GC和G1 GC。
                    3. 为server模式下的默认GC选择。
                    
                3.  CMS garbage collector
                    启用参数
                        -XX:+UseConcMarkSweepGC
                        -XX:+UseParNewGC
                        -XX:+CMSClassUnloadingEnabled 
                    应用场景
                        年轻代Parallel GC, 老年代CMS GC
                    不推荐
                        1. 不推荐，很多调优参数在JDK8已经不支持
                        2. JDK9已经移除对CMS GC的支持
                
                4.  G1 garbage collector
                    启用参数
                        -XX:+UseG1GC
                    应用场景
                        需要长期运行且对吞吐量有一定要求的后台应用服务器，
                        1、系统配置较高，通常情况下至少四核（以目前的硬件水平为准）。
                        2、对吞吐量要求较高，或需要达到一定的量。
                        3、应用程序运行时间较长。
                        4、应用程序使用的堆内存大小>4G
                    推荐
                        1. 在大堆（>4G）的配置情况下，推荐使用G1 GC。
                        2. 在测试过程中，对于大堆，GC时间优于CMS GC。
                        3. 在JDK9中为server模式下的默认GC选择。
        
        windows下jdk8 jvm参数官网链接https://docs.oracle.com/javase/8/docs/technotes/tools/windows/java.html
    
                  
##### 对象组成
    
    对象头（Header）
        Mark world
            用于存储对象自身的运行时数据，如哈希码、GC分代年龄、锁状态标志、线程持有的锁、偏向线程ID、偏向时间戳等，
            这部分数据的长度在32位和64位的虚拟机（未开启压缩指针）中分别为32bit和64bit
        class 指针
            对象头的另外一部分是klass类型指针，即对象指向它的类元数据的指针，虚拟机通过这个指针来确定这个对象是哪个类的实例.
        length(数组独有)
            如果对象是一个数组, 那在对象头中有一块数据用于记录数组长度.
    实例数据（Instance Data）
    对齐填充（Padding）
    
    Mark Word在32位JVM中的长度是32bit，在64位JVM中长度是64bit。在不同的锁状态下存储的内容不同，在32位JVM中如图所示：

   ![Image text](https://gitee.com/weixin54321a/ApiStarter/raw/master/resource/markworld.png)
    
    Mark World解释(说明: 此处摘自https://blog.csdn.net/lkforce/article/details/81128115 )

    Mark Word记录了对象和锁有关的信息，当这个对象被synchronized关键字当成同步锁时，围绕这个锁的一系列操作都和Mark Word有关。

    其中无锁和偏向锁的锁标志位都是01，只是在前面的1bit区分了这是无锁状态还是偏向锁状态。
    
    JDK1.6以后的版本在处理同步锁时存在锁升级的概念，JVM对于同步锁的处理是从偏向锁开始的，随着竞争越来越激烈，处理方式从偏向锁升级到轻量级锁，最终升级到重量级锁。
    
     
    
    JVM一般是这样使用锁和Mark Word的：
    
    1，当没有被当成锁时，这就是一个普通的对象，Mark Word记录对象的HashCode，锁标志位是01，是否偏向锁那一位是0。
    
    2，当对象被当做同步锁并有一个线程A抢到了锁时，锁标志位还是01，但是否偏向锁那一位改成1，前23bit记录抢到锁的线程id，表示进入偏向锁状态。
    
    3，当线程A再次试图来获得锁时，JVM发现同步锁对象的标志位是01，是否偏向锁是1，也就是偏向状态，Mark Word中记录的线程id就是线程A自己的id，表示线程A已经获得了这个偏向锁，可以执行同步锁的代码。
    
    4，当线程B试图获得这个锁时，JVM发现同步锁处于偏向状态，但是Mark Word中的线程id记录的不是B，那么线程B会先用CAS操作试图获得锁，这里的获得锁操作是有可能成功的，因为线程A一般不会自动释放偏向锁。
    如果抢锁成功，就把Mark Word里的线程id改为线程B的id，代表线程B获得了这个偏向锁，可以执行同步锁代码。如果抢锁失败，则继续执行步骤5。
    
    5，偏向锁状态抢锁失败，代表当前锁有一定的竞争，偏向锁将升级为轻量级锁。JVM会在当前线程的线程栈中开辟一块单独的空间，里面保存指向对象锁Mark Word的指针，
    同时在对象锁Mark Word中保存指向这片空间的指针。上述两个保存操作都是CAS操作，如果保存成功，代表线程抢到了同步锁，就把Mark Word中的锁标志位改成00，
    可以执行同步锁代码。如果保存失败，表示抢锁失败，竞争太激烈，继续执行步骤6。
    
    6，轻量级锁抢锁失败，JVM会使用自旋锁，自旋锁不是一个锁状态，只是代表不断的重试，尝试抢锁。从JDK1.7开始，自旋锁默认启用，自旋次数由JVM决定。如果抢锁成功则执行同步锁代码，如果失败则继续执行步骤7。
    
    7，自旋锁重试之后如果抢锁依然失败，同步锁会升级至重量级锁，锁标志位改为10。在这个状态下，未抢到锁的线程都会被阻塞。
        

##### JDBC

	加载驱动，建立连接 
	创建语句对象 
	执行SQL语句 
	处理结果集 
	关闭连接
	MySQL优化

##### 存储过程
    
    ?: 跨磁道存储对存储速度的影响.

##### 索引
    
    索引类型:
    B-Tree索引 平衡树
    哈希索引    哈希索引只包含哈希值和行指针，而不存储字段值。
                无法用于排序，不是按照索引值顺序存储的
                不支持部分索引列匹配查找,因为哈希索引是使用索引列的全部内容来计算哈希值得
                只支持等值比较查询，包括=、IN()、<=> 不支持范围索引
    空间数据索引(RTree) 极端情况下会造成全部搜索 
    
    B+Tree
    度 节点横向扩展
    MYISAM 文件数据分开存储
    Innodb 聚集索叶子节点包含数据,普通索引需要查找聚集索引(主键索引)
    
    索引种类:
        普通索引：查找主键信息-->根据主键查找聚集索引-->拿到值
        全文索引：对文本的内容进行分词，进行搜索
        索引合并: 使用多个普通单列索引组合搜索
        组合索引：多列值组成一个索引，专门用于组合搜索，其效率大于索引合并
        覆盖索引: 搜索的值存在与索引中直接返回,不需要拿到主键信息进行再次查找
        主键索引：聚集索引
        通过聚集索引可以查到需要查找的数据， 而通过非聚集索引可以查到记录对应的主键值 ， 再使用主键的值通过聚集索引查找到需要的数据

    索引命中:
        1. 前导模糊查询不能利用索引(like '%XX'或者like '%XX%')
        2. 如果条件中的or包含非索引字段，即使其中有条件带索引也不会使用
        3. 如果估计使用全表扫描要比使用索引快,则不使用索引
        4. 如果需要把空值存入索引，方法有二：其一，把NULL值转为一个特定的值。其二，建立一个复合索引
    
##### class文件结构
    
    魔数          0xCAFEBABE
    主版本号
    次版本号
    常量池计数器  常量成员+1 0表示Object的父类索引不引用任何一个常量池项
    常量池
    访问标志
    父类索引
    接口计数器
    接口表
    方法计数器
    方法表
    属性计数器
    属性表
    
    
    
##### 断开TCP/IP连接

     TCP的连接的拆除需要发送四个包，因此称为四次挥手(four-way handshake)。客户端或服务器均可主动发起挥手动作，在socket编程中，任何一方执行close()操作即可产生挥手操作。
    
    (1）客户端A发送一个FIN，用来关闭客户A到服务器B的数据传送。 
    
    (2）服务器B收到这个FIN，它发回一个ACK，确认序号为收到的序号加1。和SYN一样，一个FIN将占用一个序号。 
    
    (3）服务器B关闭与客户端A的连接，发送一个FIN给客户端A。 
    
    (4）客户端A发回ACK报文确认，并将确认序号设置为收到序号加1。 


##### 浏览器解释数据

	浏览器接收到来自服务器的HTTP数据,解析line,header,body,按顺序解释HTML文本,所以js一般放在body的最后一行,防止Dom未加载完成时,js初始化对dom操作而报错

##### Tomcat

	单实例单应用 (一个tomcat 一个web应用)				缺点:浪费资源
	单实例多应用 (一个tomcat多个应用)					缺点:升级一个应用要挂掉整个Tomcat,维护麻烦
	多实例单应用 (多个tomcat都部署一个应用)			    缺点:某个应用升级失败会导致用户访问到不同的版本实例		nignx反向代理-->服务器安装OpenSSL模块实现Https协议
	多实例多应用 (多个tomcat部署多个不同的应用)		    

    本项目使用内置Tomcat,通过application.yml的server进行Tomcat相关配置
    
    配置名称                                        描述
    server.tomcat.internalProxies                   正则表达式匹配可信IP地址。
    server.tomcat.protocolHeader                    保持传入协议的报头，通常称为X-Forwarded-Proto
    server.tomcat.protocolHeaderHttpsValue          协议值,指明是否开启SSL支持
    server.tomcat.portHeader                        用于重写原始端口值的HTTP报头的名称X-Forwarded-Port
    server.tomcat.remoteIpHeader                    提取远程IP的HTTP报头的名称。例如，`X-FORWARDED-FOR`
    server.tomcat.basedir                           Tomcat的基本目录。如果未指定，则使用临时目录
    server.tomcat.backgroundProcessorDelay          
    server.tomcat.maxThreads                        最大工作线程数
    server.tomcat.minSpareThreads                   最小工作线程数
    server.tmocat.maxHttpHeaderSize                 HTTP消息报头的最大大小(以字节为单位）
    server.tomcat.redirectContextRoot               是否将请求context重定向到其他PATH
    server.tomcat.useRelativeRedirects              
    server.tomcat.uriEncoding                       用于解码URI的字符编码
    server.tomcat.maxConnections                    最大socket连接到tomcat的连接数
    server.tomcat.acceptCount                       当前连接数超过maxConnections的时候，还可接受的连接数，即tcp的完全连接队列(accept队列)的大小.
    
    TomcatWebServerFactoryCustomizer类实现了WebServerFactoryCustomizer-->实现了WebServerFactoryCustomizer
    在TomcatWebServerFactoryCustomizer中进行了一系列的application.yml中Tomcat属性配置到程序配置的转化过程
    
    
### SpringBoot配置文件加载顺序

    1. 开发者工具 `Devtools` 全局配置参数;
    2. 单元测试上的 `@TestPropertySource` 注解指定的参数;
    3. 单元测试上的 `@SpringBootTest` 注解指定的参数;
    4. 命令行指定的参数，如 `java -jar springboot.jar --name="Java"`;
    5. 命令行中的 `SPRING_APPLICATION_JSONJSON` 指定参数, 如 `java -Dspring.application.json='{"name":"Java"}' -jar springboot.jar`
    6. `ServletConfig` 初始化参数;
    7. `ServletContext` 初始化参数;
    8. JNDI参数(如 `java:comp/env/spring.application.json`)
    9. Java系统参数(来源：`System.getProperties()`)
    10. 操作系统环境变量参数;
    11. `RandomValuePropertySource` 随机数，仅匹配：`ramdom.*`;
    12. JAR包外面的配置文件参数(`application-{profile}.properties(YAML）`）
    13. JAR包里面的配置文件参数(`application-{profile}.properties(YAML）`）
    14. JAR包外面的配置文件参数(`application.properties(YAML）`）
    15. JAR包里面的配置文件参数(`application.properties(YAML）`）
    16. `@Configuration`配置文件上 `@PropertySource` 注解加载的参数;
    17. 默认参数(通过 `SpringApplication.setDefaultProperties` 指定)   
    数字小的优先级越高，即数字小的会覆盖数字大的参数值

### Jenkins自动部署
1. 目标: Git代码变更通知Jenkins,Jenkins拉取代码自动构建,执行部署脚并备份文件
2. 搭建步骤:服务器到期了,买了服务器再搭建一遍然后写搭建步骤...

### 版本信息
1. 版本: 0.1

### 开发者

推荐 : QQ群 : 546556883
1. 微信: weixin54321a
2. QQ: 735376047
如果您在使用过程中有疑问或建议,欢迎提出.

### 更新信息
    
1. release v-beta1.0
2. 发布日期 2018.12.04
3. 发布功能 基于SpringBoot的restful风格Api脚手架封装.
4. 发布目标 针对中小型项目,降低学习成本,提高开发效率.
   
### 