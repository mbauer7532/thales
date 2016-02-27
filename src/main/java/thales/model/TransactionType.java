package thales.model;

/**
 * Created by Neo on 2/28/2016.
 */
public enum TransactionType {
    Balance(0),
    Credit(1);

    public int toTransactionCode() {
        return mTransactionCode;
    }

    TransactionType(final int transactionCode) {
        mTransactionCode = transactionCode;
    }

    private final int mTransactionCode;
}
