package cn.mu00.tools.blog.mapper;

import cn.mu00.tools.blog.domain.BlogCategory;
import cn.mu00.tools.blog.domain.vo.BlogTypeTreeVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogCategoryMapper extends BaseMapper<BlogCategory> {
    
    List<BlogTypeTreeVo> getBlogTypeTree();
}
