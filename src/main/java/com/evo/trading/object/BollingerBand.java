package com.evo.trading.object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BollingerBand {
	@JsonProperty("exchange") String exchange;
	@JsonProperty("symbol") String symbol;
	@JsonProperty("currencySymbol") String currencySymbol;
	@JsonProperty("baseCurrencySymbol") String baseCurrencySymbol;
	@JsonProperty("lastPrice") Double lastPrice;
	@JsonProperty("upperBB") Double upperBB;
	@JsonProperty("sma") Double sma;
	@JsonProperty("lowerBB") Double lowerBB;
	@JsonProperty("percentage") Double percentage;
	
	public static Long NUMBER_OF_CANDLESTICK_BARS = 20L;
	public static Long FACTOR = 2L;
	
	private Long SHATOSHI = 100000000L; // 1 btc = 10^8 sts
	
	private Long stsLastPrice;
	private Long stsUpperBB;
	private Long stsSma;
	private Long stsLowerBB;
	
	public BollingerBand() {
		
	}
	
	public BollingerBand(String exchange, String symbol, Double lastPrice) {
		setExchange(exchange);
		setSymbol(symbol);
		setLastPrice(lastPrice);
	}
	
	public boolean isOutOfUpperBollingerBand() {
		return lastPrice > upperBB;
	}
	
	public boolean isOutOfLowerBollingerBand() {
		return lastPrice < lowerBB;
	}
	
	public boolean isOutOfBands() {
		return isOutOfLowerBollingerBand() || isOutOfUpperBollingerBand();
	}
	
	public Double getPercentage() {
		if(percentage != null)
			return percentage;
		else if(isOutOfLowerBollingerBand())
			percentage = (double)(stsLowerBB - stsLastPrice) / stsLowerBB;
		else if(isOutOfUpperBollingerBand())
			percentage = (double)(stsLastPrice - stsUpperBB) / stsUpperBB;
		percentage *= 100.;
		return percentage;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getBaseCurrencySymbol() {
		return baseCurrencySymbol;
	}

	public void setBaseCurrencySymbol(String baseCurrencySymbol) {
		this.baseCurrencySymbol = baseCurrencySymbol;
	}

	public Double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
		this.stsLastPrice = (long)(lastPrice * SHATOSHI);
		this.percentage = null;
	}

	public Double getSma() {
		return sma;
	}

	public void setSma(Double sma) {
		this.sma = sma;
		this.stsSma = (long)(sma * SHATOSHI);
	}

	public Double getLowerBB() {
		return lowerBB;
	}

	public void setLowerBB(Double lowerBB) {
		this.lowerBB = lowerBB;
		this.stsLowerBB = (long)(lowerBB * SHATOSHI);
		this.percentage = null;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
		if(symbol.contains("BTC")) {
			baseCurrencySymbol = "BTC";
			currencySymbol = symbol.substring(0, symbol.length()-baseCurrencySymbol.length());
		}
		else if(symbol.contains("ETH")) {
			baseCurrencySymbol = "ETH";
			currencySymbol = symbol.substring(0, symbol.length()-baseCurrencySymbol.length());
		}
		else if(symbol.contains("BNB")) {
			baseCurrencySymbol = "BNB";
			currencySymbol = symbol.substring(0, symbol.length()-baseCurrencySymbol.length());
		}
		else if(symbol.contains("USDT")) {
			baseCurrencySymbol = "USDT";
			currencySymbol = symbol.substring(0, symbol.length()-baseCurrencySymbol.length());
		}
	}

	public Double getUpperBB() {
		return upperBB;
	}

	public void setUpperBB(Double upperBB) {
		this.upperBB = upperBB;
		this.stsUpperBB = (long)(upperBB * SHATOSHI);
		this.percentage = null;
	}
}
