package coffeeshop;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ibm.icu.util.Calendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.SystemColor;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;



public class Prodazba extends JFrame {

	private JPanel contentPane;
	Connection con;
	PreparedStatement pst;
	
	ResultSet rs;
	private JTable table;
	private JTextField txtSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prodazba frame = new Prodazba();
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
	public void table() {
		try {
			DecimalFormat decimal = new DecimalFormat("000");
			SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

        DefaultTableModel d = (DefaultTableModel)table.getModel();	
        ResultSetMetaData rsd;
		
			rsd = rs.getMetaData();
		
        int c;
        c=rsd.getColumnCount();
        d.setRowCount(0);
        while(rs.next()) {

        	Vector v2 = new Vector();
        for(int i =1; i<= c; i++) {
        	
        	v2.add(df.format(rs.getDate("data")));
        	v2.add(decimal.format((int)rs.getInt("barkod")));
        	v2.add(rs.getString("proizvod"));
        	v2.add(rs.getString("kolicina"));
        	v2.add(rs.getString("vkupno")+" ден");
        	v2.add(rs.getString("naplatil"));
        }
        d.addRow(v2);
        }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Prodazba() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\eclipse_workspace\\POS_Terminal\\img\\coffee-7-24.png"));
		
			try {
				Class.forName("org.h2.Driver");
				con = DriverManager.getConnection("jdbc:h2:./database/terminal","root","");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		setTitle("Продажба");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1409, 697);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 222, 179));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(99, 270, 1168, 293);
		contentPane.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(99, 150, 1168, 94);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Од");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(53, 28, 46, 31);
		panel_1.add(lblNewLabel);
		lblNewLabel.setForeground(Color.BLACK);
		java.util.Date date = new java.util.Date();
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(100, 28, 121, 31);
		panel_1.add(dateChooser);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);
		dateChooser.setDate(c.getTime());
		
		table = new JTable();
		table.setBackground(new Color(245, 255, 250));
		table.setShowGrid(false);
		table.setGridColor(Color.WHITE);
		table.setForeground(new Color(0, 0, 0));
		table.setFont(new Font("Arial", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u0414\u0430\u0442\u0430", "\u0428\u0438\u0444\u0440\u0430", "\u041F\u0440\u043E\u0438\u0437\u0432\u043E\u0434", "\u041A\u043E\u043B\u0438\u0447\u0438\u043D\u0430", "\u0412\u043A\u0443\u043F\u043D\u043E", "\u041D\u0430\u043F\u043B\u0430\u0442\u0438\u043B"
			}
		));
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableModel tableModel = table.getModel();

        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++)
        {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(new Color(160, 82, 45));
		panel.setBounds(0, 0, 1370, 88);
		contentPane.add(panel);
		
		JLabel label = new JLabel("Промет во период");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Arial", Font.BOLD, 26));
		label.setBackground(Color.WHITE);
		label.setBounds(587, 21, 264, 44);
		panel.add(label);
		
		
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(343, 28, 121, 31);
		panel_1.add(dateChooser_1);
		dateChooser_1.setDate(date);
		
		JLabel label_1 = new JLabel("До");
		label_1.setFont(new Font("Arial", Font.BOLD, 14));
		label_1.setForeground(Color.BLACK);
		label_1.setBounds(300, 29, 46, 30);
		panel_1.add(label_1);
		
		JCheckBox checkBox_2 = new JCheckBox("Количина");
		checkBox_2.setForeground(Color.BLACK);
		checkBox_2.setFont(new Font("Arial", Font.BOLD, 14));
		checkBox_2.setBackground(Color.LIGHT_GRAY);
		checkBox_2.setBounds(889, 28, 121, 31);
		panel_1.add(checkBox_2);
		
		JCheckBox checkBox_3 = new JCheckBox("Вкупно");

		checkBox_3.setForeground(Color.BLACK);
		checkBox_3.setFont(new Font("Arial", Font.BOLD, 14));
		checkBox_3.setBackground(Color.LIGHT_GRAY);
		checkBox_3.setBounds(1024, 28, 121, 31);
		panel_1.add(checkBox_3);
		
		JCheckBox checkBox_1 = new JCheckBox("Шифра");
		checkBox_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		checkBox_1.setForeground(Color.BLACK);
		checkBox_1.setFont(new Font("Arial", Font.BOLD, 14));
		checkBox_1.setBackground(Color.LIGHT_GRAY);
		checkBox_1.setBounds(753, 28, 121, 31);
		panel_1.add(checkBox_1);
		
		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				
				try {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						java.util.Date date1 = dateChooser.getDate();
						java.util.Date date2 = dateChooser_1.getDate();
						String barCode = txtSearch.getText();
                        
			       pst = con.prepareStatement("SELECT prodazba_proizvodi.naplatil,SUM(prodazba_proizvodi.kolicina),SUM(prodazba_proizvodi.vkupno), min(prodazba_proizvodi.data),max(prodazba_proizvodi.data),proizvodi.barkod,proizvodi.proizvod FROM prodazba_proizvodi LEFT JOIN proizvodi ON  prodazba_proizvodi.barkod_id=proizvodi.barkod WHERE  prodazba_proizvodi.data BETWEEN ? AND ? AND proizvodi.barkod='"+barCode+"' GROUP BY proizvodi.barkod,prodazba_proizvodi.naplatil" );
			       pst.setDate(1, new java.sql.Date(date1.getTime()));
		           pst.setDate(2, new java.sql.Date(date2.getTime()));
		           rs = pst.executeQuery();
		           DecimalFormat decimal = new DecimalFormat("000");
		           SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
		           DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

			        DefaultTableModel d = (DefaultTableModel)table.getModel();	
			        ResultSetMetaData rsd = rs.getMetaData();
			        int c;
			        c=rsd.getColumnCount();
			        d.setRowCount(0);
			        
			        while(rs.next()) {
			        	Vector v2 = new Vector();
			        for(int i =1; i<= c; i++) {
			        	
			        	v2.add(df.format(date1)+" / "+df.format(date2));
			        	v2.add(decimal.format((int)rs.getInt("barkod")));
			        	v2.add(rs.getString("proizvod"));
			        	v2.add(rs.getString("SUM(prodazba_proizvodi.kolicina)"));
			        	v2.add(rs.getString("SUM(prodazba_proizvodi.vkupno)")+" ден");
			        	v2.add(rs.getString("naplatil"));
			        }
			        d.addRow(v2);
			        }
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		txtSearch.setBackground(Color.BLACK);
		txtSearch.setForeground(new Color(160, 82, 45));
		txtSearch.setFont(new Font("Arial", Font.BOLD, 16));
		txtSearch.setHorizontalAlignment(SwingConstants.CENTER);
		txtSearch.setBounds(604, 29, 132, 31);
		panel_1.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Внеси шифра");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1.setBounds(490, 37, 104, 14);
		panel_1.add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("Откажи");
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\Close-2-icon.png"));
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
					try {
						if(con!=null)
						con.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		btnNewButton_1.setBounds(1141, 592, 109, 42);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Приказ");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\Look-icon.png"));
		btnNewButton.setBounds(969, 593, 109, 40);
		contentPane.add(btnNewButton);
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 12));
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {

					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date1 = dateChooser.getDate();
					java.util.Date date2 = dateChooser_1.getDate();
					   
				       pst = con.prepareStatement("SELECT prodazba_proizvodi.naplatil,prodazba_proizvodi.kolicina,prodazba_proizvodi.vkupno, prodazba_proizvodi.data,proizvodi.barkod,proizvodi.proizvod FROM prodazba_proizvodi LEFT JOIN proizvodi ON  prodazba_proizvodi.barkod_id=proizvodi.barkod WHERE  prodazba_proizvodi.data  BETWEEN ? AND ?" );
				       pst.setDate(1, new java.sql.Date(date1.getTime()));
			           pst.setDate(2, new java.sql.Date(date2.getTime()));
			            rs = pst.executeQuery();
			    	table();
					// TODO Auto-generated catch block
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
			}
		});
		checkBox_1.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				try {

					java.util.Date date1 = dateChooser.getDate();
					java.util.Date date2 = dateChooser_1.getDate();
					pst = con.prepareStatement("SELECT prodazba_proizvodi.naplatil,prodazba_proizvodi.data,proizvodi.barkod,proizvodi.proizvod,prodazba_proizvodi.kolicina,prodazba_proizvodi.vkupno  from prodazba_proizvodi LEFT JOIN proizvodi ON prodazba_proizvodi.barkod_id=proizvodi.barkod  WHERE prodazba_proizvodi.data BETWEEN ? AND ? ORDER BY proizvodi.barkod ASC" );
				    pst.setDate(1, new java.sql.Date(date1.getTime()));
			        pst.setDate(2, new java.sql.Date(date2.getTime()));
					rs = pst.executeQuery();
					table();
					if(checkBox_1.isSelected()) {
						checkBox_2.setEnabled(false);
						checkBox_3.setEnabled(false);
					}else {
						checkBox_2.setEnabled(true);
						checkBox_3.setEnabled(true);
						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		checkBox_2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			try{

			java.util.Date date1 = dateChooser.getDate();
			java.util.Date date2 = dateChooser_1.getDate();
			String barCode = txtSearch.getText();
			   
	            
				pst = con.prepareStatement("SELECT prodazba_proizvodi.naplatil,prodazba_proizvodi.data,proizvodi.barkod,proizvodi.proizvod,prodazba_proizvodi.kolicina,prodazba_proizvodi.vkupno  from prodazba_proizvodi LEFT JOIN proizvodi ON prodazba_proizvodi.barkod_id=proizvodi.barkod  WHERE prodazba_proizvodi.data BETWEEN ? AND ? ORDER BY prodazba_proizvodi.kolicina DESC" );	
			    pst.setDate(1, new java.sql.Date(date1.getTime()));
		        pst.setDate(2, new java.sql.Date(date2.getTime()));
			    rs = pst.executeQuery();
		            
		    		table();
		    		
		    		if(checkBox_2.isSelected()) {
						checkBox_1.setEnabled(false);
						checkBox_3.setEnabled(false);
					}else {
						checkBox_1.setEnabled(true);
						checkBox_3.setEnabled(true);
					}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}

	});
		checkBox_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				try {

				java.util.Date date1 = dateChooser.getDate();
				java.util.Date date2 = dateChooser_1.getDate();
			   pst = con.prepareStatement("SELECT prodazba_proizvodi.naplatil,prodazba_proizvodi.data,proizvodi.barkod,proizvodi.proizvod,prodazba_proizvodi.kolicina,prodazba_proizvodi.vkupno  from prodazba_proizvodi LEFT JOIN proizvodi ON prodazba_proizvodi.barkod_id=proizvodi.barkod  WHERE prodazba_proizvodi.data BETWEEN ? AND ? ORDER BY prodazba_proizvodi.vkupno DESC" );
		       pst.setDate(1, new java.sql.Date(date1.getTime()));
	           pst.setDate(2, new java.sql.Date(date2.getTime()));;
				rs = pst.executeQuery();
	            
				table();
				
				if(checkBox_3.isSelected()) {
					checkBox_1.setEnabled(false);
					checkBox_2.setEnabled(false);
			}else {
				checkBox_1.setEnabled(true);
				checkBox_2.setEnabled(true);
			}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		});
	}
}

