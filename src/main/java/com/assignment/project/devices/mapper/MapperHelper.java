package com.assignment.project.devices.mapper;

import com.assignment.project.devices.dto.AccessPoint;
import com.assignment.project.devices.dto.Device;
import com.assignment.project.devices.dto.Gateway;
import com.assignment.project.devices.dto.Switch;
import com.assignment.project.devices.entity.AccessPointEntity;
import com.assignment.project.devices.entity.DeviceEntity;
import com.assignment.project.devices.entity.GatewayEntity;
import com.assignment.project.devices.entity.SwitchEntity;

public final class MapperHelper {

    private static final DeviceMapper deviceMapper = DeviceMapper.INSTANCE;

    private MapperHelper() {
    }

    public static Device toDeviceDto(DeviceEntity deviceEntity) {
        if (deviceEntity instanceof AccessPointEntity) {
            return deviceMapper.toDto((AccessPointEntity) deviceEntity);
        } else if (deviceEntity instanceof GatewayEntity) {
            return deviceMapper.toDto((GatewayEntity) deviceEntity);
        } else if (deviceEntity instanceof SwitchEntity) {
            return deviceMapper.toDto((SwitchEntity) deviceEntity);
        } else {
            throw new IllegalArgumentException(String.format("No matching device type for device: %s", deviceEntity));
        }
    }

    public static DeviceEntity toDeviceEntity(Device device) {
        if (device instanceof AccessPoint) {
            return deviceMapper.fromDto((AccessPoint) device);
        } else if (device instanceof Gateway) {
            return deviceMapper.fromDto((Gateway) device);
        } else if (device instanceof Switch) {
            return deviceMapper.fromDto((Switch) device);
        } else {
            throw new IllegalArgumentException(String.format("No matching device type for device: %s", device));
        }
    }

}
