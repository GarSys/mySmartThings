metadata {
    definition(name: "SLG Virtual Door Control", namespace: "GarSys", author: "Scott Garver") {
		capability "Door Control"
        capability "Switch"
    }
	tiles {
		multiAttributeTile(name:"Door", type: "generic", width: 6, height: 4, canChangeIcon: true){
			tileAttribute ("device.door", key: "PRIMARY_CONTROL"){
				attributeState "closed", label: '${name}', action: "door control.open", icon: "st.doors.garage.garage-closed", backgroundColor: "#ffffff", nextstate:"open"
				attributeState "open", label: '${name}', action: "door control.close", icon: "st.doors.garage.garage-open", backgroundColor: "#79b821", nextstate:"closed"
			}
		}
		main "Door"
		details(["Door","open","close"])
	}
}
def open(){
	log.debug "Open"
	sendEvent(name: "Door", value: "open");
}

def close(){
	log.debug "Close"
	sendEvent(name: "Door", value: "close");
}