package eHotel.connections;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
				pwd = rs.getString(5);
			}
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        	closeDB();
        }
		return pwd;       
    }
	
	public boolean insertNewCustomer(String[] param){
		getConn();
        try{
        	
            
        	st = db.createStatement();
        	sql = "insert into project.customer values("+param[0]+",'"+param[1]+"','"+param[2]+"','"+param[3]+"','"+param[4]+"')";
        	
        	System.out.print(sql);
            
            st.executeUpdate(sql);
            
            return true;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }finally {
        	closeDB();
        }	       
    }
	
	public  ArrayList<Room> getAllAvailRooms(){
		
		getConn();
		
		ArrayList<Room> Rooms = new ArrayList<Room>();
		
		try {
			ps = db.prepareStatement("select * from project.room" );
			rs = ps.executeQuery();
			while(rs.next()){
				String h_name = rs.getString("h_name");
				int room_no = rs.getInt("room_no");
				int capacity = rs.getInt("capacity");
				double price = rs.getDouble("price");
				Room room = new Room(h_name,room_no,capacity,price);
				Rooms.add(room);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	closeDB();
        }
					
		return Rooms;
		
	}
	
	
	
		
}

	

