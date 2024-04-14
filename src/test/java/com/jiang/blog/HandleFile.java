package com.jiang.blog;


import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.junit.jupiter.api.Test;




public class HandleFile {
    // public  肉便器
    // private  自己
    // protected    包内所有类，其他包子类
    // default 同包 内
    // 除了public 可以修改父类属性其他按照以上规则可以访问
    // super 父类对象的引用
    MinioClient minioClient;
    private String endpoint = "http://192.168.0.108:9000";
    private String accessKey = "897965465ok";
    private String secretKey = "897965465ok";

    @Test
    public void Fff() {
        try {
            minioClient = new MinioClient(endpoint, accessKey, secretKey);
        } catch (InvalidEndpointException e) {
            throw new RuntimeException(e);
        } catch (InvalidPortException e) {
            throw new RuntimeException(e);
        }
    }


}
