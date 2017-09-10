package com.ehutson.scratchpad.common.network;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.ehutson.scratchpad.common.database.ConnectionPool;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class QueryServerInitializer extends ChannelInitializer<SocketChannel> {

	//private static final Logger logger = LoggerFactory.getLogger(QueryServerInitializer.class);

	private ConnectionPool connectionPool;

	public QueryServerInitializer(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new ObjectEncoder(), new QueryServerHandler(connectionPool));
	}

}
