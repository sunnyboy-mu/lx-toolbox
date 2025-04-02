package cn.mu00.tools.bookmark.mapper;

import cn.mu00.tools.bookmark.domain.BmInfo;
import cn.mu00.tools.bookmark.domain.vo.BookmarkVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BmInfoMapper extends BaseMapper<BmInfo> {
    List<BookmarkVo> listBookmarkVo(@Param("categoryId") String categoryId,@Param("statusList") List<Integer> statusList);
}
