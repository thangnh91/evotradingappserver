package com.evo.trading.constant;

public class EvoConstants {
	
	public static final long L_ONE_SECOND = 1000; // 1s = 1millis
	public static final String S_ONE_SECOND = "1s";
	
	public static final long L_THIRTY_SECONDS = L_ONE_SECOND * 30;
	public static final String S_THIRTY_SECONDS = "30s";
	
	public static final long L_ONE_MINUTE = L_THIRTY_SECONDS << 1;//* 2;
	public static final String S_ONE_MINUTE = "1m";
	
	public static final long L_FIVE_MINUTES = L_ONE_MINUTE * 5;
	public static final String S_FIVE_MINUTES = "5m";
	
	public static final long L_FIFTEEN_MINUTES = L_FIVE_MINUTES * 3;
	public static final String S_FIFTEEN_MINUTES = "15m";
	
	public static final long L_HALF_HOURLY = L_FIFTEEN_MINUTES << 1;//* 2;
	public static final String S_HALF_HOURLY = "30m";
	
	public static final long L_HOURLY = L_HALF_HOURLY << 1; //* 2;
	public static final String S_HOURLY = "1h";
	
	public static final long L_TWO_HOURLY = L_HOURLY << 1; //* 2;
	public static final String S_TWO_HOURLY = "2h";
	
	public static final long L_FOUR_HOURLY = L_TWO_HOURLY << 1; //* 2;
	public static final String S_FOUR_HOURLY = "4h";
	
	public static final long L_SIX_HOURLY = L_TWO_HOURLY * 3;
	public static final String S_SIX_HOURLY = "6h";
	
	public static final long L_EIGHT_HOURLY = L_FOUR_HOURLY << 1; //* 2;
	public static final String S_EIGHT_HOURLY = "8h";
	
	public static final long L_TWELVE_HOURLY = L_SIX_HOURLY << 1; //* 2;
	public static final String S_TWELVE_HOURLY = "12h";
	
	public static final long L_DAILY = L_TWELVE_HOURLY << 1; //* 2;
	public static final String S_DAILY = "1d";
	
	public static final long L_WEEKLY = L_DAILY * 7;
	public static final String S_WEEKLY = "1w";

	public EvoConstants() {
		// TODO Auto-generated constructor stub
	}

}
