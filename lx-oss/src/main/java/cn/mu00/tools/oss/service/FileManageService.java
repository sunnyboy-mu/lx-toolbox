package cn.mu00.tools.oss.service;

import cn.mu00.tools.oss.domain.vo.FileInfoVo;
import org.springframework.web.multipart.MultipartFile;

public interface FileManageService {

    /**
     * 上传公共图片
     */
    FileInfoVo uploadCommonImage(MultipartFile file);

    /**
     * 上传公共视频
     */
    FileInfoVo uploadCommonVideo(MultipartFile file);

    /**
     * 上传 Bolg 图片
     */
    FileInfoVo uploadBlogImage(MultipartFile file);


    /**
     * 上传图床图片
     * @param category 分类 comic: 动漫, scenic: 风景
     */
    FileInfoVo uploadBedImage(MultipartFile file, String category);

    /**
     * 删除图床图片
     */
    String deleteBedImage(String id);

}
