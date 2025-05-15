package cn.mu00.tools.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;

public class FileUtil {


    /**
     * 文件拓展名
     */
    public static String fileExt(String fileName) {
        return FileNameUtil.getSuffix(fileName);
    }

    /**
     * 日期目录
     */
    public static String dateDir(String fileName) {
        return DateUtil.format(DateUtil.date(), "/yyyy/MM/dd/") + fileExt(fileName);
    }

    /**
     * 时间戳文件名
     */
    public static String timestampFileName(String fileName) {
        return "/" + DateUtil.date().getTime() + fileExt(fileName);
    }

    /**
     * 是否是图片 jpg png webp jpeg gif
     */
    public static boolean isImage(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".jpeg") || fileName.endsWith(".webp") || fileName.endsWith(".gif");
    }
}
