package thales.model.serverRequests;

import thales.model.TradeType;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Created by Neo on 2/29/2016.
 */
public class OpenTradeRequest {

    public static OpenTradeRequest create(
        final long login,
        final String symbol,
        final TradeType tradeType,
        final int volume,
        final OptionalDouble stopLoss,
        final OptionalDouble takeProfit,
        final Optional<LocalDateTime> pendingOrderExpiration,
        final String comment) {

        return new OpenTradeRequest(login, symbol, tradeType, volume, stopLoss, takeProfit, pendingOrderExpiration, comment);
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

    public OptionalDouble getStopLoss() {
        return mStopLoss;
    }

    public OptionalDouble getTakeProfit() {
        return mTakeProfit;
    }

    public Optional<LocalDateTime> getPendingOrderExpiration() {
        return mPendingOrderExpiration;
    }

    public String getComment() {
        return mComment;
    }

    private OpenTradeRequest(
        final long login,
        final String symbol,
        final TradeType tradeType,
        final int volume,
        final OptionalDouble stopLoss,
        final OptionalDouble takeProfit,
        final Optional<LocalDateTime> pendingOrderExpiration,
        final String comment) {

        mLogin = login;
        mSymbol = symbol;
        mTradeType = tradeType;
        mVolume = volume;
        mStopLoss = stopLoss;
        mTakeProfit = takeProfit;
        mPendingOrderExpiration = pendingOrderExpiration;
        mComment = comment;
    }

    private final long mLogin;
    private final String mSymbol;
    private final TradeType mTradeType;
    private final int mVolume;
    private final OptionalDouble mStopLoss;
    private final OptionalDouble mTakeProfit;
    private final Optional<LocalDateTime> mPendingOrderExpiration;
    private final String mComment;
}
