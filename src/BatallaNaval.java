
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class BatallaNaval extends JFrame{
    Radar r_mio;
    Radar r_oponente;
    Chat chat;
    int num_barcos=5;
    InterfazConectar i ;
    
    //componentes de la batalla en juego
    JugadorInfo inf_mio;
    JugadorInfo inf_oponente;
    
    //componentes de la edicion
    JPanel edicion;
    JLabel lbl_filas, lbl_columnas, lbl_totalBarcos;
    JTextField txt_filas, txt_columnas, txt_totalBarcos;
    JButton aceptar;
    
    //Menu cuando esta en el juego
    JMenuBar menuJuego;
    JMenu coneccion;
    JMenuItem conectarChat, ConectarOtroUsuario, cerrarConeccion;
    
    //Menu cuando esta editando
    JMenuBar menuEdicion;
    JMenu opciones;
    JMenuItem lapiz, borrador, terminar;
    
    Coneccion checa;
    Enfrentamiento en;
    JPanel pnl_arriba;
    JPanel pnl_abajo;
    JPanel pnl_info;
    Artilleria a;
    
    public BatallaNaval() throws IOException{
        super("Batalla naval");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        pnl_arriba = new JPanel();
        pnl_abajo = new JPanel();
        pnl_info = new JPanel();
        a = new Artilleria();
        edicion = new JPanel(new FlowLayout());
        lbl_columnas = new JLabel("Columnas");
        lbl_filas = new JLabel("Filas");
        lbl_totalBarcos = new JLabel("Total de barcos");
        
        txt_columnas = new JTextField(5);
        txt_filas = new JTextField(5);
        txt_totalBarcos = new JTextField(5);
        
        aceptar = new JButton("Aplicar");
        r_mio = new Radar(10,10,Radar.OWN);
        r_oponente = new Radar(10,10,Radar.OTHER);
        inf_mio = new JugadorInfo();
        setBorder("Informacion de ", inf_mio);
        inf_oponente = new JugadorInfo();
        setBorder("Informacion de oponente", inf_oponente);
        chat = new Chat();
        menuEnJuego();
        menuEdicion();
        Edicion(1);
        setJMenuBar(menuEdicion);
        cerrarConeccion.setEnabled(false);
        ConectarOtroUsuario.setEnabled(false);
        setVisible(true);
        setSize(510, 500);
        Escuchadores();
        checa = new Coneccion(null, null, WIDTH);
    }
    
    private void Escuchadores(){
        aceptar.addActionListener(new Acciones(1));
        terminar.addActionListener(new Acciones(2));
        lapiz.addActionListener(new Acciones(3));
        borrador.addActionListener(new Acciones(4));
        
        conectarChat.addActionListener(new Acciones(5));
        ConectarOtroUsuario.addActionListener(new Acciones(6));
        cerrarConeccion.addActionListener(new Acciones(7));
    }
    
    void setDimensiones(int filas, int columnas){
        r_mio.setFilas(filas);
        r_mio.setColumnas(columnas);
    }
    
    private void menuEnJuego(){
        menuJuego = new JMenuBar();
        coneccion = new JMenu("Coneccion");
        conectarChat = new JMenuItem ("Conectar el chat");
        ConectarOtroUsuario = new JMenuItem ("Conectar para jugar");
        cerrarConeccion = new JMenuItem ("Cerrar conecciones");
//        CambiarUsuarioChat = new JMenuItem ("Cambiar el nombre de usuario");
        
        menuJuego.add(coneccion);
        coneccion.add(conectarChat);
        coneccion.add(ConectarOtroUsuario);
        coneccion.add(cerrarConeccion);
//        coneccion.add(CambiarUsuarioChat);
        
    }
    
    private void menuEdicion(){
        menuEdicion = new JMenuBar();
        opciones = new JMenu("Opciones");
        lapiz = new JMenuItem("Lapiz");
        borrador = new JMenuItem("Borrador");
        terminar = new JMenuItem("Jugar");
        
        menuEdicion.add(opciones);
        opciones.add(lapiz);
        opciones.add(borrador);
        opciones.add(terminar);
    }
    
    void setBorder(String titulo, JPanel p){
        TitledBorder b = BorderFactory.createTitledBorder(titulo);
        p.setBorder(b);
    }

    private void enJuego() {
        if(getContentPane().getComponentCount()>0)
        getContentPane().removeAll();
        r_mio.setEdicion(false);
        setLayout(new BorderLayout());
        
        pnl_arriba.setLayout(new GridLayout(1, 2,10,10));
        setBorder("Radares", pnl_arriba);
        
        pnl_arriba.add(r_mio);
        pnl_arriba.add(r_oponente);
        
        pnl_abajo.setLayout(new GridLayout(1, 2,10,10));
        pnl_info.setLayout(new GridLayout(2, 1,10,10));
        
        pnl_info.add(inf_mio);
        pnl_info.add(inf_oponente);
        
        pnl_abajo.add(pnl_info);
        pnl_abajo.add(chat);
        
        add(pnl_arriba, BorderLayout.CENTER);
        add(pnl_abajo, BorderLayout.SOUTH);
        setJMenuBar(menuJuego);
        repaint();
        setSize(getWidth()+20,getHeight()+20);
    }
    
    private void Edicion(int totalBarcos){
        if(getContentPane().getComponentCount()>0)
            getContentPane().removeAll();
        
        r_mio.setEdicion(true);
        num_barcos = totalBarcos;
        r_mio.setNum_barcos(num_barcos);
        setLayout(new BorderLayout(20, 20));
        setBorder("Barcos", a);
        setBorder("Panel Edicion", r_mio);
        
        edicion.add(lbl_filas);
        edicion.add(txt_filas);
        edicion.add(lbl_columnas);
        edicion.add(txt_columnas);
        edicion.add(lbl_totalBarcos);
        edicion.add(txt_totalBarcos);
        edicion.add(aceptar);
        
        add(edicion, BorderLayout.NORTH);
        add(r_mio, BorderLayout.CENTER);
        add(a, BorderLayout.SOUTH);
        
        repaint();
    }
    
    boolean isValido(String n){
        try{
        Integer.parseInt(n);
        }catch(java.lang.NumberFormatException el){
//        System.out.println("entre");
        return false;
        }
        return true;
    }
    
    public static void main(String[] args) throws IOException {
        new BatallaNaval();
    }
    
    /*
     * Clase de toda la artilleria pesada....
     **/
    class Artilleria extends JPanel implements MouseListener{
        ImageIcon img[] = new ImageIcon[3];
        Icon imagenes[] = new Icon[3];
        JLabel lbl_imagenes[]= new JLabel[3];
        String nombres[]={"b1.png","b2.png","b4.png"};
        
        Color colorFondo=Color.GREEN;

        public Artilleria(){
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            for (int i = 0; i < 3; i++) {
                img[i]=new ImageIcon(nombres[i]);
                imagenes[i]=new ImageIcon(img[i].getImage().getScaledInstance(190, 80, 16));
                lbl_imagenes[i]=new JLabel(imagenes[i]);
                //lbl_imagenes[i].setBackground(colorFondo);
                lbl_imagenes[i].addMouseListener(this);
                add(lbl_imagenes[i]);
            }
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            
            for (int i = 0; i < lbl_imagenes.length; i++)
            if(e.getSource()==lbl_imagenes[i]){
                if(i==2)
                r_mio.setIdBarco(i+2);
                else
                r_mio.setIdBarco(i+1);
            }
         }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
    
    /*
     * Clase de la informacion de los jugadores en general....
     **/
    

    class Acciones implements ActionListener{
        int n;

        public Acciones(int n) {
            this.n = n;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch(n){
            case 1: //aplicar cambios de filas, columnas y total de barcos
                if(isValido(txt_filas.getText())&&isValido(txt_columnas.getText())&&isValido(txt_totalBarcos.getText())){
//                System.out.println("entre");
                int opc=JOptionPane.showConfirmDialog(r_mio, "Estas seguro, tus barcos se perderan");
                if(opc==0){
                r_mio.setFilas(Integer.parseInt(txt_filas.getText()));
                r_mio.setColumnas(Integer.parseInt(txt_columnas.getText()));
                r_mio.restablecerBarcos();
                r_mio.setBorrador(false);
                }
                r_mio.setNum_barcos(Integer.parseInt(txt_totalBarcos.getText()));
                num_barcos=Integer.parseInt(txt_totalBarcos.getText());
                r_mio.repaint();
                }
                else
                JOptionPane.showMessageDialog(r_mio, "Error, algun valor no es valido");
                
            break;
            case 2: //terminar edicion
                if(num_barcos==r_mio.totalBarcosEnJuego()){
                    r_mio.setEdicion(false);
                    enJuego();
                }
                else
                    JOptionPane.showMessageDialog(null, "Error, completa la cantidad pedida");
            break;
            case 3://lapiz
                r_mio.setBorrador(false);
            break;
            case 4: //borrador
                r_mio.setBorrador(true);
            break;
            case 5: //conectar el chat
                if(!chat.isConectado() && i==null){
                i= new InterfazConectar(chat, r_oponente, r_mio);
                ConectarOtroUsuario.setEnabled(true);
                }
                else
                JOptionPane.showMessageDialog(rootPane, "Ya estas conectado carnal, si no, cierra las conecciones e intenta de nuevo");
            break;
            case 6: //conectarse para jugar
                if(chat.isConectado()){
                    //conectar para la pelea
                    //ip, usuario, tipo
                    if(en==null){
                    	en = new Enfrentamiento(inf_mio, inf_oponente, r_oponente, r_mio, i.getIp(), chat.usuario, i.getTipo(), 9000);
                    	r_oponente.setEnfrentamiento(en);
                    	setBorder("Informacion de "+chat.usuario, inf_mio);
                    	inf_mio.setBarcosVivos(r_mio.getNum_barcos());
                    	inf_mio.setBarcosMuertos(0);
                    	inf_mio.setNombre(chat.usuario);
                    	
                    	inf_oponente.setBarcosVivos(r_mio.getNum_barcos());
                    	inf_oponente.setBarcosMuertos(0);
                    	if(i.getTipo()==Coneccion.servidor)
                    		en.setTurno(true);
                    	else
                    		en.setTurno(false);
                    	cerrarConeccion.setEnabled(true);
                    }else
                        JOptionPane.showMessageDialog(rootPane, "Error, ya estas en guerra!!!!");
                }else
                	JOptionPane.showMessageDialog(r_mio.getParent(), "Error, por cuestiones de conectividad " +
                													 "tienes primero que conectarte al chat");
            break;
            case 7: //cerrar conecciones
            	try {
            		chat.cerrarConecciones();
            		en.cerrarConecciones();
            		setJMenuBar(menuEdicion);
            	} catch (IOException ex) {
            		JOptionPane.showMessageDialog(null, "Error, debrias estar conectado para cerrar sesion");
            	}
            break;
            }
        }
        
    }
}
