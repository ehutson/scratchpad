package com.ehutson.test1;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;


public class RocksDbSampleSimple {

	public static void main(String[] args) {
		System.out.println("Loading library");
		RocksDB.loadLibrary();
		
		try(final Options options = new Options().setCreateIfMissing(true)) {
			System.out.println("Creating DB");
			try (final RocksDB db = RocksDB.open(options, "/tmp/db")) {
				System.out.println("doing something");
				
				db.put("Hello".getBytes(), "World".getBytes());
				final byte[] retval = db.get("Hello".getBytes());
				System.out.println("Hello, " + new String(retval));
				System.out.println("removing hello");
				db.delete("Hello".getBytes());
				final byte[] retval2 = db.get("Hello".getBytes());
				assert(retval2 == null);
			}
		} catch (Exception e) {
			System.err.println(e);
		}

	}

}
