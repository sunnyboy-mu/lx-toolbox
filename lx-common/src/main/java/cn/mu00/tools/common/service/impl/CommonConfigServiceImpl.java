package cn.mu00.tools.common.service.impl;

import cn.mu00.tools.common.domain.CommonConfig;
import cn.mu00.tools.common.mapper.CommonConfigMapper;
import cn.mu00.tools.common.service.CommonConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommonConfigServiceImpl extends ServiceImpl<CommonConfigMapper, CommonConfig> implements CommonConfigService {

    @Override
    public CommonConfig getCommonConfigByKey(String key) {
        return getById(key);
    }

    @Override
    public List<CommonConfig> getCommonConfigByGroup(String group) {
        return list(new LambdaQueryWrapper<CommonConfig>().eq(CommonConfig::getConfigGroup, group));
    }

    @Override
    public String updateConfig(CommonConfig config) {
        updateById(config);
        return "更新成功";
    }

    @Override
    @Transactional
    public String betchUpdateConfig(List<CommonConfig> sysConfigList) {
        updateBatchById(sysConfigList);
        return "更新成功";
    }

}
