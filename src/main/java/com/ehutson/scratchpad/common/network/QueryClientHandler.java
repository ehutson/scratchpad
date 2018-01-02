package com.ehutson.scratchpad.common.network;

import com.ehutson.scratchpad.proto.ScratchpadProtocol;
import com.ehutson.scratchpad.proto.ScratchpadProtocol.QueryRequest;
import com.ehutson.scratchpad.proto.ScratchpadProtocol.QueryRequest.RequestType;
import com.ehutson.scratchpad.proto.ScratchpadProtocol.QueryResponse;
import com.ehutson.scratchpad.proto.ScratchpadProtocol.QueryResponse.ResponseType;
import com.google.protobuf.ByteString;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class QueryClientHandler extends SimpleChannelInboundHandler<QueryResponse> {

    private static final Logger logger = LoggerFactory.getLogger(QueryClientHandler.class);
    BlockingQueue<QueryResponse> responses = new LinkedBlockingDeque<>();
    private Channel channel;
    private QueryResponse response;

    public byte[] get(byte[] key) throws Exception {
        QueryResponse response = sendRequest(
                QueryRequest.newBuilder()
                        .setRequestType(RequestType.GET)
                        .setRequestKeyBytes(ByteString.copyFrom(key))
                        .build()
        );

        if (response.getResponseType() == ResponseType.OK) {
            return response.getResponseMsgBytes().toByteArray();
        } else {
            throw new Exception(response.getResponseMsg());
        }
    }

    public void set(byte[] key, byte[] value) throws Exception {
        QueryResponse response = sendRequest(
                QueryRequest.newBuilder()
                        .setRequestType(RequestType.SET)
                        .setRequestKeyBytes(ByteString.copyFrom(key))
                        .setRequestValueBytes(ByteString.copyFrom(value))
                        .build()
        );

        if (response.getResponseType() == ResponseType.ERROR) {
            throw new Exception(response.getResponseMsg());
        }
    }

    public void delete(byte[] key) throws Exception {
        QueryResponse response = sendRequest(
                QueryRequest.newBuilder()
                        .setRequestType(RequestType.DEL)
                        .setRequestKeyBytes(ByteString.copyFrom(key))
                        .build()
        );

        if (response.getResponseType() == ResponseType.ERROR) {
            throw new Exception(response.getResponseMsg());
        }
    }

    private QueryResponse sendRequest(QueryRequest request) {
        channel.writeAndFlush(request);

        boolean interrupted = false;
        for (; ; ) {
            try {
                response = responses.take();
                break;
            } catch (InterruptedException ignore) {
                interrupted = true;
            }
        }

        if (interrupted) {
            Thread.currentThread().interrupt();
        }

        return response;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ScratchpadProtocol.QueryResponse msg) throws Exception {
        responses.add(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Unknown Exception", cause);
        ctx.close();
    }


}
