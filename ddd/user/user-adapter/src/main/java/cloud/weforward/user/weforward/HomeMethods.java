package cloud.weforward.user.weforward;

import org.springframework.beans.factory.annotation.Autowired;

import cloud.weforward.user.command.UserCmdExe;
import cloud.weforward.user.dto.UserNameCountQuery;
import cloud.weforward.user.dto.UserNameCountResult;
import cloud.weforward.user.dto.param.UserAddParam;
import cloud.weforward.user.dto.view.UserView;
import cloud.weforward.user.service.UserManageService;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.protocol.doc.annotation.DocMethod;

/**
 * @author daibo
 * @date 2022/9/2 9:52
 */
@WeforwardMethods
public class HomeMethods {


    @Autowired
    protected UserCmdExe userCmdExe;

    @Autowired
    protected UserManageService userManageService;

    @DocMethod(description = "hello", index = 10)
    @WeforwardMethod
    public String hello() {
        return "HelloWorld!";
    }

    @DocMethod(description = "add", index = 20)
    @WeforwardMethod
    public UserView add(UserAddParam param) {
        return userManageService.add(param);
    }

    @DocMethod(description = "名称统计")
    @WeforwardMethod
    public UserNameCountResult nameCount(UserNameCountQuery query) {
        return userCmdExe.nameCount(query);
    }
}
