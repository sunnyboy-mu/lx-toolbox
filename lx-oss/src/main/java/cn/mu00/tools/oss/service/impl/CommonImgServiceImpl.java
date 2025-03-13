package cn.mu00.tools.oss.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.mu00.tools.common.constant.UserRole;
import cn.mu00.tools.common.exception.ServiceException;
import cn.mu00.tools.oss.domain.OssConfig;
import cn.mu00.tools.oss.domain.vo.ImageVo;
import cn.mu00.tools.oss.service.CommonImgService;
import cn.mu00.tools.oss.service.OssConfigService;
import cn.mu00.tools.oss.utils.FileUtil;
import cn.mu00.tools.oss.utils.UpYunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class CommonImgServiceImpl implements CommonImgService {

    @Autowired
    private UpYunUtil upYunUtil;

    @Autowired
    private OssConfigService ossConfigService;

    @Override
    public ImageVo upload(OssConfig ossConfig, MultipartFile file) {
        try {
            // 文件拓展名
            String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            // 文件名
            String fileName = FileUtil.timestampFileName() + suffix;
            // 文件路径
            String filePath = FileUtil.dateDir() + "/" + fileName;
            // 判断是否登录并且角色是admin
            if (StpUtil.isLogin() && StpUtil.hasRole(UserRole.ADMIN)){
                // 系统OSS配置
                ossConfig = ossConfigService.getOssConfigDetail();
                // 上传文件
                upYunUtil.upload(filePath,file.getBytes());
            }else {
                checkFileForCommonUser(file);
                UpYunUtil.upload(UpYunUtil.getUpYunRestManager(ossConfig),filePath,file.getBytes());
            }
            return new ImageVo(fileName, ossConfig.getCdnDomain() + filePath, filePath);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), 500);
        }
    }

    @Override
    public String delete(String filePath) {
        try {
            upYunUtil.delete(filePath);
            return "删除成功";
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), 500);
        }
    }

    private void checkFileForCommonUser(MultipartFile file){
        // 文件大小小于10m
        if (file.getSize() > 10 * 1024 * 1024){
            throw new ServiceException("文件大小不能超过10m", 500);
        }
        // 文件类型只允许是 jpg png webp gif
        if (!FileUtil.isImage(file.getName())){
            throw new ServiceException("文件类型只允许是 jpg png webp jpeg gif", 500);
        }
    }

}
