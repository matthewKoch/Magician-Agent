//Matthew Koch (mik5398)
package magicianagent;

import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author Matt
 */
public class Holidays
{

    private  ArrayList<String> holidays;
    final String DATABASE_URL = "jdbc:derby://localhost:1527/MagicianAgent";
    final String SELECT_QUERY = "SELECT HOLIDAY_NAME FROM HOLIDAY";
    final String USERNAME = "al";
    final String PASSWORD = "al";
    private String holidayName;
    private Connection connection;
    private Statement statement;
    private PreparedStatement selectHolidays;
    private PreparedStatement insertHoliday;
    private int rowCount;
    
    public Holidays()
    {        
        holidays = new ArrayList<>();   
    }

    
    public ArrayList<String> getHolidays()
    {
                
        try{
        connection = DriverManager.getConnection(DATABASE_URL, "al", "al");   
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        selectHolidays = connection.prepareStatement(SELECT_QUERY);
        ResultSet resultSet;
        resultSet = selectHolidays.executeQuery();

        while(resultSet.next())
        {
            holidayName = resultSet.getString(1);
            holidays.add(holidayName);
        }
                          
        }catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }  
        return holidays;
    }
}
