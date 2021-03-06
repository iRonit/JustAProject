/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.infosys.model;

/**
 *
 * @author ronit01.trn
 */
public class Line {
    
    private int id;
    private String name;
    private String status;
    private Devices devices;
    private CallParty callParty;

    public Line() {
        devices = new Devices();
        callParty = new CallParty();
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Devices getDevices() {
        return devices;
    }
    
    public CallParty getCallParty() {
        return callParty;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDevices(Devices devices) {
        this.devices = devices;
    }
    
    public void setCallParty(CallParty callParty) {
        this.callParty = callParty;
    }
    
}
