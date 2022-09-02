package cloud.weforward.user.weforward;

import org.springframework.beans.factory.annotation.Autowired;

import cloud.weforward.user.api.UserService;
import cloud.weforward.user.dto.UserNameCountQuery;
import cloud.weforward.user.dto.UserNameCountResult;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.protocol.doc.annotation.DocMethod;

/**
 * @author daibo
 * @date 2022/9/2 9:52
 */
@WeforwardMethods
public class UserServiceMethods {

    @Autowired
    protected UserService userService;

    @DocMethod(description = "hello")
    @WeforwardMethod
    public String hello() {
        return "HelloWorld!";
    }

    @DocMethod(description = "名称统计")
    @WeforwardMethod
    public UserNameCountResult nameCount(UserNameCountQuery query) {
        return userService.nameCount(query);
    }
}
