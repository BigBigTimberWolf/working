package com.glitter.working.module.security.encryption;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.dc.pr.PRError;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-06
 **/
public class RSACoder {
    /*加密方式*/
    private static final String KEY_ALGORITHM = "RSA";
    /*公钥*/
    private static final String PUBLIC_KEY="Public_Key";
    /*私钥*/
    private static final String PRIVATE_KEY="Private_Key";
    private static Cipher PRIVATE_CIPHER;
    private static Cipher PUBLIC_CIPHER;
    /*密钥长度*/
    private static  final int KEY_SIZE=1024;

    private static Map<String, RSAKey> keyMap=new HashMap<>();

    public RSACoder() throws NoSuchAlgorithmException {
        initKey();
        initPrivateCipher();
        initPublicCipher();
    }
    //初始密钥
    private void initKey() throws NoSuchAlgorithmException {
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        keyMap.put(PUBLIC_KEY,publicKey);
        keyMap.put(PRIVATE_KEY,privateKey);
    }
    private void initPrivateCipher(){
      try {
          //取得私钥
          PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(getPrivateKey());
          KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
          //生成私钥
          PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
          PRIVATE_CIPHER = Cipher.getInstance(keyFactory.getAlgorithm());
          PRIVATE_CIPHER.init(Cipher.ENCRYPT_MODE, privateKey);
      }catch (Exception e){
          e.printStackTrace();
      }
    }
    private void initPublicCipher(){
       try {
           //实例化密钥工厂
           KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
           //初始化公钥
           //密钥材料转换
           X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(getPublicKey());
           //产生公钥
           PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
           PUBLIC_CIPHER = Cipher.getInstance(keyFactory.getAlgorithm());
           PUBLIC_CIPHER.init(Cipher.DECRYPT_MODE, pubKey);
       }catch (Exception e){
           e.printStackTrace();
       }
    }
    public String encryptByPrivateKey(String data) throws BadPaddingException, IllegalBlockSizeException {
        if(StringUtils.isNotEmpty(data)){
            byte[] bytes = encryptByPrivateKey(data.getBytes());
            return Base64.encodeBase64String(bytes);
        }
        return null;
    }

    public String decryptByPublicKey(String data) throws BadPaddingException, IllegalBlockSizeException {
        if(StringUtils.isNotEmpty(data)){
            byte[] bytes = decryptByPublicKey(Base64.decodeBase64(data));
            return new String(bytes);
        }else {
            return null;
        }
    }


    public byte[] encryptByPrivateKey(byte[] data) throws BadPaddingException, IllegalBlockSizeException {
        return PRIVATE_CIPHER.doFinal(data);
    }

    public byte[] decryptByPublicKey(byte[] data) throws BadPaddingException, IllegalBlockSizeException {
        return PUBLIC_CIPHER.doFinal(data);
    }

    private byte[] getPrivateKey() {
        Key key = (Key) this.keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    private byte[] getPublicKey() {
        Key key = (Key) this.keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }
}
