package com.skyail.example;

import com.skyail.enc.constant.EncAndDecConstant;
import com.skyail.enc.util.sm.EncAndDecUtil;
import com.skyail.enc.util.sm.SignUtil;

import java.util.Map;

public class EncAndDecTest {

    public static void main(String[] args) throws Exception{
        //1.SM3加密测试
        String sm3Str = EncAndDecUtil.encryptSM3("123123");
        System.out.println("SM3 加密之后的结果： " + sm3Str);
        //2.SM4加解密测试
        String sm4Key = EncAndDecUtil.generateKeySM4();
        String sm4EncStr = EncAndDecUtil.encryptSM4(sm4Key,"123123");
        System.out.println("SM4 加密之后的结果： " + sm4EncStr);
        String sm4DecStr = EncAndDecUtil.decryptSM4(sm4Key,sm4EncStr);
        System.out.println("SM4 解密之后的结果： " + sm4DecStr);
        //3.SM2加密解密测试
        Map<String,String> keyPair = EncAndDecUtil.generateKeyPairSM2();
        String privateKey = keyPair.get(EncAndDecConstant.PRIVATE_KEY);
        String publicKey = keyPair.get(EncAndDecConstant.PUBLIC_KEY);
        String sm2EncStr = EncAndDecUtil.encryptSM2(publicKey,"123123");
        String sm2DecStr = EncAndDecUtil.decryptSM2(privateKey,sm2EncStr);
        System.out.println("SM2 加密之后的结果为： " + sm2EncStr);
        System.out.println("SM2 解密之后的结果为： " + sm2DecStr);
        //4.SM2签名与验签测试
        //使用私钥签名
        String sign = SignUtil.sign(privateKey,"123123");
        //使用公钥验签
        boolean verifyRes = SignUtil.verify(publicKey,sign,"123123");
        System.out.println("SM2 验签结果为：" + verifyRes);

    }
}
