package com.ehutson.scratchpad.zookeeper.impl;

import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ehutson.scratchpad.zookeeper.ZookeeperClient;

public class ZookeeperClientImpl implements ZookeeperClient {

	private static final Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);

	private CuratorFramework curatorFrameworkClient;

	public ZookeeperClientImpl(CuratorFramework curatorFrameworkClient) {
		this.curatorFrameworkClient = curatorFrameworkClient;
		this.curatorFrameworkClient.start();
	}

	@Override
	public void close() throws IOException {
		this.curatorFrameworkClient.close();
	}

	public void create(String path, byte[] payload) throws Exception {
		logger.debug("Creating " + path);
		curatorFrameworkClient.blockUntilConnected();
		curatorFrameworkClient.create().creatingParentsIfNeeded().forPath(path, payload);
	}

	public void update(String path, byte[] payload) throws Exception {
		logger.debug("Updating " + path);
		CuratorOp deleteOp = curatorFrameworkClient.transactionOp().delete().forPath(path);
		CuratorOp createOp = curatorFrameworkClient.transactionOp().create().forPath(path, payload);

		curatorFrameworkClient.transaction().forOperations(deleteOp, createOp);
	}

	public void delete(String path) throws Exception {
		logger.debug("Deleting " + path);
		curatorFrameworkClient.blockUntilConnected();
		curatorFrameworkClient.delete().guaranteed().forPath(path);
	}

	public byte[] get(String path) throws Exception {
		logger.debug("Getting " + path);
		curatorFrameworkClient.blockUntilConnected();
		try {
			return curatorFrameworkClient.getData().forPath(path);
		} catch (Exception e) {
			return null;
		}
	}
}
