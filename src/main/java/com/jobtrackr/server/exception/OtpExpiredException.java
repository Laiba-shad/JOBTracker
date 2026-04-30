// OtpExpiredException.java
package com.jobtrackr.server.exception;
public class OtpExpiredException extends RuntimeException {
    public OtpExpiredException() {
        super("OTP has expired. Please request a new one");
    }
}