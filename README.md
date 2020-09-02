# react-native-netscan-android

A simple React Native native module to scan network and find devices


## Usage
```javascript
import NetScan from 'react-native-netscan';

// Scan the network where the device is connected
const result = await NetScan.findDevices();

// Scan the network by Ip (for example 192.168.0.1)
const result = await NetScan.findDevicesFromIp(ip);

// Find a device Ip by MAC
const result = await NetScan.findDeviceByMAC(MAC);

const result = await NetScan.findDeviceByMACwithNetworkIP(ip, MAC);

// Port Scan by IP (TCP)
const result = await NetScan.scanOpenTCPPorts(IP,timeout);

// Port Scan by IP (UDP)
const result = await NetScan.scanOpenUDPPorts(IP,timeout);
```

Note, on some low end device, it could be necessary add android:largeHeap="true" in the Manifest

This project use my forked repo of stealthcopter AndroidNetworkTools, for more information https://github.com/stealthcopter/AndroidNetworkTools
