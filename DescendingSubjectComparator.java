package hw5;

import java.util.Comparator;

public class DescendingSubjectComparator implements Comparator<Object>{
	public int compare(Object o1, Object o2) {
		Email e1 = (Email) o1;
		Email e2 = (Email) o2;
		if(e1.getSubject().compareTo(e2.getSubject())==0) {
			return 0;
		}
		else if(e1.getSubject().compareTo(e1.getSubject())<0) {
			return 1;
		}
		else {
			return -1;
		}
	}
}
