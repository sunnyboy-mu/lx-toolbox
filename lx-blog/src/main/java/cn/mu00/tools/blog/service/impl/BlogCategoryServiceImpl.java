package cn.mu00.tools.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.mu00.tools.blog.domain.BlogCategory;
import cn.mu00.tools.blog.domain.BlogGroup;
import cn.mu00.tools.blog.domain.BlogInfo;
import cn.mu00.tools.blog.domain.vo.BlogTypeTreeVo;
import cn.mu00.tools.blog.mapper.BlogCategoryMapper;
import cn.mu00.tools.blog.service.BlogCategoryService;
import cn.mu00.tools.blog.service.BlogGroupService;
import cn.mu00.tools.blog.service.BlogInfoService;
import cn.mu00.tools.common.constant.UserRole;
import cn.mu00.tools.common.exception.ServiceException;
import cn.mu00.tools.common.utils.SortExchangeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements BlogCategoryService {


    @Resource
    private BlogCategoryMapper blogCategoryMapper;

    @Autowired
    private BlogGroupService blogGroupService;

    @Autowired
    private BlogInfoService blogInfoService;

    @Override
    public List<BlogCategory> listall() {
        // 获取所有分类
        return list(new LambdaQueryWrapper<BlogCategory>().orderByAsc(BlogCategory::getSort));
    }

    @Override
    public Integer getNextCountValue() {
        BlogCategory maxSortCategory = getOne(new LambdaQueryWrapper<BlogCategory>().orderByDesc(BlogCategory::getSort).last("limit 1"));
        return maxSortCategory == null ? 10 : maxSortCategory.getSort() + 10;
    }

    @Transactional
    @Override
    public String updateSort(List<String> ids) {
        List<BlogCategory> blogCategories = SortExchangeUtil.exchangeSort(ids, this.listByIds(ids), BlogCategory::new);
        // 批量更新
        this.updateBatchById(blogCategories);
        return "排序成功！";
    }

    @Override
    public String removeByCategoryId(String id) {
        long groups = blogGroupService.count(new LambdaQueryWrapper<BlogGroup>().eq(BlogGroup::getCategoryId, id));
        long infos = blogInfoService.count(new LambdaQueryWrapper<BlogInfo>().eq(BlogInfo::getCategoryId, id));
        if (groups > 0 || infos > 0) {
            throw new ServiceException("该分类下存在分组或文章，无法删除");
        }
        removeById(id);
        return "删除成功";
    }

    @Override
    public List<BlogTypeTreeVo> getBlogTypeTree() {
        return blogCategoryMapper.getBlogTypeTree();
    }

    @Override
    public List<BlogTypeTreeVo> getBlogTypeTreeAndHasMainUrl() {
        List<Integer> statusList = new ArrayList<>();
        statusList.add(0);
        if (StpUtil.isLogin() && StpUtil.hasRole(UserRole.ADMIN)) {
            statusList.add(2);
        }
        return blogCategoryMapper.getBlogTypeTreeAndHasMainUrl(statusList);
    }
}
