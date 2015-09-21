package com.SmartDial;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import android.net.Uri;

import android.content.Intent;

/**
 * This class makes a call.
 */


public class Caller extends CordovaPlugin {
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) 
		throws JSONException { 
		
		if ("call".equals(action)) {
			runCall(args, callbackContext);
		}
       return true;
    }
	
    private void runCall(final JSONArray args, final CallbackContext callbackContext) {

//      this.cordova.getThreadPool().execute(new Runnable() {
      this.cordova.getActivity().runOnUiThread(new Runnable() {
          public void run() {
              PluginResult result = null;
              try{                 
            	String phoneNumber = args.getString(0);
            	makeCall(phoneNumber);
                result = new PluginResult(Status.OK);
              } catch (Exception e) {

              }
              callbackContext.sendPluginResult(result);
          }
      });
  }
    
   	private void makeCall(String phoneNumber) {

   		Uri number = Uri.parse("tel:" + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_CALL, number); 
        this.cordova.getActivity().startActivity(callIntent);
   	}
	
}
   
