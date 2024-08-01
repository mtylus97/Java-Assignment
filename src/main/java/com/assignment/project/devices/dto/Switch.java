package com.assignment.project.devices.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class Switch extends Device {

    //added as a switch specific field
    @JsonIgnore
    List<Device> networkDevices;

    public Switch(@NonNull String macAddress) {
        super(macAddress, DeviceType.SWITCH);
    }

    public Switch(@NonNull String macAddress, String uplinkMacAddress) {
        super(macAddress, DeviceType.SWITCH, uplinkMacAddress);
    }

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.SWITCH;
    }

}

