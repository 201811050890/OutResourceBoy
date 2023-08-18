package my.util;

import cn.hutool.core.codec.Base64;
import my.domain.LoginEncryptBO;

/**
 * @author OutResource Boy
 * @date 2023/8/18 15:39
 */
public class TokenUtil {
    /**
     * base64 隐藏数据
     */
    public static String encrypt(LoginEncryptBO loginEncryptBO){
        String token = loginEncryptBO.getToken();
        long currentTimeMillis = loginEncryptBO.getTimeMillis();
        String baseToken = token + currentTimeMillis;
        return Base64.encode(baseToken);
    }

    /**
     * base64 解析数据
     */
    public static LoginEncryptBO decode(String token){
        String decryptToken;
        String decryptStr = Base64.decodeStr(token);
        decryptToken = decryptStr.substring(0,32);
        // 创建token的时间，token使用时效性，防止攻击者通过一堆的尝试找到aes的密码，虽然aes是目前几乎最好的加密算法
        long createTokenTime = Long.parseLong(decryptStr.substring(32,45));
        return new LoginEncryptBO(decryptToken, createTokenTime);
    }
}
