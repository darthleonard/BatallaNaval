import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;


public class Sonido  extends Thread{/*
    int sonido;
    static final int agua=0;
    static final int explosion=1;

    public Sonido(int sonido) {
        this.sonido=sonido;
        start();
    }

    @Override
    public void run() {
        try {
        AudioInputStream cancion = null;
        InputStream in;
        if(this.sonido==explosion)
        {
        in = new FileInputStream("explosion.wav");
        cancion = new AudioInputStream(in);
        AudioPlayer.player.start(cancion);
        }
        else
        if(sonido==agua)
        {
        in = new FileInputStream("agua.wav");
        cancion = new AudioStream(in);
        AudioPlayer.player.start(cancion);
        }
        while(AudioPlayer.player.isAlive())
            Thread.sleep(1000);
        
        cancion.close();
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }
*/
}
