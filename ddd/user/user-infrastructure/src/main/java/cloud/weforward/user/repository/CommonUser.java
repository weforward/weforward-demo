package cloud.weforward.user.repository;

import cloud.weforward.user.domain.Sex;
import cloud.weforward.user.entity.User;
import cn.weforward.data.persister.BusinessDi;
import cn.weforward.data.persister.support.AbstractVoPersistent;

/**
 * @author daibo
 * @date 2022/9/2 14:42
 */
public class CommonUser extends AbstractVoPersistent<BusinessDi, User> {

    protected CommonUser(BusinessDi di) {
        super(di);
    }

    public CommonUser(BusinessDi di, String name) {
        super(di);
        User user = new User();
        user.setName(name);
        setVo(user);
        persistenceUpdateNow();
    }

    public void update(String name) {
        getVo().setName(name);
        markPersistenceUpdate();
    }


    public cloud.weforward.user.domain.User getDomain() {
        Sex sex;
        if (getVo().isMarks(User.MARK_MAN)) {
            sex = Sex.MAN;
        } else {
            sex = Sex.FEMALE;
        }
        return new cloud.weforward.user.domain.User(getVo().getId(), getVo().getName(), getVo().getAge(), getVo().getMobile(), getVo().getEmail(), sex);
    }
}
