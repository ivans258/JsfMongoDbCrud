package corp.main;

import java.util.ArrayList;
import java.util.List;

import corp.bean.AgendaVO;
import corp.dao.MongoCrudAgendaDao;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int response=0;
		MongoCrudAgendaDao mongoCrudAgendaDao = new MongoCrudAgendaDao();
		List<AgendaVO> lista = new ArrayList<AgendaVO>();
		List<AgendaVO> agenda = new ArrayList<AgendaVO>();
		AgendaVO agendaVO = new AgendaVO();
		
		mongoCrudAgendaDao.consultarTodos("agenda");
		
		
//		int id=crudDao.ultimoID();
//		System.out.println("App: "+id);
		
//		response=crudDao.eliminaElementoId(4); 
//		System.out.println(response);
		
		//String respuesta=mongoCrudAgendaDao.consultaPorId(2);
		//System.out.println("DD: "+respuesta);
		
		/*
		AgendaVO respuesta= new AgendaVO();
			respuesta=mongoCrudAgendaDao.consultaPorId(2);
		
		if (respuesta!=null) {
			System.out.println("tiene informacion"+respuesta.getHeroe());
		} else {
			System.out.println("no tiene informacion");
		}
		*/
		
		/*
		agendaVO.setId(2);
		agendaVO.setNombre("test");
		agendaVO.setHeroe("test hero");
		agendaVO.setCompania("corp");
		agendaVO.setEstatus(2);
		
		response=mongoCrudAgendaDao.actualizarAgenda(agendaVO);
		System.out.println("respuesta: "+response);
		*/
	}

}
