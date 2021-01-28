package api.assignment;

public class Person {
private String name;
private Integer age;
private Integer jno;
@Override
public String toString() {
	return  name + " "+ age + " "+ jno ;
}
public String getName() {
	return name;
}
public Person(String name, int age, int jno) {
	super();
	this.name = name;
	this.age = age;
	this.jno = jno;
}
public void setName(String name) {
	this.name = name;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public Integer getJno() {
	return jno;
}
public void setJno(int jno) {
	this.jno = jno;
}

}
