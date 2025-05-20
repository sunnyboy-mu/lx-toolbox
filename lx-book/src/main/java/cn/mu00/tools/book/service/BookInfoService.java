package cn.mu00.tools.book.service;

import cn.mu00.tools.book.domain.Book;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BookInfoService extends IService<Book> {

   List<Book> listBook ();


}
