package cn.mu00.tools.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.mu00.tools.blog.domain.BlogInfo;
import cn.mu00.tools.blog.domain.vo.BlogTypeTreeVo;
import cn.mu00.tools.blog.mapper.BlogInfoMapper;
import cn.mu00.tools.blog.service.BlogInfoService;
import cn.mu00.tools.common.utils.SortExchangeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class BlogInfoServiceImpl extends ServiceImpl<BlogInfoMapper, BlogInfo> implements BlogInfoService {

    @Override
    public Page<BlogInfo> getBlogInfoPage(Page<BlogInfo> pageQuery, BlogInfo blogInfo) {
        LambdaQueryWrapper<BlogInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(blogInfo.getTitle()), BlogInfo::getTitle, blogInfo.getTitle());
        queryWrapper.eq(StrUtil.isNotEmpty(blogInfo.getGroupId()), BlogInfo::getGroupId, blogInfo.getGroupId());
        queryWrapper.eq(StrUtil.isNotEmpty(blogInfo.getCategoryId()), BlogInfo::getCategoryId, blogInfo.getCategoryId());
        queryWrapper.eq(ObjectUtil.isNotNull(blogInfo.getStatus()), BlogInfo::getStatus, blogInfo.getStatus());
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
        blogInfo.setStatus(BooleanUtil.isTrue(blogInfo.getPublish()) ? 0 : 1);
        blogInfo.setCreatedBy(StpUtil.getLoginIdAsString());
        save(blogInfo);
        return "新增成功！";
    }

    @Override
    public String updateBlogInfo(BlogInfo blogInfo) {
        blogInfo.setStatus(BooleanUtil.isTrue(blogInfo.getPublish()) ? 0 : 1);
        blogInfo.setUpdatedBy(StpUtil.getLoginIdAsString());
        updateById(blogInfo);
        return "更新成功";
    }

    @Override
    public String deleteBlogInfoById(String id) {
        updateStatus(id, 2);
        return "删除成功！";
    }

    @Transactional
    @Override
    public String batchDeleteByIds(List<String> ids) {
        batchUpdateStatus(ids, 2);
        return "批量删除成功！";
    }

    @Transactional
    @Override
    public String updateSort(List<String> ids) {
        List<BlogInfo> blogInfoList = SortExchangeUtil.exchangeSort(ids, this.listByIds(ids), BlogInfo::new);
        this.updateBatchById(blogInfoList);
        return "排序成功";
    }

    @Override
    public String deleteRecycleBlogInfoById(String id) {
        removeById(id);
        return "删除成功！";
    }

    @Transactional
    @Override
    public String batchDeleteRecycleByIds(List<String> ids) {
        removeBatchByIds(ids);
        return "批量删除成功！";
    }

    @Override
    public String recoveryFromRecycleById(String id) {
        updateStatus(id, 0);
        return "恢复成功！";
    }

    @Transactional
    @Override
    public String batchRecoveryFromRecycleByIds(List<String> ids) {
        batchUpdateStatus(ids, 0);
        return "批量恢复成功！";
    }


    @Override
    public String publishBlogInfo(String id) {
        updateStatus(id, 1);
        return "发布成功！";
    }

    @Override
    public String batchPublishByIds(List<String> ids) {
        batchUpdateStatus(ids, 1);
        return "批量发布成功！";
    }

    @Override
    public String unPublishBlogInfo(String id) {
        updateStatus(id, 0);
        return "取消发布成功！";
    }

    @Override
    public String batchUnPublishByIds(List<String> ids) {
        batchUpdateStatus(ids, 0);
        return "批量取消发布成功！";
    }

    private void updateStatus(String id, Integer status) {
        BlogInfo blogInfo = getById(id);
        blogInfo.setStatus(status);
        updateById(blogInfo);
    }

    private void batchUpdateStatus(List<String> ids, Integer status) {
        List<BlogInfo> blogInfos = listByIds(ids);
        blogInfos.forEach(blogInfo -> blogInfo.setStatus(status));
        updateBatchById(blogInfos);
    }
}
