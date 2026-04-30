// OtpInvalidException.java
package com.jobtrackr.server.exception;
public class OtpInvalidException extends RuntimeException {
    public OtpInvalidException() {
        super("Invalid OTP. Please check and try again");
    }
}