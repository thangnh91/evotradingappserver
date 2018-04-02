package com.evo.trading.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.TreeMap;

import com.coinmarketcap.api.objects.CoinmarketcapTickerPrice;
import com.evo.trading.object.BollingerBand;

public class EvoTradingDao {
	private static EvoTradingDao instance = null;
	
	private EvoTradingDao() {
		// TODO Auto-generated constructor stub
	}
	
	public static EvoTradingDao getInstance() {
		if(instance == null) {
			instance = new EvoTradingDao();
		}
		return instance;
	}

	public void saveCurrencyTreeMap(TreeMap<String, CoinmarketcapTickerPrice> treeMap) {
		try {
			File file = new File("CurrencyTreeMap.dat");
			FileOutputStream fos;
			fos = new FileOutputStream(file);
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(fos);
			oos.writeObject(treeMap);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TreeMap<String, CoinmarketcapTickerPrice> getCurrencyTreeMap() {
		TreeMap<String, CoinmarketcapTickerPrice> treeMap = null;
		try {
			File file = new File("CurrencyTreeMap.dat");
			if (!file.exists()) {
				return treeMap;
			}
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			treeMap = (TreeMap<String, CoinmarketcapTickerPrice>) ois.readObject();
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return treeMap;
	}
}
