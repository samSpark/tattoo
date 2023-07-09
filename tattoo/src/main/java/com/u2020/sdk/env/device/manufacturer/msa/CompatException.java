package com.u2020.sdk.env.device.manufacturer.msa;

public class CompatException extends RuntimeException {
    public CompatException(Throwable cause) {
        super(cause);
    }

    public CompatException(String message) {
        super(message);
    }
}
