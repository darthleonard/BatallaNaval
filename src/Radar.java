
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Radar extends JPanel{
	private int filas, columnas;
	private int alto, ancho;
	private int barcos[][] = null;
	private Image fondo;
	private Image ImgBarco = null;
	private int idBarco = 0;
	private boolean edicion=false;
	private int cantidad=0;
	private int num_barcos=0;
	int tipo=0;
	private boolean borrador=false;

	public static final int OWN=0;
	public static final int OTHER=1;

	private boolean envio=true;
	//    Coneccion mensajes;
	private ArrayList<Point> quemados = new ArrayList();
	private ArrayList<Point> nada = new ArrayList();

	private Enfrentamiento enfrentamiento;
	String nombre="";

	public Radar(int tipo) {
		filas=0;
		columnas=0;
		ancho=0;
		alto=0;
		fondo = new ImageIcon("fondo.png").getImage();
		addMouseListener(new Evento());
		this.tipo=tipo;
	}

	public Radar(int[][] barcos,int tipo){
		if(barcos!=null) {
			filas=barcos.length;
			columnas=filas;
			establecerDatos();
		}
		fondo = new ImageIcon("fondo.png").getImage();
		addMouseListener(new Evento());
		this.tipo=tipo;
	}

	public Radar(int filas, int columnas,int tipo) {
		this.filas=filas;
		this.columnas=columnas;
		if(filas>0 && columnas>0){
			establecerDatos();
			this.barcos = new int[filas][columnas];
		}
		fondo = new ImageIcon("fondo.png").getImage();
		addMouseListener(new Evento());
		this.tipo=tipo;
		if(tipo==OWN)
			nombre = "YO";
		else
			nombre="ÉL";
	}

	public Radar(int filas, int columnas, int[][] barcos,int tipo) {
		this.filas=filas;
		this.columnas=columnas;
		this.barcos=barcos;
		if(filas>0 && columnas>0){
			establecerDatos();
		}
		fondo = new ImageIcon("fondo.png").getImage();
		addMouseListener(new Evento());
		this.tipo=tipo;
	}
	//---------------fin de los contructores -----------------------------------------------

	private void establecerDatos(){
		alto = getHeight()/filas;
		ancho = getWidth()/columnas;
	}

	//    void Conectarse(String ip, String usuario, int tipo){
	//        mensajes = new Coneccion(ip, usuario, tipo);
	//        mensajes.setPuerto(9999);
	//        mensajes.Conectar();
	//    }

	@Override
	public void paint(Graphics g) {
		establecerDatos();
		g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
		Cuadricula(g);
		Barcos(g);
	}

	private void Cuadricula(Graphics g){
		for (int i = 1; i <= filas; i++)
			g.drawLine(0, i*alto, getWidth(), i*alto);
		for (int j = 1; j <= columnas; j++)
			g.drawLine(j*ancho, 0, j*ancho, getHeight());
	}

	private void Barcos(Graphics g){
		if(tipo==OWN)
			if(barcos!=null)
				for (int i = 0; i < filas; i++)
					for (int j = 0; j < columnas; j++)
						if(barcos[i][j]!=0){
							int lar=barcos[i][j];
							g.drawImage(new ImageIcon("b"+lar+".png").getImage(), j*ancho, i*alto, lar*ancho,alto,this);
							j+=lar-1;
							//                    System.out.println("pos = "+i);
						}
		for (int i = 0; i < quemados.size(); i++) {
			int x=quemados.get(i).x;
			int y=quemados.get(i).y;
			g.drawImage(new ImageIcon("b10.gif").getImage(), x*ancho, y*alto, ancho,alto,this);
		}
		if(tipo==OTHER)
			for (int i = 0; i < nada.size(); i++) 
			{
				int x=nada.get(i).x;
				int y=nada.get(i).y;
				g.drawImage(new ImageIcon("nada.png").getImage(), x*ancho, y*alto, ancho,alto,this);
			}
	}

	private boolean Entra(int y,int x, int idBarco){
		int l=columnas-x+1;
		for (int i = 0; i < idBarco; i++) {
			if(barcos[y][x+i]!=0 || idBarco>=l)
				return false;
			if(x-i>=0)
				if(barcos[y][x-i]!=0)
					return false;
		}
		return true;
	}

	private void insertaBarco(int x, int y) {
		if(totalBarcosEnJuego() + idBarco > num_barcos) {
			JOptionPane.showMessageDialog(this, "Error de insersión. Te estas excediendo.");
			return;
		}
		if(idBarco == 1 || idBarco == 2 || idBarco == 4) {
			if(Entra(y,x, idBarco))
				for (int i = 0; i < idBarco; i++) {
					barcos[y][x+i]=idBarco;
				}
			cantidad+=idBarco;
		}
		else
			JOptionPane.showMessageDialog(this, "Error de insersión. No hay un barco seleccionado.\nIntenta de nuevo");
	}

	int totalBarcosEnJuego(){
		int total=0;
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				if(barcos[i][j]!=0){
					total++;
				}
			}
		}
		return total;
	}


	boolean ElAtaco(int fila, int columna){
		if(barcos[fila][columna]!=0){

			//primero la animacion de la explosion
			//Sonido s = new Sonido(Sonido.explosion);
			quemados.add(new Point(columna,fila));
			repaint();
			return true;
			//        if(Perdio())
			//            vive=false;
			//Mostrar mensae de burla
		}
		else
		{
			//Sonido s = new Sonido(Sonido.agua);
			return false;
			//animacion de el agua

		}
	}

	private boolean Perdio(){
		if(quemados.size()==num_barcos)
			return true;
		else
			return false;
	}

	void restablecerBarcos(){
		barcos=new int[filas][columnas];
	}


	public Image getImgBarco() {
		return ImgBarco;
	}

	public void setImgBarco(Image ImgBarco) {
		this.ImgBarco = ImgBarco;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int[][] getBarcos() {
		return barcos;
	}

	public void setBarcos(int[][] barcos) {
		this.barcos = barcos;
	}

	public int getColumnas() {
		return columnas;
	}

	public void setColumnas(int columnas) {
		this.columnas = columnas;
		establecerDatos();
	}

	public boolean isEdicion() {
		return edicion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public void setEdicion(boolean edicion) {
		this.edicion = edicion;
	}

	public int getFilas() {
		return filas;
	}

	public void setFilas(int filas) {
		this.filas = filas;
		establecerDatos();
	}

	public Image getFondo() {
		return fondo;
	}

	public void setFondo(Image fondo) {
		this.fondo = fondo;
	}

	public int getIdBarco() {
		return idBarco;
	}

	public void setIdBarco(int idBarco) {
		this.idBarco = idBarco;
	}

	public ArrayList<Point> getQuemados() {
		return quemados;
	}

	public int getNum_barcos() {
		return num_barcos;
	}

	public void setNum_barcos(int num_barcos) {
		if(num_barcos>=totalBarcosEnJuego())
			this.num_barcos = num_barcos;
		else
			JOptionPane.showMessageDialog(this, "Error de cambio de barcos,\n"
					+ "Los barcos que tienes en juego son mayores a tu cambio");
	}

	public void setBorrador(boolean editable) {
		this.borrador = editable;
	}

	public void setEnfrentamiento(Enfrentamiento enfrentamiento) {
		this.enfrentamiento = enfrentamiento;
	}

	public ArrayList<Point> getNada() {
		return nada;
	}

	public void setQuemados(ArrayList<Point> quemados) {
		this.quemados = quemados;
	}

	public void setNada(ArrayList<Point> nada) {
		this.nada = nada;
	}


	//clase de los eventos del raton --------------------------------------- - - - - - - - -
	class Evento extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX()/ancho;
			int y = e.getY()/alto;

			if(tipo==OWN){
				if(edicion){
					//                System.out.println("x = " + x);
					//                System.out.println("y = " + y);
					if(!borrador)
						insertaBarco(x, y);
					else{
						//                System.out.println("Entre");
						int n = barcos[y][x];
						for (int i = 0; i < n; i++) {
							barcos[y][x+i]=0;
						}
					}
					repaint();
				}
			}

			if(tipo==OTHER)
				if(envio){
					//enviar informaccion de las posiciones a atacar;
					//                System.out.println("envio las coordenadas:");
					//                System.out.println("x = " + x);
					//                System.out.println("y = " + y);
					if(enfrentamiento!=null)
						enfrentamiento.Atacar(y, x);
					else
						JOptionPane.showMessageDialog(null, "Conectate porfavor");
				}
		}

	}//fin de la clase de los eventos del raton ---------------------------- - - - - - - 

}