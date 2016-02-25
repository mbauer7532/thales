package model;

/**
 * Created by Neo on 2/24/2016.
 */
public enum TradeType {
    Buy(0),
    Sell(1),
    BuyLimit(2),
    SellLimit(3),
    BuyStop(4),
    SellStop(5),
    Balance(6),
    Credit(7);

    public TradeType fromCmd(final int cmd) {
        switch (cmd) {
            case 0: return Buy;
            case 1: return Sell;
            case 2: return BuyLimit;
            case 3: return SellLimit;
            case 4: return BuyStop;
            case 5: return SellStop;
            case 6: return Balance;
            case 7: return Credit;
            default:
                throw new IllegalArgumentException("Illegal cmd arguement: " + cmd);
        }
    }

    TradeType(final int cmd) {
        mCmd = cmd;
    }

    private final int mCmd;
}
