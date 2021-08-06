package ru.testit.properties;

import java.net.*;
import java.io.*;
import org.slf4j.*;
import java.util.*;

public class AppProperties
{
    private static final Logger log;
    private Properties appProps;
    
    public AppProperties() {
        final String appConfigPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("testit.properties")).getPath();
        this.appProps = new Properties();
        try {
            this.appProps.load(new FileInputStream(appConfigPath));
        }
        catch (IOException e) {
            AppProperties.log.error("Exception while read properties", (Throwable)e);
        }
    }
    
    public String getProjectID() {
        return String.valueOf(((Hashtable<K, Object>)this.appProps).get("ProjectId"));
    }
    
    public String getUrl() {
        return String.valueOf(((Hashtable<K, Object>)this.appProps).get("URL"));
    }
    
    public String getPrivateToken() {
        return String.valueOf(((Hashtable<K, Object>)this.appProps).get("PrivateToken"));
    }
    
    public String getConfigurationId() {
        return String.valueOf(((Hashtable<K, Object>)this.appProps).get("ConfigurationId"));
    }
    
    static {
        log = LoggerFactory.getLogger((Class)AppProperties.class);
    }
}
