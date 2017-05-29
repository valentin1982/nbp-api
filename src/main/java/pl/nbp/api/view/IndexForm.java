package pl.nbp.api.view;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class IndexForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate listingDateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate listingDateTo;

    private String country;

    public IndexForm() {

    }

    public LocalDate getListingDateFrom() {
        return listingDateFrom;
    }

    public void setListingDateFrom(LocalDate listingDateFrom) {
        this.listingDateFrom = listingDateFrom;
    }

    public LocalDate getListingDateTo() {
        return listingDateTo;
    }

    public void setListingDateTo(LocalDate listingDateTo) {
        this.listingDateTo = listingDateTo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[listingDateFrom = "+listingDateFrom+", "
                + "listingDateTo = "+listingDateTo+", "
                + "country = , "+country+"]";
    }

}
