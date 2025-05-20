package cn.mu00.tools.blog.service.impl;

import cn.mu00.tools.blog.constant.BlogInfoType;
import cn.mu00.tools.blog.domain.BlogCatalog;
import cn.mu00.tools.blog.domain.BlogInfo;
import cn.mu00.tools.blog.mapper.BlogInfoMapper;
import cn.mu00.tools.blog.service.BlogInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BlogInfoServiceImpl extends ServiceImpl<BlogInfoMapper, BlogInfo> implements BlogInfoService {

    @Override
    public BlogInfo addBaseInfo(BlogCatalog catalogDto) {
        BlogInfo info = new BlogInfo();
        info.setType(BlogInfoType.NOTE);
        info.setTitle(catalogDto.getTitle());
        info.setIcon(catalogDto.getIcon());
        this.save(info);
        return info;
    }
}
