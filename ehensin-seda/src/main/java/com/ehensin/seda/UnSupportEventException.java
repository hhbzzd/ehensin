package com.ehensin.seda;


public class UnSupportEventException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UnSupportEventException(String msg) {
        super(msg);
    }
	
	public UnSupportEventException(Throwable cause) {
		super(cause);
	}

	public UnSupportEventException(Exception e, String msg) {
		super(msg,e);
	}
}
