package databaseTest;



import java.io.File;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Database.HibernateUtil;

/**
 * This class configures the database(-connection) for the test cases.
 */
public abstract class DatabaseTest extends DBTestCase {
	
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseTest.class);  
	  
    private static SessionFactory sessionFactory;  
    protected Session session; 
 
	public DatabaseTest() {
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:mem:PSESoSe17Gruppe4");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "PSESoSe17User4");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "w5q8zurebuZ7vEpe");  
	}
	
	@Before
	protected void setUp() throws Exception {
		HSQLServerUtil.getInstance().start("PSESoSe17Gruppe4");  
		  
        LOG.info("Loading hibernate...");  
        if (sessionFactory == null) {  
            sessionFactory = HibernateUtil.getFactory();  
        }  
  
        session = sessionFactory.openSession();
        
        super.setUp();  
		
	}
	
	@After  
    public void tearDown() throws Exception {  
        session.close();  
        super.tearDown();  
        HSQLServerUtil.getInstance().stop();  
    }  
	



	@Override
	protected IDataSet getDataSet() throws Exception {
		// TODO Auto-generated method stub
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		 builder.setColumnSensing(true);
		 IDataSet dataSet = builder.build(new File("src/test/resources/Dataset.xml"));
		 return dataSet;
	}
	@Before
	protected DatabaseOperation getSetUpOperation() throws Exception {  
        return DatabaseOperation.REFRESH;  
    }  
  
    @After
    protected DatabaseOperation getTearDownOperation() throws Exception {  
        return DatabaseOperation.NONE;  
    }  
	
	




}
