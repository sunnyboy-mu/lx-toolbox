package cn.mu00.tools.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.mu00.tools.blog.domain.BlogGroup;
import cn.mu00.tools.blog.domain.BlogInfo;
import cn.mu00.tools.blog.domain.vo.BlogDetail;
import cn.mu00.tools.blog.mapper.BlogInfoMapper;
import cn.mu00.tools.blog.service.BlogGroupService;
import cn.mu00.tools.blog.service.BlogInfoService;
import cn.mu00.tools.common.constant.UserRole;
import cn.mu00.tools.common.exception.ServiceException;
import cn.mu00.tools.common.utils.SortExchangeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogInfoServiceImpl extends ServiceImpl<BlogInfoMapper, BlogInfo> implements BlogInfoService {

    @Resource
    private BlogGroupService blogGroupService;

    @Resource
    private BlogInfoMapper blogInfoMapper;


    @Override
    public Page<BlogInfo> getBlogInfoPage(Page<BlogInfo> pageQuery, BlogInfo blogInfo) {
        LambdaQueryWrapper<BlogInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(blogInfo.getTitle()), BlogInfo::getTitle, blogInfo.getTitle());
        queryWrapper.eq(StrUtil.isNotEmpty(blogInfo.getGroupId()), BlogInfo::getGroupId, blogInfo.getGroupId());
        queryWrapper.eq(StrUtil.isNotEmpty(blogInfo.getCategoryId()), BlogInfo::getCategoryId, blogInfo.getCategoryId());
        if (ObjectUtil.isNull(blogInfo.getStatus())) {
            queryWrapper.in(BlogInfo::getStatus, 0, 1);
        } else {
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
        if (ObjectUtil.isNotNull(blogInfo.getGroupMainInfo()) && blogInfo.getGroupMainInfo() == 0) {
            blogGroupService.setGroupUrl(blogInfo.getGroupId(), blogInfo.getId());
        }
        return "新增成功！";
    }

    @Override
    public String updateBlogInfo(BlogInfo blogInfo) {
        blogInfo.setUpdatedBy(StpUtil.getLoginIdAsString());
        updateById(blogInfo);
        if (ObjectUtil.isNotNull(blogInfo.getGroupMainInfo()) && blogInfo.getGroupMainInfo() == 0) {
            blogGroupService.setGroupUrl(blogInfo.getGroupId(), blogInfo.getId());
        }
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


    @Override
    public List<BlogInfo> getBlogInfoListByGroupId(String groupId) {
        LambdaQueryWrapper<BlogInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(BlogInfo::getId, BlogInfo::getTitle, BlogInfo::getIcon);
        queryWrapper.eq(BlogInfo::getGroupId, groupId);
        return list(queryWrapper);
    }


    @Override
    public BlogDetail getBlogDetail(String id) {
        BlogInfo info = getById(id);
        if(ObjectUtil.isNull(info)){
            throw new ServiceException("文章不存在！", 404);
        }
        if(ObjectUtil.isNull(info.getStatus())){
            throw new ServiceException("文章异常！", 404);
        }

        if (info.getStatus() != 0) {
            throw new ServiceException("文章不存在！", 404);
        }

        if(!StpUtil.isLogin() && info.getSpecial() == 0){
            throw new ServiceException("文章不存在！", 404);
        }

        info.setSourceText(null);

        BlogDetail detail = new BlogDetail();
        BeanUtil.copyProperties(info, detail);
        if (StrUtil.isEmpty(info.getGroupId())) {
            return detail;
        }
        BlogGroup group = blogGroupService.getById(info.getGroupId());
        detail.setMainUrl(group.getUrl());
        // 获取某分类下的分组
        List<Integer> statusList = new ArrayList<>();
        statusList.add(0);
        if (StpUtil.isLogin() && StpUtil.hasRole(UserRole.ADMIN)) {
            statusList.add(2);
        }
        LambdaQueryWrapper<BlogGroup> groupLambdaQueryWrapper = new LambdaQueryWrapper<>();
        groupLambdaQueryWrapper.in(BlogGroup::getStatus, statusList);
        groupLambdaQueryWrapper.eq(BlogGroup::getCategoryId, group.getCategoryId());
        groupLambdaQueryWrapper.orderByAsc(BlogGroup::getSort);
        List<BlogGroup> groups = blogGroupService.list(groupLambdaQueryWrapper).stream().filter(g -> StrUtil.isNotEmpty(g.getUrl())).collect(Collectors.toList());
        List<String> groupIds = groups.stream().map(BlogGroup::getId).collect(Collectors.toList());
        LambdaQueryWrapper<BlogInfo> blogInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        blogInfoLambdaQueryWrapper.in(BlogInfo::getGroupId, groupIds);
        blogInfoLambdaQueryWrapper.in(BlogInfo::getStatus, statusList);
        blogInfoLambdaQueryWrapper.orderByAsc(BlogInfo::getSort);
        List<BlogInfo> blogInfos = list(blogInfoLambdaQueryWrapper);

        groups.forEach(g -> {
            g.setBlogInfos(blogInfos.stream().filter(b -> b.getGroupId().equals(g.getId())).collect(Collectors.toList()));
        });

        detail.setGroups(groups.stream().filter(g -> !g.getBlogInfos().isEmpty()).collect(Collectors.toList()));
        return detail;
    }

}
