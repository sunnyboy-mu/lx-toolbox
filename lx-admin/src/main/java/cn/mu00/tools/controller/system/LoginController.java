package cn.mu00.tools.controller.system;

import cn.mu00.tools.common.domain.R;
import cn.mu00.tools.system.domain.vo.LoginVo;
import cn.mu00.tools.system.domain.vo.TokenVo;
import cn.mu00.tools.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 账号密码登录
     */
    @PostMapping("/login")
    public R<TokenVo> login(@RequestBody LoginVo loginVo) {
        return R.ok(loginService.login(loginVo),"登录成功");
    }

    /**
     * 授权码登录
     */
    @PostMapping("/oauth/login")
    public R<TokenVo> oauthLogin(@RequestBody LoginVo loginVo) {
        return R.ok(loginService.oauthLogin(loginVo),"登录成功");
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public R logout() {
        loginService.logout();
        return R.ok(null,"退出成功");
    }
}
