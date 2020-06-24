package com.deutsche.TradeStore.schedular;

import com.deutsche.TradeStore.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TradeSchedular {

    @Autowired
    private TradeRepository tradeRepository;

    @Scheduled(cron = "${trade.expiry.schedular}")
    public void updateMaturedTrades(){
        System.out.println("Schedular run");
        tradeRepository.updateMaturedTrades();
    }
}
