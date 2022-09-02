package cloud.weforward.user.repository.impl;

import org.springframework.stereotype.Component;

import cloud.weforward.user.domain.User;
import cloud.weforward.user.domain.store.UserRepository;
import cloud.weforward.user.repository.CommonUser;
import cn.weforward.data.persister.BusinessDi;
import cn.weforward.data.persister.Persistent;
import cn.weforward.data.persister.Persister;
import cn.weforward.data.persister.PersisterFactory;

/**
 * @author daibo
 * @date 2022/9/2 14:15
 */
@Component
public class UserRepositoryImpl implements UserRepository, BusinessDi {

    protected PersisterFactory persisterFactory;

    final Persister<CommonUser> m_PsUser;

    public UserRepositoryImpl(PersisterFactory persisterFactory) {
        this.persisterFactory = persisterFactory;
        m_PsUser = persisterFactory.createPersister(CommonUser.class, this);
    }

    @Override
    public void update(User user) {
        if (null == user.getId()) {
            new CommonUser(this, user.getName());
        } else {
            CommonUser cuser = m_PsUser.get(user.getId());
            cuser.update(user.getName());
        }
    }

    @Override
    public User get(String id) {
        CommonUser cuser = m_PsUser.get(id);
        return null==cuser?null: cuser.getDomain();
    }

    @Override
    public <E extends Persistent> Persister<E> getPersister(Class<E> clazz) {
        return persisterFactory.getPersister(clazz);
    }
}
