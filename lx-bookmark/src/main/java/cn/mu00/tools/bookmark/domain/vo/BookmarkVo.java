package cn.mu00.tools.bookmark.domain.vo;

import cn.mu00.tools.bookmark.domain.BmInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookmarkVo implements Serializable {

    private static final long serialVersionUID = 1L; // 添加 serialVersionUID

    private String groupId;

    private String groupTitle;

    private Integer groupStatus;

    private List<BmInfo> bmInfoList;
}
