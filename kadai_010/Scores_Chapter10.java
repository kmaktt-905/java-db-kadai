package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Scores_Chapter10 {
    public static void main(String[] args) {
        // データベース接続情報
        Connection conn = null;

        try {
            // データベースに接続
            conn = DriverManager.getConnection(
            		"jdbc:mysql://localhost/challenge_java", 
                    "root", 
                    "Kbtmy-131" 
            );
            System.out.println("データベース接続成功：" + conn);

            // データ更新
            System.out.println("レコード更新を実行します");
            String updateSql = "UPDATE score SET score_math = ?, score_english = ? WHERE id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, 95);
            updateStmt.setInt(2, 80); 
            updateStmt.setInt(3, 5);  
            int rowsUpdated = updateStmt.executeUpdate();
            System.out.println(rowsUpdated + "件のレコードが更新されました");

            // データの取得と並べ替え
            System.out.println("数学・英語の点数が高い順に並べ替えました");
            String selectSql = "SELECT * FROM score ORDER BY score_math DESC, score_english DESC";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            ResultSet rs = selectStmt.executeQuery();

            int recordCount = 1;
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int scoreMath = rs.getInt("score_math");
                int scoreEnglish = rs.getInt("score_english");
                System.out.printf("%d件目：生徒ID=%d／氏名=%s／数学=%d／英語=%d\n",
                        recordCount, id, name, scoreMath, scoreEnglish);
                recordCount++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
