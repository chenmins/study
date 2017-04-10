package org.chenmin.auto.client.api;

public interface Verifier extends java.io.Serializable{
	
	public enum TypeName{
		FormData,HTML,Vals,Texts
	}
	
	boolean isMe(String url);
	
	VerifierBean[] getVerifierBean();

	void setVerifierBean(VerifierBean[] verifierBean);
	
	boolean isValid() throws VerifierException;

}
