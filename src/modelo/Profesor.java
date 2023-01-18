package modelo;

import controlador.Controlador;
import java.util.*;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.BadPaddingException;
import modelo.Paquete;

public class Profesor {
    
  private Controlador controlador;
  private RSA rsaObj;
  private MD5 md5Obj;
  private Map<String, String> alumnos;
  private PublicKey profe_pubKey;
  private PrivateKey profe_privKey;
  private String hashDescifrado;
  private String mensajeDescifrado;
  private Boolean nombreValido;
  private Paquete paquete;

  public Profesor(){

    this.rsaObj = new RSA();
    this.md5Obj = new MD5();
    this.alumnos = new HashMap<String,String>();
    this.nombreValido = false;
    iniciarListaAlumnos();

  }

  public void setControlador(Controlador controlador){
    this.controlador = controlador;
  }

  public void generarLlaves(){
    try{
      rsaObj.generateKeys(1024,"llave_profe");
      profe_pubKey = rsaObj.readPublicKeyFromFile("llave_profe_pub.key");
      profe_privKey = rsaObj.readPrivateKeyFromFile("llave_profe_priv.key");
    }catch(Exception e){}
      controlador.mostrarLlavesAl(Base64.getEncoder().encodeToString(profe_pubKey.getEncoded()), Base64.getEncoder().encodeToString(profe_privKey.getEncoded()));

  }

  private void iniciarListaAlumnos(){
    alumnos.put("Juan Pérez", "9");
    alumnos.put("Mario Hernández", "7");
    alumnos.put("Alma Juárez", "8");
  }

  public void recibeDatos(Paquete alumno){

    byte[] firmaRecibida = alumno.getFirma();
    byte[] mensajeRecibido = alumno.getMensaje();
    
    try{
      hashDescifrado = rsaObj.decryptData(firmaRecibida, alumno.getPubKey());
      mensajeDescifrado = rsaObj.decryptData(mensajeRecibido, alumno.getPubKey());
    }catch(Exception e){
        System.out.println(e.getMessage());
      controlador.error("El mensaje fue modificado");
    }finally{
      System.out.println("hash_descifrado= " + hashDescifrado);
      System.out.println("mensaje_descifrado= " + mensajeDescifrado);

      controlador.mostrarMensajeAl(Base64.getEncoder().encodeToString(mensajeRecibido));
      controlador.mostrarHashAlumno(hashDescifrado);
      controlador.mostrarMensajeCifradoAl(Base64.getEncoder().encodeToString(mensajeRecibido));
    }
  }

  public void comparaHash(){

    String hashCalificacion = "", calificacion = "";
    byte[] firmaProfe = null;
    byte[] mensajeProfe = null;

    if(md5Obj.getMd5(mensajeDescifrado).equals(hashDescifrado)){
      System.out.println("Son iguales, manda calificacion");
      nombreValido = true;
      calificacion = alumnos.get(mensajeDescifrado);
      System.out.println("Se le enviará al alumno" + mensajeDescifrado + " su calificación: "+calificacion);
      hashCalificacion = md5Obj.getMd5(calificacion);

      System.out.println("HASH calificacion = " + hashCalificacion);

      firmaProfe = rsaObj.encryptData(hashCalificacion, profe_privKey);
      mensajeProfe = rsaObj.encryptData(calificacion, profe_privKey);
      System.out.println("Firma profe = " + firmaProfe);
      paquete = new Paquete(firmaProfe, mensajeProfe, profe_pubKey);
      //
      controlador.mostrarMensajeDescifradoSer(mensajeDescifrado);
      System.out.println("Firma profe = " + paquete.getFirma());
      System.out.println("mensaje profe = " + paquete.getMensaje());
    }else{
      controlador.error("Los hashes son distintos");
    }
  }

  public Paquete getPaquete(){ return paquete; }

  public Boolean getNombreValido(){ return nombreValido; }

}
