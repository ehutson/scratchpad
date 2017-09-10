package com.ehutson.test1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperSample {

	private static final Logger logger = LoggerFactory.getLogger(ZookeeperSample.class);
	
	public static void main(String[] args) throws Exception {

		final String connectionString = "localhost:2181"; 

		ZookeeperService zk = new ZookeeperService(connectionString);

		final String KEY = "/key1";
		
		zk.create(KEY, "This is a test".getBytes());

		byte[] retval = zk.get(KEY);
		logger.info("Got " + new String(retval));
		
		zk.update(KEY, "This is another test".getBytes());
		retval = zk.get(KEY);
		logger.info("Got " + new String(retval));
		
		zk.delete(KEY);
		retval = zk.get(KEY);
		if (null == retval) {
			logger.info("key1 has been deleted");
		} else {
			logger.error("oops");
		}
		
		zk.close();
	}

}
