package com.ehutson.scratchpad.common.network;

import com.ehutson.scratchpad.common.database.Connection;
import com.ehutson.scratchpad.proto.ScratchpadProtocol.QueryRequest;
import com.ehutson.scratchpad.proto.ScratchpadProtocol.QueryRequest.RequestType;
import com.ehutson.scratchpad.proto.ScratchpadProtocol.QueryResponse;
import com.ehutson.scratchpad.proto.ScratchpadProtocol.QueryResponse.Builder;
import com.ehutson.scratchpad.proto.ScratchpadProtocol.QueryResponse.ResponseType;
import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QueryServerHandler extends SimpleChannelInboundHandler<QueryRequest> {

    private static final Logger logger = LoggerFactory.getLogger(QueryServerHandler.class);

    private Connection databaseConnection;

    public QueryServerHandler(Connection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QueryRequest msg) throws Exception {

        Builder builder = QueryResponse.newBuilder();

        if (msg.getRequestType() == RequestType.GET) {
            doGet(builder, msg);
        } else if (msg.getRequestType() == RequestType.SET) {
            doSet(builder, msg);
        } else if (msg.getRequestType() == RequestType.DEL) {
            doDelete(builder, msg);
        }

        ctx.write(builder.build());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Unknown Exception", cause);
        ctx.close();
    }

    protected void doGet(Builder builder, QueryRequest query) {
        try {
            byte[] value = databaseConnection.getConnection().get(
                    query.getRequestKeyBytes().toByteArray()
            );
            builder.setResponseMsgBytes(ByteString.copyFrom(value));
            builder.setResponseType(ResponseType.OK);
        } catch (Exception e) {
            handleError(builder, "Unable to get value for " + query.getRequestKey(), e);
        }
    }

    protected void doSet(Builder builder, QueryRequest query) {
        try {
            databaseConnection.getConnection().put(
                    query.getRequestKeyBytes().toByteArray(),
                    query.getRequestValueBytes().toByteArray()
            );
            builder.setResponseType(ResponseType.OK);
        } catch (Exception e) {
            handleError(builder, "Unable to set value for " + query.getRequestKey(), e);
        }
    }

    protected void doDelete(Builder builder, QueryRequest query) {
        try {
            databaseConnection.getConnection().delete(query.getRequestKeyBytes().toByteArray());
            builder.setResponseType(ResponseType.OK);
        } catch (Exception e) {
            handleError(builder, "Unable to delete value for " + query.getRequestKey(), e);
        }
    }

    private void handleError(Builder builder, String errorMessage, Exception exception) {
        logger.error(errorMessage, exception);
        builder.setResponseType(ResponseType.ERROR);
        builder.setResponseMsg(errorMessage);
    }
}
