package cn.mu00.tools.bookmark.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.mu00.tools.bookmark.domain.BmInfo;
import cn.mu00.tools.bookmark.mapper.BmInfoMapprt;
import cn.mu00.tools.bookmark.service.BmInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BmInfoServiceImpl extends ServiceImpl<BmInfoMapprt, BmInfo> implements BmInfoService {

    @Override
    public Page<BmInfo> page(Page<BmInfo> page, BmInfo bmInfo) {
        return this.page(page, new LambdaQueryWrapper<BmInfo>().eq(StrUtil.isNotEmpty(bmInfo.getGroupId()), BmInfo::getGroupId, bmInfo.getGroupId()));
    }
}
