package SQLTest;
import java.sql.*;
import java.util.*;

public class MDB3 {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ReflectiveOperationException ex) {
            System.err.println("Can't load MySQL JDBC, " + ex);
            System.exit(1);
        }
    }
    
    private Connection conn;
    
    public MDB3() throws SQLException {
        String host = "localhost", database = "mdb",
            user = "nisse", password = "ratthastbatterihafta";
        
        conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database +
                                           "?user=" + user + "&password=" + password);
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }
        
    public List<Object[]> getFilms() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select Titel, Ar, fID from Film");
        ResultSet rs = stmt.executeQuery();
        List<Object[]> reslist = new ArrayList<Object[]>();
        while (rs.next()) {
            Object[] row = new Object[] { rs.getString(1), rs.getInt(2), rs.getInt(3) };
            reslist.add(row);
        }
        return reslist;
    }
        
    public List<Object[]> getCrew(int fID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement( "select Fornamn, Efternamn, Roll from (Crew c join Person p on c.pID=p.pID) where fID="+fID);
        ResultSet rs = stmt.executeQuery();
        List<Object[]> reslist = new ArrayList<Object[]>();
        while (rs.next()) {
            Object[] row = new Object[] { rs.getString(1), rs.getString(2), rs.getString(3) };
            reslist.add(row);
        }
        return reslist;
    }
        
    public void addCrew(int fID, int pID, String roll) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement( "insert into Crew(fID, pID, Roll) values (?, ?, ?)");
        stmt.setInt(1, fID);
        stmt.setInt(2, pID);
        stmt.setString(3, roll);
        stmt.executeUpdate();
    }
}
