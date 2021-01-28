package api.assignment;

import java.util.Comparator;
public class StringComparator implements Comparator<Person> {
	@Override
	public int compare(Person s1, Person s2) {
		return s1.getName().compareToIgnoreCase(s2.getName());
	}
}