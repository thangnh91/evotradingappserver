package com.evo.trading;

import java.util.TreeMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.coinmarketcap.api.client.impl.CoinmarketcapApiService;
import com.coinmarketcap.api.objects.CoinmarketcapTickerPrice;
import com.coinmarketcap.api.server.impl.CoinmarketcapCurrencyService;
import com.evo.trading.constant.EvoConstants;
import com.evo.trading.dao.EvoTradingDao;
import com.evo.trading.server.EvoBollingerBandService;

@SpringBootApplication
public class EvotradingappServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvotradingappServerApplication.class, args);
		
//		CoinmarketcapCurrencyService cmcCurrencyService = new CoinmarketcapCurrencyService();
//		try {
//			cmcCurrencyService.call();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

		try {
			TreeMap<String, CoinmarketcapTickerPrice> treeMap = (new CoinmarketcapCurrencyService()).call();
			EvoTradingDao.getInstance().saveCurrencyTreeMap(treeMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EvoBollingerBandService evoBBService = new EvoBollingerBandService("Evo Bollinger Band Service", EvoConstants.L_FIVE_MINUTES);
		evoBBService.start();
	}
}