package corp.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import corp.bean.AgendaVO;
import corp.conexion.MongoConexion;

public class MongoCrudAgendaDao {
	private static final Logger LOGGER = Logger.getLogger(MongoCrudAgendaDao.class);
	MongoConexion mongoConexion=null;
	List<AgendaVO> lista=null; 
	Gson gson=null;
	int respuesta=0;
	
	// consultar todos los elementos
	public List<AgendaVO> consultarTodos(String tabla) {
		LOGGER.info("MongoCrudDao.consultarTodos()");
		mongoConexion = new MongoConexion();
		gson = new Gson();
		lista = new ArrayList<AgendaVO>();
		try {
			if(mongoConexion.mongoDatabase()!=null) {
				
				MongoCollection<Document> mongoCollection =mongoConexion.mongoDatabase().getCollection(tabla);
				FindIterable<Document> findIterable = mongoCollection.find();
				MongoCursor<Document> mongoCursor =findIterable.iterator();
				while (mongoCursor.hasNext()) {
//					System.out.println(mongoCursor.next());
					Document doc =mongoCursor.next();
					AgendaVO agenda = gson.fromJson(doc.toJson(),AgendaVO.class);
					lista.add(agenda);
				}
			}else {
				LOGGER.error("error coleccion vacia ");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		LOGGER.info(lista.toString());
		return lista;
	}
	
	// obtener el ultimo id
	public int ultimoID() {
		LOGGER.info("MongoCrudAgendaDao.ultimoID()");
		mongoConexion = new MongoConexion();
		int id=0;
		try {
			if(mongoConexion.mongoDatabase()!=null) {
				
				MongoCollection<Document> mongoCollection =mongoConexion.mongoDatabase().getCollection("agenda");
				//FindIterable<Document> findIterable = mongoCollection.find().sort(new BasicDBObject("id",-1)).limit(1);
				FindIterable<Document> findIterable = mongoCollection.find().sort(new BasicDBObject("id",-1)).limit(1);
				MongoCursor<Document> mongoCursor =findIterable.iterator();
				while (mongoCursor.hasNext()) {
//					System.out.println(mongoCursor.next());
					id=mongoCursor.next(). getInteger("id")+1;
					//Document doc =mongoCursor.next();
					//AgendaVO agenda = gson.fromJson(doc.toJson(),AgendaVO.class);
					//lista.add(agenda);
					System.out.println("next id: "+id);
				}
			}else {
				LOGGER.error("error coleccion vacia ");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//LOGGER.info(lista.toString());
		return id;
	}
	
	//agregar elemento
	public int agregarElemento(String tabla,Document document) {
		LOGGER.info("MongoCrudAgendaDao.agregarElemento()");
		mongoConexion = new MongoConexion();
		int respuesta=0;
		try {
			MongoCollection<Document> mongoCollection=mongoConexion.mongoDatabase().getCollection(tabla);
			List<Document> documents = new ArrayList<Document>();
			documents.add(document);
			mongoCollection.insertMany(documents);
			respuesta=1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			respuesta=0;
		}
		
		return respuesta;
	}
	
	public int eliminaElementoId(int id) {
		LOGGER.info("MongoCrudAgendaDao.eliminaElementoId()");
		mongoConexion = new MongoConexion();
		lista = new ArrayList<AgendaVO>();
		 
		try {
			MongoCollection<Document> mongoCollection=mongoConexion.mongoDatabase().getCollection("agenda");
			//System.out.println("valor: "+mongoCollection.deleteOne(Filters.eq("id",id)));
			if (mongoCollection.deleteOne(Filters.eq("id",id)).getDeletedCount()==1) {
				respuesta=1;
				//lista=consultarTodos("agenda");
			} else {
				respuesta=0;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return respuesta;
	}
	// CONDULTAR ELEMENTO A ACTUALIZAR
	public AgendaVO consultaPorId(int id) {
		LOGGER.info("MongoCrudAgendaDao.consultaPorId()"+id);
		mongoConexion = new MongoConexion();
		lista = new ArrayList<AgendaVO>();
		Bson bson = new Document("id", id);
		AgendaVO agenda=null;
		String json1=null;
		Gson gson  = new Gson(); 
		try {
			if (mongoConexion.mongoDatabase()!=null) {
				MongoCollection<Document> mongoCollection=mongoConexion.mongoDatabase().getCollection("agenda");
				Document findIterable = mongoCollection.find().filter(bson).first();
				System.out.println("XX"+findIterable.toString());
				
				agenda = gson.fromJson(findIterable.toJson(),AgendaVO.class);
				lista.add(agenda);
								
			}else {
				LOGGER.error("error coleccion vacia ");
			}
			
			System.out.println("##"+lista.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return agenda;
	}
	
	
	public int actualizarAgenda(AgendaVO agendaVO) {
		LOGGER.info("MongoCrudAgendaDao.actualizarAgenda()"+agendaVO);
		mongoConexion = new MongoConexion();
		Document documentFilter= new Document();
		Document document= new Document();
		gson  = new Gson();
		String json=null;
		
		if (mongoConexion.mongoDatabase()!=null) {
			MongoCollection<Document> mongoCollection=mongoConexion.mongoDatabase().getCollection("agenda");
			json=gson.toJson(agendaVO);
			documentFilter.put("id", agendaVO.getId());
			document=Document.parse(json);
			mongoCollection.replaceOne(documentFilter, document);
			System.out.println("actualizacion correcta");
			respuesta=1;
		} else {
			System.out.println("actualizacion incorrecta");
			respuesta=0;
		}
		return respuesta;
	}
	
	
	
	
	
	/*
	public AgendaVO obtenerPorId() {
		LOGGER.info("MongoCrudAgendaDao.ultimoID()");
		mongoConexion = new MongoConexion();
		int id=0;
		Bson bson = new Document("id", 2);
		lista = new ArrayList<AgendaVO>();
		AgendaVO agenda=null;
		try {
			if(mongoConexion.mongoDatabase()!=null) {
				
				MongoCollection<Document> mongoCollection =mongoConexion.mongoDatabase().getCollection("agenda");
				FindIterable<Document> findIterable = mongoCollection.find().filter(bson);
				MongoCursor<Document> mongoCursor =findIterable.iterator();
				while (mongoCursor.hasNext()) {
					//System.out.println(mongoCursor.next());
					//id=mongoCursor.next(). getInteger("id")+1;
					Document doc =mongoCursor.next();
					agenda = gson.fromJson(doc.toJson(),AgendaVO.class);
					//lista.add(agenda);
					//System.out.println("next id: "+id);
				}
			}else {
				LOGGER.error("error coleccion vacia ");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//LOGGER.info(lista.toString());
		return agenda;
	}
	*/
}
