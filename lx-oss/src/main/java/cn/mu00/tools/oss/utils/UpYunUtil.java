package cn.mu00.tools.oss.utils;


import cn.mu00.tools.oss.domain.OssConfig;
import cn.mu00.tools.oss.service.OssConfigService;
import com.upyun.RestManager;
import com.upyun.UpException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class UpYunUtil {

    @Autowired
    private OssConfigService ossConfigService;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UpYunUtil.class);

    private RestManager upYunRestManager;

    public void resetUpYunRestManager(){
        OssConfig ossConfig = ossConfigService.getOssConfigDetail();
        this.upYunRestManager = getUpYunRestManager(ossConfig);
    }


    public static RestManager getUpYunRestManager(OssConfig ossConfig){
        return new RestManager(ossConfig.getBucket(), ossConfig.getAccount(), ossConfig.getPassword());
    }

    public Boolean upload(String filePath, byte[] fileBytes) throws UpException, IOException {
        return upload(upYunRestManager, filePath, fileBytes);
    }

    public Boolean delete(String filePath) throws UpException, IOException {
        return delete(upYunRestManager, filePath);
    }

    public static Boolean upload(RestManager manager, String filePath, byte[] fileBytes) throws UpException, IOException {
        return manager.writeFile(filePath, fileBytes, null).code() == 200;
    }

    public static Boolean delete(RestManager manager, String filePath) throws UpException, IOException {
        return manager.deleteFile(filePath, null).code() == 200;
    }

    @PostConstruct
    public void refreshUpYunTemplate() {
        resetUpYunRestManager();
        log.info("UpYunUtil，初始化成功!");
    }
}
