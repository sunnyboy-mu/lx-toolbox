package cn.mu00.tools.blog.mapper;

import cn.mu00.tools.blog.domain.BlogCatalog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogCategoryMapper extends BaseMapper<BlogCatalog> {

}
