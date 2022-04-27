import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Date;  

public class Database {
	private Connection connect_db;
	private String query;
	private Statement statement;
	private ResultSet output_db;
	private String url = "jdbc:mysql://localhost:3306/employeedb";
	private String pass;
	private String name;
	private String error_msg;

	@SuppressWarnings("deprecation")
	public Connection connection() throws InstantiationException, IllegalAccessException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			this.connect_db = DriverManager.getConnection(url, "root", "password");
		} catch (SQLException | ClassNotFoundException e) {
			if (e instanceof SQLException) {
				System.out.println("SQLException: " + e.getMessage());
				System.out.println("SQLState: " + ((SQLException) e).getSQLState());
				System.out.println("VendorError: " + ((SQLException) e).getErrorCode());
			} else {
				System.out.println(e);
			}
		}
		return connect_db;

	}

	@SuppressWarnings("deprecation")
	public Connection connection(String pass, String name)
			throws SQLException, InstantiationException, IllegalAccessException {
		this.pass = pass;
		this.name = name;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			this.connect_db = DriverManager.getConnection(url, this.name, this.pass);
		} catch (SQLException | ClassNotFoundException e) {
			if (e instanceof SQLException) {
				System.out.println("SQLException: " + e.getMessage());
				System.out.println("SQLState: " + ((SQLException) e).getSQLState());
				System.out.println("VendorError: " + ((SQLException) e).getErrorCode());
			} else 
				System.out.println(e);
			
		}
		return connect_db;

	}

	public boolean update_salary(int days, double rate, int id) {

		double salary = days*rate;
		try {
		Connection connect = connection();
		ResultSet rs = search_employee(id);
		if(rs.next()) {
		query = "UPDATE employee SET salary = ? WHERE Employee_id = ?";
		PreparedStatement preparestmt = connect.prepareStatement(query);
		preparestmt.setDouble(1,salary);
		preparestmt.setInt(2, id);
		preparestmt.executeUpdate();
		
		return true;
		}
		else 
		{
			return false;
		}
		}
		catch(Exception ex) {
			System.err.println("Got an exception! ");
		    System.err.println(ex.getMessage());
		    return false;
		}
	}
	
	public boolean update_salary(int days, double rate, double bonus, int id) {

		double salary = days*rate;
		try {
		Connection connect = connection();
		ResultSet rs = search_employee(id);
		if(rs.next()) {
		query = "UPDATE employee SET salary = ?, bonus = ? WHERE Employee_id = ?";
		PreparedStatement preparestmt = connect.prepareStatement(query);
		preparestmt.setDouble(1,salary);
		preparestmt.setDouble(2, bonus);
		preparestmt.setInt(3, id);
		preparestmt.executeUpdate();
		return true;
		}
		else
			return false;
		}
		catch(Exception ex) {
			System.err.println("Got an exception! ");
		    System.err.println(ex.getMessage());
		    return false;
		}
	}
	public boolean update_salary(double bonus, int id) {
		try {
		Connection connect = connection();
		ResultSet rs = search_employee(id);
		if(rs.next()) {
			query = "UPDATE employee SET bonus = ? WHERE Employee_id = ?";
			PreparedStatement preparestmt = connect.prepareStatement(query);
			preparestmt.setDouble(1,bonus);
			preparestmt.setInt(2, id);
			preparestmt.executeUpdate();
			return true;
		}
		else
		{
			return false;
		}
		}
		catch(Exception ex) {
			System.err.println("Got an exception! ");
		    System.err.println(ex.getMessage());
		    return false;
		}
	}


	public boolean add_employee(String str_empname, String str_profession, Date str_dateOfJoin, String bank_name, long bankAcc, long ic_passport) {
		try {
			Connection connect = connection();
			query = "INSERT INTO employee(Employee_name, Profession, date_of_join, Bank_name, bank_acc, Passport_id) VALUES(?,?,?,?,?,?)";
			PreparedStatement preparestmt = connect.prepareStatement(query);
			preparestmt.setString(1, str_empname);
			preparestmt.setString(2, str_profession);
			preparestmt.setDate(3, str_dateOfJoin);
			preparestmt.setString(4, bank_name);
			preparestmt.setLong(5, bankAcc);
			preparestmt.setLong(6, ic_passport);
			preparestmt.addBatch();
			preparestmt.executeBatch();
			connect.close();
			return true;
		}
		catch(Exception ex) {
			System.err.print("Got an exception!");
			System.err.println(ex.getMessage());
			set_errormessage(ex.getMessage());
			return false;
			
		}
	}
	
	private void set_errormessage(String ex)
	{
		error_msg = ex;
	}
	
	
	public String get_errormessage()
	{
		if(error_msg.contains("for key 'employee.ic_passport_UNIQUE'"))
		
			return "Duplicate value at IC/Passport";
		
		
		else if(error_msg.contains("for key 'employee.bank_account_UNIQUE'"))
			return "Duplicate value at Bank Account";
		
		else if(error_msg.contains("Column 'profession' cannot be null"))
			return "Please select the profession";
		
		else if(error_msg.contains("Column 'Bank_name' cannot be null"))
			return "Please select the name of the Bank";
		
		return error_msg;
		
	}
	
	
	public ResultSet search_employee(int id) {
		try
		{
			Connection connect = connection();
			query = "SELECT Employee_name, Profession, date_of_join FROM employee WHERE Employee_id = ?";
			PreparedStatement preparestmt = connect.prepareStatement(query);
			preparestmt.setInt(1, id);
			output_db = preparestmt.executeQuery();
			return output_db;
		}
		catch(Exception ex) 
		{
			System.err.print("Got an exception!");
			System.err.println(ex.getMessage());
			return null;
		}
		
	}
	
	public boolean delete_employee(int id) {
		try {
			Connection connect = connection();
			ResultSet rs = search_employee(id);
			while(rs.next())
			{
				query = "DELETE FROM employee WHERE Employee_id = " + id;
				statement = connect.createStatement();
				statement.executeUpdate(query);
				return true;
			}
			return false;
		}
		catch (Exception ex) 
		{
        		System.err.print("Got an exception!");
    			System.err.println(ex.getMessage());
    			return false;
        	}
		
	}
	public ResultSet search_employee_salaryslip (int id) {
		try
		{
			Connection connect = connection();
			query = "SELECT Employee_name, Employee_id, salary, bonus FROM employee WHERE Employee_id = ?";
			PreparedStatement preparestmt = connect.prepareStatement(query);
			preparestmt.setInt(1, id);
			output_db = preparestmt.executeQuery();
			return output_db;
		}
		catch(Exception ex) 
		{
			System.err.print("Got an exception!");
			System.err.println(ex.getMessage());
			return null;
		}
	}
	public ResultSet show_ID(long icPassport) {
		try
		{
			Connection connect = connection();
			query = "Select Employee_id FROM employee WHERE Passport_id =?";
			PreparedStatement preparestmt = connect.prepareStatement(query);
			preparestmt.setLong(1, icPassport);
			output_db = preparestmt.executeQuery();
			return output_db;
		}
		catch(Exception ex)
		{
			System.err.print("Got an exception!");
			System.err.println(ex.getMessage());
			return null;
		}
		
		
	}
}
