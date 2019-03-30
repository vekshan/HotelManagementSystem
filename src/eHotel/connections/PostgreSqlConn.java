package eHotel.connections;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import eHotel.entities.Room; 


public class  PostgreSqlConn{
	
	Connection db = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Statement st = null;
    String sql;


	public void getConn(){
		
		try {
			
			Class.forName("org.postgresql.Driver"); 
							
			db = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/",
					"vbund062", "Password1234");
														
		}catch(Exception e) {
			System.out.print("error catched");
		}
					
	}
	
	public void closeDB() {
			try {
				if(rs != null){
					rs.close();
				}
				if(ps!=null){
					ps.close();
				}
				if(st!=null){
					st.close();
				}
				if(db!=null){
					db.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public String getuserinforbycustSSN(int param){
		getConn();

		String pwd = new String();
		
        try{
            ps = db.prepareStatement("select * from project.customer where c_ssn=?");
            
            ps.setInt(1, param);	               
            rs = ps.executeQuery();

			while(rs.next()) {
				pwd = rs.getString(6);
			}
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        	closeDB();
        }
		return pwd;       
    }

		
	}

