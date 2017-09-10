package com.ehutson.scratchpad.common.database;

import org.rocksdb.util.SizeUnit;

public class DbOptions {
	
	private static final boolean DEFAULT_CREATE_IF_MISSING = true;
	private static final long DEFAULT_WRITE_BUFFER_SIZE = 8 * SizeUnit.KB;
	private static final int DEFAULT_MAX_WRITE_BUFFER_NUMBER = 3;
	private static final int DEFAULT_MAX_BACKGROUND_COMPACTIONS = 10;
	private static final long DEFAULT_MEM_TABLE_BUCKET_COUNT = 100000;
	private static final long DEFAULT_BLOCK_CACHE_SIZE = 64 * SizeUnit.KB;
	private static final int DEFAULT_CACHE_NUM_SHARD_BITS = 6;
	private static final int DEFAULT_BLOCK_SIZE_DEVIATION = 5;
	private static final int DEFAULT_BLOCK_RESTART_INTERVAL = 10;
	private static final boolean DEFAULT_CACHE_INDEX_AND_FILTER_BLOCKS = true;
	private static final boolean DEFAULT_HASH_INDEX_ALLOW_COLLISION = false;
	private static final long DEFAULT_BLOCK_CACHE_COMPESSED_SIZE = 64 * SizeUnit.KB;
	private static final int DEFAULT_BLOCK_CACHE_COMPRESSED_NUM_SHARD_BITS = 10;
	private static final boolean DEFAULT_UNIVERSAL_COMPACTION = true;
	
	
	private String dbName;
	private String folderPath;
	
	private boolean createIfMissing = DEFAULT_CREATE_IF_MISSING;
	private long writeBufferSize = DEFAULT_WRITE_BUFFER_SIZE;
	private int maxWriteBufferNumber = DEFAULT_MAX_WRITE_BUFFER_NUMBER;
	private int maxBackgroundCompactions = DEFAULT_MAX_BACKGROUND_COMPACTIONS;
	private long memTableBucketCount = DEFAULT_MEM_TABLE_BUCKET_COUNT;
	private long blockCacheSize = DEFAULT_BLOCK_CACHE_SIZE;
	private int cacheNumShardBits = DEFAULT_CACHE_NUM_SHARD_BITS;
	private int blockSizeDeviation = DEFAULT_BLOCK_SIZE_DEVIATION;
	private int blockRestartInterval = DEFAULT_BLOCK_RESTART_INTERVAL;
	private boolean cacheIndexAndFilterBlocks = DEFAULT_CACHE_INDEX_AND_FILTER_BLOCKS;
	private boolean hashIndexAllowCollision = DEFAULT_HASH_INDEX_ALLOW_COLLISION;
	private long blockCacheCompressedSize = DEFAULT_BLOCK_CACHE_COMPESSED_SIZE;
	private int blockCacheCompressedNumShardBits = DEFAULT_BLOCK_CACHE_COMPRESSED_NUM_SHARD_BITS;
	private boolean universalCompaction = DEFAULT_UNIVERSAL_COMPACTION;
	
	
	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}
	/**
	 * @param dbName the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	/**
	 * @return the folderPath
	 */
	public String getFolderPath() {
		return folderPath;
	}
	/**
	 * @param folderPath the folderPath to set
	 */
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	/**
	 * @return the createIfMissing
	 */
	public boolean isCreateIfMissing() {
		return createIfMissing;
	}
	/**
	 * @param createIfMissing the createIfMissing to set
	 */
	public void setCreateIfMissing(boolean createIfMissing) {
		this.createIfMissing = createIfMissing;
	}
	/**
	 * @return the writeBufferSize
	 */
	public long getWriteBufferSize() {
		return writeBufferSize;
	}
	/**
	 * @param writeBufferSize the writeBufferSize to set
	 */
	public void setWriteBufferSize(long writeBufferSize) {
		this.writeBufferSize = writeBufferSize;
	}
	/**
	 * @return the maxWriteBufferNumber
	 */
	public int getMaxWriteBufferNumber() {
		return maxWriteBufferNumber;
	}
	/**
	 * @param maxWriteBufferNumber the maxWriteBufferNumber to set
	 */
	public void setMaxWriteBufferNumber(int maxWriteBufferNumber) {
		this.maxWriteBufferNumber = maxWriteBufferNumber;
	}
	/**
	 * @return the maxBackgroundCompactions
	 */
	public int getMaxBackgroundCompactions() {
		return maxBackgroundCompactions;
	}
	/**
	 * @param maxBackgroundCompactions the maxBackgroundCompactions to set
	 */
	public void setMaxBackgroundCompactions(int maxBackgroundCompactions) {
		this.maxBackgroundCompactions = maxBackgroundCompactions;
	}
	/**
	 * @return the memTableBucketCount
	 */
	public long getMemTableBucketCount() {
		return memTableBucketCount;
	}
	/**
	 * @param memTableBucketCount the memTableBucketCount to set
	 */
	public void setMemTableBucketCount(long memTableBucketCount) {
		this.memTableBucketCount = memTableBucketCount;
	}
	/**
	 * @return the blockCacheSize
	 */
	public long getBlockCacheSize() {
		return blockCacheSize;
	}
	/**
	 * @param blockCacheSize the blockCacheSize to set
	 */
	public void setBlockCacheSize(long blockCacheSize) {
		this.blockCacheSize = blockCacheSize;
	}
	/**
	 * @return the cacheNumShardBits
	 */
	public int getCacheNumShardBits() {
		return cacheNumShardBits;
	}
	/**
	 * @param cacheNumShardBits the cacheNumShardBits to set
	 */
	public void setCacheNumShardBits(int cacheNumShardBits) {
		this.cacheNumShardBits = cacheNumShardBits;
	}
	/**
	 * @return the blockSizeDeviation
	 */
	public int getBlockSizeDeviation() {
		return blockSizeDeviation;
	}
	/**
	 * @param blockSizeDeviation the blockSizeDeviation to set
	 */
	public void setBlockSizeDeviation(int blockSizeDeviation) {
		this.blockSizeDeviation = blockSizeDeviation;
	}
	/**
	 * @return the blockRestartInterval
	 */
	public int getBlockRestartInterval() {
		return blockRestartInterval;
	}
	/**
	 * @param blockRestartInterval the blockRestartInterval to set
	 */
	public void setBlockRestartInterval(int blockRestartInterval) {
		this.blockRestartInterval = blockRestartInterval;
	}
	/**
	 * @return the cacheIndexAndFilterBlocks
	 */
	public boolean isCacheIndexAndFilterBlocks() {
		return cacheIndexAndFilterBlocks;
	}
	/**
	 * @param cacheIndexAndFilterBlocks the cacheIndexAndFilterBlocks to set
	 */
	public void setCacheIndexAndFilterBlocks(boolean cacheIndexAndFilterBlocks) {
		this.cacheIndexAndFilterBlocks = cacheIndexAndFilterBlocks;
	}
	/**
	 * @return the hashIndexAllowCollision
	 */
	public boolean isHashIndexAllowCollision() {
		return hashIndexAllowCollision;
	}
	/**
	 * @param hashIndexAllowCollision the hashIndexAllowCollision to set
	 */
	public void setHashIndexAllowCollision(boolean hashIndexAllowCollision) {
		this.hashIndexAllowCollision = hashIndexAllowCollision;
	}
	/**
	 * @return the blockCacheCompressedSize
	 */
	public long getBlockCacheCompressedSize() {
		return blockCacheCompressedSize;
	}
	/**
	 * @param blockCacheCompressedSize the blockCacheCompressedSize to set
	 */
	public void setBlockCacheCompressedSize(long blockCacheCompressedSize) {
		this.blockCacheCompressedSize = blockCacheCompressedSize;
	}
	/**
	 * @return the blockCacheCompressedNumShardBits
	 */
	public int getBlockCacheCompressedNumShardBits() {
		return blockCacheCompressedNumShardBits;
	}
	/**
	 * @param blockCacheCompressedNumShardBits the blockCacheCompressedNumShardBits to set
	 */
	public void setBlockCacheCompressedNumShardBits(int blockCacheCompressedNumShardBits) {
		this.blockCacheCompressedNumShardBits = blockCacheCompressedNumShardBits;
	}
	/**
	 * @return the universalCompaction
	 */
	public boolean isUniversalCompaction() {
		return universalCompaction;
	}
	/**
	 * @param universalCompaction the universalCompaction to set
	 */
	public void setUniversalCompaction(boolean universalCompaction) {
		this.universalCompaction = universalCompaction;
	}
	
}
