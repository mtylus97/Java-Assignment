package com.assignment.project.validator;

import com.assignment.project.devices.dto.Device;
import com.assignment.project.devices.dto.DeviceType;

public final class ArgumentValidator {

    private static final String MAC_ADDRESS_FORMAT = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";

    private ArgumentValidator() {
    }

    public static void validateRegistrationArguments(Device device) {
        validateDeviceType(device.getDeviceType());
        validateMacAddress(device.getMacAddress());
        if (device.getUplinkMacAddress() != null && !device.getUplinkMacAddress().isBlank()) {
            validateMacAddress(device.getUplinkMacAddress());
        }
    }

    public static void validateMacAddress(String macAddress) {
        if (!macAddress.matches(MAC_ADDRESS_FORMAT)) {
            throw new IllegalArgumentException(String.format("Provided mac address: \"%s\" has an incorrect format", macAddress));
        }
    }

    public static void validateDeviceType(DeviceType deviceType) {

    }

}
