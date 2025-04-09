package cn.mu00.tools.blog.service;

import cn.mu00.tools.blog.domain.BlogCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BlogCategoryService extends IService<BlogCategory> {

    /**
     * 获取所有分类
     */
    List<BlogCategory> listall();

    /**
     * 获取下一个排序值
     */
    Integer getNextCountValue();

    /**
     * 调整排序
     */
    String updateSort(List<String> ids);

    String removeByCategoryId(String id);
}
