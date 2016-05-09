/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.infosys.jtapi;

/**
 *
 * @author Administrator
 */
import com.cisco.jtapi.PlatformExceptionImpl;
import javax.telephony.JtapiPeer;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.JtapiPeerUnavailableException;
import javax.telephony.Provider;

public class ProviderService {

	private static ProviderService instance;
	private static Provider provider;
        
  private static String ip_addr;
  private static String uid;
  private static String pass;

	public ProviderService() {
	  bootStrap();
  }

	private static boolean bootStrap(){
    try {
        JtapiPeer peer = JtapiPeerFactory.getJtapiPeer(null);

        String providerString= ip_addr +
                ";login=" + uid +
                ";passwd=" + pass;
        provider = peer.getProvider(providerString);
        return true;
    }catch (JtapiPeerUnavailableException e) {
        return false;
    }catch (PlatformExceptionImpl e) {
        return false;
    }
	}

	public static Provider getProvider(){
    if (instance == null){
      instance = new ProviderService();
    }
    return provider;
	}
	
  public static boolean validate_login(String ip_address, String userid, String passwrd){
    ip_addr = ip_address;
    uid = userid;
    pass = passwrd;
    if(!bootStrap())
        return false;
    return provider != null;
  }
}
