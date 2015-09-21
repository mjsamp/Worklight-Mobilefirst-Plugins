package com.SmartDial;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;
import java.io.*;

/**
 * This class runs commands.
 */


public class RunCommand extends CordovaPlugin {

	private static final String TAG = "RunCommand";
	
	//private static String shell;
	
	//private static final String EXIT = "exit\n";

	enum OUTPUT {
	    STDOUT,
	    STDERR,
	    BOTH
	  }

	//public static synchronized void setShell(String shell) {
	  //  RunCommand.shell = shell;
	//}


	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) 
		throws JSONException { 
		
		if ("run".equals(action)) {
			runCommand(args, callbackContext);
		}
       return true;
    }
	
    private void runCommand(final JSONArray args, final CallbackContext callbackContext) {

              PluginResult result = null;
              try {
              String command = args.getString(0);
		      _runCommand(command, OUTPUT.BOTH);
		      result = new PluginResult(Status.OK);
              } catch (Exception e) {
	      
              }
              callbackContext.sendPluginResult(result);

  }
    
   	private static String _runCommand(String command, OUTPUT o) throws IOException {

   	
		    DataOutputStream os = null;
		    Process process = null;
		    try {
		      process = Runtime.getRuntime().exec(command);
		      os = new DataOutputStream(process.getOutputStream());
		      InputStreamHandler sh = sinkProcessOutput(process, o);
		      //os.writeBytes(command + '\n');
		      //os.flush();
		      //os.writeBytes(EXIT);
		      //os.flush();
		      process.waitFor();
		      if (sh != null) {
			String output = sh.getOutput();
			Log.d(TAG, command + " output: " + output);
			return output;
		      } else {
			return null;
		      }
		    } catch (Exception e) {
		      final String msg = e.getMessage();
		      Log.e(TAG, "runCommand error: " + msg);
		      throw new IOException(msg);
		    } finally {
		      try {
			if (os != null) {
			  os.close();
			}
			if (process != null) {
			  process.destroy();
			}
		      } catch (Exception ignored) {}
		    }

   	}

   	public static InputStreamHandler sinkProcessOutput(Process p, OUTPUT o) {
   	    InputStreamHandler output = null;
   	    switch (o) {
   	      case STDOUT:
   	        output = new InputStreamHandler(p.getErrorStream(), false);
   	        new InputStreamHandler(p.getInputStream(), true);
   	        break;
   	      case STDERR:
   	        output = new InputStreamHandler(p.getInputStream(), false);
   	        new InputStreamHandler(p.getErrorStream(), true);
   	        break;
   	      case BOTH:
   	        new InputStreamHandler(p.getInputStream(), true);
   	        new InputStreamHandler(p.getErrorStream(), true);
   	        break;
   	    }
   	    return output;
   	 }
   	
   	private static class InputStreamHandler extends Thread {
   	    private final InputStream stream;
   	    private final boolean     sink;
   	    StringBuffer output;

   	    public String getOutput() {
   	      return output.toString();
   	    }

   	    InputStreamHandler(InputStream stream, boolean sink) {
   	      this.sink = sink;
   	      this.stream = stream;
   	      start();
   	    }

   	    @Override
   	    public void run() {
   	      try {
   	        if (sink) {
   	          while (stream.read() != -1) {}
   	        } else {
   	          output = new StringBuffer();
   	          BufferedReader b = new BufferedReader(new InputStreamReader(stream));
   	          String s;
   	          while ((s = b.readLine()) != null) {
   	            output.append(s);
   	          }
   	        }
   	      } catch (IOException ignored) {}
   	    }
   	  }


   	
   	
   	
}
   
