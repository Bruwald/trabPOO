package bInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import aGame.Carta;
import cliente.Cliente;
import server.Servidor;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class InterfaceMesa extends JFrame {

	private JPanel contentPane;
	private JLabel jbackground;
	private JLabel lblJogador0;
	private JLabel lblJogador1;
	private JLabel lblJogador2;
	private JLabel lblJogador3;
	private JLabel lblCartaCentro;
	private JLabel lblPlacar;
	private JLabel lblPlacarG1;
	private JLabel lblPlacarG2;
	private JLabel lblG1;
	private JLabel lblG2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		InterfaceMesa frame = new InterfaceMesa();
//		frame.setVisible(true);
//		
//		frame.pintarCarta(new Carta(1, 2), frame.getLblCartaCentro());
//		
//		new Scanner(System.in).nextLine();
//		
//		frame.pintarCarta(new Carta(2, 5), frame.getLblCartaCentro());
//		frame.pintarCarta(new Carta(3, 6), frame.getLblJogador0());
//		frame.pintarCarta(new Carta(1, 7), frame.getLblJogador1());
//		frame.pintarCarta(new Carta(1, 10), frame.getLblJogador2());
//		frame.pintarCarta(new Carta(4, 13), frame.getLblJogador3());
//		frame.pintarCarta(new Carta(2, 5), frame.getLblCartaCentro());
		
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					InterfaceMesa frame = new InterfaceMesa();
//					frame.setVisible(true);
////					frame.setTitle("Mesa");
////					frame.setResizable(false);
////					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////					frame.setBounds(0, 0, 1280, 1000);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public InterfaceMesa(Cliente cliente, String nome){
		setTitle("Mesa - " + nome);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1280, 1000);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		ImageIcon imageIcon; //------------------------------------
		Image image; //------------------------------------
		
//		imageIcon = new ImageIcon("Imagens/" + cartas2.getValor() + cartas2.getNaipe() + ".png");
//		image = imageIcon.getImage();
//		image = image.getScaledInstance(135, 230, Image.SCALE_DEFAULT);
//		imageIcon = new ImageIcon(image);
		
//		lblJogador2 = new JLabel(imageIcon);
		lblJogador2 = new JLabel();
		
		lblJogador2.setText("____________________________________________________");
		lblJogador2.setBounds(583, 11, 135, 230);
		contentPane.add(lblJogador2);
		
//		imageIcon = new ImageIcon("Imagens/" + cartas3.getValor() + cartas3.getNaipe() + ".png");
//		image = imageIcon.getImage();
//		image = image.getScaledInstance(135, 230, Image.SCALE_DEFAULT);
//		imageIcon = new ImageIcon(image);
		
		lblJogador3 = new JLabel();
		lblJogador3.setText("____________________________________________________");
		lblJogador3.setBounds(10, 382, 135, 230);
		contentPane.add(lblJogador3);
		
//		imageIcon = new ImageIcon("Imagens/" + cartas1.getValor() + cartas1.getNaipe()  + ".png");
//		image = imageIcon.getImage();
//		image = image.getScaledInstance(135, 230, Image.SCALE_DEFAULT);
//		imageIcon = new ImageIcon(image);
		
		lblJogador1 = new JLabel();
		lblJogador1.setText("____________________________________________________");
		lblJogador1.setBounds(1129, 382, 135, 230);
		contentPane.add(lblJogador1);
		
//		imageIcon = new ImageIcon("Imagens/" + cartas0.getValor() + cartas0.getNaipe() + ".png");
//		image = imageIcon.getImage();
//		image = image.getScaledInstance(135, 230, Image.SCALE_DEFAULT);
//		imageIcon = new ImageIcon(image);
		
		lblJogador0 = new JLabel();
		lblJogador0.setText("____________________________________________________");
		lblJogador0.setBounds(583, 730, 135, 230);
		contentPane.add(lblJogador0);
		
//		imageIcon = new ImageIcon("Imagens/carta.png");
//		image = imageIcon.getImage();
//		image = image.getScaledInstance(135, 230, Image.SCALE_DEFAULT);
//		imageIcon = new ImageIcon(image);
		
		lblCartaCentro = new JLabel();
		lblCartaCentro.setText("____________________________________________________");
		lblCartaCentro.setBounds(583, 382, 135, 230);
		contentPane.add(lblCartaCentro);
		
		lblPlacar = new JLabel("Placar");
		lblPlacar.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPlacar.setBounds(1081, 42, 46, 14);
		contentPane.add(lblPlacar);
		
		lblPlacarG1 = new JLabel("00");
		lblPlacarG1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPlacarG1.setBounds(1150, 42, 46, 14);
		contentPane.add(lblPlacarG1);
		
		lblPlacarG2 = new JLabel("00");
		lblPlacarG2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPlacarG2.setBounds(1218, 42, 46, 14);
		contentPane.add(lblPlacarG2);
		
		lblG1 = new JLabel("G1");
		lblG1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblG1.setBounds(1150, 11, 46, 14);
		contentPane.add(lblG1);
		
		lblG2 = new JLabel("G2");
		lblG2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblG2.setBounds(1218, 11, 46, 14);
		contentPane.add(lblG2);
		
		imageIcon = new ImageIcon("Imagens/background.png");
		image = imageIcon.getImage();
		image = image.getScaledInstance(1300, 1000, Image.SCALE_DEFAULT);
		imageIcon = new ImageIcon(image);
	    
		jbackground = new JLabel(imageIcon);
		jbackground.setSize(1300, 1000);
		jbackground.setLocation(0,0);
		contentPane.add(jbackground); 
		
	}
	
	public void pintarCarta(Carta carta, JLabel jogador){
		if(carta == null){
			jogador.setIcon(null);
			return;
		}
		
		ImageIcon imageIcon; //------------------------------------
		Image image; //------------------------------------
		
		imageIcon = new ImageIcon("Imagens/" + carta.getValor() + carta.getNaipe() + ".png");
		image = imageIcon.getImage();
		image = image.getScaledInstance(135, 230, Image.SCALE_DEFAULT);
		imageIcon = new ImageIcon(image);
		
		jogador.setIcon(imageIcon);
	}

	public JLabel getLblJogador0() {
		return lblJogador0;
	}

	public JLabel getLblJogador1() {
		return lblJogador1;
	}

	public JLabel getLblJogador2() {
		return lblJogador2;
	}

	public JLabel getLblJogador3() {
		return lblJogador3;
	}

	public JLabel getLblCartaCentro() {
		return lblCartaCentro;
	}
	
	
//	
//	public void paintComponent (Graphics g) throws IOException {
//		
//		AffineTransform AT = AffineTransform.getTranslateInstance(100, 100);
//		AT.rotate(Math.toRadians(45));
//		
//		BufferedImage Image = ImageIO.read(getClass().getResourceAsStream("carta.png"));
//		
//		Graphics2D newimage = (Graphics2D) g;
//		
//		newimage.drawImage(Image, AT, null);
//		
//	}
}
