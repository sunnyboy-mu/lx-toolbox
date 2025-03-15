package cn.mu00.tools.system.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.mu00.tools.common.constant.EntityStatus;
import cn.mu00.tools.common.exception.ServiceException;
import cn.mu00.tools.common.redis.RedisCache;
import cn.mu00.tools.system.domain.SysUser;
import cn.mu00.tools.system.domain.vo.LoginVo;
import cn.mu00.tools.system.domain.vo.TokenVo;
import cn.mu00.tools.system.service.LoginService;
import cn.mu00.tools.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public TokenVo login(LoginVo loginVo) {
        // 判断账号密码是否存在
        if (StrUtil.isEmpty(loginVo.getUsername()) || StrUtil.isEmpty(loginVo.getPassword())) {
            throw new ServiceException("账号或密码不能为空", 500);
        }

        // 账户密码正确
        String password = SaSecureUtil.md5(loginVo.getPassword());
        SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, loginVo.getUsername()).eq(SysUser::getPassword,password));
        if (ObjectUtil.isNull(user)){
            throw new ServiceException("账号或密码错误", 500);
        }
        return login(user);
    }

    @Override
    public TokenVo oauthLogin(LoginVo loginVo) {
        String code = loginVo.getAuthCode();
        if (StrUtil.isEmpty(code)){
            throw new ServiceException("授权码不能为空", 500);
        }

        SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getCode, code));

        if (ObjectUtil.isNull(user)){
            throw new ServiceException("授权码不存在，请重新输入！", 500);
        }

        return login(user);
    }

    @Override
    public void logout() {
        if (!StpUtil.isLogin()) {
            throw new ServiceException("用户未登录", 500);
        }
        String userId = StpUtil.getLoginIdAsString();
        redisCache.deleteObject(userId);
        StpUtil.logout();
    }

    @Override
    public TokenVo getUserInfo() {
        if (StpUtil.isLogin()) {
            return redisCache.getCacheObject(StpUtil.getLoginIdAsString());
        }
        return null;
    }

    /**
     * 登录用户
     * @param user
     * @return
     */
    private TokenVo login(SysUser user){
        // 状态是否正常
        if (!EntityStatus.NORMAL.equals(user.getStatus())) {
            throw new ServiceException("该账号已被禁用", 500);
        }
        // 登录成功
        StpUtil.login(user.getId());
        // 生成token
        String token = StpUtil.getTokenInfo().getTokenValue();
        TokenVo tokenVo = new TokenVo();
        BeanUtil.copyProperties(user, tokenVo);
        tokenVo.setPassword(null);
        tokenVo.setToken(token);
        redisCache.setCacheObject(user.getId(), tokenVo);
        return tokenVo;
    }
}
