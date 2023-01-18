package modelo;

import controlador.Controlador;
import java.util.*;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import modelo.Paquete;

public class Modelo {

  private Controlador controlador;
  private RSA rsaObj;
  private MD5 md5Obj;
  private Map<String, String> lista_alumnos;
  private PublicKey alumno_pubKey, profe_pubKey;
  private PrivateKey profe_privKey, alumno_privKey;
  private String hashDescifradoProfe;
  private String mensajeDescifradoProfe;
  private Paquete paqueteAlumno, paqueteProfe;

  public Modelo(){
    this.rsaObj = new RSA();
    this.md5Obj = new MD5();
    this.lista_alumnos = new HashMap<String,String>();
    
    generarLlaves();
    iniciarListaAlumnos();
  }

  public void generarLlaves(){
    try{
      rsaObj.generateKeys(1024,"llave_alumno");
      alumno_pubKey = rsaObj.readPublicKeyFromFile("llave_alumno_pub.key");
      alumno_privKey = rsaObj.readPrivateKeyFromFile("llave_alumno_priv.key");

      rsaObj.generateKeys(1024,"llave_profe");
      profe_pubKey = rsaObj.readPublicKeyFromFile("llave_profe_pub.key");
      profe_privKey = rsaObj.readPrivateKeyFromFile("llave_profe_priv.key");
    }catch(Exception e){}

    //controlador.mostrarLlaves(alumno_pubKey.toString(), alumno_privKey.toString(), profe_pubKey.toString(), profe_privKey.toString());

  }

  private String getHash(String mensaje){
    return this.md5Obj.getMd5(mensaje);
  }

  private void iniciarListaAlumnos(){
    lista_alumnos.put("Juan Perez", "9");
    lista_alumnos.put("Mario Hernandez", "7");
  }

  public void setControlador(Controlador controlador){
    this.controlador = controlador;
  }

  public Paquete recibirNombreAlumno(String nombreAlumno){
    
    byte[] firma_alumno = rsaObj.encryptData(md5Obj.getMd5(nombreAlumno), alumno_privKey);
    byte[] mensaje_3ncriptado_alumno = rsaObj.encryptData(nombreAlumno, alumno_privKey);
    

    //paqueteAlumno = new Paquete(firma_alumno, mensaje_3ncriptado_alumno);

    return paqueteAlumno;
  }
  
  public Boolean descifraMensaje(Paquete paquete_recibido, int param){ 
    // Método para decifrar mensaje recibido por alumno y saber si es auténtico el mensaje
    // regresa verdadero si es autentico y falso si no lo es
    // 0 = profesor, 1 = alumno
    
    String hashDescifrado = "";
    String mensajeDescifrado = "";

    if (param == 0){

      hashDescifrado = rsaObj.decryptData(paquete_recibido.getFirma(), alumno_pubKey);
      mensajeDescifrado = rsaObj.decryptData(paquete_recibido.getMensaje(), alumno_pubKey);
      
      hashDescifradoProfe = hashDescifrado;
      mensajeDescifradoProfe = mensajeDescifrado;

    }else if (param == 1){

      hashDescifrado = rsaObj.decryptData(paquete_recibido.getFirma(), profe_pubKey);
      mensajeDescifrado = rsaObj.decryptData(paquete_recibido.getMensaje(), profe_pubKey);
      
    }

    if(hashDescifrado.equals(md5Obj.getMd5(mensajeDescifrado))){
      return true;
    }else{
      return false;
    }

  }

  /*public Paquete enviarPaquete(String mensaje, int param){
    // Método para enviar un paquete (firma, mensaje_encriptado)
    // 0 = profe, 1 = alumno

    byte[] firma = null;
    byte[] mensaje_enc = null;

    if (param == 0){

      firma = rsaObj.encryptData(md5Obj.getMd5(lista_alumnos.get(mensaje)),profe_privKey);
      mensaje_enc = rsaObj.encryptData(lista_alumnos.get(mensaje),profe_privKey);

    }else if (param == 1){

      firma = rsaObj.encryptData(md5Obj.getMd5(lista_alumnos.get(mensaje)),alumno_privKey);
      mensaje_enc = rsaObj.encryptData(lista_alumnos.get(mensaje),alumno_privKey);
      
    }
    
    return new Paquete(firma, mensaje_enc);

  }*/

  /*public Paquete compararHashProfe(){

    String hash_obtenido_profe = md5Obj.getMd5(mensajeDescifradoProfe);
    String calificacionEnviar = "";
    String hashCalificacion = "";

    if(hash_obtenido_profe.equals(hashDescifradoProfe)){
      System.out.println("Son iguales, manda calificacion");
      calificacionEnviar = alumnos.get(mensajeDescifradoProfe);
      hashCalificacion = md5Obj.getMd5(calificacionEnviar);
      System.out.println("Se le enviará al alumno" + mensajeDescifradoProfe + " su calificación: "+calificacion_para_enviar);
    }
    
    byte[] firma_profe = rsaObj.encryptData(hashCalificacion, profe_privKey);
    byte[] mensaje_profe = rsaObj.encryptData(calificacionEnviar, profe_privKey);

    paqueteProfe = new Paquete(firma_profe, mensaje_profe);

    return paqueteProfe;
  }*/

  public RSA getRSA(){
    return this.rsaObj;
  }

  public MD5 getMD5(){
    return this.md5Obj;
  }
}
