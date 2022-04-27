import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class adminGUI implements ActionListener {

	Database db = new Database();
	JFrame frame = new JFrame();
	LoginGUI gui;
	String[] bankNameItems = {"MAYBANK", "CIMB Bank", "HSBC Bank","RHB Bank","Other Bank"};
	String[] profession = { "Lecturers", "Admin", "Janitor", "Security Guard", "Vice Chancellor", "Chancellor" };
	ImageIcon logo2 = new ImageIcon("appicon.png");
	String bigtitle_fonttype = "Bierstadt";
	int bigtitle_fontsize = 18;
	int bigtitle_fontstate = Font.BOLD;
	String profession_type, name_of_bank;

	// Declaration of Add/Hire Panel and its components
	JPanel addemp_mainpanel, bigtitle_hirePanel, newemp_contentPanel;
	JLabel bigtitle_hireLabel, empNamelabel, professionlabel, dateOfJoinlabel, bankAcclabel,bankNamelabel, passportlabel;
	JTextField empnameTextInput, professionTextInput, dateOfJoinTextInput, bankAccountTextInput,
	passportTextInput;
	JButton hireButton;
	JComboBox<String> types_profession; JComboBox<String> bankName;

	// Declaration of Employee Payroll Panel and its components
	JLabel employeePayrollTitlelabel, employeeIDlabel, bigtitle_salarylabel, dayslabel,bonuslabel, ratelabel, displaynamelabel,
			displayproflabel, displaydatelabel, displaynametext, displayproftext, displaydatetext;
	JTextField employeeIDTextInput, salaryDaysTextInput, salaryRateTextInput,bonusTextInput;
	JButton searchButton, setSalaryButton, logOutButton, fireButton;
	JPanel bigtitle_salarycalPanel, bigtitle_payrollPanel, payroll_mainPanel, search_empIDPanel,
			salary_calculationPanel, displaypanel;

	JLabel labeldesign1 = new JLabel();
	JLabel labeldesign2 = new JLabel();
	JLabel labeldesign3 = new JLabel();
	JLabel labeldesign4 = new JLabel();
	JLabel labeldesign5 = new JLabel();
	JLabel labeldesign6 = new JLabel();

	public adminGUI() {
		frame.setIconImage(logo2.getImage());

		/********************************************************
		 * This Section is used to set up Add/Hire Employee Panel
		 ********************************************************/

		addemp_mainpanel = new JPanel();
		addemp_mainpanel.setBounds(5, 5, 300, 400);
		addemp_mainpanel.setLayout(null);

		// Big Title (Add/Hire) panel and its component
		bigtitle_hirePanel = new JPanel();
		bigtitle_hireLabel = new JLabel("Add/Hire Employee:", SwingConstants.CENTER);
		bigtitle_hirePanel.setBounds(0, 0, 300, 40);
		bigtitle_hirePanel.setBackground(Color.DARK_GRAY);
		bigtitle_hireLabel.setFont(new Font(bigtitle_fonttype, bigtitle_fontstate, bigtitle_fontsize));
		bigtitle_hireLabel.setForeground(Color.white);
		bigtitle_hirePanel.add(bigtitle_hireLabel);

		// Content of Add/Hire Employee Panel
		newemp_contentPanel = new JPanel();
		types_profession = new JComboBox<String>(profession);
		bankName = new JComboBox<String>(bankNameItems);
		empNamelabel = new JLabel("Employee name:", SwingConstants.CENTER);
		professionlabel = new JLabel("Profession:", SwingConstants.CENTER);
		dateOfJoinlabel = new JLabel("Date of join:", SwingConstants.CENTER);
		bankAcclabel = new JLabel("Bank Account No:", SwingConstants.CENTER);
		passportlabel = new JLabel("IC/Passport No:", SwingConstants.CENTER);
		bankNamelabel = new JLabel("Bank name:", SwingConstants.CENTER);
		empnameTextInput = new JTextField(10);
		professionTextInput = new JTextField(10);
		dateOfJoinTextInput = new JTextField(10);
		dateOfJoinTextInput.setText("YYYY-MM-DD");
		bankAccountTextInput = new JTextField(10);
		passportTextInput = new JTextField(10);
		hireButton = new JButton("Add/Hire");
		hireButton.setFocusable(false);
		hireButton.setBounds(40, 358, 220, 35);
		newemp_contentPanel.setLayout(new GridLayout(6, 2, -10, 20));
		newemp_contentPanel.setBounds(0, 50, 290, 300);
		types_profession.setSelectedIndex(5);
		types_profession.addActionListener(this);
		newemp_contentPanel.add(empNamelabel);
		newemp_contentPanel.add(empnameTextInput);
		newemp_contentPanel.add(professionlabel);
		newemp_contentPanel.add(types_profession);
		newemp_contentPanel.add(dateOfJoinlabel);
		newemp_contentPanel.add(dateOfJoinTextInput);
		newemp_contentPanel.add(bankNamelabel);
		newemp_contentPanel.add(bankName);
		newemp_contentPanel.add(bankAcclabel);
		newemp_contentPanel.add(bankAccountTextInput);
		newemp_contentPanel.add(passportlabel);
		newemp_contentPanel.add(passportTextInput);
		addemp_mainpanel.add(bigtitle_hirePanel);
		addemp_mainpanel.add(newemp_contentPanel);
		addemp_mainpanel.add(hireButton);
		types_profession.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				profession_type = types_profession.getSelectedItem().toString();
			}
		});
		bankName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				name_of_bank  = bankName.getSelectedItem().toString();
			}
		});

		/*****************************************************
		 * This section is used to set up Employee Payroll Panel
		 *****************************************************/
		payroll_mainPanel = new JPanel();
		payroll_mainPanel.setBounds(310, 5, 300, 400);
		payroll_mainPanel.setLayout(null);

		// Big Title (Employee Payroll) panel and its component
		employeePayrollTitlelabel = new JLabel("Employee", SwingConstants.CENTER);
		bigtitle_payrollPanel = new JPanel();

		employeePayrollTitlelabel.setFont(new Font(bigtitle_fonttype, bigtitle_fontstate, bigtitle_fontsize));
		employeePayrollTitlelabel.setForeground(Color.white);
		bigtitle_payrollPanel.setBounds(0, 0, 300, 40);
		bigtitle_payrollPanel.setBackground(Color.DARK_GRAY);
		bigtitle_payrollPanel.add(employeePayrollTitlelabel);
		payroll_mainPanel.add(bigtitle_payrollPanel);

		// Search employee by ID Panel with its label and textfield and button
		employeeIDlabel = new JLabel("Employee ID:", SwingConstants.CENTER);
		employeeIDTextInput = new JTextField(10);
		searchButton = new JButton("Search");
		displaynamelabel = new JLabel("Name:", SwingConstants.CENTER);
		displayproflabel = new JLabel("Profession:", SwingConstants.CENTER);
		displaydatelabel = new JLabel("Date of Join:", SwingConstants.CENTER);
		fireButton = new JButton("Fire");
		displaynametext = new JLabel("", SwingConstants.CENTER);
		displayproftext = new JLabel("", SwingConstants.CENTER);
		displaydatetext = new JLabel("", SwingConstants.CENTER);
		search_empIDPanel = new JPanel();
		salary_calculationPanel = new JPanel();
		displaynamelabel.setFont(new Font("Microsoft GothicNeo", Font.BOLD, 11));
		displayproflabel.setFont(new Font("Microsoft GothicNeo", Font.BOLD, 11));
		displaydatelabel.setFont(new Font("Microsoft GothicNeo", Font.BOLD, 11));
		displaynametext.setFont(new Font("Microsoft GothicNeo", Font.BOLD, 11));
		displayproftext.setFont(new Font("Microsoft GothicNeo", Font.BOLD, 11));
		displaydatetext.setFont(new Font("Microsoft GothicNeo", Font.BOLD, 11));
		search_empIDPanel.setLayout(new GridLayout(1, 2, -40, 10));
		search_empIDPanel.setBounds(0, 50, 290, 30);
		fireButton.setBounds(125, 90, 80, 20);
		fireButton.setFocusable(false);
		searchButton.setBounds(210, 90, 80, 20);
		searchButton.setFocusable(false);
		displaypanel = new JPanel();
		displaypanel.setLayout(new GridLayout(3, 2, 10, 6));
		displaypanel.setBounds(5, 120, 290, 80);
		search_empIDPanel.add(employeeIDlabel);
		search_empIDPanel.add(employeeIDTextInput);
		displaypanel.add(displaynamelabel);
		displaypanel.add(displaynametext);
		displaypanel.add(displayproflabel);
		displaypanel.add(displayproftext);
		displaypanel.add(displaydatelabel);
		displaypanel.add(displaydatetext);
		payroll_mainPanel.add(search_empIDPanel);
		payroll_mainPanel.add(fireButton);
		payroll_mainPanel.add(searchButton);
		payroll_mainPanel.add(displaypanel);
		searchButton.addActionListener(this);
		fireButton.addActionListener(this);

		// Salary Calculation Panel and bigtitle (Salary Calculation)
		bigtitle_salarycalPanel = new JPanel();
		bigtitle_salarylabel = new JLabel("Salary Calculation", SwingConstants.CENTER);
		bigtitle_salarylabel.setFont(new Font(bigtitle_fonttype, bigtitle_fontstate, bigtitle_fontsize));
		bigtitle_salarylabel.setForeground(Color.white);
		bigtitle_salarycalPanel.setBounds(0, 210, 300, 40);
		bigtitle_salarycalPanel.setBackground(Color.DARK_GRAY);
		bigtitle_salarycalPanel.add(bigtitle_salarylabel);

		// Salary Calculation's content panel (Days, rate, and bonus)
		dayslabel = new JLabel("Days:", SwingConstants.CENTER);
		ratelabel = new JLabel("Rate:", SwingConstants.CENTER);
		bonuslabel = new JLabel("Bonus:", SwingConstants.CENTER);

		logOutButton = new JButton("Log Out");
		salaryDaysTextInput = new JTextField(10);
		salaryRateTextInput = new JTextField(10);
		bonusTextInput = new JTextField(10);
		setSalaryButton = new JButton("Update Salary");
		salary_calculationPanel.setBounds(0, 260, 290, 100);
		salary_calculationPanel.setLayout(new GridLayout(3, 2, -20, 15));
		salary_calculationPanel.add(dayslabel);
		salary_calculationPanel.add(salaryDaysTextInput);
		salary_calculationPanel.add(ratelabel);
		salary_calculationPanel.add(salaryRateTextInput);
		salary_calculationPanel.add(bonuslabel);
		salary_calculationPanel.add(bonusTextInput);
		payroll_mainPanel.add(salary_calculationPanel);
		payroll_mainPanel.add(bigtitle_salarycalPanel);
		setSalaryButton.setFocusable(false);
		setSalaryButton.setBounds(170, 365, 120, 30);
		payroll_mainPanel.add(setSalaryButton);
		setSalaryButton.addActionListener(this);
		logOutButton.setBounds(500, 420, 100, 30);
		logOutButton.addActionListener(this);

		labeldesign1.setBounds(0, 0, 640, 5);
		labeldesign1.setBackground(Color.LIGHT_GRAY);
		labeldesign1.setOpaque(true);
		labeldesign2.setBounds(0, 0, 5, 405);
		labeldesign2.setBackground(Color.LIGHT_GRAY);
		labeldesign2.setOpaque(true);
		labeldesign3.setBounds(0, 405, 640, 5);
		labeldesign3.setBackground(Color.LIGHT_GRAY);
		labeldesign3.setOpaque(true);
		labeldesign4.setBounds(610, 0, 5, 405);
		labeldesign4.setBackground(Color.LIGHT_GRAY);
		labeldesign4.setOpaque(true);
		labeldesign5.setBounds(305, 0, 5, 405);
		labeldesign5.setBackground(Color.LIGHT_GRAY);
		labeldesign5.setOpaque(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(new Color(255, 255, 255));
		frame.setTitle("Employee Control window");
		frame.setSize(630, 500);
		frame.setLocation(400, 200);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.add(addemp_mainpanel);
		frame.add(payroll_mainPanel);
		frame.add(logOutButton);
		frame.add(labeldesign1);
		frame.add(labeldesign2);
		frame.add(labeldesign3);
		frame.add(labeldesign4);
		frame.add(labeldesign5);
		hireButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) throws IllegalArgumentException {
		String id = employeeIDTextInput.getText().toString();
		if (e.getSource() == setSalaryButton) {
			boolean success = false;
			String days_output = salaryDaysTextInput.getText().toString();
			String rate_output = salaryRateTextInput.getText().toString();
			String bonus_output = bonusTextInput.getText().toString();
			boolean empty_bonus = bonus_output.isEmpty();
			boolean empty_days = days_output.isEmpty();
			boolean empty_rate = rate_output.isEmpty();
			boolean empty_id = id.isEmpty();
			if(empty_id == false) {
			if(empty_bonus==false && (empty_days && empty_rate))
			{
				try 
				{
					double valid_bonus =  Double.parseDouble(bonus_output);
					int valid_id = Integer.parseInt(id);
					int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to continue this operation (Employee ID: "+ valid_id + ")?",
							"Set Salary", JOptionPane.YES_NO_OPTION);
					if(result== JOptionPane.YES_OPTION)
					{
						success = db.update_salary(valid_bonus, valid_id);
						if(success) 
						{
						JOptionPane.showMessageDialog(setSalaryButton, "Successful Update Salary");
						employeeIDTextInput.setText("");
						displaynametext.setText("");
						displayproftext.setText("");
						displaydatetext.setText("");
						bonusTextInput.setText("");
						}
						else
						{
							JOptionPane.showMessageDialog(setSalaryButton, "Employee not found! Please ensure your ID is correct!");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(frame, "Operation Cancel");
					}
				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(setSalaryButton, "Wrong inputs at bonus!");
				}
			}
			else if(empty_bonus && (empty_days == false && empty_rate == false))
			{
			try {
				int days = Integer.parseInt(days_output);
				double rate = Double.parseDouble(rate_output);
				int valid_id = Integer.parseInt(id);
				int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to continue this operation (Employee ID: "+ valid_id + ")?",
						"Set Salary", JOptionPane.YES_NO_OPTION);
				if (result== JOptionPane.YES_OPTION)
				{
					success = db.update_salary(days, rate, valid_id);
					if(success) {
					JOptionPane.showMessageDialog(setSalaryButton, "Successful Update Salary");
					salaryDaysTextInput.setText("");
					salaryRateTextInput.setText("");
					employeeIDTextInput.setText("");
					displaynametext.setText("");
					displayproftext.setText("");
					displaydatetext.setText("");
					}
					else
					{
						JOptionPane.showMessageDialog(setSalaryButton, "Employee not found! Please ensure your ID is correct!");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Operation Cancel");
				}
				
				
			} 
			catch(NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(setSalaryButton, "Invalid inputs!");
			}
			}
			else if(empty_bonus == false && (empty_days == false && empty_rate == false))
			{
			try {
				double valid_bonus =  Double.parseDouble(bonus_output);
				int days = Integer.parseInt(days_output);
				double rate = Double.parseDouble(rate_output);
				int valid_id = Integer.parseInt(id);
				int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to continue this operation (Employee ID: "+ valid_id + ")?",
						"Set Salary", JOptionPane.YES_NO_OPTION);
				if (result== JOptionPane.YES_OPTION)
				{
					success = db.update_salary(days, rate, valid_bonus, valid_id);
					if(success)
					{
					JOptionPane.showMessageDialog(setSalaryButton, "Successful Update Salary");
					salaryDaysTextInput.setText("");
					salaryRateTextInput.setText("");
					employeeIDTextInput.setText("");
					displaynametext.setText("");
					displayproftext.setText("");
					displaydatetext.setText("");
					bonusTextInput.setText("");
					}
					else
						JOptionPane.showMessageDialog(setSalaryButton, "Employee not found! Please ensure your ID is correct!");
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Operation Cancel");
				}
				
				
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(setSalaryButton, "Wrong Input!");
			}
		} 
			}
			else 
				JOptionPane.showMessageDialog(setSalaryButton, "Please enter Employee ID!");
		}
		
		else if (e.getSource() == hireButton) {
			boolean valid_name = false, valid_date = false, valid_bank= false,valid_ic = false;
			Date date_dateofjoin = null;
			boolean success = true;
			String str_emp_name = empnameTextInput.getText().toString();
			String str_dateOfJoin_output = dateOfJoinTextInput.getText().toString();
			String str_bank_acc_Output = bankAccountTextInput.getText().toString();
			String str_ic_passport_Output = passportTextInput.getText().toString();
			try {
				try{
					date_dateofjoin = Date.valueOf(str_dateOfJoin_output);
					valid_date = true;
				}catch(Exception ex){
					JOptionPane.showMessageDialog(hireButton, "The date format is wrong");
					valid_date = false;
				}
				try{
				Long long_bank_acc = Long.parseLong(str_bank_acc_Output);
				if((str_bank_acc_Output.length() <10) || (str_bank_acc_Output.length() >14)){
					JOptionPane.showMessageDialog(hireButton, "Enter a bank account of 10 digits");
					valid_bank = false;
				}
				else
					valid_bank=true;
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(hireButton, "Type a number in the bank account");
					valid_bank = false;
				}
				System.out.println("*******************");
				System.out.println(valid_bank);
				System.out.println("*******************");
				
				try{
					Long long_ic_passport = Long.parseLong(str_ic_passport_Output);
					valid_ic = true;
				}catch(Exception ex){
					JOptionPane.showMessageDialog(hireButton, "Type a number in the passport or IC");
					valid_ic = false;
				}

				long bank_acc=Long.parseLong(str_bank_acc_Output); 
				long ic=Long.parseLong(str_ic_passport_Output); 
				//Date date_dateofjoin = Date.valueOf(str_dateOfJoin_output);

				char[] check_name = str_emp_name.toCharArray();
				
				for (char a : check_name) {
					if (Character.isDigit(a)) {
						valid_name = false;
						
					} else
						valid_name = true;
				}
				if(valid_name == false){
					JOptionPane.showMessageDialog(hireButton, "Please type a string for your name");
				}
				try{
				if ( valid_name == true && valid_date == true && valid_bank == true && valid_ic == true) {
					
					int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to continue this operation?",
							"Add Employee", JOptionPane.YES_NO_OPTION);
					if(result== JOptionPane.YES_OPTION) {
					success = db.add_employee(str_emp_name, profession_type, date_dateofjoin, name_of_bank, bank_acc, ic);
						if (success) 
						{
							System.out.println("success");
							ResultSet rs = db.show_ID(ic);
							if(rs.next()) 
							{
								int sql_id = rs.getInt("Employee_id");
								String display_id = Integer.toString(sql_id);
								JOptionPane.showMessageDialog(hireButton, "Successful Added Employee! ID: "+ display_id);
								empnameTextInput.setText("");
								dateOfJoinTextInput.setText("");
								bankAccountTextInput.setText("");
								passportTextInput.setText("");
							}
						}
						else 
								throw new Exception();
					}
					else
						JOptionPane.showMessageDialog(frame, "Operation Cancel");
					}
				}catch(Exception ex){
					if(db.get_errormessage()!= null)
					{
						JOptionPane.showMessageDialog(hireButton, db.get_errormessage());
					}
					else throw new Exception();
				}
			}
			catch(Exception ex){
				System.out.println(ex);
			}
		}
			
		else if (e.getSource() == logOutButton) {
			frame.dispose();
			JOptionPane.showMessageDialog(logOutButton, "Good Bye! Thank you");
			gui = new LoginGUI();
		}
		
		else if (e.getSource() == searchButton) {
			try {
				int valid_id = Integer.parseInt(id);
				ResultSet result = db.search_employee(valid_id);
				if (result.next()) {
					String Name_out = result.getString("Employee_name").toString();
					displaynametext.setText(Name_out);
					String profession_out = result.getString("Profession").toString();
					displayproftext.setText(profession_out);
					String date_out = result.getDate("date_of_join").toString();
					displaydatetext.setText(date_out);
				}

				else
					JOptionPane.showMessageDialog(searchButton, "No employee found! Please ensure your ID is correct!");
			} catch (NumberFormatException | SQLException ex) {
				JOptionPane.showMessageDialog(searchButton, "Wrong Input");
			}
		} 
		
		else if (e.getSource() == fireButton) {
			{	boolean success = false;
				try {
					int valid_id = Integer.parseInt(id);
					int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to continue this operation (Employee ID: "+ valid_id + ")?",
							"Remove Employee", JOptionPane.YES_NO_OPTION);
					if(result== JOptionPane.YES_OPTION) {
						success = db.delete_employee(valid_id);
						if(success == true)
						{
						JOptionPane.showMessageDialog(fireButton, "Successfully Removed");
						employeeIDTextInput.setText("");
						displaynametext.setText("");
						displayproftext.setText("");
						displaydatetext.setText("");
						} 
						else
						JOptionPane.showMessageDialog(fireButton, "No employee found! Please ensure your ID is correct!");
						}
					else
						JOptionPane.showMessageDialog(frame, "Operation Cancel");

				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(fireButton, "Wrong inputs!");
				}
			}
		}

	}

}