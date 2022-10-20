package com.iu.amlibrary;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import amazonia.iu.com.amlibrary.client.IUApp;


/**
 * This class echoes a string called from JavaScript.
 */
public class amlibrary extends CordovaPlugin {
  private final boolean IS_DEBUG = true;
  private static final String EVENT_NAME = "EVENT_NAME";
  private static final String PARTNER_TAG = "PARTNER_TAG";
  private static final String ADDITIONAL_DATA = "ADDITIONAL_DATA";

  @Override
  protected void pluginInitialize() {
    Log.d("AMLib", "Plugin Init launch");
    IUApp.launch(this.cordova.getActivity());
    IUApp.init(this.cordova.getActivity().getApplication(), HostComplianceActivity.class);
    
  }

  public amlibrary() {
  }

  @Override
  public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
    Log.d("AMLib", "execute : " + action);
    if (action.equals("launch")) {
      return this.launch(callbackContext);
    } else if (action.equals("onRefreshToken")) {
      return this.onRefreshToken(data, callbackContext);
    } else if (action.equals("onMessageReceived")) {
      return this.onMessageReceived(data, callbackContext);
    } else if (action.equals("setClientAttributes")) {
      return this.setClientAttributes(data, callbackContext);
    } else if (action.equals("getFCMSenderId")) {
      return this.getFCMSenderId(data, callbackContext);
    } else if (action.equals("getSDKStatus")) {
      return this.getSDKStatus(data, callbackContext);
    } else if (action.equals("trackInAppEvents")) {
      return this.trackInAppEvents(data, callbackContext);
    } else if (action.equals("updateOptInStatus")) {
      return this.updateOptInStatus(data, callbackContext);
    } else if (action.equals("getOptInStatus")) {
      return this.getOptInStatus(data, callbackContext);
    } else if (action.equals("updateEngagementOptInStatus")) {
      return this.updateEngagementOptInStatus(data, callbackContext);
    } else if (action.equals("getEngagementOptInStatus")) {
      return this.getEngagementOptInStatus(data, callbackContext);
    }
    return false;
  }

  private boolean launch(CallbackContext callbackContext) {
    Activity context = this.cordova.getActivity();
    IUApp.launch(context);
    callbackContext.success("IUApp Launched from JS");
    return true;
  }

  private boolean onRefreshToken(JSONArray data, CallbackContext callbackContext) {
    Context context = this.cordova.getActivity().getApplicationContext();
    IUApp.refreshFCMToken(context);
    callbackContext.success("IUApp Token Refresh called from JS");
    return true;
  }

  private boolean onMessageReceived(JSONArray data, CallbackContext callbackContext) {
    Context context = this.cordova.getActivity().getApplicationContext();
    try {
      if (data != null && data.length() > 0 && IUApp.handleFCMMessage(context, data.getJSONObject(0))) {
        callbackContext.success("IUApp onMessageReceived called from JS");
        return true;
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    callbackContext.error("IUApp onMessageReceived failed");
    return false;

  }

  // New methods
  public boolean setClientAttributes(JSONArray data, CallbackContext callbackContext) {
    try {
      Context context = this.cordova.getActivity().getApplicationContext();

      if (data != null && data.length() > 0 && data.getJSONObject(0) != null) {
        // if(IS_DEBUG) {
        Log.e("Client Attributes : ", data.toString());
        //  }
        IUApp.setClientAttributes(context, data.getJSONObject(0));
        callbackContext.success("IUApp set client attributes called from JS" + data.getJSONObject(0));
        return true;
      }
    } catch (Exception ex) {
      callbackContext.error("IUApp set client attributes called from JS failed");
      ex.printStackTrace();
    }
    return false;
  }

  public boolean getFCMSenderId(JSONArray data, CallbackContext callbackContext) {
    try {
      IUApp.getFCMSenderId();
      callbackContext.success("IUApp getFCMSenderId called from JS" + IUApp.getFCMSenderId());
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      callbackContext.error("IUApp getFCMSenderId called from JS failed");
    }
    return false;
  }

  public boolean getSDKStatus(JSONArray data, CallbackContext callbackContext) {
    try {
      Context context = this.cordova.getActivity().getApplicationContext();

      IUApp.getSDKStatus(context);

      callbackContext.success("IUApp getSDKStatus called from JS" + IUApp.getSDKStatus(context));
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      callbackContext.error("IUApp getSDKStatus called from JS failed");
    }
    return false;
  }

  // In App Events
  public boolean trackInAppEvents(JSONArray data, CallbackContext callbackContext) {
    Context context = this.cordova.getActivity().getApplicationContext();
    try {
      if (data != null && data.length() > 0) {
        JSONObject jsonObject = data.getJSONObject(0);
        if (jsonObject != null) {
          if (IS_DEBUG) {
            Log.e("In App Event Ionic App", jsonObject.toString());
          }

          IUApp.trackInAppEvents(context.getApplicationContext(), jsonObject);
          callbackContext.success("IUApp trackInAppEvents called from JS" + jsonObject.toString());

        }
        return true;
      }
    } catch (Exception ex) {
      callbackContext.error("IUApp trackInAppEvents called from JS failed");
      ex.printStackTrace();
    }

    return false;
  }

  // TIM - Opt In related
  public boolean updateOptInStatus(JSONArray data, CallbackContext callbackContext) {
    try {
      Context context = this.cordova.getActivity().getApplicationContext();
      if (data != null && data.length() > 0) {
        boolean optInStatus = data.getBoolean(0);
        if (IS_DEBUG) {
          Log.e("Opt In Value", "Update Value to IU SDK from Ionic App :" + optInStatus);
        }
        IUApp.updateOptInStatus(context, optInStatus);
      }
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public boolean getOptInStatus(JSONArray data, CallbackContext callbackContext) {
    try {

      Context context = this.cordova.getActivity().getApplicationContext();

      if (IS_DEBUG) {
        Log.e("Opt In Value", "Value from IU SDK in Ionic App :" + IUApp.getOptInStatus(context));
      }
      boolean optInValue = IUApp.getOptInStatus(context);
      int value = optInValue ? 1 : 0;
      // callbackContext.onsuccess will send data from here to .ts file in host application, so that we can update the value there
      callbackContext.success(value); // success doesn't not accepts boolean
      // need to check
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      callbackContext.error("IUApp getOptInStatus called from JS failed");
    }

    return false;
  }


  // Engagement Opt In
  public boolean updateEngagementOptInStatus(JSONArray data, CallbackContext callbackContext) {
    try {
      Context context = this.cordova.getActivity().getApplicationContext();
      if (data != null && data.length() > 0) {
        boolean optInStatus = data.getBoolean(0);
        if (IS_DEBUG) {
          Log.e("Eng Opt In Value", "Update Eng Opt In Value to IU SDK from Ionic App :" + optInStatus);
        }
        IUApp.updateOptInStatusForEngagement(context, optInStatus);
      }
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public boolean getEngagementOptInStatus(JSONArray data, CallbackContext callbackContext) {
    try {

      Context context = this.cordova.getActivity().getApplicationContext();

      if (IS_DEBUG) {
        Log.e("Eng Opt In Value", "Eng Opt In Value from IU SDK in Ionic App :" + IUApp.getOptInStatusForEngagement(context));
      }
      boolean optInValue = IUApp.getOptInStatusForEngagement(context);
      int value = optInValue ? 1 : 0;
      // callbackContext.onsuccess will send data from here to .ts file in host application, so that we can update the value there
      callbackContext.success(value); // success doesn't not accepts boolean
      // need to check
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      callbackContext.error("IUApp getOptInStatus called from JS failed");
    }

    return false;
  }


}

