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
public class Devices {
    private List<String> names;
    public Devices(){}
    
    public List getNames() {return new ArrayList<>(names);}
    public void setNames(List<String> names) {this.names=names;}
}
