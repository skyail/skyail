package com.skyail.enc.util.sm;

import com.skyail.enc.util.gmhelper.SM2Util;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.util.Map;

/**
 * @Description: 签名验签工具类
 * @Auther: zhouxw
 * @Date: 2019/10/8 10:33
 * @company：CTTIC
 */
@Slf4j
public class SignUtil {

	/**
	   * @Description 执行签名
	   * @Author zhouxw
	   * @Date 2019/10/8 10:49
	   * @Company CTTIC
	   * @Param [primaryKey(私钥十六进制字符串), srcData(要加密的源数据)]
	   * @return java.lang.String
	   **/
	public static String sign(String privateKey, String srcData) throws Exception {
		log.info("-->>执行签名：");
		log.info("-->>primaryKey is " + privateKey);
		log.info("-->>srcData is " + srcData);
		ECPrivateKeyParameters priKey = EncAndDecUtil.getPrivateKeyByPrivateKeyString(privateKey);
		byte[] sign = SM2Util.sign(priKey, srcData.getBytes());
		String signStr = ByteUtils.toHexString(sign).toUpperCase();
		log.info("-->>签名之后的串为:\n" + signStr);
		return signStr;
	}

	/**
	   * @Description 执行验签
	   * @Author zhouxw
	   * @Date 2019/10/8 10:50
	   * @Company CTTIC
	   * @Param [publicKey(公钥十六进制字符串), sign(签名串), srcData(要签名的源数据)]
	   * @return boolean
	   **/
	public static boolean verify(String publicKey, String sign, String srcData) throws Exception {
		log.info("-->>执行验签：");
		log.info("-->>publicKey is " + publicKey);
		log.info("-->>sign is " + sign);
		log.info("-->>srcData is " + srcData);
		ECPublicKeyParameters pubKey = EncAndDecUtil.getPublicKeyByPublicKeyString(publicKey);
		boolean flag = SM2Util.verify(pubKey, srcData.getBytes(), ByteUtils.fromHexString(sign));
		log.info("-->>验签结果：" + flag);
		return flag;
	}

	public static void test(String[] args) throws Exception {
		Map<String, String> map = EncAndDecUtil.generateKeyPairSM2();
		/*String primaryKey = (String)map.get("primaryKey");
		String publicKey = (String)map.get("publicKey");*/
		String privateKey = "26044942D5E6E8C636CB3B0917BDF6D9CD321B879EF3E5E3EC4D60F00C9F2454";
		String publicKey = "04A79020ADA3CD036534F0FF98E5A37F96636D9654A189CBDE24BFEF1AADE9FC4FEDF5AF6A0317E07C091079A4E5F7F9F1815451E2207AEA8F387217FC981DDF86";
		System.out.println("公钥：" + publicKey + "\n私钥：" + privateKey);
		/*String srcData = "123123";
		String sign = sign(privateKey, srcData);*/

		String srcData = "123123";
		String key = "43bef9af3c6f0765d60d58105ccbc910";
		String sm4 = EncAndDecUtil.encryptSM4(key ,srcData );
		String crc = EncAndDecUtil.encryptSM3(sm4);

		//String crc = "1972ae7c30da977f43f6d682b2840f67";
		String sign = "3046022100947C18F349516E00BDD17A36EE26F4C9578E46CD0C41EE6CB6E7DF58223E04C1022100AE42FA76330F94AD8B870581548CDF9313134696872D7380495D7410ACBF0BD1";
		System.out.println("sign is "+sign);
		boolean flag = verify(publicKey, sign, crc);
		System.out.println("验签之后的结果为：" + flag);
	}
}
