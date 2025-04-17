package cn.mu00.tools.controller.book;

import cn.mu00.tools.book.domain.BookInfo;
import cn.mu00.tools.book.domain.vo.YearBookVo;
import cn.mu00.tools.book.service.BookInfoService;
import cn.mu00.tools.common.domain.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/book/info")
public class BookInfoController {

    @Resource
    private BookInfoService bookInfoService;

    @GetMapping("/list")
    public R<List<BookInfo>> listBookInfo(@RequestParam(required = false) String year) {
        return R.ok(bookInfoService.listBook(year));
    }

    @PostMapping
    public R<?> addBookInfo(@RequestBody BookInfo bookInfo) {
        bookInfoService.save(bookInfo);
        return R.ok("添加成功");
    }

    @PutMapping
    public R<?> updateBookInfo(@RequestBody BookInfo bookInfo) {
        bookInfoService.updateById(bookInfo);
        return R.ok("更新成功");
    }

    @DeleteMapping("/{id}")
    public R<?> deleteBookInfo(@PathVariable String id) {
        bookInfoService.removeById(id);
        return R.ok("删除成功");
    }

    @GetMapping("/year.interface")
    public R<List<YearBookVo>> listBookGroupByYear() {
        return R.ok(bookInfoService.listBookByYear());
    }
}
