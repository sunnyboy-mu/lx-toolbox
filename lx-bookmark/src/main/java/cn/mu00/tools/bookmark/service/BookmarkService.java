package cn.mu00.tools.bookmark.service;

import cn.mu00.tools.bookmark.domain.Bookmark;
import cn.mu00.tools.bookmark.domain.vo.BookmarkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BookmarkService extends IService<Bookmark> {
    List<BookmarkVo> listBookmark();

    List<String> listGroup();
}
