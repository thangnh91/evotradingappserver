package com.binance.api.client.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.type.TypeReference;
import org.springframework.web.client.RestTemplate;

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
		String pattern = "/api/v3/ticker/price";
		pattern += "?symbol=" + symbol;
		
		TickerPrice res = null;
		try {
			res = mapper.readValue(_get(pattern), new TypeReference<TickerPrice>() {
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
		String pattern = "/api/v3/ticker/price";
		
		List<TickerPrice> res = null;
		try {
			res = mapper.readValue(_get(pattern), new TypeReference<List<TickerPrice>>() {
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
	
	private static String _get(String pattern) {
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(BINANCE_REST_SERVICE_URI + pattern, String.class);
		
		return result;
	}
}
