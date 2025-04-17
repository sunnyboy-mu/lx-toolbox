package cn.mu00.tools.controller.common;

import cn.mu00.tools.common.domain.CommonConfig;
import cn.mu00.tools.common.domain.R;
import cn.mu00.tools.common.service.CommonConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R<String> update(@RequestBody CommonConfig commonConfig) {
        return R.ok(commonConfigService.updateConfig(commonConfig));
    }

    @PutMapping("/batch")
    public R<String> batchUpdate(@RequestBody List<CommonConfig> commonConfigs) {
        return R.ok(commonConfigService.batchUpdateConfig(commonConfigs));
    }
}
