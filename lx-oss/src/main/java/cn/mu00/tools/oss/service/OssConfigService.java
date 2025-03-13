package cn.mu00.tools.oss.service;

import cn.mu00.tools.oss.domain.OssConfig;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OssConfigService extends IService<OssConfig> {

    OssConfig getOssConfigDetail();

    OssConfig update(OssConfig ossConfig);
}
