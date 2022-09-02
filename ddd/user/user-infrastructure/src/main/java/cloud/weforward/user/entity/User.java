package cloud.weforward.user.entity;

import cn.weforward.data.mybatisplus.support.MyBatisPlusBusinessVo;

/**
 * <p>
 *
 * </p>
 *
 * @author daibo
 * @since 2022-09-02 13:42:16
 */
public class User extends MyBatisPlusBusinessVo {

    public static final long MARK_MAN = 1L;
    private String name;

    private Integer age;

    private String mobile;

    private String email;

    private Long marks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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

    public Long getMarks() {
        return marks;
    }

    public void setMarks(Long marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "User{" +
                "name=" + name +
                ", age=" + age +
                ", email=" + email +
                ", marks=" + marks +
                "}";
    }

    public boolean isMarks(long mark) {
        return (this.marks | mark) == mark;
    }
}
