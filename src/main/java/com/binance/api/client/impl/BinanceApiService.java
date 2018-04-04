package com.binance.api.client.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.type.TypeReference;
import org.springframework.web.client.RestTemplate;

import com.binance.api.client.domain.market.Binance_24hrTickerPrice;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.TickerPrice;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BinanceApiService {
	
	public static final String BINANCE_REST_SERVICE_URI = "https://api.binance.com";
	
	public static final ObjectMapper mapper = new ObjectMapper();
	
	public static List<Candlestick> getCandlestickBars(String symbol, String interval, Integer limit, Long startTime, Long endTime) {
		String pattern = "/api/v1/klines";
		pattern += "?symbol=" + symbol;
		pattern += "&interval=" + interval;
		if(limit != null) {
			pattern += "&limit=" + limit;
		}
		if(startTime != null) {
			pattern += "&startTime=" + startTime;
		}
		if(endTime != null) {
			pattern += "&endTime=" + endTime;
		}
		RestTemplate restTemplate = new RestTemplate();
		Object[][] result = restTemplate.getForObject(BINANCE_REST_SERVICE_URI + pattern, Object[][].class);
		
		List<Candlestick> res = new ArrayList<>();
//		try {
//			res = mapper.readValue(result, new TypeReference<List<Candlestick>>() {
//			});
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		for(Object[] arr : result) {
			Candlestick cdsk = new Candlestick(arr);
			res.add(cdsk);
		}
		return res;
	}
	
	public static String _getCandlestickBars(String symbol, String interval, Integer limit, Long startTime, Long endTime) {
		String pattern = "/api/v1/klines";
		pattern += "?symbol=" + symbol;
		pattern += "&interval=" + interval;
		if(limit != null) {
			pattern += "&limit=" + limit;
		}
		if(startTime != null) {
			pattern += "&startTime=" + startTime;
		}
		if(endTime != null) {
			pattern += "&endTime=" + endTime;
		}
		return _get(pattern);
	}
	
	public static TickerPrice getTickerPrice(String symbol) {
		TickerPrice res = null;
		try {
			res = mapper.readValue(_getTickerPrice(symbol), new TypeReference<TickerPrice>() {
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static String _getTickerPrice(String symbol) {
		String pattern = "/api/v3/ticker/price";
		pattern += "?symbol=" + symbol;
		return _get(pattern);
	}
	
	public static List<TickerPrice> getTickerPrices() {
		List<TickerPrice> res = null;
		try {
			res = mapper.readValue(_getTickerPrices(), new TypeReference<List<TickerPrice>>() {
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static String _getTickerPrices() {
		String pattern = "/api/v3/ticker/price";
		return _get(pattern);
	}
	
	/** 24hr ticker price change statistics
		GET /api/v1/ticker/24hr
		24 hour price change statistics. Careful when accessing this with no symbol.
		
		Weight: 1 for a single symbol; number of symbols that are TRADING / 2 when the symbol parameter is omitted
		
		Parameters:
		
		Name	Type	Mandatory	Description
		symbol	STRING	NO	
		
		If the symbol is not sent, tickers for all symbols will be returned in an array.
		When all symbols are returned, the number of requests counted against the rate limiter is equal to the number of symbols currently trading on the exchange.
		Response:
		
		{
		  "symbol": "BNBBTC",
		  "priceChange": "-94.99999800",
		  "priceChangePercent": "-95.960",
		  "weightedAvgPrice": "0.29628482",
		  "prevClosePrice": "0.10002000",
		  "lastPrice": "4.00000200",
		  "lastQty": "200.00000000",
		  "bidPrice": "4.00000000",
		  "askPrice": "4.00000200",
		  "openPrice": "99.00000000",
		  "highPrice": "100.00000000",
		  "lowPrice": "0.10000000",
		  "volume": "8913.30000000",
		  "quoteVolume": "15.30000000",
		  "openTime": 1499783499040,
		  "closeTime": 1499869899040,
		  "fristId": 28385,   // First tradeId
		  "lastId": 28460,    // Last tradeId
		  "count": 76         // Trade count
		}
		OR
		
		[
		  {
		    "symbol": "BNBBTC",
		    "priceChange": "-94.99999800",
		    "priceChangePercent": "-95.960",
		    "weightedAvgPrice": "0.29628482",
		    "prevClosePrice": "0.10002000",
		    "lastPrice": "4.00000200",
		    "lastQty": "200.00000000",
		    "bidPrice": "4.00000000",
		    "askPrice": "4.00000200",
		    "openPrice": "99.00000000",
		    "highPrice": "100.00000000",
		    "lowPrice": "0.10000000",
		    "volume": "8913.30000000",
		    "quoteVolume": "15.30000000",
		    "openTime": 1499783499040,
		    "closeTime": 1499869899040,
		    "fristId": 28385,   // First tradeId
		    "lastId": 28460,    // Last tradeId
		    "count": 76         // Trade count
		  }
		]
	 * 
	 * @param symbol
	 * @return
	 */
	public static String _get24hrTickerPrice(String symbol) {
		String pattern = "/api/v1/ticker/24hr";
		pattern += "?symbol=" + symbol;
		return _get(pattern);
	}
	
	public static Binance_24hrTickerPrice get24hrTickerPrice(String symbol) {
		Binance_24hrTickerPrice bnb_24hrTickerPrice = new Binance_24hrTickerPrice();
		try {
			bnb_24hrTickerPrice = mapper.readValue(_get24hrTickerPrice(symbol), new TypeReference<Binance_24hrTickerPrice>() {
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bnb_24hrTickerPrice;
	}
	
	private static String _get(String pattern) {
		RestTemplate restTemplate = new RestTemplate();
		System.out.println(BINANCE_REST_SERVICE_URI + pattern);
		String result = restTemplate.getForObject(BINANCE_REST_SERVICE_URI + pattern, String.class);
		return result;
	}
}
