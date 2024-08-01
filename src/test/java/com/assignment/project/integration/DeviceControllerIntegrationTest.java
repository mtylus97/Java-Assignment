package com.assignment.project.integration;

import com.assignment.project.devices.dto.AccessPoint;
import com.assignment.project.devices.dto.Device;
import com.assignment.project.devices.dto.Gateway;
import com.assignment.project.devices.dto.Switch;
import com.assignment.project.repository.DeviceRepository;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DeviceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeviceRepository deviceRepository;


    @BeforeEach
    void setUp() {
        deviceRepository.deleteAll();
        objectMapper.disable(MapperFeature.USE_ANNOTATIONS);
    }

    @Test
    void testRegisterDevice() throws Exception {
        Device device = new Gateway( "00:11:22:33:44:55");

        mockMvc.perform(post("/devices/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.macAddress").value(device.getMacAddress()));
    }

    @Test
    void testRetrieveAllDevicesWithOrder() throws Exception {
        Device device1 = new AccessPoint("00:11:22:33:44:55");
        Device device2 = new Switch("00:11:22:33:44:66", "00:11:22:33:44:55");
        Device device3 = new Gateway("00:11:22:33:44:77", "00:11:22:33:44:51");

        mockMvc.perform(post("/devices/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device1)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/devices/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device2)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/devices/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device3)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/devices/all-registered-devices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //the order should be: Gateway->Switch->Access Point
                .andExpect(jsonPath("$[0].macAddress").value(device3.getMacAddress()))
                .andExpect(jsonPath("$[1].macAddress").value(device2.getMacAddress()))
                .andExpect(jsonPath("$[2].macAddress").value(device1.getMacAddress()));
    }

    @Test
    void testRetrieveDeviceByMacAddress() throws Exception {
        Device device = new Gateway("00:11:22:33:44:55");

        mockMvc.perform(post("/devices/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/devices/00:11:22:33:44:55", device.getMacAddress())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.macAddress").value(device.getMacAddress()));
    }

    @Test
    void testGetTopology() throws Exception {
        AccessPoint device1 = new AccessPoint("00:11:22:33:44:55");
        Switch device2 = new Switch("00:11:22:33:44:66", "00:11:22:33:44:55");
        Gateway device3 = new Gateway("00:11:22:33:44:77", "00:11:22:33:44:66");

        mockMvc.perform(post("/devices/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device1)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/devices/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device2)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/devices/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device3)))
                .andExpect(status().isCreated());


         mockMvc.perform(get("/devices/topology")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].macAddress").value(device1.getMacAddress()))
                .andExpect(jsonPath("$[0].childNodes[0].macAddress").value(device2.getMacAddress()))
                .andExpect(jsonPath("$[0].childNodes[0].childNodes[0].macAddress").value(device3.getMacAddress()));
    }

    @Test
    void testGetTopologyFromSpecificDevice() throws Exception {
        AccessPoint device1 = new AccessPoint("00:11:22:33:44:55");
        Switch device2 = new Switch("00:11:22:33:44:66", "00:11:22:33:44:55");
        Gateway device3 = new Gateway("00:11:22:33:44:77", "00:11:22:33:44:66");

        mockMvc.perform(post("/devices/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device1)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/devices/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device2)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/devices/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device3)))
                .andExpect(status().isCreated());


        mockMvc.perform(get("/devices/topology/00:11:22:33:44:66")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.macAddress").value(device2.getMacAddress()))
                .andExpect(jsonPath("$.childNodes[0].macAddress").value(device3.getMacAddress()));
    }

}
