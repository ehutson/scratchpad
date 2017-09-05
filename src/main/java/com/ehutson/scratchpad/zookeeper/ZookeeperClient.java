package com.ehutson.scratchpad.zookeeper;

import java.io.Closeable;
import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ZookeeperClient extends Closeable {

		
	public void create(String path, byte[] payload) throws Exception;
	
	public void update(String path, byte[] payload) throws Exception;
	
	public void delete(String path) throws Exception;
	
	public byte[] get(String path) throws Exception;
}
