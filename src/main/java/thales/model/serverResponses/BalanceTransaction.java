package thales.model.serverResponses;

import thales.model.TransactionType;

import java.time.LocalDateTime;

/**
 * Created by Neo on 2/26/2016.
 */
public final class BalanceTransaction {

    public static BalanceTransaction create(final long transactionId, final long login, final TransactionType transactionType, final double amount, final LocalDateTime depositDateTime, final String comment) {

        return new BalanceTransaction(transactionId, login, transactionType, amount, depositDateTime, comment);
    }

    public long getTransactionId() {
        return mTransactionId;
    }

    public long getLogin() {
        return mLogin;
    }

    public TransactionType getTransactionType() {
        return mTransactionType;
    }

    public double getAmount() {
        return mAmount;
    }

    public LocalDateTime getDepositDateTime() {
        return mDepositDateTime;
    }

    public String getComment() {
        return mComment;
    }

    private BalanceTransaction(final long transactionId, final long login, final TransactionType transactionType, final double amount, final LocalDateTime depositDateTime, final String comment) {

        mTransactionId = transactionId;
        mLogin = login;
        mTransactionType = transactionType;
        mAmount = amount;
        mDepositDateTime = depositDateTime;
        mComment = comment;
    }

    private final long mTransactionId;
    private final long mLogin;
    private final TransactionType mTransactionType;
    private final double mAmount;
    private final LocalDateTime mDepositDateTime;
    private final String mComment;
}
