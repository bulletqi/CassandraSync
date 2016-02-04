package com.newegg.cassandra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.newegg.cassandra.dao.CassandraDao;

public class AppMain {
	private static final Logger LOGGER = Logger.getLogger(AppMain.class);
	
	@Option(name="-sh",usage="source hosts")
	String s_hosts = null;
	@Option(name="-sk",usage="source keyspace")
	String s_keyspaceName = null;
	@Option(name="-sf",usage="source columnFamily")
	String s_columnFamily = null;
	
	@Option(name="-th",usage="target hosts")
	String t_hosts = null;
	@Option(name="-tk",usage="target keyspace")
	String t_keyspaceName = null;
	@Option(name="-tf",usage="target columnFamily")
	String t_columnFamily = null;
	
	public static void main(String[] args) throws IOException {
		AppMain app = new AppMain();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		if(args == null || args.length == 0){
			try {
				System.out.println("Please input == Source == Cassandra Hosts:");
				app.s_hosts = in.readLine();
				
				System.out.println("Please input == Source == Cassandra Keyspace:");
				app.s_keyspaceName = in.readLine();
				
				System.out.println("Please input == Source == Cassandra ColumnFamily:");
				app.s_columnFamily = in.readLine();
				
				System.out.println("===========================================");
				
				System.out.println("Please input == Target == Cassandra Hosts:");
				app.t_hosts = in.readLine();
				
				System.out.println("Please input == Target == Cassandra Keyspace:");
				app.t_keyspaceName = in.readLine();
				
				System.out.println("Please input == Target == Cassandra ColumnFamily:");
				app.t_columnFamily = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error("args Error",e);
				exit(in,-1);
				return;
			}
		}else if(args.length == 1 && (args[0].equals("-h") ||args[0].equals("-help"))){
			String help = "Usage: java -jar this.jar [...]\n"
						+ "---------\n"
						+ "     -sh source hosts\n"
						+ "     -sk source keyspace\n"
						+ "     -sf source columnFamily\n"
						+ "---------\n"
						+ "     -th target hosts\n"
						+ "     -tk target keyspace\n"
						+ "     -tf target columnFamily\n";
			System.out.println(help);
			exit(in, 0);
			return;
		}else{
			CmdLineParser parser = new CmdLineParser(app);
			try {
				parser.parseArgument(args);
			} catch (CmdLineException e) {
				e.printStackTrace();
				LOGGER.error("parase error", new CmdLineException(parser,e));
				exit(in, -1);
				return;
			}
		}
		LOGGER.info("Please Wait ...");
		LOGGER.info("connect source:" + app.s_hosts + " >>" + app.s_keyspaceName + ":" + app.s_columnFamily);
		LOGGER.info("connect target:" + app.t_hosts + " >>" + app.t_keyspaceName + ":" + app.t_columnFamily);
		
		try {
			CassandraDao source = getCassandraDao(app.s_hosts, app.s_keyspaceName, app.s_columnFamily);
			CassandraDao target = getCassandraDao(app.t_hosts, app.t_keyspaceName, app.t_columnFamily);
			LOGGER.info("Source Data Count:" + source.count());
			LOGGER.info("clear target ");
			target.truncate();
			LOGGER.info("Please Wait ...");
			source.to(target);
			LOGGER.info("Target Data Count:" + target.count());
		} catch (Exception e) {
			LOGGER.error("Sync Error!", e);
		}
		exit(in,0);
	}
	
	static CassandraDao getCassandraDao(String hosts, String keyspaceName,String columnFamily){
		CassandraHostConfigurator hostConfigurator = new CassandraHostConfigurator(hosts);
		Cluster cluster = HFactory.getOrCreateCluster(hosts, hostConfigurator);
		Keyspace keyspace = HFactory.createKeyspace(keyspaceName, cluster);
		CassandraDao cassandraDao = new CassandraDao(keyspace, cluster, columnFamily);
		return cassandraDao;
	}
	
	static void exit(BufferedReader in,int code) throws IOException{
		System.out.println("please any key to continue!");
		in.read();
		in.close();
		System.exit(code);
	}
}