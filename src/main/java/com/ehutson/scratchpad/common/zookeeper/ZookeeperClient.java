package com.ehutson.scratchpad.common.zookeeper;

import java.io.Closeable;

public interface ZookeeperClient extends Closeable {

    public void create(String path, byte[] payload) throws Exception;

    public void update(String path, byte[] payload) throws Exception;

    public void delete(String path) throws Exception;

    public byte[] get(String path) throws Exception;
}
