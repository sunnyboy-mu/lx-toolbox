package cn.mu00.tools.oss.constant;

public class FileRootPath {

    /**
     * 公共文件根路径
     */
    public static final String COMMON = "/common";

    /**
     * 博客文件根路径
     */
    public static final String BLOG = "/blog";

    /**
     * 图床文件根路径
     */
    public static final String BED = "/bed";

    // 私有构造函数，防止实例化
    private FileRootPath() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }
}
