package fi.muni.redhat.hiperex.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.data.xy.XYSeries;

public class DataProducer {

	private static Logger log = Logger.getLogger(DataProducer.class);
	private static Logger fileLog = Logger.getLogger("fileApp");
	private Map<Integer, HashMap<String, Long>> queryResults = new HashMap<Integer, HashMap<String, Long>>();
	private HashMap<String, Double> avgLapResults = new HashMap<String, Double>();

	
	/**
	 * Repeats query execution and stores measured time
	 * @param n number of executions
	 * @param query query to execute
	 */
	public void cycle(int n, Repeatable query) {
		log.info("Starting cycle loop: " + query.getClass());
		fileLog.info("==== Query: "+ query.getClass().getName()+" ====");
		for (int i = 1; i < n + 1; i++) {
			queryResults.put(i, query.execute());
		}
	}

	/**
	 * 
	 * @return average time of query execution
	 */
	public double getAverageTime() {
		long time = 0;
		for (int i : queryResults.keySet()) {
			time = time + queryResults.get(i).get("overall");
		}
		double avg = (double) time / (double) queryResults.size() / (double) 1000000; //in milliseconds
		return avg;
	}

	/**
	 * 
	 * @param key series identifier
	 * @return series for XY chart
	 */
	public XYSeries getXYSeries(String key) {
		XYSeries series = new XYSeries(key);
		for (int i : queryResults.keySet()) {
			series.add(i, queryResults.get(i).get("overall") / (double) 1000000); //in milliseconds
		}
		return series;
	}
	/**
	 * Calculate average value for all lap keys
	 */
	public void calculateAvgLapResults() {
		fileLog.info("Calculated average lap times: ");
		for (String lap: queryResults.get(1).keySet()) {
			long time = 0;
			for (int i : queryResults.keySet()) {
				time = time + queryResults.get(i).get(lap);
			}
			double avg = (double) time / (double) queryResults.size() / (double) 1000000; //in milliseconds
			fileLog.info(lap+": "+avg);
			avgLapResults.put(lap, avg);
		}
		fileLog.info("--------------------");
	}
	
	/**
	 * 
	 * @param key lap identifier
	 * @return measured lap time
	 */
	public double getLapAverage(String key) {
		return avgLapResults.get(key);
	}
}
