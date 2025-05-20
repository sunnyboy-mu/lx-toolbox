package cn.mu00.tools.blog.service;

import cn.mu00.tools.blog.domain.BlogCatalog;
import cn.mu00.tools.blog.domain.vo.DropCatalogNode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface BlogCatalogService extends IService<BlogCatalog> {

    List<BlogCatalog> notesCatalog();

    BlogCatalog addCatalog(BlogCatalog catalog);

    String deleteCatalog(String id);

    BlogCatalog updateCatalog(BlogCatalog catalog);

    String dropSort(DropCatalogNode dropCatalogNode);
}
