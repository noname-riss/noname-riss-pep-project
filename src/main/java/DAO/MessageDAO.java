package DAO;

import Model.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Util.ConnectionUtil;

public class MessageDAO {
    

public Message insertMessage(Message m)
{
    Connection conn= ConnectionUtil.getConnection();
    try {
        String sql="Insert into Message (posted_by,message_text,time_posted_epoch) Values (?,?,?);";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1,m.getPosted_by());
        ps.setString(2,m.getMessage_text());    
        ps.setLong(3,m.getTime_posted_epoch());
    
        ps.executeUpdate();

        ResultSet pkeyResultSet = ps.getGeneratedKeys();
        if(pkeyResultSet.next()){
            int message_id = (int) pkeyResultSet.getLong(1);
            return new Message(message_id,m.getPosted_by(), m.getMessage_text(),m.getTime_posted_epoch());
        }

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
}





public List<Message> getAllMessages()
{
    Connection conn= ConnectionUtil.getConnection();
    List<Message> messages=new ArrayList<>();
    try {
        String sql="Select * From Message";
        PreparedStatement ps = conn.prepareStatement(sql);
       


        ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message m = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(m);
            }
            return messages;

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
}

public Message getMessageByID(int mID)
{
    Connection conn= ConnectionUtil.getConnection();
    
    try {
        String sql="Select * From Message Where message_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mID);
    
        

        ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message m = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"),rs.getLong("time_posted_epoch"));
            return m;
            }
            

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
}



public void deleteMessageByID(int mID)
{
    Connection conn= ConnectionUtil.getConnection();
    
    try {
        String sql="Delete From Message Where message_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mID);
    
        ps.execute();
            

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    
}


public int updateMessageByID(int mID, String newText)
{
    Connection conn= ConnectionUtil.getConnection();
    
    try {
        String sql="update message set message_text=? Where message_id=? ;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, newText);
        ps.setInt(2,mID);
    
        

        int rowupdated = ps.executeUpdate();
        return rowupdated;
    
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return 0;
}


public List<Message> getMessageByUser(int postedID)
{
    Connection conn= ConnectionUtil.getConnection();
    List<Message> messages=new ArrayList<>();
    
    try {
        String sql="Select * From Message Where posted_by=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, postedID);
    
        ps.executeQuery();

        ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message m = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"),rs.getLong("time_posted_epoch"));
            messages.add(m);
            }
            return messages;

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
}


public boolean doesUserExist(int postedId)
{
    Connection conn= ConnectionUtil.getConnection();
    
    try {
        String sql="Select * From Message JOIN Acconunt ON posted_by=account_id Where posted_by=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, postedId);
    
        ps.executeQuery();

        ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message m = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"),rs.getLong("time_posted_epoch"));
            return true;
            }

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false; 
}

}
