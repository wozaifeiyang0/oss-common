package site.tanghy.oss;

import com.aliyun.oss.OSSClient;

import java.io.IOException;

public interface OSSCreator {
    OSSClient generate(String endpoint) throws IOException;
}
