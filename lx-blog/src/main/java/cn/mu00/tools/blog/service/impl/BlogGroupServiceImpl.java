package cn.mu00.tools.blog.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.mu00.tools.blog.domain.BlogGroup;
import cn.mu00.tools.blog.domain.BlogInfo;
import cn.mu00.tools.blog.mapper.BlogGroupMapper;
import cn.mu00.tools.blog.service.BlogGroupService;
import cn.mu00.tools.blog.service.BlogInfoService;
import cn.mu00.tools.common.exception.ServiceException;
import cn.mu00.tools.common.utils.SortExchangeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogGroupServiceImpl extends ServiceImpl<BlogGroupMapper, BlogGroup> implements BlogGroupService {

    @Autowired
    private BlogInfoService blogInfoService;


    @Override
    public List<BlogGroup> listAllGroup(BlogGroup blogGroup) {
        LambdaQueryWrapper<BlogGroup> queryWrapper = new LambdaQueryWrapper<BlogGroup>()
                .like(StrUtil.isNotEmpty(blogGroup.getTitle()), BlogGroup::getTitle, blogGroup.getTitle())
                .eq(ObjectUtil.isNotNull(blogGroup.getStatus()), BlogGroup::getStatus, blogGroup.getStatus())
                .eq(BlogGroup::getCategoryId, blogGroup.getCategoryId())
                .orderByAsc(BlogGroup::getSort);
        return list(queryWrapper);
    }

    @Override
    public Integer nextSortValue() {
        BlogGroup maxSortBlogGroup = getOne(new LambdaQueryWrapper<BlogGroup>().orderByDesc(BlogGroup::getSort).last("limit 1"));
        return ObjectUtil.isNull(maxSortBlogGroup) ? 10 : maxSortBlogGroup.getSort() + 10;
    }

    @Override
    public String removeByGroupId(String id) {
        long infos = blogInfoService.count(new LambdaQueryWrapper<BlogInfo>().eq(BlogInfo::getGroupId, id));
        if (infos > 0) {
            throw new ServiceException("该分组下存在文章，无法删除");
        }
        removeById(id);
        return "删除成功";
    }

    @Transactional
    @Override
    public String updateSort(List<String> ids) {
        List<BlogGroup> blogGroups = SortExchangeUtil.exchangeSort(ids, this.listByIds(ids), BlogGroup::new);
        this.updateBatchById(blogGroups);
        return "排序成功";
    }
}
