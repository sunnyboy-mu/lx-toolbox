package cn.mu00.tools.blog.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.mu00.tools.blog.constant.CatalogType;
import cn.mu00.tools.blog.constant.DropType;
import cn.mu00.tools.blog.domain.BlogCatalog;
import cn.mu00.tools.blog.domain.BlogInfo;
import cn.mu00.tools.blog.domain.vo.DropCatalogNode;
import cn.mu00.tools.blog.mapper.BlogCategoryMapper;
import cn.mu00.tools.blog.service.BlogCatalogService;
import cn.mu00.tools.blog.service.BlogInfoService;
import cn.mu00.tools.common.constant.SQLDialect;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BlogCatalogServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCatalog> implements BlogCatalogService {

    @Autowired
    private BlogInfoService blogInfoService;

    @Override
    public List<BlogCatalog> notesCatalog() {
        LambdaQueryWrapper<BlogCatalog> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(BlogCatalog::getSort);
        return buildTree(list(wrapper));
    }

    @Transactional
    @Override
    public BlogCatalog addCatalog(BlogCatalog catalog) {
        if (CatalogType.DOC.equals(catalog.getType())){
            BlogInfo blogInfo = blogInfoService.addBaseInfo(catalog);
            catalog.setUrl(blogInfo.getId());
        }
        catalog.setSort(getMinSort(catalog.getParentId()) - 1);
        this.save(catalog);
        return catalog;
    }

    private Integer getMinSort(String parentId) {
        LambdaQueryWrapper<BlogCatalog> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(BlogCatalog::getSort);
        if (StrUtil.isEmpty(parentId)) {
            wrapper.isNull(BlogCatalog::getParentId);
        }else {
            wrapper.eq(BlogCatalog::getParentId, parentId);
        }
        wrapper.orderByAsc(BlogCatalog::getSort);
        wrapper.last(SQLDialect.SQL_LIMIT_ONE);
        BlogCatalog catalog = this.getOne(wrapper);
        if (ObjectUtil.isNotNull(catalog)){
            return catalog.getSort();
        }
        return 99;
    }

    @Override
    public String deleteCatalog(String id) {
        BlogCatalog catalog = getById(id);
        if(CatalogType.DOC.equals(catalog.getType())){
            blogInfoService.removeById(catalog.getUrl());
        }
        this.removeById(id);
        return "删除成功！";
    }

    @Override
    public BlogCatalog updateCatalog(BlogCatalog catalog) {
        BlogCatalog current = getById(catalog.getId());
        if (CatalogType.DOC.equals(current.getType()) && StrUtil.isNotEmpty(current.getUrl())){
            BlogInfo info = blogInfoService.getById(current.getUrl());
            info.setTitle(catalog.getTitle());
            info.setIcon(catalog.getIcon());
            blogInfoService.updateById(info);
        }
        current.setTitle(catalog.getTitle());
        current.setIcon(catalog.getIcon());
        this.updateById(current);
        return current;
    }


    @Transactional
    @Override
    public String dropSort(DropCatalogNode dropCatalogNode) {
        BlogCatalog current = getById(dropCatalogNode.getNodeId());
        BlogCatalog target = getById(dropCatalogNode.getTargetNodeId());

        switch (dropCatalogNode.getDropType()) {
            case DropType.INNER: {
                current.setParentId(target.getId());
                current.setSort(99); // 可以考虑动态生成或再优化
                this.updateById(current);
                break;
            }
            case DropType.BEFORE:
            case DropType.AFTER: {
                current.setParentId(target.getParentId());
                boolean insertBefore = DropType.BEFORE.equals(dropCatalogNode.getDropType());
                updateSortPosition(current, target, insertBefore);
                break;
            }
            default:
                return "未知的 drop 类型！";
        }
        return "排序成功！";
    }

    /**
     * 更新排序 - 将当前节点插入到目标节点之前或之后
     */
    @Transactional
    public void updateSortPosition(BlogCatalog current, BlogCatalog target, boolean insertBefore) {
        // 取得排序起点
        int firstSort = target.getSort() < 1 ? 10 : target.getSort();

        // 构造MyBatis-Plus的条件
        LambdaQueryWrapper<BlogCatalog> wrapper = new LambdaQueryWrapper<>();

        // 判断parentId是否为空
        if (StrUtil.isNotEmpty(target.getParentId())) {
            wrapper.eq(BlogCatalog::getParentId, target.getParentId());
        } else {
            wrapper.isNull(BlogCatalog::getParentId);
        }

        // 排除当前节点和目标节点
        wrapper.notIn(BlogCatalog::getId, current.getId(), target.getId());

        // 根据插入位置不同选择条件
        wrapper.gt(BlogCatalog::getSort, target.getSort());

        wrapper.orderByAsc(BlogCatalog::getSort);
        List<BlogCatalog> list = this.list(wrapper);

        List<BlogCatalog> result = new ArrayList<>();

        if (insertBefore) {
            result.add(current);
            result.add(target);
        } else {
            result.add(target);
            result.add(current);
        }

        result.addAll(list);

        // 重新设置排序值
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setSort(firstSort + i);
        }

        // 批量更新
        updateBatchById(result);
    }

    private List<BlogCatalog> buildTree(List<BlogCatalog> data) {
        LinkedHashMap<String, BlogCatalog> map = new LinkedHashMap<>();
        for (BlogCatalog item : data) {
            map.put(item.getId(), item);
        }
        for (BlogCatalog item : data) {
            BlogCatalog parent = map.get(item.getParentId());
            if (ObjectUtil.isNotNull(parent)) {
                if (ObjectUtil.isNull(parent.getChildren())) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(item);
            }
        }
        return map.values().stream().filter(item -> StrUtil.isEmpty(item.getParentId())).collect(Collectors.toList());
    }
}
