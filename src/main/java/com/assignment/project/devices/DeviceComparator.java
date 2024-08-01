package com.assignment.project.devices;

import com.assignment.project.devices.dto.Device;

import java.util.Comparator;

public class DeviceComparator implements Comparator<Device> {

    @Override
    public int compare(Device o1, Device o2) {
        return Integer.compare(o1.getDeviceType().getOrder(), o2.getDeviceType().getOrder());
    }

}
