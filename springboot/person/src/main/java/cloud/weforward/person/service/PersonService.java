package cloud.weforward.person.service;

import cn.weforward.common.ResultPage;

public interface PersonService {

	Person create(String name);

	Person tryCreate(String name);

	ResultPage<? extends Person> search(String name);

}
