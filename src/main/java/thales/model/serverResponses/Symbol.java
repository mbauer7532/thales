package thales.model.serverResponses;

import thales.model.Currency;
import thales.model.ExecutionMode;
import thales.model.TradeMode;
import thales.model.symbolProfitModes.ProfitMode;

/**
 * Created by Neo on 2/29/2016.
 */
public final class Symbol {

    public static Symbol create(
        final String symbolName,
        final String description,
        final Currency currency,
        final int digits,
        final TradeMode tradeMode,
        final ProfitMode profitMode,
        final int filter,
        final int filterCounter,
        final double filterLimit,
        final int filterSmoothing,
        final int spread,
        final int spreadBalance,
        final ExecutionMode executionMode,
        final boolean logTicks) {

        return new Symbol(
            symbolName,
            description,
            currency,
            digits,
            tradeMode,
            profitMode,
            filter,
            filterCounter,
            filterLimit,
            filterSmoothing,
            spread,
            spreadBalance,
            executionMode,
            logTicks);
    }

    private Symbol(
        final String symbolName,
        final String description,
        final Currency currency,
        final int digits,
        final TradeMode tradeMode,
        final ProfitMode profitMode,
        final int filter,
        final int filterCounter,
        final double filterLimit,
        final int filterSmoothing,
        final int spread,
        final int spreadBalance,
        final ExecutionMode executionMode,
        final boolean logTicks) {

        mSymbolName = symbolName;
        mDescription = description;
        mCurrency = currency;
        mDigits = digits;
        mTradeMode = tradeMode;
        mProfitMode = profitMode;
        mFilter = filter;
        mFilterCounter = filterCounter;
        mFilterLimit = filterLimit;
        mFilterSmoothing = filterSmoothing;
        mSpread = spread;
        mSpreadBalance = spreadBalance;
        mExecutionMode = executionMode;
        mLogTicks = logTicks;
    }

    private final String mSymbolName;
    private final String mDescription;
    private final Currency mCurrency;
    private final int mDigits;

    private final TradeMode mTradeMode;
    // Open Sessions
    // Quote Sessions
    private final ProfitMode mProfitMode;

    // Filtration -- what do they mean?
    private final int mFilter;
    private final int mFilterCounter;
    private final double mFilterLimit;
    private final int mFilterSmoothing;

    // Spreads
    private final int mSpread;
    private final int mSpreadBalance;

    // Execution mode
    private final ExecutionMode mExecutionMode;

    // Log ticks
    private final boolean mLogTicks;

    // Swaps
}
