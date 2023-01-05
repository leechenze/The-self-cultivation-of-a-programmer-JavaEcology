
package com.lee.jdbc;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.Statement;

public class JDBCPacket_DriverManager {
    public static void main(String[] args) throws Exception {
        // 2。获取连接
        String url = "jdbc:mysql:///jdbc_packer";
        String userName = "root";
        String password = "lcz19930316";
        Connection conn = DriverManager.getConnection(url, userName, password);
        // 3。定义sql语句
        String sql = "update account set money = 9999 where id = 1;";
        // 4。获取执行sql的对象
        Statement stmt = conn.createStatement();
        // 5。执行sql(受影响的行数 Affected rows: 1)
        int count = stmt.executeUpdate(sql);
        // 6.打印处理结果
        System.out.println(count);
        // 7.释放资源
        stmt.close();
        conn.close();

    }
}
