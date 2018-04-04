package com.binance.api.client.domain.market;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Binance_24hrTickerPrice {
	@JsonProperty("symbol") String symbol;
	@JsonProperty("priceChange") String priceChange;
	@JsonProperty("priceChangePercent") String priceChangePercent;
	@JsonProperty("weightedAvgPrice") String weightedAvgPrice;
	@JsonProperty("prevClosePrice") String prevClosePrice;
	@JsonProperty("lastPrice") String lastPrice;
	@JsonProperty("lastQty") String lastQty;
	@JsonProperty("bidPrice") String bidPrice;
	@JsonProperty("bidQty") String bidQty;
	@JsonProperty("askPrice") String askPrice;
	@JsonProperty("askQty") String askQty;
	@JsonProperty("openPrice") String openPrice;
	@JsonProperty("highPrice") String highPrice;
	@JsonProperty("lowPrice") String lowPrice;
	@JsonProperty("volume") String volume;
	@JsonProperty("quoteVolume") String quoteVolume;
	@JsonProperty("openTime") Long openTime;
	@JsonProperty("closeTime") Long closeTime;
	@JsonProperty("firstId") Integer firstId;
	@JsonProperty("lastId") Integer lastId;
	@JsonProperty("count") Integer count;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getPriceChange() {
		return priceChange;
	}
	public void setPriceChange(String priceChange) {
		this.priceChange = priceChange;
	}
	public String getPriceChangePercent() {
		return priceChangePercent;
	}
	public void setPriceChangePercent(String priceChangePercent) {
		this.priceChangePercent = priceChangePercent;
	}
	public String getWeightedAvgPrice() {
		return weightedAvgPrice;
	}
	public void setWeightedAvgPrice(String weightedAvgPrice) {
		this.weightedAvgPrice = weightedAvgPrice;
	}
	public String getPrevClosePrice() {
		return prevClosePrice;
	}
	public void setPrevClosePrice(String prevClosePrice) {
		this.prevClosePrice = prevClosePrice;
	}
	public String getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}
	public String getLastQty() {
		return lastQty;
	}
	public void setLastQty(String lastQty) {
		this.lastQty = lastQty;
	}
	public String getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(String bidPrice) {
		this.bidPrice = bidPrice;
	}
	public String getBidQty() {
		return bidQty;
	}
	public void setBidQty(String bidQty) {
		this.bidQty = bidQty;
	}
	public String getAskPrice() {
		return askPrice;
	}
	public void setAskPrice(String askPrice) {
		this.askPrice = askPrice;
	}
	public String getAskQty() {
		return askQty;
	}
	public void setAskQty(String askQty) {
		this.askQty = askQty;
	}
	public String getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(String openPrice) {
		this.openPrice = openPrice;
	}
	public String getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}
	public String getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getQuoteVolume() {
		return quoteVolume;
	}
	public void setQuoteVolume(String quoteVolume) {
		this.quoteVolume = quoteVolume;
	}
	public Long getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Long openTime) {
		this.openTime = openTime;
	}
	public Long getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Long closeTime) {
		this.closeTime = closeTime;
	}
	public Integer getFirstId() {
		return firstId;
	}
	public void setFirstId(Integer firstId) {
		this.firstId = firstId;
	}
	public Integer getLastId() {
		return lastId;
	}
	public void setLastId(Integer lastId) {
		this.lastId = lastId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	
}
