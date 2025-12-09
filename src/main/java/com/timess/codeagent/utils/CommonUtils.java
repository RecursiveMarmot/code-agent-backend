package com.timess.codeagent.utils;

import org.springframework.util.DigestUtils;

/**
 * @author 33363
 */
public class CommonUtils {

    public static String getEncryptPassword(String userPassword) {
        // 盐值，混淆密码
        final String SALT = "timess";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }

}
