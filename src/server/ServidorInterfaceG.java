package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import cliente.ChatMessage;
import server.Servidor;

public class ServidorInterfaceG extends JFrame {

	private JPanel contentPane;
	private JButton btnClose, btnStart;

	/**
	 * Launch the application.  Main do Servidor, responsavel pela inicializacao do programa no servidor (Versao com interface)
	 * Apenas eh possivel encerra-lo no fim do jogo, e nao eh necessario clicar em start
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		//Servidor e Cliente TCP/IP ("basico") 
		System.out.println("Iniciando servidor.");
		
		//ServerSocket server = new ServerSocket(2525); //porta (onde "ficara" o servidor) [default ip -> local]
		Servidor servidor = new Servidor(2525); //Criar instancia do servidor e iniciar 
		servidor.start(); //apos o start a thread principal continua rodando junto com a auxiliar do servidor
		
		ServidorInterfaceG frame = new ServidorInterfaceG();
		frame.setVisible(true);
		
		frame.getBtnClose().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//ServerSocket server = new ServerSocket(2525); //porta (onde "ficara" o servidor) [default ip -> local]
				System.out.println("Encerrando Servidor.");
				try {
					servidor.stop();
					
					frame.dispose();
				} catch (Exception e) {
					e.printStackTrace();
				} //encerrar servidor
			}
		});		
	}
	
	public JButton getBtnClose() {
		return btnClose;
	}

	public JButton getBtnStart() {
		return btnStart;
	}

	/**
	 * Create the frame.
	 */
	public ServidorInterfaceG() {
		setFont(new Font("Arial", Font.PLAIN, 12));
		setTitle("Server");
		setResizable(false);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		//setUndecorated(true);
		//setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnClose = new JButton("Close");
		btnClose.setToolTipText("Close Server");
		btnClose.setFont(new Font("Arial", Font.PLAIN, 16));
//		btnSend.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//						
//			}
//		});
		btnClose.setBounds(329, 350, 100, 44);
		contentPane.add(btnClose);	
		
		btnStart = new JButton("Start");
		btnStart.setToolTipText("Open Server");
		btnStart.setFont(new Font("Arial", Font.PLAIN, 16));
		btnStart.setBounds(15, 350, 100, 44);
		contentPane.add(btnStart);
	}
}
