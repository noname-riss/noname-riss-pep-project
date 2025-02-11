package DAO;


import Model.Account;
import Util.ConnectionUtil;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class AccountDAO {
    



    public List<Account> getAllAccounts()
    {
        Connection conn= ConnectionUtil.getConnection();
        List<Account> accounts=new ArrayList<>();
        try {
            String sql ="Select * From Account";

            PreparedStatement state=conn.prepareStatement(sql);


            ResultSet rs = state.executeQuery();
            while(rs.next()){
                Account ac = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                accounts.add(ac);
            }
            return accounts;

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
    

    public Account getAccountByUserName(String username)
    {
        Connection conn=ConnectionUtil.getConnection();

        try {
            String sql ="Select * From Account Where username=?;";
            PreparedStatement ps=conn.prepareStatement(sql);
            
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account ac = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                return ac;
            }

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return null;
    }




    public Account Login(String userName, String passWord)
    {
        Connection conn= ConnectionUtil.getConnection();
        try {
            String sql ="Select * From Account Where username=? AND password=?";

            PreparedStatement state=conn.prepareStatement(sql);
            state.setString(1, userName);
            state.setString(2,passWord);

            ResultSet rs = state.executeQuery();
            while(rs.next()){
                Account ac = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                return ac;
            }

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public Account insertAccount(Account ac)
    {
        Connection conn=ConnectionUtil.getConnection();

        try {
            String sql="Insert Into Account (username,password) Values (?,?)";
        PreparedStatement ps=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, ac.getUsername());
        ps.setString(2, ac.getPassword());

        ps.executeUpdate();

        ResultSet pkeyResultSet = ps.getGeneratedKeys();
        if(pkeyResultSet.next()){
            int account_id = (int) pkeyResultSet.getLong(1);
            return new Account(account_id, ac.getUsername(), ac.getPassword());
        }

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }





}
