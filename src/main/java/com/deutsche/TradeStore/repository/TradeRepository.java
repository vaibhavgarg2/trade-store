package com.deutsche.TradeStore.repository;

import com.deutsche.TradeStore.entity.Trade;
import com.deutsche.TradeStore.entity.TradeId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TradeRepository extends CrudRepository<Trade, TradeId> {

    /**
     * This method returns the highest trade version for a given trade id
     * @param trade_id input trade id of trade
     * @return
     */
    @Query(nativeQuery = true, value = "select max(t.version) from trade t where t.trade_id= ?1")
    List<Object[]> findMaxTradeVersion(String trade_id);

    /**
     * This method updates the trades for old maturity date(maturity date < today's date)
     */
    @Modifying
    @Query(nativeQuery = true, value = "update trade set EXPIRED='Y' where MATURITY_DATE < SYSDATE")
    void updateMaturedTrades();

    @Override
    Optional<Trade> findById(TradeId tradeId);
}
