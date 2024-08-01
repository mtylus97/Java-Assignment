package com.assignment.project.devices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public class Device {

    @NonNull
    private String macAddress;

    @NonNull
    private DeviceType deviceType;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String uplinkMacAddress;

    public Device() {
        this.macAddress = "";
    }

    public Device(@NonNull String macAddress, @NonNull DeviceType deviceType) {
        this.macAddress = macAddress;
        this.deviceType = deviceType;
    }

    public Device(@NonNull String macAddress, @NonNull DeviceType deviceType, String uplinkMacAddress) {
        this.macAddress = macAddress;
        this.deviceType = deviceType;
        this.uplinkMacAddress = uplinkMacAddress;
    }

}
