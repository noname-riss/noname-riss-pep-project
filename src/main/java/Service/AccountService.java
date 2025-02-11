package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
     AccountDAO accountDAO;



    public AccountService()
    {
        this.accountDAO=new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO=accountDAO;
    }


    public Account addAccount(Account ac)
    {
        if(ac.getPassword().length()>4 && !ac.getUsername().equals("")&& accountDAO.getAccountByUserName(ac.getUsername())==null)
        {
             return this.accountDAO.insertAccount(ac); 
        }

        return null;
      
    }

    public Account loginToAccount(Account ac)
    {
        return this.accountDAO.Login(ac.getUsername(), ac.getPassword());
    }
}
