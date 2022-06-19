package server;

import java.util.Properties;

public interface ServerBaseModule {

    void initialize() throws Exception;

    void setProperties(Properties properties) throws Exception;

    void close();

}
