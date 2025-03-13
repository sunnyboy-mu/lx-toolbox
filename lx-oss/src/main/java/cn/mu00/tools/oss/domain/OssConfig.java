package cn.mu00.tools.oss.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("oss_config") // 对应数据库表名称
public class OssConfig implements Serializable { // 实现 Serializable 接口

    private static final long serialVersionUID = 1L; // 添加 serialVersionUID

    @TableId // 主键 ID，使用 MyBatis-Plus 注解
    private String id;  // 雪花算法生成的 ID

    private String bucket; // 存储桶

    private String account; // 操作员账户

    private String password; // 操作员密码

    private String cdnDomain; // 加速域名

    private String domainSuffix; // 网址后缀

    private String storageBasePath; // 存储根路径
}