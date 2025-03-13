package cn.mu00.tools.oss.service.impl;

import cn.mu00.tools.oss.domain.OssConfig;
import cn.mu00.tools.oss.mapper.OssConfigMapper;
import cn.mu00.tools.oss.service.OssConfigService;
import cn.mu00.tools.oss.utils.UpYunUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OssConfigServiceImpl extends ServiceImpl<OssConfigMapper, OssConfig> implements OssConfigService {

    @Autowired
    private UpYunUtil upYunUtil;

    @Override
    public OssConfig getOssConfigDetail() {
        return getOne(new QueryWrapper<OssConfig>().last("LIMIT 1"));
    }

    @Override
    public OssConfig update(OssConfig ossConfig) {
        updateById(ossConfig);
        upYunUtil.resetUpYunRestManager();
        return ossConfig;
    }
}
