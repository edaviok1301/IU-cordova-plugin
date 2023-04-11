package com.iu.amlibrary;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.telecom.Call;
import android.util.Log;

import com.google.gson.Gson;

import amazonia.iu.com.MainActivity;
import amazonia.iu.com.amlibrary.client.IUApp;
import amazonia.iu.com.amlibrary.client.NotificationsListener;
import amazonia.iu.com.amlibrary.client.OTAPromotionReceiverListener;
import amazonia.iu.com.amlibrary.client.OtaEvent;
import amazonia.iu.com.amlibrary.data.OtaPromotion;


/**
 * This class echoes a string called from JavaScript.
 */
public class amlibrary extends CordovaPlugin implements OTAPromotionReceiverListener, NotificationsListener {
  private final String TAG = amlibrary.class.getName();

  private final boolean IS_DEBUG = true;
  private static final String EVENT_NAME = "EVENT_NAME";
  private static final String PARTNER_TAG = "PARTNER_TAG";
  private static final String ADDITIONAL_DATA = "ADDITIONAL_DATA";
  CallbackContext otaPromotionCallbackContext;
  CallbackContext notificationListenerCallbackContext;

  @Override
  protected void pluginInitialize() {
    Log.d("AMLib", "Plugin Init launch");
    IUApp.launch(this.cordova.getActivity());
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
    } else if (action.equals("setAppPermissionStatus")) {
      return this.setAppPermissionStatus(data, callbackContext);
    } else if (action.equals("getAppPermissionStatus")) {
      return this.getAppPermissionStatus(data, callbackContext);
    } else if (action.equals("createOrganisationFromCountryCode")) {
      return this.createOrganisationFromCountryCode(data, callbackContext);
    } else if (action.equals("getCurrentOrganizationName")) {
      return this.getCurrentOrganizationName(data, callbackContext);
    } else if (action.equals("setSensitiveDataSwitch")) {
      return this.setSensitiveDataSwitch(data, callbackContext);
    } else if (action.equals("saveFCMToken")) {
      return this.saveFCMToken(data, callbackContext);
    } else if (action.equals("requestNotificationPermission")) {
      return this.requestNotificationPermission(data, callbackContext);
    } else if (action.equals("addOTAPromotionReceiverListener")) {
      return this.addOTAPromotionReceiverListener(data, callbackContext);
    } else if (action.equals("addNotificationListener")) {
      return this.addNotificationListener(data, callbackContext);
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
        Log.e(TAG, "Client Attributes : " + data.toString());
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
            Log.e(TAG, "In App Event Ionic App - " + jsonObject.toString());
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
          Log.e(TAG, "Opt In Value - Update Value to IU SDK from Ionic App :" + optInStatus);
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
        Log.e(TAG, "Opt In Value - Value from IU SDK in Ionic App :" + IUApp.getOptInStatus(context));
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
          Log.e(TAG, "Eng Opt In Value - Update Eng Opt In Value to IU SDK from Ionic App :" + optInStatus);
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
        Log.e(TAG, "Eng Opt In Value - Eng Opt In Value from IU SDK in Ionic App :" + IUApp.getOptInStatusForEngagement(context));
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

  // New methods

  public boolean setAppPermissionStatus(JSONArray data, CallbackContext callbackContext) {
    try {
      Context context = this.cordova.getActivity().getApplicationContext();
      if (data != null && data.length() > 0) {
        boolean appPermissionsStatus = data.getBoolean(0);
        if (IS_DEBUG) {
          Log.e(TAG, "App Permission Status Value - Update Value to IU SDK from Ionic App :" + appPermissionsStatus);
        }
        IUApp.setAppPermissionStatus(context, appPermissionsStatus);
      }
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public boolean getAppPermissionStatus(JSONArray data, CallbackContext callbackContext) {
    try {

      Context context = this.cordova.getActivity().getApplicationContext();

      if (IS_DEBUG) {
        Log.e(TAG, "App Permission Status Value - Value from IU SDK in Ionic App :" + IUApp.getAppPermissionStatus(context));
      }
      boolean optInValue = IUApp.getAppPermissionStatus(context);
      int value = optInValue ? 1 : 0;
      // callbackContext.onsuccess will send data from here to .ts file in host application, so that we can update the value there
      callbackContext.success(value); // success doesn't not accepts boolean
      // need to check
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      callbackContext.error("IUApp getAppPermissionStatus called from JS failed");
    }

    return false;
  }


  public boolean createOrganisationFromCountryCode(JSONArray data, CallbackContext callbackContext) {
    try {
      Context context = this.cordova.getActivity().getApplicationContext();
      if (data != null && data.length() > 0) {
        String countryCode = data.getString(0);
        if (IS_DEBUG) {
          Log.e(TAG, "create Organisation From CountryCode - Update Value to IU SDK from Ionic App :" + countryCode);
        }
        IUApp.createOrganisationFromCountryCode(context, countryCode);
      }
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public boolean getCurrentOrganizationName(JSONArray data, CallbackContext callbackContext) {
    try {

      Context context = this.cordova.getActivity().getApplicationContext();

      if (IS_DEBUG) {
        Log.e(TAG, "Get Current Organization Name Value - Value from IU SDK in Ionic App :" + IUApp.getCurrentOrganizationName(context));
      }
      String organizationName = IUApp.getCurrentOrganizationName(context);

      callbackContext.success(organizationName); // success doesn't not accepts boolean
      // need to check
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      callbackContext.error("IUApp getAppPermissionStatus called from JS failed");
    }

    return false;
  }

  public boolean setSensitiveDataSwitch(JSONArray data, CallbackContext callbackContext) {
    try {
      Context context = this.cordova.getActivity().getApplicationContext();
      if (data != null && data.length() > 0) {
        boolean sensitiveData = data.getBoolean(0);
        if (IS_DEBUG) {
          Log.e(TAG, "App Permission Status Value - Update Value to IU SDK from Ionic App :" + sensitiveData);
        }
        IUApp.setSensitiveDataSwitch(context, sensitiveData);
      }
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  /**
   * No Need to expose here, can be used in from Firebase FCM plugin class
   */
  public boolean saveFCMToken(JSONArray data, CallbackContext callbackContext) {
    try {
      Context context = this.cordova.getActivity().getApplicationContext();
      if (data != null && data.length() > 0) {
        String hostAppFCMToken = data.getString(0);
        if (IS_DEBUG) {
          Log.e(TAG, "Save Host App FCM Token - Update Value to IU SDK from Ionic App :" + hostAppFCMToken);
        }
        IUApp.saveFCMToken(context, hostAppFCMToken);
      }
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  /**
   * Android 13 Notification permission request
   */
  public boolean requestNotificationPermission(JSONArray data, CallbackContext callbackContext) {
    try {
      Context context = this.cordova.getActivity().getApplicationContext();
      IUApp.requestNotificationPermission(context, granted -> {
        if (IS_DEBUG) {
          String text = granted ? "GRANTED" : "NO PERMISSION";
          Log.e(TAG, "RequestNotificationPermission - Ionic App :" + text);
        }
      });
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }


  // COTA - OTA

  public boolean addOTAPromotionReceiverListener(JSONArray data, CallbackContext otaPromotionCallbackContext) {
    try {
      this.otaPromotionCallbackContext = otaPromotionCallbackContext;
      if (IS_DEBUG) {
        Log.e(TAG, "OTA - Add OTA Promotion listener Receiver");
      }
      IUApp.addOTAPromotionReceiverListener(this);

      PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
      result.setKeepCallback(true);
      this.otaPromotionCallbackContext.sendPluginResult(result);
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  @Override
  public void onOTAPromotionReceived(OtaPromotion otaPromotion) {
    String json = serializeToJson(otaPromotion);
    CallbackContext context = this.otaPromotionCallbackContext;
    if (IS_DEBUG) {
      Log.d(TAG, "COTA-OtaPromotion" + json);
    }

    if (this.otaPromotionCallbackContext != null) {

      PluginResult result = new PluginResult(PluginResult.Status.OK,
        json);
      result.setKeepCallback(false);
      if (this.otaPromotionCallbackContext != null) {
        this.otaPromotionCallbackContext.sendPluginResult(result);
        this.otaPromotionCallbackContext = null;
      }
    }

  }

  public boolean addNotificationListener(JSONArray data, CallbackContext notificationListenerCallbackContext) {
    try {
      this.notificationListenerCallbackContext = notificationListenerCallbackContext;
      if (IS_DEBUG) {
        Log.e(TAG, "OTA - Add Notification listener");
      }
      IUApp.addNotificationListener(this);
      PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
      result.setKeepCallback(true);
      this.notificationListenerCallbackContext.sendPluginResult(result);

      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }


  @Override
  public void onNotificationActionDismiss(String s, OtaEvent.Type type) {
    String receivedData = "";
    if (IS_DEBUG) {
      receivedData = "COTA-Event " + s + " Event: " + type.name();
      Log.e(TAG, receivedData);
    }
    if (this.notificationListenerCallbackContext != null) {

      PluginResult result = new PluginResult(PluginResult.Status.OK,
        receivedData);
      result.setKeepCallback(false);
      if (this.notificationListenerCallbackContext != null) {
        this.notificationListenerCallbackContext.sendPluginResult(result);
        this.notificationListenerCallbackContext = null;
      }
    }
  }

  public String serializeToJson(OtaPromotion data) {
    Gson gson = new Gson();
    return gson.toJson(data);
  }
}

