package cn.zwh.ymcc;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;

public class Main {
    public static void main(String[] args){
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(getOptions());
        try {
            // 2. 发起API调用（以创建当面付收款二维码为例）
            /*AlipayTradePrecreateResponse response = Payment.FaceToFace()
                    .preCreate("Apple iPhone11 128G", "2234567890", "5799.00");*/
            AlipayTradePagePayResponse response = Payment.Page().pay("测试使用", "22345671890",
                    "1000.00", "http://www.baidu.com");
            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
            } else {
                System.err.println("调用失败，原因：" + response.getBody());
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi-sandbox.dl.alipaydev.com";
        config.signType = "RSA2";
        config.appId = "9021000151641192";
        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCoz9O6vMNBEyCSCS/aU12cVNMNrQutgELfjrZzaV+mljLevB1R4/f1w7xwqH9JJHO5HJvDt9GDN1/MLWEkYDUmz84hy02bDuqoNScRfSY5Gl4EN5mE1JbUGoXZEioAn9yaM1L6m8uNpmSXGgaPX4Lp7H8B5JFvmXcQa4JGvFET9taKIJBkT3Mx8McwODJM85iwU1hMepcXTKEpaIhwuJEEH81UdXOV80z5HhDNCFWf8nyVbFwJcCAZHNFoDKTLd1S0UunFZfILzwOlRBe0Yhq/omFfS5c6FLRCcUZJxW7h7lfMfxTJ8VDYMDxWWI7OWfykkx8rQXaWm+W1oUDO0U4hAgMBAAECggEARZC3YOmu0N9P+b49cQwPtH+kJauc91e2tye5eh//YcfdmLoOnxOBuE58FjTplzulULBAZEwY06j7GknNEU9wN9lEydpc1bw06TJWMjfPi5KZEcXTxtBQg4GWjNiWZIF8SDNES6/uFyZtp3L7JxQl/74MrLwDTukADs0u93VdOAtB0ubMwhw2/wmY7K702mIGwkOIML7Uu2zqKdMGJy1Q7U80h2qoVzo7QdbRJczyZlBvxY+ApF8dJT29yCyJ0N8PeXGSGWGLSRfzjxGGGY/X6xw2WYNSP4FPHTb+g80ycs2RHiwygUNmOUAC5rQEPR3gOkGq/UyZldV4EAkMHyaQAQKBgQDbU/Uo5lNcRctGCDl1LGMEgM2nNC4ZHQwz5SPtL3oAfffkddrooDyhpwGQBpsAX8Zwcicy4WoiM7xE7cmmO9jj2XRhfv/wx7FJ06Z5CuigiS68mphDP01qQNPOY08h3Tq61SHq3Hc4W9H4DIB4qH/AYMV2kKBN6DQTLGG2zuANgQKBgQDFCZfgPwtUr3THJ/osncPNWdMJS2tBqPtbaSFQ90GT8KbHR4tP9DhMvQZIyUA9SQR9mgorvsyQHbZJSoNH/bp5/Org21/KNLNzSQNAlPZjLdpfeEY1MYFMsYHBFvgQ+m0b4iWZzd/xce36D5ik2nIdUvYYAuSo/qUdYLImAPDQoQKBgHWJc7tcW1X6rSm1pJ0PkWzLXep6Ay5AK9bvuCYWqEn1N9B9/DdoD7YNmr/FWnGD4gNGmY0h0c/Ma9uAluz3646HbWAGQECXs4G949vAJdRKT/qQ2oJHPURww1E6VSDvvtZ6+3cyeM2CH27PvFVs7zF4arnUXaQCiU2xHhhqmxWBAoGAOkKV57Z8cTPYAbMuY9baOHPg1JS6SBXhDKxPL5OMjmPPzIi5BpEdvIL/wrHPDs/yRfkwHLMz5UEDzzP7EWRSqRNBl+8FqdJfZS0niwcAu16pfSUhDUVwB+9MgH18J/kEHgSGPa4EE24ugYvvBH+a0lTfc0YXumhhBgi7ZEVO70ECgYEAtplV0P3/eAH5XUII5QDI9ti9SJ4RkM7R0woB8OhoSpnSwKEe6SEQLVNyVxfJVTKOetko9sDFV4rzMC3fS4wRT9fisLtIejbmNTMpaWt8q4NxjC2EN5hBoz/IeBybe4Mf7xIJUvaiSM65gj7sZlA6IXCB19+RI9j0GZREZBd9CWo=";
        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
        config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl9IOzpCTq8at9UXuRy60/T0cwoPqPlrB4JcKKYZcq5auHSGnVzBqiJbYNHuSy8vqz/iT9gqcpJnEPZCNfRbcUXsqyMRl6pOeCDDA5TCtjex4jKeXBGy6EdvWYXApzsKzms5Kzsl7IJDArYwEK4c0wy99NyitBVJzAgbwYWv32/kgeoD0XStuyw2IGcQLqF5447Zo/1w1zY4/3YqbpZJsQnst/BhunBUPiuw812XnS8S0moVOFdMU7Z/26Ae/C1JCk0S2gTfB26yeOZylba2+t1SWROOZ2+bammZveFlmBJFHSSwNRWwGAoAIl5zMJ/pV66JUfS6fanb+znw6ilO1qwIDAQAB";
        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = "https://localhost:10010/callback";
        return config;
    }
}

