/*
    import java.io.*;
    import java.math.BigInteger;
    import java.security.*;
    import java.security.spec.InvalidKeySpecException;
    import java.security.spec.RSAPrivateKeySpec;
    import java.security.spec.RSAPublicKeySpec;
    import javax.crypto.BadPaddingException;
    import javax.crypto.Cipher;
    import javax.crypto.IllegalBlockSizeException;
    import javax.crypto.NoSuchPaddingException;
    import java.io.BufferedReader;
    import java.io.InputStreamReader;


    public class Pruebas_rsa{
        
        private static final String PUBLIC_KEY_FILE = "Public.key";
        private static final String PRIVATE_KEY_FILE = "Private.key";

        public static void main(String []args )throws IOException{

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                System.out.println("----------------GENERAR LLAVES PUBLICAS Y PRIVADAS---------------------");
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(1024);

                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                //System.out.println("keyPair:");
                //System.out.println(keyPair);

                PublicKey publicKey = keyPair.getPublic();
                System.out.println("\n\n***LLAVE PUBLICA***");
                System.out.println(publicKey);

                System.out.println("Presione enter para continuar...");br.readLine();

                PrivateKey privateKey = keyPair.getPrivate();
                System.out.println("\n\n***LLAVE PRIVADA***");
                System.out.println(privateKey);

                System.out.println("Presione enter para continuar...");br.readLine();

                System.out.println("\n->EXTRACCIÓN DE PARÁMETROS PARA GENERAR PAR DE LLAVES...");
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");

                RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
                System.out.println("***rsaPublicKeySpec***");
                System.out.println(rsaPublicKeySpec.getParams());

                System.out.println("Presione enter para continuar...");br.readLine();

                RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
                System.out.println("***r saPrivateKeySpec***");
                System.out.println(rsaPrivateKeySpec.getParams());

                System.out.println("Presione enter para continuar...");br.readLine();

                System.out.println("\n\n->GUARDANDO LLAVES EN ARCHIVOS");

                Pruebas_rsa rsaObj = new Pruebas_rsa();

                rsaObj.saveKeys(PUBLIC_KEY_FILE,rsaPublicKeySpec.getModulus(), rsaPublicKeySpec.getPublicExponent());
                rsaObj.saveKeys(PRIVATE_KEY_FILE,rsaPrivateKeySpec.getModulus(), rsaPrivateKeySpec.getPrivateExponent());

                System.out.println("\nLlaves guardadas correctamente");
                System.out.println("\nPresione enter para continuar...");br.readLine();

                //Encriptar datos usando llave publica

                System.out.println("\n\n----------------ENCRIPTACIÓN DE MENSAJE---------------------");
                System.out.println("\nINGRESE MENSAJE A ENCRIPTAR: ");
                String msj = br.readLine();
                byte[] encryptedData = rsaObj.encryptData(msj);
                System.out.println("\nPresione enter para continuar...");br.readLine();

                //desencriptar usando la llave privada
                rsaObj.decryptData(encryptedData);
                System.out.println("\nPresione enter para continuar...");br.readLine();

            } catch (Exception e) {
                //TODO: handle exception
            }
        }

        private void saveKeys(String fileName, BigInteger mod, BigInteger exp) throws IOException{
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;

            try {
                System.out.println("\n\nGenerando "+fileName+" ...\n");
                fos = new FileOutputStream(fileName);
                oos = new ObjectOutputStream(new BufferedOutputStream(fos));

                oos.writeObject(mod);
                oos.writeObject(exp);

                System.out.println(fileName+" generado correctamente");
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

        private byte[] encryptData(String data) throws IOException{

            System.out.println("\n->ENCRIPTANDO DATOS...");
            System.out.println("\nDatos antes de encriptacion: "+data);

            byte[] dataToEncrypt = data.getBytes();
            byte[] encryptedData = null;
            try {

                PublicKey pubKey = readPublicKeyFromFile(this.PUBLIC_KEY_FILE);
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, pubKey);
                encryptedData = cipher.doFinal(dataToEncrypt);
                System.out.println("\nDatos despues de encriptacion: "+encryptedData);

            } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
            
            System.out.println("\n->ENCRIPTACIÓN COPLETADA...");
            return encryptedData;
        }

        private void decryptData(byte[] data) throws IOException{

            System.out.println("\n->DESENCRIPTANDO DATOS...");
            byte[] descryptedData = null;
            try{
                PrivateKey privateKey = readPrivateKeyFromFile(this.PRIVATE_KEY_FILE);
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                descryptedData = cipher.doFinal(data);
                System.out.println("\nDatos desencriptados: "+new String(descryptedData));
            }catch(IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
                e.printStackTrace();
            }
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
*/

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
// import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
// import javax.crypto.IllegalBlockSizeException;
// import javax.crypto.NoSuchPaddingException;

public class Pruebas_rsa{

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