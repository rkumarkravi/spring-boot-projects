package com.rk.animestream.exceptions;

public class ExpiredJwtTokenException extends Exception {
    public ExpiredJwtTokenException(String s) {
        super(s);
    }
}
