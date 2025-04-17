package cn.mu00.tools.book.service;

import cn.mu00.tools.book.domain.BookInfo;
import cn.mu00.tools.book.domain.vo.YearBookVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BookInfoService extends IService<BookInfo> {

   List<BookInfo> listBook (String year);


   List<YearBookVo> listBookByYear();

}
