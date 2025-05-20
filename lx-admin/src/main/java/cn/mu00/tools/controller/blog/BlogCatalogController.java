package cn.mu00.tools.controller.blog;

import cn.mu00.tools.blog.domain.BlogCatalog;
import cn.mu00.tools.blog.domain.vo.DropCatalogNode;
import cn.mu00.tools.blog.service.BlogCatalogService;
import cn.mu00.tools.common.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/catalog")
public class BlogCatalogController {

    @Autowired
    private BlogCatalogService blogCategoryService;

    @GetMapping("/tree.interface")
    public R<List<BlogCatalog>> notesCatalog() {
        return R.ok(blogCategoryService.notesCatalog());
    }

    @PostMapping("/add")
    public R<BlogCatalog> addCatalog(@RequestBody BlogCatalog catalog){
        return R.ok(blogCategoryService.addCatalog(catalog));
    }

    @PutMapping("/update")
    public R<BlogCatalog> updateCatalog(@RequestBody BlogCatalog catalog){
        return R.ok(blogCategoryService.updateCatalog(catalog));
    }

    @DeleteMapping("/delete/{id}")
    public R<String> deleteCatalog(@PathVariable String id){
        return R.ok(blogCategoryService.deleteCatalog(id));
    }

    @PutMapping("/dropSort")
    public R<String> dropSort(@RequestBody DropCatalogNode dropCatalogNode){
        return R.ok(blogCategoryService.dropSort(dropCatalogNode));
    }
}
