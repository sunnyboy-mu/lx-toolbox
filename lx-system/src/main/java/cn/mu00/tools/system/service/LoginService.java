package cn.mu00.tools.system.service;

import cn.mu00.tools.system.domain.vo.LoginVo;
import cn.mu00.tools.system.domain.vo.TokenVo;

public interface LoginService {

    TokenVo login(LoginVo loginVo);

    TokenVo oauthLogin(LoginVo loginVo);

    void logout();

    TokenVo getUserInfo();
}
