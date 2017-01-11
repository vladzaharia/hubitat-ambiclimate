/**
 *  Ambi Climate
 *
 *  Copyright 2017 Alisdair Smyth
 *
 */
definition(
    name: "Ambi Climate",
    namespace: "alisdairjsmyth",
    author: "Alisdair Smyth",
    description: "SmartApp supporting control of Ambi Climate devices within SmartThings",
    category: "SmartThings Labs",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    singleInstance: false)
{
	appSetting "bearerToken"
}


preferences {
	section("Parameters") {
    	input (name: "mac", type: "text", title: "Device MAC")
        input (name: "roomName", type: "text", title: "Room Name")
        input (name: "locationName", type: "text", title: "Location Name")
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.

	// Delete Existing Child Device
    def devices = getChildDevices()
    devices.each {
    	deleteChildDevice(it.deviceNetworkId)
    }
    
    // Retrieve Current Values
    def resp = getDeviceState()
    if (resp.status == 200) {
    	def data = [
        	name: settings.roomName,
            temperature: resp.data.value
        ]
        log.debug "Device data: ${data}"

        device = addChildDevice("alisdairjsmyth", "Ambi Climate", settings.mac, null, data)
    }
    
    // Schedule Every 10 Minutes
	runEvery10Minutes(poll)
}

def uninstalled() {
	// Delete Existing Child Device
    def devices = getChildDevices()
    devices.each {
    	deleteChildDevice(it.deviceNetworkId)
    }
}

def getDeviceState() {
	def params = [
    	uri: "https://api.ambiclimate.com/api/v1",
        path: "/device/sensor/temperature",
        query: [
        	access_token: appSettings.bearerToken,
            room_name: settings.roomName,
            location_name: settings.locationName
        ]
    ]
    
    try {
	    httpGet(params) {resp ->
        	return resp;
        }
   	} catch (groovyx.net.http.HttpResponseException e) {
		return e.response
    }
    
}

// TODO: implement event handlers