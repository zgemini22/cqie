package com.zds.user.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码工具类
 */
public final class SafeHashGenerator {

    private static final int STRETCH_COUNT = 1000;

    public static String getSaltedPassword(String password, String userId) {
        String salt = getSha256(userId);
        return getSha256(salt + password);
    }

    public static String getStretchedPassword(String password, String userId) {
        String salt = getSha256(userId);
        String hash = "";
        for (int i = 0; i < STRETCH_COUNT; i++) {
            hash = getSha256(hash + salt + password);
        }
        return hash;
    }

    public static void main(String[] args) {
        System.out.println(getStretchedPassword("123456", "19"));
    }

    private static String getSha256(String target) {
        MessageDigest md = null;
        StringBuffer buf = new StringBuffer();
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(target.getBytes());
            byte[] digest = md.digest();

            for (int i = 0; i < digest.length; i++) {
                buf.append(String.format("%02x", digest[i]));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public static String getStretchedText(String stretchedText) {
        String salt = getSha256(stretchedText);
        String hash = "";
        for (int i = 0; i < STRETCH_COUNT; i++) {
            hash = getSha256(hash + salt + stretchedText);
        }
        return hash;
    }
}
