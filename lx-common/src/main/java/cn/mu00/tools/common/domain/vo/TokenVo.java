package cn.mu00.tools.common.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // 雪花算法生成的 ID

    private String username;

    private String avatar;

    private String nickname;

    private String tags;

    private String role; // 角色，使用枚举或字符串

    private Integer status; // 0: 启用, 1: 禁用

    private String token;
}
