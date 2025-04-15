package cn.mu00.tools.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.mu00.tools.blog.domain.BlogInfo;
import cn.mu00.tools.blog.mapper.BlogInfoMapper;
import cn.mu00.tools.blog.service.BlogInfoService;
import cn.mu00.tools.common.utils.SortExchangeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogInfoServiceImpl extends ServiceImpl<BlogInfoMapper, BlogInfo> implements BlogInfoService {

    @Override
    public Page<BlogInfo> getBlogInfoPage(Page<BlogInfo> pageQuery, BlogInfo blogInfo) {
        LambdaQueryWrapper<BlogInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(blogInfo.getTitle()), BlogInfo::getTitle, blogInfo.getTitle());
        queryWrapper.eq(StrUtil.isNotEmpty(blogInfo.getGroupId()), BlogInfo::getGroupId, blogInfo.getGroupId());
        queryWrapper.eq(StrUtil.isNotEmpty(blogInfo.getCategoryId()), BlogInfo::getCategoryId, blogInfo.getCategoryId());
        if (ObjectUtil.isNull(blogInfo.getStatus())) {
            queryWrapper.in(BlogInfo::getStatus, 0, 1);
        }else {
            queryWrapper.eq(BlogInfo::getStatus, blogInfo.getStatus());
        }
        queryWrapper.eq(ObjectUtil.isNotNull(blogInfo.getSpecial()), BlogInfo::getSpecial, blogInfo.getSpecial());
        queryWrapper.orderByAsc(BlogInfo::getSort);
        queryWrapper.orderByDesc(BlogInfo::getCreatedAt);
        return page(pageQuery, queryWrapper);
    }

    @Override
    public Integer getNextSortValue() {
        BlogInfo maxSortBlogInfo = getOne(new LambdaQueryWrapper<BlogInfo>().orderByDesc(BlogInfo::getSort).last("limit 1"));
        return ObjectUtil.isNotNull(maxSortBlogInfo) ? maxSortBlogInfo.getSort() + 1 : 1;
    }

    @Override
    public String addBlogInfo(BlogInfo blogInfo) {
        blogInfo.setCreatedBy(StpUtil.getLoginIdAsString());
        save(blogInfo);
        return "新增成功！";
    }

    @Override
    public String updateBlogInfo(BlogInfo blogInfo) {
        blogInfo.setUpdatedBy(StpUtil.getLoginIdAsString());
        updateById(blogInfo);
        return "更新成功";
    }

    @Override
    public String deleteBlogInfoById(String id) {
        BlogInfo blogInfo = getById(id);
        blogInfo.setStatus(2);
        updateById(blogInfo);
        return "删除成功！";
    }


    @Transactional
    @Override
    public String updateSort(List<String> ids) {
        List<BlogInfo> blogInfoList = SortExchangeUtil.exchangeSort(ids, this.listByIds(ids), BlogInfo::new);
        this.updateBatchById(blogInfoList);
        return "排序成功";
    }

    @Override
    public Page<BlogInfo> recyclePage(Page<BlogInfo> page) {
        LambdaQueryWrapper<BlogInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogInfo::getStatus, 2);
        queryWrapper.orderByAsc(BlogInfo::getSort);
        queryWrapper.orderByDesc(BlogInfo::getCreatedAt);
        return page(page, queryWrapper);
    }

    @Transactional
    @Override
    public String batchDeleteRecycleByIds(List<String> ids) {
        removeBatchByIds(ids);
        return "删除成功！";
    }


    @Transactional
    @Override
    public String batchRecoveryFromRecycleByIds(List<String> ids) {
        List<BlogInfo> blogInfos = listByIds(ids);
        blogInfos.forEach(blogInfo -> blogInfo.setStatus(1));
        updateBatchById(blogInfos);
        return "恢复成功！";
    }



}
