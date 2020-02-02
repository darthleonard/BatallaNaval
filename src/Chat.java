import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Chat extends JPanel implements KeyListener, Runnable{
    JTextField mensaje;
    JTextArea area;
    String usuario = "";
    Coneccion coneccion;
    boolean conectado=false;

    public Chat() {
        Construir();
    }
    
    public void Coneccion(String ip, int tipo, int puerto){
        if(puerto != 9999){
        coneccion = new Coneccion(ip, usuario, tipo);
        coneccion.setPuerto(puerto);
        coneccion.Conectar();
        conectado=true;
        Thread t = new Thread(this);
        t.start();
        }
        else
        JOptionPane.showMessageDialog(this, "Error, ese puerto esta ocupado");
    }

    private void Construir() {
        setLayout(new BorderLayout(10,10));
        setBorder("Chat de: "+usuario);
        mensaje = new JTextField();
        area = new JTextArea(10,10);
        JScrollPane js = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(js, BorderLayout.CENTER);
        add(mensaje, BorderLayout.SOUTH);
        area.setEditable(false);
        mensaje.addKeyListener(this);
    }
    
    private void setBorder(String titulo){
        TitledBorder t= BorderFactory.createTitledBorder(titulo);
        setBorder(t);
        repaint();
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
        setBorder(usuario);
    }
    
    void cerrarConecciones() throws IOException{
        coneccion.cerrarConecciones();
        coneccion=null;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        try {
            
            if(e.getKeyChar()=='\n'){
            if(coneccion!=null){
            if(coneccion.getSalida()!=null){
            coneccion.mandarMensaje(area.getText()+usuario+".- "+mensaje.getText()+"\n");
            area.setText(area.getText()+usuario+".- "+mensaje.getText()+"\n");
            mensaje.setText("");
            }else{
            JOptionPane.showMessageDialog(this, "Error de envio de mensaje, no hay otra conección");
            }
            }else{
            JOptionPane.showMessageDialog(this, "Error de envio de mensaje, primero estabece una conección");
            }
            }
            
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al enviar ensaje\n"
                    + "Se ha desconectado el oponete");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void run() {
        while(coneccion.getSalida()==null){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
        }
        //System.out.println("AQUIIIOIOIOIO");
        while(conectado){
            try {
                String mensaje = coneccion.getEntrada().readUTF();
                area.setText(mensaje);
            } catch (IOException ex) {}
        }
    }
}
