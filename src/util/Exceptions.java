package util;

public class Exceptions {
    public static class CompteNotFoundException extends RuntimeException {
        public CompteNotFoundException(String msg) { super(msg); }
    }
}
