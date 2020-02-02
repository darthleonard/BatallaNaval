
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class InterfazConectar extends JFrame implements ActionListener{
    private int puerto = 9000;
    private String ip = "localhost";
    private int tipo = Coneccion.servidor;
    private String usuario="";
    
    private JLabel lbl_ip, lbl_puerto, lbl_tipo, lbl_usuario;
    private JTextArea txt_ip, txt_puerto, txt_usuario;
    private JComboBox cmb_tipo;
    private JButton aceptar, cancelar;
    private JPanel pnl_botones, pnl_opciones;
    private Chat chat;
    private Radar oponente, mio;
    
    public InterfazConectar(Chat chat, Radar oponente, Radar mio){
        super("Coneccion");
        this.chat=chat;
        this.oponente=oponente;
        this.mio = mio;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lbl_usuario = new JLabel("Usuario");
        lbl_ip = new JLabel("Ip");
        lbl_puerto = new JLabel("Puerto");
        lbl_tipo = new JLabel("Tipo de conección");
        txt_usuario = new JTextArea();
        txt_ip = new JTextArea();
        txt_puerto = new JTextArea();
        String[] i = {"Servidor", "Cliente"};
        cmb_tipo = new JComboBox(i);
        aceptar = new JButton("Aceptar");
        cancelar = new JButton("Cancelar");
        pnl_botones = new JPanel(new FlowLayout());
        aceptar.addActionListener(this);
        cancelar.addActionListener(this);
        pnl_botones.add(aceptar);
        pnl_botones.add(cancelar);
        pnl_opciones = new JPanel(new GridLayout(4, 2, 5, 5));
        pnl_opciones.add(lbl_usuario);
        pnl_opciones.add(txt_usuario);
        pnl_opciones.add(lbl_ip);
        pnl_opciones.add(txt_ip);
        pnl_opciones.add(lbl_puerto);
        pnl_opciones.add(txt_puerto);
        pnl_opciones.add(lbl_tipo);
        pnl_opciones.add(cmb_tipo);
        
        setLayout(new BorderLayout());
        add(pnl_opciones, BorderLayout.CENTER);
        add(pnl_botones, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        setResizable(false);
    }
    
    private boolean isIpValida(String ip){
        if(ip.equals("localhost"))
            return true;
        int total=0;
        String num="";
        for (int i = 0; i < ip.length(); i++) {
            char c = ip.charAt(i);
            if(c=='.'){
                total++;
                num="";
            }
            else{
                try{
                num+=c+"";
                if(Integer.parseInt(num)>255)
                    return false;
                }catch(java.lang.NumberFormatException el){
                    return false;
                }
            }
            
        }
        if(total==3)
            return true;
        else
            return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==aceptar){
            if(!chat.isConectado()){
            if(isIpValida(txt_ip.getText())){
                if(puerto!=9999){
                    ip=txt_ip.getText();
                    puerto=Integer.parseInt(txt_puerto.getText());
                    if(cmb_tipo.getSelectedItem()=="Servidor")
                        tipo = Coneccion.servidor;
                    if(cmb_tipo.getSelectedItem()=="Cliente")
                        tipo = Coneccion.cliente;
                    chat.setUsuario(txt_usuario.getText());
                    System.out.println("ip = " + ip);
                    System.out.println("puerto = " + puerto);
                    System.out.println("tipo = " + tipo);
                    chat.Coneccion(ip, tipo, puerto);
                    this.dispose();
                }else{
                    JOptionPane.showMessageDialog(this, "Error de coneccion, el puerto esta mal");
                }
            }else{
                JOptionPane.showMessageDialog(this, "Error de coneccion. La ip no es valida");
            }
            }else{
                JOptionPane.showMessageDialog(this, "Error de coneccion. Ya has establecido una coneccion");
            }
        }
        if(e.getSource()==cancelar){
            this.dispose();
        }
    }

    public String getIp() {
        return ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public int getTipo() {
        return tipo;
    }
    
//    public static void main(String[] args) {
//        InterfazConectar i = new InterfazConectar();
////        System.out.println(i.isIpValida("182.09.87.2"));//true
////        System.out.println(i.isIpValida("255.255.255.256"));//true
////        System.out.println(i.isIpValida("-182.09.87.2"));//false
////        System.out.println(i.isIpValida("182.09.87..2"));//false
////        System.out.println(i.isIpValida("182.09.87.2."));//false
//    }

}
