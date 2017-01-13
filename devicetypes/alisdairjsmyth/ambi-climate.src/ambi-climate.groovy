/**
 *  Ambi Climate
 *
 *  Copyright 2017 Alisdair Smyth
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "Ambi Climate", namespace: "alisdairjsmyth", author: "Alisdair Smyth") {
		capability "Temperature Measurement"
        capability "Relative Humidity Measurement"
	}


	simulator {
		// TODO: define status and reply messages here
	}

	tiles(scale: 2) {
    	multiAttributeTile(name:"thermostatFull", type:"thermostat", width:6, height:4) {
			tileAttribute("device.temperature", key: "PRIMARY_CONTROL") {
				attributeState("temp", label:'${currentValue}°', unit:"dC", defaultState: true)
			}
			tileAttribute("device.humidity", key: "SECONDARY_CONTROL") {
				attributeState("humidity", label:'${currentValue}%', unit:"%", defaultState: true)
			}
		}
	}
}

def poll() {
    results = parent.getDeviceState()
    generateEvent(results)
}

def generateEvent(Map results) {
  	results.each { name, value ->
    	sendEvent(name: name, value: value)
  	}
 	return null
}