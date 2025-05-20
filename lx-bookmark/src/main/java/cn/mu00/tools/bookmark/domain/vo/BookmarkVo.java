package cn.mu00.tools.bookmark.domain.vo;

import cn.mu00.tools.bookmark.domain.Bookmark;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class BookmarkVo implements Serializable {

    private static final long serialVersionUID = 1L; // 添加 serialVersionUID

    private String group;

    private List<Bookmark> navs;
}
