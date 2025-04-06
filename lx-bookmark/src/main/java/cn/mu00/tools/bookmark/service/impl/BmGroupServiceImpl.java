package cn.mu00.tools.bookmark.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.mu00.tools.bookmark.domain.BmGroup;
import cn.mu00.tools.bookmark.domain.BmInfo;
import cn.mu00.tools.bookmark.mapper.BmGroupMapprt;
import cn.mu00.tools.bookmark.service.BmGroupService;
import cn.mu00.tools.bookmark.service.BmInfoService;
import cn.mu00.tools.common.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BmGroupServiceImpl extends ServiceImpl<BmGroupMapprt, BmGroup> implements BmGroupService {

    @Autowired
    private BmInfoService bmInfoService;

    @Override
    public List<BmGroup> listByCategoryId(String categoryId) {
        return this.list(new LambdaQueryWrapper<BmGroup>().eq(BmGroup::getCategoryId, categoryId).orderByAsc(BmGroup::getSort));
    }

    @Override
    public BmGroup add(BmGroup bmGroup) {
        bmGroup.setSort(getNextSortValue(bmGroup.getCategoryId()));
        this.save(bmGroup);
        return bmGroup;
    }

    @Override
    public String deleteById(String id) {
        long count = bmInfoService.count(new LambdaQueryWrapper<BmInfo>().eq(BmInfo::getGroupId, id));
        if (count > 0) {
            throw new ServiceException("该分组下还有书签，无法删除！");
        }
        this.removeById(id);
        return "删除成功！";
    }

    @Transactional
    @Override
    public String sort(List<String> bmGroupIds) {
        Map<String, BmGroup> bmGroupMap = this.listByIds(bmGroupIds).stream()
                .collect(Collectors.toMap(BmGroup::getId, Function.identity()));

        // 按bmGroupIds顺序收集对应的排序值，确保顺序一致
        List<Integer> bmGroupSorts = new ArrayList<>(bmGroupIds.size());
        for (String id : bmGroupIds) {
            BmGroup group = bmGroupMap.get(id);
            if (group == null) {
                throw new IllegalArgumentException("无效的分组ID: " + id);
            }
            bmGroupSorts.add(group.getSort());
        }

        // 升序排序
        bmGroupSorts.sort(Integer::compareTo);

        // 构建更新列表，通过索引匹配ID与排序值
        List<BmGroup> bmGroups = IntStream.range(0, bmGroupIds.size())
                .mapToObj(i -> {
                    BmGroup bmGroup = new BmGroup();
                    bmGroup.setId(bmGroupIds.get(i));
                    bmGroup.setSort(bmGroupSorts.get(i));
                    return bmGroup;
                })
                .collect(Collectors.toList());
        // 批量更新
        this.updateBatchById(bmGroups);
        return "排序成功！";
    }

    @Override
    public Integer getNextSortValue(String categoryId) {
        BmGroup bmGroup = this.getOne(new LambdaQueryWrapper<BmGroup>().eq(BmGroup::getCategoryId, categoryId).orderByDesc(BmGroup::getSort).last("limit 1"));
        return ObjectUtil.isNull(bmGroup) ? 10 : bmGroup.getSort() + 10;
    }
}
