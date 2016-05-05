/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.infosys.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronit01.TRN
 */
public class CallParty {
    private String caller;
    private List<String> called;
    
    public CallParty(){}
    
    public String getCaller() {return caller;}
    public List<String> getCalled() {return new ArrayList<>(called);}
    
    public void setCaller(String caller) {this.caller = caller;}
    public void setCalled(List<String> called) {this.called = called;}
}
