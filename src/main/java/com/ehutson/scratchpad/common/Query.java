package com.ehutson.scratchpad.common;

import java.io.Serializable;

public class Query implements Serializable {

    private static final long serialVersionUID = 1L;
    private String key;
    private byte[] value;
    private QueryType queryType;
    private QueryResult queryResult;

    public Query() {
        key = "";
        value = null;
        queryType = QueryType.NIL;
        queryResult = QueryResult.NIL;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getKeyBytes() {
        return key.getBytes();
    }

    /**
     * @return the value
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(byte[] value) {
        this.value = value;
    }

    /**
     * @return the queryType
     */
    public QueryType getQueryType() {
        return queryType;
    }

    /**
     * @param queryType the queryType to set
     */
    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    /**
     * @return the queryResult
     */
    public QueryResult getQueryResult() {
        return queryResult;
    }

    /**
     * @param queryResult the queryResult to set
     */
    public void setQueryResult(QueryResult queryResult) {
        this.queryResult = queryResult;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Query [key=" + key + ", value=" + new String(value) + ", queryType=" + queryType + ", queryResult="
                + queryResult + "]";
    }

    public enum QueryType {
        GET, PUT, UPDATE, DELETE, NIL
    }

    public enum QueryResult {
        OK, ERROR, NIL
    }

}
