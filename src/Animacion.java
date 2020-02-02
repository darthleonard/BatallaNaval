
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Animacion extends Thread{
    private JPanel campo;
    private URL url_imagen;
    private String str_imagen;
    private int suma = 50;
    private int x = 0, y = 0;
    private BufferedImage img;
    private BufferedImage img_recortada;
    private long tiempo=1000;
    
    public Animacion(JPanel campo, String url){
        this.campo = campo;
        str_imagen = url;
        Establecer();
    }
    
    void Establecer(){
        try{
        File f = new File(str_imagen);
        img = ImageIO.read(f);
        }
        catch(IOException ex){
        JOptionPane.showMessageDialog(campo, "Error para cargar la imagén");
        }
    }
    
    void IniciaAnimacion(int tiempo){
        this.tiempo = tiempo*1000;
        if(img!=null)
        start();
        else
        JOptionPane.showMessageDialog(campo, "Error imagen no cargada");
    }
    
    void IniciaAnimacion(){
        start();
    }

    public JPanel getCampo() {
        return campo;
    }

    public void setCampo(JPanel campo) {
        this.campo = campo;
        Establecer();
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
        Establecer();
    }

    public BufferedImage getImg_recortada() {
        return img_recortada;
    }

    public void setImg_recortada(BufferedImage img_recortada) {
        this.img_recortada = img_recortada;
        Establecer();
    }

    public String getStr_imagen() {
        return str_imagen;
    }

    public void setStr_imagen(String str_imagen) {
        this.str_imagen = str_imagen;
        Establecer();
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public URL getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(URL url_imagen) {
        this.url_imagen = url_imagen;
        Establecer();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public void run() {
        
        
        try {
        Thread.sleep(1000);
        for(int sum_x = 0; sum_x<img.getWidth()/suma; sum_x++){
        
        img_recortada=img.getSubimage(sum_x*suma, 0, suma, img.getHeight());
//        campo.repaint();
        Graphics2D g=(Graphics2D) campo.getGraphics();
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, campo.getWidth(), campo.getHeight());
        g.drawImage(img_recortada, null, x, y);
        
        Thread.sleep(tiempo);
        System.out.println("entre - - - - " + sum_x*suma);
        }
        } catch (InterruptedException ex) {}
    }
    
    public static void main(String[] args) {
        JPanel p = new JPanel();
        Animacion a = new Animacion(p, "explosion.png");
        JFrame v= new JFrame("Prueba");
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        v.setLayout(new GridLayout());
        v.add(p);
        
        v.setVisible(true);
        v.setSize(500, 500);
        
        a.setSuma(75);
        a.IniciaAnimacion(1);
    }
    
}
