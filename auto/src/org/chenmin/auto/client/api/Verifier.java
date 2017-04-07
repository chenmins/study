package org.chenmin.auto.client.api;

public interface Verifier extends java.io.Serializable{
	
	enum TypeName{
		FormData,HTML,Vals,Texts
	}
	
	TypeName type();
	
	String name();
	
	String sels();
	
	void setData(String data);
	
	String getData();
	
	boolean isValid(String orderID) throws VerifierException;

}
