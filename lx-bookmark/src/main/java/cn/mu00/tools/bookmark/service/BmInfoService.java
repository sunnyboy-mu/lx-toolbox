package cn.mu00.tools.bookmark.service;

import cn.mu00.tools.bookmark.domain.BmInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BmInfoService extends IService<BmInfo> {

    Page<BmInfo> page(Page<BmInfo> page, BmInfo bmInfo);

}
