package thales.model.serverResponses;

import thales.model.TradeType;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Created by Neo on 2/24/2016.
 */
public final class OpenTrade {

    public static OpenTrade create(
        final long tradeId,
        final long login,
        final String symbol,
        final TradeType tradeType,
        final int volume,
        final LocalDateTime openDateTime,
        final double openPrice,
        final OptionalDouble stopLoss,
        final OptionalDouble takeProfit,
        final Optional<LocalDateTime> pendingOrderExpiration,
        final double convRateOpen,
        final double commission,
        final double swaps,
        final double profit,
        final double taxes,
        final double marginRate,
        final LocalDateTime timestamp,
        final String comment) {

        return new OpenTrade(tradeId, login, symbol, tradeType, volume, openDateTime, openPrice, stopLoss, takeProfit, pendingOrderExpiration, convRateOpen, commission, swaps, profit, taxes, marginRate, timestamp, comment);
    }

    public long getTradeId() {
        return mTradeId;
    }

    public long getLogin() {
        return mLogin;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public TradeType getTradeType() {
        return mTradeType;
    }

    public int getVolume() {
        return mVolume;
    }

    public LocalDateTime getOpenDateTime() {
        return mOpenDateTime;
    }

    public double getOpenPrice() {
        return mOpenPrice;
    }

    public OptionalDouble getStopLoss() {
        return mStopLoss;
    }

    public OptionalDouble getTakeProfit() {
        return mTakeProfit;
    }

    public Optional<LocalDateTime> getPendingOrderExpiration() {
        return mPendingOrderExpiration;
    }

    public double getConvRateOpen() {
        return mConvRateOpen;
    }

    public double getCommission() {
        return mCommission;
    }

    public double getSwaps() {
        return mSwaps;
    }

    public double getProfit() {
        return mProfit;
    }

    public double getTaxes() {
        return mTaxes;
    }

    public double getMarginRate() {
        return mMarginRate;
    }

    public LocalDateTime getTimestamp() {
        return mTimestamp;
    }

    public String getComment() {
        return mComment;
    }

    private OpenTrade(
        final long tradeId,
        final long login,
        final String symbol,
        final TradeType tradeType,
        final int volume,
        final LocalDateTime openDateTime,
        final double openPrice,
        final OptionalDouble stopLoss,
        final OptionalDouble takeProfit,
        final Optional<LocalDateTime> pendingOrderExpiration,
        final double convRateOpen,
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
        mStopLoss = stopLoss;
        mTakeProfit = takeProfit;
        mPendingOrderExpiration = pendingOrderExpiration;
        mConvRateOpen = convRateOpen;
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
    private final OptionalDouble mStopLoss;
    private final OptionalDouble mTakeProfit;
    private final Optional<LocalDateTime> mPendingOrderExpiration;
    private final double mConvRateOpen;   // convertation rates from profit currency to group deposit currency
    private final double mCommission;
    private final double mSwaps;
    private final double mProfit;
    private final double mTaxes;
    private final double mMarginRate;       // margin convertation rate (rate of convertation from margin currency to deposit one)
    private final LocalDateTime mTimestamp; // timestamp
    private final String mComment;
}
