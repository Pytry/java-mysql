package org.xitikit.examples.java.mysql.logwatch.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Properties;

import static java.nio.file.Files.*;
import static java.util.Arrays.*;

public class DataSourceProperties{

    private final Properties properties = new Properties();

    public DataSourceProperties(){

        Path path = Paths.get("application.properties");
        if(exists(path) && isReadable(path)){
            loadPropertiesFromFileSystem(path);
        }
        else{
            loadPropertiesFromClassPath();
        }
    }

    private void loadPropertiesFromClassPath(){

        try(InputStream input = this.getClass().getResourceAsStream("/application.properties")){
            properties.load(input);
        }
        catch(IOException e){
            throw new IllegalStateException("Failed to load 'application.properties' from the running directory. " + e.getMessage(), e);
        }
    }

    private void loadPropertiesFromFileSystem(Path path){

        assert path != null;
        try(BufferedReader reader = newBufferedReader(path)){
            properties.load(reader);
        }
        catch(IOException e){
            throw new IllegalStateException("Failed to load 'application.properties' from the running directory. " + e.getMessage(), e);
        }
    }

    public String getUrl(){

        return properties.getProperty("datasource.url");
    }

    public String getUsername(){

        return properties.getProperty("datasource.username");
    }

    public String getPassword(){

        return properties.getProperty("datasource.password");
    }

    public String getDriverClassName(){

        return properties.getProperty("datasource.driver-class-name");
    }

    public Collection<String> getConnectionInitSqls(){

        return asList(properties.getProperty("datasource.connection-init-sql"));
    }
}

