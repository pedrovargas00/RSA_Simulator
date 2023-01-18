package vista;

import controlador.Controlador;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vista extends JFrame{
    
  private JButton enviarButton, descifrarButton;
  private JPanel pgeneral;
  private JLabel nombreAlumnoLabel, calificacionLabel;
  private JLabel mensajeAlumnoLabel, llavePuAlumnoLabel, llavePriAlumnoLabel;
  private JLabel hashAlumnoLabel, mensajeCifradoAlumnoLabel;
  private JLabel mensajeCalLabel, llavePuCalLabel, llavePriCalLabel;
  private JLabel hashCalLabel, mensajeCifradoCalLabel;
  private JLabel mensajeRecibidoLabel, mensajeDescifradoLabel;
  private JPanel p1;
  private JPanel p2;
  private JPanel p3;
  private JTextField nombreAlumnoField,calificacionField;
  private JTextField mensajeAlumnoField, llavepublicaAlumnoField;
  private JTextField llaveprivadaAlumnoField, hashAlumnoField, mcifradoAlumnoField;
  private JTextField mensajecalField, llavepublicacalField;
  private JTextField llaveprivadacalField, hashcalField, mdescifradocalField;
  private JTextField mensajeRecibidoField, mensajeDescifradoField;
  private Controlador controlador;

  public Vista(){
      
    initComponentes();
      
  }

  public void setControlador(Controlador controlador){
      this.controlador = controlador;
  }

  private void initComponentes(){
    setSize(720,650);
    setLocation(200,30);
    setTitle("Consulta calificaciones");
    pgeneral = new JPanel();
    p1 = new JPanel();
    p2 = new JPanel();
    p3 = new JPanel();
    pgeneral.setLayout(null);
    //pgeneral.setBackground(new Color(230,238,249));
    pgeneral.setBackground(new Color(187,224,232));
    pgeneral.setBounds(0,0, 600, 600);
    add(pgeneral);
    
    
    
    p1.setLayout(null);
    p1.setBackground(new Color(64,167,189));
    p1.setBounds(50, 100, 300, 325);
  //  p1.setBorder();
    pgeneral.add(p1);
    
    p2.setLayout(null);
    p2.setBackground(new Color(64,167,189));
    p2.setBounds(360,100,300,325);
    pgeneral.add(p2);
    
            
    
    enviarButton = new JButton("Enviar");
    enviarButton.setBounds(150, 60, 115, 30);
    enviarButton.setForeground(Color.WHITE);
    enviarButton.setBackground(new Color(44,97,96));
    pgeneral.add(enviarButton);
    

    nombreAlumnoLabel = new JLabel("Nombre del alumno:");
    nombreAlumnoLabel.setBounds(55, 25, 115, 25);
    pgeneral.add(nombreAlumnoLabel);
    
    nombreAlumnoField = new JTextField();
    nombreAlumnoField.setBounds(170, 25, 150, 25);
    pgeneral.add(nombreAlumnoField);
    
    
    calificacionLabel = new JLabel("Calificación:");
    calificacionLabel.setBounds(380, 25, 100, 25);
    pgeneral.add(calificacionLabel);
    
    
    calificacionField = new JTextField();
    calificacionField.setBounds(460, 25, 150, 25);
    calificacionField.setEditable(false);
    pgeneral.add(calificacionField);
    
    descifrarButton = new JButton("Descifrar");
    descifrarButton.setLayout(null);
    descifrarButton.setForeground(Color.WHITE);
    descifrarButton.setBackground(new Color(44,97,96));
    descifrarButton.setBounds(450, 60, 115, 30);
    pgeneral.add(descifrarButton);
    
    
    //Panel 1, alumno
    mensajeAlumnoLabel = new JLabel("Mensaje");
    mensajeAlumnoLabel.setBounds(35, 50, 100, 25);
    p1.add(mensajeAlumnoLabel);
    
    mensajeAlumnoField = new JTextField();
    mensajeAlumnoField.setBounds(135, 50, 130, 25);
    mensajeAlumnoField.setEditable(false);
    p1.add(mensajeAlumnoField);
    
    llavePuAlumnoLabel= new JLabel("Llave publica");
    llavePuAlumnoLabel.setBounds(35, 100, 100, 25);
    p1.add(llavePuAlumnoLabel);
    
    llavepublicaAlumnoField = new JTextField();
    llavepublicaAlumnoField.setBounds(135, 100, 130, 25);
    llavepublicaAlumnoField.setEditable(false);
    p1.add(llavepublicaAlumnoField);
    
    llavePriAlumnoLabel= new JLabel("Llave privada");
    llavePriAlumnoLabel.setBounds(35, 150, 100, 25);
    p1.add(llavePriAlumnoLabel);
    
    llaveprivadaAlumnoField= new JTextField();
    llaveprivadaAlumnoField.setBounds(135, 150, 130, 25);
    llaveprivadaAlumnoField.setEditable(false);
    p1.add(llaveprivadaAlumnoField);
    
    hashAlumnoLabel = new JLabel("Hash");
    hashAlumnoLabel.setBounds(35, 200, 100, 25);
    p1.add(hashAlumnoLabel);
    
    hashAlumnoField = new JTextField();
    hashAlumnoField.setBounds(135, 200, 130, 25);
    hashAlumnoField.setEditable(false);
    p1.add(hashAlumnoField);
    
    mensajeCifradoAlumnoLabel= new JLabel("Mensaje Cifrado");
    mensajeCifradoAlumnoLabel.setBounds(35, 250, 100, 25);
    p1.add(mensajeCifradoAlumnoLabel);
    
    mcifradoAlumnoField = new JTextField();
    mcifradoAlumnoField.setBounds(135, 250, 130,25);
    mcifradoAlumnoField.setEditable(false);
    p1.add(mcifradoAlumnoField);
    
    
    //Panel 2, calificacion
    mensajeCalLabel = new JLabel("Mensaje");
    mensajeCalLabel.setBounds(35, 50, 100, 25);
    p2.add(mensajeCalLabel);
    
    mensajecalField = new JTextField();
    mensajecalField.setBounds(135, 50, 130, 25);
    mensajecalField.setEditable(false);
    p2.add(mensajecalField);
    
    llavePuCalLabel= new JLabel("Llave publica");
    llavePuCalLabel.setBounds(35, 100, 100, 25);
    p2.add(llavePuCalLabel);
    
    llavepublicacalField = new JTextField();
    llavepublicacalField.setBounds(135, 100, 130, 25);
    llavepublicacalField.setEditable(false);
    p2.add(llavepublicacalField);
    
    llavePriCalLabel= new JLabel("Llave privada");
    llavePriCalLabel.setBounds(35, 150, 100, 25);
    p2.add(llavePriCalLabel);
    
    llaveprivadacalField= new JTextField();
    llaveprivadacalField.setBounds(135, 150, 130, 25);
    llaveprivadacalField.setEditable(false);
    p2.add(llaveprivadacalField);
    
    hashCalLabel = new JLabel("Hash");
    hashCalLabel.setBounds(35, 200, 100, 25);
    p2.add(hashCalLabel);
    
    hashcalField = new JTextField();
    hashcalField.setBounds(135, 200, 130, 25);
    hashcalField.setEditable(false);
    p2.add(hashcalField);
    
    mensajeCifradoCalLabel= new JLabel("Mensaje Cifrado");
    mensajeCifradoCalLabel.setBounds(35, 250, 100, 25);
    p2.add(mensajeCifradoCalLabel);
    
    mdescifradocalField = new JTextField();
    mdescifradocalField.setBounds(135, 250, 130,25);
    mdescifradocalField.setEditable(false);
    p2.add(mdescifradocalField);
    
    //Panel3 Servidor
    p3 = new JPanel();
    
    p3.setLayout(null);
    p3.setBackground(new Color(187,224,232));;
    p3.setBounds(55, 450, 600, 140);
    pgeneral.add(p3);
    
    //mensajeRecibidoLabel, mensajeDescifradoLabel
    mensajeRecibidoLabel = new JLabel("Mensaje recibido");
    mensajeRecibidoLabel.setBounds(160, 25, 100, 25);
    p3.add(mensajeRecibidoLabel);
    
    mensajeRecibidoField = new JTextField();
    mensajeRecibidoField.setBounds(280 , 25, 170, 25);
    mensajeRecibidoField.setEditable(false);
    p3.add(mensajeRecibidoField);
    
    mensajeDescifradoLabel = new JLabel("Mensaje descifrado");
    mensajeDescifradoLabel.setBounds(160, 70, 120, 25);
    p3.add(mensajeDescifradoLabel);
    
    mensajeDescifradoField= new JTextField();
    mensajeDescifradoField.setBounds(280 , 70, 170, 25);
    mensajeDescifradoField.setEditable(false);
    p3.add(mensajeDescifradoField);
    
    /*ActionListener b2 = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {

      }
  };
  boton.addActionListener(b2);*/
    enviarButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        controlador.setNombre(nombreAlumnoField.getText());
      }});

    descifrarButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        calificacionField.setText(controlador.descifrarCalificacion());
      }});
  }

  public String ingresarCambioMensaje(String mensaje){

    return JOptionPane.showInputDialog("Modifique el mensaje", mensaje);
  }
  
  public void error(String error){
    JOptionPane.showMessageDialog(null, error);
    System.exit(0);
    
  }

  public void mostrarLlavesAl(String llavePublica, String llavePrivada){

    llavepublicaAlumnoField.setText(llavePublica);
    llaveprivadaAlumnoField.setText(llavePrivada);
  }

  public void mostrarLlavesCal(String llavePublica, String llavePrivada){

    llavepublicacalField.setText(llavePublica);
    llaveprivadacalField.setText(llavePrivada);
  }

  public void mostrarMensajeAl(String mensaje){ mensajeAlumnoField.setText(mensaje); }
  public void mostrarMensajeCal(String mensaje){ mensajecalField.setText(mensaje); }

  public void mostrarHashAlumno(String hash){ hashAlumnoField.setText(hash); }
  public void mostrarHashCal(String hash){ hashcalField.setText(hash); }

  public void mostrarMensajeCifradoAl(String mensaje){ mcifradoAlumnoField.setText(mensaje); }
  public void mostrarMensajeCifradoCal(String mensaje){ mdescifradocalField.setText(mensaje); }
  
  public void mostrarMensajeRecibidoSer(String mensaje){ mensajeRecibidoField.setText(mensaje); }
  public void mostrarMensajeDescifradoSer(String mensaje){ mensajeDescifradoField.setText(mensaje); }

}