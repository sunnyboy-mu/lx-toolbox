package cn.mu00.tools.system.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.mu00.tools.common.domain.R;
import cn.mu00.tools.common.exception.ServiceException;
import cn.mu00.tools.common.redis.RedisCache;
import cn.mu00.tools.system.domain.SysUser;
import cn.mu00.tools.system.domain.vo.LoginVo;
import cn.mu00.tools.system.domain.vo.PasswordVo;
import cn.mu00.tools.system.domain.vo.TokenVo;
import cn.mu00.tools.system.mapper.SysUserMapper;
import cn.mu00.tools.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public SysUser add(SysUser sysUser) {
        if (StrUtil.isEmpty(sysUser.getUsername()) || StrUtil.isEmpty(sysUser.getPassword())) {
            throw new ServiceException("用户名或密码不能为空", 500);
        }
        // 判断用户名是否已存在
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        SysUser one = sysUserMapper.selectOne(queryWrapper.eq(SysUser::getUsername, sysUser.getUsername()));
        if (ObjectUtil.isNotEmpty(one)) {
            throw new ServiceException("用户名已存在", 500);
        }
        // 对密码进行加密
        String newPwd = SaSecureUtil.md5(sysUser.getPassword());
        sysUser.setPassword(newPwd);
        sysUserMapper.insert(sysUser);
        sysUser.setPassword(null);
        return sysUser;
    }

    @Override
    public Page<SysUser> page(Page<SysUser> page, SysUser sysUser) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sysUser.getUsername()), SysUser::getUsername, sysUser.getUsername());
        return sysUserMapper.selectPage(page, queryWrapper);
    }

    @Override
    public SysUser update(SysUser sysUser) {
        SysUser origin = sysUserMapper.selectById(sysUser.getId());
        if (origin.getUsername().equals(sysUser.getUsername())) {
            throw new ServiceException("用户名不能修改", 500);
        }
        sysUser.setPassword(null);
        sysUserMapper.updateById(sysUser);
        return sysUser;
    }

    @Override
    public String delete(String id) {
        sysUserMapper.deleteById(id);
        return "删除成功";
    }

    @Override
    public String deleteBatch(List<String> ids) {
        sysUserMapper.deleteBatchIds(ids);
        return "删除成功";
    }

    @Override
    public String updatePassword(PasswordVo passwordVo) {
        String userId = StpUtil.getLoginIdAsString();
        SysUser sysUser = sysUserMapper.selectById(userId);
        // 判断旧密码是否正确
        if (!sysUser.getPassword().equals(SaSecureUtil.md5(passwordVo.getOldPwd()))) {
            throw new ServiceException("旧密码不正确", 500);
        }
        // 新密码和确认密码是否一致
        if (!passwordVo.getNewPwd().equals(passwordVo.getConfirmPwd())) {
            throw new ServiceException("新密码和确认密码不一致", 500);
        }
        // 对密码进行加密
        String newPwd = SaSecureUtil.md5(passwordVo.getNewPwd());
        sysUser.setPassword(newPwd);
        sysUserMapper.updateById(sysUser);
        return "密码修改成功";
    }

    @Override
    public Map<String, String> resetPwd(String userId) {
        // 生成随机密码
        String randomPwd = RandomUtil.randomString(12);
        SysUser sysUser = sysUserMapper.selectById(userId);
        // 对密码进行加密
        String newPwd = SaSecureUtil.md5(randomPwd);
        sysUser.setPassword(newPwd);
        sysUserMapper.updateById(sysUser);
        return MapUtil.of("新密码", randomPwd);
    }

    @Override
    public TokenVo login(LoginVo loginVo) {
        // 判断账号密码是否存在
        if (StrUtil.isEmpty(loginVo.getUsername()) || StrUtil.isEmpty(loginVo.getPassword())) {
            throw new ServiceException("账号或密码不能为空", 500);
        }
        // 账户正确
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, loginVo.getUsername()));
        if (ObjectUtil.isNull(user)){
            throw new ServiceException("账号或密码错误", 500);
        }

        // 加密密码
        String password = SaSecureUtil.md5(loginVo.getPassword());
        if (!user.getPassword().equals(password)) {
            throw new ServiceException("账号或密码错误", 500);
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

    /**
     * 登录用户
     * @param user
     * @return
     */
    private TokenVo login(SysUser user){
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