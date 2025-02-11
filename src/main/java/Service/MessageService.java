package Service;

import DAO.MessageDAO;
import Model.Message;

import static org.mockito.ArgumentMatchers.nullable;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService()
    {
        messageDAO=new MessageDAO();
    }

    public MessageService(MessageDAO mDAO)
    {
        messageDAO=mDAO;
    }


    public Message addMessage(Message m)
    {
        if(!m.getMessage_text().equals("")&&m.getMessage_text().length()<255)
        {
            return messageDAO.insertMessage(m);
        }
        return null;
    }

    public List<Message> getAllMessages()
    {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int miD)
    {
        return messageDAO.getMessageByID(miD);
    }

    public String deleteMessage(int mid)
    {
        if(messageDAO.getMessageByID(mid)!=null){
            String messageText = messageDAO.getMessageByID(mid).getMessage_text();
            messageDAO.deleteMessageByID(mid);
            return messageText;
        }
        return "";
    }


    public Message updateMessage(int mid, String mText)
    {
        if(messageDAO.getMessageByID(mid)!=null&&mText.length()<255&&!mText.equals(""))
        {
            messageDAO.updateMessageByID(mid, mText);
            return messageDAO.getMessageByID(mid);
        }
        return null;
    }

    public List<Message> getAllMessagesByUser(int user)
    {
        return messageDAO.getMessageByUser(user);
    }

}
