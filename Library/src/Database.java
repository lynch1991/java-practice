import java.sql.*;

public class Database {
    private Connection conn = null;
    private Statement stmt = null;


    Database(){
        String url = "jdbc:sqlite:Library.db";
        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS members " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NATIONAL_NUMBER  TEXT   NOT NULL, " +
                    " NAME           TEXT    NOT NULL, " +
                    " LAST_NAME           TEXT   NOT NULL, " +
                    " GENDER           TEXT NOT NULL, " +
                    " AGE            INT, " +
                    " ADDRESS        TEXT, " +
                    " REGISTER_DATE        TEXT NOT NULL,"+
                    " STATUS        TEXT NOT NULL);";
            stmt.executeUpdate(sql);
            stmt.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void transaction(String sql) {
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void getResult(String sql) throws SQLException {
        stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        int idColumn=result.getInt("ID");
        String nationalNumberColumn=result.getString("NATIONAL_NUMBER");
        String nameColumn=result.getString("NAME");
        String lasNameColumn=result.getString("LAST_NAME");
        String genderColumn=result.getString("GENDER");
        int ageColumn=result.getInt("AGE");
        String addressColumn=result.getString("ADDRESS");
        String registerDateColumn=result.getString("REGISTER_DATE");
        int statusColumn=result.getInt("STATUS");
        System.out.printf(String.format("""
                search result:
                Id: %s
                National Number: %s
                Firstname: %s
                Lastname: %s
                Gender: %s
                Age: %d
                Address: %s
                Register date: %s
                Status: %s
                """,idColumn,nationalNumberColumn,nameColumn,
                lasNameColumn,genderColumn,ageColumn,addressColumn,registerDateColumn,statusColumn ));
        stmt.close();
    }
    public int getResultCount(String sql,String label) throws SQLException {
        stmt = conn.createStatement();
        int count = stmt.executeQuery(sql).getInt(label);
        stmt.close();
        return count;
    }
}
