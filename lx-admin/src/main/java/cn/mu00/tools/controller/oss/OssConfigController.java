package cn.mu00.tools.controller.oss;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.mu00.tools.common.constant.UserRole;
import cn.mu00.tools.common.domain.R;
import cn.mu00.tools.oss.domain.OssConfig;
import cn.mu00.tools.oss.service.OssConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oss-config")
public class OssConfigController {

    @Autowired
    private OssConfigService ossConfigService;

    @SaCheckRole(UserRole.ADMIN)
    @GetMapping("/detail")
    public R<OssConfig> getConfig(){
        return R.ok(ossConfigService.getOssConfigDetail());
    }

    @SaCheckRole(UserRole.ADMIN)
    @PutMapping("/update")
    public R<OssConfig> updateConfig(@RequestBody OssConfig ossConfig){
        return R.ok(ossConfigService.update(ossConfig));
    }
}
