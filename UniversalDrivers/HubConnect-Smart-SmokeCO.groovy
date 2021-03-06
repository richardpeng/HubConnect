/*
 *	Copyright 2019 Steve White
 *
 *	Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *	use this file except in compliance with the License. You may obtain a copy
 *	of the License at:
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *	WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *	License for the specific language governing permissions and limitations
 *	under the License.
 *
 *
 */
metadata 
{
	definition(name: "HubConnect Smart Smoke/CO", namespace: "shackrat", author: "Steve White", importUrl: "https://raw.githubusercontent.com/HubitatCommunity/HubConnect/master/UniversalDrivers/HubConnect-Smart-SmokeCO.groovy")
	{
		capability "Smoke Detector"
		capability "Carbon Monoxide Detector"
		capability "Relative Humidity Measurement"
		capability "Battery"
		capability "Switch"
		capability "Switch Level"
		capability "Color Control"
		capability "Refresh"

		attribute "pressure", "string"
		attribute "version", "string"
		
		command "sync"
	}
}


/*
	installed
    
	Doesn't do much other than call initialize().
*/
def installed()
{
	initialize()
}


/*
	updated
    
	Doesn't do much other than call initialize().
*/
def updated()
{
	initialize()
}


/*
	initialize
    
	Doesn't do much other than call refresh().
*/
def initialize()
{
	refresh()
}


/*
	parse
    
	In a virtual world this should never be called.
*/
def parse(String description)
{
	log.trace "Msg: Description is $description"
}


/*
	on
    
	Turns the device on.
*/
def on()
{
	// The server will update on/off status
	parent.sendDeviceEvent(device.deviceNetworkId, "on")
}


/*
	off
    
	Turns the device off.
*/
def off()
{
	// The server will update on/off status
	parent.sendDeviceEvent(device.deviceNetworkId, "off")
}


/*
	setLevel
    
	Sets the level to <level> over duration <duration>.
*/
def setLevel(value, duration=1)
{
	// The server will update status
	parent.sendDeviceEvent(device.deviceNetworkId, "setLevel", [value, duration])
}


/*
	setColor
    
	Sets color by setting both hue and saturation.
*/
def setColor(value)
{
	if (value.hue == null || value.saturation == null) return

	// The server will update status
	parent.sendDeviceEvent(device.deviceNetworkId, "setColor", [hue: value.hue, saturation: value.saturation, level: value?.level])
}


/*
	setHue
    
	Sets the Hue.
*/
def setHue(value)
{
	// The server will update status
	parent.sendDeviceEvent(device.deviceNetworkId, "setHue", [value])
}


/*
	setSaturation
    
	Sets the Saturation.
*/
def setSaturation(value)
{
	// The server will update status
	parent.sendDeviceEvent(device.deviceNetworkId, "setSaturation", [value])
}


/*
	refresh
    
	Refreshes the device by requesting an update from the client hub.
*/
def refresh()
{
	// The server will update status
	parent.sendDeviceEvent(device.deviceNetworkId, "refresh")
}


/*
	sync
    
	Synchronizes the device details with the parent.
*/
def sync()
{
	// The server will respond with updated status and details
	parent.syncDevice(device.deviceNetworkId, "smartsmoke")
	sendEvent([name: "version", value: "v${driverVersion.major}.${driverVersion.minor}.${driverVersion.build}"])
}
def getDriverVersion() {[platform: "Universal", major: 1, minor: 2, build: 1]}