//Matthew Koch (mik5398)
package magicianagent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public class Customers
{

    private ArrayList<String> customers;
    final String DATABASE_URL = "jdbc:derby://localhost:1527/MagicianAgent";
    final String USERNAME = "al";
    final String PASSWORD = "al";
    final String SELECT_QUERY = "SELECT CUSTOMER_NAME FROM CUSTOMER";
    final String INSERT_QUERY = "INSERT INTO CUSTOMER(CUSTOMER_NAME) VALUES (?)";
    private Connection connection;
    private PreparedStatement selectCustomers;
    private PreparedStatement insertCustomer;

    public Customers()
    {

    }

    public void addCustomer(String customerName)
    {
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            insertCustomer = connection.prepareStatement(INSERT_QUERY);
            insertCustomer.setString(1, customerName);
            insertCustomer.executeUpdate();          
        }catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public boolean getIsNewCustomer(String customerName, ArrayList<String> customers)
    {       
        boolean isNewCustomer = true;
        
        for(int i = 0; i < customers.size(); i++)
        {            
            if(customerName.equals(customers.get(i)))
            {
                isNewCustomer = false;
            }
            
            i++;
        }
        
        return isNewCustomer;
    }

    public ArrayList<String> getCustomers()
    {

        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            selectCustomers = connection.prepareStatement(SELECT_QUERY);
            ResultSet resultSet;
            resultSet = selectCustomers.executeQuery();

            customers = new ArrayList<>();
            
            while (resultSet.next())
            {
                customers.add(resultSet.getString(1));
            }

        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }

        return customers;
    }

}
