package ca.myseneca.model;

public class Department implements java.io.Serializable {
	/**
	 * @author Laurie Shields (034448142), Mark Lindan (063336143)
	 * 
	 * POJO Java Bean: Holds ID/name pairs for departments
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	
	/**
	 * Default constructor
	 */
	public Department() { }
	
	/**
	 * Full constructor
	 * 
	 * @param id department id to set
	 * @param name department name to set
	 */
	public Department(int id, String name) {
		this.setId(id);
		this.setName(name);
	}
	
	/**
	 * @return the department id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the department id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the department name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the department name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


}
