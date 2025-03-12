package cn.mu00.tools.controller;

import cn.mu00.tools.common.domain.R;
import cn.mu00.tools.system.domain.vo.LoginVo;
import cn.mu00.tools.system.domain.vo.TokenVo;
import cn.mu00.tools.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 账号密码登录
     */
    @PostMapping("/login")
    public R<TokenVo> login(@RequestBody LoginVo loginVo) {
        return R.ok(sysUserService.login(loginVo),"登录成功");
    }

    @GetMapping("/logout")
    public R logout() {
        sysUserService.logout();
        return R.ok(null,"退出成功");
    }
}
