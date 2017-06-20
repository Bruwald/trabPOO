package bInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import aGame.Carta;
import cliente.Cliente;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfaceMao extends JFrame {

	private JPanel contentPane;
	private JLabel jbackground;
	private ButtonGroup group;
	private JRadioButton rdbtnCarta0;
	private JRadioButton rdbtnCarta1;
	private JRadioButton rdbtnCarta2;
	private JLabel lblCarta0;
	private JLabel lblCarta1;
	private JLabel lblCarta2;
	private JButton btnJogar;
	private JButton btnNao;
	private JButton btnSim;
	private JButton btnAumentar;
	private JLabel lblTrucar;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		InterfaceMesa frame2 = new InterfaceMesa();
//		frame2.setVisible(true);
//		
//		InterfaceMao frame = new InterfaceMao(new Carta[]{new Carta(1, 2), new Carta(1, 10), new Carta(1, 5)});
//		frame.setVisible(true);
//		frame.toFront();
//		
//		new Scanner(System.in).nextLine();
//		frame.dispose();
//		
//		frame = new InterfaceMao(new Carta[]{new Carta(3, 4), new Carta(2, 11), new Carta(3, 6)});
//		frame.setVisible(true);
//		frame.toFront();

		
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					InterfaceMao frame = new InterfaceMao(new Carta[]{new Carta(1, 2), new Carta(1, 10), new Carta(1, 5)});
//					frame.setVisible(true);
//					new Scanner(System.in).nextLine();
//					frame.dispose();
//					frame = new InterfaceMao(new Carta[]{new Carta(3, 4), new Carta(2, 11), new Carta(3, 6)});
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public InterfaceMao(Carta[] cartas, Cliente cliente, String nome) {
		setResizable(false);
		setTitle("Mao - " + nome);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon imageIcon; //------------------------------------
		Image image; //------------------------------------
	
		btnJogar = new JButton("Jogar");
		btnJogar.setForeground(Color.DARK_GRAY);
		btnJogar.setFont(new Font("Arial Black", Font.PLAIN, 16));
		//btnJogar.setBackground(Color.WHITE);
		btnJogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(rdbtnCarta0.isSelected()){
					System.out.println(cartas[0]);
					lblCarta0.setEnabled(false);
					rdbtnCarta0.setEnabled(false);	
										
					btnJogar.setEnabled(false);
					
					try {
						cliente.sendInt(0);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}else if(rdbtnCarta1.isSelected()){
					System.out.println(cartas[1]);
					lblCarta1.setEnabled(false);
					rdbtnCarta1.setEnabled(false);	
					
					btnJogar.setEnabled(false);
				
					try {
						cliente.sendInt(1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}else if(rdbtnCarta2.isSelected()){
					System.out.println(cartas[2]);
					lblCarta2.setEnabled(false);
					rdbtnCarta2.setEnabled(false);
					
					btnJogar.setEnabled(false);

					try {
						cliente.sendInt(2);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				group.clearSelection();
			}
		});
		btnJogar.setBounds(465, 248, 109, 52);
		contentPane.add(btnJogar);
		
		//CARTA 1
		imageIcon = new ImageIcon("Imagens/" + cartas[0].getValor() + cartas[0].getNaipe() + ".png");
		image = imageIcon.getImage();
		image = image.getScaledInstance(135, 230, Image.SCALE_DEFAULT);
		imageIcon = new ImageIcon(image);	    
		
		btnNao = new JButton("NAO");
		btnNao.setForeground(Color.DARK_GRAY);
		btnNao.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cliente.sendInt(0);

					btnSim.setVisible(false);
					btnNao.setVisible(false);
					btnAumentar.setVisible(false);
//					frameMao.getLblTrucar().setVisible(false);			
					lblTrucar.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNao.setBounds(475, 169, 89, 23);
		contentPane.add(btnNao);
		
		btnSim = new JButton("SIM");
		btnSim.setForeground(Color.DARK_GRAY);
		btnSim.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cliente.sendInt(1);
					
					btnSim.setVisible(false);
					btnNao.setVisible(false);
					btnAumentar.setVisible(false);
//					frameMao.getLblTrucar().setVisible(false);			
					lblTrucar.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSim.setBounds(475, 130, 89, 23);
		contentPane.add(btnSim);
		
		btnAumentar = new JButton("UP");
		btnAumentar.setForeground(Color.DARK_GRAY);
		btnAumentar.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnAumentar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cliente.sendInt(2);
					
					btnSim.setVisible(false);
					btnNao.setVisible(false);
					btnAumentar.setVisible(false);
//					frameMao.getLblTrucar().setVisible(false);			
					lblTrucar.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAumentar.setBounds(475, 209, 89, 23);
		contentPane.add(btnAumentar);
		
		lblCarta0 = new JLabel(imageIcon);		
		lblCarta0.setBounds(10, 11, 135, 230);
		contentPane.add(lblCarta0);
		
		//CARTA 2
		imageIcon = new ImageIcon("Imagens/" + cartas[1].getValor() + cartas[1].getNaipe() + ".png");
		image = imageIcon.getImage();
		image = image.getScaledInstance(135, 230, Image.SCALE_DEFAULT);
		imageIcon = new ImageIcon(image);	
	    
		lblCarta1 = new JLabel(imageIcon);
		lblCarta1.setBounds(155, 11, 135, 230);
		contentPane.add(lblCarta1);
		
		//208 303 - 73
		//135, 230
		//CARTA 3 ----------------------------------------
		imageIcon = new ImageIcon("Imagens/" + cartas[2].getValor() + cartas[2].getNaipe() + ".png");
		image = imageIcon.getImage();
		image = image.getScaledInstance(135, 230, Image.SCALE_DEFAULT);
		imageIcon = new ImageIcon(image);	
	    
		lblCarta2 = new JLabel(imageIcon);
		lblCarta2.setBounds(300, 11, 135, 230);
		contentPane.add(lblCarta2);
		
		lblTrucar = new JLabel("Trucar?");
		lblTrucar.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrucar.setForeground(Color.WHITE);
		lblTrucar.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblTrucar.setBounds(455, 67, 124, 47);
		contentPane.add(lblTrucar);
		
		rdbtnCarta0 = new JRadioButton("");
		rdbtnCarta0.setBounds(67, 248, 20, 20);
		rdbtnCarta0.setBackground(new Color(0, 102, 51));
		contentPane.add(rdbtnCarta0);
		
		rdbtnCarta1 = new JRadioButton("");
		rdbtnCarta1.setBounds(213, 248, 20, 20);
		rdbtnCarta1.setBackground(new Color(0, 102, 51));
		contentPane.add(rdbtnCarta1);
		
		rdbtnCarta2 = new JRadioButton("");
		rdbtnCarta2.setBounds(357, 248, 20, 20);
		rdbtnCarta2.setBackground(new Color(0, 102, 51));
		contentPane.add(rdbtnCarta2);
		
		group = new ButtonGroup();
	    group.add(rdbtnCarta0);
	    group.add(rdbtnCarta1);
	    group.add(rdbtnCarta2);
	    
	    lblCarta0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(lblCarta0.isEnabled())
					rdbtnCarta0.setSelected(true);
			}
		});
	    
	    lblCarta1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(lblCarta1.isEnabled())
					rdbtnCarta1.setSelected(true);
			}
		});
	    
	    lblCarta2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(lblCarta2.isEnabled())
					rdbtnCarta2.setSelected(true);
			}
		});
	    
	    imageIcon = new ImageIcon("Imagens/background.png");
		image = imageIcon.getImage();
		image = image.getScaledInstance(650, 400, Image.SCALE_DEFAULT);
		imageIcon = new ImageIcon(image);
	    
		jbackground = new JLabel(imageIcon);
		jbackground.setSize(650, 400);
		jbackground.setLocation(0,0);
		contentPane.add(jbackground); 
	}

	public JButton getBtnJogar() {
		return btnJogar;
	}

	public JButton getBtnNao() {
		return btnNao;
	}

	public JButton getBtnSim() {
		return btnSim;
	}

	public JButton getBtnAumentar() {
		return btnAumentar;
	}

	public JLabel getLblTrucar() {
		return lblTrucar;
	}
	
	
}
