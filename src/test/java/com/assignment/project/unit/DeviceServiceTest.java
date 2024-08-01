package com.assignment.project.unit;

import com.assignment.project.devices.dto.AccessPoint;
import com.assignment.project.devices.dto.Device;
import com.assignment.project.devices.dto.DeviceType;
import com.assignment.project.devices.dto.Gateway;
import com.assignment.project.devices.dto.Switch;
import com.assignment.project.devices.entity.AccessPointEntity;
import com.assignment.project.devices.entity.DeviceEntity;
import com.assignment.project.devices.entity.GatewayEntity;
import com.assignment.project.devices.entity.SwitchEntity;
import com.assignment.project.devices.topology.DeviceTopologyNode;
import com.assignment.project.repository.DeviceRepository;
import com.assignment.project.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    private Device gatewayDevice;

    private DeviceEntity gatewayEntity;

    @BeforeEach
    public void initialize() {
        gatewayEntity = new GatewayEntity();
        gatewayEntity.setDeviceType(DeviceType.GATEWAY);
        gatewayEntity.setMacAddress("00:11:22:33:44:55");
        gatewayDevice = new Gateway("00:11:22:33:44:55");
    }

    @Test
    void testRegisterDevice() {
        Mockito.when(deviceRepository.save(Mockito.any(DeviceEntity.class))).thenReturn(gatewayEntity);
        Device result = deviceService.registerDevice(gatewayDevice);

        assertNotNull(result);
        assertEquals(gatewayDevice.getMacAddress(), result.getMacAddress());
        assertEquals(gatewayDevice.getDeviceType(), result.getDeviceType());
    }

    @Test
    void testAllDeviceRetrieval() {

        DeviceEntity switchEntity = new SwitchEntity();
        switchEntity.setDeviceType(DeviceType.GATEWAY);
        switchEntity.setMacAddress("00:11:22:33:44:56");

        DeviceEntity accessPointEntity = new AccessPointEntity();
        accessPointEntity.setDeviceType(DeviceType.ACCESS_POINT);
        accessPointEntity.setMacAddress("00:11:22:33:44:57");

        Mockito.when(deviceRepository.findAll()).thenReturn(Arrays.asList(gatewayEntity, switchEntity, accessPointEntity));

        Device switchDevice = new Switch("00:11:22:33:44:56");
        Device accessPointDevice = new AccessPoint("00:11:22:33:44:57");

        List<Device> devicesResult = deviceService.retrieveAllDevices();

        assertNotNull(devicesResult);
        assertFalse(devicesResult.isEmpty());

        //check a correct order
        assertEquals(devicesResult.get(0).getDeviceType(), DeviceType.GATEWAY);
        assertEquals(devicesResult.get(1).getDeviceType(), DeviceType.SWITCH);
        assertEquals(devicesResult.get(2).getDeviceType(), DeviceType.ACCESS_POINT);

        assertTrue(devicesResult.containsAll(Arrays.asList(gatewayDevice, switchDevice, accessPointDevice)));
    }

    @Test
    void testDeviceRetrievalByMacAddress() {
        Mockito.when(deviceRepository.findDeviceByMacAddress("00:11:22:33:44:55")).thenReturn(gatewayEntity);
        Device result = deviceService.retrieveDevice("00:11:22:33:44:55");

        assertNotNull(result);
        assertEquals(gatewayDevice.getMacAddress(), result.getMacAddress());
        assertEquals(gatewayDevice.getDeviceType(), result.getDeviceType());
    }

    @Test
    void testGetTopology() {
        DeviceEntity gatewayEntity = new GatewayEntity();
        gatewayEntity.setDeviceType(DeviceType.GATEWAY);
        gatewayEntity.setMacAddress("00:11:22:33:44:55");

        DeviceEntity switchEntity = new SwitchEntity();
        switchEntity.setDeviceType(DeviceType.SWITCH);
        switchEntity.setMacAddress("00:11:22:33:44:66");
        switchEntity.setUplinkMacAddress("00:11:22:33:44:55");

        DeviceEntity accessPointEntity = new AccessPointEntity();
        accessPointEntity.setDeviceType(DeviceType.ACCESS_POINT);
        accessPointEntity.setMacAddress("00:11:22:33:44:77");
        accessPointEntity.setUplinkMacAddress("00:11:22:33:44:66");

        Mockito.when(deviceRepository.findAll()).thenReturn(Arrays.asList(gatewayEntity, switchEntity, accessPointEntity));

        List<DeviceTopologyNode> topology = deviceService.retrieveAllRegisteredNetworkDeviceTopology();

        assertNotNull(topology);
        assertEquals(topology.get(0).getMacAddress(), gatewayEntity.getMacAddress());
        assertEquals(topology.get(0).getChildNodes().get(0).getMacAddress(), switchEntity.getMacAddress());
        assertEquals(topology.get(0).getChildNodes().get(0).getChildNodes().get(0).getMacAddress(), accessPointEntity.getMacAddress());
    }

    @Test
    void testGetTopologyFromSpecifiedDevice() {
        DeviceEntity gatewayEntity = new GatewayEntity();
        gatewayEntity.setDeviceType(DeviceType.GATEWAY);
        gatewayEntity.setMacAddress("00:11:22:33:44:55");

        DeviceEntity switchEntity = new SwitchEntity();
        switchEntity.setDeviceType(DeviceType.SWITCH);
        switchEntity.setMacAddress("00:11:22:33:44:66");
        switchEntity.setUplinkMacAddress("00:11:22:33:44:55");

        DeviceEntity accessPointEntity = new AccessPointEntity();
        accessPointEntity.setDeviceType(DeviceType.ACCESS_POINT);
        accessPointEntity.setMacAddress("00:11:22:33:44:77");
        accessPointEntity.setUplinkMacAddress("00:11:22:33:44:66");

        Mockito.when(deviceRepository.findAll()).thenReturn(Arrays.asList(gatewayEntity, switchEntity, accessPointEntity));

        DeviceTopologyNode topologyNode = deviceService.retrieveAllRegisteredNetworkDeviceTopologyFromSpecifiedDevice(switchEntity.getMacAddress());

        assertNotNull(topologyNode);
        assertEquals(topologyNode.getMacAddress(), switchEntity.getMacAddress());
        assertEquals(topologyNode.getChildNodes().get(0).getMacAddress(), accessPointEntity.getMacAddress());
    }

}
