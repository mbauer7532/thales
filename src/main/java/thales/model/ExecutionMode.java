package thales.model;

/**
 * Created by Neo on 2/29/2016.
 */
public enum ExecutionMode {
    MARKET(0);

    public static ExecutionMode fromTradeModelCode(final int executionModeCode) {

        switch (executionModeCode) {
            case 0: return MARKET;
            default:
                throw new IllegalArgumentException("Illegal execution mode code: " + executionModeCode);
        }
    }

    public int toExecutionModeCode() {

        return mExecutionModeCode;
    }

    ExecutionMode(final int executionModeCode) {

        mExecutionModeCode = executionModeCode;
    }
    private final int mExecutionModeCode;
}
