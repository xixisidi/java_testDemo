/*
 * @(#)Digest.java    Created on 2015年11月30日
 * Copyright (c) 2015 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package RSA;

import java.security.MessageDigest;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2015年11月30日 下午3:26:59 $
 */
public class Digest {

    /**
     * 计算字符串的SHA数字摘要，以byte[]形式返回
     */
    public static byte[] MdigestSHA(String source) {
        // byte[] nullreturn = { 0 };
        try {
            MessageDigest thisMD = MessageDigest.getInstance("SHA");
            byte[] digest = thisMD.digest(source.getBytes("UTF8"));
            return digest;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 计算byte[]的SHA数字摘要，以byte[]形式返回
     */
    public static byte[] MdigestSHA(byte[] source) {
        // byte[] nullreturn = { 0 };
        try {
            MessageDigest thisMD = MessageDigest.getInstance("SHA");
            byte[] digest = thisMD.digest(source);
            return digest;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 计算字符串的MD5数字摘要，以byte[]形式返回
     */
    public static byte[] MdigestMD5(String source) {
        // byte[] nullreturn = { 0 };
        try {
            MessageDigest thisMD = MessageDigest.getInstance("MD5");
            byte[] digest = thisMD.digest(source.getBytes("UTF8"));
            return digest;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 计算byte[]的数MD5字摘要，以byte[]形式返回
     */
    public static byte[] MdigestMD5(byte[] source) {
        // byte[] nullreturn = { 0 };
        try {
            MessageDigest thisMD = MessageDigest.getInstance("MD5");
            byte[] digest = thisMD.digest(source);
            return digest;
        }
        catch (Exception e) {
            return null;
        }
    }

}
