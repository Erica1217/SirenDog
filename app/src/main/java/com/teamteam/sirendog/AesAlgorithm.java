package com.teamteam.sirendog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.teamteam.sirendog.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class AesAlgorithm {

    Context context;
    public AesAlgorithm(Context context){
        this.context = context;
    }

    public void fileWrite(String fileName, byte[] contents) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile().toString(), fileName);
        try {
            FileOutputStream outFs = new FileOutputStream(file);
            outFs.write(contents);
            outFs.close();

        }catch (Exception e){

        }
    }

    public byte[] fileRead(String fileName) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile().toString(), fileName);
        try{
            FileInputStream inFs = new FileInputStream(file);
            long len = file.length();

            byte[] contents = new byte[(int)len];
            inFs.read(contents);
            inFs.close();

            return contents;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] encryptRSA(PublicKey publicKey, byte[] plainData) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(plainData);

        return encryptedData;
    }
    //공개키 복호화
    private static byte[] decryptRSA(PrivateKey privateKey, byte[] cipherData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] recoveredData = cipher.doFinal(cipherData);

        return recoveredData;
    }

    // 암호화하
    public void encryption(byte[] plainText) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES"); //AES 암호화 알고리즘으로 설정
        keyGenerator.init(128); //128-bit key 설정
        SecretKey key = keyGenerator.generateKey();  //key를 생성함
        Charset charset = Charset.forName("UTF-8");

        PublicKey publicKey = null;
        PrivateKey privateKey = null;

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] iv = new SecureRandom().getSeed(16); //IV 생성
        fileWrite("initialvector.txt", iv);

        //공개키 쌍 생성하는 부분
        if(fileRead("public.key") != null && fileRead("private.key") != null) {
            byte[] publicKeyBytes = fileRead("public.key");
            byte[] privateKeyBytes = fileRead("private.key");
            //읽기부분
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        }
        else {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            publicKey = pair.getPublic();
            privateKey = pair.getPrivate();

            fileWrite("public.key", publicKey.getEncoded());
            fileWrite("private.key", privateKey.getEncoded());
        }

        //평문을 AES/CBC로 암호화하여 파일에 저장
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] encryptedData = cipher.doFinal(plainText);
        fileWrite("encryptedFile.txt", encryptedData);

        //AES키를 RSA를 이용하여 암호화하고 파일에 저장
        byte[] encryptedKey = encryptRSA(publicKey, key.getEncoded());
        fileWrite("encryptedKeyFile.txt", encryptedKey);

    }

    //평문 복호화 메서드
    public byte[] decryption() throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Charset charset = Charset.forName("UTF-8");
        byte[] iv = new byte[16];
        iv = fileRead("initialvector.txt");

        byte[] privateKeyBytes = fileRead("private.key");

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        byte[] loadedEncryptedKey = fileRead("encryptedKeyFile.txt");
        byte[] recoveredKeyRaw = decryptRSA(privateKey, loadedEncryptedKey);

        SecretKey recoveredKey = new SecretKeySpec(recoveredKeyRaw, "AES");

        cipher.init(Cipher.DECRYPT_MODE, recoveredKey, new IvParameterSpec(iv));

        byte[] encryptedText = fileRead("encryptedfile.txt");
        byte[] decrypted = cipher.doFinal(encryptedText);

        //String recoveredText = new String(decrypted, charset);
        return decrypted;

    }
}