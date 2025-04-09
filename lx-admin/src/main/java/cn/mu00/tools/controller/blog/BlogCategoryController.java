package cn.mu00.tools.controller.blog;

import cn.mu00.tools.blog.domain.BlogCategory;
import cn.mu00.tools.blog.service.BlogCategoryService;
import cn.mu00.tools.common.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/category")
public class BlogCategoryController {
    @Autowired
    private BlogCategoryService blogCategoryService;


    @GetMapping
    public R<List<BlogCategory>> listall(){
        return R.ok(blogCategoryService.listall());
    }

    @PostMapping
    public R<String> add(@RequestBody BlogCategory blogCategory){
        blogCategoryService.save(blogCategory);
        return R.ok("添加成功");
    }

    @PutMapping
    public R<String> update(@RequestBody BlogCategory blogCategory){
        blogCategoryService.updateById(blogCategory);
        return R.ok("更新成功");
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable String id){
        return R.ok(blogCategoryService.removeByCategoryId(id));
    }

    @PutMapping("/sort")
    public R<String> sort(@RequestBody List<String> ids){
        return R.ok(blogCategoryService.updateSort(ids));
    }

    @GetMapping("/next-sort")
    public R<Integer> nextSort(){
        return R.ok(blogCategoryService.getNextCountValue());
    }

}
