//Matthew Koch (mik5398)

package magicianagent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Matt
 */
public class Waitlist
{
    private String customer;
    private String holiday;
    private Timestamp firstRowTimestamp;
    private ArrayList<String> waitlist;
    private Connection connection;
    private PreparedStatement insertWaitList;
    private PreparedStatement removeWaitList;
    private final String SELECT_QUERY = "SELECT TIMESTAMP FROM WAITLIST";
    private final String REMOVE_QUERY = "SELECT";
    private final String INSERT_QUERY = "INSERT INTO WAITLIST(CUSTOMER, HOLIDAY, TIMESTAMP) VALUES (?, ?, ?)";
        
    private final String  DATABASE_URL ="jdbc:derby://localhost:1527/MagicianAgent";
    private final String USERNAME = "al";
    private final String PASSWORD = "al";
    
    public Waitlist()
    {
        customer = "";
        holiday = "";
        waitlist = new ArrayList<>();
    }
    
    public void addToWaitlist(String customerName, String holiday)
    {
        try
        {
            Calendar calendar = Calendar.getInstance();
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());            
            insertWaitList = connection.prepareStatement(INSERT_QUERY);            
            insertWaitList.setString(1, customerName);
            insertWaitList.setString(2, holiday);
            insertWaitList.setTimestamp(3, currentTimestamp);
            insertWaitList.executeUpdate();          
        }catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        
    }
}
