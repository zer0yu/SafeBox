package com.mxn.soul.flowingdrawer.DES_TooLs;


import android.os.Environment;

import com.mxn.soul.flowingdrawer.secretshare.cli.MainCombine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;


/**
 * AESTest.java
 *
 * @author Techzero
 * @Email techzero@163.com
 * @Time 2013-12-12 ����1:25:44
 */
public class discrypt {
	private static String password="";
	private final static String DES = "DES";
	private Integer level;
	//密钥内容
	private String name1,name2,name3,name4,name5,content1,content2,content3,content4,content5;
	//密文
	private String encryptResult="";
	//输出明文位置
	private String out_path="";
	//构造方法
	public discrypt(String encryptResult, Integer level, String out_path, String name1, String name2, String name3, String name4, String name5, String content1, String content2, String content3, String content4, String content5) {
		this.encryptResult=encryptResult;
		this.level=level;
		this.out_path=out_path;
		this.name1=name1;
		this.name2=name2;
		this.name3=name3;
		this.name4=name4;
		this.name5=name5;
		this.content1=content1;
		this.content2=content2;
		this.content3=content3;
		this.content4=content4;
		this.content5=content5;
	}
	public File Discrypt(){
		MainCombine text2=new MainCombine();
		String[]key2 = new String[100];
		key2[1]="-k";
		key2[2]= Integer.toString(level);           //对应的加密等级
		key2[3]="-n";
		key2[4]="10";
		key2[5]=name1;                                    //碎片编号
		key2[6]=content1;    //碎片内的内容
		key2[7]=name2;                                  //碎片编号
		key2[8]=content2;   //碎片内的内容
		key2[9]=name3;                                 //碎片编号
		key2[10]=content3;   //碎片内的内容
		key2[11]=name4;   //碎片内的内容
		key2[12]=content4;   //碎片内的内容
		key2[13]=name5;    //碎片内的内容
		key2[14]=content5; //碎片内的内容
		text2.main(key2);      // 合成密钥
		String password="";
		File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/password/se2.txt");
		try {
			FileReader reader=new FileReader(file);
			BufferedReader br=new BufferedReader(reader);
			String line;
			while((line=br.readLine())!=null){
				password+=line;
			}
			br.close();
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File ms_file=new File(out_path);
		String decryptResult;
		try {
			decryptResult = decrypt(encryptResult, password);//解密
			BASE64Decoder decoder=new BASE64Decoder();
			byte[] result=decoder.decodeBuffer(decryptResult);
			//输出明文的文件
			FileOutputStream os=new FileOutputStream(ms_file);
			os.write(result, 0, result.length);
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ms_file;
	}
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		String strs = new BASE64Encoder().encode(bt);
		return strs;
	}

	/**
	 * Description 根据键值进行解密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws IOException,
			Exception {
		if (data == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buf = decoder.decodeBuffer(data);
		byte[] bt = decrypt(buf,key.getBytes());
		return new String(bt);
	}

	/**
	 * Description 根据键值进行加密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}


	/**
	 * Description 根据键值进行解密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
}