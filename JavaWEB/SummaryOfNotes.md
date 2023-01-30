博学之, 审问之, 慎思之, 明辨之, 笃行之;
零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾;



零.JDBC（jdbcPacker)

    JDBC（Java DataBase Connectivity）Java数据库连接
    JDBC简言之就是使用Java操作关系型数据库的一套API
    JDBC定义了操作所有的数据库的规则
    即可以通过同一套Java代码进行操作多种不同的数据库（mysql，oracle等）
    每个不同的数据库都自行定义对JDBC的实现类，进行对JDBC接口的实现，提供数据库驱动Jar包
    Mysql驱动指的就是mysql对于JDBC的实现类，一个Jar包里面各种的实现类而已
    大体步骤：
        1。创建工程，导入驱动jar包
        2。注册驱动
            Class.forName("com.mysql.jdbc.Driver")
        3。获取连接
            Connection conn = DriverManager.getConnection(url, username, password)
        4。定义SQL语句
            String sql = "..."
        5。获取执行sql对象
            Statement stmt = conn.createStatement()
        6。执行sql
            stmt.executeUpdate(sql)
        7。处理返回结果
        8。释放资源








贰.JDBC API详解
    
    Drivermanager（JDBCPacket_DriverManager）
        DriverManager的作用：
            1。注册驱动
            2。获取数据库连接
        管理JDBC驱动程序的基本服务。
        mysql五版本之后可以去掉这一行驱动注册代码，
        可以看到（JavaWEB/lib/mysql-connector-java-8.0.29/mysql-connector-java-8.0.29.jar/META-INF/services/java.sql.Driver）
        其中有配置的com.mysql.cj.jdbc.Driver这一行代码，所以可以弃掉
            Class.forName("com.mysql.cj.jdbc.Driver");
        
        如果jdbc连接的是本地的话，简写形式如下：
            "jdbc:mysql://127.0.0.1:3306/jdbc_packer";
            "jdbc:mysql:///jdbc_packer";
    
    Connection（JDBCPacket_Connection）
        数据库连接对象
        作用：
            1。获取执行SQL的对象
                普通执行SQL对象
                Statement createStatement()
                预编译SQL的执行SQL对象：防止SQL注入
                PreparedStatement prepareStatement(sql)
                执行存储过程的对象（不多用）
                CallableStatement prepareCall(sql)
                
            2。管理事物
                MySql事务管理：
                    开启事务：BEGIN;/ START TRANSACTION
                        MySql默认自动提交事务
                    提交事务：COMMIT
                    回滚事务：ROLLBACK
                JDBC事务管理：
                    开启事务：setAutoCommit(boolean autoCommit)
                        true为自动提交事务
                        false为手动提交事务（即开启事务）
                    提交事务：commit()
                    回滚事务：rollback()
    
    Statement（JDBCPacket_Statement）
        
        Statement作用：执行sql语句；
        执行Sql语句
            int executeUpdate(sql)
                DML：对数据的增删改操作
                    返回的是受影响的行数，如果返回零则表示没有修改成功
                DDL：对表和库的增删改查操作
                    DDL可以对数据库和表进行一些增删改查，所以即使是成功创建或删除数据库都会返回零
                    这就意味着我们无法通过 executeUpdate 的返回值进行判断是否操作成功
                    所以一般对于DDL操作来说执行时不报异常就算成功
            ResultSet executeQuery(sql)
                DQL：对数据的查询操作
        
    ResultSet（JDBCPacket_ResultSet)
        结果集对象
        ResultSet 就是 executeQuery 方法的返回值
        
        boolean next()
            作用：
                1。将光标从当前位置向前移动一行
                2。判断当前行是否为有效行
            返回值：
                true：有效行
                false：无效行
        xxx getXxx(): 获取数据
            xxx：数据类型；如：int getInt（参数）； String getString（参数）
            参数
                1。int：列的编号，注意索引从1开始
                2。String：列的名称
            返回值：xxx；
        使用步骤：
            1。游标向下移动一行，并判断改行是否有数据：next（）
            2。获取数据：getXxx（参数）
            代码：
                while(rs.next()){
                    // 获取数据
                    rs.getXxx(参数)
                }
        练习：查询account账户表数据，封装为Account对象中，并存储到ArrayList集合中
            创建pojo包（pojo一般是用来存放实体类的）
                Account类
                 
    PreparedStatement（JDBCPacket_PreparedStatement) 
        JDBCPacket_PreparedStatement.testLoginInject
            PreparedStatement 是继承自 Statement的
            也是执行sql语句的对象，表示预编译sql语句的对象
            PreparedStatement作用：
                预编译Sql语句并执行：预防Sql注入问题
            Sql注入：
                Sql注入就是通过操作输入来修改事先定义好的sql语句，用以达到执行代码就可以成功登录系统对服务器进行攻击的方法
            为什么 （' or '1' = '1）这段代码可以成功代替密码登入数据库？
                因为拼接后sql的结果是：
                    select * from tb_user where name = 'george' and password = '' or '1' = '1'
                而不是
                    select * from tb_user where name = 'george' and password = 'xxx'
                so 明白了，这就是所谓的（Sql注入就是通过操作输入来修改事先定义好的sql语句）
                那么处理方法就是预编译sql语句（preparedStatement）

        JDBCPacket_PreparedStatement.testLoginInject
            1.sql语句中的参数值，使用 ？占位符进行代替
                String sql = "select * from tb_user where name = ? and password = ?"
                PreparedStatement pstmt = conn.preparedStatement(sql)
            2.设置参数值
                pstmt.setXxx(param1，param2）：给 ？赋值
                Xxx:数据类型；如setInt(param1，param2）
                参数：
                    param1：？的位置编号，从1开始
                    param2：？的值
            3.执行Sql
                executeUpdate();/executeQuery(); 不需要再传递sql
            4.开启预编译
                jdbc:mysql:///jdbc_packer?useServerPrepStmts=true
            
    数据库连接池（JDBCPacket_Druid）
        和线程池一样的作用，用来管理数据库连接的一个容器
        Druid数据库连接池技术
            开启一个数据库连接和关闭数据库连接都是非常耗时的工作
            因为开启一个链接是要建立系统底层的资源进行TCP网络的连接等工作
            而连接池技术会在系统启动之前初始化一个容器，就是一个集合，在集合中提前申请几个数据库的连接
            提前创建好，如果有用户访问时，就从数据库取出一个连接给用户提供服务，当用户执行完事之后，
            连接不会释放掉，而是要归还到容器中，进行资源复用
            所以好处在于：资源复用，提升系统响应速度，同时避免数据库连接遗漏
            连接遗漏意思是当容器中的连接用完之后，没了空闲连接后，会进行判断并对没有操作的用户进行断开连接
            归还到容器中，避免空闲的资源占用。
        常见的数据库连接池：
            DBCP，C3PO，Druid
        数据库连接池实现：
            DataSource
            由Sun公司提供的数据库连接池标准接口，由第三方组织实现此接口
            Connection getConnection()
            换言之，开发中就不用DriveManager获取数据库连接了，而是使用这个getConnection进行连接
        Druid连接池是阿里巴巴开源的数据库连接池项目
        Druid使用步骤：
            导入Jar包 druid-1.1.12.jar
            定义配置文件（druid.properties）
            加载配置文件
            获取数据库连接池对象
            获取连接
        获取当前目录
        System.out.println(System.getProperty("user.dir"))
    
    练习（JDBCPacket_Practice）
        准备环境
            数据库表 tb_brand
                执行sql脚本（lib/sql_script/tb_brand.sql）
            实体类 Brand
                创建实体类（pojo/Brand）
                在实体类中基本数据类型建议使用包装类
                以int为例，Integer默认值为null，int默认值为0，避免对业务产生影响；
            测试用例（test/BrandTest）
                在BrandTest测试类中编写测试方法
        查询：查询所有数据
            test/BrandTest.testSelectAll()
        添加：添加品牌
            test/BrandTest.testAdd()
        修改：根据ID修改
            test/BrandTest.testUpdate()
        删除：根据ID删除
            
                
        
                        
                
        

                
        

叁.Maven
    
    Maven是专门用于构建和管理Java项目的工具，其主要功能：
        提供了一套标准化的项目结构
            常用的开发编辑器：eclipse，myeclipse，idea
            不同的编辑器生成的项目结构是不同的，造成项目不通用的问题
            比如说在eclipse中创建的项目无法在myeclipse中运行，maven就是觉得这个问题的
            项目结构：
                maven-project
                    src     开发代码
                        main        源代码
                            java        java源代码目录
                            resource        源代码配置文件目录
                            webapp          Web项目核心目录（如果是java项目，这个目录可以不要）
                        test        测试代码
                            java            测试代码目录
                            resource        测试代码配置文件目录
                    pom.xml     配置文件
        提供了一套标准化的构建流程（编译，测试，打包，发布。。。）
            通过maven提供的一套简单的命令完成项目的构建
        提供了一套依赖管理机制
            依赖管理就是管理项目中所依赖的第三方资源（jar包，插件）
            比如使用jdbc操作mqsql数据库，需要在项目中导入mysql的驱动jar包，意味着当先项目依赖musql驱动包
            这就是所谓的依赖管理，但是实际开发中并不会手动导入jar包，因为依赖的包太多了，这时就需要maven的统一依赖管理机制
            所有的依赖包都在 External Libraries中存放，通过修改pom.xml刷新后可以看到依赖的具体包。
            所以pom.xml就是一个具体的依赖配置文件
    
    Maven简介：
        官网：http://maven.apache.org/
        基于项目对象的模型（POM）的概念，POM：Project Object Model
        通过 pom.xml 来管理项目的构建，报告和文档
        Maven 读取pom中的依赖，从仓库中查找相应的jar包，进行加载
            maven仓库：本地仓库，中央仓库，远程仓库（私服）。三种仓库作用均不一样
            本地仓库：就是在本机上的 .m2 目录中存放的所有jar包
            中央仓库：有maven团队维护的全球唯一的仓库，在中央仓库中存放了大部分的开源jar包
                https://repo1.maven.org/maven2/
            远程仓库（私服）：一般由公司团队搭建的私有仓库
        Maven查找Jar包流程和机制：
            根据pom所依赖的包首先会在本机仓库进行查找，如果没有结果接下来就到中央仓库查找，如果有的话
            maven就会将中央仓库中的jar包下载到本地仓库中。
            如果有私服的情况下，一般都会将中央仓库的所有jar包同步到私服上来，私服的目的就是为了提升中央仓库访问的速度
            因为中央仓库在国外速度很慢，那么在查找时，先从本地查找，如果没有时再从私服查找，如果还没有才会到中央仓库进行查找并下载到私服和本地
            所以其优先级依次是：本地仓库 => 远程仓库 => 中央仓库
            如果公司没有自己的私服的话，国内也有很多现成可用的私服供使用
    Maven安装配置：
        1。解压 apache-maven-3.6.1.rar 即安装完成
        2。配置环境变量。
            根据自己的系统自行google
        3。配置本地仓库。
            修改 conf/settings.xml
                <localRepository/> 标签为一个指定目录来覆盖Maven默认的本地仓库位置
                本地maven默认本地仓库位置：/Users/lee/.m2/repository
        4。配置阿里云私服。
            修改 conf/settings.xml
                <mirrors/>标签指定阿里云私服
                 <mirror>
                      <id>alimaven</id>
                      <name>aliyun maven</name>
                      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
                      <mirrorOf>central</mirrorOf>
                 </mirror>
    Maven基本使用：
        Maven常用命令(mvn)：
            compile：编译
                第一次执行compile时会进行下载编译插件的一个过程，如果有这个插件则没有步骤了。
                生成 maven 项目中的 target 目录（其中都是编译生成的class文件）
            clean：清理
                删除 maven 项目中的 target 目录
            package：打包
                java项目生成一个jar包
                web项目生成一个war包
                此配置在pom.xml文件中的 packaging 标签中设置
                jar包或war包将生成到target目录下
                packaging 默认为pom，如果为pom则无法生成target，所以必须指定war或jar
                <packaging>jar</packaging>
            test：测试
                会自动执行test/java/com/xxx 下的测试用例的代码
            install：安装
                会将当前项目生成的jar包, 安装到本机maven仓库，安装目录会打印在控制台信息中
        Maven生命周期：
            clear：清理生命周期
            default：核心工作生命周期，例如：编译，测试，打包，安装等...
            site：产生报告，发布站点等（不常用，因为一般不会使用maven发布项目，而是使用其他的工具发布项目）

            清理周期：pre-clean， clean， post-clean
            默认周期：compoile，test，package，install
            发布周期：pre-site，site，post-site

            特点：在同一生命周期内，执行后边的命令，前面的命令会自动执行
            解释：在执行 install命令时，必然会执行compoile，test，package命令
                并不会执行pre-clean，clean，post-clean周期。
                清理和默认核心周期不在同一周期。
            
    IDEA配置Maven：
        IDEA配置 Maven 环境
            settings 中搜索 Maven，根据自身情况执行设置，主要配置三项：
                1。Maven home path   本地maven的根目录地址
                2。User settings file    maven的settings.xml配置文件
                3。Local repository      maven的本地仓库
        Maven 坐标详解
            坐标解释：（其实就是pom.xml中的依赖作用的标签）
                Maven中的坐标指的是资源的唯一标识
                使用坐标来定义项目或引入项目中需要的依赖
            Maven坐标主要组成：
                groupId：定义当前Maven项目隶属组织的名称（通常都是域名反写，比如：com.baidu）
                artifactId：定义当前Maven项目的名称（模块名称）
                version：定义当前项目的版本号
        Idea创建Maven项目
            简单，此处略过
        Idea导入Maven项目
            1。打开右侧Maven面板，找到一个加号（Add Maven Projects）
            2。选择对应项目的pom.xml文件，双击即可导入
            View ==> Appearance ==> Tool Window Bars
        插件推荐：Maven Helper
        
    依赖管理：
        使用坐标导入Jar包
            1。在pom.xml中编写<dependencies>标签
            2。在<dependencies>标签中使用<dependency>引入坐标
            3。定义坐标的groupId，artifactId，version
            4。刷新按钮使坐标生效
            
        settings ==> build tools ==> reload project after changes in the build scripts ==> any changes
        配置pom.xml文件修改后自动生效，无需刷新
        command + n 命令 ==> 选择dependency 选择本地仓库中的现有模块导入到pom.xml
        
        依赖范围：
            通过设置坐标的依赖范围（scope）：可以设置对应jar包的作用范围：编译环境，测试环境，运行环境
            编译环境：只在project/src/main/ 目录下生效
            测试环境：只在project/src/test/ 目录下生效
            运行环境：导入的jar包在最终运行时生效
            
            compile
                scope的默认值，在编译，测试，运行环境生效
            test
                只在测试环境生效
            provided
                在编译，测试环境生效
            runtime
                在测试，运行环境生效
            system（用到不多，做个了解）
                在编译，测试环境生效
            import
                用于引入DependencyManagement
            
    
    
                
                
        

肆.MyBatis
    
    MyBatis简介：
        MyBatis是一款优秀的持久层框架，用于简化JDBC开发
        持久层：
            负责将数据保存到数据库的那一层代码
            JavaEE三层架构：表现层，业务层，持久层
                表现层：页面展示
                业务层：逻辑处理
                持久层：对数据进行持久化，保存到数据库
            
                
    
    



伍.



陆.





柒.




捌.




玖.







拾.




拾壹.





拾贰.




拾叁.




拾肆.

