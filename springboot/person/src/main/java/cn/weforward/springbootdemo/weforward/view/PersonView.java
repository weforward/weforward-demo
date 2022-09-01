package cn.weforward.springbootdemo.weforward.view;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.springbootdemo.person.Person;

public class PersonView {

	protected Person person;

	public PersonView(Person person) {
		this.person = person;
	}

	public static PersonView valueOf(Person person) {
		return new PersonView(person);
	}

	@DocAttribute(description = "唯一id")
	public String getId() {
		return person.getId();
	}

	@DocAttribute(description = "名称")
	public String getName() {
		return person.getName();
	}

	@DocAttribute(description = "年龄")
	public int getAge() {
		return person.getAge();
	}

}
