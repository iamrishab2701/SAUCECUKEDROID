package config;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CapabilitiesReader {
    private static final String CAPABILITIES_FILE_PATH = "src/test/resources/config/capabilities.json";

    private static JsonNode getEnvironmentNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(CAPABILITIES_FILE_PATH);
            JsonNode rootNode = objectMapper.readTree(file);

            // Read the environment
            String environment = System.getProperty("env", "staging");
            JsonNode envNode = rootNode.get(environment);

            if (envNode == null) {
                throw new RuntimeException("Environment " + environment + " not found in capabilities.json");
            }

            return envNode;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read capabilities file: " + e.getMessage());
        }
    }

    public static DesiredCapabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        JsonNode envNode = getEnvironmentNode();

        // Iterate over key-value pairs and populate DesiredCapabilities
        Iterator<Map.Entry<String, JsonNode>> fields = envNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode valueNode = entry.getValue();

            // Convert boolean values to string
            if (valueNode.isBoolean()) {
                capabilities.setCapability(key, String.valueOf(valueNode.asBoolean()));
            } else if (valueNode.isInt()) {
                capabilities.setCapability(key, valueNode.asInt());
            } else if (valueNode.isDouble()) {
                capabilities.setCapability(key, valueNode.asDouble());
            } else {
                capabilities.setCapability(key, valueNode.asText());
            }
        }
        return capabilities;
    }

    public static String getDeviceName() {
        JsonNode envNode = getEnvironmentNode();
        JsonNode deviceNameNode = envNode.get("appium:deviceName");

        if (deviceNameNode == null || deviceNameNode.asText().isEmpty()) {
            throw new RuntimeException("Device name is not specified in capabilities.json");
        }

        return deviceNameNode.asText();
    }
}
