package VeroPackage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JRadioButtonMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class VeroTsekkaus extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private static JFrame frame2;
	private static DefaultTableModel model;
	private JTextField txtLiike;
	private JTextField txtHinta;
	private JTextField txtPVM;
	private JTextField txtALVPros;
	private JTextField txtMyyntiVero;
	private JTextField txtTilitys;
    public Connection conn;
	private String mTable;
	private Matkakulut mFrame;
	
	
	static boolean lock()
	 {
	   try
	    {
	        final File file=new File("bpmdj.lock");
	        if (file.createNewFile())
	        {
	            file.deleteOnExit();
	            return true;
	        }
	        return false;
	    }
	    catch (IOException e)
	    {
	        return false;
	    }
	}

	
	  static void infoBox(String infoMessage, String titleBar)
	    {
		  JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	    }
	

	/**
	 * Launch the application.
	*/ 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (!lock()) {
						infoBox("Appi käynnissä", "Sovelluksen tila");
						System.exit(0);
					}
					lock();
					VeroTsekkaus frame = new VeroTsekkaus();
					frame.setVisible(true);
					frame2 = frame;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void NaytaLomake(String mTable, Connection conn) throws SQLException {		
		//model.setRowCount(0);
		ReadRecords(mTable, conn);					
		frame2.setVisible(true);
	}
	
	
	public VeroTsekkaus() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException {
		model = new DefaultTableModel();
		mFrame = null;
		ConnectionDB c = new ConnectionDB();
		Connection conn = c.Initialize();		
		String mTable = c.GetTable();
		
		setTitle("Verotus - Jouni Riimala (151269-075A)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 994, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 963, 516);
		contentPane.add(panel);
		panel.setLayout(null);
				
		JTextArea txtAreaHankinta = new JTextArea();
		txtAreaHankinta.setRows(5);
		txtAreaHankinta.setLineWrap(true);
		txtAreaHankinta.setWrapStyleWord(true);
		txtAreaHankinta.setBounds(32, 138, 187, 100);
		panel.add(txtAreaHankinta);
		
		
		
		JComboBox cBLaji = new JComboBox();
		cBLaji.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean hasWindow = false;
				int ind = cBLaji.getSelectedIndex();
				
				if (ind == 3) {
					try {
						frame2.dispose();	
																
						if (mFrame != null ) {
							System.out.println("mFrame != null");
							mFrame.toFront();
						}
						else {
							System.out.println("mFrame == null");
							mFrame = new Matkakulut();
							mFrame.setVisible(true);
							hasWindow = true;
						}
						
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchMethodException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				}
			}
		});
		cBLaji.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent event) {

			}
			public void inputMethodTextChanged(InputMethodEvent event) {
			
			}
		});
		cBLaji.setModel(new DefaultComboBoxModel(new String[] {"Kotitalousv\u00E4hennys", "Mets\u00E4talous", "Puukauppa", "Matkakulut"}));
		cBLaji.setBounds(102, 79, 117, 22);
		panel.add(cBLaji);
				
		JCheckBox omaCheckBox = new JCheckBox("Ilmoitettu omaveroon");
		omaCheckBox.setBounds(29, 440, 173, 23);
		panel.add(omaCheckBox);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(true);
				
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(253, 50, 687, 148);
		panel.add(scrollPane);				
			
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					UpdateFields(txtAreaHankinta, omaCheckBox, cBLaji);
			}
		});
		
		DefaultTableModel model = new DefaultTableModel();
		//String[] columnNames = new String[] {"Column Header1", "Column Header2", "Column Header3"};
		String[] columnNames = new String[] { "ID", "PVM", "Laji", "Hankinta", "Liike", "Hinta", "ALV", "MyyntiVero", "Tilitys", "Oma" };
		model.setColumnIdentifiers(columnNames);
		
		//scrollPane.setColumnHeaderView(table);
		
		
		ReadRecords(mTable, conn);
		
		JButton btnSulje = new JButton("Sulje");
		btnSulje.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			System.exit(ABORT);
			}
		});
		btnSulje.setBounds(851, 470, 89, 23);
		panel.add(btnSulje);
		
		JButton btnDel = new JButton("Poista");
		btnDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Records r = new Records();
				//String s = JOptionPane.showInputDialog("Poistettava rivi?");
				
				int row = Integer.parseInt(table.getValueAt(table.getSelectedRow(),  0).toString());
						
				System.out.println("Poistettava rivi " + row);
				if (row > 0) {
					r.DeleteRecords(mTable, (Connection) conn, row );					
					try {
					Verotus v;					
						v = new Verotus();
						v.infoBox("Recordi deletetoitu", "Tuhoamis Operaatio");
						TyhjennaTxtKentat(txtAreaHankinta);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchMethodException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}		
				
				try {
					model.setRowCount(0);
					ReadRecords(mTable, conn);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});
		btnDel.setBounds(229, 470, 89, 23);
		panel.add(btnDel);
		
		JButton btnUusi = new JButton("Uusi");
		btnUusi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Records r = new Records();
					Verotus v = new Verotus();
					frame2.dispose();
					v.NaytaLomake();					
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUusi.setBounds(32, 470, 89, 23);
		panel.add(btnUusi);
		
		txtLiike = new JTextField();
		txtLiike.setBounds(32, 271, 187, 20);
		panel.add(txtLiike);
		txtLiike.setColumns(10);
		
		txtHinta = new JTextField();
		txtHinta.setBounds(144, 315, 75, 20);
		panel.add(txtHinta);
		txtHinta.setColumns(10);
		
		
		
		txtPVM = new JTextField();
		txtPVM.setBounds(102, 48, 118, 20);
		panel.add(txtPVM);
		txtPVM.setColumns(10);
		
		JButton btnPaivita = new JButton("P\u00E4ivit\u00E4");
		btnPaivita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPaivita.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Records r = new Records();
				
				//luetaan tekstikenttiin
				String pvm  = txtPVM.getText();
				int id = Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
				String hankinta = txtAreaHankinta.getText();
				String liike = txtLiike.getText();
				String strHinta  = txtHinta.getText();
				double hinta = Double.parseDouble(strHinta);
				
				String oma = "";
												
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date date;
				try {
					date = sdf.parse(pvm);
					java.sql.Date dt = new java.sql.Date(date.getTime());
					System.out.println("Päivä: " + dt);
					try {
						
						if (omaCheckBox.isEnabled()) { oma = "Kyllä"; } else { oma = "Ei"; };
						r.UpdateRecords(mTable, (Connection) conn, id, hankinta, liike, hinta, dt, oma);
						Verotus v = new Verotus();
						v.infoBox("Recordi " + id + " päivitetty", "Päivitys Operaatio");
						model.setRowCount(0);
						ReadRecords(mTable, conn);
						//conn.close();
					} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException | ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
								
			}
		});
		btnPaivita.setBounds(130, 470, 89, 23);
		panel.add(btnPaivita);
		
		JButton btnTyhjaa = new JButton("Tyhjenn\u00E4");
		btnTyhjaa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTyhjaa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TyhjennaTxtKentat(txtAreaHankinta);
			}
		});
		btnTyhjaa.setBounds(328, 470, 89, 23);
		panel.add(btnTyhjaa);
		
		JButton btnVirkista = new JButton("Virkist\u00E4");
		btnVirkista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ReadRecords(mTable, conn);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnVirkista.setBounds(851, 11, 89, 23);
		panel.add(btnVirkista);
		
		JLabel lblNewLabel = new JLabel("PVM");
		lblNewLabel.setBounds(32, 51, 46, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Liike");
		lblNewLabel_1.setBounds(32, 249, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Hinta");
		lblNewLabel_2.setBounds(32, 318, 89, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Hankinta / kulu");
		lblNewLabel_3.setBounds(32, 119, 109, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("ALV %");
		lblNewLabel_4.setBounds(32, 355, 32, 14);
		panel.add(lblNewLabel_4);
		
		txtALVPros = new JTextField();
		txtALVPros.setBounds(181, 346, 38, 20);
		panel.add(txtALVPros);
		txtALVPros.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("V\u00E4hennettev\u00E4");
		lblNewLabel_5.setToolTipText("Met\u00E4tuotannon Alkutuotantoon Liittyv\u00E4\u00E4");
		lblNewLabel_5.setBounds(32, 380, 75, 14);
		panel.add(lblNewLabel_5);
		
		txtMyyntiVero = new JTextField();
		txtMyyntiVero.setBounds(133, 380, 86, 20);
		panel.add(txtMyyntiVero);
		txtMyyntiVero.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Laji");
		lblNewLabel_6.setBounds(32, 83, 46, 14);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Tilitys");
		lblNewLabel_7.setBounds(32, 418, 46, 14);
		panel.add(lblNewLabel_7);
		
		txtTilitys = new JTextField();
		txtTilitys.setBounds(133, 415, 86, 20);
		panel.add(txtTilitys);
		txtTilitys.setColumns(10);
				
	}
	
	
	public void UpdateFields(JTextArea txtAreaHankinta, JCheckBox omaCheckBox, JComboBox cBLaji){

		String pvm  = table.getValueAt(table.getSelectedRow(), 1).toString();

		String laji = table.getValueAt(table.getSelectedRow(), 2).toString();
	
		
		String hankinta = table.getValueAt(table.getSelectedRow(), 3).toString();
		String liike = table.getValueAt(table.getSelectedRow(), 4).toString();		
		String strHinta  = table.getValueAt(table.getSelectedRow(), 5).toString();
		double hinta = Double.parseDouble(strHinta);
		String alvStr =  table.getValueAt(table.getSelectedRow(),  6).toString();
		double alv = Double.parseDouble(alvStr);
		double myyntiVero = (double) table.getValueAt(table.getSelectedRow(),7);
		double tilitys = (double) table.getValueAt(table.getSelectedRow(),8);
		String oma = table.getValueAt(table.getSelectedRow(), 9).toString();
		
		if (oma.equals("Ei")) {
			omaCheckBox.setEnabled(false);
		}
		else {
			omaCheckBox.setEnabled(true);
		}

		
		//Päivitä kentät
		txtPVM.setText(pvm);
		if (laji.equals("Metsätalous")){
			cBLaji.setSelectedIndex(1);			
		} 
		else if (laji.equals("Kotitalousvähennys")){
			cBLaji.setSelectedIndex(0);
		}
		else if (laji.equals("Puukauppa")) {
			cBLaji.setSelectedIndex(2);
		}
		txtAreaHankinta.setText(hankinta);
		txtLiike.setText(liike);
		txtHinta.setText("" + hinta);
		txtALVPros.setText("" + alv);
		txtMyyntiVero.setText("" + myyntiVero);
		txtTilitys.setText("" + tilitys);
	}

	
	private void TyhjennaTxtKentat(JTextArea txtAreaHankinta) {
		txtAreaHankinta.setText("");
		txtHinta.setText("");
		txtLiike.setText("");
		txtPVM.setText("");		
	}
	
	public void setTableLayout() {
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(600);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);

	}

	

	
	public void ReadRecords(String mTable, Connection conn) throws SQLException {
		DefaultTableModel model = new DefaultTableModel();
		//String[] columnNames = new String[] {"Column Header1", "Column Header2", "Column Header3"};
		String[] columnNames = new String[] { "ID", "PVM", "Laji", "Hankinta", "Liike", "Hinta", "ALV", "MyyntiVero", "Tilitys", "Oma" };
		model.setColumnIdentifiers(columnNames);
		model.setRowCount(0);
		
		String query = "SELECT * FROM " + mTable;
		Statement st = ((Connection) conn).createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()) {
			int id = rs.getInt("ID");
			String hPaiva = rs.getString("PVM");
			String laji = rs.getString("Laji");			
			String hankinta = rs.getString("Hankinta");
			String liike = rs.getString("Liike");
			double hinta = rs.getDouble("Hinta");
			double alv = rs.getDouble("ALV");
			double myyntiVero = rs.getDouble("MyyntiVero"); 
			double tilitys = rs.getDouble("Tilitys"); 
			String omaVero = rs.getString("Oma");			
			Object o [] = {id, hPaiva, laji, hankinta, liike, hinta, alv, myyntiVero, tilitys, omaVero};
			model.addRow(o);			
		}
		
		table.setModel(model);
		//setTableLayout();
		//table.setFillsViewportHeight(true);
	}
}
