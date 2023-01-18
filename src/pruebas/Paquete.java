public class Paquete {

    private byte[] firma;
    private byte[] mensaje;

    public Paquete(byte[] firma, byte[] mensaje){
        this.firma = firma;
        this.mensaje = mensaje;
    }

    public byte[] getMensaje(){
        return mensaje;
    }

    public byte[] getFirma(){
        return firma;
    }
}
