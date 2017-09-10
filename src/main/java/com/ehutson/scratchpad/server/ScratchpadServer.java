package com.ehutson.scratchpad.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ehutson.scratchpad.common.database.ConnectionPool;
import com.ehutson.scratchpad.common.database.DbOptions;
import com.ehutson.scratchpad.common.network.QueryServer;

public class ScratchpadServer {

	private static final Logger logger = LoggerFactory.getLogger(ScratchpadServer.class);
	
	public static void main(String[] args) throws Exception {
		
		int port = 4269;
		
		logger.info("Starting up...");
		DbOptions dbOptions = new DbOptions();
		dbOptions.setFolderPath("/tmp");
		dbOptions.setDbName("scratchDb");
		
		logger.info("Creating DB connection pool");
		ConnectionPool connectionPool = new ConnectionPool();
		connectionPool.init(dbOptions);
		
		logger.info("Starting new QueryServer on port " + port);
		QueryServer server = new QueryServer(port, connectionPool);
		
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		        try {
		        	logger.info("Shutting Down");
		        	logger.info("Closing server connections");
					server.close();
		        	logger.info("Closing connection pool");
					connectionPool.close();
					logger.info("Done");
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
		    }
		}));
		
		logger.info("Running server");
		server.run();
	}

}
