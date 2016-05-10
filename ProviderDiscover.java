/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.infosys.jtapi;


import javax.telephony.Provider;
import javax.telephony.Terminal;
import org.infosys.model.Line;
import org.infosys.websocket.LineSessionHandler;
import javax.telephony.Address;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class ProviderDiscover {

    
    public static void populateUserLines(LineSessionHandler sessionHandler) {
        try{
            Provider provider = ProviderService.getProvider();
            if(provider==null)return;
            
            Line line = new Line();
            Address[] addresses = provider.getAddresses();
            for(Address address:addresses)
            {
                line.setName(address.getName());
                
                String status="idle";
                //Get the status of the address
                if(address.getConnections().length>=1)
                    status="active";
                line.setStatus(status);
                
                //Get the devices associated with the address
                Terminal[] terminals = address.getTerminals();
                List<String> names = new ArrayList<>();
                for(Terminal trn: terminals)
                    names.add(trn.getName());
                line.getDevices().setNames(names);
                
                //Somehow Get the CallParty
                
                
                
                //add the line to the session
                sessionHandler.addLine(line);
            }
            
        }catch(Exception e){
            //Log
            System.err.println("Exception in populateUserLines()");
        }
        
        
    }
}
