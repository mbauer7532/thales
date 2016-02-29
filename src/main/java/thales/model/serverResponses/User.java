package thales.model.serverResponses;

/**
 * Created by Neo on 2/26/2016.
 */
public final class User {

    public static User create(
        final long userId,
        final String firstName,
        final String lastName,
        final boolean isEnabled,
        final boolean isReadOnly,
        final boolean sendReports,
        final int leverage,
        final String email,
        final String comment,
        final double balance,
        final double credit,
        final double taxes) {

        return new User(userId, firstName, lastName, isEnabled, isReadOnly, sendReports, leverage, email, comment, balance, credit, taxes);
    }

    public long getUserId() {
        return mUserId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public boolean getIsEnabled() {
        return mIsEnabled;
    }

    public boolean getIsReadOnly() {
        return mIsReadOnly;
    }

    public boolean getSendReports() {
        return mSendReports;
    }

    public int getLeverage() {
        return mLeverage;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getComment() {
        return mComment;
    }

    public double getBalance() {
        return mBalance;
    }

    public double getCredit() {
        return mCredit;
    }

    public double getTaxes() {
        return mTaxes;
    }

    private User(
        final long userId,
        final String firstName,
        final String lastName,
        final boolean isEnabled,
        final boolean isReadOnly,
        final boolean sendReports,
        final int leverage,
        final String email,
        final String comment,
        final double balance,
        final double credit,
        final double taxes) {

        mUserId = userId;
        mFirstName = firstName;
        mLastName = lastName;
        mIsEnabled = isEnabled;
        mIsReadOnly = isReadOnly;
        mSendReports = sendReports;
        mLeverage = leverage;
        mEmail = email;
        mComment = comment;
        mBalance = balance;
        mCredit = credit;
        mTaxes = taxes;
    }

    private final long mUserId;
    private final String mFirstName;
    private final String mLastName;
    private final boolean mIsEnabled;
    private final boolean mIsReadOnly;
    private final boolean mSendReports;
    private final int mLeverage;
    private final String mEmail;
    private final String mComment;

    private final double mBalance;
    private final double mCredit;
    private final double mTaxes;
}
