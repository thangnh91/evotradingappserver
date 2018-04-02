package com.evo.trading.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.binance.api.server.impl.BinanceBollingerBandService;
import com.evo.trading.dao.EvoBollingerBandDao;
import com.evo.trading.object.BollingerBand;

public class EvoBollingerBandService implements Runnable{

	// Thread implements
	protected Thread thread;
	protected String threadName;
	protected boolean threadSuspended = false;
	protected long interval = 1000; // milliseconds

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
				for(String candlestickInterval : BollingerBand.CANDLESTICK_INTERVALS) {
					BinanceBollingerBandService bnbBollingerBandService = new BinanceBollingerBandService("Binance BollingerBand Service - " + candlestickInterval, 10L, candlestickInterval);
					bnbBollingerBandService.start();
				}
				
//				ExecutorService executor = Executors.newFixedThreadPool(10);
////				List< Future<List<BollingerBand>> > futureBBList = new ArrayList<>();
//				List<BollingerBand> bollingerbands = new ArrayList<>();
//				
//				for(String interval : intervals) {
//					Future< List<BollingerBand> > futureBBs = executor.submit(new BinanceBollingerBandService(interval));
//					bollingerbands.addAll(futureBBs.get());
////					futureBBList.add(future);
//				}
//				
////				for(Future< List<BollingerBand> > futureBBs : futureBBList) {
////					bollingerbands.addAll(futureBBs.get());
////				}
//				
//				EvoBollingerBandDao.getInstance().saveBollingerBands(bollingerbands);
				// END your codes
				long etime = System.currentTimeMillis();
				System.out.println(threadName + " running: " + ((etime - stime) / 60 / 1000));
				
				Thread.sleep(interval);
				synchronized (this) {
					while (threadSuspended && thread == thisThread)
						wait();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// END: Thread implements
	
	public EvoBollingerBandService() {
		// TODO Auto-generated constructor stub
	}

	public EvoBollingerBandService(String threadName, long interval) {
		this.threadName = threadName;
		this.interval = interval;
	}
}
