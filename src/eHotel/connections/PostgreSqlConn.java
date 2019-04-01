package eHotel.connections;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
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
				int chain_id =rs.getInt("chain_id");
				String h_name = rs.getString("h_name");
				int room_no = rs.getInt("room_no");
				int capacity = rs.getInt("capacity");
				double price = rs.getDouble("price");
				Room room = new Room(chain_id,h_name,room_no,capacity,price);
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
	
	public String bookRoom(String custSSN, String chain_id, String h_name, String roomno){
		getConn();
		
        try{
			
        	ps = db.prepareStatement("INSERT INTO project.booking(startdate, enddate, c_ssn, chain_id, h_name, room_no) VALUES (?, ?, ?, ?, ?, ?)");
        	SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        	String paramDateAsString = "2007-12-25"; 
        	java.util.Date myDate = null; 
        	try { 
				myDate = textFormat.parse(paramDateAsString);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	ps.setDate(1,new java.sql.Date(myDate.getTime()));
        	ps.setDate(2, new java.sql.Date(myDate.getTime()));
        	ps.setInt(3, Integer.parseInt(custSSN));
        	ps.setInt(4, Integer.parseInt(chain_id));
        	ps.setString(5, h_name);
        	ps.setInt(6, Integer.parseInt(roomno));
        	ps.executeUpdate();
            return custSSN;

        }catch(SQLException e){
            e.printStackTrace();
            return "";	 
        }finally {
        	closeDB();
        }
		      
    }
    
	
	
	
		
}

	

