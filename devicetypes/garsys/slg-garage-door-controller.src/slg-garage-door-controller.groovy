/**
 *  SLG Garage Door Controller
 *
 *  Copyright 2018 Scott Garver
 */
metadata {
	definition (name: "SLG Garage Door Controller", namespace: "GarSys", author: "Scott Garver") {
		capability "Switch"
		capability "Door Control"
		capability "Contact Sensor"
		capability "Refresh"
		capability "Health Check"
	}
	tiles {
		standardTile("toggle", "device.door", width: 2, height: 2) {
			state("closed", label:'${name}', action:"door control.open", icon:"st.doors.garage.garage-closed", backgroundColor:"#00A0DC", nextState:"opening")
			state("open", label:'${name}', action:"door control.close", icon:"st.doors.garage.garage-open", backgroundColor:"#e86d13", nextState:"closing")
			state("opening", label:'${name}', icon:"st.doors.garage.garage-closed", backgroundColor:"#e86d13")
			state("closing", label:'${name}', icon:"st.doors.garage.garage-open", backgroundColor:"#00A0DC")
		}
		standardTile("open", "device.door", inactiveLabel: false, decoration: "flat") {
			state "default", label:'open', action:"door control.open", icon:"st.doors.garage.garage-opening"
		}
		standardTile("close", "device.door", inactiveLabel: false, decoration: "flat") {
			state "default", label:'close', action:"door control.close", icon:"st.doors.garage.garage-closing"
		}
		main "toggle"
		details(["toggle", "open", "close"])
	}
}
def parse(String description) {
	log.trace "parse($description)"
}
def open() {
	sendEvent(name: "door", value: "opening")
    runIn(1, finishOpening)
}
def close() {
    sendEvent(name: "door", value: "closing")
	runIn(1, finishClosing)
}
def finishOpening() {
    sendEvent(name: "door", value: "open")
    sendEvent(name: "contact", value: "open")
}
def finishClosing() {
    sendEvent(name: "door", value: "closed")
    sendEvent(name: "contact", value: "closed")
}
def installed() {
	log.trace "Executing 'installed'"
	initialize()
}
def updated() {
	log.trace "Executing 'updated'"
	initialize()
}
private initialize() {
	log.trace "Executing 'initialize'"
	sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
	sendEvent(name: "healthStatus", value: "online")
	sendEvent(name: "DeviceWatch-Enroll", value: [protocol: "cloud", scheme:"untracked"].encodeAsJson(), displayed: false)
	sendEvent(name: "checkInterval", value: 2 * 30 * 60 + 2 * 60, displayed: false, data: '${name}')
}