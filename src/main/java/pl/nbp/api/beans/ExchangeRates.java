package pl.nbp.api.beans;

import pl.nbp.api.domain.Rate;

import java.util.List;

public class ExchangeRates {

    private Currency currency;

    private List<Rate> rates;

    public ExchangeRates() {

    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "ExchangeRates{" +
                "currency=" + currency +
                ", rates=" + rates +
                '}';
    }
}
