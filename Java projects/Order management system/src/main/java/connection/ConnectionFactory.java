package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;


// TODO: Auto-generated Javadoc
/**
 * A factory for creating Connection objects.
 */
public class ConnectionFactory {
	
	/** The Constant LOGGER. */
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
	
	/** The Constant DRIVER. */
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	/** The Constant DBURL. */
	private static final String DBURL = "jdbc:mysql://localhost:3306/assig3";
	
	/** The Constant USRE. */
	private static final String USRE = "root";
	
	/** The Constant PASS. */
	private static final String PASS = "root";
	
	/** The con. */
	private static Connection con;
	
	/** The single instance. */
	@SuppressWarnings("unused")
	private static ConnectionFactory singleInstance=new ConnectionFactory();
	
	/**
	 * Instantiates a new connection factory.
	 */
	private ConnectionFactory() {
		try {
			Class.forName(DRIVER);
			con=createConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new Connection object.
	 *
	 * @return the connection
	 */
	private Connection createConnection() {
		
			try {
				return  DriverManager.getConnection(DBURL,USRE,PASS);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public static Connection getConnection() {
		return con;
	}
	
	/**
	 * Close.
	 *
	 * @param connection the connection
	 */
	public static void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Close.
	 *
	 * @param statement the statement
	 */
	public static void close(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Close.
	 *
	 * @param resultSet the result set
	 */
	public static void close(ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
