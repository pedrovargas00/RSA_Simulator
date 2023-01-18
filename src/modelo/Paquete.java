package modelo;

import java.security.spec.RSAPublicKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Paquete {

    private byte[] firma;
    private byte[] mensaje;
    private PublicKey pubKey;

    public Paquete(byte[] firma, byte[] mensaje, PublicKey pubKey){
        this.firma = firma;
        this.mensaje = mensaje;
        this.pubKey = pubKey;
    }

    public byte[] getMensaje(){
        return mensaje;
    }
    
    public void setMensaje(byte mensaje[]){
            this.mensaje = mensaje;
    }

    public byte[] getFirma(){
        return firma;
    }

    public PublicKey getPubKey(){
        return pubKey;
    }
}
