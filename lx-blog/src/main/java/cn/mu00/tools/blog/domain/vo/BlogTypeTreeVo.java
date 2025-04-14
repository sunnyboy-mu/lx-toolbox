package cn.mu00.tools.blog.domain.vo;

import cn.mu00.tools.blog.domain.BlogCategory;
import cn.mu00.tools.blog.domain.BlogGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BlogTypeTreeVo extends BlogCategory {
    
    private static final long serialVersionUID = 1L;

    private List<BlogGroup> children;
}
