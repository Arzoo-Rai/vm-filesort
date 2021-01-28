package api.assignment;

import java.util.Comparator;

public class JnoComparator implements Comparator<Person> {
	@Override
	public int compare(Person s1, Person s2) {
		return s1.getJno()-s2.getJno();
	}

}
