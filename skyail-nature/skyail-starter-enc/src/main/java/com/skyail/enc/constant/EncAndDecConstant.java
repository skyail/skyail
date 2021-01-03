package com.skyail.enc.constant;

/**
 * @Description: TODO
 * @Auther: zhouxw
 * @Date: 2019/10/7 18:19
 * @company：CTTIC
 */

public class EncAndDecConstant {

	//私钥
	public static final String PRIVATE_KEY = "privateKey";
	//公钥
	public static final String PUBLIC_KEY = "publicKey";
	//源数据
	public static final String SRC_DATA = "srcData";
	//私钥加密存入数据库时使用的SM4密钥
	public static final String SM4_KEY_FOR_PRIMARY_KEY = "2b18b26f5fbc5f0074af5416cfe72fb0";
	//加密解密-通用状态-数据有效
	public static final String COMMMON_STATE_EFFECTIVE = "U";
	//对crc进行的签名标识(map中使用)
	public static final String CRC_SIGN = "crcSign";
	//加密的报文标识(map中使用)
	public static final String SECRET_DATA = "secretData";
	//加密的key标识(map中使用)
	public static final String SECRET_KEY = "secretKey";

	public static final String CURRENT_SYSTEM_USERNAME = "MT999999PPT0001";
}
