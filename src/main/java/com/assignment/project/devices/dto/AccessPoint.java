package com.assignment.project.devices.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccessPoint extends Device {

    public AccessPoint(String macAddress) {
        super(macAddress, DeviceType.ACCESS_POINT);
    }

    public AccessPoint(String macAddress, String uplinkMacAddress) {
        super(macAddress, DeviceType.ACCESS_POINT, uplinkMacAddress);
    }

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.ACCESS_POINT;
    }

}
