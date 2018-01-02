package com.ehutson.scratchpad.common.database;

import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DbConnectionFactory {
    private static final Logger logger = LoggerFactory.getLogger(DbConnectionFactory.class);

    private DbOptions options;

    public DbConnectionFactory(DbOptions options) {
        this.options = options;
    }

    public Connection getConnection() throws RocksDBException {
        //Env re = RocksEnv.getDefault();
        //re.setBackgroundThreads(number, RocksEnv.COMPACTION_POOL)
        //re.setBackgroundThreads(number, RocksEnv.FLUSH_POOL)

        Options rOptions = new Options();

        Filter filter = new BloomFilter(10);

        if (options.isUniversalCompaction()) {
            rOptions.setCompactionStyle(CompactionStyle.UNIVERSAL);
        }

        String dbName = options.getDbName();
        String dbPath = options.getFolderPath() + "/" + dbName;

        rOptions.setCreateIfMissing(options.isCreateIfMissing());
        rOptions.setWriteBufferSize(options.getWriteBufferSize());
        rOptions.setMaxWriteBufferNumber(options.getMaxWriteBufferNumber());
        rOptions.setMaxBackgroundCompactions(options.getMaxBackgroundCompactions());
        rOptions.setCompressionType(CompressionType.SNAPPY_COMPRESSION);

        //rOptions.setMemTableConfig(new HashLinkedListMemTableConfig().setBucketCount(100000));

        final BlockBasedTableConfig tableOptions = new BlockBasedTableConfig();
        tableOptions.setBlockCacheSize(options.getBlockCacheCompressedSize());
        tableOptions.setFilter(filter);
        tableOptions.setCacheNumShardBits(options.getCacheNumShardBits());
        tableOptions.setBlockSizeDeviation(options.getBlockSizeDeviation());
        tableOptions.setBlockRestartInterval(options.getBlockRestartInterval());
        tableOptions.setCacheIndexAndFilterBlocks(options.isCacheIndexAndFilterBlocks());
        tableOptions.setHashIndexAllowCollision(options.isHashIndexAllowCollision());
        tableOptions.setBlockCacheCompressedSize(options.getBlockCacheCompressedSize());
        tableOptions.setBlockCacheCompressedNumShardBits(options.getBlockCacheCompressedNumShardBits());

        rOptions.setTableFormatConfig(tableOptions);

        logger.warn("Creating connection to " + dbPath);
        Connection connection = new Connection(RocksDB.open(rOptions, dbPath));
        return connection;
    }
}
