

import java.awt.Point;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Enfrentamiento extends Coneccion{
    private Thread t;
//    boolean ataque=false, recibo=false;
    private Radar r_mio, r_oponente;
    private boolean turno=false;
    private int fila=0, columna=0;
    private boolean enJuego=true;
    private JugadorInfo inf_mio, inf_oponente;
    private boolean muerto=false, gane=false;
    
    public Enfrentamiento(JugadorInfo inf_mio, JugadorInfo inf_oponente, Radar r_oponente, Radar r_mio, String ip, String usuario, int tipo, int puerto) {
        super(ip, usuario, tipo);
        this.r_mio=r_mio;
        this.r_oponente=r_oponente;
        this.inf_mio=inf_mio;
        this.inf_oponente=inf_oponente;
        setPuerto(puerto);
        Conectar();
        t = new Thread(new hilo());
        t.start();
    }

    void Atacar(int fila, int columna){
        this.fila=fila;
        this.columna=columna;
        if(!(muerto || gane)){
        if(turno){
        try {
            getSalida().writeInt(fila);
            getSalida().writeInt(columna);
            turno=false;
//            System.out.println("Mande");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error, checa la conección");
        }
        }else{
            JOptionPane.showMessageDialog(null, "Espera tu turno!!!");
        }
        }
        else
            if(gane)
                JOptionPane.showMessageDialog(null, "El juego se ha terminado, has ganado!!");
            else
                JOptionPane.showMessageDialog(null, "El juego se ha terminado, has perdido!!");
                
    }

    @Override
    void cerrarConecciones() throws IOException {
        super.cerrarConecciones();
        fila=0;
        columna=0;
        enJuego=false;
    }

    boolean getTurno() {
        return turno;
    }
    
    void setTurno(boolean turno){
        this.turno = turno;
    }
    
    class hilo implements Runnable{
    @Override
    public void run() {
        while(getEntrada()==null){
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {}
        }
        
        while(enJuego){
        //turno = true
        try {
        if(turno){
            boolean pegue=getEntrada().readBoolean();
            if(pegue){
                r_oponente.getQuemados().add(new Point(columna,fila));
                inf_oponente.DisminuyeBarcosVivos();
            }
            else
                r_oponente.getNada().add(new Point(columna,fila));
            r_oponente.repaint();
            turno=false;
        }
        if(inf_mio.isMuerto()){
            muerto=true;
            getSalida().writeBoolean(false);
            JOptionPane.showMessageDialog(r_mio, "ESTAS MUERTO!!!!");
            break;
        }
        if(inf_oponente.isMuerto()){
            gane=true;
            getSalida().writeBoolean(false);
            JOptionPane.showMessageDialog(r_oponente, "HAS GANADO!!!!");
            break;
        }
        int fila = getEntrada().readInt();
        int columna = getEntrada().readInt();
        if(r_mio.ElAtaco(fila, columna)){
            getSalida().writeBoolean(true);
            if(inf_mio.isMuerto()){
                JOptionPane.showMessageDialog(r_mio, "ESTAS MUERTO!!!!");
                muerto=true;
                break;
            }
            if(inf_oponente.isMuerto()){
                JOptionPane.showMessageDialog(r_oponente, "HAS GANADO!!!!");
                gane=true;
                break;
            }
            inf_mio.DisminuyeBarcosVivos();
        }
        else
            getSalida().writeBoolean(false);
        turno=true;
        }
        catch (IOException ex) {
            enJuego=false;
        }
        }
    }}
}
