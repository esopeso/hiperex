package fi.muni.redhat.hiperex.util;

import org.apache.log4j.Logger;

public class AvgDataProducer {
	
	private static Logger log = Logger.getLogger(AvgDataProducer.class);

	public double cycle(int n, Repeatable query) {
		log.info("Starting cycle loop: "+query.getClass());
		long time = 0;
		for (int i=1; i<n+1; i++) {
			 time = time + query.repeat();
		}
		double avg = (double)time/(double)n/(double)1000000;
		log.debug("Calculated average time: "+avg);
		return avg;
	}
}
