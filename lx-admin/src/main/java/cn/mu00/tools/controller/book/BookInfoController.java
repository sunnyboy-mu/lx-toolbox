package cn.mu00.tools.controller.book;

import cn.mu00.tools.book.domain.Book;
import cn.mu00.tools.book.service.BookInfoService;
import cn.mu00.tools.common.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookInfoController {

    @Autowired
    private BookInfoService bookInfoService;

    @GetMapping("/list.interface")
    public R<List<Book>> listBookInfo() {
        return R.ok(bookInfoService.listBook());
    }

    @PostMapping("/add")
    public R<?> addBookInfo(@RequestBody Book book) {
        bookInfoService.save(book);
        return R.ok(book);
    }

    @PutMapping("/update")
    public R<?> updateBookInfo(@RequestBody Book book) {
        bookInfoService.updateById(book);
        return R.ok(book);
    }

    @DeleteMapping("/delete/{id}")
    public R<?> deleteBookInfo(@PathVariable String id) {
        bookInfoService.removeById(id);
        return R.ok("删除成功");
    }
}
