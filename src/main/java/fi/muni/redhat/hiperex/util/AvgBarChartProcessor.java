package fi.muni.redhat.hiperex.util;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class AvgBarChartProcessor {
	
	private static Logger log = Logger.getLogger(AvgBarChartProcessor.class);
	private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	
	public void addValue(Double value, String category, String serie) {
		dataset.addValue(value, category, serie);
	}

	public void produceChart(String fileName, String title) throws IOException {
		JFreeChart chart = 
				ChartFactory.createBarChart
				(title, "Query", "Time", dataset, PlotOrientation.VERTICAL, true, false, false);
		
		ChartUtilities.saveChartAsJPEG(new File(fileName), chart, 800, 600);
		log.info("Chart "+fileName+" created.");
	}
}
