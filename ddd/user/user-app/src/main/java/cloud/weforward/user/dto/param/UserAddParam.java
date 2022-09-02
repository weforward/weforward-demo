package cloud.weforward.user.dto.param;

import cn.weforward.protocol.doc.annotation.DocAttribute;

/**
 * @author daibo
 * @date 2022/9/2 14:04
 */
public class UserAddParam {

    @DocAttribute(description = "名称")
    protected String name;
    @DocAttribute(description = "年龄")
    protected int age;
    @DocAttribute(description = "手机号")
    protected String mobile;
    @DocAttribute(description = "邮箱")
    protected String email;
    @DocAttribute(description = "性别,1男,0女")
    protected int sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
