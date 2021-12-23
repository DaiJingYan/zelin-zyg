package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;
public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016110200786382";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCHB1LOSNReLpg96pMlUz3KMTOUfGQM4lU58BjU+kSV3wxD6ZzFqv72nWcmRiJezOtnf1R/GWZptji3YXU3g6xMfypmnoQcBh90MIj60tnzeFNoH/4HHFGsTxFMZnqN56hAgOWkJg8kootKt+0mLzlWoRGvzRV9p3aOpYbxDRZphcTkkG1M5CkB2LxyZv6LY8txpdNSKamzFvwEIPVT664q14CpO9wgjJ6C9XssA+agR7Ch+k3IHw62JLnQ996KtmcGPr2c0xzj7BUJyMwPpN2ewihbNYLIFFj62DJ8xf0o2jSq/LRUd/BxnqsXcGhIoGYDf764Ve6Agl9KYpGfKCNjAgMBAAECggEAAoQDae53bm3FzlmBdk+PtiWmhVyv7IGG/vZXMBidkIFAVD+U+SXPbD7QfikOWN/OfvYjV97cRpnrgpchPPCKFD7GRnUrlu4SRfLlim7FOsECJM17yRwg9Wnfs6GxJNWtD86W+LDw1Ujl+XVlyLFWiyOjFbhnbxjgn5W75t/MjBi339gs9qtG0YwCPUvjeFNKaR1kcmcJhHSIMS5DQ83iSVQyfPCWJfsPYvMb69PeHulNqW2wx063TQLyA6qhDN7HlU2i3dDZ2ocsthzFaTYLn9yOHrdFN10j4XduNohlBw6sJVilqq2GAWZNBghxgvqkTyoARnZjgoPYUm7hDrxGcQKBgQDfZ6rUQZL9gc49SNuKMOAaxPKF5K6GMKQQJLpssCmb/bvZEYQ+95am3Iv0l3dGRkbcKXKB4ayT8yl7QVrrX7hRvkjT3W4u+fOLrtHbBGf6bb7JZWjZtUOS3ANpPkYBL+IU7UG2e/EqzXkOyTcXCoFzrj0R01QrC4WpBWsdQFgW+wKBgQCaurzA6bhuoTRDw2lUBTszoTXONFnRoyVraKKdLDfiHlSDFRSvs4FRLLP7y7w7IU4TbSJ8Oms5MaHsCN+utnRyxiHoL2oH1TVJ+f+jQx9b+kDOcda9EXuZVbKtk38HMxvvGFlJDBBgzxBYg5ch+slX2emvhePJjrOSxyA8eIgYuQKBgGppBoVzdoAphALM4UVBYn1dseb0texU3tCRrfp3LlqO0RrjrQoRBEEWa9NlEfr3HmlflJso0vrlp6x9xMC4JyNIb4f6wLC/fbHa4zpfLuepvmZPV1HbdQROp5FJiC6okBdHqzUwc/7fP/o6/XKMHnzrUUtcTYrzQgbv/cVBELRhAoGAMQlxfKoVu2n3iGpL3UbzJj+3L6zDsn7EAzBcKPe0lkMKgaiSr8Rx/WnveKNu+kZp2hpCTaCuDqg+KvH9Af/tE9rbMBymu+jERLeoXvOM4reDBmpppe3+f+/6+qu+HGgEOFg9I7thDW1SPtslcK1sVh1HxErokB5bpu7YtpF0gHECgYBVkC105wHwlC5LFSpOFXoGZB9TpCOPNfm5H1+Wl+1os6wVx97maZQXI4bzRYIHh6nFFCfqR/8+l8jS0j7V7FhUsc+ee/jk7smWzy41O+WTVOPob0BLDXtGGIc8Da/AevRdS5k11ZW/DkTgMSK14G5ZRJXSsK+OL2CDiNbniOqvVA==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr1mPk6rIzSaYkaOthD0zsivN4pln2o66yP1DdIuauV6ggAC6jkO7kfZHJmBYXY/oGXPSbWqdn2g8s6FHJzLAjT9XMOqhFtQh5mMCTr5/gdgN6oc9O/zYCD1xEV9pAXZ1rBufzXixjmlnA8lCNiLsRUcYjoAcUyDBPUP3iLTvFJm1XhmA3ogR5U2CR56wynIa+toaCLxEav9S6gkjALmLwFSkimySEUZB361f+kXCcO+riZwsO65uquXEH8p7z44hezkqT09kaEIQDAHXH6f/RhOzmAuNQHSD93DbzvjrWPyW2vYA/eQnPLNc+ctka/Nf6CH5X4XMfmp9q+4p19URcQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

