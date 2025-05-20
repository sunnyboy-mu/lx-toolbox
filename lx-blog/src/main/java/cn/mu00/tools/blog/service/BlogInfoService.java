package cn.mu00.tools.blog.service;

import cn.mu00.tools.blog.domain.BlogCatalog;
import cn.mu00.tools.blog.domain.BlogInfo;
import com.baomidou.mybatisplus.extension.service.IService;


public interface BlogInfoService extends IService<BlogInfo> {

    BlogInfo addBaseInfo(BlogCatalog catalogDto);


}
