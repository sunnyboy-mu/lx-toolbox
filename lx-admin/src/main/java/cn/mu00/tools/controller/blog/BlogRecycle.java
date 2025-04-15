package cn.mu00.tools.controller.blog;

import cn.mu00.tools.blog.domain.BlogInfo;
import cn.mu00.tools.blog.service.BlogInfoService;
import cn.mu00.tools.common.domain.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/blog/recycle")
public class BlogRecycle {

    @Resource
    private BlogInfoService blogInfoService;

    @GetMapping("/page")
    public R<Page<BlogInfo>> page(Page<BlogInfo> page){
        return R.ok(blogInfoService.recyclePage(page));
    }

    @PutMapping("/batch-del")
    public R<String> batchDeleteRecycle(@RequestBody List<String> ids) {
        return R.ok(blogInfoService.batchDeleteRecycleByIds(ids));
    }

    @PutMapping("/batch-recovery")
    public R<String> batchRecoveryFromRecycle(@RequestBody List<String> ids) {
        return R.ok(blogInfoService.batchRecoveryFromRecycleByIds(ids));
    }
}
