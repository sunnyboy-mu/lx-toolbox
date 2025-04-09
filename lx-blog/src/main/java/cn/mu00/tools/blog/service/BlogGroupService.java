package cn.mu00.tools.blog.service;

import cn.mu00.tools.blog.domain.BlogGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BlogGroupService extends IService<BlogGroup> {

    List<BlogGroup> listAllGroup(BlogGroup blogGroup);

    Integer nextSortValue();

    String removeByGroupId(String id);

    String updateSort(List<String> ids);
}
