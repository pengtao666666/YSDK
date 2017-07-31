package com.hjr.sdkkit.framework.aes;

public class AESUtil {

	static{
		System.loadLibrary("hjrsdkkit");
	}
	
	
	// AES加密方法
	public static native String s_Encryption(String src, int len, String key);

	
	// AES解密方法
	public static native String s_Decryption(String src, int len, String key);
	
}