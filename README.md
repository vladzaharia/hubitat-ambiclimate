# smartthings-ambiclimate
SmartThings integration for Ambi Climate

## SmartThings Installation
This integration capability is implemented as a combination of SmartApp and Device Handler. It is installed through the [SmartThings IDE](http://graph.api.smartthings.com/).
* Log into the SmartThings IDE using your SmartThings account credentials
### SmartApp
* Goto "My SmartApps"
* Click on Settings and add the respository with Owner "alisdairjsmyth" and name of "smartthings-ambiclimate" and branch of master and then click save
* Click "Update from Repo" and select "smartthings-ambiclimate"
* A new row should appear in the New section.  Check it, check Publish at the bottom and click "Execute Update"
* Click on the SmartApp in the list and then click "App Settings"
* Scoll down to the Settings and enter your Ambi Climate account credentials in the settings "email" and "password"
* Press "Update" button
### Device Handler
* Goto "My Device Handlers"
* Click on Settings and add the respository with Owner "alisdairjsmyth" and name of "smartthings-ambiclimate" and branch of master and then click save
* Click "Update from Repo" and select "smartthings-ambiclimate"
* A new row should appear in the New section.  Check it, check Publish at the bottom and click "Execute Update"

## SmartThings Mobile App
* In the SmartThings App, goto "Marketplace" and select "SmartApps".  
* At the bottom of the list, select "My Apps".
* Select "Ambi Climate" from the list
* The SmartApp performs a device discovery.  Press "Tap to Select" to choose the devices at add to SmartThings.  Press Done
* Press Done.

Your Ambi Climate devices will be added to your list of "Things", and the Ambi Climate is thereafter accessible from "Automation" tab within the SmartThings App.

## Notes
This capability uses an unofficial API (see [debug/ampipy](https://github.com/debug/ambipy)) which may stop working at any point.  At the moment, this capability only exposes the current Temperature and Relative Humidity.  It does not allow any update of Ambi Climate or air conditioner settings.
