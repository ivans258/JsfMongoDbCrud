package corp.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.primefaces.context.RequestContext;

import corp.bean.AgendaVO;
import corp.conexion.MongoConexion;
import corp.dao.MongoCrudAgendaDao;

/**
 * 
 * @author Ivan Niño Jimenez
 * @version 1.0
 * @category view
 * @desc  vista de crud mongodb
 * 
 */

@ManagedBean
@ViewScoped
public class MongoViewAgenda {

	private static final Logger LOGGER = Logger.getLogger(MongoViewAgenda.class);
	
	private String selecTable;
	MongoCrudAgendaDao mongoCrudAgendaDao;
	static private List<AgendaVO> agenda;
	List<AgendaVO> agendaTem;
	private AgendaVO agendaObj;
	static private AgendaVO agendaVO; 
	
	// estatus de los paneles
	private boolean panelConsultar;
	private boolean panelAgregar;
	private boolean panelEliminar;
	private boolean panelActualizar;
	private boolean tableDatos;
	

	/* contenedores de bean*/
	static private int id;
	private String nombre;
	private String heroe;
	private String compania;
	private int estatus;
	
	private int respuesta;
	
	public void verPanelConsultar() {
		LOGGER.info("MongoViewAgenda.verPanelConsultar()");
		this.panelConsultar =true;
		this.panelAgregar 	=false;
		this.panelEliminar 	=false;
		this.panelActualizar=false;
		this.tableDatos		=false;
	}
	/* metodo que condulta todos los elementos de la tabla agenda*/
	public void consultaAgenda() {
		LOGGER.info("MongoViewAgenda.consultaAgenda()"+selecTable);
		mongoCrudAgendaDao = new MongoCrudAgendaDao();
		agenda=new ArrayList<AgendaVO>();
		try {
			agenda=mongoCrudAgendaDao.consultarTodos(selecTable);
			if (agenda!=null && !agenda.isEmpty()) {
				//activar paneles y botones
				this.tableDatos =true;
				this.panelConsultar =true;
				LOGGER.info(panelConsultar);
			} else {
				RequestContext.getCurrentInstance().execute("PF('digSinRegistro').show()");
				this.panelConsultar =false;
				this.tableDatos =false;
							}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void verPanelAgregar() {
		LOGGER.info("MongoViewAgenda.verPanelConsultar()");
		this.panelConsultar =false;
		this.panelAgregar 	=true;
		this.panelEliminar 	=false;
		this.panelActualizar=false;
		ultimoInsertado();
	}
	
//	metodo agregar elemento
	public void agregarElemento() {
		LOGGER.info("MongoViewAgenda.agregarElemento()");
		mongoCrudAgendaDao = new MongoCrudAgendaDao();
		LOGGER.info("ID: "+id);
		LOGGER.info("NOMBRE: "+nombre);
		LOGGER.info("HEROE: "+heroe);
		LOGGER.info("COMPANIA: "+compania);
		LOGGER.info("ESTATUS: "+estatus);
		if (id!=0 && nombre!=null && heroe!=null && compania!=null && estatus!=0) {
//			RequestContext.getCurrentInstance().execute("PF('diagOK').show()");
			Document document = new Document("id",id).append("nombre", nombre).append("heroe", heroe).append("compania", compania).append("estatus", estatus);
			respuesta=mongoCrudAgendaDao.agregarElemento("agenda", document);
			
			if (respuesta==1) {
				RequestContext.getCurrentInstance().execute("PF('diagOK').show()");
				this.panelConsultar =false;
				this.panelAgregar 	=true;
				this.panelEliminar 	=false;
				this.panelActualizar=false;
			}
		} else {
			RequestContext.getCurrentInstance().execute("PF('diagErrorGuadar').show()");
		}
		

	}


	public void ultimoInsertado() {
		LOGGER.info("MongoViewAgenda.ultimoInsertado()");
		mongoCrudAgendaDao = new MongoCrudAgendaDao();
		
		if(mongoCrudAgendaDao.ultimoID()!=0 ) {
			//setId(agendaDao.ultimoID());
			id=mongoCrudAgendaDao.ultimoID();
		}
		else {
			RequestContext.getCurrentInstance().execute("PF('diagNoId').show()");
		}
	}

	
	public void verPanelEliminar() {
		LOGGER.info("MongoViewAgenda.verPanelEliminar()");
		this.panelConsultar =false;
		this.panelAgregar 	=false;
		this.panelEliminar 	=true;
		this.panelActualizar=false;
	}
	
	
	public void eliminarElemento() {
		LOGGER.info("MongoViewAgenda.eliminarElemento()");
		LOGGER.info("id: "+id);
		mongoCrudAgendaDao = new MongoCrudAgendaDao();
		this.panelConsultar =false;
		this.panelAgregar 	=false;
		this.panelEliminar 	=true;
		this.panelActualizar=false;
		respuesta=mongoCrudAgendaDao.eliminaElementoId(id);
		if (respuesta==1) {
			RequestContext.getCurrentInstance().execute("PF('diagOkDelete').show()");
			id=0;
		} else {
			respuesta=0;
			id=0;
			RequestContext.getCurrentInstance().execute("PF('diagErrorDelete').show()");
		}
	}

	public void verPanelActualizar() {
		LOGGER.info("MongoViewAgenda.verPanelActualizar()");
		limpiar();
		this.panelConsultar =false;
		this.panelAgregar 	=false;
		this.panelEliminar 	=false;
		this.panelActualizar=true;
	}
	
	public void consultaPorId() {
		LOGGER.info("MongoViewAgenda.consultaPorId()");
		mongoCrudAgendaDao 	=new MongoCrudAgendaDao();
		agendaObj 	=new AgendaVO();
		agendaVO 	=new AgendaVO();
		try {
			agendaObj=mongoCrudAgendaDao.consultaPorId(respuesta);
			if (agendaObj!=null && agendaObj.toString().length()>0) {
				System.out.println("con datos: ");
				agendaVO.setId(agendaObj.getId());
				respuesta=agendaObj.getId();
				agendaVO.setNombre(agendaObj.getNombre());
				agendaVO.setHeroe(agendaObj.getHeroe());
				agendaVO.setCompania(agendaObj.getCompania());
				agendaVO.setEstatus(agendaObj.getEstatus());
				
				RequestContext.getCurrentInstance().execute("PF('diagActualizar').show()");
				System.out.println("si hay datos");
			} else {
				RequestContext.getCurrentInstance().execute("PF('diagErrorActualizar').show()");
				System.out.println("error al consultar datos");
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void obtenerForm() {
		LOGGER.info("MongoViewAgenda.obtenerForm()");
		LOGGER.info("datos: "+agendaVO.toString());
		
	}
	public void actualizarElemento() {
		LOGGER.info("MongoViewAgenda.actualizarElemento()");
		mongoCrudAgendaDao= new MongoCrudAgendaDao();
		this.panelActualizar=true;
		
		if(respuesta==id && mongoCrudAgendaDao.actualizarAgenda(agendaVO)==1) {
			LOGGER.info("son iguales"+agendaVO.toString());
			
			RequestContext.getCurrentInstance().execute("PF('diagOkActualizar').show()");
						
		}else {
			System.out.println("no son iguales");
			RequestContext.getCurrentInstance().execute("PF('diagErrorActualizar').show()");
		}
	}
	
	
	
	@PostConstruct
	private void init() {
		// TODO Auto-generated method stub
		LOGGER.info("MongoViewAgenda.init()");
		this.panelConsultar	=false;
		this.panelAgregar	=false;
		this.panelEliminar	=false;
		this.panelActualizar=false;
	}
	public void limpiar() {
		LOGGER.info("MongoViewAgenda.limpiar()");
		id=0;
		nombre="";
		heroe="";
		compania="";
		estatus=0;
	}

	// setter y getter
	
	public List<AgendaVO> getAgenda() {
		return agenda;
	}
	public void setAgenda(List<AgendaVO> agenda) {
		this.agenda = agenda;
	}


	public String getSelecTable() {
		return selecTable;
	}


	public void setSelecTable(String selecTable) {
		this.selecTable = selecTable;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getHeroe() {
		return heroe;
	}


	public void setHeroe(String heroe) {
		this.heroe = heroe;
	}


	public String getCompania() {
		return compania;
	}


	public void setCompania(String compania) {
		this.compania = compania;
	}


	public int getEstatus() {
		return estatus;
	}


	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	
	public boolean ispanelConsultar() {
		return panelConsultar;
	}
	public void setpanelConsultar(boolean panelConsultar) {
		this.panelConsultar = panelConsultar;
	}


	public boolean ispanelAgregar() {
		return panelAgregar;
	}


	public void setpanelAgregar(boolean panelAgregar) {
		this.panelAgregar = panelAgregar;
	}


	public boolean ispanelEliminar() {
		return panelEliminar;
	}


	public void setpanelEliminar(boolean panelEliminar) {
		this.panelEliminar = panelEliminar;
	}
	public boolean ispanelActualizar() {
		return panelActualizar;
	}
	public void setpanelActualizar(boolean panelActualizar) {
		this.panelActualizar = panelActualizar;
	}
	public int getrespuesta() {
		return respuesta;
	}
	public void setrespuesta(int respuesta) {
		this.respuesta = respuesta;
	}
	public List<AgendaVO> getAgendaTem() {
		return agendaTem;
	}
	public void setAgendaTem(List<AgendaVO> agendaTem) {
		this.agendaTem = agendaTem;
	}
	public boolean isTableDatos() {
		return tableDatos;
	}
	public void setTableDatos(boolean tableDatos) {
		this.tableDatos = tableDatos;
	}
	public AgendaVO getAgendaObj() {
		return agendaObj;
	}
	public void setAgendaObj(AgendaVO agendaObj) {
		this.agendaObj = agendaObj;
	}
	public AgendaVO getAgendaVO() {
		return agendaVO;
	}
	public void setAgendaVO(AgendaVO agendaVO) {
		this.agendaVO = agendaVO;
	}
	




}
