package hw5;

import java.util.Comparator;

public class DescendingDateComparator implements Comparator<Object> {
	public int compare(Object o1, Object o2) {
		Email e1 = (Email) o1;
		Email e2 = (Email) o2;
		if(e1.getTime().compareTo(e2.getTime())==0) {
			return 0;
		}
		else if(e1.getTime().compareTo(e2.getTime())<0) {
			return 1;
		}
		else {
			return -1;
		}
	}

}
