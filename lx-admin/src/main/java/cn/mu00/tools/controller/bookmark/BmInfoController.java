package cn.mu00.tools.controller.bookmark;

import cn.mu00.tools.bookmark.domain.BmInfo;
import cn.mu00.tools.bookmark.domain.vo.BmInfoVo;
import cn.mu00.tools.bookmark.domain.vo.BookmarkVo;
import cn.mu00.tools.bookmark.service.BmInfoService;
import cn.mu00.tools.common.domain.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bm/info")
@RestController
public class BmInfoController {

    @Autowired
    private BmInfoService bmInfoService;


    @GetMapping("/page")
    public R<Page<BmInfo>> page(Page<BmInfo> page, BmInfoVo bmInfoVo){
        return R.ok(bmInfoService.page(page, bmInfoVo));
    }

    @PostMapping
    public R<BmInfo> save(@RequestBody BmInfo bmInfo){
        bmInfoService.save(bmInfo);
        return R.ok(bmInfo);
    }

    @PutMapping
    public R<BmInfo> update(@RequestBody BmInfo bmInfo){
        bmInfoService.updateById(bmInfo);
        return R.ok(bmInfo);
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable String id){
        bmInfoService.removeById(id);
        return R.ok("删除成功！");
    }

    @GetMapping("/list/{categoryId}.interface")
    public R<List<BookmarkVo>> list(@PathVariable String categoryId){
        return R.ok(bmInfoService.listBookmarkVo(categoryId));
    }

}
