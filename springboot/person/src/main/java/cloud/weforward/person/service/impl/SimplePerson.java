package cloud.weforward.person.service.impl;

import java.util.Random;

import cn.weforward.data.persister.support.AbstractVoPersistent;
import cloud.weforward.person.entity.OnePerson;
import cloud.weforward.person.service.Person;
import cloud.weforward.person.service.di.PersonDi;

public class SimplePerson extends AbstractVoPersistent<PersonDi, OnePerson> implements Person {

	protected SimplePerson(PersonDi di) {
		super(di);
	}

	public SimplePerson(PersonDi di, String name) {
		super(di);
		initPersistenceId();
		OnePerson vo = new OnePerson();
		vo.setId(getPersistenceId().getOrdinal());
		vo.setName(name);
		vo.setAge(new Random().nextInt(100));
		setVo(vo);
		persistenceUpdateNow();
	}

	@Override
	public String getId() {
		return getPersistenceId().getOrdinal();
	}

	@Override
	public String getName() {
		return getVo().getName();
	}

	@Override
	public int getAge() {
		return getVo().getAge();
	}

}
