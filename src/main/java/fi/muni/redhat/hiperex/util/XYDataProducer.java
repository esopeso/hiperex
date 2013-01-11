package fi.muni.redhat.hiperex.util;

import org.jfree.data.xy.XYSeries;

public class XYDataProducer {
	
	// x,y = sequence,time
	public XYSeries cycle(int n, Repeatable repeatable, String key) {
		XYSeries series = new XYSeries(key);
		double y =0;
		for (int i=1; i<n+1; i++) {
			y=repeatable.repeat();
			series.add(i, y);
		}
		return series;
	}

}
