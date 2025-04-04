package cn.mu00.tools.bookmark.service;

import cn.mu00.tools.bookmark.domain.BmInfo;
import cn.mu00.tools.bookmark.domain.vo.BmInfoVo;
import cn.mu00.tools.bookmark.domain.vo.BookmarkVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BmInfoService extends IService<BmInfo> {

    Page<BmInfo> page(Page<BmInfo> page, BmInfoVo bmInfoVo);

    List<BookmarkVo> listBookmarkVo(String categoryId);
}
