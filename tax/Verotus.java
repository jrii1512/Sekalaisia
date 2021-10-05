package VeroPackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class Verotus {

	private JFrame frame;
	private JTextField txtPVM;
	private JTextField txtLiike;
	private JTextField txtHinta;
	public static String userString;
	public Records r;
	public ConnectionDB c;
	private JTextField txtAlv;
	private JTextField txtMyyntiVero;
	private JTextField txtTulo;
	private Matkakulut mFrame;

	/**
	 * Launch the application.
	 */
	
	
	  static void infoBox(String infoMessage, String titleBar)
	    {
		  JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	    }
	
	public void NaytaLomake() {
		frame.setVisible(true);
	}
	
	public Verotus()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			NoSuchMethodException, SecurityException, InvocationTargetException, SQLException, ParseException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws SQLException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 */
	private void initialize() throws ParseException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException,
			InvocationTargetException, SQLException, ParseException {
		int counter = 0;
		//mFrame = null;
		r = new Records();
		c = new ConnectionDB();
		Connection conn = c.Initialize();
		String table = c.GetTable();

		frame = new JFrame();
		frame.setBounds(100, 100, 719, 493);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Laji");
		lblNewLabel_1.setBounds(96, 93, 89, 14);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel_1);
		
		txtAlv = new JTextField();
		txtAlv.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		txtAlv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtAlv.setText("0");
		txtAlv.setBounds(247, 227, 86, 22);
		panel.add(txtAlv);
		txtAlv.setColumns(10);


		JComboBox cBLaji = new JComboBox();
		cBLaji.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int lajiInd = cBLaji.getSelectedIndex();
				String strHinta = txtHinta.getText();
				double hinta = Double.parseDouble(txtHinta.getText());
				if (lajiInd == 1) {
					txtAlv.setText("0");
					if (!txtHinta.getText().equals("")) {
						txtAlv.setText(" " +  (hinta * 0.24));
					}
				}
				else if (lajiInd == 0){
					txtAlv.setText("0");
					txtAlv.setText("" + (hinta * 0.4));
				}
				else if (lajiInd == 2) {
					txtMyyntiVero.setText("0");
					txtAlv.setText("0");
					txtAlv.setText("" + (hinta * 0.24));
					
					double alv = hinta * 1.24;
					double ennakkopid = hinta* 0.19;
					double verollinen = alv - ennakkopid;
					double verotMaksettu = verollinen * 0.3;
					txtMyyntiVero.setText("" + (verotMaksettu));
					txtTulo.setText("" + verollinen);
				}
				else if (lajiInd == 3) {
					frame.dispose();
					
					try {	
						if (mFrame == null) {
							Matkakulut mFrame = new Matkakulut();
							System.out.println("mFrame if reached");
							//System.out.println("Already initialized, just showing");
							mFrame.setVisible(true);}
						else if (mFrame != null){
							System.out.println("mFrame != null reached");
							mFrame.toFront();
						}
														

					} catch (IllegalArgumentException | SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchMethodException e1) {
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
		cBLaji.setBounds(247, 90, 149, 20);
		cBLaji.setModel(new DefaultComboBoxModel(new String[] {"Kotitalousv\u00E4hennys", "Mets\u00E4talous", "Puukauppa", "Matkakulut"}));
		panel.add(cBLaji);

		JLabel lblHankinta = new JLabel("Kulu / tulo");
		lblHankinta.setBounds(96, 118, 86, 14);
		lblHankinta.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblHankinta);

		JLabel lblNewLabel = new JLabel("Liike");
		lblNewLabel.setBounds(96, 174, 62, 14);
		panel.add(lblNewLabel);

		txtLiike = new JTextField();
		txtLiike.setBounds(247, 171, 285, 20);
		panel.add(txtLiike);
		txtLiike.setColumns(10);

		JLabel lblPVM = new JLabel("Hankinta pvm");
		lblPVM.setBounds(96, 57, 121, 14);
		panel.add(lblPVM);

		txtPVM = new JTextField();
		txtPVM.setBounds(247, 54, 285, 20);
		panel.add(txtPVM);
		txtPVM.setColumns(10);

		JCheckBox omaCheckBox = new JCheckBox("Ilmoitettu omaveroon?");
		omaCheckBox.setBounds(247, 365, 133, 23);
		panel.add(omaCheckBox);

		JLabel lblNewLabel_3 = new JLabel("Hinta (ei alv)");
		lblNewLabel_3.setBounds(96, 199, 89, 14);
		panel.add(lblNewLabel_3);

		txtHinta = new JTextField();
		txtHinta.setText("0");
		txtHinta.setBounds(247, 196, 86, 20);
		panel.add(txtHinta);
		txtHinta.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("ALV %");
		lblNewLabel_2.setBounds(96, 232, 46, 14);
		panel.add(lblNewLabel_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(244, 121, 288, 39);
		panel.add(scrollPane);
		
		JTextArea txtHankinta = new JTextArea();
		scrollPane.setViewportView(txtHankinta);

		txtMyyntiVero = new JTextField();
		txtMyyntiVero.setText("0");
		txtMyyntiVero.setBounds(247, 266, 86, 20);
		panel.add(txtMyyntiVero);
		txtMyyntiVero.setColumns(10);

		
		txtPVM.setText("19-02-2021");

		JButton btnNewButton = new JButton("Kantaan");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String laji = cBLaji.getSelectedItem().toString();
				String hankinta = txtHankinta.getText();
				double hinta = Double.parseDouble(txtHinta.getText());
				String alvStr = txtAlv.getText();
				double alv = Double.parseDouble(alvStr);
				double myyntiVero = 0;
				
				if (laji.equals("Metsätalous")) {					
					myyntiVero = 0;
				} 
				else if (laji.equals("Kotitalousvähennys")) {
					myyntiVero = 0;
				}
				else if (laji.equals("Puukauppa")) {
					myyntiVero = (hinta *0.3);
				}
								
				String liike = txtLiike.getText();
				String tulo = txtTulo.getText();
				double tilitys = Double.parseDouble(tulo);
				
				String omaVero = "Ei";
				Boolean oma = false;
				if (omaCheckBox.isSelected()) {
					oma = true;
					omaVero = "Kyllä";
				}

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				try {
				
					Date date = sdf.parse(txtPVM.getText());
					System.out.println(date);
					java.sql.Date s = new java.sql.Date(date.getTime());
					System.out.println(s);
					r.CreateRecord(table, conn, s, laji, hankinta, liike, hinta, alv, myyntiVero, tilitys, omaVero);
					frame.dispose();
					
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(247, 403, 89, 23);
		panel.add(btnNewButton);

		JButton btnTyhjennä = new JButton("Tyhjenn\u00E4");
		btnTyhjennä.setBounds(443, 403, 89, 23);
		panel.add(btnTyhjennä);
		
		JLabel lblNewLabel_4 = new JLabel("Myyntivero");
		lblNewLabel_4.setBounds(96, 269, 89, 14);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Tulo");
		lblNewLabel_5.setBounds(96, 311, 46, 14);
		panel.add(lblNewLabel_5);
		
		txtTulo = new JTextField();
		txtTulo.setText("0");
		txtTulo.setBounds(247, 308, 86, 20);
		panel.add(txtTulo);
		txtTulo.setColumns(10);
		
		
		
				
	}
}
