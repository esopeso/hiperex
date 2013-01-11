package fi.muni.redhat.hiperex.chart;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartGenerator {
	
	
	
	public XYSeriesCollection createSeries() {
		XYSeries series1 = new XYSeries("test1");
		
		series1.add(1.0, 2.0);
		series1.add(2.0, 3.0);
		series1.add(3.0, 5.0);
		series1.add(4.0, 2.0);
		series1.add(5.0, 1.0);
		series1.add(6.0, 0.50);
		series1.add(7.0, 0.20);
		
		XYSeries series2 = new XYSeries("test2");
		series2.add(1.0, 4.0);
		series2.add(2.0, 3.0);
		series2.add(3.0, 6.0);
		series2.add(4.0, 5.0);
		series2.add(5.0, 3.0);
		series2.add(6.0, 2.50);
		series2.add(7.0, 1.20);
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		
		return dataset;
	}
	
	public void chartOutput(JFreeChart chart) throws IOException {
		ChartUtilities.saveChartAsJPEG(new File("testchart.jpeg"), chart, 400, 300);
	}
	

}
