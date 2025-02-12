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

    public Message deleteMessage(int mid)
    {
        if(messageDAO.getMessageByID(mid)!=null){
            Message oldMessage = messageDAO.getMessageByID(mid);
            messageDAO.deleteMessageByID(mid);
            return oldMessage;
        }
        return null;
    }


    public Message updateMessage(int mid, String mText)
    {
        if(messageDAO.getMessageByID(mid)!=null&&mText.length()<255&&mText.length()>0)
        {
            int rows = messageDAO.updateMessageByID(mid, mText);

            if(rows>0)
            return messageDAO.getMessageByID(mid);
        }
        return null;
    }

    public List<Message> getAllMessagesByUser(int user)
    {
        return messageDAO.getMessageByUser(user);
    }

}
