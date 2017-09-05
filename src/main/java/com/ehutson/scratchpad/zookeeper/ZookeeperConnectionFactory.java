package com.ehutson.scratchpad.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ZookeeperConnectionFactory {

	private static final Logger logger = LoggerFactory.getLogger(ZookeeperConnectionFactory.class);
	
	private ZookeeperOptions options;
	
	public ZookeeperConnectionFactory(ZookeeperOptions options) {
		this.options = options;
	}
	
	public ZookeeperClient getClient() {
		
		logger.debug("Initializing Zookeeper connection to " + options.getConnectString());
		
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(
				options.getRetryBaseSleepTimeMs(), 
				options.getRetryMaxRetries(), 
				options.getRetryMaxSleepMs());
		
		CuratorFramework framework = CuratorFrameworkFactory.newClient(
				options.getConnectString(),
				options.getSessionTimeoutMs(), 
				options.getConnectionTimeoutMs(), 
				retryPolicy);
		
		return new ZookeeperClient(framework);
	}
}
