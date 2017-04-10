package org.chenmin.auto.client.api;

public interface Verifier extends java.io.Serializable{
	
	public enum TypeName{
		FormData,HTML,Vals,Texts
	}
	
	boolean isMe(String url);
	
	TypeName type();
	
	String name();
	
	String sels();
	
	void setData(String data);
	
	String getData();
	
	boolean isValid(String orderID) throws VerifierException;

}
