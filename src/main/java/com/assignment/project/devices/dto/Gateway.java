package com.assignment.project.devices.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Gateway extends Device {

    //added as a gateway specific field
    @JsonIgnore
    String network;

    public Gateway(String macAddress) {
        super(macAddress, DeviceType.GATEWAY);
    }

    public Gateway(String macAddress, String uplinkMacAddress) {
        super(macAddress, DeviceType.GATEWAY, uplinkMacAddress);
    }


    @Override
    public DeviceType getDeviceType() {
        return DeviceType.GATEWAY;
    }

}
