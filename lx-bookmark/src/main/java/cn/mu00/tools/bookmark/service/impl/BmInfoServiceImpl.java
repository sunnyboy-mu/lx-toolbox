package cn.mu00.tools.bookmark.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.mu00.tools.bookmark.domain.BmInfo;
import cn.mu00.tools.bookmark.domain.vo.BookmarkVo;
import cn.mu00.tools.bookmark.mapper.BmInfoMapper;
import cn.mu00.tools.bookmark.service.BmInfoService;
import cn.mu00.tools.common.constant.UserRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BmInfoServiceImpl extends ServiceImpl<BmInfoMapper, BmInfo> implements BmInfoService {

    @Autowired
    private BmInfoMapper bmInfoMapper;

    @Override
    public Page<BmInfo> page(Page<BmInfo> page, BmInfo bmInfo) {
        return this.page(page, new LambdaQueryWrapper<BmInfo>().eq(StrUtil.isNotEmpty(bmInfo.getGroupId()), BmInfo::getGroupId, bmInfo.getGroupId()));
    }

    @Override
    public List<BookmarkVo> listBookmarkVo(String categoryId) {
        List<Integer> statusList = new ArrayList<>();
        statusList.add(0);
        if (StpUtil.isLogin() && StpUtil.hasRole(UserRole.ADMIN)) {
            statusList.add(2);
        }
        return bmInfoMapper.listBookmarkVo(categoryId, statusList);
    }
}
