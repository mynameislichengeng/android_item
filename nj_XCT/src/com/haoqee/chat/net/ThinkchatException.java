package com.haoqee.chat.net;


/**
 * 用户处理自定义异常
 */
public class ThinkchatException extends Exception {

	private static final long serialVersionUID = 475022994858770424L;
	
	
	private int statusCode = -1;
	
	
    public ThinkchatException(String msg) {
        super(msg);
    }

    public ThinkchatException(Exception cause) {
        super(cause);
    }

    public ThinkchatException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }

    public ThinkchatException(String msg, Exception cause) {
        super(msg, cause);
    }

    public ThinkchatException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
    
    
	public ThinkchatException() {
		super(); 
	}

	public ThinkchatException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ThinkchatException(Throwable throwable) {
		super(throwable);
	}

	public ThinkchatException(int statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
}
