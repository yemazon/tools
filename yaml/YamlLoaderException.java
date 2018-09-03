package tools.yaml;

/**
 * Exception class for any issues with reading the Yaml configuration.
 */
public class YamlLoaderException extends RuntimeException {

    private static final long serialVersionUID = 3332139986319218847L;

    /**
     * Constructor with message.
     * @param message Message to throw exception with.
     */
    public YamlLoaderException(final String message) {
        super(message);
    }

    /**
     * Constructor with message and throwable.
     * @param message Message to throw exception with.
     * @param cause the cause.
     */
    public YamlLoaderException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
