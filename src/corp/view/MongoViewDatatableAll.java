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
import corp.dao.MongoCrudAgendaDao;

@ManagedBean
@ViewScoped
public class MongoViewDatatableAll {
	private static final Logger LOGGER = Logger.getLogger(MongoViewDatatableAll.class);

	MongoCrudAgendaDao mongoCrudAgendaDao;
	static private List<AgendaVO> agenda;  // sepone static para mantener la informacion
	static private String selecTable;			
	
	static private AgendaVO objSelect;				// contenedor para retonar la informacion seleccionada
	private AgendaVO agendaVO;
	// panel de datatable
	private boolean panelDataTable;
	private boolean tableDatos;
	
	private int respuesta;				// respuesta a metodos int

	/* contenedores de bean*/
	static private int id;
	private String nombre;
	private String heroe;
	private String compania;
	private int estatus;
	
	
@PostConstruct
public void init() {
	LOGGER.info("MongoViewDatatableAll.init()");
	this.panelDataTable=true;
	this.tableDatos=false;
//	limpiar();
	LOGGER.info("valor"+selecTable);
}

/* metodo que condulta todos los elementos de la tabla agenda*/
public void consultaAgenda() {
	LOGGER.info("MongoViewDatatableAll.consultaAgenda()");
	mongoCrudAgendaDao = new MongoCrudAgendaDao();
	agenda=new ArrayList<AgendaVO>();
	try {
		agenda=mongoCrudAgendaDao.consultarTodos(selecTable);
		if (agenda!=null && !agenda.isEmpty()) {
			//activar paneles y botones
			//this.panelDataTable =true;
			this.tableDatos =true;
			LOGGER.info(panelDataTable);
		} else {
			RequestContext.getCurrentInstance().execute("PF('digSinRegistro').show()");
			this.panelDataTable =false;
			this.tableDatos =false;
						}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}

public void obtenerIdNext() {
	LOGGER.info("MongoViewDatatableAll.obtenerIdNext() : ");
	
	mongoCrudAgendaDao = new MongoCrudAgendaDao();
	
	if (mongoCrudAgendaDao.ultimoID()>0) {
		id=mongoCrudAgendaDao.ultimoID();
	} else {
		RequestContext.getCurrentInstance().execute("PF('diagNoId').show()");
	}
	LOGGER.info("## "+id);
}

public void agregarElemento() {
	LOGGER.info("MongoViewDatatableAll.agregarElemento() : "+selecTable+" id: "+id);
	mongoCrudAgendaDao = new MongoCrudAgendaDao();
	LOGGER.info("ID: "+id);
	LOGGER.info("NOMBRE: "+nombre);
	LOGGER.info("HEROE: "+heroe);
	LOGGER.info("COMPANIA: "+compania);
	LOGGER.info("ESTATUS: "+estatus);
	
	if (id!=0 && nombre!=null && heroe!=null && compania!=null && estatus!=0) {
//		RequestContext.getCurrentInstance().execute("PF('diagOK').show()");
		Document document = new Document("id",id).append("nombre", nombre).append("heroe", heroe).append("compania", compania).append("estatus", estatus);
		respuesta=mongoCrudAgendaDao.agregarElemento("agenda", document);
		
		if (respuesta==1) {
			RequestContext.getCurrentInstance().execute("PF('diagOK').show()");
			consultaAgenda();
		}
	} else {
		RequestContext.getCurrentInstance().execute("PF('diagErrorGuadar').show()");
	}
	
}

public void limpiar() {
	LOGGER.info("MongoViewDatatableAll.limpiar()");
	id=0;
	nombre="";
	heroe="";
	compania="";
	estatus=0;
}


public void verSelect() {
	LOGGER.info("MongoViewDatatableAll.verSelect()");

}

// actualizar elemento
public void actualizar() {
	LOGGER.info("MongoViewDatatableAll.actualizar()"+ selecTable);
	//agendaVO = new AgendaVO();
	
	mongoCrudAgendaDao = new MongoCrudAgendaDao();
	List<AgendaVO> listaNueva = new ArrayList<AgendaVO>();
	
	if(objSelect!=null && objSelect.toString().length()>0 && mongoCrudAgendaDao.actualizarAgenda(objSelect)==1) {
		RequestContext.getCurrentInstance().execute("PF('diagOkActualizar').show()");
		consultaAgenda();
		
	}else {
		RequestContext.getCurrentInstance().execute("PF('diagErrorActualizar').show()");	
	}
	LOGGER.info("agendaVO: "+objSelect.toString());
}



//eliminar Elemento
public void eliminaElemento() {
	LOGGER.info("MongoViewDatatableAll.eliminaElemento()");
	mongoCrudAgendaDao = new MongoCrudAgendaDao();
	if(objSelect!=null && objSelect.toString().length()>0 && mongoCrudAgendaDao.eliminaElementoId(objSelect.getId())==1) {
		RequestContext.getCurrentInstance().execute("PF('diagOkElimina').show()");
		consultaAgenda();
		
	}else {
		RequestContext.getCurrentInstance().execute("PF('diagErrorElimina').show()");	
	}
}



//setter y getter 

public String getSelecTable() {
	return selecTable;
}

public void setSelecTable(String selecTable) {
	this.selecTable = selecTable;
}

public boolean isPanelDataTable() {
	return panelDataTable;
}

public void setPanelDataTable(boolean panelDataTable) {
	this.panelDataTable = panelDataTable;
}

public boolean isTableDatos() {
	return tableDatos;
}

public void setTableDatos(boolean tableDatos) {
	this.tableDatos = tableDatos;
}

public List<AgendaVO> getAgenda() {
	return agenda;
}

public void setAgenda(List<AgendaVO> agenda) {
	this.agenda = agenda;
}

public AgendaVO getObjSelect() {
	return objSelect;
}

public void setObjSelect(AgendaVO objSelect) {
	this.objSelect = objSelect;
}

public AgendaVO getAgendaVO() {
	return agendaVO;
}

public void setAgendaVO(AgendaVO agendaVO) {
	this.agendaVO = agendaVO;
}

public int getRespuesta() {
	return respuesta;
}

public void setRespuesta(int respuesta) {
	this.respuesta = respuesta;
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


}
