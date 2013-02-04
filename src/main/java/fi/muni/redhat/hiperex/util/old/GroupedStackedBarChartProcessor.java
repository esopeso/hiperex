package fi.muni.redhat.hiperex.util.old;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.DefaultCategoryDataset;

public class GroupedStackedBarChartProcessor {
	
	private static Logger log = Logger.getLogger(GroupedStackedBarChartProcessor.class);
	private SubCategoryAxis axis = new SubCategoryAxis("Type");
	private KeyToGroupMap map = new KeyToGroupMap();
	private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	
	public void produceChart(String fileName, String title) throws IOException {
		JFreeChart chart = 
				ChartFactory.createStackedBarChart(title, "n", "Time (ms)", dataset, PlotOrientation.VERTICAL, true, false, false);
		
		
		
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		GroupedStackedBarRenderer renderer = new  GroupedStackedBarRenderer();
		renderer.setSeriesToGroupMap(map);
		plot.setDomainAxis(axis);
		plot.setRenderer(renderer);
		
		ChartUtilities.saveChartAsJPEG(new File(fileName), chart, 800, 600);
		log.info("Chart "+fileName+" created.");
		resetDataSet();
		
	}
	
	public void addSubCategory(String subCategory) {
		axis.addSubCategory(subCategory);
	}
	
	public void addGroupMap(String key, String group) {
		map.mapKeyToGroup(key, group);
	}
	
	public void addValue(Double time, String queryPart, String queryType) {
			dataset.addValue(time, queryPart, queryType);
	}
	
	public void resetDataSet() {
		dataset.clear();
	}
	
	public void resetAll() {
		dataset.clear();
		map = new KeyToGroupMap();
		axis = new SubCategoryAxis("Type");
	}

}
