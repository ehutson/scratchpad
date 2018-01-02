package com.ehutson.scratchpad.server;

import com.ehutson.scratchpad.common.database.Connection;
import com.ehutson.scratchpad.common.database.DbConnectionFactory;
import com.ehutson.scratchpad.common.database.DbOptions;
import com.ehutson.scratchpad.common.network.QueryServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ScratchpadServer {

    private static final Logger logger = LoggerFactory.getLogger(ScratchpadServer.class);

    public static void main(String[] args) throws Exception {

        int port = 4269;

        logger.info("Starting up...");
        DbOptions databaseOptions = new DbOptions();
        databaseOptions.setFolderPath("/tmp");
        databaseOptions.setDbName("scratchDb");

        logger.info("Creating DB connection pool");

        DbConnectionFactory factory = new DbConnectionFactory(databaseOptions);
        Connection databaseConnection = factory.getConnection();
        logger.info("Starting new QueryServer on port " + port);
        try (QueryServer server = new QueryServer(port, databaseConnection)) {

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    logger.info("Shutting Down");
                    logger.info("Closing server connections");
                    server.close();
                    logger.info("Closing database connection");
                    databaseConnection.close();
                    logger.info("Done");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }));

            logger.info("Running server");
            server.run();
        }
    }

}
