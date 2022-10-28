package corp.bean;

/**
 * @author ininojim
 *
 */
public class AgendaVO {
	private int id;
	private String nombre;
	private String heroe;
	private String compania;
	private int estatus;
	
	public AgendaVO() {
		// TODO Auto-generated constructor stub
	}

	
	public AgendaVO(int id, String nombre, String heroe, String compania,
			int estatus) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.heroe = heroe;
		this.compania = compania;
		this.estatus = estatus;
	}



	@Override
	public String toString() {
		return "AgendaVO [id=" + id + ", nombre=" + nombre + ", heroe=" + heroe
				+ ", compania=" + compania + ", estatus=" + estatus + "]";
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
