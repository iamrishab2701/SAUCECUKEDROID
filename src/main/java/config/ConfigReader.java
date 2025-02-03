package config;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ConfigReader {
    private static Map<String, Map<String, String>> environmentData;
    private static String environment;

    static {
        loadYaml();
        setEnvironmentFromCommandLine();
    }

    private static void loadYaml() {
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream("config/environment.yaml")) {
            if (inputStream == null) {
                throw new RuntimeException("environment.yaml file not found");
            }
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            environmentData = (Map<String, Map<String, String>>) data.get("environments");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load environment.yaml", e);
        }
    }

    private static void setEnvironmentFromCommandLine() {
        environment = System.getProperty("env", "staging"); // Default to "staging" if not provided
    }

    public static String getUsername() {
        return environmentData.get(environment).get("username");
    }

    public static String getPassword() {
        return environmentData.get(environment).get("password");
    }

    public static String getEnvironment() {
        return environment;
    }
}
