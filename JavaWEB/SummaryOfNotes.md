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
        框架：
            框架就是一套可重用的，通用的，软件基础代码模型
            在框架的基础之上构建软件编写更加高效，规范，通用，可扩展
            
        JDBC缺点：
            1。硬编码：
                就是通常所说的一些活动的变量写成固定值，比如密码，sql语句等
            2。操作繁琐：
                手动设置参数：sql语句中的？未知值等
                手动封装结果集：遍历result，获取数据，创建对象，设置属性，装入集合。。。
        
        MyBatis简化了哪些：
            1。硬编码写入配置文件
                数据库账号密码，sql语句写入到配置文件中，而不是在代码中写入
            2。繁琐操作：
                List<User> users = sqlSession.selectList("test.selectByGender", "男")
                MyBatis省去了几乎所有的JDBC代码以及设置的参数和获取结果集的工作
        
        创建MyBatisModules模块
            MyBatis快速入门：
                查询User表中所有的数据：
                    1。创建User表，添加数据
                    2。创建模块，导入坐标
                    3。编写MyBatis核心配置文件，替换连接信息，解决硬编码问题
                        mybatis官网地址：https://mybatis.org/mybatis-3/getting-started.html
                        可以全程按着官网进行配置
                        在resources下新增：mybatis-config.xml（mybatis核心配置文件）
                            路径：JavaWEB/MyBatisModules/src/main/resources/mybatis-config.xml
                        在resources下新增：logback.xml（日志配置文件）
                            路径：JavaWEB/MyBatisModules/src/main/resources/logback.xml
                    4。编写SQL映射文件，统一管理sql语句，解决硬编码问题
                        在resources下新增：UserMapper.xml
                            路径：JavaWEB/MyBatisModules/src/main/resources/mybatis-config.xml
                            说明：User是表名称，Mapper表示映射，意思为：user表的sql映射
                                如果是Order表，就可以命名为OrderMapper.xml，以此类推
                    5。编码
                        1。定义POJO类
                            创建一个User类
                                路径：JavaWEB/MyBatisModules/src/main/java/com/lee1/pojo/User.java
                            创建一个加载核心配置文件的MyBatis的Demo类
                                路径：JavaWEB/MyBatisModules/src/main/java/com/lee1/MyBatisDemo.java
                        2。加载核心配置文件，获取SqlSessionFactory对象
                            详见：MyBatisDemo.java
                        3。获取SqlSession对象，执行sql语句
                            详见：MyBatisDemo.java
                        4。释放资源
                            详见：MyBatisDemo.java
            Mapper代理开发：
                在MyBatisDemo中：
                    List<User> users = sqlSession.selectList("user.selectAll");
                user.selectAll 这段根据名称空间选择的sql语句标识 又成为了硬编码；
                Mapper代理开发解决的就是这个问题；
                
                使用Mapper：
                    1。定义与SQL映射文件同名的Mapper接口，并将Mapper接口和Sql映射文件放置在统一目录下，
                        在src/main/java/com/lee1下新建mapper包专门放置Mapper接口
                        最优方案是在resource下创建和java目录下同样的路径com/lee1/mapper/UserMapper.xml
                        以替换掉原UserMapper.xml配置文件，这样编译之后的目录就会置于同一目录下。
                        注意在resource下创建的是目录而不是包，所以要用/而不能用.创建目录层级
                    2。设置SQL映射文件的namespace属性为Mapper接口全限定名
                        即是namespace="user" 替换为 namespace="com.lee1.mapper.UserMapper"
                    3。在Mapper接口中定义方法，方法名就是SQL映射文件中sql语句的ID，并保持参数类型和返回值类型一致；
                        public interface UserMapper {
                            List<User> selectAll();
                        }
                        最终是一个User类型的集合，所以应该是 List<User>
                    4。编码
                        1。通过SqlSession 的 getMapper方法获取 Mapper接口的代理对象
                        2。调用对应的方法完成sql执行
                    5。mybatis-config.xml加载所有SQL映射文件
                        <!-- ❌❌❌ mappers 用于加载SQL映射文件 -->
                        <!--<mapper resource="com/lee1/mapper/UserMapper.xml"/>-->
                        <!--<mapper resource="com/lee1/mapper/OrderMapper.xml"/>-->
                        <!--<mapper resource="com/lee1/mapper/LoginMapper.xml"/>-->
                        <!-- ✅✅✅ Mapper 包扫描的方式加载所有的SQL映射文件-->
                        <package name="com.lee1.mapper"/>
            MyBatis核心配置文件：
                详见mybatis-config.xml中的注释
                官网文档：https://mybatis.org/mybatis-3/configuration.html
                细节：在配置时要严格遵守标签的前后顺序，详见官网文档
            配置文件完成增删改查：
                即：将sql语句写到配置文件上；
                准备环境：
                    数据库表：tb_brand
                    实体类：main/java/com.lee1.pojo.Brand
                    测试用例：test/java/com.lee1.pojo.MyBatisTest
                    安装MyBatisX插件：助于快速开发mybatis
                        需要注意这个插件会和mybatis的别名配置有冲突，导致resultType的值找不到配置的别名
                需要完成的功能列表清单：
                    1。查询：
                        查询所有数据
                            总结：
                                数据库表的字段名称 和 实体类的属性名称格式不一致，则不能自动封装，导致一些查询的结果为null
                                解决方法：
                                    1。起别名
                                        select id, brand_name as brandName, company_name as companyName, ordered, description, status from mybatis.tb_brand;
                                    2。抽离sql片段
                                    3。resultMap(属性名和字段名的映射)
                        查看详情
                            总结：
                                参数占位符：
                                    where id = #{id} 将会替换为 where id = ? 为了放置sql注入
                                    where id = ${id} 将会替换为 where id = 1 会存在sql注入问题
                                    使用时机：参数传递时候都用 #{}，一般在表名或列名不固定的情况下可以使用${}
                                参数类型：
                                    parameterType 指定#{id}的类型，一般省略不写，因为在Mapper中的selectById方法已经指定了参数类型
                                特殊字符处理：在xml中不能使用一些特殊字符比如：< 在xml中有特殊含义，可以使用&lt，或者CDATA
                                    1。转义字符：
                                        &lt;
                                    2。CDATA区：
                                        <![CDATA[
                                            <
                                        ]]>
                        条件查询
                            总结： 
                                1。散装参数
                                2。对象参数
                                3。map集合参数
                                
                                动态sql：(即sql语句会随着用户的输入或外部条件的变化而变化)
                                    动态sql(多条件)：
                                        if 判断如果有值时才会进行查询
                                            test 逻辑表达式
                                        问题：（第一个条件不需要逻辑运算符）
                                            如果没有status的值，sql将是：select * from tb_brand where and company_name like '%华为%';
                                            这就导致了语法错误
                                            解决方案：
                                                1。恒等式： where后面拼接 1=1 and 后面拼接sql条件
                                                2。mybatis提供的解决方案：<where></where> 替换 where
                                    动态sql(单条件)
                                        choose(when，otherwise)
                                            choose 类似于 switch
                                            when 类似于 case
                                            otherwise 类似于 default
                    2。添加
                        insert
                            useGeneratedKeys="true" 指定允许生成key
                            keyProperty="key" 指定生成哪个key
                                用以生成主键ID，如：MyBatisTest.java中
                                Integer id = brand.getId();
                                System.out.println(id);
                                不指定这两个属性无法获取id；
                    3。修改
                        修改全部字段
                        修改动态字段
                    4。删除
                        删除一个
                        批量删除
                步骤：
                    1。编写接口方法：Mapper接口
                        参数：无
                        结果：List<Brand>
                    2。编写SQL语句：SQL映射文件
                    3。执行对应的测试方法
                        test/java/com.lee1.pojo/MyBatisTest.java

            参数传递：（UserMapperTest1.java, UserMapper1.java ）
                mybatis接口方法中可以接收各种各样的参数，Mybatis底层对于这些参数进行不同的封装处理
                mybatis底层提供了ParamNameResolver 类来进行参数封装
                单个参数：
                    POJO类型
                    Map集合
                    Collection
                    List
                    Array
                    其他类型
                多个参数：
                    需要使用 @Param 注解进行指定
            
            注解完成增删改查：（UserMapperTest1.java, UserMapper1.java ）
                即：将sql语句写到注解上；
                使用注解开发会比使用配置文件开发更加方便
                注解一般完成简单功能，配置文件完成复杂功能
                    查询：@Select
                    添加：@Insert
                    修改：@Update
                    删除：@Delete
                







伍.Web核心
    
    JavaWEB中最重要的部分
    web核心介绍：
        JavaWeb技术栈：
            B/S架构：Browser/Server，浏览器/服务器架构模式。
    HTTP：
        概念：HyperText Transfer Protocol，超文本传输协议。
        请求格式(Request Headers)：
            请求行：GET / HTTP/1.1
                GET表示请求方式
                /表示请求资源路径
                HTTP/1.1表示协议版本
            请求头：从第二行开始，格式为key：value形式
                Host：请求的主机名
                User-Agent：浏览器版本信息
                Accept：表示浏览器能够接收的资源类型，如：text/*，image/* 或者 */* 表示所有；
                Accept-Language：浏览器偏好语言，服务器可以根据此项返回不同语言的网页
                Accept-Encoding：表示浏览器可以支持的压缩类型，例如：gzip，deflate等
            请求体：POST特有的东西
                参数在 和 Request Headers 同级的 Form Data中
        响应格式（Response Headers）
            响应行：HTTP/1.1 200 OK
                HTTP/1.1表示协议版本，200表示响应的状态码，OK表示状态码描述
            响应头：第二行开始，格式为Key：value形式
                Content-Type：表示响应的内容类型，例如：text/html，image/jpeg；
                Content-Length：表示该响应内容的长度（字节数）
                Content-Encoding：表示该响应的压缩算法，例如gzip
                Cache-Control：指示客户端该如何缓存，例如：max-age=300 表示最多可以缓存300秒
            响应体：最后一部分，存放响应数据
    Web服务器 - Tomcat：
        Web服务器是一个应用程序，对HTTP协议的操作进行封装，使得开发者不必再直接对协议进行操作，专注于Web开发，主要功能就是"提供网上信息浏览服务"
        简介：Tomcat也被成为Web容器，Servlet容器，Servlet需要依赖于Tomcat才能运行
            是一个轻量级的Web服务器，支持Servlet/JSP少量的JavaEE规范。
        官网：https://tomcat.apache.org/
        Tomcat：
            基本使用：安装，卸载，启动，关闭，配置，部署
                bin：核心目录（启动，关闭）
                    sudo chmod +x *.sh（给予操作权限）
                    sh startup.sh（启动）
                    sh shutdown.sh（关闭）
                    sh catalina.sh run（查看日志）
                conf：配置目录（配置）
                    logging.properties：日志信息相关配置（常用 encoding = GBK）解决日志输出信息中文乱码问题
                    server.xml：服务运行配置，常用：Connector标签配置端口号
                        注：HTTP协议默认端口号为80，将端口改为80，那么访问Tomcat时，可以直接输入 localhost进行访问
                lib：tomcat运行过程中依赖的jar包
                logs：日志文件
                temp：运行过程中产生的临时文件
                webapps：tomcat中的web核心项目，将来写的Web项目放置此处（部署）
                    一般JavaWeb项目会打成一个war包，然后将war包放到webapps目录下，即完成部署，Tomcat会自动解压缩war文件。
                work：运行过程中产生的临时数据
            IDEA中创建Maven Web项目
                Web项目结构：
                    开发中的项目：（和Maven Java项目结构一样，只不过在main下多了一个webapp目录）
                        project
                            src
                                main
                                    java
                                    resources
                                    webapp      web项目特有目录
                                        html    HTML文件目录
                                        WEB-INF     Web项目核心目录
                                            web.xml     Web项目配置文件
                                test
                                pom.xml
                    部署的JavaWeb项目结构：
                        project
                            html
                            WEB-INF
                                classes     Java字节码文件（对应main目录下的java和resource目录）
                                lib         项目所需的依赖Jar包（对应pom.xml文件中的一些Jar包）
                            web.xml     Web项目配置文件

                创建：MavenWebModules模块
                    使用骨架的方式：创建模块时勾选 create from archetype，选中 maven-archetype-webapp作为骨架
                    使用非骨架的方式：直接创建模块不勾选 create from archetype，创建完成后project settings中的 Facets 中设置选择加号添加web项目... 无图难以解释，自行CSDN摸索
                
            IDEA中使用Tomcat
                方式1。集成本地Tomcat，即可在Idea中启动项目并且部署项目了
                    edit configurations 点击 + 号，添加Tomcat服务，配置很简单，自行CSDN，不在详述。
                方式2。Tomcat Maven插件
                    在 pom.xml中：快捷键command + n 快速生成 插件模版
                    启动：右键选择Run Maven：tomcat7:run
                    <plugin>
                        <groupId>org.apache.tomcat.maven</groupId>
                        <artifactId>tomcat7-maven-plugin</artifactId>
                        <version>2.2</version>
                        <configuration>
                            <port>8082</port>
                            <path>/</path>
                        </configuration>
                    </plugin>
                    <path>/</path>这里配置了访问路径为/，所以可以直接访问 http://localhost:8082/b.html
                    
                    缺点：这个插件只支持到tomcat7这个版本，更高的tomcat版本无法支持 
    Servlet：（ServletModules）
        Servlet是Java提供的一门动态web资源开发技术
        Servlet就是JavaEE规范之一，其实就是一个接口，需要定义Servlet类实现Servlet接口，并由web服务器运行Servlet
        Servlet快速入门
            1。创建web项目，导入Servlet坐标（必须将scope指定为provided，否则将会报错）
                <dependency>
                    <groupId>javax.servlet</groupId>
                    <artifactId>javax.servlet-api</artifactId>
                    <version>3.1.0</version>
                    <scope>provided</scope>
                </dependency>
            2。创建：定义一个类，实现Servlet接口，并重写接口中所有方法，并在service方法中输出一句话，以证明是否正确执行
                ServletModules/src/main/java/com.lee.web.SerletDemo1
                // 重写service方法
                @Override
                public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
                    System.out.println("hello servlet");
                }
            3。配置：在类上使用@WebServlet注解，配置该Servlet的访问路径
                @WebServlet("/demo1")
                public class ServletDemo1 implements Servlet {...}
            4。访问：启动Tomcat，浏览器输入URL访问该Servlet
                Running war on http://localhost:8080/ServletModules
        Servlet执行流程
            Servlet的创建和方法调用都是由Web服务器Tomcat完成的
        Servlet生命周期（ServletDemo2）
            Servlet运行在Servlet容器中（web服务器），其生命周期由容器来管理，分为四个阶段：
                1。加载和实例化阶段
                    默认情况下，当Servlet第一次被访问时，由容器创建Servlet对象
                    @WebServlet（urlPatterns = "/demo"，loadOnStartUp = -1）
                    可以通过修改loadOnStartUp修改默认情况，默认值为-1
                        负整数时：第一次被访问时创建Servlet对象
                        0或正整数时：服务器启动时创建Servlet对象，数字越小优先级越高
                2。初始化
                    在Servlet实例化之后，容器调用init方法初始化这个对象，完成一些如加载配置文件，创建连接等初始化工作，该方法只调用一次
                3。请求处理阶段
                    每次请求Servlet时，Servlet容器都会调用Servlet的service方法对请求进行处理
                4。服务终止阶段
                    当需要释放内存或容器关闭时，容器就会调用Servlet实例的destory 方法完成资源释放，调用之后容器释放Servlet实例，该实例随后被Java垃圾收集器回收
                    这里需要在命令行中使用 mvn tomcat7:run 命令启动 然后 control + c进行停止才会触发 destory方法
                    idea中 stop 'Tomcat9.0.7' 的红色方块按钮不会触发，这个按钮等于直接拔电源
            
            获取ServletConfig对象（一般返回一个任意字符串就行，没有用处）
                ServletConfig getServletConfig()
            获取Servlet信息
                String getServletInfo()
            
        Servlet体系结构（ServletDemo3）
            Servlet（根接口）
                GenericServlet（抽象实现类）
                HttpServlet（对HTTP协议封装的Servlet实现类）
            开发B/S架构的Web项目，都是针对HTTP协议，所以我们自定义Servlet，会继承HttpServlet
                复写 doGet 和 doPost方法
                get请求会执行doGet，post请求会执行doPost 方法；
            HttpServlet原理，获取请求方式，并根据不同的请求方式，调用不同的doXxx方法。
        Servlet urlPattern配置
            Servlet想要被访问必须配置其访问路径（urlPattern）
            1。一个Servlet，可以配置多个urlPattern（ServletDemo4）
                @WebServlet(urlPattern = {"/demo1", "/demo2"})
            2。urlPattern匹配规则
                精确匹配
                    配置路径：@WebServlet(urlPatterns = "/user/select")
                    访问路径：localhost:8080/projectname/user/select
                目录匹配
                    配置路径：@WebServlet(urlPatterns = "/user/*")
                    访问路径：localhost:8080/projectname/aaa
                    访问路径：localhost:8080/projectname/bbb
                扩展名匹配
                    配置路径：@WebServlet(urlPatterns = "*.html")
                    访问路径：localhost:8080/projectname/aaa.html
                    访问路径：localhost:8080/projectname/bbb.html
                任意匹配
                    配置路径：@WebServlet(urlPatterns = "/")
                    配置路径：@WebServlet(urlPatterns = "/*")
                    访问路径：localhost:8080/projectname/aaa
                    访问路径：localhost:8080/projectname/aaa/bbb
                    / 和 /*的区别：（两者都谨慎使用）
                        /：tomcat中默认的路径DefaultServlet，当其他url-pattern都匹配不上时，会走这个Servlet
                            所以会导致一个问题就是所有的静态资源都无法访问：比如在浏览器输入 a.html会跳转到urlPattern为/的这个Servlet中
                        /*：意味匹配任意访问路径
        XML配置方式编写Servlet（ServletDemo5）
            Servlet从3.0版本之后开始支持注解配置，3.0版本之前只支持xml配置文件进行配置
            1。编写Servlet类
            2。在web.xml中配置该Servlet
                <!--配置Servlet全类名-->
                    <servlet>
                        <servlet-name>demo5</servlet-name>
                        <servlet-class>com.lee.web.ServletDemo5</servlet-class>
                    </servlet>
                <!--Servlet的访问路径-->
                    <servlet-mapping>
                        <servlet-name>demo5</servlet-name>
                        <url-pattern>/demo5</url-pattern>
                    </servlet-mapping>
        Request & Response的介绍和继承体系（ServletDemo6）
            Request继承体系
                ServletRequest：Java提供的请求对象根接口
                HttpServletRequest：Java提供的对Http协议封装的请求对象接口
                RequestFacade：Tomcat定义的实现类
            Request获取请求数据（RequestDemo1 和 RequestDemo.html）
                请求行
                    GET /request-demo/req1?username=trump HTTP/1.1
                    String getMethod()：获取请求方式
                    String getContextPath()：获取虚拟目录（即项目的访问路径的根路径：request-demo）
                    StringBuffer getRequestURL()：获取URL（统一资源定位符）：http://localhost:8080/request-demo/req1
                    String getRequestURI()：获取URL（统一资源标识符）：request-demo/req1
                    String getQuerySring()：获取请求参数（GET方式）：username=trump&password=123
                请求头
                    String getHeader(String name)：根据请求头的名称获取值
                请求体
                    只有post参数才有请求体
                    ServletInputStream getInputStream(): 获取字节输入流，一般用于读取字节数据（比如文件上传）
                    BufferedReader getReader(): 获取字符输入流，一般用于读取文本数据
            Request通用方式获取请求参数：(RequestDemo2 & RequestDemo2.html)
                请求参数获取方式
                    Get方式：getQueryString()
                    Post方式：BufferedReader getReader()
                思考：如果能够提供一种统一的获取请求参数的方式，从而就可以统一doGet和doPost方法内的代码
                    Map<String,String[]> getParameterMap()：获取所有所有参数Map集合
                    String[] getParameterValues(String name)：根据名称获取参数值（数组）
                    String getParameter(String name)：根据名称获取参数值（单个）
                工具：（RequestDemo3）
                    创建时可以右键 New ==> Servlet
                    相关模版配置：
                        File and code template
                            Other ==> Web ==> Servlet Annotated Class.java
                中文乱码问题：（RequestDemo3 & RequestDemo3.html）
                    // post请求方式解决乱码：设置字符输入流编码为UTF-8，因为post底层是走的getReader() 获取字符输入流
                    request.setCharacterEncoding("UTF-8");
                    // get请求方式解决乱码：tomcat进行url解码默认字符集是 iso-8859-1的方式
                    // 1。先对乱码数据进行编码：转为字节数组
                    String username = request.getParameter("username");
                    System.out.println("解决乱码前的username" + username);
                    byte[] bytes = username.getBytes(StandardCharsets.ISO_8859_1);
                    // 2。对字节数组解码
                    username = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("解决乱码后的username" + username);
                    提醒：
                    在Tomcat8.0之后已将默认编码方式设为UTF-8，这就意味着在Tomcat8之后的版本都不存在乱码问题了
            Request请求转发（RequestDemo4 & RequestDemo5）
                Forward：一种在服务器内部的资源跳转方式，大白话就是在demo4中触发demo5中的逻辑
                实现方式：
                    request.getRequestDispatcher("资源路径").forward(request,response);
                请求转发资源间共享数据：使用request对象
                    void setAttribute(String name, Object o):存储数据到request域中
                    Object getAttribute(String name): 根据Key，获取值
                    void removeAttribute(String name): 根据Key，删除该键值对
                请求转发的特点：
                    浏览器地址栏路径不会发生变化
                    只能转发当前服务器的内部资源（请求转发无需加虚拟目录）
                    一次请求，可以在转发的资源间使用request共享数据
            Response设置响应数据
                响应行
                    HTTP/1.1 200 OK
                    void setStatus(int sc): 设置响应状态码
                响应头
                    Content-Type: text/html
                    void setHeader(String name, String value): 设置响应头键值对
                响应体
                    PrintWriter gerWriter(): 获取字符输出流
                    ServletOutputStream getOutputStream(): 获取字节输出流
            Response完成重定向（ResponseDemo1 & ResponseDemo2）
                Redirect：一种资源跳转的方式，类似于Request请求转发Forward，但两者有很大的区别。
                场景:
                    资源A：客户端来请求，无法处理这次请求，但是资源B可以处理，然后响应给客户端302状态码和资源B的路径
                    资源B：接收请求进行请求处理
                实现方式：
                    response.setStatus(302);
                    response.setHeader("location","资源路径")
                重定向特点：
                    浏览器地址栏路径会发生变化
                    可以重定向到任意位置的资源（服务器内部，外部均可，如果内部重定向注意需要加上虚拟目录）
                    两次请求，不能在多个资源使用request共享数据
                路径问题：
                    重定向和请求转发两者关于路径的问题：
                        一般给浏览器使用时都需要加上根目录（虚拟路径）
                        给服务端使用时则无需加虚拟目录
                    固定路径（不推荐）
                        response.sendRedirect("/ServletModules/ResponseDemo2");
                    动态路径（推荐）
                        String contextPath = request.getContextPath();
                        response.sendRedirect(contextPath + "/ResponseDemo2");
            Response响应字符数据（ResponseDemo3）
                1。通过Response获取字符输出流
                    PrintWriter writer = response.getWriter();
                2。写数据
                    writer.write("aaa");
                细节：writer 写入流不需要关闭，因为write是随着response对象获取出来的，所以响应完毕之后response对象会被销毁，销毁之后这个流会自动被关掉，不在需要手动调用close方法
            Response响应字节数据
                1。通过Response对象获取字节输出流
                    ServletOutputStream outputStream = response.getOutputStream();
                2。写数据
                    outputStream.write("字节数据")
                工具：commons-io 2.6 是apache提供的一个io相关的工具类，非常好用，其中有提供对应的拷贝的方法
        案例：（Login-example-modules）
            详见 Login-example-modules 这个模块
            准备环境：
                创建静态页面到项目的webapp项目目录下
                创建login_db数据库，创建tb_user表，创建User实体类
                导入MyBatis坐标，MySql驱动坐标
                创建mybatis-config.xml核心配置文件，UserMapper.xml映射文件，UserMapper接口
            用户登录：（LoginServlet）
            用户注册：（RegisterServlet）
                

陆.





柒.




捌.




玖.







拾.




拾壹.





拾贰.




拾叁.




拾肆.

