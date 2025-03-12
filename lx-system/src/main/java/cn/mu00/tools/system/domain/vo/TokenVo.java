package cn.mu00.tools.system.domain.vo;

import cn.mu00.tools.system.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TokenVo extends SysUser {

    private static final long serialVersionUID = 1L;

    private String token;
}
