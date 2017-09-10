package com.ehutson.test1;

import org.rocksdb.BlockBasedTableConfig;
import org.rocksdb.BloomFilter;
import org.rocksdb.CompactionStyle;
import org.rocksdb.CompressionType;
import org.rocksdb.Filter;
import org.rocksdb.HashLinkedListMemTableConfig;
import org.rocksdb.Options;
import org.rocksdb.RateLimiter;
import org.rocksdb.ReadOptions;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.util.SizeUnit;

public class RocksDbSample {

	static {
		RocksDB.loadLibrary();
	}

	public static void main(final String[] args) {

		final String dbPath = "/tmp/db";

		System.out.println("RocksDBSample");

		try (final Options options = new Options();
				final Filter bloomFilter = new BloomFilter(10);
				final ReadOptions readOptions = new ReadOptions().setFillCache(false);
				// final Statistics stats = new Statistics();
				final RateLimiter rateLimiter = new RateLimiter(10000000, 10000, 10);) {

			try {
				options.setCreateIfMissing(true).setWriteBufferSize(8 * SizeUnit.KB).setMaxWriteBufferNumber(3)
						.setMaxBackgroundCompactions(10).setCompressionType(CompressionType.SNAPPY_COMPRESSION)
						.setCompactionStyle(CompactionStyle.UNIVERSAL);

				options.setMemTableConfig(new HashLinkedListMemTableConfig().setBucketCount(100000));

				final BlockBasedTableConfig tableOptions = new BlockBasedTableConfig();
				tableOptions.setBlockCacheSize(64 * SizeUnit.KB).setFilter(bloomFilter).setCacheNumShardBits(6)
						.setBlockSizeDeviation(5).setBlockRestartInterval(10).setCacheIndexAndFilterBlocks(true)
						.setHashIndexAllowCollision(false).setBlockCacheCompressedSize(64 * SizeUnit.KB)
						.setBlockCacheCompressedNumShardBits(10);

				options.setTableFormatConfig(tableOptions);

				
				try (final RocksDB db = RocksDB.open(options,dbPath)) {
					db.put("hello".getBytes(), "world".getBytes());
					
					final byte[] value = db.get("hello".getBytes());
					System.out.println("Hello, " + value.toString());
					
					db.delete("hello".getBytes());
					final byte[] value2 = db.get("hello".getBytes());
					System.out.println("hello, " + value2.toString());
					
					db.close();
				} catch(final RocksDBException e) {
					System.err.println(e);
				}
				
			} catch (final IllegalArgumentException e) {
				System.err.println(e);
			}

		} catch (final Exception e) {
			System.err.println(e);
		}

	}
}
