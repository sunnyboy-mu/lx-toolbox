package cn.mu00.tools.controller.blog;

import cn.mu00.tools.blog.domain.BlogInfo;
import cn.mu00.tools.blog.service.BlogInfoService;
import cn.mu00.tools.common.domain.R;
import cn.mu00.tools.oss.service.CommonImgService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/blog/info")
public class BlogInfoController {

    @Resource
    private BlogInfoService blogInfoService;

    @Resource
    private CommonImgService commonImgService;

    @GetMapping("/page")
    public R<Page<BlogInfo>> page(Page<BlogInfo> pageQuery, BlogInfo blogInfo) {
        return R.ok(blogInfoService.getBlogInfoPage(pageQuery, blogInfo));
    }

    @GetMapping("/detail/{id}")
    public R<BlogInfo> getOne(@PathVariable String id) {
        return R.ok(blogInfoService.getById(id));
    }

    @GetMapping("/getSortValue")
    public R<Integer> getSortValue() {
        return R.ok(blogInfoService.getNextSortValue());
    }

    @PostMapping
    public R<String> add(@RequestBody BlogInfo blogInfo) {
        return R.ok(blogInfoService.addBlogInfo(blogInfo));
    }

    @PutMapping
    public R<String> update(@RequestBody BlogInfo blogInfo) {
        return R.ok(blogInfoService.updateBlogInfo(blogInfo));
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable String id) {
        return R.ok(blogInfoService.deleteBlogInfoById(id));
    }

    @PutMapping("/sort")
    public R<String> sort(@RequestBody List<String> ids) {
        return R.ok(blogInfoService.updateSort(ids));
    }


    @PostMapping("/upload-img")
    public R<?> uploadImg(MultipartFile file) {
        return R.ok(commonImgService.updateBlogImg(file));
    }

}
