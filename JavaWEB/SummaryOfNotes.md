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
        添加：添加品牌
        修改：根据ID修改
        删除：根据ID删除
        
                
        

叁.

肆.



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

