package thales.model;

import thales.model.protoBuf.LocalDateTimeProto;
import thales.model.protoBuf.OpenTradeResponseProto;
import thales.model.protoBuf.UserResponseProto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalLong;

/**
 * Created by Neo on 2/26/2016.
 */

public final class Encoders {

    public static byte[] encodeUserRequest(
        final OptionalLong userId,
        final String firstName,
        final String lastName,
        final boolean isEnabled,
        final boolean isReadOnly,
        final boolean sendReports,
        final int leverage,
        final String email,
        final String comment) {

        final UserResponseProto.Builder builder = UserResponseProto.newBuilder();

        userId.ifPresent(builder::setUserId);

        return builder.setFirstName(firstName)
                      .setLastName(lastName)
                      .setIsEnabled(isEnabled)
                      .setIsReadOnly(isReadOnly)
                      .setSendReports(sendReports)
                      .setLeverage(leverage)
                      .setEmail(email)
                      .setComment(comment)
                      .build()
                      .toByteArray();
    }

    public static byte[] encodeUserResponse(final User user) {

        return UserResponseProto.newBuilder()
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

    public static byte[] encodeOpenTradeRequest(
        final long login,
        final String symbol,
        final TradeType tradeType,
        final int volume,
        final OptionalDouble stopLoss,
        final OptionalDouble takeProfit,
        final Optional<LocalDateTime> pendingTradeExpiration,
        final String comment) {

        final OpenTradeResponseProto.Builder builder =
            OpenTradeResponseProto.newBuilder()
                .setLogin(login)
                .setSymbol(symbol)
                .setTradeType(tradeType.toCmd())
                .setVolume(volume)
                .setComment(comment);

        stopLoss.ifPresent(builder::setStopLoss);
        takeProfit.ifPresent(builder::setTakeProfit);
        pendingTradeExpiration.ifPresent(expiration -> builder.setPendingTradeExpiration(toProtoObject(expiration)));

        return builder.build().toByteArray();
    }

    public static byte[] encodeOpenTradeResponse(final OpenTrade openTrade) {

        final OpenTradeResponseProto.Builder builder =
            OpenTradeResponseProto.newBuilder()
                .setTradeId(openTrade.getTradeId())
                .setLogin(openTrade.getLogin())
                .setSymbol(openTrade.getSymbol())
                .setTradeType(openTrade.getTradeType().toCmd())
                .setVolume(openTrade.getVolume())
                .setOpenDateTime(toProtoObject(openTrade.getOpenDateTime()))
                .setOpenPrice(openTrade.getOpenPrice());

        openTrade.getStopLoss().ifPresent(builder::setStopLoss);
        openTrade.getTakeProfit().ifPresent(builder::setTakeProfit);
        openTrade.getPendingOrderExpiration().ifPresent(expiration -> builder.setPendingTradeExpiration(toProtoObject(expiration)));

        return builder.setConvRateOpen(openTrade.getConvRateOpen())
            .setCommission(openTrade.getCommission())
            .setSwaps(openTrade.getSwaps())
            .setProfit(openTrade.getProfit())
            .setTaxes(openTrade.getTaxes())
            .setMarginRate(openTrade.getMarginRate())
            .setTimestamp(toProtoObject(openTrade.getTimestamp()))
            .setComment(openTrade.getComment())
            .build()
            .toByteArray();
    }

    private static LocalDateTimeProto toProtoObject(final LocalDateTime ldt) {

        return LocalDateTimeProto.newBuilder()
            .setYear(ldt.getYear())
            .setMonth(ldt.getMonth().getValue())
            .setDay(ldt.getDayOfMonth())
            .setHour(ldt.getHour())
            .setMinute(ldt.getMinute())
            .setSecond(ldt.getSecond())
            .setNanosecond(ldt.getNano())
            .build();
    }
}
