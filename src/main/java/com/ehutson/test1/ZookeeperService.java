package com.ehutson.test1;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperService {

	private static final Logger logger = LoggerFactory.getLogger(ZookeeperService.class);
	
	protected static CuratorFramework client;
	
	public ZookeeperService(String connectionString) {
		logger.debug("Initializing Zookeeper connection to " + connectionString);
		client = createSimple(connectionString);
		client.start();	
		logger.debug("Client started");
	}
	
	public void close() {
		client.close();
	}
	
	public static CuratorFramework createSimple(String connectionString) {
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000,3);
		return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
	}
	
	public void create(String path, byte[] payload) throws Exception {
		logger.debug("Creating " + path);
		create(client, path, payload);
	}
	
	public void update(String path, byte[] payload) throws Exception {
		logger.debug("Updating " + path);
		update(client, path, payload);
	}
	
	public void delete(String path) throws Exception {
		logger.debug("Deleting " + path);
		delete(client, path);
	}
	
	public byte[] get(String path) throws Exception {
		logger.debug("Getting " + path);
		return get(client, path);
	}
	
	public void create(CuratorFramework frameworkClient, String path, byte[] payload) throws Exception {
		//frameworkClient.create().creatingParentContainersIfNeeded().forPath(path, payload);
		frameworkClient.blockUntilConnected();
		frameworkClient.create().creatingParentsIfNeeded().forPath(path, payload);
	}
	
	public void update(CuratorFramework frameworkClient, String path, byte[] payload) throws Exception {
		//frameworkClient.setData().forPath(path, payload);
		
		CuratorOp deleteOp = frameworkClient.transactionOp().delete().forPath(path);
		CuratorOp createOp = frameworkClient.transactionOp().create().forPath(path, payload);
		
		frameworkClient.transaction().forOperations(deleteOp, createOp);
		//delete(frameworkClient, path);
		//create(frameworkClient, path, payload);
	}
	
	public void delete(CuratorFramework frameworkClient, String path) throws Exception {
		frameworkClient.blockUntilConnected();
		frameworkClient.delete().guaranteed().forPath(path);
	}
	
	public byte[] get(CuratorFramework frameworkClient, String path) throws Exception {
		frameworkClient.blockUntilConnected();
		try {
			return frameworkClient.getData().forPath(path);
		} catch (Exception e) {
			return null;
		}
	}
}
