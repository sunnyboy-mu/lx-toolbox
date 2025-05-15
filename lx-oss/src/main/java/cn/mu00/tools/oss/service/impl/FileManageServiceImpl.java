package cn.mu00.tools.oss.service.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.mu00.tools.common.exception.ServiceException;
import cn.mu00.tools.common.utils.FileUtil;
import cn.mu00.tools.oss.constant.FileRootPath;
import cn.mu00.tools.oss.domain.BedImage;
import cn.mu00.tools.oss.domain.vo.FileInfoVo;
import cn.mu00.tools.oss.service.BedImageService;
import cn.mu00.tools.oss.service.FileManageService;
import cn.mu00.tools.oss.utils.UpYunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.InputStream;

@Service
public class FileManageServiceImpl implements FileManageService {

    @Autowired
    private UpYunUtil upYunUtil;

    @Autowired
    private BedImageService bedImageService;

    @Override
    public FileInfoVo uploadCommonImage(MultipartFile file) {
        this.checkIsImage(file.getName());
        String filePath = FileRootPath.COMMON + FileUtil.timestampFileName(file.getName());
        try {
            return upYunUtil.upload(file.getName(), filePath, file.getBytes());
        } catch (Exception e) {
            throw new ServiceException("文件上传失败！");
        }
    }

    @Override
    public FileInfoVo uploadCommonVideo(MultipartFile file) {
        if (!("mp4".equals(FileUtil.fileExt(file.getName())))) {
            throw new ServiceException("只允许上传mp4视频资源！");
        }
        String filePath = FileRootPath.COMMON +  FileUtil.timestampFileName(file.getName());
        try {
            return upYunUtil.upload(file.getName(), filePath, file.getBytes());
        } catch (Exception e) {
            throw new ServiceException("文件上传失败！");
        }
    }

    @Override
    public FileInfoVo uploadBlogImage(MultipartFile file) {
        this.checkIsImage(file.getName());
        String filePath = FileRootPath.BLOG +  FileUtil.dateDir(file.getName());
        try {
            return upYunUtil.upload(file.getName(), filePath, file.getBytes());
        } catch (Exception e) {
            throw new ServiceException("文件上传失败！");
        }
    }

    @Override
    public FileInfoVo uploadBedImage(MultipartFile file, String category) {
        this.checkIsImage(file.getName());
        String filePath = FileRootPath.BED +  FileUtil.dateDir(file.getName());
        try {
            String fileName = file.getName();
            FileInfoVo infoVo = upYunUtil.upload(fileName, filePath, file.getBytes());
            BedImage bedImage = new BedImage();
            bedImage.setName(fileName);
            bedImage.setUrl(infoVo.getUrl());
            bedImage.setPath(filePath);
            bedImage.setSize(file.getSize());
            bedImage.setCategory(category);
            bedImage.setType(getImageType(file));
            bedImageService.save(bedImage);
            return infoVo;
        } catch (Exception e) {
            throw new ServiceException("文件上传失败！");
        }
    }

    @Override
    public String deleteBedImage(String id) {
        BedImage bedImage = bedImageService.getById(id);
        try {
            if (ObjectUtil.isNotNull(bedImage)){
                upYunUtil.delete(bedImage.getPath());
                bedImageService.removeById(id);
                return "删除成功";
            }
        }catch (Exception e){
            throw new ServiceException("文件删除失败！");
        }
        return "ok";
    }


    private void checkIsImage(String filaName){
        if (!FileUtil.isImage(filaName)) {
            throw new ServiceException("只允许上传图片资源！");
        }
    }

    private String getImageType(MultipartFile file){
        try (InputStream inputStream = file.getInputStream()) {
            BufferedImage image = ImgUtil.read(inputStream);
            if (image == null) {
                throw new ServiceException("无效的图片文件");
            }
            int width = image.getWidth();
            int height = image.getHeight();
            double ratio = (double) width / height;
            // 判断是否为 PC 类型（16:9，允许误差 ±0.2）
            if (Math.abs(ratio - 16.0 / 9.0) < 0.2) {
                return "pc";
            }
            // 判断是否为 Avatar 类型（1:1，允许误差 ±0.1）
            else if (Math.abs(ratio - 1.0) < 0.1) {
                return "avatar";
            }
            // 判断是否为 Phone 类型（9:16，允许误差 ±0.1）
            else if (Math.abs(ratio - 9.0 / 16.0) < 0.1) {
                return "phone";
            }
            else {
                return null;
            }
        }catch (Exception e){
            throw new ServiceException("文件解析失败！");
        }
    }

}
