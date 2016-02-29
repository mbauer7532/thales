package thales.model;

/**
 * Created by Neo on 2/29/2016.
 */
public enum TradeMode {
    NoTrade(0),     // Cannot trade at all.
    CloseTrade(1),  // Can only close open trades.
    FullTrade(2);   // Can open and close trade.

    public static TradeMode fromTradeModelCode(final int tradeModeCode) {

        switch (tradeModeCode) {
            case 0: return NoTrade;
            case 1: return CloseTrade;
            case 2: return FullTrade;
            default:
                throw new IllegalArgumentException("Illegal trade mode code: " + tradeModeCode);
        }
    }

    public int toTradeModeCode() {

        return mTradeModeCode;
    }

    @Override
    public String toString() {

        switch (this) {
            case NoTrade: return "NoTrade";
            case CloseTrade: return "CloseTrade";
            case FullTrade: return "FullTrade";
        }

        throw new RuntimeException("Can't happen");
    }

    TradeMode(final int tradeModeCode) {
        mTradeModeCode = tradeModeCode;
    }

    private final int mTradeModeCode;
}
