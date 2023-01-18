package modelo;

import controlador.Controlador;
import java.util.*;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import modelo.Paquete;

public class Alumno {
    
  private Controlador controlador;
  private RSA rsaObj;
  private MD5 md5Obj;
  private PublicKey alumno_pubKey;
  private PrivateKey alumno_privKey;
  private Boolean calificacionValida;
  private String nombre;
  private String hashDescifrado;
  private String mensajeDescifrado;
  private Paquete paquete;

  public Alumno(){

    this.rsaObj = new RSA();
    this.md5Obj = new MD5();
    this.calificacionValida = false;
  }

  public void setControlador(Controlador controlador){
    this.controlador = controlador;
  }

  public void generarLlaves(){
    try{
      rsaObj.generateKeys(1024,"llave_alumno");
      alumno_pubKey = rsaObj.readPublicKeyFromFile("llave_alumno_pub.key");
      alumno_privKey = rsaObj.readPrivateKeyFromFile("llave_alumno_priv.key");
    }catch(Exception e){}

    controlador.mostrarLlavesCal(Base64.getEncoder().encodeToString(alumno_pubKey.getEncoded()), Base64.getEncoder().encodeToString(alumno_privKey.getEncoded()));

  }

  public void setNombre(String nombre){ this.nombre = nombre; }

  public void generaHashFirmaMensaje(){

    //Obtiene hash, firma y cifrado del mensaje
    
    if(nombre.isEmpty())
        controlador.error("El nombre está vacío");
    
    String hashNombre = md5Obj.getMd5(nombre);
    byte[] firmaAlumno = rsaObj.encryptData(hashNombre, alumno_privKey);
    byte[] mensajeAlumno = rsaObj.encryptData(nombre, alumno_privKey);

    paquete = new Paquete(firmaAlumno, mensajeAlumno, alumno_pubKey);
  }

  public void obtieneFirmaCalificacion(Paquete paquete){

    byte[] firmaRecibida = paquete.getFirma();
    byte[] mensajeRecibido = paquete.getMensaje();

    hashDescifrado = rsaObj.decryptData(firmaRecibida, paquete.getPubKey());
    mensajeDescifrado = rsaObj.decryptData(mensajeRecibido, paquete.getPubKey());

    System.out.println("hash_descifrado= "+hashDescifrado);
    System.out.println("mensaje_descifrado= "+mensajeDescifrado);

    controlador.mostrarHashCal(hashDescifrado);
    controlador.mostrarMensajeCifradoCal(Base64.getEncoder().encodeToString(mensajeRecibido));
  }

  public void comparaHash(){

    String hashObtenido = md5Obj.getMd5(mensajeDescifrado);

    if(hashObtenido.equals(hashDescifrado)){
        calificacionValida = true;
        System.out.println("Tu calificación es "+ mensajeDescifrado);
    }else
        System.out.println("Son diferentes");
  }
  
  public String getMensajeDescifrado(){ return mensajeDescifrado; }

  public Paquete getPaquete(){ return paquete; }

  public Boolean getCalificacionValida(){ return calificacionValida; }

}
