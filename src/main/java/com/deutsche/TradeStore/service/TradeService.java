package com.deutsche.TradeStore.service;

import com.deutsche.TradeStore.entity.Trade;
import com.deutsche.TradeStore.entity.TradeId;
import com.deutsche.TradeStore.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * This method saves the input trade.
     * @param trade the saved trade
     */
    public Trade addTrade(Trade trade){
        return tradeRepository.save(trade);
    }

    /**
     * This method fetches all trades present in database.
     * @return Iterable<Trade> list of all trades
     */
    public Iterable<Trade> fetchAllTrades() {
        return tradeRepository.findAll();
    }

    /**
     * This method returns the highest trade version for a given trade id
     * @param tradeId trade id of trade
     * @return List<Object[]> list of columns in an array
     */
    public Optional<Trade> verifyTradeVersion(TradeId tradeId) {
        return tradeRepository.findById(tradeId);
    }

    /**
     * This method returns the highest trade version for a given trade id
     * @param tradeId trade id of trade
     * @return List<Object[]> list of columns in an array
     */
    public  List<Object[]> findMaxTradeVersion(String tradeId) {
        return tradeRepository.findMaxTradeVersion(tradeId);
    }
}
