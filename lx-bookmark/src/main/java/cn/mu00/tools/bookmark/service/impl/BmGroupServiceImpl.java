package cn.mu00.tools.bookmark.service.impl;

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

import java.util.List;

@Service
public class BmGroupServiceImpl extends ServiceImpl<BmGroupMapprt, BmGroup> implements BmGroupService {

    @Autowired
    private BmInfoService bmInfoService;

    @Override
    public List<BmGroup> listByCategoryId(String categoryId) {
        return this.list(new LambdaQueryWrapper<BmGroup>().eq(BmGroup::getCategoryId, categoryId));
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
}
