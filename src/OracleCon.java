
/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; */

import java.sql.*;  

class OracleCon
{  
	public static void main(String args[]){  
		
		try{  
			//step1 load the driver class  
			Class.forName("oracle.jdbc.driver.OracleDriver");   // or Class.forName("oracle.jdbc.OracleDriver");
			  
			//step2 create  the connection object  
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","mydbms","mydbms");  
			  
			//step3 create the statement object  
			Statement stmt=con.createStatement();  
			System.out.println("HI");
			//step4 execute query  
			ResultSet rs=stmt.executeQuery("select * from sailors");  
			while(rs.next())  
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			  
			//step5 close the connection object  
			con.close();  
			  
			}catch(Exception e){ System.out.println(e);}  
			  
	}  
}  