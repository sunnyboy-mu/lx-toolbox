package cn.mu00.tools.system.domain.vo;

import lombok.Data;

@Data
public class PasswordVo {
    private String oldPwd;

    private String newPwd;

    private String confirmPwd;
}
