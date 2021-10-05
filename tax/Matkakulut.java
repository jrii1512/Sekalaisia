package VeroPackage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Dialog.ModalExclusionType;

public class Matkakulut extends JFrame {

	private JPanel contentPane;
	private JTextField txtMatka;
	private JTextField txtKM;
	private JTextField textField;
	private JTextField txtVahennys;
	private JTextField txtSyy;
	private JTextField txtPVMAlku;
	private JTextField txtPVMLoppu;
	private JFrame frame;
	private JFrame frame2;
	private JTable table;
	private Connection conn;
	/**
	 * Launch the application.
	*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Matkakulut frame = new Matkakulut();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws InvocationTargetException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	
	
	
	  static void infoBox(String infoMessage, String titleBar)
	    {
		  JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	    }
	
	public Matkakulut() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException, SQLException {
		
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);		
		//setVisible(true);
	
		if ( conn == null ) {
			ConnectionDB c = new ConnectionDB();
			this.conn = c.Initialize();
			String mTable = "matkakulut";
		}
		
		setTitle("Matkav\u00E4hennys");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 987, 691);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Matka");
		lblNewLabel_1.setBounds(25, 16, 57, 20);
		contentPane.add(lblNewLabel_1);
		
		txtMatka = new JTextField();
		txtMatka.setBounds(107, 16, 301, 20);
		contentPane.add(txtMatka);
		txtMatka.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Pituus");
		lblNewLabel.setBounds(25, 144, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtKM = new JTextField();
		txtKM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtKM.setBounds(107, 141, 86, 20);
		contentPane.add(txtKM);
		txtKM.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("KM-korvaus");
		lblNewLabel_2.setBounds(25, 171, 71, 14);
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setText("0.44");
		textField.setBounds(107, 168, 57, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("V\u00E4hennys");
		lblNewLabel_3.setBounds(25, 196, 71, 14);
		contentPane.add(lblNewLabel_3);
		
		txtVahennys = new JTextField();
		txtVahennys.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String strKM = txtKM.getText();
				int km = Integer.parseInt(strKM);
				txtVahennys.setText("" + km * 0.44);
			}
		});
		txtVahennys.setBounds(107, 193, 86, 20);
		contentPane.add(txtVahennys);
		txtVahennys.setColumns(10);
		
		JCheckBox cBMatka = new JCheckBox("V\u00E4hennys kirjattu?");
		cBMatka.setBounds(108, 220, 139, 23);
		contentPane.add(cBMatka);
		
		JButton btnNewButton = new JButton("Kantaan");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				try {
				
					Records r = new Records();
					
					Date aDate = sdf.parse(txtPVMAlku.getText());
					Date eDate = sdf.parse(txtPVMLoppu.getText());
					String matka = txtMatka.getText();
					String syy = txtSyy.getText();
					String strKM = txtKM.getText();
					String kirjattu = "Ei";
					int km = Integer.parseInt(strKM);
					
					if (cBMatka.isSelected()) {
						kirjattu = "Kyllä";
					}
					
					String strVahennys = txtVahennys.getText();
					double vahennys = Double.parseDouble(strVahennys);

					java.sql.Date a = new java.sql.Date(aDate.getTime());
					java.sql.Date et = new java.sql.Date(eDate.getTime());
					
					r.CreateTravel("matkakulut", conn, a, et, matka, syy, km, vahennys, kirjattu);
					ReadTravel("matkakulut", conn);
					
				} catch (Exception e3) {
					e3.printStackTrace();
				}
				
				
				
				
			}
		});
		btnNewButton.setBounds(10, 250, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_4 = new JLabel("Syy");
		lblNewLabel_4.setBounds(25, 119, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		txtSyy = new JTextField();
		txtSyy.setBounds(107, 110, 301, 20);
		contentPane.add(txtSyy);
		txtSyy.setColumns(10);
		
		JLabel label = new JLabel("New label");
		label.setBounds(24, 47, 9, -22);
		contentPane.add(label);
		
		JLabel lblNewLabel_5 = new JLabel("Alkaa");
		lblNewLabel_5.setBounds(25, 47, 46, 14);
		contentPane.add(lblNewLabel_5);
		
		txtPVMAlku = new JTextField();
		txtPVMAlku.setText("26-05-2021");
		txtPVMAlku.setBounds(107, 47, 86, 20);
		contentPane.add(txtPVMAlku);
		txtPVMAlku.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Loppu");
		lblNewLabel_6.setBounds(25, 79, 46, 14);
		contentPane.add(lblNewLabel_6);
		
		txtPVMLoppu = new JTextField();
		txtPVMLoppu.setText("30-05-2021");
		txtPVMLoppu.setBounds(107, 76, 86, 20);
		contentPane.add(txtPVMLoppu);
		txtPVMLoppu.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 316, 735, 275);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		JButton btnPoista = new JButton("Poista");
		btnPoista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//String s = JOptionPane.showInputDialog("Poistettava rivi?");
				Records r = new Records();
				int row = Integer.parseInt(table.getValueAt(table.getSelectedRow(),  0).toString());
						
				System.out.println("Poistettava rivi " + row);
				if (row > 0) {
					try {						
						r.DeleteRecords("matkakulut", conn, row );										
						infoBox("Recordi deletetoitu", "Tuhoamis Operaatio");
						ReadTravel("matkakulut", conn);
					} catch (Exception e1) {
						e1.printStackTrace();
					} 				
					}						
			}
			
		});
		btnPoista.setBounds(769, 313, 174, 114);
		contentPane.add(btnPoista);
		
		JButton btnVirkista = new JButton("Virkist\u00E4");
		btnVirkista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			try {
				ReadTravel("matkakulut", (Connection) conn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		});
		btnVirkista.setBounds(854, 11, 89, 23);
		contentPane.add(btnVirkista);
		
		ReadTravel("matkakulut", conn);
	}
	
	public void ReadTravel(String mTable, Connection conn) throws SQLException {
		DefaultTableModel model = new DefaultTableModel();
		model.setRowCount(0);
		//String[] columnNames = new String[] {"Column Header1", "Column Header2", "Column Header3"};
		String[] columnNames = new String[] { "ID", "Matka", "Alku", "Loppu", "Pituus", "Vähennuys", "Kirjattu" };
		model.setColumnIdentifiers(columnNames);
		model.setRowCount(0);
		
		String query = "SELECT * FROM matkakulut";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()) {
			int id = rs.getInt("ID");
			String matka = rs.getString("Matka");
			Date alku = rs.getDate("Alku");
			Date loppu = rs.getDate("Loppu");
			String syy = rs.getString("Syy");
			int km = rs.getInt("Pituus");
			double vahennys = rs.getDouble("Vähennys");
			String oma = rs.getString("Kirjattu");			
			Object o [] = {id, matka, alku, loppu, syy, km, vahennys, oma};
			model.addRow(o);			
		}
		
		table.setModel(model);
		//setTableLayout();
		//table.setFillsViewportHeight(true);
	}
}
