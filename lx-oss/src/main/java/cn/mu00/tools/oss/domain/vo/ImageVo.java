package cn.mu00.tools.oss.domain.vo;

import lombok.Data;

@Data
public class ImageVo {
    private String name;
    private String url;
    private String filePath;
    private Long size;

    public ImageVo(String name, String url, String filePath, Long size) {
        this.name = name;
        this.url = url;
        this.filePath = filePath;
        this.size = size;
    }
}
