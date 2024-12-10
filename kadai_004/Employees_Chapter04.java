package kadai_004;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Employees_Chapter04 {
    public static void main(String[] args) {
        // MySQL接続情報
        String jdbcUrl = "jdbc:mysql://localhost/challenge_java"; // challenge_javaに接続
        String dbUser = "root";  // MySQLユーザー名
        String dbPass = "Kbtmy-131"; // MySQLのパスワードを入力

        Connection conn = null;
        Statement stmt = null;

        try {
            // データベース接続
            conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
            System.out.println("データベース接続成功：" + conn);

            // テーブル作成SQL
            String sql ="""
                CREATE TABLE IF NOT EXISTS employees (
                    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(60) NOT NULL,
                    email VARCHAR(255) NOT NULL,
                    age INT(11),
                    address VARCHAR(255)
                );
            """;

            // SQL実行
            stmt = conn.createStatement();
            int result = stmt.executeUpdate(sql);
            System.out.println("社員テーブルを作成しました:更新レコード数=" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
