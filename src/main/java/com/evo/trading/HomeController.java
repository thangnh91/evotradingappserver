package com.evo.trading;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.impl.BinanceApiService;
import com.evo.trading.object.BollingerBand;
import com.evo.trading.utils.Utilities;
import com.fasterxml.jackson.annotation.JsonProperty;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://evotradeapp.herokuapp.com" })
public class HomeController {
	
    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> hello(){
        return Collections.singletonMap("response", "Hello");
    }
    
    class BollingerBandResponse {
    	public @JsonProperty("OutOfUpperBB") List<BollingerBand> ooUpperBBs;
    	public @JsonProperty("OutOfLowerBB") List<BollingerBand> ooLowerBBs;
    	
    	public BollingerBandResponse() {
    		ooUpperBBs = new ArrayList<>();
    		ooLowerBBs = new ArrayList<>();
    	}
    }
    
    @RequestMapping(value = "/api/bollingerband/{exchange}/{interval}", method = RequestMethod.GET, produces = "application/json")
    public BollingerBandResponse getBollingerBands(@PathVariable("exchange") String exchange,
    		@PathVariable("interval") String interval) {
    	BollingerBandResponse bbres = new BollingerBandResponse();
    	List<TickerPrice> tickerPrices = BinanceApiService.getTickerPrices();
    	for(TickerPrice tickerPrice : tickerPrices) {
    		BollingerBand bb = calcBollingerBand(tickerPrice, BinanceApiService.getCandlestickBars(tickerPrice.getSymbol(), interval, 20, null, null));
    		if(bb == null) {
    			continue;
    		}
    		if(bb.isOutOfLowerBollingerBand())
    			bbres.ooLowerBBs.add(bb);
    		else if(bb.isOutOfUpperBollingerBand())
    			bbres.ooUpperBBs.add(bb);
    	}
    	Collections.sort(bbres.ooLowerBBs, new Sortbypercentage());
    	Collections.sort(bbres.ooUpperBBs, new Sortbypercentage());

    	return bbres;
    }
    
    class Sortbypercentage implements Comparator<BollingerBand>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(BollingerBand a, BollingerBand b)
        {
        	Double avalue = a.getPercentage();
        	Double bvalue = b.getPercentage();
            if(avalue > bvalue)
            	return -1;
            if(avalue < bvalue)
            	return 1;
            return 0;
        }
    }
    
    private BollingerBand calcBollingerBand(TickerPrice tickerPrice, List<Candlestick> candlestickBars) {
    	if(tickerPrice == null || candlestickBars == null)
    		return null;
    	
    	List<Double> closePrices = new ArrayList<>();
    	for(Candlestick cdstBar : candlestickBars) {
    		closePrices.add( Double.valueOf( cdstBar.getClose() ));
    	}
    	
    	BollingerBand bb = new BollingerBand("binance", tickerPrice.getSymbol(), Double.valueOf(tickerPrice.getPrice()));
    	bb.setSma( Utilities.average(closePrices) );
    	Double stddev = Utilities.stdDeviation(closePrices);
    	bb.setUpperBB( bb.getSma() + (stddev * BollingerBand.FACTOR));
    	bb.setLowerBB( bb.getSma() - (stddev * BollingerBand.FACTOR));
    	
    	if(bb.isOutOfBands())
    		return bb;
    	
    	return null;
    }
    
    @RequestMapping(value = "/api/v1/klines/{symbol}/{interval}/{limit}/{startTime}/{endTime}", method = RequestMethod.GET, produces = "application/json")
    public String getCandlestickBars(
    		@PathVariable("symbol") String symbol,
    		@PathVariable("interval") String interval,
    		@PathVariable("limit") Integer limit,
    		@PathVariable("startTime") Long startTime,
    		@PathVariable("endTime") Long endTime) {
    	return BinanceApiService._getCandlestickBars(symbol, interval, limit, startTime, endTime);
    }
    
    @RequestMapping(value = "/api/v1/klines/{symbol}/{interval}/{limit}", method = RequestMethod.GET, produces = "application/json")
    public String getCandlestickBars(
    		@PathVariable("symbol") String symbol,
    		@PathVariable("interval") String interval,
    		@PathVariable("limit") Integer limit) {
    	return BinanceApiService._getCandlestickBars(symbol, interval, limit, null, null);
    }
    
    @RequestMapping(value = "/api/v3/ticker/price", method = RequestMethod.GET, produces = "application/json")
    public String getTickerPrices() {
    	return BinanceApiService._getTickerPrices();
    }
    
    @RequestMapping(value = "/api/v3/ticker/price/{symbol}", method = RequestMethod.GET, produces = "application/json")
    public String getTickerPrice(@PathVariable("symbol") String symbol) {
    	return BinanceApiService._getTickerPrice(symbol);
    }
}