package api.assignment;

import java.util.Comparator;

public class AgeComparator  implements Comparator<Person> {
	@Override
	public int compare(Person s1, Person s2) {
		return s2.getAge()-s1.getAge();
	}

}
