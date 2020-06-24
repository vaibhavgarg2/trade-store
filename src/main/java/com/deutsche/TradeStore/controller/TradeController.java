package com.deutsche.TradeStore.controller;

import com.deutsche.TradeStore.entity.Trade;
import com.deutsche.TradeStore.service.TradeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.deutsche.TradeStore.constant.TradeConstants.*;

@RestController
public class TradeController {

    @Autowired
    private TradeService tradeService;

    /**
     * This method validate the input trade and save in database.
     * @param trade the trade object to store
     * @return ResponseEntity<Object>
     */
    @PostMapping(value = "/trades", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addTrade(@RequestBody Trade trade) {
        //verify maturity date
        if(trade.getMaturityDate().isBefore(LocalDate.now())){
            return new ResponseEntity<Object>(REJECT_MATURITY_DATE_ERROR, HttpStatus.BAD_REQUEST);
        }
        //verify version
        Optional<Trade> optionalTrade =  tradeService.verifyTradeVersion(trade.getTradeId());
        if(!optionalTrade.isPresent()) {
            List<Object[]> tradeDB = tradeService.findMaxTradeVersion(trade.getTradeId().getId());
            if (CollectionUtils.isNotEmpty(tradeDB) && !Objects.isNull(tradeDB.get(0)) && ((Integer) (tradeDB.get(0)[0])).intValue() > trade.getTradeId().getVersion()) {
                return new ResponseEntity<Object>(REJECT_HIGHER_VERSION_ERROR, HttpStatus.BAD_REQUEST);
            }
        }

        //add to database
        trade = tradeService.addTrade(trade);
        return new ResponseEntity<Object>(trade, HttpStatus.OK);
    }

    /**
     * This method fetches all trades present in database.
     * @return ResponseEntity
     */
    @GetMapping(value = "/trades", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getAllTrades() {
        List<Trade> allTrades = (List<Trade>) tradeService.fetchAllTrades();
        return new ResponseEntity(allTrades, HttpStatus.OK);
    }
}
