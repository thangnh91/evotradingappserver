package com.binance.api.server.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.impl.BinanceApiService;
import com.coinmarketcap.api.client.impl.CoinmarketcapApiService;
import com.coinmarketcap.api.objects.CoinmarketcapTickerPrice;
import com.coinmarketcap.api.server.impl.CoinmarketcapCurrencyService;
import com.evo.trading.dao.EvoBollingerBandDao;
import com.evo.trading.dao.EvoTradingDao;
import com.evo.trading.object.BollingerBand;
import com.evo.trading.utils.Utilities;

public class BinanceBollingerBandService implements Runnable {

	// Thread implements
	protected Thread thread;
	protected String threadName;
	protected boolean threadSuspended = false;
	protected long interval; // milliseconds

	public void suspend() {
		threadSuspended = true;
		System.out.println(threadName + " suspended");
	}

	public synchronized void resume() {
		threadSuspended = false;
		notify();
		System.out.println(threadName + " resumed");
	}

	public synchronized void stop() {
		thread = null;
		notify();
		System.out.println(threadName + " stoped");
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
			System.out.println(threadName + " started");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread thisThread = Thread.currentThread();
		while (thread == thisThread) {
			try {
				long stime = System.currentTimeMillis();
				// Please place your codes here.
				// ...
				List<BollingerBand> bollingerbands = new ArrayList<>();
//				TreeMap<String, CoinmarketcapTickerPrice> treeMap = EvoTradingDao.getInstance().getCurrencyTreeMap();
				List<TickerPrice> tickerPrices = BinanceApiService.getTickerPrices();
				CoinmarketcapTickerPrice cmcTickerPrice = CoinmarketcapApiService.getCoinmarketcapTickerPrice("bitcoin", null);
				for (TickerPrice tickerPrice : tickerPrices) {
					BollingerBand bb = calcBollingerBand(tickerPrice,
							BinanceApiService.getCandlestickBars(tickerPrice.getSymbol(), candlestickInterval, 20, null, null));
					if (bb == null) {
						continue;
					}
					if (bb.isOutOfBands()) {
						bb.getComparativePercentage(); // to calc the comparative percentage
						bb.setUsdPrice( Double.valueOf( tickerPrice.getPrice() ) * Double.valueOf( cmcTickerPrice.getPrice_usd() ) );
						bb.setBtcPrice( Double.valueOf( tickerPrice.getPrice() ) );
						List<Candlestick> _1wCandlesticks = BinanceApiService.getCandlestickBars(tickerPrice.getSymbol(), "1w", 1, null, null);
						List<Candlestick> _1MCandlesticks = BinanceApiService.getCandlestickBars(tickerPrice.getSymbol(), "1M", 1, null, null);
						bb.set_1wHighPrice( Double.valueOf(_1wCandlesticks.get(0).getHigh()) );
						bb.set_1wLowPrice( Double.valueOf(_1wCandlesticks.get(0).getLow()) );
						bb.set_1MHighPrice( Double.valueOf(_1MCandlesticks.get(0).getHigh()) );
						bb.set_1MLowPrice( Double.valueOf(_1MCandlesticks.get(0).getLow()) );
						bollingerbands.add(bb);
					}
				}
				EvoBollingerBandDao.getInstance().saveBollingerBands("BinanceBollingerBand" + candlestickInterval + ".dat", bollingerbands);
				// END your codes
				long etime = System.currentTimeMillis();
				System.out.println(threadName + " running: " + ((etime - stime)));

				stop();

				Thread.sleep(interval);
				synchronized (this) {
					while (threadSuspended && thread == thisThread)
						wait();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// END: Thread implements
	
	private String candlestickInterval;
	
	public BinanceBollingerBandService() {
		threadName = "";
		interval = 1000;
		candlestickInterval = "30m";
	}
	
	public BinanceBollingerBandService(String threadName, long interval, String candlestickInterval) {
		this.threadName = threadName;
		this.interval = interval;
		this.candlestickInterval = candlestickInterval;
	}

	private BollingerBand calcBollingerBand(TickerPrice tickerPrice, List<Candlestick> candlestickBars) {
		if (tickerPrice == null || candlestickBars == null)
			return null;

		List<Double> closePrices = new ArrayList<>();
		int i=0, _10_period_avg_volume_num = 10;
		Double quoteAssetVolumeSum = 0.;
		
		for (Candlestick cdstBar : candlestickBars) {
			closePrices.add(Double.valueOf(cdstBar.getClose()));
			if(i < _10_period_avg_volume_num) {
				++i;
				quoteAssetVolumeSum += Double.valueOf( cdstBar.getQuoteAssetVolume() );
			}
		}
		
		BollingerBand bb = new BollingerBand("binance", tickerPrice.getSymbol(), candlestickInterval,
				Double.valueOf(tickerPrice.getPrice()));
		bb.set_10PeriodAVGVolume( quoteAssetVolumeSum / _10_period_avg_volume_num );
		bb.setTimestamp(System.currentTimeMillis());
		bb.setSma(Utilities.average(closePrices));
		Double stddev = Utilities.stdDeviation(closePrices);
		bb.setUpperBB(bb.getSma() + (stddev * BollingerBand.FACTOR));
		bb.setLowerBB(bb.getSma() - (stddev * BollingerBand.FACTOR));
		if (bb.isOutOfBands())
			return bb;

		return null;
	}
}

//public class BinanceBollingerBandService implements Callable<List<BollingerBand>> {
//	private String interval;
//
//	public BinanceBollingerBandService(String interval) {
//		this.interval = interval;
//	}
//
//	public List<BollingerBand> call() throws Exception {
//		List<BollingerBand> bollingerbands = new ArrayList<>();
//		List<TickerPrice> tickerPrices = BinanceApiService.getTickerPrices();
//		for (TickerPrice tickerPrice : tickerPrices) {
//			BollingerBand bb = calcBollingerBand(tickerPrice,
//					BinanceApiService.getCandlestickBars(tickerPrice.getSymbol(), interval, 20, null, null));
//			if (bb == null) {
//				continue;
//			}
//			if (bb.isOutOfBands()) {
//				bollingerbands.add(bb);
//			}
//		}
//		return bollingerbands;
//	}
//
//	private BollingerBand calcBollingerBand(TickerPrice tickerPrice, List<Candlestick> candlestickBars) {
//		if (tickerPrice == null || candlestickBars == null)
//			return null;
//
//		List<Double> closePrices = new ArrayList<>();
//		for (Candlestick cdstBar : candlestickBars) {
//			closePrices.add(Double.valueOf(cdstBar.getClose()));
//		}
//
//		BollingerBand bb = new BollingerBand("binance", tickerPrice.getSymbol(), interval,
//				Double.valueOf(tickerPrice.getPrice()));
//		bb.setSma(Utilities.average(closePrices));
//		Double stddev = Utilities.stdDeviation(closePrices);
//		bb.setUpperBB(bb.getSma() + (stddev * BollingerBand.FACTOR));
//		bb.setLowerBB(bb.getSma() - (stddev * BollingerBand.FACTOR));
//
//		if (bb.isOutOfBands())
//			return bb;
//
//		return null;
//	}
//}
