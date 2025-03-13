package cn.mu00.tools.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_user") // 与数据库表名对应
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId // 使用 MyBatis-Plus 的注解标识为主键
    private String id; // 雪花算法生成的 ID

    private String username;
    private String password;
    private String avatar;
    private String nickname;

    private String code;

    private String tags;

    private String role; // 角色，使用枚举或字符串

    private Integer status; // 0: 启用, 1: 禁用

    @TableLogic
    private Integer isDeleted; // 0: 正常, 1: 已删除

    @JsonFormat(pattern = "yyyy-MM-dd") // 使用 FastJSON 进行日期格式化
    private Date createdAt; // 创建时间

    private String createdBy; // 创建人

    @JsonFormat(pattern = "yyyy-MM-dd") // 使用 FastJSON 进行日期格式化
    private Date updatedAt; // 更新时间

    private String updatedBy; // 更新人
}