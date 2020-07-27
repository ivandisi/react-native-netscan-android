package it.id.netscan;

import android.app.Activity;
import android.content.ComponentName;
import android.util.SparseArray;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.WritableArray; 

import android.content.ComponentName;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.annotation.Nullable;

import com.stealthcopter.networktools.PortScan;
import com.stealthcopter.networktools.SubnetDevices;
import com.stealthcopter.networktools.subnet.Device;

public class NetScanModule extends ReactContextBaseJavaModule{

  public NetScanModule(ReactApplicationContext reactContext) {
      super(reactContext);
  }

  @Override
  public String getName() {
      return "NetScan";
  }

  @ReactMethod
  public void findDeviceByMAC(final String mac, final Promise promise) {
      if (!TextUtils.isEmpty(mac)) {
          SubnetDevices.fromLocalAddress().findDevices(new SubnetDevices.OnSubnetDeviceFound() {
              @Override
              public void onDeviceFound(Device device) {
                  if (mac.toUpperCase().equals(device.mac)) {
                      SubnetDevices.fromLocalAddress().cancel();
                      
                      WritableMap d = new WritableNativeMap();

                      d.putString("ip", device.ip);
                      d.putString("mac", device.mac);
                      d.putString("hostname", device.hostname);

                      promise.resolve(d);
                  }
              }
              @Override
              public void onFinished(ArrayList<Device> devicesFound) {
                  try {
                      promise.resolve(null);
                  } catch (Exception e) {
                      // Promise already resolved
                  }
              }
          });
      } else {
          promise.resolve(null);
      }
  }

  @ReactMethod
  public void findDevicesFromIp(final String networkIp, final Promise promise) {
    try {
        SubnetDevices.fromIPAddress(networkIp).findDevices(new SubnetDevices.OnSubnetDeviceFound() {
            @Override
            public void onDeviceFound(Device device) {
            }

            @Override
            public void onFinished(ArrayList<Device> devicesFound) {
                WritableArray list = Arguments.createArray();

                for (Device device : devicesFound) {
                    WritableMap d = new WritableNativeMap();

                    d.putString("ip", device.ip);
                    d.putString("mac", device.mac);
                    d.putString("hostname", device.hostname);

                list.pushMap(d);
            }

                promise.resolve(list);
            }
        });
      } catch (Exception e) {
        e.printStackTrace();
        promise.resolve(null);
      }
  }

  @ReactMethod
  public void findDevices(final Promise promise) {
      SubnetDevices.fromLocalAddress().findDevices(new SubnetDevices.OnSubnetDeviceFound() {
          @Override
          public void onDeviceFound(Device device) {
          }

          @Override
          public void onFinished(ArrayList<Device> devicesFound) {
              WritableArray list = Arguments.createArray();
                  
              for (Device device : devicesFound) {
                  WritableMap d = new WritableNativeMap();

                  d.putString("ip", device.ip);
                  d.putString("mac", device.mac);
                  d.putString("hostname", device.hostname);
                  
                  list.pushMap(d);
              }

              promise.resolve(list);
          }
    });
  }


  @ReactMethod
  public void scanOpenTCPPorts(final String ip, final int timeout, final Promise promise) {
      if (!TextUtils.isEmpty(ip)) {
          try {
              PortScan.onAddress(ip).setTimeOutMillis(timeout).setPortsAll().setMethodTCP().doScan(new PortScan.PortListener() {
                  @Override
                  public void onResult(int portNo, boolean open) {
                  }

                  @Override
                  public void onFinished(ArrayList<Integer> openPorts) {
                      WritableArray list = Arguments.createArray();

                      for (Integer port : openPorts) {
                          list.pushString(port.toString());
                      }

                      promise.resolve(list);
                  }
              });
          } catch (UnknownHostException e) {
              e.printStackTrace();
              promise.resolve(null);
          }

      } else {
          promise.resolve(null);
      }
  }
  

  @ReactMethod
  public void scanOpenUDPPorts(final String ip, final int timeout, final Promise promise) {
      if (!TextUtils.isEmpty(ip)) {
          try {
              PortScan.onAddress(ip).setTimeOutMillis(timeout).setPortsAll().setMethodUDP().doScan(new PortScan.PortListener() {
                  @Override
                  public void onResult(int portNo, boolean open) {
                  }

                  @Override
                  public void onFinished(ArrayList<Integer> openPorts) {
                      WritableArray list = Arguments.createArray();

                      for (Integer port : openPorts) {
                          list.pushString(port.toString());
                      }

                      promise.resolve(list);
                  }
              });
          } catch (UnknownHostException e) {
              e.printStackTrace();
              promise.resolve(null);
          }

      } else {
          promise.resolve(null);
      }
  }
}