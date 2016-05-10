/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.infosys.jtapi;


import com.cisco.jtapi.extensions.CiscoAddrInServiceEv;
import com.cisco.jtapi.extensions.CiscoAddrOutOfServiceEv;
import com.cisco.jtapi.extensions.CiscoTermInServiceEv;
import com.cisco.jtapi.extensions.CiscoTermOutOfServiceEv;
import javax.telephony.Address;
import javax.telephony.AddressObserver;
import javax.telephony.CallObserver;
import javax.telephony.Terminal;
import javax.telephony.TerminalObserver;
import javax.telephony.events.AddrEv;
import javax.telephony.events.CallEv;
import javax.telephony.events.TermEv;

/**
 *
 * @author Administrator
 */
public abstract class ClientObserver implements CallObserver, AddressObserver, TerminalObserver{
    
    public static final int ACTOR_OUT_OF_SERVICE = 0;
    public static final int ACTOR_IN_SERVICE =1;
    private Address observedAddress;
    private Terminal observedTerminal;
    private boolean addressInService;

    private boolean terminalInService;

    protected int state = ClientObserver.ACTOR_OUT_OF_SERVICE;



    public ClientObserver(Address observed) {
        this.observedAddress = observed;
        this.observedTerminal = observed.getTerminals()[0];
    }

    public void initialize() {
        try {
            if ( observedAddress != null ) {
                observedAddress.addCallObserver ( this );
//Now add observer on Address and Terminal
                observedAddress.addObserver ( this );
                observedTerminal.addObserver ( this );
                }	
            }
        catch (Exception e) {
            } 
    }





    public final void start() {
        onStart();
    }


    public final void dispose() {
        try {
            onStop ();
            if ( observedAddress != null ) {
                observedAddress.removeObserver ( this );
                observedAddress.removeCallObserver ( this );
            }
            if ( observedTerminal != null ){
                observedTerminal.removeObserver ( this );
            }
        }
        catch (Exception e) {
        }

    }





    public final void stop() {
            onStop();
    }







    public final void callChangedEvent(CallEv [] events) {
            // for now, all metaevents are delivered in the
            // same package...
        metaEvent ( events );
    }





    @Override
    public void addressChangedEvent (AddrEv [] events) {
        for ( int i=0; i<events.length; i++ ) {
            Address address = events[i].getAddress ();
            switch ( events[i].getID () ) {
                case CiscoAddrInServiceEv.ID:
                    addressInService = true;
                    if ( terminalInService ) {
                        if ( state != ClientObserver.ACTOR_IN_SERVICE ) {
                            state = ClientObserver.ACTOR_IN_SERVICE ;
                            fireStateChanged();
                        }
                    }
                    break;
                case CiscoAddrOutOfServiceEv.ID:
                    addressInService = false;
                    if ( state != ClientObserver.ACTOR_OUT_OF_SERVICE ) {
                        state = ClientObserver.ACTOR_OUT_OF_SERVICE; // you only want to notify when you had notified earlier that you are IN_SERVICE
                        fireStateChanged();
                    }
                    break;
                    }
            }		


    }





    public void terminalChangedEvent (TermEv [] events) {
        for ( int i=0; i<events.length; i++ ) {
            Terminal terminal = events[i].getTerminal ();
            switch ( events[i].getID () ) {
                case CiscoTermInServiceEv.ID:
                    terminalInService = true;
                    if ( addressInService ) {
                        if ( state != ClientObserver.ACTOR_IN_SERVICE ) {
                            state = ClientObserver.ACTOR_IN_SERVICE;
                            fireStateChanged ();
                        }
                    }
                    break;
                case CiscoTermOutOfServiceEv.ID:
                    terminalInService = false;
                    if ( state != ClientObserver.ACTOR_OUT_OF_SERVICE ) { // you only want to notify when you had notified earlier that you are IN_SERVICE						 
                         state = ClientObserver.ACTOR_OUT_OF_SERVICE;
                         fireStateChanged ();
                    }
                    break;
            }
        }
    }





    protected abstract void metaEvent ( CallEv [] events );

    protected abstract void onStart ();

    protected abstract void onStop ();

    protected abstract void fireStateChanged ();


}
