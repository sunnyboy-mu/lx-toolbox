package cn.mu00.tools.system.service;

import cn.mu00.tools.common.domain.R;
import cn.mu00.tools.system.domain.SysUser;
import cn.mu00.tools.system.domain.vo.LoginVo;
import cn.mu00.tools.system.domain.vo.PasswordVo;
import cn.mu00.tools.system.domain.vo.TokenVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {

    SysUser findByUsername(String username);

    SysUser add(SysUser sysUser);

    Page<SysUser> page(Page<SysUser> page, SysUser sysUser);

    SysUser update(SysUser sysUser);

    String delete(String id);

    String deleteBatch(List<String> ids);

    String updatePassword(PasswordVo passwordVo);

    Map<String, String> resetPwd(String userId);

    TokenVo login(LoginVo loginVo);

    void logout();
}
