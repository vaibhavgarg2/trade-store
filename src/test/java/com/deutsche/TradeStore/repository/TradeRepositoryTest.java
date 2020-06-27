package com.deutsche.TradeStore.repository;

import com.deutsche.TradeStore.entity.Trade;
import com.deutsche.TradeStore.entity.TradeId;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@DataJpaTest
public class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    public void findMaxTradeVersionTest(){
        IntStream.of(1,2).forEach(count ->{
            IntStream.rangeClosed(1,4).forEach(num ->{
                Trade trade = new Trade();
                TradeId tradeId1 = new TradeId("T"+count, num);
                trade.setTradeId(tradeId1);
                trade.setBookId("Book1");
                trade.setCounterPartyId("CP01");
                trade.setMaturityDate(LocalDate.now());
                trade.setCreatedDate(LocalDate.now());
                trade.setExpired("N");
                tradeRepository.save(trade);
            });
        });
        List<Object[]> versionList = tradeRepository.findMaxTradeVersion("T1");
        assertEquals(4, versionList.get(0)[0]);
    }

    @Test
    public void saveTradeTest(){
        Trade trade = new Trade();
        TradeId tradeId1 = new TradeId("T1", 1);
        trade.setTradeId(tradeId1);
        trade.setBookId("Book1");
        trade.setCounterPartyId("CP01");
        trade.setMaturityDate(LocalDate.now());
        trade.setCreatedDate(LocalDate.now());
        trade.setExpired("N");
        tradeRepository.save(trade);
        Optional<Trade> tradeOptional = tradeRepository.findById(new TradeId("T1", 1));
        assertNotNull(tradeOptional.isPresent());
    }


}
