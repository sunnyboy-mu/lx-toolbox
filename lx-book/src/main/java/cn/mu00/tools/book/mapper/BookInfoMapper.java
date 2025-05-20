package cn.mu00.tools.book.mapper;

import cn.mu00.tools.book.domain.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookInfoMapper extends BaseMapper<Book> {
}
