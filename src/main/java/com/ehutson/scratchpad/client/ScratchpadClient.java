package com.ehutson.scratchpad.client;

import com.ehutson.scratchpad.common.network.QueryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScratchpadClient {

    private static final Logger logger = LoggerFactory.getLogger(ScratchpadClient.class);

    QueryClient client;

    public ScratchpadClient(String host, int port) {
        client = new QueryClient(host, port);
    }

    public void connect() throws InterruptedException {
        client.connect();
        ;
    }

    public void disconnect() {
        client.disconnect();
    }

    public String get(String key) throws Exception {
        return client.get(key);
    }

    public byte[] getAsBytes(String key) throws Exception {
        return client.getAsBytes(key);
    }

    public void set(String key, String value) throws Exception {
        client.set(key, value);
    }

    public void set(String key, byte[] value) throws Exception {
        client.set(key, value);
    }

    public void delete(String key) throws Exception {
        client.delete(key);
    }
}
