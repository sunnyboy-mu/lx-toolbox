package cn.mu00.tools.oss.domain.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileInfoVo {

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件大小
     */
    private Long size;
}
