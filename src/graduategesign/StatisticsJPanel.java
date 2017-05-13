package graduategesign;

import java.awt.Font;
import java.util.Date;
import java.util.Calendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * 锟斤拷锟斤拷统锟狡革拷锟斤拷锟斤拷锟斤拷锟斤拷
 * @author Administrator
 *
 */
public class StatisticsJPanel {
    private static final String SAVEPATH="D:\\chart\\";
	public static void main(String[] args) {  
		   
//	     创建类别图（Category）数据对象  
	   
	       DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
	   
	       dataset.addValue(100, "北京", "苹果");  
	   
	       dataset.addValue(100, "上海", "苹果");  
	   
	       dataset.addValue(100, "广州", "苹果");  
	   
	       dataset.addValue(200, "北京", "梨子");  
	   
	       dataset.addValue(200, "上海", "梨子");  
	   
	       dataset.addValue(200, "广州", "梨子");  
	   
	       dataset.addValue(300, "北京", "葡萄");  
	   
	       dataset.addValue(300, "上海", "葡萄");  
	   
	       dataset.addValue(300, "广州", "葡萄");  
	   
	       dataset.addValue(400, "北京", "香蕉");  
	   
	       dataset.addValue(400, "上海", "香蕉");  
	   
	       dataset.addValue(400, "广州", "香蕉");  
	   
	       dataset.addValue(500, "北京", "荔枝");  
	   
	       dataset.addValue(500, "上海", "荔枝");  
	   
	       dataset.addValue(500, "广州", "荔枝");  
	       //创建主题样式  
	       StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
	       //设置标题字体  
	       standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));  
	       //设置图例的字体  
	       standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
	       //设置轴向的字体  
	       standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));  
	       //应用主题样式  
	       ChartFactory.setChartTheme(standardChartTheme);  
	        JFreeChart chart=ChartFactory.createBarChart3D("水果产量图", "水果", "水果", dataset, PlotOrientation.VERTICAL, true, true, true);  
//	        TextTitle textTitle = chart.getTitle();  
//	      textTitle.setFont(new Font("宋体", Font.BOLD, 20));  
//	      LegendTitle legend = chart.getLegend();  
//	      if (legend != null) {  
//	          legend.setItemFont(new Font("宋体", Font.BOLD, 20));  
//	      }  
	       
	       ChartFrame  frame=new ChartFrame ("水果产量图 ",chart,true);  
	       Calendar now=Calendar.getInstance();
	       String fileName=(SAVEPATH+now.getTimeInMillis());
	       //保存图片
	       UtilDipose.saveChartAsFile(chart, fileName, 400, 400);
	       frame.pack();  
	       frame.setVisible(true); 
	}
	
}
