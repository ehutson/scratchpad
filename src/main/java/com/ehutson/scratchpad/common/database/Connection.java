package com.ehutson.scratchpad.common.database;

import java.io.Closeable;
import java.io.IOException;

import org.rocksdb.RocksDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connection implements Closeable {

	private static final Logger logger = LoggerFactory.getLogger(Connection.class);
	
	private RocksDB connection;
	private boolean closed = true;

	public Connection(RocksDB connection) {
		this.connection = connection;
	}

	public RocksDB getConnection() throws Exception {
		if (closed) {
			throw new Exception("Connection is closed");
		}
		logger.debug("Getting connection...");
		return connection;
	}

	/**
	 * Currently rocksdb doesn't have a way to check to see if the connection is
	 * closed...
	 * 
	 * @return boolean
	 */
	public boolean isClosed() {
		return closed;
	}

	@Override
	public void close() throws IOException {
		logger.debug("Closing connection");
		connection.close();
		closed = true;
	}

}
