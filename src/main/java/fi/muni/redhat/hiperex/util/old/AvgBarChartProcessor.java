package fi.muni.redhat.hiperex.util.old;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class AvgBarChartProcessor {
	
	private static Logger log = Logger.getLogger(AvgBarChartProcessor.class);
	private DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
	
	public void addValue(Double value, String category, String serie) {
		dataSet.addValue(value, category, serie);
	}

	public void produceChart(String fileName, String title) throws IOException {		
		JFreeChart chart = 
				ChartFactory.createBarChart
				(title, "Query", "Time (ms)", dataSet, PlotOrientation.VERTICAL, true, false, false);
		
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryItemRenderer renderer = plot.getRenderer();
		CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0.00"));
		renderer.setBaseItemLabelGenerator(generator);
		renderer.setBaseItemLabelsVisible(true);
		
		ChartUtilities.saveChartAsJPEG(new File(fileName), chart, 800, 600);
		log.info("Chart "+fileName+" created.");
		dataSet.clear(); // clean dataSet for next usage
	}
}
