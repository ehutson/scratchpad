package com.ehutson.scratchpad.common.network;

import com.ehutson.scratchpad.common.database.Connection;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

public class QueryServer implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(QueryServer.class);

    private int port;
    private EventLoopGroup serverGroup;
    private EventLoopGroup workerGroup;
    private Connection databaseConnection;

    public QueryServer(int port, Connection databaseConnection) {
        this.port = port;
        this.databaseConnection = databaseConnection;
    }

    public void run() throws Exception {
        logger.debug("Starting server");
        serverGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(serverGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new QueryServerInitializer(databaseConnection));
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
            logger.debug("Server started on port " + port);
        } catch (Exception e) {

        } finally {
            close();
        }

    }

    @Override
    public void close() {
        logger.debug("Closing server");
        try {
            workerGroup.shutdownGracefully();
            serverGroup.shutdownGracefully();
            databaseConnection.close();
            logger.debug("Server closed");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
