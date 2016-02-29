package thales.model;

import java.util.OptionalLong;

/**
 * Created by Neo on 2/29/2016.
 */
public final class createUserRequest {

    public static createUserRequest create(
        final OptionalLong userId,
        final String firstName,
        final String lastName,
        final boolean isEnabled,
        final boolean isReadOnly,
        final boolean sendReports,
        final int leverage,
        final String email,
        final String comment) {

        return new createUserRequest(userId, firstName, lastName, isEnabled, isReadOnly, sendReports, leverage, email, comment);
    }

    public OptionalLong getUserId() {
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

    private createUserRequest(
        final OptionalLong userId,
        final String firstName,
        final String lastName,
        final boolean isEnabled,
        final boolean isReadOnly,
        final boolean sendReports,
        final int leverage,
        final String email,
        final String comment) {

        mUserId = userId;
        mFirstName = firstName;
        mLastName = lastName;
        mIsEnabled = isEnabled;
        mIsReadOnly = isReadOnly;
        mSendReports = sendReports;
        mLeverage = leverage;
        mEmail = email;
        mComment = comment;
    }

    private final OptionalLong mUserId;
    private final String mFirstName;
    private final String mLastName;
    private final boolean mIsEnabled;
    private final boolean mIsReadOnly;
    private final boolean mSendReports;
    private final int mLeverage;
    private final String mEmail;
    private final String mComment;
}
