package com.pjb.fileserver.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具类.
 */
public class MD5Util {
    private MD5Util() {

    }

    /**
     * 获取该输入流的MD5值
     */
    public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
        StringBuilder md5 = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];

        int nread;
        while ((nread = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        byte[] mdbytes = md.digest();
        for (byte mdbyte : mdbytes) {
            md5.append(Integer.toString((mdbyte & 0xff) + 0x100, 16).substring(1));
        }
        return md5.toString();
    }

}
