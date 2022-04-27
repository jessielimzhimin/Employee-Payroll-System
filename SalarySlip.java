import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SalarySlip extends JFrame implements ActionListener, Printable {

	private final JPanel contentPanel = new JPanel();
	Database db = new Database();
	private final int medical_allowance = 300;
	private final int tax = 11;
	double net_salary;
	JButton backbutton, printBtn;
	JLabel salary_slip_label, emp_name_label, name, emp_id, id_output, medical_output, medicalLabel, taxLabel,
			basicSalaryLabel, basicSalaryOutput, netSalaryLabel, netSalaryOutput, bonusLabel, bonusOutput;
	LoginGUI gui;
	ImageIcon window_logo = new ImageIcon("appicon.png");

	public SalarySlip(int id, String name, double basic_salary, double bonus) {
		String namee = name;
		String str_basic_salary = Double.toString(basic_salary);
		String str_id = Integer.toString(id);
		String str_bonus = Double.toString(bonus);
		net_salary = ( basic_salary + medical_allowance + bonus ) * (100-tax)/100;
		String str_net_salary = Double.toString(net_salary);
		new SalarySlip(str_id, namee, str_basic_salary, str_net_salary, str_bonus);
	}

	public SalarySlip(String id, String Name, String basic_salary, String net_salary, String bonus) {

		// JFrame Constructor
		setIconImage(window_logo.getImage());
		setBounds(100, 100, 351, 473);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(236, 240, 241));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// Salary Slip Title Panel and its Component
		JPanel SalaryTitlePanel = new JPanel();
		SalaryTitlePanel.setBounds(0, 0, 317, 42);
		SalaryTitlePanel.setBackground(new Color(236, 240, 241));
		salary_slip_label = new JLabel("Salary Slip");
		salary_slip_label.setFont(new Font("Tahoma", Font.BOLD, 21));
		SalaryTitlePanel.add(salary_slip_label);
		contentPanel.add(SalaryTitlePanel);

		/****************************************************
		 * This section is used to set up the content panel
		 ****************************************************/

		// Employee name and its output
		emp_name_label = new JLabel("Employee Name:");
		name = new JLabel(Name);
		emp_name_label.setFont(new Font("Tahoma", Font.BOLD, 13));
		emp_name_label.setBounds(10, 52, 128, 26);
		name.setBounds(145, 52, 150, 22);
		contentPanel.add(name);
		contentPanel.add(emp_name_label);

		// ID and its output
		emp_id = new JLabel("Employee's ID:");
		id_output = new JLabel(id);
		emp_id.setFont(new Font("Tahoma", Font.BOLD, 13));
		emp_id.setBounds(10, 90, 108, 26);
		id_output.setBounds(145, 90, 150, 22);
		contentPanel.add(id_output);
		contentPanel.add(emp_id);

		// Allowance Panel (Medical labels)
		JPanel allowancePanel = new JPanel();
		medical_output = new JLabel("300");
		medicalLabel = new JLabel("Medical Allowances:");
		allowancePanel.setBorder(null);
		allowancePanel.setBounds(10, 190, 307, 60);
		allowancePanel.setBackground(new Color(236, 240, 241));
		allowancePanel.setLayout(null);
		medicalLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		medicalLabel.setBounds(8, 26, 129, 14);
		medicalLabel.setVerticalAlignment(SwingConstants.TOP);
		medical_output.setBounds(140, 23, 121, 22);
		allowancePanel.add(medicalLabel);
		allowancePanel.add(medical_output);
		contentPanel.add(allowancePanel);

		// Tax Panel (Tax Labels)
		JPanel taxpanel = new JPanel();
		taxLabel = new JLabel("Tax:    11%");
		taxpanel.setLayout(null);
		taxpanel.setBorder(null);
		taxpanel.setBackground(new Color(236, 240, 241));
		taxpanel.setBounds(10, 250, 307, 48);
		taxLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		taxLabel.setVerticalAlignment(SwingConstants.TOP);
		taxLabel.setBounds(10, 11, 95, 14);
		taxpanel.add(taxLabel);
		contentPanel.add(taxpanel);

		// Basic Salary labels
		basicSalaryLabel = new JLabel("Basic Salary:");
		basicSalaryOutput = new JLabel(basic_salary);
		basicSalaryLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		basicSalaryLabel.setBounds(10, 123, 108, 26);
		basicSalaryOutput.setBounds(145, 122, 150, 22);
		contentPanel.add(basicSalaryOutput);
		contentPanel.add(basicSalaryLabel);
		
		// Bonus Labels
		bonusLabel = new JLabel("Bonus:");
		bonusOutput = new JLabel(bonus);
		bonusLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		bonusLabel.setBounds(10, 156, 108, 26);
		bonusOutput.setBounds(145, 156, 150, 22);
		contentPanel.add(bonusLabel);
		contentPanel.add(bonusOutput);
		

		// Net Salary Labels
		netSalaryLabel = new JLabel("Net Salary:");
		netSalaryOutput = new JLabel(net_salary);
		netSalaryOutput.setBounds(145, 295, 150, 22);
		netSalaryLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		netSalaryLabel.setBounds(10, 295, 77, 22);
		contentPanel.add(netSalaryOutput);
		contentPanel.add(netSalaryLabel);
		

		// OK Buttons and Print Buttons
		backbutton = new JButton("Back");
		backbutton.setBounds(156, 345, 70, 22);
		printBtn = new JButton("Print");
		printBtn.setBounds(255, 345, 70, 22);
		printBtn.addActionListener(this);
		backbutton.addActionListener(this);
		contentPanel.add(printBtn);
		contentPanel.add(backbutton);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backbutton) {
			dispose();
			gui = new LoginGUI();
			JOptionPane.showMessageDialog(backbutton, "Thank You! GoodBye!");
		} else if (e.getSource() == printBtn) {
			try {
				PrinterJob printer = PrinterJob.getPrinterJob();
				printer.setPrintable(new Printable() {

					@Override
					public int print(Graphics graphics, PageFormat pdf, int page) throws PrinterException {
						if (page > 0) {
							return NO_SUCH_PAGE;
						}

						Graphics2D graphics2d = (Graphics2D) graphics;
						graphics2d.translate(pdf.getImageableX(), pdf.getImageableY());
						graphics2d.scale(1.5, 1.5);
						contentPanel.paint(graphics2d);

						return PAGE_EXISTS;
					}

				});
				if (printer.printDialog()) {
					printer.print();
				}
			}
			catch (PrinterException ex) {
				System.out.println("Error printing: " + ex);
			}
		}

	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// Must declare to ensure no error will be thrown
		return 0;
	}
}
