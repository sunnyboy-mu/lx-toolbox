package cn.mu00.tools.bookmark.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.mu00.tools.bookmark.domain.Bookmark;
import cn.mu00.tools.bookmark.domain.vo.BookmarkVo;
import cn.mu00.tools.bookmark.mapper.BookmarkMapper;
import cn.mu00.tools.bookmark.service.BookmarkService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class BookmarkServiceImpl extends ServiceImpl<BookmarkMapper, Bookmark> implements BookmarkService {

    @Override
    public List<BookmarkVo> listBookmark() {
        LambdaQueryWrapper<Bookmark> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Bookmark::getCreatedAt);
        wrapper.eq(!StpUtil.isLogin(), Bookmark::getStatus, 0);
        List<Bookmark> all = list(wrapper);
        // 按组分组并保持顺序
        Map<String, List<Bookmark>> result = all.stream()
                .collect(Collectors.groupingBy(
                        Bookmark::getGroup,
                        LinkedHashMap::new, // 保持插入顺序
                        Collectors.toList()
                ));
        return result.entrySet().stream()
                .map(entry -> BookmarkVo.builder()
                        .group(entry.getKey())
                        .navs(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listGroup() {
        LambdaQueryWrapper<Bookmark> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Bookmark::getCreatedAt);
        return list(wrapper).stream().map(Bookmark::getGroup).distinct().collect(Collectors.toList());
    }
}
