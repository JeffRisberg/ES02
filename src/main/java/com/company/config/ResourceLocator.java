package com.company.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

@Slf4j
public class ResourceLocator {
  private static final String SEARCH_HOST_KEY = "es02.search.host";
  private static final String SEARCH_PORT_KEY = "es02.search.port";
  private static final String SEARCH_CLUSTER_NAME_KEY = "es02.search.clusterName";

  private static final String SEARCH_DEFAULT_HOST = "localhost";
  private static final int SEARCH_DEFAULT_PORT = 9300;
  private static final String SEARCH_DEFAULT_CLUSTER_NAME = "elasticsearch";

  private static Properties GLOBAL_PROPERTIES = new Properties();

  public static void registerProperties(Properties properties) {
    if (properties != null) {
      try {
        synchronized (GLOBAL_PROPERTIES) {
          GLOBAL_PROPERTIES.putAll(properties);
        }
      } catch (Exception e) {
        log.error("Error loading properties from map " + properties, e);
      }
    }
  }

  public static void registerProperties(InputStream is) {
    if (is != null) {
      try {
        synchronized (GLOBAL_PROPERTIES) {
          GLOBAL_PROPERTIES.load(is);
        }
      } catch (Exception e) {
        log.error("Error loading properties from stream " + is, e);
      }
    }
  }

  /**
   * Depending on the orchestration, search cluster can be located as a different service
   * or in the same node as a separate microservice.
   *
   * @return
   */
  public static String getSearchHost() {
    return getResource(SEARCH_HOST_KEY).orElse(SEARCH_DEFAULT_HOST);
  }

  public static int getSearchPort() {
    return getResource(SEARCH_PORT_KEY).map((x) ->
      Integer.parseInt(x)).orElse(SEARCH_DEFAULT_PORT);
  }

  public static String getSearchClusterName() {
    return getResource(SEARCH_CLUSTER_NAME_KEY).orElse(SEARCH_DEFAULT_CLUSTER_NAME);
  }

  /**
   * Dynamic properties per namespace
   *
   * @param namespace
   * @param key
   * @return
   */
  public static Optional<String> getResource(String namespace, String key) {
    final String newKey = key.replaceAll("\\.", "_");
    return getResource(newKey);

    /*
    return DynamicProperties.getInstance()
      .map(dp -> dp.getProperty(namespace, newKey)) // get the dynamic property
      .filter(op -> op.isPresent()) // if dynamic property is not present, default to the static property
      .orElse(getResource(key));
      */
  }

  /**
   * Env takes precedence over properties
   *
   * @param key
   * @return
   */
  public static Optional<String> getResource(String key) {
    // Change all the key structure in Milestone_6 to use '_' instead of dots
    // This is a performance overhead, but making sticking to it for M5.
    // Order of resolution:
    //  get env value with modified key
    //  get env value with original key
    //  get properties value with original key

    String newKey = key.replaceAll("\\.", "_");
    String value = getEnvValue(newKey).orElseGet(() ->
      getEnvValue(key).orElseGet(() -> GLOBAL_PROPERTIES.getProperty(key)));

    return (StringUtils.isEmpty(value)) ? Optional.empty() : Optional.of(value);
  }

  private static Optional<String> getEnvValue(String key) {
    String value = System.getenv(key);
    return ((value == null) || value.isEmpty()) ? Optional.empty() : Optional.of(value);
  }

  public static Optional<Boolean> getResourceAsBoolean(String namespace, String key) {
    return getResource(namespace, key).map(x -> new Boolean(Boolean.valueOf(x)));
  }

  public static Optional<Boolean> getResourceAsBoolean(String key) {
    return getResource(key).map(x -> new Boolean(Boolean.valueOf(x)));
  }

  public static Optional<Integer> getResourceAsInt(String namespace, String key) {
    return getResource(namespace, key).map(x -> new Integer(Integer.parseInt(x)));
  }

  public static Optional<Integer> getResourceAsInt(String key) {
    return getResource(key).map(x -> new Integer(Integer.parseInt(x)));
  }
}
