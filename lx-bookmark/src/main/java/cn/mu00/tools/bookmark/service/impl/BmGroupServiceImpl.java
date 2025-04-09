package cn.mu00.tools.bookmark.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.mu00.tools.bookmark.domain.BmGroup;
import cn.mu00.tools.bookmark.domain.BmInfo;
import cn.mu00.tools.bookmark.mapper.BmGroupMapprt;
import cn.mu00.tools.bookmark.service.BmGroupService;
import cn.mu00.tools.bookmark.service.BmInfoService;
import cn.mu00.tools.common.exception.ServiceException;
import cn.mu00.tools.common.utils.SortExchangeUtil;
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
        List<BmGroup> bmGroups = SortExchangeUtil.exchangeSort(bmGroupIds, this.listByIds(bmGroupIds), BmGroup::new);
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
