package tools.yaml;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

/**
 * Loads configuration from a Yaml file.
 *
 * The configuration files must be placed in a sub-package called "config" of the ConfigFileLoader class within the
 * P11CatDynamoDbCopyLambdaConfigs brazil package. They cannot be loaded from an arbitrary location on the file system.
 */
public class SimpleYamlFileReader implements YamlLoader {

    @Override
    public <T> T deserialize(final String configFilePath) throws YamlLoaderException {
        return parseYamlAndClose(openFile(configFilePath));
    }

    private InputStream openFile(final String filePath) throws YamlLoaderException {

        InputStream stream = null;
        try {
            stream = SimpleYamlFileReader.class.getResourceAsStream(filePath);
        } catch (Exception e) {
            throw new YamlLoaderException("Exception occurred when attempting to read config file.", e);
        }
        return stream;
    }

    /**
     * Parses the input stream Yaml content into a Map.
     * @param content the input stream to read configuration from
     * @param <T> the type of the POJO deserialized from Yaml
     * @return A map of configuration items.
     */
    @SuppressWarnings("unchecked")
    public <T> T parseYamlAndClose(final InputStream content) {

        try {
            Yaml yaml = new Yaml();
            // If the stream is empty e.g. the file is empty, then yaml.load() will return null instead of an empty map
            // Get the entire config from Yaml file.
            T yamlConfig = (T) yaml.load(content);

            if (yamlConfig != null) {
                return yamlConfig;
            } else {
                throw new YamlLoaderException("Could not find any configuration.");
            }
        } catch (YAMLException e) {
            throw new YamlLoaderException("Error parsing YAML content", e);
        } finally {
            IOUtils.closeQuietly(content);
        }
    }
}
