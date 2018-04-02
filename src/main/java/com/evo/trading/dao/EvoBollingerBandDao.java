package com.evo.trading.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.evo.trading.object.BollingerBand;

public class EvoBollingerBandDao extends ConfigDao {
	// Singleton design pattern
	private static EvoBollingerBandDao instance = null;

	private EvoBollingerBandDao() {
		// TODO Auto-generated constructor stub
	}

	public static EvoBollingerBandDao getInstance() {
		if (instance == null) {
			instance = new EvoBollingerBandDao();
		}
		return instance;
	}
	
	//
	private static String filepath = "/" + EvoBollingerBandDao.class.getProtectionDomain().getCodeSource().getLocation().getPath();

	public List<BollingerBand> getBollingerBands() {
		List<BollingerBand> bollingerbands = new ArrayList<>();
		switch (database) {
		case FILE:
			for (String candlestickInterval : BollingerBand.CANDLESTICK_INTERVALS) {
				bollingerbands.addAll( getBollingerBands("BinanceBollingerBand" + candlestickInterval + ".dat") );
			}
			break;

		case MYSQL:
			break;
		}

		return bollingerbands;
	}

	public List<BollingerBand> getBollingerBands(String fileName) {
		List<BollingerBand> bollingerbands = new ArrayList<>();
		switch (database) {
		case FILE:
			try {
				File file = new File(filepath + fileName);
				System.out.println("getBollingerBands - path: " + filepath + fileName);
				if (!file.exists()) {
					break;
				}
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				bollingerbands = (List<BollingerBand>) ois.readObject();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case MYSQL:
			break;
		}

		return bollingerbands;
	}

	public void saveBollingerBands(List<BollingerBand> bollingerbands) {
		switch (database) {
		case FILE:
			try {
				File file = new File(filepath + "Bollingerband.dat");
				System.out.println("1 saveBollingerBans - path: " + filepath + "Bollingerband.dat");
				FileOutputStream fos;
				fos = new FileOutputStream(file);
				ObjectOutputStream oos;
				oos = new ObjectOutputStream(fos);
				oos.writeObject(bollingerbands);
				oos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case MYSQL:
			break;
		}
	}

	public void saveBollingerBands(String fileName, List<BollingerBand> bollingerbands) {
		switch (database) {
		case FILE:
			try {
				File file = new File(filepath + fileName);
				System.out.println("2 saveBollingerBands - path:" + filepath + fileName);
				FileOutputStream fos;
				fos = new FileOutputStream(file);
				ObjectOutputStream oos;
				oos = new ObjectOutputStream(fos);
				oos.writeObject(bollingerbands);
				oos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case MYSQL:
			break;
		}
	}
}
