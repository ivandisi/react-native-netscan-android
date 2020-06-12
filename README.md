# react-native-netscan-android

A simple React Native native module to scan network and find devices


## Usage
```javascript
import NetScan from 'react-native-netscan';

// Scan the network where the device is connected
const result = await NetScan.findDevices();

// Find a device Ip by MAC
const result = await NetScan.findDeviceByMAC(MAC);


// Port Scan by IP (TCP)
const result = await NetScan.scanOpenTCPPorts(IP,timeout);

// Port Scan by IP (UDP)
const result = await NetScan.scanOpenUDPPorts(IP,timeout);
```

This project use stealthcopter AndroidNetworkTools, for more information https://github.com/stealthcopter/AndroidNetworkTools
