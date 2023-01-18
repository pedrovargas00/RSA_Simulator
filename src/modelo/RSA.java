
package modelo;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;


public class RSA{

    public void generateKeys(int n_bits, String file){
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(n_bits);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();

            PrivateKey privateKey = keyPair.getPrivate();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

            RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

            saveKeys(file+"_pub.key",rsaPublicKeySpec.getModulus(), rsaPublicKeySpec.getPublicExponent());
            saveKeys(file+"_priv.key",rsaPrivateKeySpec.getModulus(), rsaPrivateKeySpec.getPrivateExponent());
        }catch(Exception e){

        }
    }

    private void saveKeys(String fileName, BigInteger mod, BigInteger exp) throws IOException{
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(new BufferedOutputStream(fos));
            oos.writeObject(mod);
            oos.writeObject(exp);
        } catch (Exception e) {
            //TODO: handle exception
        }finally{
            if(oos!=null){
                oos.close();
                if(fos!=null){
                    fos.close();
                }
            }
        }
    }

    public byte[] encryptData(String data, PrivateKey privKey) {
        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privKey);
            encryptedData = cipher.doFinal(dataToEncrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedData;
    }

    public String decryptData(byte[] data, PublicKey pubKey){
        byte[] descryptedData = null;
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, pubKey);
            descryptedData = cipher.doFinal(data);
            //System.out.println("\nDatos desencriptados: "+new String(descryptedData));
        }catch(Exception e){
            e.printStackTrace();
        }
        return new String(descryptedData);
    }

    public PublicKey readPublicKeyFromFile(String fileName) throws IOException{
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        PublicKey pubKey = null;
        try {
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);
            BigInteger mod = (BigInteger)ois.readObject();
            BigInteger exp = (BigInteger)ois.readObject();

            //obtener llave publica
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(mod, exp);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            pubKey = fact.generatePublic(rsaPublicKeySpec);

        } catch (Exception e) {
            //TODO: handle exception
        } finally{
            if(ois!=null){
                ois.close();
                if(fis!=null){
                    fis.close();
                }
            }
        }
        return pubKey;   
    }

    public PrivateKey readPrivateKeyFromFile(String fileName) throws IOException{
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        PrivateKey privKey = null;

        try {
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);

            BigInteger mod = (BigInteger)ois.readObject();
            BigInteger exp = (BigInteger)ois.readObject();

            //obtener llave publica
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(mod, exp);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            privKey = fact.generatePrivate(rsaPrivateKeySpec);
        } catch (Exception e) {
            //TODO: handle exception
        } finally{
            if(ois!=null){
                ois.close();
                if(fis!=null){
                    fis.close();
                }
            }
        }

        return privKey;
    }
}