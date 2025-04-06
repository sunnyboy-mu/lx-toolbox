package cn.mu00.tools.controller.bookmark;

import cn.mu00.tools.bookmark.domain.BmGroup;
import cn.mu00.tools.bookmark.service.BmGroupService;
import cn.mu00.tools.common.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bm/group")
public class BmGroupController {

    @Autowired
    private BmGroupService bmGroupService;

    @GetMapping
    public R<List<BmGroup>> list(String categoryId) {
        return R.ok(bmGroupService.listByCategoryId(categoryId));
    }

    @PostMapping
    public R<BmGroup> add(@RequestBody BmGroup bmGroup) {
        bmGroupService.add(bmGroup);
        return R.ok(bmGroup);
    }

    @PutMapping
    public R<BmGroup> update(@RequestBody BmGroup bmGroup) {
        bmGroupService.updateById(bmGroup);
        return R.ok(bmGroup);
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable String id) {
        return R.ok(bmGroupService.deleteById(id));
    }

    @PutMapping("sort")
    public R<String> sort(@RequestBody List<String> bmGroupIds) {
        return R.ok(bmGroupService.sort(bmGroupIds));
    }
}
