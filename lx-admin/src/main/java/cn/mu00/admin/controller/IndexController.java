package cn.mu00.admin.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.mu00.common.domain.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {


    @RequestMapping("/hello")
    public R<Map<String, Object>> hello(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg","hello");
        if (StpUtil.isLogin()){
            map.put("isLogin",true);
            map.put("userId",StpUtil.getLoginIdAsInt());
        }else {
            StpUtil.login(1);
            map.put("token",StpUtil.getTokenInfo());
        }
        return R.ok(map);
    }
}
