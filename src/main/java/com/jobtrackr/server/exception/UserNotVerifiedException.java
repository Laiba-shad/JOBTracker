// UserNotVerifiedException.java
package com.jobtrackr.server.exception;
public class UserNotVerifiedException extends RuntimeException {
    public UserNotVerifiedException() {
        super("Please verify your email before logging in");
    }
}