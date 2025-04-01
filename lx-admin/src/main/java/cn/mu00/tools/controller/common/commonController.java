package cn.mu00.tools.controller.common;

import cn.mu00.tools.bookmark.domain.BmCategory;
import cn.mu00.tools.bookmark.service.BmCategoryService;
import cn.mu00.tools.common.domain.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/common")
public class commonController {

    @Autowired
    private BmCategoryService bmCategoryService;

    @GetMapping("/system-info.interface")
    public R<?> systemInfo() {
        // 构建HashMap
        HashMap<String, Object> map = new HashMap<>();
        // 1、书签分类
        List<BmCategory> bmCategoryList = bmCategoryService.list(new LambdaQueryWrapper<BmCategory>().orderByAsc(BmCategory::getSort));
        map.put("bookmark", bmCategoryList);

        return R.ok(map);
    }
}
