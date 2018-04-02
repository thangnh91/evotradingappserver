package com.coinmarketcap.api.server.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import com.coinmarketcap.api.client.impl.CoinmarketcapApiService;
import com.coinmarketcap.api.objects.CoinmarketcapTickerPrice;

public class CoinmarketcapCurrencyService implements Callable< TreeMap<String, CoinmarketcapTickerPrice> >{

	public CoinmarketcapCurrencyService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TreeMap<String, CoinmarketcapTickerPrice> call() throws Exception {
		// TODO Auto-generated method stub
		TreeMap<String, CoinmarketcapTickerPrice> treeMap = new TreeMap<>();
		List<CoinmarketcapTickerPrice> cmcTickerPrices = new ArrayList<>();
		cmcTickerPrices = CoinmarketcapApiService.getCoinmarketcapTickerPrices(0, 0, null);
		for(CoinmarketcapTickerPrice cmcTickerPrice : cmcTickerPrices) {
			treeMap.put(cmcTickerPrice.getSymbol(), cmcTickerPrice);
			
//			// rename file name: 16x16 & 32x32
//			File coinnameFile = new File("C:\\Users\\EVOTEAM\\Pictures\\coins\\32x32\\" + cmcTickerPrice.getId() + ".png");
//			File coinsymbolFile = new File("C:\\Users\\EVOTEAM\\Pictures\\coins\\32x32\\" + cmcTickerPrice.getSymbol() + ".png");
//			
//			coinnameFile.renameTo(coinsymbolFile);
			
		}
		
		return treeMap;
	}

	
}
