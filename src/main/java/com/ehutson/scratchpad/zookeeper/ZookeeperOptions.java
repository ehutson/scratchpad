package com.ehutson.scratchpad.zookeeper;

import static org.apache.curator.utils.Compatibility.isZK34;

import java.util.List;
import java.util.concurrent.ThreadFactory;

import org.apache.curator.RetryPolicy;
import org.apache.curator.connection.ConnectionHandlingPolicy;
import org.apache.curator.connection.StandardConnectionHandlingPolicy;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.api.CompressionProvider;
import org.apache.curator.framework.schema.SchemaSet;
import org.apache.curator.framework.state.ConnectionStateErrorPolicy;
import org.apache.curator.framework.state.StandardConnectionStateErrorPolicy;
import org.apache.curator.utils.ZookeeperFactory;

public class ZookeeperOptions {
	
	private static final int DEFAULT_SESSION_TIMEOUT_MS = 10 * 60 * 1000;
    private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 10 * 15 * 1000;
    
    private static final int DEFAULT_RETRY_BASE_SLEEP_TIME_MS = 1000;
    private static final int DEFAULT_RETRY_MAX_RETRIES = 3;
    private static final int DEFAULT_RETRY_MAX_SLEEP_MS = Integer.MAX_VALUE;

	private String connectString; 
	private int sessionTimeoutMs = DEFAULT_SESSION_TIMEOUT_MS; 
	private int connectionTimeoutMs = DEFAULT_CONNECTION_TIMEOUT_MS;
	
	private int retryBaseSleepTimeMs = DEFAULT_RETRY_BASE_SLEEP_TIME_MS;
	private int retryMaxRetries = DEFAULT_RETRY_MAX_RETRIES;
	private int retryMaxSleepMs = DEFAULT_RETRY_MAX_SLEEP_MS;
	
	public ZookeeperOptions() {
		
	}

	/**
	 * @return the connectString
	 */
	public String getConnectString() {
		return connectString;
	}

	/**
	 * @param connectString the connectString to set
	 */
	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	/**
	 * @return the sessionTimeoutMs
	 */
	public int getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	/**
	 * @param sessionTimeoutMs the sessionTimeoutMs to set
	 */
	public void setSessionTimeoutMs(int sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	/**
	 * @return the connectionTimeoutMs
	 */
	public int getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}

	/**
	 * @param connectionTimeoutMs the connectionTimeoutMs to set
	 */
	public void setConnectionTimeoutMs(int connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
	}

	/**
	 * @return the retryBaseSleepTimeMs
	 */
	public int getRetryBaseSleepTimeMs() {
		return retryBaseSleepTimeMs;
	}

	/**
	 * @param retryBaseSleepTimeMs the retryBaseSleepTimeMs to set
	 */
	public void setRetryBaseSleepTimeMs(int retryBaseSleepTimeMs) {
		this.retryBaseSleepTimeMs = retryBaseSleepTimeMs;
	}

	/**
	 * @return the retryMaxRetries
	 */
	public int getRetryMaxRetries() {
		return retryMaxRetries;
	}

	/**
	 * @param retryMaxRetries the retryMaxRetries to set
	 */
	public void setRetryMaxRetries(int retryMaxRetries) {
		this.retryMaxRetries = retryMaxRetries;
	}

	/**
	 * @return the retryMaxSleepMs
	 */
	public int getRetryMaxSleepMs() {
		return retryMaxSleepMs;
	}

	/**
	 * @param retryMaxSleepMs the retryMaxSleepMs to set
	 */
	public void setRetryMaxSleepMs(int retryMaxSleepMs) {
		this.retryMaxSleepMs = retryMaxSleepMs;
	}
	
	
	
}
