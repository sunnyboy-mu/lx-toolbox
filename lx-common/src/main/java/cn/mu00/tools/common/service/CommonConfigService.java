package cn.mu00.tools.common.service;

import cn.mu00.tools.common.domain.CommonConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CommonConfigService extends IService<CommonConfig> {

    CommonConfig getCommonConfigByKey(String key);

    List<CommonConfig> getCommonConfigByGroup(String group);

    String updateConfig(CommonConfig config);

    String betchUpdateConfig(List<CommonConfig> sysConfigList);
}
