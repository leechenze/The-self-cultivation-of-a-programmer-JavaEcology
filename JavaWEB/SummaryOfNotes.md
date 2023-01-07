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

