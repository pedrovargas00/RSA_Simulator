import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MD5 {
        
    public static String getMd5(String input){

        String hashtext = null;
        
        try{

            MessageDigest md = MessageDigest.getInstance("MD5");//para el hash
            byte[] messageDigest = md.digest(input.getBytes()); //obtiene arreglo de bytes
            BigInteger no = new BigInteger(1,messageDigest); //convierte array en rep. semántica
            hashtext = no.toString(16); //hexadecimal
            while(hashtext.length()<32){
                hashtext="0"+hashtext;
            }
        
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MD5.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hashtext;
    }  
}
    
