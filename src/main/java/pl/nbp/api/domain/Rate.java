package pl.nbp.api.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * entity object
 */
@Entity(name = "EXCHANGERATESSERIES")
public class Rate {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate effectivedate;

    private double mid;

    public Rate() {

    }

    public Rate(LocalDate effectivedate, double mid) {
        this.effectivedate = effectivedate;
        this.mid = mid;
    }

    public LocalDate getEffectivedate() {
        return effectivedate;
    }

    public void setEffectivedate(LocalDate effectivedate) {
        this.effectivedate = effectivedate;
    }

    public double getMid() {
        return mid;
    }

    public void setMid(double mid) {
        this.mid = mid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[effectivedate = " + effectivedate + ", mid = " + mid + "]";
    }

}
