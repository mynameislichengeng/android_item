package com.haoqee.chatsdk.net;


/**
 * 用户处理自定义异常
 */
public class HaoqeeChatException extends Exception {

	private static final long serialVersionUID = 475022994858770424L;
	
	
	private int statusCode = -1;
	
	
    public HaoqeeChatException(String msg) {
        super(msg);
    }

    public HaoqeeChatException(Exception cause) {
        super(cause);
    }

    public HaoqeeChatException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }

    public HaoqeeChatException(String msg, Exception cause) {
        super(msg, cause);
    }

    public HaoqeeChatException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
    
    
	public HaoqeeChatException() {
		super(); 
	}

	public HaoqeeChatException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public HaoqeeChatException(Throwable throwable) {
		super(throwable);
	}

	public HaoqeeChatException(int statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
}
