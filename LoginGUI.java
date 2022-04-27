
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class LoginGUI extends Frame implements ActionListener {
	JFrame frame1 = new JFrame();
	JButton button1 = new JButton("Generate salary");
	JButton button2 = new JButton("admin login");
	ImageIcon ucsi_logo = new ImageIcon("logo.jpg");
	ImageIcon window_logo = new ImageIcon("appicon.png");
	adminGUI admin_page;
	Database db = new Database();

	JPanel paneladmin = new JPanel();
	JPanel designpanel = new JPanel();
	JPanel paneluser = new JPanel();
	JPanel panelusernested = new JPanel();
	JPanel panelusernested2 = new JPanel();
	JPanel paneladminnested = new JPanel();
	JPanel paneladminnested2 = new JPanel();
	JPanel paneladminnested3 = new JPanel();

	JLabel labeluser = new JLabel();
	JLabel logoo = new JLabel();
	JLabel adminNameLabel = new JLabel();
	JLabel adminpassLabel = new JLabel();

	JTextField userid = new JTextField(20);
	JTextField output = new JTextField(20);
	JTextField adminname = new JTextField(20);
	JPasswordField password = new JPasswordField(20);

	public LoginGUI() {
		// To set up main frame
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(600, 480);
		frame1.setLocation(500, 200);
		frame1.setResizable(false);
		frame1.setVisible(true);
		frame1.setLayout(new GridLayout(3, 1));
		frame1.setIconImage(window_logo.getImage());
		frame1.setTitle("UCSI University Payroll Management System");
		
		// For employee to check salary
		labeluser.setText("Enter your ID: ");
		labeluser.setForeground(Color.white);
		paneluser.setLayout(new GridLayout(2, 1));
		panelusernested.setLayout(new FlowLayout());
		panelusernested2.setLayout(new FlowLayout());
		panelusernested.add(labeluser);
		panelusernested.add(userid);
		panelusernested2.add(button1);
		paneluser.add(panelusernested);
		paneluser.add(panelusernested2);
		panelusernested.setBackground(new Color(55, 65, 64));
		panelusernested2.setBackground(new Color(55, 65, 64));
		
		// Header and Icon Panel
		designpanel.setLayout(null);
		logoo.setIcon(ucsi_logo);
		logoo.setBounds(20, 20, 500, 100);
		logoo.setText("Payroll Management System");
		logoo.setForeground(Color.BLACK);
		logoo.setFont(new Font("Garamond", Font.BOLD, 32));
		logoo.setIconTextGap(20);
		designpanel.add(logoo);
		designpanel.setBackground(Color.WHITE);

		/***********************************************
		 * This section consists of nested panel
		 * to construct admin panel (login and password)
		 ***********************************************/
		// panel admin layout is set here and admin name and password Label
		adminNameLabel.setText("Enter your name:        ");
		adminNameLabel.setForeground(Color.white);
		adminNameLabel.setBounds(100, 10, 200, 30);
		adminname.setBounds(250, 10, 200, 30);
		adminpassLabel.setBounds(100, 80, 200, 30);
		adminpassLabel.setText("Enter your password: ");
		adminpassLabel.setForeground(Color.white);
		password.setBounds(250, 80, 200, 30);
		button2.setBounds(150, 120, 200, 30);
		paneladmin.setLayout(new GridLayout(3, 1));
		
		// nested panel admin
		paneladminnested.setLayout(new FlowLayout());
		paneladminnested2.setLayout(new FlowLayout());
		paneladminnested3.setLayout(new FlowLayout());
		paneladminnested.setBackground(new Color(55, 65, 64));
		paneladminnested2.setBackground(new Color(55, 65, 64));
		paneladminnested3.setBackground(new Color(55, 65, 64));
		paneladminnested.add(adminNameLabel);
		paneladminnested.add(adminname);
		paneladminnested2.add(adminpassLabel);
		paneladminnested2.add(password);
		paneladminnested3.add(button2);
		paneladmin.add(paneladminnested);
		paneladmin.add(paneladminnested2);
		paneladmin.add(paneladminnested3);

		frame1.add(designpanel);
		frame1.add(paneluser);
		frame1.add(paneladmin);
		button1.addActionListener(this);
		button2.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {
			String emp_id = userid.getText().toString();
			double bonus = 0;
			try {
				int id = Integer.parseInt(emp_id);
				try {
					ResultSet result = db.search_employee_salaryslip(id);
					if (result.next()) {
						int check_bonus = result.getInt("bonus");
						if (check_bonus == 0) 
							bonus = 0;
						 else if (check_bonus > 0) {
							String bonus_out = result.getString("bonus").toString();
							bonus = Double.parseDouble(bonus_out);
						} 
						String Name_out = result.getString("Employee_name").toString();
						String basic_salary_out = result.getString("salary").toString();
						double basic_salary = Double.parseDouble(basic_salary_out);
						SalarySlip dialog = new SalarySlip(id, Name_out, basic_salary, bonus);
						frame1.dispose();
					} else
						JOptionPane.showMessageDialog(button1, "No Employee found. Please ensure your ID is correct!");
				} catch (SQLException | NullPointerException ex) {
					JOptionPane.showMessageDialog(button1, "Salary not set yet");
				}

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(button1, "Invalid Input");
			}


		} else if (e.getSource() == button2) {
			String stradmin = adminname.getText().toString();
			String strpass = password.getText().toString();
			
			  /*try 
			  {
				  	if(db.connection(strpass, stradmin) != null)
				  	{ 
				  		frame1.dispose();
				  		admin_page = new adminGUI(); 
				  	}
			  } 
			  catch(SQLException | InstantiationException | IllegalAccessException ex){ 
				  JOptionPane.showMessageDialog(button2, "Invalid Username or Password!"); }*/
			 
			if (stradmin.equals("admin") && strpass.equals("admin")) {
				frame1.dispose();
				admin_page = new adminGUI();
			} else {
				JOptionPane.showMessageDialog(button2, "Invalid Username or Password");
			}
		}
	}
}
