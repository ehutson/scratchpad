package com.ehutson.scratchpad.common.database;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConnectionPool implements Closeable {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
	
	protected BlockingQueue<Connection> connectionQueue;
	
	public ConnectionPool() {
		
	}
	
	public Connection getConnection() throws Exception {
		return connectionQueue.take();
	}
	
	public void releaseConnection(Connection connection) throws Exception {
		connectionQueue.put(connection);
	}
	
	public void closeAll() throws Exception {
		if (connectionQueue != null) {
			while(!connectionQueue.isEmpty()) {
				Connection connection = connectionQueue.take();
				connection.close();
			}
		}
	}
	
	public boolean init(DbOptions options) {
		try {
			connectionQueue = new LinkedBlockingQueue<Connection>(1);
			
			DbConnectionFactory factory = new DbConnectionFactory(options);
			Connection connection = factory.getConnection();
			
			connectionQueue.add(connection);
			
			return true;
		} catch (Exception e) {
			logger.error("Unable to create database connection:  " + e.getMessage(), e);
			return false;
		}
	}

	@Override
	public void close() throws IOException {
		try {
			closeAll();
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}
}
