package com.binance.api.client.domain.market;

import com.binance.api.client.constant.BinanceApiConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Kline/Candlestick bars for a symbol. Klines are uniquely identified by their open time.
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder()
@JsonIgnoreProperties(ignoreUnknown = true)
public class Candlestick {

  private Long openTime; // 0

  private String open; // 1

  private String high; // 2

  private String low; // 3

  private String close; // 4

  private String volume; // 5

  private Long closeTime; // 6

  private String quoteAssetVolume; // 7

  private Long numberOfTrades; // 8

  private String takerBuyBaseAssetVolume; // 9

  private String takerBuyQuoteAssetVolume; // 10
  
  public Candlestick() {
	  
  }
  
  public Candlestick(Object[] arr) {
	  openTime = (long)arr[0];
	  open = arr[1].toString();
	  high = arr[2].toString();
	  low = arr[3].toString();
	  close = arr[4].toString();
	  volume = arr[5].toString();
	  closeTime = (long)arr[6];
	  quoteAssetVolume = arr[7].toString();
	  numberOfTrades = Long.valueOf(arr[8].toString());
	  takerBuyBaseAssetVolume = arr[9].toString();
	  takerBuyQuoteAssetVolume = arr[10].toString();
  }

  public Long getOpenTime() {
    return openTime;
  }

  public void setOpenTime(Long openTime) {
    this.openTime = openTime;
  }

  public String getOpen() {
    return open;
  }

  public void setOpen(String open) {
    this.open = open;
  }

  public String getHigh() {
    return high;
  }

  public void setHigh(String high) {
    this.high = high;
  }

  public String getLow() {
    return low;
  }

  public void setLow(String low) {
    this.low = low;
  }

  public String getClose() {
    return close;
  }

  public void setClose(String close) {
    this.close = close;
  }

  public String getVolume() {
    return volume;
  }

  public void setVolume(String volume) {
    this.volume = volume;
  }

  public Long getCloseTime() {
    return closeTime;
  }

  public void setCloseTime(Long closeTime) {
    this.closeTime = closeTime;
  }

  public String getQuoteAssetVolume() {
    return quoteAssetVolume;
  }

  public void setQuoteAssetVolume(String quoteAssetVolume) {
    this.quoteAssetVolume = quoteAssetVolume;
  }

  public Long getNumberOfTrades() {
    return numberOfTrades;
  }

  public void setNumberOfTrades(Long numberOfTrades) {
    this.numberOfTrades = numberOfTrades;
  }

  public String getTakerBuyBaseAssetVolume() {
    return takerBuyBaseAssetVolume;
  }

  public void setTakerBuyBaseAssetVolume(String takerBuyBaseAssetVolume) {
    this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
  }

  public String getTakerBuyQuoteAssetVolume() {
    return takerBuyQuoteAssetVolume;
  }

  public void setTakerBuyQuoteAssetVolume(String takerBuyQuoteAssetVolume) {
    this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
  }

//  @Override
//  public String toString() {
//    return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
//        .append("openTime", openTime)
//        .append("open", open)
//        .append("high", high)
//        .append("low", low)
//        .append("close", close)
//        .append("volume", volume)
//        .append("closeTime", closeTime)
//        .append("quoteAssetVolume", quoteAssetVolume)
//        .append("numberOfTrades", numberOfTrades)
//        .append("takerBuyBaseAssetVolume", takerBuyBaseAssetVolume)
//        .append("takerBuyQuoteAssetVolume", takerBuyQuoteAssetVolume)
//        .toString();
//  }
}