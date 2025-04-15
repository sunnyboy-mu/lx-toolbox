package cn.mu00.tools.blog.service;

import cn.mu00.tools.blog.domain.BlogInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BlogInfoService extends IService<BlogInfo> {

    Page<BlogInfo> getBlogInfoPage(Page<BlogInfo> page, BlogInfo blogInfo);

    Integer getNextSortValue();

    String addBlogInfo(BlogInfo blogInfo);

    String updateBlogInfo(BlogInfo blogInfo);

    String deleteBlogInfoById(String id);

    String updateSort(List<String> ids);

    String batchDeleteRecycleByIds(List<String> ids);


    String batchRecoveryFromRecycleByIds(List<String> ids);

    Page<BlogInfo> recyclePage(Page<BlogInfo> page);


}
