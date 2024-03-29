package cloud.weforward.person.domain;

import cn.weforward.common.ResultPage;

public interface PersonService {

	Person create(String name);

	Person tryCreate(String name);

	ResultPage<? extends Person> search(String name);

}
