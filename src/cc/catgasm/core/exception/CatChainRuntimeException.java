package cc.catgasm.core.exception;

public class CatChainRuntimeException extends RuntimeException {
    private ErrorCode errorCode;

    public CatChainRuntimeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CatChainRuntimeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CatChainRuntimeException(String message, Throwable cause, ErrorCode errorCode) {
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
