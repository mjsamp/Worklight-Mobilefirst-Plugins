package com.SmartDial;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONArray;


public class PhoneCallTrap extends CordovaPlugin {
	private static final String TAG = "PhoneCallTrap";
    CallStateListener listener;
    
    public PhoneCallTrap()	{
		this.listener = null;
	}

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    
        
        if (action.equals("startListen")) {
        prepareListener();
        listener.setCallbackContext(callbackContext);
        
        }
        else if (action.equals("stopListen")) {
        	removePhoneListener();
        }
        
        return true; 
    }

    private void removePhoneListener() {
    	Log.d(TAG, "Removing Listener...");
        if (this.listener != null) {
        	try {
        		TelephonyManager TelephonyMgr = (TelephonyManager) cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        	    TelephonyMgr.listen(listener, PhoneStateListener.LISTEN_NONE);
        	    this.listener = null;
        	} catch (Exception e) {

        	}
        }
		
	}

	private void prepareListener() {
    Log.d(TAG, "Preparing Listener...");
        if (listener == null) {
            listener = new CallStateListener();
            TelephonyManager TelephonyMgr = (TelephonyManager) cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            TelephonyMgr.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
}

class CallStateListener extends PhoneStateListener {

    private static final String TAG = "PhoneCallTrap";
	private CallbackContext callbackContext;

    public void setCallbackContext(CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
    }

    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        if (callbackContext == null) return;

        String msg = "";

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
            msg = "IDLE";
            Log.d(TAG, "IDLE");
            break;

            case TelephonyManager.CALL_STATE_OFFHOOK:
            msg = "OFFHOOK";
            Log.d(TAG, "OFFHOOK");
            break;

            case TelephonyManager.CALL_STATE_RINGING:
            msg = "RINGING";
            Log.d(TAG, "RINGING");
            break;
        }

        PluginResult result = new PluginResult(PluginResult.Status.OK, msg);
        result.setKeepCallback(true);

        callbackContext.sendPluginResult(result);
        Log.d(TAG, "Result: " + result);
    }
    
    

  
   
}



