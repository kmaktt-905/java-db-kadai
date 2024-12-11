package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement statement = null;

        try {
            // データベース接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java", // データベースURL
                "root", // ユーザー名
                "Kbtmy-131" // パスワード
            );
            System.out.println("データベース接続成功：" + con);

            // INSERT文で複数行のデータを追加
            String insertSql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES " +
                               "(1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13), " +
                               "(1002, '2023-02-08', 'お疲れ様です！', 12), " +
                               "(1003, '2023-02-09', '今日も頑張ります！', 18), " +
                               "(1001, '2023-02-09', '無理は禁物ですよ！', 17), " +
                               "(1002, '2023-02-10', '明日から連休ですね！', 20);";

            statement = con.prepareStatement(insertSql);
            int rowsInserted = statement.executeUpdate();
            System.out.println(rowsInserted + "件のレコードが追加されました");

            // SELECT文でユーザーIDが1002のデータを検索
            String selectSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = ?;";
            statement = con.prepareStatement(selectSql); // PreparedStatement を作成
            statement.setInt(1, 1002); // プレースホルダに値を設定

            ResultSet result = statement.executeQuery();
            System.out.println("ユーザーIDが1002のレコードを検索しました");

            // 結果を出力
            int recordNumber = 0;
            while (result.next()) {
                recordNumber++;
                String postedAt = result.getDate("posted_at").toString();
                String postContent = result.getString("post_content");
                int likes = result.getInt("likes");

                System.out.printf("%d件目：投稿日時=%s／投稿内容=%s／いいね数=%d%n", 
                                  recordNumber, postedAt, postContent, likes);
            }

        } catch (SQLException e) {
            // エラー内容を出力
            System.out.println("エラー発生：" + e.getMessage());
            e.printStackTrace();
        } finally {
            // リソースの解放
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }
}
