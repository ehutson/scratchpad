package com.ehutson.scratchpad.common.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ehutson.scratchpad.common.Query;
import com.ehutson.scratchpad.common.Query.QueryResult;
import com.ehutson.scratchpad.common.database.Connection;
import com.ehutson.scratchpad.common.database.ConnectionPool;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class QueryServerHandler extends SimpleChannelInboundHandler<Query> {

	private static final Logger logger = LoggerFactory.getLogger(QueryServerHandler.class);

	private ConnectionPool connectionPool;

	public QueryServerHandler(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Query msg) throws Exception {
		switch (msg.getQueryType()) {
		case PUT:
			doPut(msg);
			break;
		case GET:
			doGet(msg);
			break;
		case UPDATE:
			doUpdate(msg);
			break;
		case DELETE:
			doDelete(msg);
			break;
		default:
			break;
		}

		ctx.writeAndFlush(msg);
	}

	protected void doGet(Query query) {
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
			byte[] value = connection.getConnection().get(query.getKeyBytes());
			query.setValue(value);
			query.setQueryResult(QueryResult.OK);
		} catch (Exception e) {
			logger.error("Unable to get value", e);
			query.setQueryResult(QueryResult.ERROR);
		} finally {
			if (connection != null) {
				try {
					connectionPool.releaseConnection(connection);
				} catch (Exception e) {
					logger.debug("Unable to release connection", e);
				}
			}
		}
	}

	protected void doPut(Query query) {
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
			connection.getConnection().put(query.getKeyBytes(), query.getValue());
			query.setQueryResult(QueryResult.OK);
		} catch (Exception e) {
			logger.error("Unable to get value", e);
			query.setQueryResult(QueryResult.ERROR);
		} finally {
			if (connection != null) {
				try {
					connectionPool.releaseConnection(connection);
				} catch (Exception e) {
					logger.debug("Unable to release connection", e);
				}
			}
		}
	}

	protected void doUpdate(Query query) {
		doPut(query);
	}

	protected void doDelete(Query query) {
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
			connection.getConnection().delete(query.getKeyBytes());
			query.setQueryResult(QueryResult.OK);
		} catch (Exception e) {
			logger.error("Unable to get value", e);
			query.setQueryResult(QueryResult.ERROR);
		} finally {
			if (connection != null) {
				try {
					connectionPool.releaseConnection(connection);
				} catch (Exception e) {
					logger.debug("Unable to release connection", e);
				}
			}
		}
	}
}
