package thales.model;

import thales.model.protoBuf.UserProto;

/**
 * Created by Neo on 2/26/2016.
 */

/*
required int64 userId     = 1;
    required string firstName = 2;
    required string lastName  = 3;
    required bool ssEnabled   = 4;
    required bool ssReadOnly  = 5;
    required bool sendReports = 6;
    required int32 leverage   = 7;
    required string email     = 8;
    required string comment   = 9;

    required double balance   = 10;
    required double credit    = 11;
    required double taxes     = 12;
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

        return
    }
}
