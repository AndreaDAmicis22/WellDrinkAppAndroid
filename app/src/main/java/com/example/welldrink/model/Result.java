package com.example.welldrink.model;

public abstract class Result {

    private Result() {
    }

    public boolean isSuccess() {
        if (this instanceof Success)
            return true;
        else
            return false;
    }

    public static final class Success<T> extends Result {
        private final T res;

        public Success(T res) {
            this.res = res;
        }

        public T getData() {
            return res;
        }
    }

    public static final class Error extends Result {
        private final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }

}
