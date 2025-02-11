package Controller;

import static org.mockito.ArgumentMatchers.nullable;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

AccountService accountService;
MessageService messageService;

public SocialMediaController()
{
    accountService=new AccountService();
    messageService=new MessageService();
}


    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::addAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::addMessageHandler); 
        app.get("/messages", this::getMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}",this::deleteMessageHandler);
        app.patch("/message/{message_id}",this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages",this::messageByUserHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void addAccountHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper oM = new ObjectMapper();
        Account ac=oM.readValue(ctx.body(), Account.class);
        Account addAttempt = accountService.addAccount(ac);

        if(addAttempt == null){
            ctx.status(400);
        }else{
            ctx.status(200);
            ctx.json(oM.writeValueAsString(addAttempt));
        }
    }

    private void loginAccountHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper oM = new ObjectMapper();
        Account ac = oM.readValue(ctx.body(), Account.class);
        Account loginattempt =accountService.loginToAccount(ac);
        System.out.println(loginattempt);
        if(loginattempt == null){
            ctx.status(401);
        }else{
            ctx.status(200);
            ctx.json(oM.writeValueAsString(loginattempt));
        }
    }
    private void addMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper oM = new ObjectMapper();
        Message m = oM.readValue(ctx.body(), Message.class);
        Message addAttempt =messageService.addMessage(m);
        System.out.println(addAttempt);

        if(addAttempt==null)
        {
        ctx.status(400);
        }
        else{
          ctx.status(200);
        ctx.json(oM.writeValueAsString(addAttempt));  
        }
        
        
        
    }




    private void getMessageHandler(Context ctx) throws JsonProcessingException
    {
        ctx.status(200);
        ctx.json(messageService.getAllMessages());
    }




    private void getMessageByIDHandler(Context ctx) throws JsonProcessingException
    {
        int mid = Integer.parseInt(ctx.pathParam("message_id"));
        Message m=messageService.getMessageByID(mid);
        if(m==null)
        {
            ctx.status(200);
            ctx.json("");
        }
        else{
          ctx.json(messageService.getMessageByID(mid));
        ctx.status(200);  
        }
        
        
    }




    private void deleteMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper oM = new ObjectMapper();
        int mid = Integer.parseInt(ctx.pathParam("message_id"));
        String deleteAttempt =messageService.deleteMessage(mid);
        if(deleteAttempt == null){
            ctx.status(400);
        }else{
            ctx.status(200);
            ctx.json(oM.writeValueAsString(deleteAttempt));
        }
    }



    private void updateMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper oM = new ObjectMapper();
        int mid = Integer.parseInt(ctx.pathParam("message_id"));
        String mText = ctx.pathParam("message_text");
        Message updatMessage =messageService.updateMessage(mid,mText);
        if(updatMessage == null){
            ctx.status(400);
        }else{
            ctx.status(200);
            ctx.json(oM.writeValueAsString(updatMessage));
        }
    }



    private void messageByUserHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper oM = new ObjectMapper();
        int account = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> mList=messageService.getAllMessagesByUser(account);
        ctx.status(200);
        ctx.json(oM.writeValueAsString(mList));
    }

}