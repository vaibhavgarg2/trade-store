package com.deutsche.TradeStore.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TradeId implements Serializable {

    @Column(name = "TRADE_ID")
    private String id;

    @Column(name = "VERSION")
    private int version;

    TradeId(){}

    public TradeId(String id, int version) {
        this.id = id;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TradeId)) return false;
        TradeId tradeId1 = (TradeId) o;
        return version == tradeId1.version &&
                id.equals(tradeId1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }
}
