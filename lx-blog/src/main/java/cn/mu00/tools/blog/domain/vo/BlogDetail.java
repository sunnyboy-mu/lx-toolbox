package cn.mu00.tools.blog.domain.vo;


import cn.mu00.tools.blog.domain.BlogGroup;
import cn.mu00.tools.blog.domain.BlogInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BlogDetail extends BlogInfo {

    private String mainUrl;

    private List<BlogGroup> groups;
}
