package com.mxn.soul.flowingdrawer.DES_TooLs;


import com.mxn.soul.flowingdrawer.secretshare.cli.Main;
import com.mxn.soul.flowingdrawer.secretshare.cli.MainSplit;

import java.io.File;
import java.io.FileOutputStream;
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
 * @Time 2013-12-12 下午1:25:44
 */
public class Encrypt {
    private Integer level;
    private final static String DES = "DES";
    private String encrypt_filepath="";
    //明文内容
    private String file_content="";
    public Encrypt(Integer level,String file_content,String encrypt_filepath) {
        this.level=level;
        this.file_content=file_content;
        this.encrypt_filepath=encrypt_filepath;
    }
    public void file_encrypt(){
//		File file=new File("D:/a1.txt");       //用户输入的数据
//    	FileInputStream is = null;
//			try {
//				is = new FileInputStream(file);
//			} catch (FileNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			int count = 0;
//			try {
//				count = is.available();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			byte[] b=new byte[count];
//			try {
//				is.read(b);
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
        String str2 = file_content;
        String key;
        String content=str2;
        int x=(int)(Math.random()*999999999);
        key=String.valueOf(x);
        String password = key;
        String encryptResult = null;
        try {
            encryptResult = encrypt(content, key);//进行加密
            //加密后的文件输出在这里
            File cs_file=new File(encrypt_filepath);
            FileOutputStream os=new FileOutputStream(cs_file);
            BASE64Decoder decoder=new BASE64Decoder();
            byte[] filebyte=decoder.decodeBuffer(encryptResult);
            for(int i=0;i<filebyte.length;i++){
                if(filebyte[i]<0){
                    filebyte[i]+=256;
                }
            }
            os.write(filebyte, 0, filebyte.length);
            os.close();
            //下面的代码只能输出可读性的字符串
//			FileWriter writer=new FileWriter(cs_file);
//			BufferedWriter bw=new BufferedWriter(writer);
//			bw.write(encryptResult);
//			bw.flush();
//			bw.close();
//			writer.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        MainSplit text1=new MainSplit();
        Main text=new Main();
        String []key1 = new String[100];
        key1[1]="-k";
        key1[2]=Integer.toString(level);             //加密等级
        key1[3]="-n";
        key1[4]="10";
        key1[5]="-sN";
        key1[6]=password;
        text1.main(key1);//到此加密结束，会输出加密后的10个碎片
    }
    /**
     * 加密
     *
     * @param content
     *            待加密内容
     * @param password
     *            加密的密钥
     * @return
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
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
        byte[] bt = decrypt(buf, key.getBytes());
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