package coffeeshop;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;

import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Cursor;

public class Lista extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton jbutton3;
	private JTextField txtProduct;
	private JTextField txtPrice;
	private JTextField txtQuantity;
    
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	DefaultTableModel d;
	
	public void refresh() {
		
    	try {
    		Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:./database/terminal","root","");
				pst = con.prepareStatement("select * FROM proizvodi ");
				rs = pst.executeQuery();
				DecimalFormat df = new DecimalFormat("000");
				DefaultTableModel d = (DefaultTableModel)table.getModel();
				ResultSetMetaData rsd = rs.getMetaData();
		        int c;
		        c=rsd.getColumnCount();
		        
		        d.setRowCount(0);
		        while(rs.next()) {
		        	
		        Vector v2 = new Vector();
		        for(int i =1; i<= c; i++) {
			v2.add(df.format(rs.getInt("barkod")));
        	v2.add(rs.getString("proizvod"));
        	v2.add(rs.getString("cena"));
        	v2.add(rs.getString("kolicina"));
		}
		        d.addRow(v2);
		        }
    	}catch (SQLException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lista frame = new Lista();
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
	public Lista() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\eclipse_workspace\\POS_Terminal\\img\\coffee-7-24.png"));
		setTitle("Листа");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1385, 788);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 222, 179));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.LIGHT_GRAY);
		scrollPane.setBounds(136, 227, 961, 325);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(new Color(245, 255, 250));
		table.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		table.setShowGrid(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u0428\u0438\u0444\u0440\u0430", "\u041F\u0440\u043E\u0438\u0437\u0432\u043E\u0434", "\u0426\u0435\u043D\u0430", "\u041A\u043E\u043B\u0438\u0447\u0438\u043D\u0430"
			}
		));
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableModel tableModel = table.getModel();

        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++)
        {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
		table.setForeground(Color.BLACK);
		table.setFont(new Font("Arial", Font.BOLD, 14));
		table.setBorder(null);
		scrollPane.setViewportView(table);
		
		jbutton3 = new JButton("Ажурирај");
		jbutton3.setForeground(Color.BLACK);
		jbutton3.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\Actions-view-refresh-icon.png"));
		jbutton3.setFont(new Font("Arial", Font.BOLD, 11));
		jbutton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refresh();
			        }
		});
		jbutton3.setBounds(479, 577, 124, 34);
		contentPane.add(jbutton3);
		
		JButton addButton = new JButton("Додај");
		addButton.setForeground(Color.BLACK);
		addButton.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\Files-New-File-icon.png"));
		addButton.setFont(new Font("Arial", Font.BOLD, 11));
		addButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			String product = txtProduct.getText();
			int quantity = Integer.parseInt(txtQuantity.getText());
			int price = Integer.parseInt(txtPrice.getText());
			
			String query2 = "insert INTO proizvodi(proizvod,kolicina,cena)values('"+product+"',"+quantity+","+price+")";
			try {
				Class.forName("org.h2.Driver");
				con = DriverManager.getConnection("jdbc:h2:./database/terminal","root","");
				pst= con.prepareStatement(query2);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(contentPane, "Додаден е нов производ!");
				refresh();
				if(product.isEmpty() || quantity == 0 || price == 0) {
					JOptionPane.showMessageDialog(contentPane, "Пополнете ги сите полиња!");
					
				}
			} catch (SQLException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			
		}
	});

		addButton.setBounds(989, 136, 105, 34);
		contentPane.add(addButton);
		
		JButton deleteButton = new JButton("Избриши");
		deleteButton.setForeground(Color.BLACK);
		deleteButton.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\Button-Delete-icon.png"));
		deleteButton.setFont(new Font("Arial", Font.BOLD, 11));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 DefaultTableModel model = (DefaultTableModel)table.getModel();
				int selected = table.getSelectedRow();
				String barCode = model.getValueAt(selected,0).toString();
				
				try {
					if(table.getSelectedRow()>=0) {
					String query = "delete FROM proizvodi WHERE barkod='"+barCode+"'";
					pst = con.prepareStatement(query);
					pst.executeUpdate();
					txtProduct.setText("");
					txtPrice.setText("");
					txtQuantity.setText("");
					}
					txtProduct.setText("");
					txtPrice.setText("");
					txtQuantity.setText("");
						
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				refresh();
			}
		});
		deleteButton.setBounds(802, 577, 124, 34);
		contentPane.add(deleteButton);
		
		JButton exitButton = new JButton("Откажи");
		exitButton.setForeground(Color.BLACK);
		exitButton.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\Close-2-icon.png"));
		exitButton.setFont(new Font("Arial", Font.BOLD, 11));
		exitButton.addActionListener(new ActionListener() {
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
		exitButton.setBounds(969, 577, 105, 34);
		contentPane.add(exitButton);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(136, 127, 832, 56);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("Производ");
		label.setBounds(51, 16, 81, 14);
		panel.add(label);
		label.setFont(new Font("Arial", Font.BOLD, 14));
		label.setForeground(new Color(0, 0, 0));
		
		txtProduct = new JTextField();
		txtProduct.setFont(new Font("Arial", Font.BOLD, 14));
		txtProduct.setBackground(new Color(240, 255, 255));
		txtProduct.setBounds(155, 12, 86, 23);
		panel.add(txtProduct);
		txtProduct.setColumns(10);
		
		JLabel label_1 = new JLabel("Цена");
		label_1.setBounds(324, 17, 46, 14);
		panel.add(label_1);
		label_1.setFont(new Font("Arial", Font.BOLD, 14));
		label_1.setForeground(new Color(0, 0, 0));
		
		txtPrice = new JTextField();
		txtPrice.setFont(new Font("Arial", Font.BOLD, 14));
		txtPrice.setBackground(new Color(240, 255, 255));
		txtPrice.setBounds(385, 12, 98, 23);
		panel.add(txtPrice);
		txtPrice.setColumns(10);
		
		JLabel label_2 = new JLabel("Количина");
		label_2.setBounds(565, 17, 77, 14);
		panel.add(label_2);
		label_2.setFont(new Font("Arial", Font.BOLD, 14));
		label_2.setForeground(new Color(0, 0, 0));
		
		txtQuantity = new JTextField();
		txtQuantity.setFont(new Font("Arial", Font.BOLD, 14));
		txtQuantity.setBackground(new Color(240, 255, 255));
		txtQuantity.setBounds(663, 12, 98, 23);
		panel.add(txtQuantity);
		txtQuantity.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(160, 82, 45));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(0, 0, 1369, 90);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Листа со производи");
		lblNewLabel.setBounds(508, 32, 282, 24);
		panel_1.add(lblNewLabel);
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 26));
		
		JButton btnNewButton = new JButton("Сними");
		btnNewButton.setIcon(new ImageIcon("D:\\eclipse_workspace\\POS_Terminal\\img\\Devices-media-floppy-icon.png"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    int dialogButton = JOptionPane.showConfirmDialog(null, "Дали сакате да ја зачувате измената?", "WARNING",
				        JOptionPane.YES_NO_OPTION);
			    
				if(dialogButton == JOptionPane.YES_OPTION) {
				DefaultTableModel d1 = (DefaultTableModel)table.getModel();
				int selected = table.getSelectedRow();
				String code= d1.getValueAt(selected, 0).toString();
				String product = d1.getValueAt(selected, 1).toString();
				String price = d1.getValueAt(selected, 2).toString();
				String quantity= d1.getValueAt(selected, 3).toString();
				
				String query = "UPDATE proizvodi SET proizvod='"+product+"',barkod='"+code+"',kolicina="+Integer.parseInt(quantity)+",cena="+Integer.parseInt(price) +"WHERE barkod='"+code+"'";
				try {
					pst = con.prepareStatement(query);
					pst.executeUpdate();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}else {
					refresh();
			
			}
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 11));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBounds(641, 577, 117, 34);
		contentPane.add(btnNewButton);
	}
}
