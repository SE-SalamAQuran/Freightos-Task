package vending;

public class NotPaidFullException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String message;
    private double remaining;

    public NotPaidFullException(String message, double remaining) {
        this.message = message;
        this.remaining = remaining;
    }

    public double getRemaining() {
        return remaining;
    }

    @Override
    public String getMessage() {
        return message + remaining;
    }

}
