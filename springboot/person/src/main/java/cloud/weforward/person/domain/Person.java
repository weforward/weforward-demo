package cloud.weforward.person.domain;

import java.util.Random;

import javax.annotation.Resource;

import cn.weforward.data.persister.support.AbstractPersistent;
import cloud.weforward.person.domain.di.PersonDi;

public class Person extends AbstractPersistent<PersonDi> {

	@Resource
	protected String m_Name;
	@Resource
	protected int m_Age;

	protected Person(PersonDi di) {
		super(di);
	}

	public Person(PersonDi di, String name) {
		super(di);
		initPersistenceId();
		m_Name = name;
		m_Age = new Random().nextInt(100);
		persistenceUpdateNow();
	}

	public String getId() {
		return getPersistenceId().getOrdinal();
	}

	public String getName() {
		return m_Name;
	}

	public int getAge() {
		return m_Age;
	}

}
