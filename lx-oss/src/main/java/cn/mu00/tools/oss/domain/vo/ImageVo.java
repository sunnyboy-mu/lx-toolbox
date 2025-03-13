package cn.mu00.tools.oss.domain.vo;

import cn.hutool.core.io.FileTypeUtil;
import lombok.Data;

@Data
public class ImageVo {
    private String name;
    private String url;
    private String filePath;
    private String fileType;

    public ImageVo(String name, String url, String filePath)  {
        this.name = name;
        this.url = url;
        this.filePath = filePath;
        this.fileType = FileTypeUtil.getType(filePath);
    }
}
