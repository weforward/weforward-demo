package cloud.weforward.user.domain;

import java.util.Objects;

/**
 * 用户
 *
 * @author daibo
 * @date 2022/9/2 13:50
 */
public class User {

    protected String id;

    protected String name;

    protected int age;

    protected String mobile;

    protected String email;

    protected Sex sex;

    public User(String id, String name, int age, String mobile, String email, Sex sex) {
        Objects.requireNonNull(name, "名称不能为空");
        Objects.requireNonNull(email, "邮箱不能为空");
        Objects.requireNonNull(mobile, "手机不能为空");
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("年龄不合法");
        }
        if (email.indexOf('@') == -1) {
            Objects.requireNonNull(email, "邮箱不合法");
        }
        if (mobile.length() != 11) {
            Objects.requireNonNull(mobile, "手机不合法");
        }
        Objects.requireNonNull(sex, "性别不能为空");
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Sex getSex() {
        return sex;
    }
}
