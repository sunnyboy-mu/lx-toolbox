package cn.mu00.tools.bookmark.service;

import cn.mu00.tools.bookmark.domain.BmGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BmGroupService extends IService<BmGroup> {
    List<BmGroup> listByCategoryId(String categoryId);

    String deleteById(String id);
}
