package org.chenmin.auto.shared;

import java.util.Date;

public class PassengerWG implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1567159932119332534L;
	
	private String orderID;
	private String firstname;
	private String lastname;
	private String sexy;
	private String type;
	private String credentialsname;
	private String credentialsID;
	private String nationality;
	private Date birthday;
	private Date expirydate;
	private String credentialsnationality;
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getSexy() {
		return sexy;
	}
	public void setSexy(String sexy) {
		this.sexy = sexy;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCredentialsname() {
		return credentialsname;
	}
	public void setCredentialsname(String credentialsname) {
		this.credentialsname = credentialsname;
	}
	public String getCredentialsID() {
		return credentialsID;
	}
	public void setCredentialsID(String credentialsID) {
		this.credentialsID = credentialsID;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}
	public String getCredentialsnationality() {
		return credentialsnationality;
	}
	public void setCredentialsnationality(String credentialsnationality) {
		this.credentialsnationality = credentialsnationality;
	}
	@Override
	public String toString() {
		return "PassengerWG [orderID=" + orderID + ", firstname=" + firstname + ", lastname=" + lastname + ", sexy="
				+ sexy + ", type=" + type + ", credentialsname=" + credentialsname + ", credentialsID=" + credentialsID
				+ ", nationality=" + nationality + ", birthday=" + birthday + ", expirydate=" + expirydate
				+ ", credentialsnationality=" + credentialsnationality + "]";
	}
	

}
