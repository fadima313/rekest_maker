package com.rekest.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rekest.helpers.RekestData;

public class TestDataBase {

	public final static Logger logger = LogManager.getLogger(TestDataBase.class);

	public static void main(String[] args) {
		RekestData rekestData = new RekestData();
		rekestData.initAllEntity();
	}
	
	
}
