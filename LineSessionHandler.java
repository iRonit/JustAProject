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
import java.io.IOException;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import org.infosys.model.Line;

@ApplicationScoped
public class LineSessionHandler {
    
    private int lineId = 0;
    private final Set<Session> sessions = new HashSet<>();
    private final Set<Line> lines = new HashSet<>();
    
    public void addSession(Session session) {
        sessions.add(session);
        for (Line line: lines) {
            JsonObject addMessage = createAddMessage(line);
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    //Methods operating on Line
    public List getLines() {
        return new ArrayList<>(lines);
    }

    public void addLine(Line line) {
        System.err.println("ADD LINE()");
        System.out.println("add line()");
        line.setId(lineId);
        lines.add(line);
        lineId++;
        JsonObject addMessage = createAddMessage(line);
        sendToAllConnectedSessions(addMessage);
    }

    public void removeLine(int id) {
        Line line = getLineById(id);
        if (line != null) {
            lines.remove(line);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }
    }

    /*
    public void toggleDevice(int id) {
        JsonProvider provider = JsonProvider.provider();
        Line device = getDeviceById(id);
        if (device != null) {
            if ("On".equals(device.getStatus())) {
                device.setStatus("Off");
            } else {
                device.setStatus("On");
            }
            JsonObject updateDevMessage = provider.createObjectBuilder()
                    .add("action", "toggle")
                    .add("id", device.getId())
                    .add("status", device.getStatus())
                    .build();
            sendToAllConnectedSessions(updateDevMessage);
        }
    }
*/
    
    private Line getLineById(int id) {
        for(Iterator it = lines.iterator(); it.hasNext();) {
            Line line = (Line) it.next();
            if (line.getId() == id) {
                return line;
            }
        }
        return null;
    }

    private JsonObject createAddMessage(Line line) {
        //JsonArray for devices associated with the line
        JsonArrayBuilder deviceNames = Json.createArrayBuilder();
        List<String> listOfDeviceNames = line.getDevices().getNames();
        for(String name: listOfDeviceNames)
        {
            deviceNames.add(name);
        }
        //JsonArray for called party addresses 
        JsonArrayBuilder calledParty = Json.createArrayBuilder();
        List<String> listOfCalledParty = line.getCallParty().getCalled();
        for(String called: listOfCalledParty)
        {
            calledParty.add(called);
        }
        //-------------------------------------------
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", line.getId())
                .add("name", line.getName())
                .add("status", line.getStatus())
                .add("devices", deviceNames)
                .add("caller", line.getCallParty().getCaller())
                .add("called", calledParty)
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session: sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(LineSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
