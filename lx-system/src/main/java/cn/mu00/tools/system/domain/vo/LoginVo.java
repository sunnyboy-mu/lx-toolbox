package cn.mu00.tools.system.domain.vo;

import lombok.Data;

@Data
public class LoginVo {

    private String username;
    private String password;
    private String authCode;
}
