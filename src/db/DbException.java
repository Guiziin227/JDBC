package db;

public class DbException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DbException(String msg) {
        super(msg);
    }

    public DbException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DbException(Throwable cause) {
        super(cause);
    }
}
