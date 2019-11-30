package cc.catgasm.core.exception;

public class CatChainException extends Exception {
    private ErrorCode errorCode;

    public CatChainException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CatChainException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CatChainException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode.toString();
    }

    public void printErrorCode() {
        System.err.println(errorCode.toString());
    }
}
