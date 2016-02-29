package thales.model.serverRequests;

import thales.model.TradeType;
import thales.model.protoBuf.clientToServer.CreateUserRequest;
import thales.model.protoBuf.clientToServer.LocalDateTimeProto;
import thales.model.protoBuf.clientToServer.OpenTradeRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalLong;

/**
 * Created by Neo on 2/28/2016.
 */
public class EncoderCli2Ser {

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

    public static byte[] encodeCreateUserRequest(
        final OptionalLong userId,
        final String firstName,
        final String lastName,
        final boolean isEnabled,
        final boolean isReadOnly,
        final boolean sendReports,
        final int leverage,
        final String email,
        final String comment) {

        final CreateUserRequest.Builder builder = CreateUserRequest.newBuilder();

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

    public static byte[] encodeOpenTradeRequest(
        final long login,
        final String symbol,
        final TradeType tradeType,
        final int volume,
        final OptionalDouble stopLoss,
        final OptionalDouble takeProfit,
        final Optional<LocalDateTime> pendingTradeExpiration,
        final String comment) {

        final OpenTradeRequest.Builder builder =
            OpenTradeRequest.newBuilder()
                .setLogin(login)
                .setSymbol(symbol)
                .setTradeType(tradeType.toCmd())
                .setVolume(volume);

        stopLoss.ifPresent(builder::setStopLoss);
        takeProfit.ifPresent(builder::setTakeProfit);
        pendingTradeExpiration.ifPresent(expiration -> builder.setPendingTradeExpiration(toProtoObject(expiration)));

        return builder.setComment(comment)
                      .build().toByteArray();
    }
}
