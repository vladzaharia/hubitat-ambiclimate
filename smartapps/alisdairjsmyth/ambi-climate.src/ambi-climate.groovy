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
	appSetting "email"
    appSetting "password"
	appSetting "bearerToken"
}

preferences {
    page(name:"deviceDiscovery", title:"Device Selection", content:"deviceDiscovery")
}

private deviceDiscovery() {
	def options = deviceOptions() ?: []
    def count   = options.size()

    return dynamicPage(name:"deviceDiscovery", title:"Device Selection", nextPage:"", refreshInterval: refreshInterval, install:true, uninstall: true) {
        section("Select Ambi Climate devices to add...") {
            input "selectedDevices", "enum", required:true, title:"Blinds (${count} found)", multiple:true, options:options
        }
    }
}

Map deviceOptions() {
	def options = [:]
    def devices = getDevices()
    devices.data.devices.each { device ->
    	options[device.mac] = device.room_name
    }
    log.debug("Options: ${options}")
    return options
}

def getDevices() {
    def response
    Map params

	// Authenticate
    params = [
   		uri: "https://rest.ambiclimate.com",
       	path: "/UserCredential",
        query: [
   	    	email: appSettings.email,
       	    pwd: appSettings.password
        ]
   	]
    
   	try {
   		httpGet(params) { resp ->
       		log.debug("Authentication Response: ${resp.data}")

            response = resp
   	    }
    } catch (groovyx.net.http.HttpResponseException e) {
		throw e
    }
    
    // Retrieve List of Ambi Climate Devices
    params = [
    	uri: "https://rest.ambiclimate.com",
        path: "/User",
        query: [
        	expand: "appliance%2Cdevice",
            user_id: resp.data.user_id
        ],
        headers: [
        	Authorization: "Bearer ${resp.data.token_id}"
        ]
    ]
    
    try {
    	httpGet(params) { resp ->
        	log.debug("Retrieve List Response: ${resp.data}")
            return resp
        }
    } catch (groovyx.net.http.HttpResponseException e) {
		throw e
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
    updateDevices()
}

def uninstalled() {
	// Delete Existing Child Device

}

def updateDevices() {
	if (!state.devices) {
    	state.devices = [:]
    }
    def selectors = []
    

}