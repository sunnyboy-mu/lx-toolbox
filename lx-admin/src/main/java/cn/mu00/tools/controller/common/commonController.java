package cn.mu00.tools.controller.common;

import cn.mu00.tools.blog.service.BlogCatalogService;
import cn.mu00.tools.common.constant.ConfigKey;
import cn.mu00.tools.common.domain.R;
import cn.mu00.tools.common.service.CommonConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/common")
public class commonController {


    @Autowired
    private CommonConfigService commonConfigService;

    @Autowired
    private BlogCatalogService blogCategoryService;


    @GetMapping("/system-info.interface")
    public R<?> systemInfo() {
        // 构建HashMap
        HashMap<String, Object> map = new HashMap<>();
        // 2. 博客图标
        map.put(ConfigKey.BLOG_ICON, commonConfigService.getCommonConfigByKey(ConfigKey.BLOG_ICON));
        return R.ok(map);
    }
}
