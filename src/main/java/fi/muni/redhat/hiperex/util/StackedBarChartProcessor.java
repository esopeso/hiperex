package fi.muni.redhat.hiperex.util;

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

public class StackedBarChartProcessor {
	
	private int xResolution = 800;
	private int yResolution = 600;

	private static Logger log = Logger.getLogger(GroupedStackedBarChartProcessor.class);
	private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	
	public void produceChart(String fileName, String title) throws IOException {
		JFreeChart chart = 
				ChartFactory.createStackedBarChart(title, "Query type", "Time (ms)", dataset, PlotOrientation.VERTICAL, true, false, false);
		
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryItemRenderer renderer = plot.getRenderer();
		CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0.00"));
		renderer.setBaseItemLabelGenerator(generator);
		renderer.setBaseItemLabelsVisible(true);
		
		ChartUtilities.saveChartAsJPEG(new File(fileName), chart, 800, 600);
		log.info("Chart "+fileName+" created.");
		resetDataSet();
		
	}
	
	public void addValue(Double time, String category, String series) {
			dataset.addValue(time, category, series);
	}
	
	public void resetDataSet() {
		dataset.clear();
	}
	
	public void setResolution(int x, int y) {
		this.xResolution = x;
		this.yResolution = y;
	}
}
