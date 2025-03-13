package cn.mu00.tools.oss.utils;

import cn.hutool.core.date.DateUtil;

public class FileUtil {

    /**
     * 日期目录
     */
    public static String dateDir() {
        return DateUtil.format(DateUtil.date(), "/yyyy/MM/dd/");
    }

    /**
     * 时间戳文件名
     */
    public static String timestampFileName() {
        return String.valueOf(DateUtil.date().getTime());
    }

    /**
     * 是否是图片 jpg png webp jpeg gif
     */
    public static boolean isImage(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".jpeg") || fileName.endsWith(".webp") || fileName.endsWith(".gif");
    }
}
