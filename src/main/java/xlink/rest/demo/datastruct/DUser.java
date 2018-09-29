package xlink.rest.demo.datastruct;

import java.io.Serializable;

public class DUser implements Serializable{

	private static final long serialVersionUID = -2257305677000965606L;
	
	/**
	 * 用户标识
	 */
	private String id;
	/**
	 * 用户名称
	 */
	private String name;
	
	public DUser(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DUser [id=" + id + ", name=" + name + "]";
	}
	
}
