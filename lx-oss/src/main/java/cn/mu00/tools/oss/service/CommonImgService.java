package cn.mu00.tools.oss.service;

import cn.mu00.tools.oss.domain.OssConfig;
import cn.mu00.tools.oss.domain.vo.ImageVo;
import org.springframework.web.multipart.MultipartFile;

public interface CommonImgService {
    ImageVo upload(OssConfig ossConfig, MultipartFile file);

    String delete(String filePath);

    ImageVo updateBlogImg(MultipartFile file);
}
