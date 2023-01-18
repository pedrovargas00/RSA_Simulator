import javax.swing.JFrame;

import modelo.Alumno;
import modelo.Profesor;
import vista.Vista;
import controlador.Controlador;

public class Principal {
   public static void main(String[] args) {
     Controlador controlador = new Controlador();
     //Modelo modelo = new Modelo();
     Profesor profesor = new Profesor();
     Alumno alumno = new Alumno();
     Vista vista = new Vista();

     controlador.setProfesor(profesor);
     controlador.setAlumno(alumno);
     controlador.setVista(vista);

     //modelo.setControlador(controlador);
     alumno.setControlador(controlador);
     profesor.setControlador(controlador);
     vista.setControlador(controlador);
     
     controlador.iniciarLlaves();
     
     vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     vista.setLocationRelativeTo(null);
     vista.setVisible(true);

   }
}
