package hw5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Folder class implements the variables and methods to store and manipulate a folder of Email objects. 
 * 
 * 
 * @author Samuel Sundararaman 
 * 		e-mail: samuel.sundararaman@stonybrook.edu 
 * 		Stony Brook ID: 111352739
 */
public class Folder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Email> emails; // List of emails in this folder
	private String name; // Name of this folder 
	private String currentSortingMethod; // Current sorting method used for this folder  
	
	public Folder(String n) {
		emails = new ArrayList<Email>();
		this.name = n;
		currentSortingMethod = "datedescending";
	}
	
	public int getSize() {
		return this.emails.size();
	}
	
	public Email getEmail(int index) {
		return emails.get(index);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSort() {
		return this.currentSortingMethod;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public void setSort(String sort) {
		this.currentSortingMethod = sort;
	}
	public void addEmail(Email email) {
		emails.add(email);
	}
	
	public Email removeEmail(int index) {
		return emails.remove(index);
	}
	
	public void sortBySubjectAscending() {
		Collections.sort(emails, new AscendingSubjectComparator());
	}
	
	public void sortBySubjectDescending() {
		Collections.sort(emails, new DescendingSubjectComparator());
	}
	
	public void sortByDateAscending() {
		Collections.sort(emails, new AscendingDateComparator());
	}
	
	public void sortByDateDescending() {
		Collections.sort(emails, new DescendingDateComparator());
	}
	
	

}
