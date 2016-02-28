package thales.model;

import thales.model.protoBuf.serverToClient.GetOpenTradeResponse;
import thales.model.protoBuf.serverToClient.GetUserResponse;
import thales.model.protoBuf.serverToClient.LocalDateTimeProto;

import java.time.LocalDateTime;

/**
 * Created by Neo on 2/28/2016.
 */
public class EncoderSer2Cli {

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

    public static byte[] encodeGetUserResponse(final User user) {

        return GetUserResponse.newBuilder()
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

    public static byte[] encodeGetOpenTradeResponse(final OpenTrade openTrade) {

        final GetOpenTradeResponse.Builder builder =
            GetOpenTradeResponse.newBuilder()
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
}
