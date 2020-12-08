package coffeeshop;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.http.HttpRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Toolkit;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Frame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.LineBorder;


public class Smetka extends JFrame {
    
	private JPanel contentPane;
	private JTextField txtBarCode;
	private JTextField txtProduct;
	private JTextField txtPrice;
	private JTextField txtQuantity;
	private JTextField txtSub;
	private JTextField txtPay;
	private JTextField txtBalance;
	private JTable table;
	
	Connection con;
	PreparedStatement pst;
	PreparedStatement pst1;
	ResultSet rs;
	DefaultTableModel model ;
	
	java.util.Date date;
	java.sql.Date sqldate;
	
	private static String usern;
	
	public void connect() {
		
		try {
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:./database/terminal","root","");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
    public void sales() {
    	
    	String subTotal = txtSub.getText();
    	String pay = txtPay.getText();
    	String balance = txtBalance.getText();
    	
    	int lastID = 0;
    	
    	try {
    		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		String query = "insert INTO prodazba(sevkupno,plati,vrati)values('"+subTotal+"','"+pay+"','"+balance+"')";
			pst = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			
			ResultSet generateKey = pst.getGeneratedKeys();
			
			if(generateKey.next()) {
				
				lastID = generateKey.getInt(1);
			}
			
		    String date = null;
			String barcodeID = "";
			String price = "";
			String quantity = "";
			int total = 0;
			
            
			for(int i =0; i< table.getRowCount();i++) {
				date = (String)table.getValueAt(i, 0);
				barcodeID = (String)table.getValueAt(i, 1);
				price = (String)table.getValueAt(i, 3);
				quantity = (String)table.getValueAt(i, 4);
				total = (int)table.getValueAt(i, 5);
			
			int rows = table.getRowCount();
			String query1 = "insert INTO prodazba_proizvodi(data,prodazba_id,barkod_id,cena,kolicina,vkupno,naplatil)values('"+date+"',"+lastID+",'"+barcodeID+"','"+price+"','"+quantity+"',"+total+",'"+usern+"')";
			pst1 = con.prepareStatement(query1);
			pst1.executeUpdate();
			pst.addBatch();
			pst1.addBatch();
			}
			JOptionPane.showMessageDialog(contentPane, "Подготвување на сметка за принтање!");
		
			
			HashMap map = new HashMap();
			
			try {
				JasperDesign design = JRXmlLoader.load("D:\\eclipse_workspace\\POS_Terminal\\Blank_A4.jrxml");
				JasperReport ireport = JasperCompileManager.compileReport(design);
				map.put("InvoiceNo",lastID);
				map.put("User",usern);
			    JasperPrint print = JasperFillManager.fillReport(ireport, map,con);
			    JasperViewer.viewReport(print,false);   
	
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Smetka frame = new Smetka(usern);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
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

	public Smetka(String user) {
		
		this.usern=user;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\eclipse_workspace\\POS_Terminal\\img\\coffee-7-24.png"));
		setTitle("Coffee house");
		connect();
		
		setBounds(100, 100, 1382, 813);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu_2 = new JMenu("Корисник");
		mnNewMenu_2.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Нов корисник");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(usern.equals("admin")) {
				Registracija r = new Registracija();
				r.setVisible(true);
			}else {
				JOptionPane.showMessageDialog(contentPane, "Немате пермисии за оваа функција!");
			}
			}
		});
		mntmNewMenuItem_2.setFont(new Font("Arial", Font.PLAIN, 12));
		mnNewMenu_2.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu = new JMenu("Производи");
		mnNewMenu.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Листа со производи");
		mntmNewMenuItem.setFont(new Font("Arial", Font.PLAIN, 12));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(usern.equals("admin")) {
				Lista lista = new Lista();
				lista.setVisible(true);
				lista.setExtendedState(JFrame.MAXIMIZED_BOTH);
				
			}else {
				
			JOptionPane.showMessageDialog(contentPane, "Немате пермисии за оваа функција!");
			}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("Продажба");
		mnNewMenu_1.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Промет во период");
		mntmNewMenuItem_1.setFont(new Font("Arial", Font.PLAIN, 12));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(usern.equals("admin")) {
				Prodazba p = new Prodazba();
				p.setVisible(true);
				p.setExtendedState(JFrame.MAXIMIZED_BOTH);
			}else {
				JOptionPane.showMessageDialog(contentPane, "Немате пермисии за оваа функција!");
			}
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_3 = new JMenu("Oдјави се");
		mnNewMenu_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			if(e.getSource()==mnNewMenu_3) {
				try {
					if(con!=null)
					con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Firma f = new Firma();
			f.setVisible(true);
			f.setExtendedState(JFrame.MAXIMIZED_BOTH);
			setVisible(false);
			}
			}
		});
		mnNewMenu_3.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnNewMenu_3);
		
		JMenuBar menuBar_1 = new JMenuBar();
		menuBar.add(menuBar_1);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 222, 179));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(210, 180, 140));
		panel.setBounds(72, 292, 1010, 122);
		panel.setForeground(Color.BLACK);
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel barcode = new JLabel("Шифра");
		barcode.setFont(new Font("Arial", Font.BOLD, 16));
		barcode.setForeground(Color.BLACK);
		barcode.setBounds(68, 26, 78, 22);
		panel.add(barcode);
		
		JLabel productName = new JLabel("\u041F\u0440\u043E\u0438\u0437\u0432\u043E\u0434");
		productName.setFont(new Font("Arial", Font.BOLD, 16));
		productName.setForeground(Color.BLACK);
		productName.setBounds(333, 26, 111, 22);
		panel.add(productName);
		
		JLabel price = new JLabel("\u0426\u0435\u043D\u0430");
		price.setFont(new Font("Arial", Font.BOLD, 16));
		price.setForeground(Color.BLACK);
		price.setBounds(623, 26, 61, 22);
		panel.add(price);
		
		JLabel quantity = new JLabel("\u041A\u043E\u043B\u0438\u0447\u0438\u043D\u0430");
		quantity.setFont(new Font("Arial", Font.BOLD, 16));
		quantity.setForeground(Color.BLACK);
		quantity.setBounds(841, 26, 104, 22);
		panel.add(quantity);
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_2.setFont(new Font("Arial", Font.BOLD, 10));
		panel_2.setLayout(null);
		panel_2.setBounds(73, 652, 160, 23);
		contentPane.add(panel_2);
		JLabel labelDate = new JLabel();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calobj = Calendar.getInstance();
		labelDate.setText(df.format(calobj.getTime()));
		labelDate.setForeground(Color.BLACK);
		labelDate.setFont(new Font("Arial", Font.BOLD, 11));
		labelDate.setBounds(97, 0, 63, 22);
		panel_2.add(labelDate);
		
		JLabel label_1 = new JLabel("Дата");
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Arial", Font.BOLD, 11));
		label_1.setBounds(10, 0, 150, 22);
		panel_2.add(label_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_3.setLayout(null);
		panel_3.setFont(new Font("Arial", Font.BOLD, 10));
		panel_3.setBounds(243, 652, 165, 23);
		contentPane.add(panel_3);
		
		JLabel lblNewLabel_2 = new JLabel("Корисник");
		lblNewLabel_2.setForeground(Color.BLACK);
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 0, 75, 23);
		panel_3.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel(usern);
		lblNewLabel_3.setForeground(Color.BLACK);
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 11));
		lblNewLabel_3.setBounds(108, 0, 52, 23);
		panel_3.add(lblNewLabel_3);
		
		txtBarCode = new JTextField();
		txtBarCode.setHorizontalAlignment(SwingConstants.CENTER);
		txtBarCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String bcode = txtBarCode.getText();
					try {
						pst = con.prepareStatement("select * FROM proizvodi WHERE barkod ='"+bcode+"'");
						rs = pst.executeQuery();
						
						
						if(rs.next() == false) {
							JOptionPane.showMessageDialog(contentPane, "Внесената шифра не постои!");
						}else {
							
							String pname = rs.getString("proizvod");
							String price = rs.getString("cena");
							
							txtProduct.setText(pname.trim());
							txtPrice.setText(price.trim());
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			}
		});
		
		
		txtBarCode.setForeground(new Color(160, 82, 45));
		txtBarCode.setBackground(Color.BLACK);
		txtBarCode.setFont(new Font("Arial", Font.BOLD, 19));
		txtBarCode.setBounds(48, 59, 122, 40);
		panel.add(txtBarCode);
		txtBarCode.setColumns(10);
		
		txtProduct = new JTextField();
		txtProduct.setHorizontalAlignment(SwingConstants.CENTER);
		txtProduct.setForeground(Color.BLACK);
		txtProduct.setBackground(new Color(230, 230, 250));
		txtProduct.setFont(new Font("Arial", Font.BOLD, 19));
		txtProduct.setColumns(10);
		txtProduct.setBounds(288, 59, 182, 40);
		panel.add(txtProduct);
		
		txtPrice = new JTextField();
		txtPrice.setHorizontalAlignment(SwingConstants.CENTER);
		txtPrice.setForeground(Color.BLACK);
		txtPrice.setBackground(new Color(230, 230, 250));
		txtPrice.setFont(new Font("Arial", Font.BOLD, 19));
		txtPrice.setColumns(10);
		txtPrice.setBounds(571, 59, 162, 40);
		panel.add(txtPrice);
		
		txtQuantity = new JTextField();
		txtQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		txtQuantity.setForeground(Color.BLACK);
		txtQuantity.setBackground(new Color(230, 230, 250));
		txtQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				String bcode = txtBarCode.getText();
				try {
					pst = con.prepareStatement("select * FROM proizvodi WHERE barkod ='"+bcode+"'");
					rs = pst.executeQuery();
					
					if(rs.next() == false) {
						JOptionPane.showMessageDialog(contentPane, "Внесената шифра не постои!");
					}else {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					int price = Integer.parseInt(txtPrice.getText());
					int quantity = Integer.parseInt(txtQuantity.getText());
					int total = price * quantity;
					model = (DefaultTableModel)table.getModel();
					model.addRow(new Object[]
					{
						labelDate.getText(),	
						txtBarCode.getText(),
						txtProduct.getText(),
						txtPrice.getText(),
						txtQuantity.getText(),
						total,
							});
					
					int sum = 0;
					
					for(int i =0; i<table.getRowCount(); i++) {
						
						sum = sum + Integer.parseInt(table.getValueAt(i, 5).toString());
					}
					
					txtSub.setText(String.valueOf(sum));
					txtBarCode.setText("");
					txtProduct.setText("");
					txtPrice.setText("");
					txtQuantity.setText("");
					txtBarCode.requestFocus();
			}
			}
				}catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		txtQuantity.setFont(new Font("Arial", Font.BOLD, 19));
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(810, 59, 152, 40);
		panel.add(txtQuantity);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(72, 436, 1010, 193);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(new Color(245, 255, 250));
		table.setForeground(Color.BLACK);
		table.setFont(new Font("Arial", Font.BOLD, 14));
		table.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u0414\u0430\u0442\u0430", "\u0428\u0438\u0444\u0440\u0430", "\u041F\u0440\u043E\u0438\u0437\u0432\u043E\u0434", "\u0426\u0435\u043D\u0430", "\u041A\u043E\u043B\u0438\u0447\u0438\u043D\u0430", "\u0412\u043A\u0443\u043F\u043D\u043E"
			}
		));
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableModel tableModel = table.getModel();

        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++)
        {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
		scrollPane.setViewportView(table);
		
		JButton invoice = new JButton("Сметка");
		invoice.setForeground(Color.BLACK);
		invoice.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\3-Gray-Printer-icon.png"));
		invoice.setFont(new Font("Arial", Font.BOLD, 12));
		invoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				sales();
			}
		});
		invoice.setBounds(1160, 459, 122, 57);
		contentPane.add(invoice);
		
		JButton btnNewButton = new JButton("Избриши");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\Button-Delete-icon.png"));
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewButton.setBounds(1160, 553, 122, 54);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBackground(new Color(240, 240, 240));
		lblNewLabel_1.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\logo 1.png"));
		lblNewLabel_1.setBounds(356, 11, 456, 260);
		contentPane.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(210, 180, 140));
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(1145, 47, 165, 357);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		txtSub = new JTextField();
		txtSub.setHorizontalAlignment(SwingConstants.CENTER);
		txtSub.setBounds(10, 72, 145, 40);
		panel_1.add(txtSub);
		txtSub.setForeground(Color.BLACK);
		txtSub.setBackground(new Color(230, 230, 250));
		txtSub.setFont(new Font("Arial", Font.BOLD, 19));
		txtSub.setColumns(10);
		
		txtBalance = new JTextField();
		txtBalance.setHorizontalAlignment(SwingConstants.CENTER);
		txtBalance.setBounds(12, 276, 143, 40);
		panel_1.add(txtBalance);
		txtBalance.setForeground(Color.BLACK);
		txtBalance.setBackground(new Color(230, 230, 250));
		txtBalance.setFont(new Font("Arial", Font.BOLD, 19));
		txtBalance.setColumns(10);
		
		JLabel balance = new JLabel("Врати");
		balance.setBounds(45, 241, 72, 24);
		panel_1.add(balance);
		balance.setForeground(Color.BLACK);
		balance.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel payment = new JLabel("Прими");
		payment.setBounds(40, 134, 115, 24);
		panel_1.add(payment);
		payment.setForeground(Color.BLACK);
		payment.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel lblNewLabel = new JLabel("Наплати");
		lblNewLabel.setBounds(39, 30, 101, 31);
		panel_1.add(lblNewLabel);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		txtPay = new JTextField();
		txtPay.setHorizontalAlignment(SwingConstants.CENTER);
		txtPay.setBounds(10, 181, 145, 40);
		panel_1.add(txtPay);
		txtPay.setForeground(Color.BLACK);
		txtPay.setBackground(new Color(230, 230, 250));
		txtPay.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				int pay = Integer.parseInt(txtPay.getText());
				int sub = Integer.parseInt(txtSub.getText());
				int balance = pay - sub;
				
				txtBalance.setText(String.valueOf(balance));
			}
			}
		});
		txtPay.setFont(new Font("Arial", Font.BOLD, 19));
		txtPay.setColumns(10);
		
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow()>=0){
				model.removeRow(table.getSelectedRow());
				
				int sum = 0;
				
				for(int i =0; i<table.getRowCount(); i++) {
					
					sum = sum + Integer.parseInt(table.getValueAt(i, 4).toString());
				}
				
				txtSub.setText(String.valueOf(sum));
				txtBarCode.setText("");
				txtProduct.setText("");
				txtPrice.setText("");
				txtQuantity.setText("");
				txtPay.setText("");
				txtBalance.setText("");
				txtBarCode.requestFocus();
				
			}else {
				txtBarCode.setText("");
				txtProduct.setText("");
				txtPrice.setText("");
				txtQuantity.setText("");
			}
			}
		});
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
