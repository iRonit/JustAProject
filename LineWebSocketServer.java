/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.infosys.websocket;

/**
 *
 * @author ronit01.trn
 */
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import org.infosys.model.Line;

@ApplicationScoped
@ServerEndpoint("/CTIConnector")
public class LineWebSocketServer {

    @Inject
    private LineSessionHandler sessionHandler;
    
    @OnOpen
        public void open(Session session) {
            sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(LineWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        System.out.println("BreakPoint: "+message);
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("add".equals(jsonMessage.getString("action"))) {
                Line line = new Line();
                line.setName(jsonMessage.getString("name"));
                line.setStatus(jsonMessage.getString("status"));
                
                //----------------For Devices---------------------------
                List<String> names = new ArrayList<>();
                JsonArray deviceNames = jsonMessage.getJsonArray("devices");
                for (JsonValue jsonValue : deviceNames) {
                    names.add(jsonValue.toString());
                }
                line.getDevices().setNames(names);
                //------------------------------------------------------
                
                line.getCallParty().setCaller(jsonMessage.getString("caller"));
                
                //----------------For Called Party----------------------
                List<String> called = new ArrayList<>();
                JsonArray calledParty = jsonMessage.getJsonArray("called");
                for (JsonValue jsonValue : calledParty) {
                    called.add(jsonValue.toString());
                }
                line.getCallParty().setCalled(called);
                //------------------------------------------------------
                sessionHandler.addLine(line);
            }

            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.removeLine(id);
            }
        }catch(Exception e){
            System.out.println("EXCEPTION IN HANDLEMESAGE!!!");
        }
    }
        
     
    
}    
