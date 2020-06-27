package com.deutsche.TradeStore.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
public class Trade implements Serializable {

	private static final long serialVersionUID = -4779535104594588025L;

	@EmbeddedId
    private TradeId tradeId;

    @Column(name = "COUNTER_PARTY")
    private String counterPartyId;

    @Column(name = "BOOK_ID")
    private String bookId;

    @Column(name = "MATURITY_DATE")
    private LocalDate maturityDate;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "EXPIRED")
    private String expired;

    public TradeId getTradeId() {
        return tradeId;
    }

    public void setTradeId(TradeId tradeId) {
        this.tradeId = tradeId;
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public void setCounterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }
}
