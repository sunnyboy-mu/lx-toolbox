package cn.mu00.tools.common.constant;

public class EntityStatus {
    public static final Integer NORMAL = 0;

    public static final Integer DISABLED = 1;

    // 私有构造函数，防止实例化
    private EntityStatus() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }
}