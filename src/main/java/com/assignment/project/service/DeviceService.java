package com.assignment.project.service;

import com.assignment.project.devices.DeviceComparator;
import com.assignment.project.devices.dto.AccessPoint;
import com.assignment.project.devices.dto.Device;
import com.assignment.project.devices.dto.DeviceType;
import com.assignment.project.devices.dto.Gateway;
import com.assignment.project.devices.dto.Switch;
import com.assignment.project.devices.entity.DeviceEntity;
import com.assignment.project.devices.mapper.MapperHelper;
import com.assignment.project.devices.topology.DeviceTopologyNode;
import com.assignment.project.devices.topology.Topology;
import com.assignment.project.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;

@Slf4j
@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device registerDevice(Device device) {
        Device createdDevice;
        switch (device.getDeviceType()) {
            case ACCESS_POINT -> {
                createdDevice = new AccessPoint(device.getMacAddress(), device.getUplinkMacAddress());
                addDevice(createdDevice);
            }
            case GATEWAY -> {
                createdDevice = new Gateway(device.getMacAddress(), device.getUplinkMacAddress());
                addDevice(createdDevice);
            }
            case SWITCH -> {
                createdDevice = new Switch(device.getMacAddress(), device.getUplinkMacAddress());
                addDevice(createdDevice);
            }
            default -> {
                throw new IllegalArgumentException(String.format("Provided device type is wrong. Possible device types are: %s", EnumSet.allOf(DeviceType.class)));
            }
        }
        return createdDevice;
    }

    private void addDevice(Device device) {
        deviceRepository.save(MapperHelper.toDeviceEntity(device));
    }

    public List<Device> retrieveAllDevices() {
        return deviceRepository.findAll().stream().map(MapperHelper::toDeviceDto).sorted(new DeviceComparator()).toList();
    }

    public Device retrieveDevice(String macAddress) {
        try {
            return MapperHelper.toDeviceDto(deviceRepository.findDeviceByMacAddress(macAddress));
        } catch (Exception e) {
            log.error("An issue occurred when retrieving device for mac address: {}", macAddress);
            throw e;
        }
    }

    public List<DeviceTopologyNode> retrieveAllRegisteredNetworkDeviceTopology() {
        List<DeviceEntity> allDevices = deviceRepository.findAll();
        return Topology.buildTopologyTree(allDevices);
    }

    public DeviceTopologyNode retrieveAllRegisteredNetworkDeviceTopologyFromSpecifiedDevice(String macAddress) {
        List<DeviceEntity> allDevices = deviceRepository.findAll();
        return Topology.buildTopologyTreeFromRoot(allDevices, macAddress);
    }

}
