package cn.mu00.tools.oss.utils;

import cn.mu00.tools.oss.domain.vo.FileInfoVo;
import com.upyun.RestManager;
import com.upyun.UpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@Slf4j
public class UpYunUtil {

    private RestManager restManager;

    @Value("${upyun.bucket}")
    private String bucket;

    @Value("${upyun.account}")
    private String account;

    @Value("${upyun.password}")
    private String password;

    @Value("${upyun.domain-url}")
    private String domainUrl;

    @Value("${upyun.domain-suffix}")
    private String domainSuffix;




    /**
     * 上传文件
     * @param fileName 文件名
     * @param filePath  文件路径
     * @param fileBytes 文件字节数组
     * @throws UpException 上传异常
     * @throws IOException IO异常
     * @return 文件信息
     */
    public FileInfoVo upload(String fileName, String filePath, byte[] fileBytes) throws UpException, IOException {
        this.restManager.writeFile(filePath, fileBytes, null);
        String url = domainUrl + filePath + domainSuffix;
        return FileInfoVo.builder().name(fileName).url(url).path(filePath).size((long) fileBytes.length).build();
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @throws UpException 删除异常
     * @throws IOException IO异常
     */
    public void delete(String filePath) throws UpException, IOException {
        this.restManager.deleteFile(filePath, null);
    }

    @PostConstruct
    public void refreshUpYunTemplate() {
        this.restManager = new RestManager(this.bucket, this.account, this.password);
        log.info("UpYunUtil，初始化成功!");
    }
}
