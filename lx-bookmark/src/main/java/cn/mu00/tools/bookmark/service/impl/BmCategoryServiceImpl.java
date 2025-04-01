package cn.mu00.tools.bookmark.service.impl;

import cn.mu00.tools.bookmark.domain.BmCategory;
import cn.mu00.tools.bookmark.mapper.BmCategoryMapper;
import cn.mu00.tools.bookmark.service.BmCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class BmCategoryServiceImpl extends ServiceImpl<BmCategoryMapper, BmCategory> implements BmCategoryService {
}
