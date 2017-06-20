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
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import aGame.Carta;
import cliente.Cliente;
import server.Servidor;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

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
	private JLabel lblRodada;
	private JLabel lblPlacarRodada;
	private JLabel lblGrupo1;
	private JLabel lblRodadaG1;
	private JLabel lblGrupo2;
	private JLabel lblRodadaG2;
	private JLabel lblBaralho;
	private StyledDocument doc;
	private SimpleAttributeSet leftRed, leftBlue, right; 
	private JLabel lblJogador_1;
	private JLabel lblJogador_0;
	private JLabel lblJogador_3;
	private JLabel lblJogador_2;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
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
//	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public InterfaceMesa(Cliente cliente, String nome){
		setTitle("Mesa - " + nome);
		setResizable(false);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 1280, 1000);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		ImageIcon imageIcon; //------------------------------------
		Image image; //------------------------------------
		
		lblBaralho = new JLabel();
		lblBaralho.setText("____________________________________________________");
		lblBaralho.setBounds(515, 346, 135, 230);
		contentPane.add(lblBaralho);
		
		pintarCarta(new Carta(0, 0), lblBaralho);
		
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
		lblPlacar.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlacar.setForeground(Color.WHITE);
		lblPlacar.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblPlacar.setBounds(984, 49, 91, 33);
		contentPane.add(lblPlacar);
		
		lblPlacarG1 = new JLabel("00");
		lblPlacarG1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlacarG1.setForeground(Color.WHITE);
		lblPlacarG1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblPlacarG1.setBounds(1078, 49, 91, 33);
		contentPane.add(lblPlacarG1);
		
		lblPlacarG2 = new JLabel("00");
		lblPlacarG2.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlacarG2.setForeground(Color.WHITE);
		lblPlacarG2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblPlacarG2.setBounds(1168, 49, 91, 33);
		contentPane.add(lblPlacarG2);
		
		lblG1 = new JLabel("G1");
		lblG1.setHorizontalAlignment(SwingConstants.CENTER);
		lblG1.setForeground(Color.WHITE);
		lblG1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblG1.setBounds(1078, 16, 91, 33);
		contentPane.add(lblG1);
		
		lblG2 = new JLabel("G2");
		lblG2.setHorizontalAlignment(SwingConstants.CENTER);
		lblG2.setForeground(Color.WHITE);
		lblG2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblG2.setBounds(1168, 16, 91, 33);
		contentPane.add(lblG2);
		
		imageIcon = new ImageIcon("Imagens/background.png");
		image = imageIcon.getImage();
		image = image.getScaledInstance(1300, 1000, Image.SCALE_DEFAULT);
		imageIcon = new ImageIcon(image);
		
		JLabel lblJogo = new JLabel("Jogo");
		lblJogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblJogo.setForeground(Color.WHITE);
		lblJogo.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblJogo.setBounds(984, 16, 91, 33);
		contentPane.add(lblJogo);
		
		lblRodada = new JLabel("Rodada");
		lblRodada.setHorizontalAlignment(SwingConstants.CENTER);
		lblRodada.setForeground(Color.WHITE);
		lblRodada.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblRodada.setBounds(10, 11, 91, 33);
		contentPane.add(lblRodada);
		
		lblPlacarRodada = new JLabel("Placar");
		lblPlacarRodada.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlacarRodada.setForeground(Color.WHITE);
		lblPlacarRodada.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblPlacarRodada.setBounds(10, 44, 91, 33);
		contentPane.add(lblPlacarRodada);
		
		lblGrupo1 = new JLabel("G1");
		lblGrupo1.setHorizontalAlignment(SwingConstants.CENTER);
		lblGrupo1.setForeground(Color.WHITE);
		lblGrupo1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblGrupo1.setBounds(104, 11, 91, 33);
		contentPane.add(lblGrupo1);
		
		lblRodadaG1 = new JLabel("00");
		lblRodadaG1.setHorizontalAlignment(SwingConstants.CENTER);
		lblRodadaG1.setForeground(Color.WHITE);
		lblRodadaG1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblRodadaG1.setBounds(104, 44, 91, 33);
		contentPane.add(lblRodadaG1);
		
		lblGrupo2 = new JLabel("G2");
		lblGrupo2.setHorizontalAlignment(SwingConstants.CENTER);
		lblGrupo2.setForeground(Color.WHITE);
		lblGrupo2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblGrupo2.setBounds(194, 11, 91, 33);
		contentPane.add(lblGrupo2);
		
		lblRodadaG2 = new JLabel("00");
		lblRodadaG2.setHorizontalAlignment(SwingConstants.CENTER);
		lblRodadaG2.setForeground(Color.WHITE);
		lblRodadaG2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblRodadaG2.setBounds(194, 44, 91, 33);
		contentPane.add(lblRodadaG2);
		
		leftRed = new SimpleAttributeSet(); //escrever na esquerda
        StyleConstants.setAlignment(leftRed, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(leftRed, Color.RED);
        
        leftBlue = new SimpleAttributeSet(); //escrever na esquerda
        StyleConstants.setAlignment(leftBlue, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(leftBlue, Color.BLUE);
        
        right = new SimpleAttributeSet(); //escrever na direita
        StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setForeground(right, Color.BLACK);
		
		JTextPane txtpnMensagensDoServidor = new JTextPane();
		txtpnMensagensDoServidor.setText("Mensagens do Servidor: ");
		txtpnMensagensDoServidor.setBounds(959, 664, 300, 280);
		txtpnMensagensDoServidor.setFont(new Font("Arial", Font.PLAIN, 18));
		txtpnMensagensDoServidor.setEditable(false);		
		//		contentPane.add(textPane);
				
		doc = txtpnMensagensDoServidor.getStyledDocument();
				
		lblJogador_0 = new JLabel("Jogador 0");
		lblJogador_0.setHorizontalAlignment(SwingConstants.CENTER);
		lblJogador_0.setForeground(Color.WHITE);
		lblJogador_0.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblJogador_0.setBounds(417, 911, 135, 33);
		contentPane.add(lblJogador_0);
		
		lblJogador_1 = new JLabel("Jogador 1");
		lblJogador_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblJogador_1.setForeground(Color.WHITE);
		lblJogador_1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblJogador_1.setBounds(1129, 333, 135, 33);
		contentPane.add(lblJogador_1);
		
		lblJogador_2 = new JLabel("Jogador 2");
		lblJogador_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblJogador_2.setForeground(Color.WHITE);
		lblJogador_2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblJogador_2.setBounds(433, 11, 135, 33);
		contentPane.add(lblJogador_2);
		
		lblJogador_3 = new JLabel("Jogador 3");
		lblJogador_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblJogador_3.setForeground(Color.WHITE);
		lblJogador_3.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblJogador_3.setBounds(10, 333, 135, 33);
		contentPane.add(lblJogador_3);

		JScrollPane scrollPane = new JScrollPane(txtpnMensagensDoServidor);
		scrollPane.setBounds(959, 664, 300, 280);
		//		scrollPane.setViewportView(txtPane);
				contentPane.add(scrollPane);
        
	    
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

	public JLabel getLblPlacarG1() {
		return lblPlacarG1;
	}

	public JLabel getLblPlacarG2() {
		return lblPlacarG2;
	}

	public JLabel getLblRodadaG1() {
		return lblRodadaG1;
	}

	public JLabel getLblRodadaG2() {
		return lblRodadaG2;
	}

	public JLabel getLblBaralho() {
		return lblBaralho;
	}

	public StyledDocument getDoc() {
		return doc;
	}

	public SimpleAttributeSet getLeftRed() {
		return leftRed;
	}

	public SimpleAttributeSet getLeftBlue() {
		return leftBlue;
	}

	public SimpleAttributeSet getRight() {
		return right;
	}
}
