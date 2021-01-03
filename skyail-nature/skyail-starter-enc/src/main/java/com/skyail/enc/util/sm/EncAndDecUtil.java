package com.skyail.enc.util.sm;

import com.skyail.enc.constant.EncAndDecConstant;
import com.skyail.enc.util.gmhelper.SM3Util;
import com.skyail.enc.util.gmhelper.BCECUtil;
import com.skyail.enc.util.gmhelper.SM2Util;
import com.skyail.enc.util.gmhelper.SM4Util;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static com.skyail.enc.util.gmhelper.SM4Util.encrypt_Ecb_Padding;

/**
 * @Description: 加密解密工具类
 * @Auther: zhouxw
 * @Date: 2019/10/8 10:33
 * @company：CTTIC
 */
@Slf4j
public class EncAndDecUtil {


	//****************************************SM2加密解密部分*****************************************************
	/**
	   * @Description 生成公钥私钥对，返回公私钥的十六进制字符串
	   * @Author zhouxw
	   * @Date 2019/10/8 10:40
	   * @Company CTTIC
	   * @Param []
	   * @return java.util.Map<java.lang.String,java.lang.String>
	   **/
	public static Map<String, String> generateKeyPairSM2() {
		AsymmetricCipherKeyPair keyPair = SM2Util.generateKeyPairParameter();
		ECPrivateKeyParameters priKey = (ECPrivateKeyParameters)keyPair.getPrivate();
		ECPublicKeyParameters pubKey = (ECPublicKeyParameters)keyPair.getPublic();
		String primaryKey = ByteUtils.toHexString(priKey.getD().toByteArray()).toUpperCase();
		String publicKey = ByteUtils.toHexString(pubKey.getQ().getEncoded(false)).toUpperCase();
		Map<String, String> map = new HashMap();
		map.put(EncAndDecConstant.PRIVATE_KEY, primaryKey);
		map.put(EncAndDecConstant.PUBLIC_KEY, publicKey);

		log.info("-->>私钥：" + primaryKey);
		log.info("-->>公钥：" + publicKey);
		return map;
	}

	/**
	   * @Description 通过公钥十六进制字符串获取公钥对象
	   * @Author zhouxw
	   * @Date 2019/10/8 10:43
	   * @Company CTTIC
	   * @Param [publicKeyString(公钥十六进制字符串)]
	   * @return org.bouncycastle.crypto.params.ECPublicKeyParameters
	   **/
	public static ECPublicKeyParameters getPublicKeyByPublicKeyString(String publicKeyString) {
		String pointString = publicKeyString.substring(2);
		String xHex = pointString.substring(0, pointString.length() / 2);
		String yHex = pointString.substring(pointString.length() / 2);
		ECPublicKeyParameters pubKey = BCECUtil.createECPublicKeyParameters(xHex, yHex, SM2Util.CURVE, SM2Util.DOMAIN_PARAMS);
		return pubKey;
	}

	/**
	   * @Description 执行加密SM2
	   * @Author zhouxw
	   * @Date 2019/10/8 10:44
	   * @Company CTTIC
	   * @Param [publicKey(公钥十六进制字符串), srcData(需要加密的源数据)]
	   * @return java.lang.String
	   **/
	public static String encryptSM2(String publicKey, String srcData) throws Exception {
		log.info("-->>加密开始：");
		log.info("-->>publicKey is " + publicKey);
		log.info("-->>srcData is " + srcData);
		ECPublicKeyParameters pubKey = getPublicKeyByPublicKeyString(publicKey);
		byte[] encryptedData = SM2Util.encrypt(pubKey, srcData.getBytes("UTF-8"));
		String result = ByteUtils.toHexString(encryptedData);
		log.info("-->>加密的结果为：" + result);
		return result;
	}

	/**
	   * @Description 根据给定的私钥十六进制字符串获取私钥对象
	   * @Author zhouxw
	   * @Date 2019/10/8 10:45
	   * @Company CTTIC
	   * @Param [privateKeyString(私钥十六进制字符串)]
	   * @return org.bouncycastle.crypto.params.ECPrivateKeyParameters
	   **/
	public static ECPrivateKeyParameters getPrivateKeyByPrivateKeyString(String privateKeyString) {
		ECPrivateKeyParameters priKey = new ECPrivateKeyParameters(new BigInteger(ByteUtils.fromHexString(privateKeyString)), SM2Util.DOMAIN_PARAMS);
		return priKey;
	}

	/**
	   * @Description 执行解密SM2
	   * @Author zhouxw
	   * @Date 2019/10/8 10:47
	   * @Company CTTIC
	   * @Param [privateKey(私钥十六进制字符串), encryptedData(加密之后的加密串)]
	   * @return java.lang.String
	   **/
	public static String decryptSM2(String privateKey, String encryptedData) throws Exception {
		log.info("-->>开始解密：");
		log.info("-->>primaryKey is " + privateKey);
		log.info("-->>encryptedData is " + encryptedData);
		ECPrivateKeyParameters priKey = getPrivateKeyByPrivateKeyString(privateKey);
		byte[] decryptedData = SM2Util.decrypt(priKey, ByteUtils.fromHexString(encryptedData));
		String result = new String(decryptedData,"UTF-8");
		log.info("-->>解密之后的结果为：" + result);
		return result;
	}








	//**********************SM4加密解密部分************************************************************

	/**
	   * @Description 获取SM4加密串，返回加密串的十六进制字符串
	   * @Author zhouxw
	   * @Date 2019/10/8 17:32 
	   * @Company CTTIC
	   * @Param []
	   * @return java.lang.String
	   **/
	public static String generateKeySM4() throws Exception{
		byte[] key = SM4Util.generateKey();
		String result = ByteUtils.toHexString(key);
		return result;
	}

	/**
	   * @Description 执行加密 SM4，返回加密之后的十六进制字符串
	   * @Author zhouxw
	   * @Date 2019/10/8 17:38
	   * @Company CTTIC
	   * @Param [key(key的16进制字符串), srcData(要加密的源数据)]
	   * @return java.lang.String
	   **/
	public static String encryptSM4(String key , String srcData) throws Exception{
		byte[] keyByte = ByteUtils.fromHexString(key);
		byte[] res = encrypt_Ecb_Padding(keyByte,srcData.getBytes("UTF-8"));
		return ByteUtils.toHexString(res);
	}

	/**
	   * @Description 执行解密 SM4
	   * @Author zhouxw
	   * @Date 2019/10/8 17:41
	   * @Company CTTIC
	   * @Param [key(密钥的十六进制字符串), secretData(加密之后的16进制字符串)]
	   * @return java.lang.String
	   **/
	public static String decryptSM4(String key ,String secretData) throws Exception{
		byte[] keyByte = ByteUtils.fromHexString(key);
		byte[] sData = ByteUtils.fromHexString(secretData);
		byte[] decryptedData = SM4Util.decrypt_Ecb_NoPadding(keyByte, sData);
		log.info("执行解密：解密之后的 byte[] 为["+decryptedData+"]  ");
		String result = new String (decryptedData,"UTF-8");
		//String result = ByteUtils.toHexString(decryptedData);
		return result;
	}


	//*****************************SM3部分********************************************************
	/**
	   * @Description 执行加密 SM3
	   * @Author zhouxw
	   * @Date 2019/10/8 21:06
	   * @Company CTTIC
	   * @Param [srcData]
	   * @return java.lang.String
	   **/
	public static String encryptSM3(String srcData) throws Exception{
		byte[] hash = SM3Util.hash(srcData.getBytes("UTF-8"));
		return ByteUtils.toHexString(hash);
	}


	/*public static void main(String[] args) throws Exception{
		Map<String , String> map = generateKeyPairSM2();
		System.out.println("公钥为：\n"+map.get(EncAndDecConstant.PUBLIC_KEY));
		System.out.println("私钥为：\n"+map.get(EncAndDecConstant.PRIVATE_KEY));



		//String key = generateKeySM4();
		String key = "2b18b26f5fbc5f0074af5416cfe72fb0";
		String srcData = "26044942D5E6E8C636CB3B0917BDF6D9CD321B879EF3E5E3EC4D60F00C9F2454";
		System.out.println("明文："+srcData);
		System.out.println("key : "+key);
		String sData = encryptSM4(key,srcData);
		System.out.println("密文："+sData);
		String pData = decryptSM4(key ,sData);
		System.out.println("解密之后的明文："+pData);

	}*/

}
