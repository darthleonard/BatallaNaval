

import java.io.*;
import java.net.*;
import javax.swing.*;

public class Coneccion extends Thread{
    private ServerSocket server;
    private Socket socket;
    private int puerto = 9000;
    private DataOutputStream salida;
    private DataInputStream entrada;
    private String usuario;
    private String ip;
    public static final int servidor=0;
    public static final int cliente=1;
    int tipo;
    private Object mensaje;
    private boolean conectado=false;
    
//    BatallaNaval bn;
    public Coneccion(String ip, String usuario, int tipo) {
        this.tipo=tipo;
        this.usuario=usuario;
        this.ip=ip;
    }
    
    void Conectar(){
        start();
    }
    
    void cerrarConecciones() throws IOException{
        conectado=false;
        socket.close();
        entrada.close();
        salida.close();
    }
    
    void mandarMensaje(String mensaje) throws IOException{
        salida.writeUTF(mensaje);
    }
    
    @Override
    public void run() {
    	System.out.println("comenzo hilo conexion batalla");
        if(tipo==servidor){
        conectado=true;
//        System.out.println("Server");
        try{
        server = new ServerSocket(puerto);//se establece el servidor de socket en el canal puerto
        socket = new Socket();//se establece un socket
        System.out.println("esperando un cliente: Servidor");
        socket = server.accept();//esperar a que se menade una peticion para conectarse
        
        entrada = new DataInputStream(socket.getInputStream());//obteniendo canal de entrada
        salida = new DataOutputStream(socket.getOutputStream());//el socket nos provee el canal de salida para enviar informacion
        
//        JOptionPane.showMessageDialog(bn, "confirmado un usuario....");
        System.out.println("ha entrado un cliente....");
        }catch(IOException e){}
        }
        
        if(tipo==cliente){
        	conectado=true;
        	try{
//        		System.out.println("cliente");
        		socket = new Socket(ip, puerto);
        		System.out.println("1");
        		entrada = new DataInputStream(socket.getInputStream());
        		System.out.println("2");
        		salida = new DataOutputStream(socket.getOutputStream());
        		System.out.println("Me conecte cliente");
        	}catch(Exception e){
        		JOptionPane.showMessageDialog(null, "Error de conección");
        		socket=null;
        	}
        }
        System.out.println("comenzo hilo conexion batalla");
    }

    public String getIp() {
        return ip;
    }

    public Object getMensaje() {
        return mensaje;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public DataInputStream getEntrada() {
        return entrada;
    }

    public DataOutputStream getSalida() {
        return salida;
    }
    
    boolean getConectado(){
        return conectado;
    }
}
