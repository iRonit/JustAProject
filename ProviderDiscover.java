/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.infosys.jtapi;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.telephony.Provider;
import org.infosys.model.CallParty;
import org.infosys.model.Devices;
import org.infosys.model.Line;
import org.infosys.websocket.LineSessionHandler;

/**
 *
 * @author Administrator
 */
public class ProviderDiscover {
    
    public Line[] lines; 
    
    public static void populateUserLines(LineSessionHandler sessionHandler) {
        try{
            //Provider provider = ProviderService.getProvider();
            Line line = new Line();
            line.setName("Ronit");
            line.setStatus("idle");
            sessionHandler.addLine(line);
            System.err.println("populateUserLines() "+sessionHandler.getLines());
            
        }catch(Exception e){
            //Log
            System.err.println("Exception in populateUserLines()");
        }
        
        
    }
}
