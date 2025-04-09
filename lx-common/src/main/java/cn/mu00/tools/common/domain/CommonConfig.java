package cn.mu00.tools.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("common_config") // 与数据库表名对应
public class CommonConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.NONE)
    private String configKey;

    private String configName;

    private String configValue;

    private String configDesc;

    /**
     * input | select | switch
     */
    private String configType;

    private String configPayload;

    private String configGroup;

}
