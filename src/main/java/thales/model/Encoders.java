package thales.model;

import thales.model.protoBuf.UserProto;

/**
 * Created by Neo on 2/26/2016.
 */

public final class Encoders {

    public static byte[] encode(final User user) {

        return UserProto.newBuilder()
                .setUserId(user.getUserId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setIsEnabled(user.getIsEnabled())
                .setIsReadOnly(user.getIsReadOnly())
                .setSendReports(user.getSendReports())
                .setLeverage(user.getLeverage())
                .setEmail(user.getEmail())
                .setComment(user.getComment())
                .setBalance(user.getBalance())
                .setCredit(user.getCredit())
                .setTaxes(user.getTaxes())
                .build()
                .toByteArray();
    }

    public static byte[] encode(final OpenTrade openTrade) {

        return null;
    }
}
