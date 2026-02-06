
/////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Examples for the report "Making external components for 1C mobile platform for Android""
// at the conference INFOSTART 2018 EVENT EDUCATION https://event.infostart.ru/2018/
//
// Sample 1: Delay in code
// Sample 2: Getting device information
// Sample 3: Device blocking: receiving external event about changing of sceen
//
// Copyright: Igor Kisil 2018
//
/////////////////////////////////////////////////////////////////////////////////////////////////////

package org.printtech.printaddin;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

// SAMPLE 3

public class PrintApp implements Runnable {

  private long m_V8Object; // 1C application context
  private Activity m_Activity; // custom activity of 1C:Enterprise

  public PrintApp(Activity activity, long v8Object)
  {
    m_Activity = activity;
    m_V8Object = v8Object;
  }

  public void run()
  {
    System.loadLibrary("org_printtech_printaddin");
  }

  public void show()
  {
    m_Activity.runOnUiThread(this);
  }

  public void bluetoothPrint(String address, String data)
  {
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (bluetoothAdapter == null) {
      Log.e("PrintApp", "Bluetooth not supported");
      return;
    }
    if (!bluetoothAdapter.isEnabled()) {
      Log.e("PrintApp", "Bluetooth not enabled");
      return;
    }
    BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
    try {
      UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // SPP UUID
      BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuid);
      socket.connect();
      OutputStream outputStream = socket.getOutputStream();
      outputStream.write(data.getBytes());
      outputStream.close();
      socket.close();
    } catch (IOException e) {
      Log.e("PrintApp", "Error printing", e);
    }
  }

  public native void testScreenActions();

  public native void testSleep();
}
