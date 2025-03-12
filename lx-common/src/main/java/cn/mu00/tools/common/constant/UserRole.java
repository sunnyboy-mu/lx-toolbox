package cn.mu00.tools.common.constant;

public class UserRole {
    public static final String ADMIN = "admin";
    public static final String USER = "user";

    // 私有构造函数，防止实例化
    private UserRole() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }
}