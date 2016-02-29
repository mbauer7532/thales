package thales.model.serverResponses;

import thales.model.ExecutionMode;
import thales.model.TradeMode;
import thales.model.symbolProfitModes.ProfitMode;

/**
 * Created by Neo on 2/29/2016.
 */
public final class Symbol {

    private final String mSymbolName;
    private final String mDescription;
    private final String mCurrency;
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
}
