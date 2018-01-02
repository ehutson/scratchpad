package com.ehutson.scratchpad.common.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

public class QueryClient implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(QueryClient.class);

    private String host;
    private int port;

    private QueryClientHandler handle;
    private EventLoopGroup group;
    private Channel channel;

    public QueryClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws InterruptedException {
        group = new NioEventLoopGroup();


        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new QueryClientInitializer());

        channel = bootstrap.connect(host, port).sync().channel();

        handle = channel.pipeline().get(QueryClientHandler.class);

    }

    public void disconnect() {
        logger.debug("Closing connection");
        channel.close();
        group.shutdownGracefully();
        logger.debug("Connection closed");
    }

    public String get(String key) throws Exception {
        return new String(getAsBytes(key));
    }

    public byte[] getAsBytes(String key) throws Exception {
        return handle.get(key.getBytes());
    }

    public void set(String key, String value) throws Exception {
        handle.set(key.getBytes(), value.getBytes());
    }

    public void set(String key, byte[] value) throws Exception {
        handle.set(key.getBytes(), value);
    }

    public void delete(String key) throws Exception {
        handle.delete(key.getBytes());
    }

    @Override
    public void close() {
        try {
            disconnect();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
