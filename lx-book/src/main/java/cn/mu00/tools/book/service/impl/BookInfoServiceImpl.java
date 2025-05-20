package cn.mu00.tools.book.service.impl;

import cn.mu00.tools.book.domain.Book;
import cn.mu00.tools.book.mapper.BookInfoMapper;
import cn.mu00.tools.book.service.BookInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, Book> implements BookInfoService {
    @Override
    public List<Book> listBook() {
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Book::getCreatedAt);
        return list(queryWrapper);
    }
}
