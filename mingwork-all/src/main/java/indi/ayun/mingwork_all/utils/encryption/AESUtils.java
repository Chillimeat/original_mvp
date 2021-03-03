package indi.ayun.mingwork_all.utils.encryption;

import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description:主要功能: AES对称加密 （Advanced Encryption Standard，高级数据加密标准，AES算法可以有效抵制针对DES的攻击算法，对称加密算法）
 * AES加密：
 * 1. 介绍：
 *      在密码学中又称Rijndael加密法，是美国联邦政府采用的一种区块加密标准。这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用。经过五年的甄选流程，
 *      高级加密标准由美国国家标准与技术研究院（NIST）于2001年11月26日发布于FIPS PUB 197，并在2002年5月26日成为有效的标准。2006年，高级加密标准已然成为对称
 *      密钥加密中最流行的算法之一。
 * 2. 内容：
 *      0和1组成的明文块被分成若干个明文块，每个明文块128b，通过AES加密器结合密钥加密，密钥为128位，192位或256位，加密成若干个（与明文块数量相当）密文块，
 *      密文块同样是128b。所以AES加密也成为块加密。
 */

public class AESUtils {


    /*
     * 生成密钥
     */
    public static byte[] initKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
    }

    /*
     * AES 加密
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherBytes = cipher.doFinal(data);
        return cipherBytes;
    }

    /*
     * AES 解密
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] plainBytes = cipher.doFinal(data);
        return plainBytes;
    }


    private SecretKeySpec skeySpec;
    private Cipher cipher;

    public AESUtils(byte[] keyraw) throws Exception {
        if (keyraw == null) {
            byte[] bytesOfMessage = "".getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(bytesOfMessage);

            skeySpec = new SecretKeySpec(bytes, "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } else {

            skeySpec = new SecretKeySpec(keyraw, "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        }
    }

    public AESUtils(String passphrase) throws Exception {
        byte[] bytesOfMessage = passphrase.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        skeySpec = new SecretKeySpec(thedigest, "AES");
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    }

    public AESUtils() throws Exception {
        byte[] bytesOfMessage = "".getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        skeySpec = new SecretKeySpec(thedigest, "AES");

        skeySpec = new SecretKeySpec(new byte[16], "AES");
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    }

    /**
     * 加密:先AES再base64
     *
     * @param plaintext
     * @return
     * @throws Exception
     */
    public String encrypt(String plaintext) throws Exception {

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

        byte[] ciphertext = cipher.doFinal(plaintext.getBytes("UTF-8"));

        return Base64.encodeToString(ciphertext, Base64.DEFAULT);
    }

    /**
     * 解密:先base64再AES
     * @param ciphertext
     * @return
     * @throws Exception
     */
    public String decrypt(String ciphertext) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);

        byte[] plain64text = Base64.decode(ciphertext, Base64.DEFAULT);

        byte[] plaintext = cipher.doFinal(plain64text);

        return new String(plaintext, "UTF-8");
    }

}