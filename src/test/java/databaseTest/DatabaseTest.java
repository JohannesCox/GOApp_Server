package databaseTest;



import java.io.File;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;


import Database.HibernateUtil;

public class DatabaseTest extends DBTestCase {
	private IDatabaseConnection connection;
	private SessionFactory factory;
	Session session;
	public DatabaseTest() {
		try {
		factory = HibernateUtil.getFactory();
		connection = new DatabaseConnection(factory.getSessionFactoryOptions()
				.getServiceRegistry().getService(ConnectionProvider.class).getConnection());
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql:C:/Users/Martin/Downloads/PSESoSe17Gruppe4");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "PSESoSe17User4");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "w5q8zurebuZ7vEpe");  
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		try {
			session = factory.openSession();
			DatabaseOperation.CLEAN_INSERT.execute(connection,this.getDataSet());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void tearDown() throws Exception {
		DatabaseOperation.DELETE_ALL.execute(connection, this.getDataSet());
		session.close();
	}


	@Override
	protected IDataSet getDataSet() throws Exception {
		// TODO Auto-generated method stub
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		 builder.setColumnSensing(true);
		 IDataSet dataSet = builder.build(new File("Dataset.xml"));
		 return dataSet;
	}




}
