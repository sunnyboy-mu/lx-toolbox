package cn.mu00.tools.controller.blog;

import cn.mu00.tools.blog.domain.BlogInfo;
import cn.mu00.tools.blog.service.BlogInfoService;
import cn.mu00.tools.common.domain.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/info")
public class BlogInfoController {
    @Autowired
    private BlogInfoService blogInfoService;

    @GetMapping("/page")
    public R<Page<BlogInfo>> page(Page<BlogInfo> pageQuery, BlogInfo blogInfo) {
        return R.ok(blogInfoService.getBlogInfoPage(pageQuery, blogInfo));
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

    @PutMapping("/batch-del")
    public R<String> batchDelete(@RequestBody List<String> ids) {
        return R.ok(blogInfoService.batchDeleteByIds(ids));
    }

    @PutMapping("/sort")
    public R<String> sort(@RequestBody List<String> ids) {
        return R.ok(blogInfoService.updateSort(ids));
    }

    @DeleteMapping("/recycle/{id}")
    public R<String> deleteRecycle(@PathVariable String id) {
        return R.ok(blogInfoService.deleteRecycleBlogInfoById(id));
    }

    @PutMapping("/recycle/batch-del")
    public R<String> batchDeleteRecycle(@RequestBody List<String> ids) {
        return R.ok(blogInfoService.batchDeleteRecycleByIds(ids));
    }

    @PutMapping("/recycle/recovery/{id}")
    public R<String> recoveryFromRecycle(@PathVariable String id) {
        return R.ok(blogInfoService.recoveryFromRecycleById(id));
    }

    @PutMapping("/recycle/batch-recovery")
    public R<String> batchRecoveryFromRecycle(@RequestBody List<String> ids) {
        return R.ok(blogInfoService.batchRecoveryFromRecycleByIds(ids));
    }

    @PutMapping("/publish/{id}")
    public R<String> publish(@PathVariable String id) {
        return R.ok(blogInfoService.publishBlogInfo(id));
    }

    @PutMapping("/publish/batch")
    public R<String> batchPublish(@RequestBody List<String> ids) {
        return R.ok(blogInfoService.batchPublishByIds(ids));
    }

    @PutMapping("/unpublish/{id}")
    public R<String> unPublish(@PathVariable String id) {
        return R.ok(blogInfoService.unPublishBlogInfo(id));
    }

    @PutMapping("/unpublish/batch")
    public R<String> batchUnPublish(@RequestBody List<String> ids) {
        return R.ok(blogInfoService.batchUnPublishByIds(ids));
    }

}
