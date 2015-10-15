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
public class Mage
{

    private ArrayList<String> magicians;
    private String magicianName;
    final String DATABASE_URL = "jdbc:derby://localhost:1527/MagicianAgent";
    final String USERNAME = "AL";
    final String PASSWORD = "AL";
    final String SELECT_ALL_QUERY = "SELECT MAGICIAN FROM MAGICIANS";
    final String SELECT_QUERY = "select magician from Magicians where Magician not in(select Magicians from bookings where holiday = ?)";
    private ArrayList<String> availableMagician;
    private Connection connection;
    private PreparedStatement selectMagicians;
    private Statement statement;
    private boolean isAvailableMagician;

    public Mage()
    {
        magicians = new ArrayList<>();
    }
    
    public ArrayList<String> getMagicians()
    {

        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, "al", "al");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            selectMagicians = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet resultSet;
            resultSet = selectMagicians.executeQuery();

            while (resultSet.next())
            {
                magicianName = resultSet.getString(1);
                magicians.add(magicianName);
            }

        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }

        return magicians;
    }
}
