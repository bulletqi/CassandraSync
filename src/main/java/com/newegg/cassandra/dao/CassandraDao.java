package com.newegg.cassandra.dao;

import java.util.List;
import org.apache.log4j.Logger;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import com.newegg.cassandra.exception.DbException;

/**
 * Cassandra Sync
 * @author Fritz.F.yan
 */
public class CassandraDao{
	private static final Logger LOGGER = Logger.getLogger(CassandraDao.class);
	protected Keyspace keyspace;
	private Cluster cluster;
	//表名称
	String columnFamily;
	//ID字段序列化方式
	
	public CassandraDao(Keyspace keyspace,Cluster cluster,String columnFamily){
		this.columnFamily = columnFamily;
		this.cluster = cluster;
		this.keyspace = keyspace;
	}
	
	public long count() throws DbException {
		try {
			int rowCount = 100;
			RangeSlicesQuery<byte[], byte[], byte[]> query = HFactory.createRangeSlicesQuery(keyspace,BytesArraySerializer.get(),BytesArraySerializer.get(),BytesArraySerializer.get());
			query.setColumnFamily(columnFamily);
			query.setKeys(null, null);
			query.setRange(null, null, false,1000);
			query.setRowCount(rowCount);
			boolean more = true;
			long count = 0;
			while(more){
				QueryResult<OrderedRows<byte[], byte[], byte[]>> result = query.execute();
				OrderedRows<byte[], byte[], byte[]> orderedRows = result.get();
				if(orderedRows == null){
					more = false;
					break;
				}
				List<Row<byte[], byte[], byte[]>> rowList = orderedRows.getList();
				if(rowList.size() < rowCount){
					more = false;
				}else{
					query.setKeys(orderedRows.peekLast().getKey(), null);
				}
				for (Row<byte[], byte[], byte[]> row : rowList) {
					List<HColumn<byte[], byte[]>> columns = row.getColumnSlice().getColumns();
					if(0 == columns.size()){
						continue;
					}
					count++;
				}
			}
			return count;
		} catch (Exception e) {
			throw new DbException(e, "Count Exception");
		}
	}
	
	public long insert(List<Row<byte[], byte[], byte[]>> rowList) throws DbException{
		try {
			long count = 0;
			Mutator<byte[]> m = HFactory.createMutator(keyspace, BytesArraySerializer.get());
			for (Row<byte[], byte[], byte[]> row : rowList) {
				List<HColumn<byte[], byte[]>> columns = row.getColumnSlice().getColumns();
				if(0 == columns.size()){
					continue;
				}
				byte[] id = row.getKey();
				for (HColumn<byte[], byte[]> hColumn : columns) {
					m.addInsertion(id, columnFamily, hColumn);
				}
				count++;
			}
			m.execute();
			LOGGER.info("sync data size:" + count);
			return count;
		}catch (Exception e) {
			throw new DbException(e,"Insert Data Error");
		}
	}
	
	public void to(CassandraDao target) throws DbException{
		try {
			int rowCount = 100;
			RangeSlicesQuery<byte[], byte[], byte[]> query = HFactory.createRangeSlicesQuery(keyspace,BytesArraySerializer.get(),BytesArraySerializer.get(),BytesArraySerializer.get());
			query.setColumnFamily(columnFamily);
			query.setKeys(null, null);
			query.setRange(null, null, false,1000);
			query.setRowCount(rowCount);
			boolean more = true;
			long count = 0;
			while(more){
				QueryResult<OrderedRows<byte[], byte[], byte[]>> result = query.execute();
				OrderedRows<byte[], byte[], byte[]> orderedRows = result.get();
				if(orderedRows == null){
					more = false;
					break;
				}
				List<Row<byte[], byte[], byte[]>> rowList = orderedRows.getList();
				if(rowList.size() < rowCount){
					more = false;
				}else{
					query.setKeys(orderedRows.peekLast().getKey(), null);
				}
				count += target.insert(rowList);
			}
			LOGGER.info("Sync done! count:"+ count);
		} catch (Exception e) {
			throw new DbException(e, "Sync Exception");
		}
	}
	
	
	/**
	 * 删除本表所有数据
	 */
	public void truncate() throws DbException{
		try {
			cluster.truncate(keyspace.getKeyspaceName(), columnFamily);
			LOGGER.info("Truncate done!");
		} catch (Exception e) {
			throw new DbException(e, "Truncate Error!");
		}
	}
}
