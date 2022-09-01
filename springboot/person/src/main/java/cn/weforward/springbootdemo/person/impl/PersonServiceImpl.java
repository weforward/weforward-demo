package cn.weforward.springbootdemo.person.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.weforward.common.ResultPage;
import cn.weforward.data.persister.Persistent;
import cn.weforward.data.persister.Persister;
import cn.weforward.data.persister.PersisterFactory;
import cn.weforward.data.persister.ext.ConditionUtil;
import cn.weforward.springbootdemo.person.Person;
import cn.weforward.springbootdemo.person.PersonService;
import cn.weforward.springbootdemo.person.di.PersonDi;

@Service
public class PersonServiceImpl implements PersonDi, PersonService {

	protected final PersisterFactory m_Factory;

	protected final Persister<SimplePerson> m_PsPerson;

	public PersonServiceImpl(PersisterFactory factory) {
		m_Factory = factory;
		m_PsPerson = m_Factory.createPersister(SimplePerson.class, this);
	}

	@Override
	public Person create(String name) {
		return new SimplePerson(this, name);
	}

	@Transactional
	@Override
	public Person tryCreate(String name) {
		SimplePerson p = new SimplePerson(this, name);
		if (p.getAge() % 2 == 0) {
			throw new RuntimeException("中奖了，创建不成功");
		}
		return p;
	}

	@Override
	public ResultPage<? extends Person> search(String name) {
		if (cn.weforward.common.util.StringUtil.isEmpty(name)) {
			return m_PsPerson.search(null);
		}
		return m_PsPerson.search(ConditionUtil.like("name", name));
	}

	@Override
	public <E extends Persistent> Persister<E> getPersister(Class<E> clazz) {
		return m_Factory.getPersister(clazz);
	}

}
