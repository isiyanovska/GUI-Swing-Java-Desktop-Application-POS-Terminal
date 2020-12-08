package coffeeshop;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Toolkit;
import javax.swing.border.LineBorder;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Registracija extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtUser;
	Connection con;
	PreparedStatement pst;
	private JPasswordField txtPassword;
	private JPasswordField txtPassword2;
	private JTextField txtSurname;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registracija frame = new Registracija();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Registracija() {
		setBounds(new Rectangle(550, 390, 0, 0));
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\eclipse_workspace\\POS_Terminal\\img\\coffee-7-24.png"));
		setTitle("Регистрација");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 618, 573);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 222, 179));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\Actions-dialog-ok-apply-icon.png"));
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String name = txtName.getText();
				String surname = txtSurname.getText();
				String user = txtUser.getText();
				String pass = txtPassword.getText();
				String pass2 = txtPassword2.getText();
				
				try {
					Class.forName("org.h2.Driver");
					con = DriverManager.getConnection("jdbc:h2:./database/terminal","root","");
					if(pass.equals(pass2)) {
		    		String query = "insert INTO korisnici(ime,prezime,korisnicko_ime,lozinka)values('"+name+"','"+surname+"','"+user+"','"+pass+"')";
		    		
		    		pst = con.prepareStatement(query);
		    		pst.executeUpdate();
		    		JOptionPane.showMessageDialog(contentPane, "Креиран е нов корисник!");
				}else {
					JOptionPane.showMessageDialog(contentPane, "Внесената лозинка не се совпаѓа!");
					}
			}catch(SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			}

		});
		btnNewButton.setBounds(318, 459, 101, 28);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Откажи");
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewButton_1.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\Close-2-icon.png"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(con!=null)
					con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			setVisible(false);
		}
				
		});
		btnNewButton_1.setBounds(445, 459, 107, 28);
		contentPane.add(btnNewButton_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(51, 113, 501, 312);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBackground(new Color(240, 255, 255));
		txtName.setForeground(Color.BLACK);
		txtName.setBounds(275, 30, 197, 26);
		panel.add(txtName);
		txtName.setColumns(10);
		
		txtUser = new JTextField();
		txtUser.setBackground(new Color(240, 255, 255));
		txtUser.setForeground(Color.BLACK);
		txtUser.setBounds(275, 138, 197, 26);
		panel.add(txtUser);
		txtUser.setColumns(10);
		
		JLabel label_2 = new JLabel("Повтори лозинка");
		label_2.setForeground(Color.BLACK);
		label_2.setBounds(67, 251, 154, 14);
		panel.add(label_2);
		label_2.setFont(new Font("Arial", Font.BOLD, 13));
		
		JLabel label_1 = new JLabel("Лозинка");
		label_1.setForeground(Color.BLACK);
		label_1.setBounds(67, 193, 154, 14);
		panel.add(label_1);
		label_1.setFont(new Font("Arial", Font.BOLD, 13));
		
		JLabel label = new JLabel("Корисничко име");
		label.setForeground(Color.BLACK);
		label.setBounds(67, 138, 154, 14);
		panel.add(label);
		label.setFont(new Font("Arial", Font.BOLD, 13));
		
		JLabel lblNewLabel = new JLabel("Име");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(67, 30, 154, 14);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
		
		txtPassword = new JPasswordField();
		txtPassword.setBackground(new Color(240, 255, 255));
		txtPassword.setForeground(Color.BLACK);
		txtPassword.setBounds(275, 200, 197, 26);
		panel.add(txtPassword);
		
		txtPassword2 = new JPasswordField();
		txtPassword2.setBackground(new Color(240, 255, 255));
		txtPassword2.setForeground(Color.BLACK);
		txtPassword2.setBounds(275, 251, 197, 26);
		panel.add(txtPassword2);
		
		JLabel label_3 = new JLabel("Презиме");
		label_3.setForeground(Color.BLACK);
		label_3.setFont(new Font("Arial", Font.BOLD, 13));
		label_3.setBounds(67, 85, 154, 14);
		panel.add(label_3);
		
		txtSurname = new JTextField();
		txtSurname.setBackground(new Color(240, 255, 255));
		txtSurname.setForeground(Color.BLACK);
		txtSurname.setColumns(10);
		txtSurname.setBounds(275, 85, 197, 26);
		panel.add(txtSurname);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(new Color(160, 82, 45));
		panel_1.setBounds(0, 0, 653, 89);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Нов корисник");
		lblNewLabel_1.setBounds(211, 22, 181, 35);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 24));
	}
}
