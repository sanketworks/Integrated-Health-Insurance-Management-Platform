package com.nt.exceptions;

public class SsaWebApiException extends RuntimeException {
	public SsaWebApiException() {
        super();
    }

    public SsaWebApiException(String msg) {
        super(msg);
    }
}
