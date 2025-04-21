package cn.mu00.tools.book.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.mu00.tools.book.domain.BookInfo;
import cn.mu00.tools.book.domain.vo.YearBookVo;
import cn.mu00.tools.book.mapper.BookInfoMapper;
import cn.mu00.tools.book.service.BookInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements BookInfoService {
    @Override
    public List<BookInfo> listBook(String year) {
        LambdaQueryWrapper<BookInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(year)) {
            queryWrapper.like(BookInfo::getCreatedAt, year);
        }
        queryWrapper.orderByDesc(BookInfo::getCreatedAt);
        return list(queryWrapper);
    }

    @Override
    public List<YearBookVo> listBookByYear() {
        List<BookInfo> bookList = list(new LambdaQueryWrapper<BookInfo>().orderByDesc(BookInfo::getCreatedAt));
        // 按年份分组
        Map<String, List<BookInfo>> groupedByYear = bookList.stream()
                .collect(Collectors.groupingBy(
                        book -> {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(book.getCreatedAt());
                            return StrUtil.toString(cal.get(Calendar.YEAR));
                        }
                ));

        return groupedByYear.entrySet().stream()
                .map(entry -> new YearBookVo(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
