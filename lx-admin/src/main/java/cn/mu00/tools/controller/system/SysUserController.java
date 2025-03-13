package cn.mu00.tools.controller.system;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.mu00.tools.common.constant.UserRole;
import cn.mu00.tools.common.domain.R;
import cn.mu00.tools.system.domain.SysUser;
import cn.mu00.tools.system.domain.vo.PasswordVo;
import cn.mu00.tools.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 分页查询用户
     */
    @GetMapping("/page")
    public R<Page<SysUser>> page(Page<SysUser> page, SysUser sysUser){
        return R.ok(sysUserService.page(page, sysUser));
    }

    /**
     * 添加用户
     */
    @PostMapping("/add")
    public R<SysUser> add(@RequestBody SysUser sysUser){
        return R.ok(sysUserService.add(sysUser));
    }

    /**
     * 修改用户
     */
    @PutMapping("/update")
    public R<SysUser> update(@RequestBody SysUser sysUser){
        return R.ok(sysUserService.update(sysUser));
    }


    /**
     * 删除用户
     */
    @DeleteMapping("/delete/{id}")
    public R<String> delete(@PathVariable String id){
        return R.ok(sysUserService.delete(id));
    }

    /**
     * 批量删除用户
     */
    @PutMapping("/delete/batch")
    public R<String> deleteBatch(@RequestBody List<String> ids) {
        return R.ok(sysUserService.deleteBatch(ids));
    }

    /**
     * 修改密码
     */
    @PutMapping("/update/password")
    public R<String> updatePassword(@RequestBody PasswordVo passwordVo){
        return R.ok(sysUserService.updatePassword(passwordVo));
    }

    /**
     * 重置密码
     */
    @SaCheckRole(UserRole.ADMIN)
    @PutMapping("/resetPwd/{userId}")
    public R<Map<String, String>> resetPwd(@PathVariable String userId){
        return R.ok(sysUserService.resetPwd(userId));
    }

    /**
     * 生成授权码
     */
    @GetMapping("/generate-code")
    public R<String> generateCode(){
        return R.ok(sysUserService.generateAuthCode());
    }
}
