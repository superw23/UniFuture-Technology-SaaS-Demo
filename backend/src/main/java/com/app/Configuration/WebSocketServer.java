package com.app.Configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/websocket/{userName}")
@Component
public class WebSocketServer {
    private final static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    //store the number of users online
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    private String userName;
    private String receiver;

    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName){
        sessionMap.put(userName, session);
        this.userName = userName;
        log.info(userName+"becomes active! Current online users: "+sessionMap.size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.put("users", array);
        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", key);//{"username": "Matt", "username": "Dave"}
            array.add(jsonObject);
        }
        log.info(result.toString());
        //{"users":[{"username": "Matt"}, {"username": "Dave"}]}
        sendAllMessages(result.toString());
    }

    @OnClose
    public void onClose(Session session, @PathParam("userName") String userName){
        sessionMap.remove(userName);
        log.info(userName+"closed! Current online users: "+sessionMap.size());
    }

    //Receive messages from clients.
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userName") String userName){
        log.info("From the client side, userName="+userName+" message: "+message);
        JSONObject object = JSON.parseObject(message);
        this.userName = userName;
        receiver = object.getString("to");
        String text = object.getString("text");
        log.info(message);
        sendAllMessages(message);
    }

    @OnError
    public void onError(Session session, Throwable error){
        log.error("Error");
        error.printStackTrace();
    }

    public void sendAllMessages(String message) {
        try {
            for(Session session : sessionMap.values()){
                log.info("Server sent messages to the client of " + session.getId() + message);
                session.getBasicRemote().sendText(message);
            }
        }catch (Exception e){
            log.error("Fail to send messages to clients from the server.", e);
        }
    }
}
