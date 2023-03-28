package com.jiang.blog.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;



@Controller
public class Constant {

    public static String FILE_UPLOAD_DIR;
    public static String SECRET;
    public static Long EXPIRATION;

    // 静态变量不能用@Value赋值 重配置文件拿环境变量
    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        Constant.FILE_UPLOAD_DIR = fileUploadDir;
    }


    @Value("${jwt.secret}")
    public void setSECRET(String secret) {
        Constant.SECRET = secret;
    }

    @Value("${jwt.expiration}")
    public void setEXPIRATION(Long expiration) {
        Constant.EXPIRATION = expiration;
    }
}
