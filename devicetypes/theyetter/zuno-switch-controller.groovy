/**
 
 */
metadata {
	definition (name: "Z-Uno Switch Controller", namespace: "smartthings", author: "Ryan Yetter") { 
		capability "Switch"
		capability "Refresh"
        capability "Temperature Measurement"
		//capability "Actuator"
		capability "Sensor"
		capability "Configuration"
        capability "Battery"
		capability "Health Check"
		capability "Power Source"
        
        //command "associationSet"
        //command "createChildDevices"

		/*//fingerprint manufacturer: "015D", prod: "0651", model: "F51C", deviceJoinName: "Zooz ZEN 20 Power Strip"*/
        //zw:L type:1001 mfr:0115 prod:0110 model:0001 ver:2.14 zwv:4.38 lib:03 cc:5E,20,25,70,85,8E,60,7A,5A,59,73,86,72 role:05 ff:8700 ui:8700 ep:['1001 25', '1001 25', '1001 25', '1001 25']
		//fingerprint mfr: "0115", prod: "0110", model: "0001", cc: "5E,20,25,70,85,8E,60,7A,5A,59,73,86,72"
        
        
        fingerprint mfr: "0115", prod: "0110", model: "0001"
	} 

	tiles {
    
		multiAttributeTile(name:"switch", type: "lighting", width: 1, height: 1, canChangeIcon: true){
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "off", label: '${name}', action: "", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState:"on"
				attributeState "on", label: '${name}', action: "", icon: "st.switches.switch.on", backgroundColor: "#00A0DC", nextState:"off"
				//attributeState "turningOn", label:'${name}', action:"switch.off", icon:"st.switches.switch.on", backgroundColor:"#00A0DC", nextState:"turningOff"
				//attributeState "turningOff", label:'${name}', action:"switch.on", icon:"st.switches.switch.off", backgroundColor:"#ffffff", nextState:"turningOn"
			}
		}
        
        childDeviceTile("Fireplace ON","ch1")
        childDeviceTile("Fireplace OFF","ch2")
        childDeviceTile("Temp1","ch3")
        childDeviceTile("Flame","ch4")
       /* childDeviceTile("Temperature Sensor 1","ch3", childTileName: "temperature") /*, width: 6, height: 1) {
			state "temperature", label: '${currentValue}°',
				backgroundColors: [
					[value: 32, color: "#153591"],
					[value: 44, color: "#1e9cbb"],
					[value: 59, color: "#90d2a7"],
					[value: 74, color: "#44b621"],
					[value: 84, color: "#f1d801"],
					[value: 92, color: "#d04e00"],
					[value: 98, color: "#bc2323"]
				]
		}
        */
        /*
        valueTile("temperature", "device.temperature", inactiveLabel: false, width: 6, height: 1) {
			state "temperature", label: '${currentValue}°',
				backgroundColors: [
					[value: 32, color: "#153591"],
					[value: 44, color: "#1e9cbb"],
					[value: 59, color: "#90d2a7"],
					[value: 74, color: "#44b621"],
					[value: 84, color: "#f1d801"],
					[value: 92, color: "#d04e00"],
					[value: 98, color: "#bc2323"]
				]
		}
        */
        
        /*
        childDeviceTile("Channel 3","ch3")
        childDeviceTile("Channel 4","ch4")
        */
		//childDeviceTiles("switches")
		standardTile("refresh", "device.switch", width: 1, height: 1, inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
		}
	}
}


/////////////////////////////
// Installation and update //
/////////////////////////////
def installed() {
    log.trace "calling installed()"
	def childList = getChildDevices()
    log.trace "childList: ${childList}"
    
	//associationSet()
	createChildDevices()
    refresh()
    command(zwave.multiChannelV3.multiChannelEndPointGet())
}




def updated() {
	log.trace "callling updated()"
	def childList = getChildDevices()
    log.trace "childList: ${childList}"
   
   /*
	if (!childDevices) {
		createChildDevices()
	}
	else if (device.label != state.oldLabel) {
		childDevices.each {
			def newLabel = "${device.displayName} (CH${channelNumber(it.deviceNetworkId)})"
			it.setLabel(newLabel)
		}
		state.oldLabel = device.label
	}
    */
    refresh()
}

def configure() {
    log.trace "calling configure()"

    
    def assocCmds = []
// WOrks but badly
/*
    assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationRemove(groupingIdentifier: 1, nodeId:[zwaveHubNodeId]).format()
	assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 1, nodeId: [zwaveHubNodeId, 0]).format()
    assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier: 1).format()
	assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationRemove(groupingIdentifier: 1, nodeId:[zwaveHubNodeId]).format()
	assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 1, nodeId: [zwaveHubNodeId, 0, zwaveHubNodeId, 1, zwaveHubNodeId, 2, zwaveHubNodeId, 3, zwaveHubNodeId, 4]).format()
    assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier: 1).format()
*/
/*
	assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationRemove(groupingIdentifier: 1, nodeId: []).format()
	assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 1, nodeId: [zwaveHubNodeId, 0]).format()
    assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier: 1).format()
*/
//RNY - instead of 2, 1, 2 - just 2, 1?
	//assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationRemove(groupingIdentifier: 2, nodeId:[zwaveHubNodeId]).format()
	//assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 2, nodeId: [1, 0, 1, 1]).format()
    //assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier: 2).format()

	//assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationRemove(groupingIdentifier: 3, nodeId:[zwaveHubNodeId]).format()
	//assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 3, nodeId: [1, 0, 1, 2]).format()
    //assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier: 3).format()
    
    //assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationRemove(groupingIdentifier: 4, nodeId:[zwaveHubNodeId]).format()
	//assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 4, nodeId: [1, 0, 1, 3]).format()
    //assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier: 4).format()
    
    //assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationRemove(groupingIdentifier: 5, nodeId:[zwaveHubNodeId]).format()
	//assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 5, nodeId: [1, 0, 1, 4]).format()
    //assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier: 5).format()
    
    //assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationRemove(groupingIdentifier: 1, nodeId: []).format()
	
	//assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 1, nodeId: [zwaveHubNodeId, 0]).format()
    //assocCmds << "8E0101000100"
    //assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier: 1).format()
    
    //assocCmds << encap(zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier:3, nodeId:[zwaveHubNodeId]), 0)
    //assocCmds << encap(zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier:2, nodeId:[zwaveHubNodeId]), 0)
    

/*
    assocCmds << encap(zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier:1, nodeId:[0, zwaveHubNodeId, 1]), 1)
    assocCmds << encap(zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier:1, nodeId:[0, zwaveHubNodeId, 1]), 2)
    assocCmds << encap(zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier:1, nodeId:[0, zwaveHubNodeId, 1]), 3)
    assocCmds << encap(zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier:1, nodeId:[0, zwaveHubNodeId, 1]), 4)
*/
	assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 1, nodeId:[0, zwaveHubNodeId, 1]).format()
    assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier: 1).format()
    
    assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 2, nodeId:[0, zwaveHubNodeId, 2]).format()
    assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier: 2).format()
    
    assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 3, nodeId:[0, zwaveHubNodeId, 3]).format()
    assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier: 3).format()
    
    assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 4, nodeId:[0, zwaveHubNodeId, 4]).format()
    assocCmds << zwave.multiChannelAssociationV2.multiChannelAssociationGet(groupingIdentifier: 4).format()

    
    trace "assocCmds: ${assocCmds}"
	return delayBetween(assocCmds, 1000)
    
    
	refresh()
}


//////////////////////
// Event Generation //
//////////////////////
def parse(String description) {
	trace "z-uno switch controller parse('${description}')"
	def result = []
	if (description.startsWith("Err")) {
		result = createEvent(descriptionText:description, isStateChange:true)
	} else if (description != "updated") {
    	//trace "special: ${description.payload}"
    	//[0x20: 1, 0x25: 1, 0x86: 1, 0x30: 1, 0x31: 1, 0x72: 1, 0x71: 1]
        //[0x60: 3, 0x32: 3, 0x25: 1, 0x20: 1]
		def cmd = zwave.parse(description, [0x60: 3, 0x32: 3, 0x20: 1, 0x20: 1, 0x25: 1, 0x86: 1, 0x30: 1, 0x31: 5, 0x72: 1, 0x71: 3, 0x8E: 2, 0x85: 2])
        trace "new cmd: ${cmd}"
		if (cmd) {
			//result += zwaveEvent(cmd, 1)
            trace "cmd.CMD = ${cmd.CMD}"
            if(cmd.hasProperty("CMD") && (cmd.CMD == "600D" || cmd.CMD == "8E03" || cmd.CMD == "2000")) {
            	result += zwaveEvent(cmd)
                log.warn "Smart event: ${cmd}"
            } else if (cmd.hasProperty("CMD") && cmd.hasProperty('sourceEndPoint') && cmd.sourceEndPoint > 0) {
            	result += zwave_event(cmd, cmd.sourceEndPoint)
                log.warn "Smart event: ${cmd}, ${cmd.sourceEndpoint}"
            } else {
            	log.debug "Stupid event: ${cmd}"
                result += zwaveEvent(cmd, 1)
            }
		}
		else {
			log.warn "Unparsed description $description"
		}
	}
	result
}

def zwaveEvent(physicalgraph.zwave.commands.alarmv2.AlarmReport cmd, ep = null) {
	trace "zwaveEvent(physicalgraph.zwave.commands.alarmv2.AlarmReport ${cmd}, ${ep})"
    def map = [zwaveAlarmType: cmd.zwaveAlarmType, alarmLevel: cmd.alarmLevel, zwaveAlarmStatus: cmd.zwaveAlarmStatus, zwaveAlarmEvent: cmd.zwaveAlarmEvent, alarmType: cmd.alarmType, eventParameter: cmd.eventParameter, numberOfEventParameters: cmd.numberOfEventParameters ]
    zwaveFlameEvent(cmd, ep, map)
}

def zwaveEvent(physicalgraph.zwave.commands.notificationv3.NotificationReport cmd, ep = null) {
	trace "zwaveEvent(physicalgraph.zwave.commands.notificationv3.NotificationReport ${cmd}, ${ep})"
    //zwaveEvent(physicalgraph.zwave.commands.notificationv3.NotificationReport NotificationReport(eventParametersLength: 0, eventParameter: [], zensorNetSourceNodeId: 0, v1AlarmType: 0, reserved61: 0, notificationStatus: 255, sequence: false, event: 2, notificationType: 7, v1AlarmLevel: 0), 0)
    def map = [zwaveAlarmType: cmd.v1AlarmType, alarmLevel: cmd.v1AlarmLevel, zwaveAlarmStatus: cmd.notificationStatus, zwaveAlarmEvent: cmd.event, alarmType: cmd.v1AlarmType, eventParameter: cmd.eventParameter, numberOfEventParameters: cmd.eventParametersLength]
    trace "calling zwaveFlameEvent"
    zwaveFlameEvent(cmd, ep, map)
    trace "after zwaveFlameEvent"
}

// Devices that support the Security command class can send messages in an
// encrypted form; they arrive wrapped in a SecurityMessageEncapsulation
// command and must be unencapsulated
def zwaveEvent(physicalgraph.zwave.commands.securityv1.SecurityMessageEncapsulation cmd) {
	trace "zwaveEvent(physicalgraph.zwave.commands.securityv1.SecurityMessageEncapsulation ${cmd})"
        def encapsulatedCommand = cmd.encapsulatedCommand([0x98: 1, 0x20: 1])

        // can specify command class versions here like in zwave.parse
        if (encapsulatedCommand) {
                return zwaveEvent(encapsulatedCommand)
        }
}

// EVENTS
def zwaveEvent(physicalgraph.zwave.commands.multichannelv3.MultiChannelEndPointReport cmd, ep = null) {
    trace "physicalgraph.zwave.commands.multichannelv3.MultiChannelEndPointReport ${cmd}"
	def epc = cmd.endPoints
	def cmds = []
	state.epc = epc
	
	for (i in 1..epc) { 
		cmds << command(zwave.multiChannelV3.multiChannelCapabilityGet(endPoint: i))
        trace "command(zwave.multiChannelV3.multiChannelCapabilityGet(endPoint: ${i}))"
  	}
    /*
    //def cmds = []
	cmds << response(zwave.associationV1.associationRemove(groupingIdentifier:1).format())
	//cmds << "delay 500"
	cmds << response(zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 1, nodeId: [0,zwaveHubNodeId,1]).format())
	////return cmds
	*/
	[response(cmds)]
    
}

def zwaveEvent(physicalgraph.zwave.commands.multichannelv3.MultiChannelCapabilityReport cmd, ep = null) {
	trace "zwaveEvent(physicalgraph.zwave.commands.multichannelv3.MultiChannelCapabilityReport ${cmd})"
    /*
    def cmds = []
	cmds << response(zwave.associationV1.associationRemove(groupingIdentifier:1).format())
	//cmds << "delay 500"
	cmds << response(zwave.multiChannelAssociationV2.multiChannelAssociationSet(groupingIdentifier: 1, nodeId: [0,zwaveHubNodeId,1]).format())
	return cmds
    */
    
    /*
    def cc = cmd.commandClass
	def ep = cmd.endPoint
	def needCreate = null
	
	if (!childDevices.find{ it.deviceNetworkId.endsWith("-ep${ep}") || !childDevices}) {
		createChildDevices(cc, ep)
	}
    */
}

def zwaveEvent(physicalgraph.zwave.commands.multichannelv3.MultiChannelCmdEncap cmd, ep = null) {
	trace "zwaveEvent(physicalgraph.zwave.commands.multichannelv3.MultiChannelCmdEncap ${cmd}, ${ep})"
	def encapsulatedCommand = cmd.encapsulatedCommand([0x32: 3, 0x20: 1, 0x20: 1, 0x25: 1, 0x86: 1, 0x30: 1, 0x31: 5, 0x72: 1, 0x71: 3, 0x8E: 2, 0x85: 2])
	if (encapsulatedCommand) {
		zwaveEvent(encapsulatedCommand, cmd.sourceEndPoint as Integer)
	}
}

def zwaveEvent(physicalgraph.zwave.commands.multichannelv3.MultiInstanceCmdEncap cmd, ep = null) {
	trace "zwaveEvent(physicalgraph.zwave.commands.multichannelv3.MultiInstanceCmdEncap cmd)"
    def encapsulatedCommand = cmd.encapsulatedCommand([0x32: 3, 0x20: 1, 0x20: 1, 0x25: 1, 0x86: 1, 0x30: 1, 0x31: 5, 0x72: 1, 0x71: 3, 0x8E: 2, 0x85: 2])

    // can specify command class versions here like in zwave.parse
    log.debug ("Command from instance ${cmd.instance}: ${encapsulatedCommand}")

    if (encapsulatedCommand) {
        return zwaveEvent(encapsulatedCommand)
    }
}

def zwaveEvent(physicalgraph.zwave.commands.multichannelassociationv2.MultiChannelAssociationReport cmd, ep = null) {
	trace "zwaveEvent(physicalgraph.zwave.commands.multichannelassociationv2.MultiChannelAssociationReport ${cmd}, ${ep})"
}

def zwaveEvent(physicalgraph.zwave.commands.associationv2.AssociationReport cmd, ep = null) {
	trace "zwaveEvent(physicalgraph.zwave.commands.associationv2.AssociationReport ${cmd}, ${ep})"
    /*
    def result = []
    if (cmd.nodeId.any { it == zwaveHubNodeId }) {
        result << createEvent(descriptionText: "$device.displayName is associated in group ${cmd.groupingIdentifier}")
    } else if (cmd.groupingIdentifier == 1) {
        // We're not associated properly to group 1, set association
        result << createEvent(descriptionText: "Associating $device.displayName in group ${cmd.groupingIdentifier}")
        result << response(zwave.associationV1.associationSet(groupingIdentifier:cmd.groupingIdentifier, nodeId:zwaveHubNodeId))
    }
    result
    */    
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicReport cmd, endpoint = null) {
	trace "zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicReport ${cmd}, ${endpoint})"
	zwaveBinaryEvent(cmd, endpoint)
}

def zwaveEvent(physicalgraph.zwave.commands.switchbinaryv1.SwitchBinaryReport cmd, endpoint = null) {
	trace "zwaveEvent(physicalgraph.zwave.commands.switchbinaryv1.SwitchBinaryReport ${cmd}, ${endpoint})"
	zwaveBinaryEvent(cmd, endpoint)
}

def zwaveEvent(physicalgraph.zwave.commands.sensorbinaryv1.SensorBinaryReport cmd, endpoint = null)
{
	trace "zwaveEvent(physicalgraph.zwave.commands.sensorbinaryv1.SensorBinaryReport ${cmd}, ${endpoint})"
        // Version 1 of SensorBinary doesn't have a sensor type
        //createEvent(name:"sensor", value: cmd.sensorValue ? "active" : "inactive")
    zwaveSensorEvent(cmd, endpoint)
}

def zwaveEvent(physicalgraph.zwave.commands.sensormultilevelv5.SensorMultilevelReport cmd, endpoint = null)
{
	trace "zwaveEvent(physicalgraph.zwave.commands.sensormultilevelv5.SensorMultilevelReport ${cmd}, ${endpoint})"
        def map = [ displayed: true, value: cmd.scaledSensorValue.toString() ]
        switch (cmd.sensorType) {
                case 1:
                		map.name = "temperature"
                        def cmdScale = cmd.scale == 1 ? "F" : "C"
                        map.value = convertTemperatureIfNeeded(cmd.scaledSensorValue, cmdScale, cmd.precision)
                        map.unit = getTemperatureScale()
                        break
                case 2:
                        map.name = "value"
                        map.unit = cmd.scale == 1 ? "%" : ""
                        break;
                case 3:
                        map.name = "illuminance"
                        map.value = cmd.scaledSensorValue.toInteger().toString()
                        map.unit = "lux"
                        break;
                case 4:
                        map.name = "power"
                        map.unit = cmd.scale == 1 ? "Btu/h" : "W"
                        break;
                case 5:
                        map.name = "humidity"
                        map.value = cmd.scaledSensorValue.toInteger().toString()
                        map.unit = cmd.scale == 0 ? "%" : ""
                        break;
                case 6:
                        map.name = "velocity"
                        map.unit = cmd.scale == 1 ? "mph" : "m/s"
                        break;
                case 8:
                case 9:
                        map.name = "pressure"
                        map.unit = cmd.scale == 1 ? "inHg" : "kPa"
                        break;
                case 0xE:
                        map.name = "weight"
                        map.unit = cmd.scale == 1 ? "lbs" : "kg"
                        break;
                case 0xF:
                        map.name = "voltage"
                        map.unit = cmd.scale == 1 ? "mV" : "V"
                        break;
                case 0x10:
                        map.name = "current"
                        map.unit = cmd.scale == 1 ? "mA" : "A"
                        break;
                case 0x12:
                        map.name = "air flow"
                        map.unit = cmd.scale == 1 ? "cfm" : "m^3/h"
                        break;
                case 0x1E:
                        map.name = "loudness"
                        map.unit = cmd.scale == 1 ? "dBA" : "dB"
                        break;
        }
        //createEvent(map)
        //endpoint = 3
        trace "SensorMultiLevelReport: cmd: ${cmd} - map: ${map} - ${endpoint}"
        zwaveTempEvent(cmd, endpoint, map)
}

def zwaveSensorEvent(cmd, endpoint) {
	trace "zwaveSensorEvent(${cmd}, ${endpoint})"
    //createEvent(name:"sensor", value: cmd.sensorValue ? "active" : "inactive")
    def children = childDevices
	def childDevice = children.find{it.deviceNetworkId.endsWith("$endpoint")}
	if (childDevice) {
		childDevice.sendEvent(name:"sensor", value: cmd.sensorValue ? "active" : "inactive")
    }
    //createEvent(name: "temp2", value: "112")
}

def zwaveFlameEvent(cmd, endpoint, map = null) {
    trace "zwaveFlameEvent(${cmd}, ${map}, ${endpoint})"
    def result = []
    def newMap = [ name: "switch", value: map.numberOfEventParameters ? "on" : "off", data: map ]
    //def lval = map.numberOfEventParameters?"triggered":"idle"
    //scale: 0, sensorValue: [128, 1], precision: 2, sensorType: 1, scaledSensorValue: -327.67, size: 2), [displayed:true, value:-557.81, name:temperature, unit:F]
	//createEvent(map)
    def children = childDevices
	def childDevice = children.find{it.deviceNetworkId.endsWith("$endpoint")}
	if (childDevice) {
    	trace "zwaveFlameEvent - childDevice: ${childDevice}"
    	trace "zwaveFlameEvent - sending to childDevice: ${childDevice} with value: ${newMap.value}"
    	//[ newMap.name = "Flame", value = map.numberOfEventParameters, data = map ]
        
		childDevice.sendEvent(newMap)
        //childDevice.createEvent(map)
    }
}

def zwaveTempEvent(cmd, endpoint, map = null) {
    trace "zwaveTempEvent(${cmd}, ${map}, ${endpoint})"
    def result = []
    //scale: 0, sensorValue: [128, 1], precision: 2, sensorType: 1, scaledSensorValue: -327.67, size: 2), [displayed:true, value:-557.81, name:temperature, unit:F]
	//createEvent(map)
    def children = childDevices
	def childDevice = children.find{it.deviceNetworkId.endsWith("$endpoint")}
	if (childDevice) {
		childDevice.sendEvent(map)
        
    }
}

def zwaveBinaryEvent(cmd, endpoint) {
    trace "zwaveBinaryEvent(${cmd}, ${endpoint})"
	def result = []
	def children = childDevices
	def childDevice = children.find{it.deviceNetworkId.endsWith("$endpoint")}
	if (childDevice) {
		childDevice.sendEvent(name: "switch", value: cmd.value ? "on" : "off")

		if (cmd.value) {
			// One on and the strip is on
			result << createEvent(name: "switch", value: "on")
		} else {
			// All off and the strip is off
			if (!children.any { it.currentValue("switch") == "on" }) {
				result << createEvent(name: "switch", value: "off")
			}
		}
	}
	result
}

def zwaveEvent(physicalgraph.zwave.commands.manufacturerspecificv2.ManufacturerSpecificReport cmd, ep = null) {
	trace "zwaveEvent(physicalgraph.zwave.commands.manufacturerspecificv2.ManufacturerSpecificReport ${cmd}, ${ep})"
	updateDataValue("MSR", String.format("%04X-%04X-%04X", cmd.manufacturerId, cmd.productTypeId, cmd.productId))
	return null
}

def zwaveEvent(physicalgraph.zwave.commands.versionv1.VersionReport cmd, ep = null) {
	trace "applicationVersion $cmd.applicationVersion"
}

def zwaveEvent(physicalgraph.zwave.Command cmd, ep = null) {
	log.warn("${device.displayName}: Unhandled ${cmd}" + (ep ? " from endpoint $ep" : ""))
}

/////////////////////////////
// Installation and update //
/////////////////////////////
def on() {
	def cmds = []
	def cmd = zwave.switchBinaryV1.switchBinarySet(switchValue: 0xFF)
	cmds << zwave.multiChannelV3.multiChannelCmdEncap(bitAddress: true, destinationEndPoint:0x1F).encapsulate(cmd).format()
	//cmds << "delay 200"
	//cmds.addAll(refresh())
	return cmds
}

def off() {
	def cmds = []
	def cmd = zwave.switchBinaryV1.switchBinarySet(switchValue: 0x00)
	cmds << zwave.multiChannelV3.multiChannelCmdEncap(bitAddress: true, destinationEndPoint:0x1F).encapsulate(cmd).format()
	//cmds << "delay 200"
	//cmds.addAll(refresh())
	return cmds
}

//////////////////////
// Child Device API //
//////////////////////
void childOn(String dni) {
	trace "childOn - dni: ${dni}"
	onOffCmd(0xFF, channelNumber(dni))
}

void childOff(String dni) {
	onOffCmd(0, channelNumber(dni))
}

def refresh() {
	def cmds = (1..2).collect { endpoint ->
		encap(zwave.switchBinaryV1.switchBinaryGet(), endpoint)
	}
    cmds << encap(zwave.sensorMultilevelV5.sensorMultilevelGet(sensorType:1, scale:1), 3) 
    cmds << encap(zwave.alarmV2.alarmGet(), 4)
    cmds << encap(zwave.notificationV3.notificationGet(), 4)
	delayBetween(cmds, 100)
}

///////////////////
// Local Methods //
///////////////////
private channelNumber(String dni) {
	dni.split("-ep")[-1] as Integer
}

private void onOffCmd(value, endpoint = null) {

    sendHubCommand(new physicalgraph.device.HubAction(encap(zwave.basicV1.basicSet(value: value), endpoint)))
    
    def actionsAfter = [
    	new physicalgraph.device.HubAction(encap(zwave.switchBinaryV1.switchBinaryGet(), endpoint)),
        new physicalgraph.device.HubAction(encap(zwave.switchBinaryV1.switchBinaryGet(), endpoint)),
    ]
    sendHubCommand(actionsAfter, 2500)

}

private void createChildDevices() {
	state.oldLabel = device.label
	/*
    for (i in 1..4) {
    	log.warn("adding children: network id: ${device.deviceNetworkId} for-count: ${i} displayName: ${device.displayName}");
		addChildDevice("Child Switch", "${device.deviceNetworkId}-ep${i}", null,
				[completedSetup: true, label: "${device.displayName} (CH${i})",
				 isComponent: true, componentName: "ch$i", componentLabel: "ChannelA $i"])
	}
    */
    
    getChildDevices().each { device ->
        try{
            trace "Attempting deleteChildDevice child ${device.displayName}"
            deleteChildDevice(device.deviceNetworkId)
        }
        catch (e) {
            log.debug "Error deleting ${device.deviceNetworkId}: ${e}"
        }
    } 
    
    log.debug("adding children: network id: ${device.deviceNetworkId} for-count: 1 displayName: ${device.displayName}");
    addChildDevice("Child Switch", "${device.deviceNetworkId}-ep1", null,
                   [completedSetup: true, label: "${device.displayName} Fireplace ON (CH1)",
                    isComponent: true, componentName: "ch1", componentLabel: "Fireplace ON"])
    log.debug("adding children: network id: ${device.deviceNetworkId} for-count: 2 displayName: ${device.displayName}");
    addChildDevice("Child Switch", "${device.deviceNetworkId}-ep2", null,
                   [completedSetup: true, label: "${device.displayName} Fireplace OFF (CH2)",
                    isComponent: true, componentName: "ch2", componentLabel: "Fireplace OFF"]) 
    log.debug("adding children: network id: ${device.deviceNetworkId} for-count: 3 displayName: ${device.displayName}");
    addChildDevice("Child Temperature Sensor", "${device.deviceNetworkId}-ep3", null,
                   [completedSetup: true, label: "Temperature - ${device.displayName} (CH3)",
                    isComponent: true, componentName: "ch3", componentLabel: "Temperature Sensor 1"]) 
    log.debug("adding children: network id: ${device.deviceNetworkId} for-count: 4 displayName: ${device.displayName}");
    addChildDevice("Child Flame Sensor", "${device.deviceNetworkId}-ep4", null,
                   [completedSetup: true, label: "Flame - ${device.displayName} (CH4)",
                    isComponent: true, componentName: "ch4", componentLabel: "Flame Sensor 1"]) 
    
    //associationSet()
}

private encap(cmd, endpoint) {
	if (endpoint) {
    	//return secure(zwave.multiChannelV3.multiChannelCmdEncap(bitAddress: false, sourceEndPoint:0, destinationEndPoint: endpoint).encapsulate(cmd))
		zwave.multiChannelV3.multiChannelCmdEncap(destinationEndPoint:endpoint).encapsulate(cmd).format()
	} else {
		cmd.format()
	}
}

def command(physicalgraph.zwave.Command cmd) {
	trace "command(physicalgraph.zwave.Command ${cmd})"
	if (state.sec) {
		zwave.securityV1.securityMessageEncapsulation().encapsulate(cmd).format()
	} else {
		cmd.format()
	}
}

private trace(msg) {
	//log.trace(msg)
}


def prependZero(def s) {
	if (s > 9)
		return s
	else
		return "0$s"
}
