package cn.mu00.tools.oss.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("oss_bed_image")
public class BedImage implements Serializable {

    @TableId
    private String id;

    private String name;

    private String url;

    private String path;

    private Long size;

    /**
     * 分类
     * comic: 动漫, scenic: 风景
     */
    private String category;

    /**
     * 类别
     * pc: 电脑, phone: 手机, avatar: 头像
     */
    private String type;

}
