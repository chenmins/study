package org.chenmin.auto.client.api;

import org.chenmin.auto.client.api.Verifier.TypeName;

public class VerifierBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1569159932119332534L;
	private String name;
	private String sel;
	private TypeName type;
	private String data = new String();
	
	public VerifierBean(String name, String sel, TypeName type) {
		super();
		this.name = name;
		this.sel = sel;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSel() {
		return sel;
	}
	public void setSel(String sel) {
		this.sel = sel;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public TypeName getType() {
		return type;
	}
	public void setType(TypeName type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "VerifierBean [name=" + name + ", sel=" + sel + ", type=" + type + ", data=" + data + "]";
	}
	
}
