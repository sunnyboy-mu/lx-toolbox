package cn.mu00.tools.controller.bookmark;

import cn.mu00.tools.bookmark.domain.Bookmark;
import cn.mu00.tools.bookmark.domain.vo.BookmarkVo;
import cn.mu00.tools.bookmark.service.BookmarkService;
import cn.mu00.tools.common.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bm")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping("/list.interface")
    public R<List<BookmarkVo>> list(){
        return R.ok(bookmarkService.listBookmark());
    }

    @GetMapping("/group")
    public R<List<String>> group(){
        return R.ok(bookmarkService.listGroup());
    }

    @PostMapping("/add")
    public R<Bookmark> add(@RequestBody Bookmark bookmark){
        bookmarkService.save(bookmark);
        return R.ok(bookmark);
    }

    @PostMapping("/update")
    public R<Bookmark> update(@RequestBody Bookmark bookmark){
        bookmarkService.updateById(bookmark);
        return R.ok(bookmark);
    }

    @DeleteMapping("/delete/{id}")
    public R<String> delete(@PathVariable String id){
        bookmarkService.removeById(id);
        return R.ok("删除成功");
    }
}
