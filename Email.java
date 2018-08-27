package hw5;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * The Email class implements the variables and methods for a single Email object. 
 * 
 * 
 * @author Samuel Sundararaman 
 * 		e-mail: samuel.sundararaman@stonybrook.edu 
 * 		Stony Brook ID: 111352739
 */
public class Email implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String to; // Recipient of the email
	private String cc; // Carbon copy recipient of the email
	private String bcc; // Blind carbon copy recipient of the email
	private String subject; // Subject of the email
	private String body; // Body of the email
	private GregorianCalendar timestamp; // Time email was created 
	
	public Email(String t, String c, String b, String sub, String bod, GregorianCalendar time) {
		this.to = t;
		this.cc = c;
		this.bcc = b;
		this.subject = sub;
		this.body = bod;
		this.timestamp = time;
	}
	
	public String getTo() {
		return this.to;
	}
	
	public String getCC() {
		return this.cc;
	}
	
	public String getBCC() {
		return this.bcc;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public GregorianCalendar getTime() {
		return this.timestamp;
	}
	
	public void setTo(String t) {
		this.to = t;
	}
	
	public void setCC(String c) {
		this.cc = c;
	}
	
	public void setBCC(String bc) {
		this.bcc = bc;
	}
	
	public void setSubject(String sub) {
		this.subject = sub;
	}
	
	public void setBody(String bod) {
		this.body = bod;
	}
	
	public void setTime(GregorianCalendar time) {
		this.timestamp = time;
	}
	

}
