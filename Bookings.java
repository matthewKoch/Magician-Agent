//Matthew Koch (mik5398)
package magicianagent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public class Bookings
{

    private int numBookings;
    private boolean isAvailableMagician;
    private final String DATABASE_URL = "jdbc:derby://localhost:1527/MagicianAgent";
    private final String USERNAME = "al";
    private final String PASSWORD = "al";
    private String magicianName;
    
    ResultSet resultSet;
    private Statement statement;
    private Connection connection;
    private PreparedStatement insertBooking;
    private PreparedStatement selectWaitlist;
    private PreparedStatement selectHoliday;
    private PreparedStatement selectMagicians;
    
    private String[] bookingsArray;
    private ArrayList<String> bookings;
    private ArrayList<String> magicians;
    private ArrayList<Object> results;
    private ArrayList<String> availableMagician;
    
    final String SELECT_CUSTOMER_QUERY = "SELECT CUSTOMER_NAME FROM CUSTOMERS";
    final String INSERT_QUERY = "INSERT INTO BOOKINGS(HOLIDAY, CUSTOMER, MAGICIANS) VALUES (?, ?, ?)";    
    final String SELECT_ALL_QUERY = "SELECT MAGICIAN FROM MAGICIANS";
    final String SELECT_MAGICIAN_QUERY = "select magician from Magicians where Magician not in(select Magicians from bookings where holiday = ?)";
    final private String SELECT_HOLIDAY_STATUS_QUERY = "SELECT CUSTOMER, MAGICIANS FROM BOOKINGS WHERE HOLIDAY = ?";
    final private String SELECT_WAITLIST_STATUS_QUERY = "SELECT * FROM WAITLIST";
    final private String SELECT_MAGICIAN_STATUS_QUERY = "SELECT CUSTOMER, HOLIDAY FROM BOOKINGS WHERE MAGICIANS = ?";

    public Bookings()
    {
        numBookings = 0;
        results = new ArrayList<>();
    }

    public void addBookingEntry(String holiday, String customerName, String magician)
    {
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            insertBooking = connection.prepareStatement(INSERT_QUERY);
            insertBooking.setString(1, holiday);
            insertBooking.setString(2, customerName);
            insertBooking.setString(3, magician);
            insertBooking.executeUpdate();
        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        numBookings++;
    }

    public int getNumBookings()
    {
        return numBookings;
    }

    public String[] getBookings()
    {
        bookingsArray = new String[numBookings];
        return bookingsArray;
    }

    public String getAvailableMagician(String holiday)
    {
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            selectMagicians = connection.prepareStatement(SELECT_MAGICIAN_QUERY);
            selectMagicians.setString(1, holiday);
            resultSet = selectMagicians.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            magicians = new ArrayList<>();

            while (resultSet.next())
            {
                magicians.add(resultSet.getString(1));
            }

        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return magicians.get(0);
    }

    public boolean getIsAvailableMagician(String holiday)
    {
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            selectMagicians = connection.prepareStatement(SELECT_MAGICIAN_QUERY);
            ResultSet resultSet;
            selectMagicians.setString(1, holiday);
            resultSet = selectMagicians.executeQuery();

            magicians = new ArrayList<>();

            while (resultSet.next())
            {
                magicians.add(resultSet.getString(1));
            }

        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        if (magicians.size() == 0)
        {
            return false;
        } else
        {
            return true;
        }
    }

    public ArrayList<Object> getHolidayStatus(String holiday)
    {
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            selectHoliday = connection.prepareStatement(SELECT_HOLIDAY_STATUS_QUERY);
            selectHoliday.setString(1, holiday);
            resultSet = selectHoliday.executeQuery();
            results = new ArrayList<>();
            
            while (resultSet.next())
            {
                results.add(resultSet.getString(1) + "          " + resultSet.getString(2));
            }

        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return results;
    }

    public ArrayList<Object> getMagicianStatus(String magician)
    {
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            selectMagicians = connection.prepareStatement(SELECT_MAGICIAN_STATUS_QUERY);
            selectMagicians.setString(1, magician);
            resultSet = selectMagicians.executeQuery();
            results = new ArrayList<>();

            while (resultSet.next())
            {
                results.add(resultSet.getString(1) + "                                       " + resultSet.getString(2));
            }

        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
    }

    public ArrayList<Object> getWaitlistStatus()
    {
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            selectWaitlist = connection.prepareStatement(SELECT_WAITLIST_STATUS_QUERY);
            resultSet = selectWaitlist.executeQuery();
            results = new ArrayList<>();

            while (resultSet.next())
            {
                results.add(resultSet.getString(1) + "                " +  resultSet.getString(2) + "                 " +  resultSet.getString(3));
            }

        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return results;
    }
}
