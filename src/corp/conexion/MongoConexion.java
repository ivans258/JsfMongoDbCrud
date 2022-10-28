package corp.conexion;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConexion {

	String url="localhost";
	String base="CORP";
	int puerto=27017;
	
	MongoDatabase mongoDatabase;
	static MongoClient  mongoClient=null;
	
	private static final Logger LOGGER = Logger.getLogger(MongoConexion.class); 
	
	@SuppressWarnings("deprecation")
	public MongoDatabase mongoDatabase() {
		LOGGER.info("MongoConexion.mongoDatabase()");
		try {
			mongoClient = new MongoClient(url, puerto);
			mongoDatabase=mongoClient.getDatabase(base);
			List<String> lista = new ArrayList<String>();
			lista=mongoClient.getDatabaseNames();
			System.out.println(lista);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			
		return mongoDatabase;
	}
	
	
}
