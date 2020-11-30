package site.tanghy.oss;

import com.aliyun.oss.OSSClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class OSSJianjuCreator implements OSSCreator{

    private String defaultPath = System.getProperty("user.home") + "/.oss/ak";

    private final String accessKeyId = "accessKeyId";
    private final String accessKeySecret = "accessKeySecret";

    public OSSJianjuCreator() {

    }

    public OSSJianjuCreator(String path){
        this.defaultPath = path;
    }

    public OSSClient generate(String endpoint) {
        Properties properties = new Properties();
        // 判断目录是否存在，不存在时创建目录文件
        createFile();
        try {
            properties.load(new FileInputStream(defaultPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String key = properties.getProperty(accessKeyId);
        String secret = properties.getProperty(accessKeySecret);

        return new OSSClient(endpoint, key, secret);
    }

    private boolean createFile() {
        File file = new File(defaultPath);
        // 如果存在直接返回 true
        if (file.exists()) {
            return true;
        }

        // 判断父目录是否存在
        File parentFile = file.getParentFile();
        // 不存在创建目录
        if (null != parentFile && !parentFile.exists()) {
            boolean mkdirs = parentFile.mkdirs();
            // 创建失败返回 false
            if (!mkdirs) {
                return false;
            }
        }

        try {
            // 创建文件，成功返回 true
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file, true);
                fileWriter.write(accessKeyId + "=\n");
                fileWriter.write(accessKeySecret + "=");
                fileWriter.close();
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
