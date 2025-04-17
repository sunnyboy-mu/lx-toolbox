package cn.mu00.tools.book.domain.vo;

import cn.mu00.tools.book.domain.BookInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class YearBookVo {

    private String year;

    List<BookInfo> books;

}
