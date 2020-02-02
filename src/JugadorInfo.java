import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

class JugadorInfo extends JPanel{
        JLabel texnombre, texbarcosVivos, texbarcosMuertos;
        JLabel infnombre, infbarcosVivos, infbarcosMuertos;
        String nombre="FULANITO";
        FlowLayout f = new FlowLayout();
        Color Fondo = Color.GREEN;

        public JugadorInfo() {
            setLayout(f);
            texnombre=new JLabel("Jugador"); 
            texbarcosVivos=new JLabel("Barcos vivos"); 
            texbarcosMuertos=new JLabel("Barcos muertos"); 
            
            infnombre=new JLabel("Fulanito");
            infbarcosVivos=new JLabel("0"); 
            infbarcosMuertos=new JLabel("0"); 
            
            add(texnombre);
            add(infnombre);
            add(texbarcosVivos);
            add(infbarcosVivos);
            add(texbarcosMuertos);
            add(infbarcosMuertos);
        }
        
        void setNombre(String nombre){
        infnombre.setText(nombre);
        }
        
        void setBarcosVivos(int vivos){
        infbarcosVivos.setText(""+vivos);
        }
        
        void DisminuyeBarcosVivos(){
        infbarcosVivos.setText(""+(Integer.parseInt(infbarcosVivos.getText())-1));
        infbarcosMuertos.setText(""+(Integer.parseInt(infbarcosVivos.getText())+1));
        }
        
        void setBarcosMuertos(int muertos){
        infbarcosMuertos.setText(""+muertos);
        }
        
        String getNombre(){
        return infnombre.getText();
        }
        
        int getBarcosVivos(){
        return Integer.parseInt(infbarcosVivos.getText());
        }
        
        int getBarcosMuertos(){
        return Integer.parseInt(infbarcosMuertos.getText());
        }
        
        boolean isMuerto(){
        if(Integer.parseInt(infbarcosVivos.getText())==0)
            return true;
        else
            return false;
        }
    }