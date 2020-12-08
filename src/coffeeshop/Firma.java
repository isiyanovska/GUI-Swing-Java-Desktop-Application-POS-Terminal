package coffeeshop;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.HeadlessException;
import javax.swing.UIManager;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

public class Firma extends JFrame {

	private JPanel contentPane;
	private static JTextField username;
	private JPasswordField password;
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private String user;
	
     /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Firma frame = new Firma();
					frame.setVisible(true);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Firma() {
		setTitle("Најави се");
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\eclipse_workspace\\POS_Terminal\\img\\coffee-7-24.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1444, 788);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 240, 230));
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(210, 180, 140));
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(485, 400, 353, 192);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Корисничко име");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel_1.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\username.png"));
		lblNewLabel_1.setBounds(21, 19, 125, 33);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Лозинка");
		lblNewLabel_2.setForeground(Color.BLACK);
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel_2.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\lock.png"));
		lblNewLabel_2.setBounds(31, 67, 79, 33);
		panel.add(lblNewLabel_2);
		
		username = new JTextField();
		username.setBackground(new Color(240, 255, 255));
		username.setForeground(new Color(0, 0, 0));
		username.setBounds(195, 22, 135, 26);
		panel.add(username);
		username.setColumns(10);
		password = new JPasswordField();
		password.setBackground(new Color(240, 255, 255));
		password.setForeground(new Color(0, 0, 0));
		password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String user = username.getText();
					String pass = password.getText();
					
					try {
						Class.forName("org.h2.Driver");
						con = DriverManager.getConnection("jdbc:h2:./database/terminal","root","");   
						
						String query="SELECT FROM korisnici WHERE korisnicko_ime='"+user+"'AND lozinka='"+pass+"'";
						pst = con.prepareStatement(query);
						rs=pst.executeQuery();
						if(rs.next()) {
							
							JOptionPane.showMessageDialog(contentPane, "Успешно се најавивте!");
							
							Smetka smetka = new Smetka(username.getText());
							smetka.setVisible(true);
							smetka.setExtendedState(JFrame.MAXIMIZED_BOTH);
							setVisible(false);
						}else {
							JOptionPane.showMessageDialog(contentPane, "Погрешна лозинка или корисничко име!");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}		
		});
		password.setBounds(195, 70, 135, 26);
		panel.add(password);
		JButton login = new JButton("Најави се\r\n");
		login.setForeground(Color.BLACK);
		login.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\Actions-dialog-ok-apply-icon.png"));
		login.setFont(new Font("Arial", Font.BOLD, 12));
		login.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					Class.forName("org.h2.Driver");
					con = DriverManager.getConnection("jdbc:h2:./database/terminal","root","");   
					String user = username.getText();
					String pass = password.getText();
					String query="SELECT FROM korisnici WHERE korisnicko_ime='"+user+"'AND lozinka='"+pass+"'";
					pst = con.prepareStatement(query);
					rs=pst.executeQuery();
					
					if(rs.next()) {
					
						JOptionPane.showMessageDialog(contentPane, "Успешно се најавивте!");
						
						Smetka smetka = new Smetka(username.getText());
						smetka.setVisible(true);
						smetka.setExtendedState(JFrame.MAXIMIZED_BOTH);
						setVisible(false);
					}else {
						JOptionPane.showMessageDialog(contentPane, "Погрешна лозинка или корисничко име!");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		login.setBounds(69, 132, 125, 33);
		panel.add(login);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\contacts-32.png"));
		lblNewLabel_5.setBounds(145, 16, 40, 32);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\padlock-32.png"));
		lblNewLabel_4.setBounds(147, 67, 38, 33);
		panel.add(lblNewLabel_4);
		
		JButton btnNewButton = new JButton("Затвори");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\Close-2-icon.png"));
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewButton.setBounds(214, 132, 116, 33);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\logo.png"));
		lblNewLabel.setBounds(368, 28, 570, 305);
		contentPane.add(lblNewLabel);
		
}
}
