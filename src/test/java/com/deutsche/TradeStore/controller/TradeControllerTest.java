package com.deutsche.TradeStore.controller;

import com.deutsche.TradeStore.entity.Trade;
import com.deutsche.TradeStore.entity.TradeId;
import com.deutsche.TradeStore.service.TradeService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.deutsche.TradeStore.constant.TradeConstants.REJECT_HIGHER_VERSION_ERROR;
import static com.deutsche.TradeStore.constant.TradeConstants.REJECT_MATURITY_DATE_ERROR;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Test
    public void fetchAllTrades() throws Exception {
        List<Trade> tradeList = new ArrayList<>();
        buildTestData(tradeList);
        when(tradeService.fetchAllTrades()).thenReturn(tradeList);
        this.mockMvc.perform(get("/trades")).andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testWhenMaturityDateLessThanCurrentDate_ThenRejectTrade() throws Exception{
        String maturityDate = LocalDate.now().minusDays(2).toString();
        String trade = "{\"tradeId\":{\"id\":\"T1\",\"version\":\"3\"},\"counterPartyId\":\"CP01\",\"bookId\":\"book1\",\"maturityDate\":\""+
                maturityDate+"\",\"createdDate\":\"2020-06-21\",\"expired\":\"N\"}";
        this.mockMvc.perform(post("/trades").content(trade).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).
                andExpect(content().string(REJECT_MATURITY_DATE_ERROR));
    }

    @Test
    public void testWhenLowerVersion_ThenRejectTrade() throws Exception{
        String maturityDate = LocalDate.now().toString();
        String tradeStr = "{\"tradeId\":{\"id\":\"T1\",\"version\":\"2\"},\"counterPartyId\":\"CP01\",\"bookId\":\"book1\",\"maturityDate\":\""+
                maturityDate+"\",\"createdDate\":\"2020-06-21\",\"expired\":\"N\"}";
        Optional<Trade> optionalTrade = Optional.empty();
        when(tradeService.verifyTradeVersion(Mockito.any(TradeId.class))).thenReturn(optionalTrade);
        Object[] objects = new Object[]{5};
        List<Object[]> objectList = new ArrayList<>();
        objectList.add(objects);
        when(tradeService.findMaxTradeVersion(Mockito.any(String.class))).thenReturn(objectList);
        this.mockMvc.perform(post("/trades").content(tradeStr).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).
                andExpect(content().string(REJECT_HIGHER_VERSION_ERROR));
    }

    @Test
    @Ignore
    public void testAddTradeSuccess() throws Exception{
        String maturityDate = LocalDate.now().toString();
        String tradeStr = "{\"tradeId\":{\"id\":\"T1\",\"version\":\"2\"},\"counterPartyId\":\"CP01\",\"bookId\":\"book1\",\"maturityDate\":\""+
                maturityDate+"\",\"createdDate\":\"2020-06-21\",\"expired\":\"N\"}";
        Trade trade = new Trade();
        TradeId tradeId1 = new TradeId("T1", 3);
        trade.setTradeId(tradeId1);
        trade.setBookId("Book1");
        trade.setCounterPartyId("CP01");
        trade.setMaturityDate(LocalDate.now());
        trade.setCreatedDate(LocalDate.now());
        trade.setExpired("N");
        Optional<Trade> optionalTrade = Optional.of(trade);
        when(tradeService.verifyTradeVersion(Mockito.any(TradeId.class))).thenReturn(optionalTrade);
        Object[] objects = new Object[]{3};
        List<Object[]> objectList = new ArrayList<>();
        objectList.add(objects);
        when(tradeService.findMaxTradeVersion(Mockito.any(String.class))).thenReturn(objectList);
        this.mockMvc.perform(post("/trades").content(tradeStr).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    public void buildTestData(List<Trade> tradeList) {
        IntStream.of(1, 2, 3).forEach(value -> {
            Trade trade = new Trade();
            TradeId tradeId1 = new TradeId("T1", value);
            trade.setTradeId(tradeId1);
            trade.setBookId("Book1");
            trade.setCounterPartyId("CP01");
            trade.setMaturityDate(LocalDate.now());
            trade.setCreatedDate(LocalDate.now());
            trade.setExpired("N");
            tradeList.add(trade);
        });
    }
}
