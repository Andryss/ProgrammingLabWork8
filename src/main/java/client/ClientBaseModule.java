package client;

import java.util.Properties;

public interface ClientBaseModule {

    void initialize() throws Exception;

    void setProperties(Properties properties) throws Exception;

}
