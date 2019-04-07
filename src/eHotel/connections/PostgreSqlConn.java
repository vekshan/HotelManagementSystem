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
	
	public ArrayList<String> getView1() {
		getConn();
		
		ArrayList<String> info = new ArrayList<String>();
		try{
            ps = db.prepareStatement("select * from project.view1");
            	               
            rs = ps.executeQuery();

			while(rs.next()) {
				info.add(Integer.toString(rs.getInt("no_of_rooms"))+" - "+rs.getString("area"));
			}
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        	closeDB();
        }
		return info;  
	}
	
	public String getuserinforbyempSSN(int param){
		getConn();

		String pwd = new String();
		
        try{
            ps = db.prepareStatement("select * from project.employee where e_ssn=?");
            
            ps.setInt(1, param);	               
            rs = ps.executeQuery();

			while(rs.next()) {
				pwd = rs.getString("password");
			}
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        	closeDB();
        }
		return pwd;       
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
	
	public ArrayList<String> getAllHotels() {
		getConn();

		ArrayList<String> hotels = new ArrayList<String>();
		
        try{
            ps = db.prepareStatement("select * from project.hotel");
            	               
            rs = ps.executeQuery();

			while(rs.next()) {
				hotels.add(Integer.toString(rs.getInt(1)) +","+ rs.getString(2));
			}
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        	closeDB();
        }
		return hotels;  
	}
	
	public boolean updateEmail(String newEmail, String custSSN) {
		getConn();
		 try{
	        	
	            
	        	st = db.createStatement();
	        	sql = "update project.customer set email='" +newEmail+"' where c_ssn ='"+custSSN+"'; ";
	        	
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
	
	public boolean insertNewEmployee(String[] param){
		getConn();
        try{
        	
            
        	st = db.createStatement();
        	sql = "insert into project.employee values("+param[0]+",'"+param[1]+"','"+param[2]+"','"+param[3]+"','"+param[4]+"','"+param[5]+"')";
        	
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
	
public  ArrayList<Room> searchRooms(int capacity, String area, String startdateStr, String enddateStr){
		
		getConn();
		
		ArrayList<Room> Rooms = new ArrayList<Room>();
		SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.util.Date startdate = null, enddate = null; 
    	try { 
			startdate = textFormat.parse(startdateStr);
			enddate= textFormat.parse(enddateStr);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ps = db.prepareStatement("select * from project.room natural join project.hotel natural join project.hotel_chain where capacity = ? and LOWER(area) like ? except select chain_id, h_name, room_no, capacity, price,street,area,no_of_rooms,email, category, no_of_hotels,hc_name  \r\n" + 
					"	from project.room natural join project.hotel natural join project.hotel_chain natural join project.booking\r\n" + 
					"	where (? <= enddate and ? >= startdate);" );
			ps.setInt(1, capacity);
			ps.setString(2, area.toLowerCase()+"%");
			ps.setDate(3,new java.sql.Date(startdate.getTime()));
			ps.setDate(4,new java.sql.Date(enddate.getTime()));
			
			rs = ps.executeQuery();
			while(rs.next()){
				int chain_id =rs.getInt("chain_id");
				String h_name = rs.getString("h_name");
				int room_no = rs.getInt("room_no");
				//int capacity = rs.getInt("capacity");
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

	public  ArrayList<Room> getbookedRooms(String custSSN){
		
		getConn();
		
		ArrayList<Room> Rooms = new ArrayList<Room>();
		
		try {
			ps = db.prepareStatement("select * from project.booking natural join project.room where c_ssn='"+custSSN+"'");
			rs = ps.executeQuery();
			while(rs.next()){
				
				int chain_id =rs.getInt("chain_id");
				String h_name = rs.getString("h_name");
				int room_no = rs.getInt("room_no");
				int capacity = rs.getInt("capacity");
				double price = rs.getDouble("price");
				Date startdate =rs.getDate("startdate");
				Date enddate = rs.getDate("enddate");
				
				Room room = new Room(chain_id,h_name,room_no,capacity,price,startdate,enddate);
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
	
public  ArrayList<Room> getbookedRooms(String custSSN, String empSSN){
		
		getConn();
		
		ArrayList<Room> Rooms = new ArrayList<Room>();
		int chain_id = 0;
		String h_name="";
		try{
            ps = db.prepareStatement("select * from project.employee where e_ssn=?");
            
            ps.setInt(1, Integer.parseInt(empSSN));	               
            rs = ps.executeQuery();

			while(rs.next()) {
				chain_id = rs.getInt("chain_id");
				h_name = rs.getString("h_name");
				
			}
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        	closeDB();
        }
		
		getConn();
		
		try {
			ps = db.prepareStatement("select * from project.booking natural join project.room where c_ssn='"+custSSN+"' and chain_id='"+chain_id+"' and h_name='"+h_name+"';");
			rs = ps.executeQuery();
			while(rs.next()){
				
				chain_id =rs.getInt("chain_id");
				h_name = rs.getString("h_name");
				int room_no = rs.getInt("room_no");
				int capacity = rs.getInt("capacity");
				double price = rs.getDouble("price");
				Date startdate =rs.getDate("startdate");
				Date enddate = rs.getDate("enddate");
				
				Room room = new Room(chain_id,h_name,room_no,capacity,price,startdate,enddate);
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
	
	
	
	public String bookRoom(String custSSN, String chain_id, String h_name, String roomno, String startdateStr, String enddateStr){
		getConn();
		
        try{
			
        	ps = db.prepareStatement("INSERT INTO project.booking(startdate, enddate, c_ssn, chain_id, h_name, room_no) VALUES (?, ?, ?, ?, ?, ?)");
        	SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        	java.util.Date startdate = null, enddate = null; 
        	try { 
				startdate = textFormat.parse(startdateStr);
				enddate= textFormat.parse(enddateStr);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	ps.setDate(1,new java.sql.Date(startdate.getTime()));
        	ps.setDate(2, new java.sql.Date(enddate.getTime()));
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
	

	
	public boolean createRenting(String e_ssn,String c_ssn, String chain_id, String h_name, String room_no,String startdateStr, String enddateStr, double payment) {
		getConn();
		
		SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.util.Date startdate = null, enddate = null; 
    	try { 
			startdate = textFormat.parse(startdateStr);
			enddate= textFormat.parse(enddateStr);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
        	
			
			ps = db.prepareStatement("INSERT INTO project.renting (startdate, enddate, payment, c_ssn, e_ssn, chain_id, h_name, room_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setDate(1,new java.sql.Date(startdate.getTime()));
        	ps.setDate(2, new java.sql.Date(enddate.getTime()));
        	ps.setDouble(3, payment);
        	ps.setInt(4, Integer.parseInt(c_ssn));
        	ps.setInt(5, Integer.parseInt(e_ssn));
        	ps.setInt(6,Integer.parseInt(chain_id));
        	ps.setString(7,h_name);
        	ps.setInt(8,Integer.parseInt(room_no));
        	
        	
        	ps.executeUpdate();
			
            
            return true;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }finally {
        	closeDB();
        }	       
	}
	
	public boolean createRenting(String e_ssn,String c_ssn, String room_no,String startdateStr, String enddateStr, double payment) {
		getConn();
		
		SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd"); 
    	java.util.Date startdate = null, enddate = null; 
    	int chain_id=0;
    	String h_name="";
    	try { 
			startdate = textFormat.parse(startdateStr);
			enddate= textFormat.parse(enddateStr);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try{
            ps = db.prepareStatement("select * from project.employee where e_ssn=?");
            
            ps.setInt(1, Integer.parseInt(e_ssn));	               
            rs = ps.executeQuery();

			while(rs.next()) {
				chain_id = rs.getInt("chain_id");
				h_name = rs.getString("h_name");
			}
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        	closeDB();
        }
    	
    	getConn();
		
		try{
        	
			
			ps = db.prepareStatement("INSERT INTO project.renting (startdate, enddate, payment, c_ssn, e_ssn, chain_id, h_name, room_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setDate(1,new java.sql.Date(startdate.getTime()));
        	ps.setDate(2, new java.sql.Date(enddate.getTime()));
        	ps.setDouble(3, payment);
        	ps.setInt(4, Integer.parseInt(c_ssn));
        	ps.setInt(5, Integer.parseInt(e_ssn));
        	ps.setInt(6,chain_id);
        	ps.setString(7,h_name);
        	ps.setInt(8,Integer.parseInt(room_no));
        	
        	
        	ps.executeUpdate();
			
            
            return true;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }finally {
        	closeDB();
        }	       
	}
	
	public boolean convertToRenting(int bid, int e_ssn) {
		getConn();
		Date startdate = null;
		Date enddate = null;
		double payment = 0;
		int c_ssn = 0;
		int chain_id = 0;
		String h_name = null;
		int room_no = 0;
		
		
		try{
        	
            
			ps = db.prepareStatement("select * from project.booking where bid=?");
			ps.setInt(1, bid);	               
            rs = ps.executeQuery();

			while(rs.next()) {
				chain_id =rs.getInt("chain_id");
				h_name = rs.getString("h_name");
				room_no = rs.getInt("room_no");
				payment = rs.getDouble("payment");
				startdate = rs.getDate("startdate");
				enddate = rs.getDate("enddate");
				c_ssn = rs.getInt("c_ssn");
			}
			
			ps = db.prepareStatement("INSERT INTO project.renting (startdate, enddate, payment, c_ssn, e_ssn, bid, chain_id, h_name, room_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setDate(1,startdate);
        	ps.setDate(2, enddate);
        	ps.setDouble(3, payment);
        	ps.setInt(4, c_ssn);
        	ps.setInt(5, e_ssn);
        	ps.setInt(6, bid);
        	ps.setInt(7,chain_id);
        	ps.setString(8,h_name);
        	ps.setInt(9,room_no);
        	
        	
        	ps.executeUpdate();
			
            
            return true;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }finally {
        	closeDB();
        }	       
	}
    
	
	
	
		
}

	

