package cloud.weforward.user.domain;

/**
 * @author daibo
 * @date 2022/9/2 13:52
 */
public enum Sex {

    MAN(1, "男"), FEMALE(0, "女");
    final int code;
    final String name;


    Sex(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Sex findById(int sex) {
        for (Sex s : values()) {
            if (s.ordinal() == sex) {
                return s;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


}


