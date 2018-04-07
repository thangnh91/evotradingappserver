package com.evo.trading;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.impl.BinanceApiService;
import com.coinmarketcap.api.client.impl.CoinmarketcapApiService;
import com.evo.trading.dao.EvoBollingerBandDao;
import com.evo.trading.object.BollingerBand;
import com.evo.trading.utils.Utilities;
import com.fasterxml.jackson.annotation.JsonProperty;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://evotradeapp.herokuapp.com", "https://dreamerstradeapp.herokuapp.com" })
public class HomeController {
	
	/**
	 * EVOlution
	 */

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> hello(){
        return Collections.singletonMap("response", "Hello");
    }
    
    
	class BollingerBandResponse {
		public @JsonProperty("LastUpdated") Long lastUpdated;
    	public @JsonProperty("OutOfUpperBB") List<BollingerBand> ooUpperBBs;
    	public @JsonProperty("OutOfLowerBB") List<BollingerBand> ooLowerBBs;
    	
    	public BollingerBandResponse() {
    		ooUpperBBs = new ArrayList<>();
    		ooLowerBBs = new ArrayList<>();
    	}
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
    
	@RequestMapping(value = "/api/evo/bollingerband", method = RequestMethod.GET, produces = "application/json")
	public BollingerBandResponse getEvoBollingerBands(
			@RequestParam("exchange") String exchange,
			@RequestParam("interval") String interval) {
		BollingerBandResponse bbres = new BollingerBandResponse();
		List<BollingerBand> bollingerbands = EvoBollingerBandDao.getInstance().getBollingerBands();
		if(bollingerbands == null)
			return bbres;
		if(bollingerbands != null && !bollingerbands.isEmpty()) {
			bbres.lastUpdated = bollingerbands.get(0).getTimestamp();
		}
		else {
			bbres.lastUpdated = System.currentTimeMillis();
		}
		if(!exchange.equalsIgnoreCase("all")) {
			bollingerbands = bollingerbands.stream()
					.filter( bb -> bb.getExchange().equalsIgnoreCase(exchange))
					.collect(Collectors.toList());
		}
		bollingerbands = bollingerbands.stream()
				.filter( bb -> bb.getInterval().equals(interval))
				.collect(Collectors.toList());
		bbres.ooLowerBBs = bollingerbands.stream()
					.filter( bb -> bb.isOutOfLowerBollingerBand())
					.collect(Collectors.toList());
		bbres.ooUpperBBs = bollingerbands.stream()
					.filter( bb -> bb.isOutOfUpperBollingerBand())
					.collect(Collectors.toList());
		
//		for(BollingerBand bb : bollingerbands) {
//    		if(bb.isOutOfLowerBollingerBand())
//    			bbres.ooLowerBBs.add(bb);
//    		else if(bb.isOutOfUpperBollingerBand())
//    			bbres.ooUpperBBs.add(bb);
//		}
    	Collections.sort(bbres.ooLowerBBs, new Sortbypercentage());
    	Collections.sort(bbres.ooUpperBBs, new Sortbypercentage());
		return bbres;
	}
	
	/**
	 * Binance
	 * @return
	 */
	
//    @RequestMapping(value = "/api/evo/bollingerband", method = RequestMethod.GET, produces = "application/json")
    public BollingerBandResponse getBinanceBollingerBands(@RequestParam("exchange") String exchange,
    		@RequestParam("interval") String interval) {
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
    
    
    private BollingerBand calcBollingerBand(TickerPrice tickerPrice, List<Candlestick> candlestickBars) {
    	if(tickerPrice == null || candlestickBars == null)
    		return null;
    	
    	List<Double> closePrices = new ArrayList<>();
    	for(Candlestick cdstBar : candlestickBars) {
    		closePrices.add( Double.valueOf( cdstBar.getClose() ));
    	}
    	
    	BollingerBand bb = new BollingerBand("binance", tickerPrice.getSymbol(), "30m", Double.valueOf(tickerPrice.getPrice()));
    	bb.setSma( Utilities.average(closePrices) );
    	Double stddev = Utilities.stdDeviation(closePrices);
    	bb.setUpperBB( bb.getSma() + (stddev * BollingerBand.FACTOR));
    	bb.setLowerBB( bb.getSma() - (stddev * BollingerBand.FACTOR));
    	
    	if(bb.isOutOfBands())
    		return bb;
    	
    	return null;
    }
    
    @RequestMapping(value = "/api/binance/v1/klines", method = RequestMethod.GET, produces = "application/json")
    public String getBinanceCandlestickBars(
    		@RequestParam("symbol") String symbol,
    		@RequestParam("interval") String interval,
    		@RequestParam(value="limit", defaultValue="500") Integer limit,
    		@RequestParam(value="startTime", defaultValue="") Long startTime,
    		@RequestParam(value="endTime", defaultValue="") Long endTime) {
    	return BinanceApiService._getCandlestickBars(symbol, interval, limit, startTime, endTime);
    }
    
//    @RequestMapping(value = "/api/binance/v1/klines", method = RequestMethod.GET, produces = "application/json")
//    public String getBinanceCandlestickBars(
//    		@RequestParam("symbol") String symbol,
//    		@RequestParam("interval") String interval,
//    		@RequestParam(value="limit", defaultValue="500") Integer limit) {
//    	return BinanceApiService._getCandlestickBars(symbol, interval, limit, null, null);
//    }
    
//    @RequestMapping(value = "/api/binance/v3/ticker/price", method = RequestMethod.GET, produces = "application/json")
//    public String getBinanceTickerPrices() {
//    	return BinanceApiService._getTickerPrices();
//    }
    
    @RequestMapping(value = "/api/binance/v3/ticker/price", method = RequestMethod.GET, produces = "application/json")
    public String getBinanceTickerPrice(@RequestParam(value="symbol", defaultValue="") String symbol) {
    	if(symbol.isEmpty()) {
    		return BinanceApiService._getTickerPrices();
    	}
    	return BinanceApiService._getTickerPrice(symbol);
    }
    
    /** End: Binance */
    
    /**
     * Coinmarketcap
     */
    @RequestMapping(value = "/api/coinmarketcap/v1/ticker", method = RequestMethod.GET, produces = "application/json")
    public String getCoinmarketcapTicker(@RequestParam(value="start", defaultValue="0") Integer start,
    		@RequestParam(value="limit", defaultValue="100") Integer limit,
    		@RequestParam(value="convert", defaultValue="") String convert) {
    	return CoinmarketcapApiService.getTicker(start, limit, convert);
    }
    
    @RequestMapping(value = "/api/coinmarketcap/v1/ticker/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getCoinmarketcapTicker(@PathVariable("id") String id,
    		@RequestParam(value="convert", defaultValue="") String convert) {
    	return CoinmarketcapApiService.getTicker(id, convert);
    }
    
    @RequestMapping(value = "/api/coinmarketcap/v1/global", method = RequestMethod.GET, produces = "application/json")
    public String getCoinmarketcapGlobalData(@RequestParam(value = "convert", defaultValue = "") String convert) {
    	return CoinmarketcapApiService.getGlobalData(convert);
    }
    
    /** End: Coinmarketcap */
}