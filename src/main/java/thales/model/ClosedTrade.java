package thales.model;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Created by Neo on 2/26/2016.
 */
public final class ClosedTrade {

    public static ClosedTrade create(
        final long tradeId,
        final long login,
        final String symbol,
        final TradeType tradeType,
        final int volume,
        final LocalDateTime openDateTime,
        final double openPrice,
        final LocalDateTime closeDateTime,
        final double closePrice,
        final OptionalDouble stopLoss,
        final OptionalDouble takeProfit,
        final Optional<LocalDateTime> pendingOrderExpiration,
        final double convRateOpen,
        final double convRateClosed,
        final double commission,
        final double swaps,
        final double profit,
        final double taxes,
        final double marginRate,
        final LocalDateTime timestamp,
        final String comment) {

        return new ClosedTrade(tradeId, login, symbol, tradeType, volume, openDateTime, openPrice, closeDateTime, closePrice, stopLoss, takeProfit, pendingOrderExpiration, convRateOpen, convRateClosed, commission, swaps, profit, taxes, marginRate, timestamp, comment);
    }

    private ClosedTrade(
        final long tradeId,
        final long login,
        final String symbol,
        final TradeType tradeType,
        final int volume,
        final LocalDateTime openDateTime,
        final double openPrice,
        final LocalDateTime closeDateTime,
        final double closePrice,
        final OptionalDouble stopLoss,
        final OptionalDouble takeProfit,
        final Optional<LocalDateTime> pendingOrderExpiration,
        final double convRateOpen,
        final double convRateClosed,
        final double commission,
        final double swaps,
        final double profit,
        final double taxes,
        final double marginRate,
        final LocalDateTime timestamp,
        final String comment) {

        mTradeId = tradeId;
        mLogin = login;
        mSymbol = symbol;
        mTradeType = tradeType;
        mVolume = volume;
        mOpenDateTime = openDateTime;
        mOpenPrice = openPrice;
        mCloseDateTime = closeDateTime;
        mClosePrice = closePrice;
        mStopLoss = stopLoss;
        mTakeProfit = takeProfit;
        mPendingOrderExpiration = pendingOrderExpiration;
        mConvRateOpen = convRateOpen;
        mConvRateClosed = convRateClosed;
        mCommission = commission;
        mSwaps = swaps;
        mProfit = profit;
        mTaxes = taxes;
        mMarginRate = marginRate;
        mTimestamp = timestamp;
        mComment = comment;
    }

    private final long mTradeId;
    private final long mLogin;
    private final String mSymbol;
    private final TradeType mTradeType;
    private final int mVolume;
    private final LocalDateTime mOpenDateTime;
    private final double mOpenPrice;
    private final LocalDateTime mCloseDateTime;
    private final double mClosePrice;
    private final OptionalDouble mStopLoss;
    private final OptionalDouble mTakeProfit;
    private final Optional<LocalDateTime> mPendingOrderExpiration;
    private final double mConvRateOpen;   // convertation rates from profit currency to group deposit currency
    private final double mConvRateClosed; // convertation rates from profit currency to group deposit currency
    private final double mCommission;
    private final double mSwaps;
    private final double mProfit;
    private final double mTaxes;
    private final double mMarginRate;       // margin convertation rate (rate of convertation from margin currency to deposit one)
    private final LocalDateTime mTimestamp; // timestamp
    private final String mComment;
}
