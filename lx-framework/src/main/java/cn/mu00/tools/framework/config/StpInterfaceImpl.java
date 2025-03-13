package cn.mu00.tools.framework.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.mu00.tools.common.redis.RedisCache;
import cn.mu00.tools.system.domain.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private RedisCache redisCache;

    /**
     * 返回一个账号所拥有的权限码集合 
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<>();
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        TokenVo tokenVO = redisCache.getCacheObject(loginId.toString());
        if (ObjectUtil.isNull(tokenVO)){
            return list;
        }
        String role = tokenVO.getRole();
        if (StrUtil.isNotEmpty(role)){
            list.add(role);
        }
        return list;
    }

}
