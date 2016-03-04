package thales.model;

/**
 * Created by Neo on 3/4/2016.
 */
public final class Currency {

    public static Currency create(final String currencyName) {
        return new Currency(currencyName);
    }

    public String getCurrencyName() {
        return mCurrencyName;
    }

    private Currency(final String currencyName) {
        mCurrencyName = currencyName;
    }

    private final String mCurrencyName;
}
