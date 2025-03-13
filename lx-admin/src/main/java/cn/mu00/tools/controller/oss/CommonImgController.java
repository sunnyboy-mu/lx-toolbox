package cn.mu00.tools.controller.oss;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.mu00.tools.common.constant.UserRole;
import cn.mu00.tools.common.domain.R;
import cn.mu00.tools.oss.domain.OssConfig;
import cn.mu00.tools.oss.domain.vo.ImageVo;
import cn.mu00.tools.oss.service.CommonImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class CommonImgController {

    @Autowired
    private CommonImgService commonImgService;

    @PostMapping("/upload.interface")
    public R<ImageVo> upload(OssConfig ossConfig, MultipartFile file){
        return R.ok(commonImgService.upload(ossConfig,file));
    }

    @SaCheckRole(UserRole.ADMIN)
    @PostMapping("delete")
    public R<String> delete(String filePath){
        return R.ok(commonImgService.delete(filePath));
    }

}
