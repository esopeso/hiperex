package fi.muni.redhat.hiperex.util;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class XYChartProcessor {
	
	private static Logger log = Logger.getLogger(XYChartProcessor.class);
	private XYSeriesCollection dataSet = new XYSeriesCollection();
	
	public void addSeries(XYSeries series) {
		dataSet.addSeries(series);
	}
	
	public void produceChart(String fileName, String title) throws IOException {
		JFreeChart chart = 
				ChartFactory.createXYLineChart
				(title, "X", "Y", dataSet,  PlotOrientation.VERTICAL, false, false, false);
		
		ChartUtilities.saveChartAsJPEG(new File(fileName), chart, 800, 600);
		log.info("Chart "+fileName+" created.");
	}

}