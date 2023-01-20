package org.kagaka.influx;

import java.io.IOException;
import java.util.Properties;

public class InfluxConfig {
    
    private static final String DEFAULT_URL = "http://localhost:8086";
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "changeit";
    private static final String DEFAULT_ORGNAME = "qa";
    private static final String DEFAULT_BUCKETNAME = "testing";
    private static final String DEFAULT_PROPS_FILE = "/influx2.conf";
    
    private String url;
    private String username;
    private String password;
    private String orgname;
    private String bucketname;
    private String token;
    private String orgId;
    private String propsFile = DEFAULT_PROPS_FILE;
    private Properties props;
    
    public InfluxConfig(String url, String username, String password, String orgname, String bucketname) {
        super();
        this.url = url;
        this.username = username;
        this.password = password;
        this.orgname = orgname;
        this.bucketname = bucketname;
        this.token = null;
        this.orgId = null;
    }
    
    

    public InfluxConfig() {
        super();
        // TODO Auto-generated constructor stub
        // TODO use reading of config file or ENV vals
        props = new Properties();
        
        System.out.println("DEBUG propsFile " + this.propsFile);
      //  System.out.println("DEBUG classpath " + System.getProperty("java.class.path"));
      //  ClassLoader loader = this.getClass().getClassLoader();
      //  System.out.println("DEBUG path to propsFile " + loader.getResource(this.propsFile));
        
        try {
            props.load(InfluxConfig.class.getResourceAsStream(this.propsFile));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new IllegalStateException(e);
        }
        
        this.url = props.getProperty("url") != null ? props.getProperty("url") : DEFAULT_URL;
        this.username = props.getProperty("username") != null ? props.getProperty("username") : DEFAULT_USERNAME;
        this.password = props.getProperty("password") != null ? props.getProperty("password") : DEFAULT_PASSWORD;
        this.orgname = props.getProperty("org.name") != null ? props.getProperty("org.name") : DEFAULT_ORGNAME;
        this.bucketname = props.getProperty("bucket.name") != null ? props.getProperty("bucket.name") : DEFAULT_BUCKETNAME;
        this.token = props.getProperty("token");
        this.orgId = props.getProperty("org.id");
    
    }

    public InfluxConfig(String propsFile) {
        super();
        this.propsFile = propsFile;

        props = new Properties();
        
        try {
            props.load(this.getClass().getResourceAsStream(this.propsFile));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new IllegalStateException(e);
        }
        
        this.url = props.getProperty("url") != null ? props.getProperty("url") : DEFAULT_URL;
        this.username = props.getProperty("username") != null ? props.getProperty("username") : DEFAULT_USERNAME;
        this.password = props.getProperty("password") != null ? props.getProperty("password") : DEFAULT_PASSWORD;
        this.orgname = props.getProperty("org.name") != null ? props.getProperty("org.name") : DEFAULT_ORGNAME;
        this.bucketname = props.getProperty("bucket.name") != null ? props.getProperty("bucket.name") : DEFAULT_BUCKETNAME;
        this.token = props.getProperty("token");
        this.orgId = props.getProperty("org.id");
    
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getBucketname() {
        return bucketname;
    }

    public void setBucketname(String bucketname) {
        this.bucketname = bucketname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPropsFile() {
        return propsFile;
    }

    public Properties getProps() {
        return props;
    }

}
