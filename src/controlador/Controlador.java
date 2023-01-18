package controlador;

import java.util.Base64;
import modelo.Profesor;
import modelo.Alumno;
import vista.Vista;

public class Controlador {

  private Alumno alumno;
  private Profesor profesor;
  private Vista vista;

  public Controlador(){}
  
  /*public void setModelo(Modelo modelo){
    this.modelo = modelo;
  }*/

  public void setProfesor(Profesor profesor){
    this.profesor = profesor;
  }

  public void setAlumno(Alumno alumno){
    this.alumno = alumno;
  }

  public void setVista(Vista vista){
    this.vista = vista;
  }
  
  public void iniciarLlaves(){
    
    alumno.generarLlaves();
    profesor.generarLlaves();
  }
  
  public void iniciarProceso(){
   
    String cambio;
    String cambioAsignado;
    
    alumno.generaHashFirmaMensaje();
    System.out.println("Longitud sin cambiar" + alumno.getPaquete().getMensaje().length);
    System.out.println("Mensaje" + Base64.getEncoder().encodeToString(alumno.getPaquete().getMensaje()));
    cambio = Base64.getEncoder().encodeToString(alumno.getPaquete().getMensaje());
    cambioAsignado = vista.ingresarCambioMensaje(cambio);
    alumno.getPaquete().setMensaje(Base64.getDecoder().decode(cambioAsignado));
    System.out.println("Longitud con cambio" + alumno.getPaquete().getMensaje().length);
    System.out.println("Mensaje" + Base64.getEncoder().encodeToString(alumno.getPaquete().getMensaje()));
    profesor.recibeDatos(alumno.getPaquete());
    profesor.comparaHash();
    cambio = Base64.getEncoder().encodeToString(profesor.getPaquete().getMensaje());
    cambioAsignado = vista.ingresarCambioMensaje(cambio);
    profesor.getPaquete().setMensaje(Base64.getDecoder().decode(cambioAsignado));
    alumno.obtieneFirmaCalificacion(profesor.getPaquete());
    alumno.comparaHash();
  }
  
  public void setNombre(String nombre){
    alumno.setNombre(nombre);
    
    iniciarProceso();
  }
  
  public String descifrarCalificacion(){ return alumno.getMensajeDescifrado(); }
  
  //Esto lo llama modelo

  public void mostrarLlavesAl(String llavePublicaAl, String llavePrivadaAl){

    vista.mostrarLlavesAl(llavePublicaAl, llavePrivadaAl);
  }

  public void mostrarLlavesCal(String llavePublicaPr, String llavePrivadaPr){

    vista.mostrarLlavesCal(llavePublicaPr, llavePrivadaPr);
  }

  public void mostrarMensajeAl(String mensaje){
    
    vista.mostrarMensajeAl(mensaje);
  }

  public void mostrarMensajeCal(String mensaje){

    vista.mostrarMensajeCal(mensaje);
  }
  
  public void mostrarHashAlumno(String hash){ 

    vista.mostrarHashAlumno(hash);
  }

  public void mostrarHashCal(String hash){

    vista.mostrarHashCal(hash);
  }

  public void mostrarMensajeCifradoAl(String mensaje){

    vista.mostrarMensajeCifradoAl(mensaje);
  }

  public void mostrarMensajeCifradoCal(String mensaje){

    vista.mostrarMensajeCifradoCal(mensaje);
  }
  
  public void mostrarMensajeRecibidoSer(String mensaje){

    vista.mostrarMensajeRecibidoSer(mensaje);
  }

  public void mostrarMensajeDescifradoSer(String mensaje){

    vista.mostrarMensajeDescifradoSer(mensaje);
  }
  
  public void error(String error){
    
    vista.error(error);
  }
}
