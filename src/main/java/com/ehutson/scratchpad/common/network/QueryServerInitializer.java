package com.ehutson.scratchpad.common.network;

import com.ehutson.scratchpad.common.database.Connection;
import com.ehutson.scratchpad.proto.ScratchpadProtocol;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class QueryServerInitializer extends ChannelInitializer<SocketChannel> {

    private Connection databaseConnection;

    public QueryServerInitializer(Connection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(ScratchpadProtocol.QueryRequest.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new QueryServerHandler(databaseConnection));
    }
}
