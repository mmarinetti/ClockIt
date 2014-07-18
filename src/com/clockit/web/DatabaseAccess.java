package com.clockit.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.clockit.util.Employee;
import com.clockit.web.actions.SearchAction;

public class DatabaseAccess {
    
	/**
	 * Goes to the database and retreives information about all the employees
	 * This function is terribly inefficient, but I don't think this version of 
	 * Derby supports required functions. So the whole table must be fetched and then the
	 * ResultSet object is filtered.
	 * @param start ID to start fetching from
	 * @param amount Number of records to get, negative value means fetch all records after 'start'
	 * @return Collection of employees
	 */
    public static Collection<Employee> getEmployees(int page, int amount){
    	// Check to see if page is negative
    	if(page < 0) {
    		return null;
    	}
    	Connection conn = null;
    	// Collection to hold the employees
    	Collection<Employee> results = null;
		try {
			// Set up database information
			Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");
			
    		// Set up the query
    		String sql = "SELECT * FROM Employees";
			Statement stmt = conn.createStatement();
			// Execute query
			ResultSet rs = stmt.executeQuery(sql);
			results = new ArrayList<Employee>();
			int i = 0;
			while(rs.next()) {
				// Dat repetitive code...
				if(amount != -1 && i >= page *10 && i < page * 10 + amount) {
					int id = rs.getInt("Employee_id");
					String first = rs.getString("first_name");
					String last = rs.getString("last_name");
					int age = rs.getInt("age");
					Boolean working = rs.getBoolean("working");
					Boolean gender = rs.getBoolean("isMale");
					Timestamp start_date = rs.getTimestamp("start_date");
					Timestamp end_date = rs.getTimestamp("end_date");
					int hoursWorked = rs.getInt("hoursWorked");
					int totalHours = rs.getInt("totalHours");
					
					// Construct employee and populated with information
					Employee e = new Employee();
					e.setEmployee_id(id);
					e.setFirst_name(first);
					e.setLast_name(last);
					e.setAge(age);
					e.setWorking(working);
					e.setGender(gender);
					e.setStart_date(start_date);
					e.setEnd_date(end_date);
					e.setHoursWorked(hoursWorked);
					e.setTotalHours(totalHours);
					// Add employee to collection
					results.add(e);
				} else if(amount == -1) {
					int id = rs.getInt("Employee_id");
					String first = rs.getString("first_name");
					String last = rs.getString("last_name");
					int age = rs.getInt("age");
					Boolean working = rs.getBoolean("working");
					Boolean gender = rs.getBoolean("isMale");
					Timestamp start_date = rs.getTimestamp("start_date");
					Timestamp end_date = rs.getTimestamp("end_date");
					int hoursWorked = rs.getInt("hoursWorked");
					int totalHours = rs.getInt("totalHours");
					
					// Construct employee and populated with information
					Employee e = new Employee();
					e.setEmployee_id(id);
					e.setFirst_name(first);
					e.setLast_name(last);
					e.setAge(age);
					e.setWorking(working);
					e.setGender(gender);
					e.setStart_date(start_date);
					e.setEnd_date(end_date);
					e.setHoursWorked(hoursWorked);
					e.setTotalHours(totalHours);
					// Add employee to collection
					results.add(e);
				}
				i++;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	
		return results;
    }
    
    public static int numberOfEmployees() {
    	Connection conn = null;
    	try {
    		Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");
    		
    		String sql = "SELECT COUNT(*) AS rowcount FROM Employees";
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(sql);
    		rs.next();
    		int count = rs.getInt("rowcount") ;
    		rs.close();
    		return count;
    	} catch (Exception e) {
            System.out.println("SearchUtilityDB says, problem: " + e);
        } finally {
           try
           {
              // close DAO and connection to release db resources
              if (conn != null) { conn.close(); }
           }
           catch (Exception ignored) { }
        }
    	return 0;
    }
    
    /**
     * Searches database based on keyword
     * @param searchWhat const values in SearchAction, what field to search
     * @param searchWord keyword to search by
     * @return collection of employees
     */
    public static Collection<Employee> findByKeyword(String searchWhat, String searchWord) {
    	// declare return value
        Collection<Employee> results = null;
        
        // declare connection
        Connection conn = null;
        try {
        	Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");
           // do query
        	String sql = "";
        	//conn = ds.getConnection();
        	PreparedStatement pstmt = null;
        	switch (searchWhat) {
        	case SearchAction.SQL_FIRST_NAME:
        		sql = "SELECT * FROM Employees WHERE UPPER(first_name) LIKE UPPER(?)";
        		break;
        	case SearchAction.SQL_LAST_NAME:
        		sql = "SELECT * FROM Employees WHERE UPPER(last_name) LIKE UPPER(?)";
        		break;
        	case SearchAction.SQL_ID:
        		sql = "SELECT * FROM Employees WHERE employee_id = ?";
        		break;
        	case SearchAction.SQL_GENDER:
        		sql = "SELECT * FROM Employees WHERE isMale = ?";
        		break;
        	case SearchAction.SQL_WORKING:
        		sql = "SELECT * FROM Employees WHERE working = ?";
        		break;
        	default:
        		sql = "SELECT * FROM Employees";
        		break;
        	}
    	   pstmt = conn.prepareStatement(sql);
           
           String wildcardedWord = "%" + searchWord + "%";

    	   //-- set the ? parameters on the PreparedStatement --//
           if(searchWhat.equals(SearchAction.SQL_ID) || searchWhat.equals(SearchAction.SQL_WORKING))
        	   pstmt.setInt(1, Integer.parseInt(searchWord));
           else if(searchWhat.equals(SearchAction.SQL_GENDER)) {
        	   int si = searchWord.equals("male") ? 1 : 0;
        	   pstmt.setInt(1, si);
           } else
        	   pstmt.setString(1, wildcardedWord);  

    	   //-- execute the PreparedStatement, get a ResultSet back --//
    	   ResultSet rs = pstmt.executeQuery();      

    	   results = new ArrayList<Employee>();
    	   while (rs.next())
    	   {
    		   int id = rs.getInt("employee_id");
				String firstname = rs.getString("first_name");
				String lastname = rs.getString("last_name");
				int age = rs.getInt("age");
				Boolean gender = rs.getBoolean("isMale");
				Boolean working = rs.getBoolean("working");
				Timestamp start_date = rs.getTimestamp("start_date");
				Timestamp end_date = rs.getTimestamp("end_date");
				int hoursWorked = rs.getInt("hoursWorked");
				int totalHours = rs.getInt("totalHours");
				
				Employee e = new Employee();
				e.setEmployee_id(id);
				e.setFirst_name(firstname);
				e.setLast_name(lastname);
				e.setAge(age);
				e.setGender(gender);
				e.setWorking(working);
				e.setStart_date(start_date);
				e.setEnd_date(end_date);
				e.setHoursWorked(hoursWorked);
				e.setTotalHours(totalHours);
				results.add(e);
    	   }
        }
        catch (Exception e)
        {
           System.out.println("SearchUtilityDB says, problem: " + e);
        }
        finally
        {
           try
           {
              // close DAO and connection to release db resources
              if (conn != null) { conn.close(); }
           }
           catch (Exception ignored) { }
        }
        
        // return the search results
        return results;
    }
    
    /**
     * Insert employee into the database
     * @param e Employee to insert
     * @return true if employee was successfully inserted
     */
    public static boolean insertEmployee(Employee e, String username, String password) {
    	Connection conn = null;
    	try {
    		// Set up database info
    		Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");
    		String sql = "INSERT INTO Employees (first_name, last_name, age, start_date, end_date, isMale, working) VALUES (?, ?, ?, NULL, NULL, ?, 0)";
    		
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, e.getFirstName());
    		pstmt.setString(2, e.getLastName());
    		pstmt.setInt(3, e.getAge());
    		if(e.getGender()) {
    			pstmt.setShort(4, (short) 1);
    		} else {
    			pstmt.setShort(4, (short) 0);
    		}
    		
    		int result = pstmt.executeUpdate();
    		
    		if(result != 1) {
    			return false;
    		}
    		
    		// Get the id that was just inserted
    		// getGeneratedKeys not supported
    		sql = "SELECT MAX(employee_id) FROM Employees";
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(sql);
    		
    		if(rs.next()) {
    			int idAdded = rs.getInt(1);
    			sql = "INSERT INTO Users (employee_id, username, password)"
    					+ "VALUES (?, ?, ?)";
    			pstmt = conn.prepareStatement(sql);
    			pstmt.setInt(1, idAdded);
    			pstmt.setString(2, username);
    			pstmt.setString(3, password);
    			result = pstmt.executeUpdate();
    			if(result != 1) {
    				return false;
    			}
    		}
    	} catch (SQLException | ClassNotFoundException sqle) {
    		sqle.printStackTrace();
    		return false;
    	}
    	finally
        {
           try
           {
              // close DAO and connection to release db resources
              if (conn != null) { conn.close(); }
           }
           catch (Exception ignored) { }
        }
    	return true;
    }
    
    /**
     * Delete an employee from the database
     * @param e Employee to delete
     * @return true if employee was successfully deleted
     */
    public static boolean deleteEmployee(Employee e) {
    	Connection conn = null;
    	try {
    		Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");
    		String sql = "DELETE FROM Employees WHERE employee_id = ?";
    		
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, e.getEmployee_id());
    		int result = pstmt.executeUpdate();
    		if (result != 1)
    			return false;
    		
    		// Delete user
    		sql = "DELETE FROM Users WHERE employee_id = ?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, e.getEmployee_id());
    		result = pstmt.executeUpdate();
    	} catch (SQLException | ClassNotFoundException sqle) {
    		sqle.printStackTrace();
    		return false;
    	}
    	finally
        {
           try
           {
              // close DAO and connection to release db resources
              if (conn != null) { conn.close(); }
           }
           catch (Exception ignored) { }
        }
    	return true;
    }
    
    /**
     * Updates the passed employee in the database
     * @param e Employee to update
     * @return true if update was successful
     */
    public static boolean updateEmployee(Employee e) {
    	Connection conn = null;
    	// Invalid id
    	if(e.getEmployee_id() <= 0) {
    		return false;
    	}
    	
    	try {
    		Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");
    		String sql = "UPDATE Employees"
    				+ " SET first_name = ?, last_name = ?, age = ?, isMale = ?, totalHours = ?"
    				+ " WHERE employee_id = ?";
    		
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, e.getFirstName());
    		pstmt.setString(2, e.getLastName());
    		pstmt.setInt(3, e.getAge());
    		if(e.getGender()) {
    			pstmt.setShort(4, (short) 1);
    		} else {
    			pstmt.setShort(4, (short) 0);
    		}
    		pstmt.setInt(5, e.getTotalHours());
    		pstmt.setInt(6, e.getEmployee_id());
    		int result = pstmt.executeUpdate();
    		if(result != 1) {
    			return false;
    		}
    	} catch (SQLException | ClassNotFoundException sqle) {
    		sqle.printStackTrace();
    		return false;
    	} finally {
    		try {
    			if(conn != null) {conn.close();}
    		} catch (Exception ignored) {}
    	}
    	return true;
    }
    
    /**
     * Check username and password to see if they exist within the database
     * @param username
     * @param password
     * @return true if both username and password are in the database
     */
    public static boolean checkLogIn(String username, String password) {
    	Connection conn = null;
    	try {
    		Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");

    		String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";

    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, username);
    		pstmt.setString(2, password);
    		if(!pstmt.executeQuery().next()) {
    			return false;
    		}
    	} catch (SQLException | ClassNotFoundException sqle) {
    		sqle.printStackTrace();
    		return false;
    	}
    	finally
        {
           try
           {
              // close DAO and connection to release db resources
              if (conn != null) { conn.close(); }
           }
           catch (Exception ignored) { }
        }
    	return true;
    }
    
    /**
     * Check to see if the user is an admin
     * @param username
     * @return true if user is an admin
     */
    public static boolean isAdmin(String username) {
    	Connection conn = null;
    	try {
    		Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");
    		String sql = "SELECT admin FROM Users WHERE username = ? AND admin = 1";
    		//conn = ds.getConnection();
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, username);
    		if(!pstmt.executeQuery().next()) {
    			return false;
    		}
    	} catch (SQLException | ClassNotFoundException sqle) {
    		sqle.printStackTrace();
    		return false;
    	}
    	finally
        {
           try {
              // close DAO and connection to release db resources
              if (conn != null) { conn.close(); }
           }
           catch (Exception ignored) { }
        }
    	
    	return true;
    }
    
    /**
     * Clock in an employee
     * @param username Username of the employee that is clocking in
     * @return true if employee was successfully clocked in
     */
    public static boolean clockIn(String username)
    {
    	Connection conn = null;
    	try {
    		Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");
    		
    		// First check to see if the employee is already working
    		// Don't want to reset the check in time
    		if(checkWorking(username)) {
    			return false;
    		}
    		
    		// Then check to see if the employee has checked in already today
    		if(hasCheckedIn(username)) {
    			// Reset the checked out time to null
    			String sql = "UPDATE Employees SET end_date=NULL "
    					+ "WHERE employee_id = (SELECT employee_id FROM Users WHERE username = ?)";
    			PreparedStatement pstmt = conn.prepareStatement(sql);
    			pstmt.setString(1, username);
    			int r = pstmt.executeUpdate();
    			// If the query was not successful, don't check them in
    			if(r != 1) {
    				return false;
    			}
    		}
    		
    		// If not... check them in
    		String sql = "UPDATE Employees SET start_date=CURRENT_TIMESTAMP, working=1 "
    				+ "WHERE employee_id = (SELECT employee_id FROM Users WHERE username = ?)";
    		//conn = ds.getConnection();
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, username);
    		int r = pstmt.executeUpdate();
    		if(r != 1) {
    			return false;
    		}
    	} catch (SQLException | ClassNotFoundException sqle) {
    		sqle.printStackTrace();
    		return false;
    	} finally {
    		try {
    			if(conn != null) {conn.close();}
    		} catch (Exception ignored) {
    		}
    	}
    	return true;
    }
    
    /**
     * Clock out an employee
     * @param username Username of the employee that is clocking out
     * @return true if employee was successfully clocked out
     */
    public static boolean clockOut(String username) {
    	Connection conn = null;
    	try {
    		Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");
    		// Only clock out if currently working
    		if(checkWorking(username)) {
	    		String sql = "UPDATE Employees SET end_date=CURRENT_TIMESTAMP, working=0 "
	    				+ "WHERE employee_id = (SELECT employee_id FROM Users WHERE username = ?)";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		pstmt.setString(1, username);
	    		int r = pstmt.executeUpdate();
	    		
	    		// Update hours worked and total hours worked
	    		updateHours(username);
	    		if(r == 1) {
	    			return true;
	    		}
    		}
    	} catch (SQLException | ClassNotFoundException sqle) {
    		sqle.printStackTrace();
    		return false;
    	} finally {
    		try {
    			if(conn != null) {conn.close();}
    		} catch (Exception ignored) {
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Checks to see if the employee has already checked in AND out
     * If you just want to see if the employee has checked in, try checkWorking(String username)
     * @param username Username of the employee
     * @return true if the employee has checked in AND out
     */
    public static boolean hasCheckedIn(String username) {
    	Connection conn = null;
    	try {
    		Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");
    		String sql = "SELECT end_date FROM Employees, Users WHERE Employees.employee_id = Users.employee_id AND Users.username = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, username);
    		ResultSet rs = pstmt.executeQuery();
    		if(rs.next()) {
    			Timestamp end_date = rs.getTimestamp("end_date");
    			// If the end date exists... user has checked out. Return true
    			if(end_date != null) {
    				return true;
    			}
    		}
    	} catch (SQLException | ClassNotFoundException sqle) {
    		sqle.printStackTrace();
    		return false;
    	} finally {
    		try {
    			if(conn != null) {conn.close();}
    		} catch (Exception ignored) {
    		}
    	}
    	return false;
    }
    
    /**
     * Check if employee is working
     * @param username Username of the employee
     * @return true if employee is working
     */
    public static boolean checkWorking(String username) {
    	Connection conn = null;
    	try {
    		Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");
    		String sql = "SELECT working FROM Employees, Users WHERE Employees.employee_id = Users.employee_id AND Users.username = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, username);
    		ResultSet rs = pstmt.executeQuery();
    		if(rs.next()) {
    			boolean working = rs.getBoolean("working");
    			if(working) {
    				return true;
    			}
    		}
    		return false;
    	} catch (SQLException | ClassNotFoundException sqle) {
    		sqle.printStackTrace();
    		return false;
    	} finally {
    		try {
    			if(conn != null) {conn.close();}
    		} catch (Exception ignored) {
    		}
    	}
    }
    
    /**
     * Update hours of employee in the database based on start_date and end_date in database
     * @param username Username of the employee to update
     */
    public static void updateHours(String username) {
    	Connection conn = null;
    	try {
    		Class.forName("org.apache.derby.jdbc.ClientDriver");
    		conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PERSONNEL", "guest", "password");
    		String sql = "UPDATE Employees SET hoursWorked={fn TIMESTAMPDIFF(SQL_TSI_HOUR, start_date, end_date)}, totalHours=totalHours+{fn TIMESTAMPDIFF(SQL_TSI_HOUR, start_date, end_date)}"
    				+ " WHERE start_date IS NOT NULL AND end_date IS NOT NULL AND employee_id = (SELECT employee_id FROM Users WHERE username = ?)";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, username);
    		pstmt.executeUpdate();
    	} catch (SQLException | ClassNotFoundException sqle) {
    		sqle.printStackTrace();
    	} finally {
    		try {
    			if(conn != null) {conn.close();}
    		} catch (Exception ignored) {
    		}
    	}
    }

}
