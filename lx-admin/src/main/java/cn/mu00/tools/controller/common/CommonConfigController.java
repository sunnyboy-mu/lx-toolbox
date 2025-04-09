package cn.mu00.tools.controller.common;

import cn.mu00.tools.common.domain.CommonConfig;
import cn.mu00.tools.common.domain.R;
import cn.mu00.tools.common.service.CommonConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/common/config")
public class CommonConfigController {
    @Autowired
    private CommonConfigService commonConfigService;

    @GetMapping("/getConfigByKey")
    public R<CommonConfig> getConfigByKey(String key) {
        return R.ok(commonConfigService.getCommonConfigByKey(key));
    }

    @GetMapping("/getConfigByGroup")
    public R<List<CommonConfig>> getConfigByGroup(String group) {
        return R.ok(commonConfigService.getCommonConfigByGroup(group));
    }

    @PutMapping
    public R<String> update(CommonConfig commonConfig) {
        return R.ok(commonConfigService.updateConfig(commonConfig));
    }

    @PutMapping("/batch")
    public R<String> batchUpdate(List<CommonConfig> commonConfigs) {
        return R.ok(commonConfigService.betchUpdateConfig(commonConfigs));
    }
}
