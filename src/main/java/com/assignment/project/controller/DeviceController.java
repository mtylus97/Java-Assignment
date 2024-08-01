package com.assignment.project.controller;

import com.assignment.project.devices.dto.Device;
import com.assignment.project.devices.topology.DeviceTopologyNode;
import com.assignment.project.service.DeviceService;
import com.assignment.project.validator.ArgumentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @PostMapping("/register")
    public ResponseEntity<Device> registerDevice(@RequestBody Device device) {
        ArgumentValidator.validateRegistrationArguments(device);
        Device createdDevice = deviceService.registerDevice(device);
        if (createdDevice == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
    }

    @GetMapping("/all-registered-devices")
    public ResponseEntity<List<Device>> retrieveAllDevices() {
        List<Device> devices = deviceService.retrieveAllDevices();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/{macAddress}")
    public ResponseEntity<Device> retrieveNetworkDeploymentDevice(@PathVariable String macAddress) {
        ArgumentValidator.validateMacAddress(macAddress);
        Device device = deviceService.retrieveDevice(macAddress);
        if (device == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @GetMapping("/topology")
    public ResponseEntity<List<DeviceTopologyNode>> retrieveAllRegisteredNetworkDeviceTopology() {
        List<DeviceTopologyNode> topology = deviceService.retrieveAllRegisteredNetworkDeviceTopology();
        return new ResponseEntity<>(topology, HttpStatus.OK);
    }

    @GetMapping("/topology/{macAddress}")
    public ResponseEntity<DeviceTopologyNode> retrieveNetworkDeviceTopologyFromDevice(@PathVariable String macAddress) {
        DeviceTopologyNode topology = deviceService.retrieveAllRegisteredNetworkDeviceTopologyFromSpecifiedDevice(macAddress);
        return new ResponseEntity<>(topology, HttpStatus.OK);
    }

}
