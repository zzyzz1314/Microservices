package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.result.JSONResult;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/oss")
public class OSSController {
    @PostMapping("/uploadFile")
    public JSONResult uploadFile(@RequestParam MultipartFile fileName){

        // Endpoint以华东1（杭州）为例，填写为https://oss-cn-hangzhou.aliyuncs.com，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
        String bucketName = ("zsq-zz");
        // 填写Bucket所在地域。以华东1（杭州）为例，Region填写为cn-hangzhou。
        String region = "cn-shenzhen";
        OSS ossClient = null;
        try {
            // 从环境变量中获取访问凭证。运行本代码示例之前，请先配置环境变量
            EnvironmentVariableCredentialsProvider credentialsProvider =
                    CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
            // 创建OSSClient实例。
            // 当OSSClient实例不再使用时，调用shutdown方法以释放资源。
            ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
            // 显式声明使用 V4 签名算法
            clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
            ossClient = OSSClientBuilder.create()
                    .endpoint(endpoint)
                    .credentialsProvider(credentialsProvider)
                    .region(region)
                    .build();
            // 2. 上传文件
            String format = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            //获取后缀
            String substring = fileName.getOriginalFilename().substring(fileName.getOriginalFilename().lastIndexOf("."));
            String objectName = format+"/"+System.currentTimeMillis()+substring;
            ossClient.putObject(bucketName, objectName,fileName.getInputStream());
            System.out.println(" 文件 " + objectName + " 上传成功。");
            //https://zsq-zz.oss-cn-shenzhen.aliyuncs.com/2025/08/15/1755244315697.png
            return JSONResult.success("https://zsq-zz.oss-cn-shenzhen.aliyuncs.com/"+objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return JSONResult.error("上传失败");
    }

}
