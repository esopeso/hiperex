package fi.muni.redhat.hiperex.util.old;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.data.xy.XYSeries;

public class DataProducer {

	private static Logger log = Logger.getLogger(DataProducer.class);
	private Map<Integer, Long> queryResults = new HashMap<Integer, Long>();


	public void cycle(int n, Repeatable query) {
		log.info("Starting cycle loop: " + query.getClass());
		for (int i = 1; i < n + 1; i++) {
			queryResults.put(i, query.execute());
		}
	}

	public double getAverageTime() {
		long time = 0;
//		cycle(n, query);
		for (int i : queryResults.keySet()) {
			time = time + queryResults.get(i);
		}
		double avg = (double) time / (double) queryResults.size() / (double) 1000000; //in milliseconds
		return avg;
	}

	public XYSeries getXYSeries(String key) {
		XYSeries series = new XYSeries(key);
		for (int i : queryResults.keySet()) {
			series.add(i, queryResults.get(i) / (double) 1000000); //in milliseconds
		}
		return series;
	}
}
