package com.newegg.cassandra.exception;

public class DbException extends Exception{
	private static final long serialVersionUID = 3780917330757333709L;
	
	public DbException(Throwable e, String msg) {
		super(msg,e);
	}
}
