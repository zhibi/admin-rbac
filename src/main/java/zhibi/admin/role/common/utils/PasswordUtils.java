package zhibi.admin.role.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 密码生成工具类
 *
 * @author 执笔
 */
public class PasswordUtils {

    /**
     * 创建密码
     *
     * @param password
     * @param salt
     * @return
     */
    public static String createPwd(String password, String salt) {
        return new SimpleHash("md5", password, ByteSource.Util.bytes(salt), 2).toHex();
    }
}
