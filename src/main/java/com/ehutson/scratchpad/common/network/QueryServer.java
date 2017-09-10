package com.ehutson.scratchpad.common.network;

import java.io.Closeable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ehutson.scratchpad.common.database.ConnectionPool;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class QueryServer implements Closeable {

	private static final Logger logger = LoggerFactory.getLogger(QueryServer.class);

	private int port;
	private ConnectionPool connectionPool;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	public QueryServer(int port, ConnectionPool connectionPool) {
		this.port = port;
		this.connectionPool = connectionPool;
	}

	public void run() throws Exception {
		logger.debug("Starting server");
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();

			bootstrap.group(bossGroup, workerGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.childHandler(new QueryServerInitializer(connectionPool));
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
			bossGroup.shutdownGracefully();
			connectionPool.closeAll();
			logger.debug("Server closed");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
