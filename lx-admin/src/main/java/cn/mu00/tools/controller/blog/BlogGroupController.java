package cn.mu00.tools.controller.blog;

import cn.mu00.tools.blog.domain.BlogGroup;
import cn.mu00.tools.blog.domain.BlogInfo;
import cn.mu00.tools.blog.service.BlogGroupService;
import cn.mu00.tools.blog.service.BlogInfoService;
import cn.mu00.tools.common.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/group")
public class BlogGroupController {

    @Autowired
    private BlogGroupService blogGroupService;

    @Autowired
    private BlogInfoService blogInfoService;

    @GetMapping
    public R<List<BlogGroup>> list(BlogGroup blogGroup) {
        return R.ok(blogGroupService.listAllGroup(blogGroup));
    }

    @GetMapping("/nextSortValue")
    public R<Integer> nextSortValue() {
        return R.ok(blogGroupService.nextSortValue());
    }

    @PostMapping
    public R<String> add(@RequestBody BlogGroup blogGroup) {
        blogGroupService.save(blogGroup);
        return R.ok("添加成功！");
    }

    @PutMapping
    public R<String> update(@RequestBody BlogGroup blogGroup) {
        blogGroupService.updateById(blogGroup);
        return R.ok("修改成功！");
    }

    @DeleteMapping("/{id}")
    public R<String> remove(@PathVariable String id) {
        return R.ok(blogGroupService.removeByGroupId(id));
    }

    @PutMapping("/sort")
    public R<String> updateSort(@RequestBody List<String> ids) {
        return R.ok(blogGroupService.updateSort(ids));
    }

    @GetMapping("/info")
    public R<List<BlogInfo>> listBlogByGroupId(String groupId) {
        return R.ok(blogInfoService.getBlogInfoListByGroupId(groupId));
    }
}
