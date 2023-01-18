package Pruebas;

//import Pruebas_rsa;
//import MD5;
//import Paquete;
import java.util.Scanner;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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

public class Ejecutable{

    public static void main(String[] args) {

        Pruebas_rsa rsaObj = new Pruebas_rsa();
        MD5 md5Obj = new MD5();
        Scanner br = new Scanner(System.in);

        Map<String, String> alumnos = new HashMap<String,String>();

        alumnos.put("Juan Perez", "9");
        alumnos.put("Mario Hernandez", "7");

        
        try{
            rsaObj.generateKeys(1024,"llave_alumno");
            PublicKey alumno_pubKey = rsaObj.readPublicKeyFromFile("llave_alumno_pub.key");
            PrivateKey alumno_privKey = rsaObj.readPrivateKeyFromFile("llave_alumno_priv.key");

            rsaObj.generateKeys(1024,"llave_profe");
            PublicKey profe_pubKey = rsaObj.readPublicKeyFromFile("llaves_profe_pub.key");
            PrivateKey profe_privKey = rsaObj.readPrivateKeyFromFile("llaves_profe_priv.key");

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/* Alumno envía su nombbre al servidor*/
            String nombre_alumno = "Juan Perez";
            String hash_nombre = md5Obj.getMd5(nombre_alumno);
        
            System.out.println("modificar hash? n=n0 y=si");
            char mod; 
            mod = br.nextLine().charAt(0);
            if(mod=='y'){
                System.out.println("escribe algo");
                String extra = br.nextLine();
                hash_nombre+=extra;
            }

            System.out.println("HASH nombre = "+hash_nombre);

            byte[] firma_alumno = rsaObj.encryptData(hash_nombre,alumno_privKey);
            byte[] mensaje_alumno = rsaObj.encryptData(nombre_alumno,alumno_privKey);

            Paquete paquete_alumno = new Paquete(firma_alumno,mensaje_alumno);
            
            System.out.println("Firma alumno = " + paquete_alumno.getFirma());
            System.out.println("mensaje alumno = " + paquete_alumno.getMensaje());
            
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/* Servidor recibe mensaje y hash, válida los hashes para obtener la calificación */
            
            Paquete paquete_recibido = paquete_alumno;

            byte[] firma_recibida = paquete_recibido.getFirma();
            byte[] mensaje_recibido = paquete_recibido.getMensaje();

            String hash_descifrado = rsaObj.decryptData(firma_recibida, alumno_pubKey);
            String mensaje_descifrado = rsaObj.decryptData(mensaje_recibido, alumno_pubKey);

            System.out.println("hash_descifrado= "+hash_descifrado);
            System.out.println("mensaje_descifrado= "+mensaje_descifrado);

            String hash_obtenido_profe = md5Obj.getMd5(mensaje_descifrado);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*Profe envía calificación*/
            

            if(hash_obtenido_profe.equals(hash_descifrado)){
                System.out.println("Son iguales, manda calificacion");
                String calificacion_para_enviar = alumnos.get(mensaje_descifrado);
                System.out.println("Se le enviará al alumno"+mensaje_descifrado+" su calificación: "+calificacion_para_enviar);
                
                String hash_calificacion = md5Obj.getMd5(calificacion_para_enviar);
            
                System.out.println("modificar hash? n=n0 y=si");
                 
                mod = br.nextLine().charAt(0);
                if(mod=='y'){
                    System.out.println("escribe algo");
                    String extra = br.nextLine();
                    hash_calificacion+=extra;
                }

                System.out.println("HASH calificacion = "+hash_calificacion);

                byte[] firma_profe = rsaObj.encryptData(hash_calificacion,profe_privKey);
                byte[] mensaje_profe = rsaObj.encryptData(calificacion_para_enviar,profe_privKey);

                System.out.println("Firma profe = " + firma_profe);
                

                Paquete paquete_profe = new Paquete(firma_profe,mensaje_profe);
                
                System.out.println("Firma profe = " + paquete_profe.getFirma());
                System.out.println("mensaje profe = " + paquete_profe.getMensaje());

                ///////////////////////////////////////////////////////////////////////////////
                /* Alumno recibe calificación*/

                Paquete paquete_recibido_alumno = paquete_profe;

                byte[] firma_recibida_a = paquete_recibido_alumno.getFirma();
                byte[] mensaje_recibido_a = paquete_recibido_alumno.getMensaje();

                String hash_descifrado_a = rsaObj.decryptData(firma_recibida_a, profe_pubKey);
                String mensaje_descifrado_a = rsaObj.decryptData(mensaje_recibido_a, profe_pubKey);

                System.out.println("hash_descifrado= "+hash_descifrado_a);
                System.out.println("mensaje_descifrado= "+mensaje_descifrado_a);

                String hash_obtenido_a = md5Obj.getMd5(mensaje_descifrado_a);

                if(hash_obtenido_a.equals(hash_descifrado_a)){
                    System.out.println("Tu calificación es   "+mensaje_descifrado_a);
                }else{
                    System.out.println("Son diferentes");
                }


            }else{
                System.out.println("Son diferentes");
            }



        }catch(Exception e){
            System.out.println(e.getMessage());     
        }
    }
}