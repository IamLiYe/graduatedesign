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
 * ����ͳ�Ƹ���������
 * @author Administrator
 *
 */
public class StatisticsJPanel {
    private static final String SAVEPATH="D:\\chart\\";
	public static void main(String[] args) {  
		   
//	     �������ͼ��Category�����ݶ���  
	   
	       DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
	   
	       dataset.addValue(100, "����", "ƻ��");  
	   
	       dataset.addValue(100, "�Ϻ�", "ƻ��");  
	   
	       dataset.addValue(100, "����", "ƻ��");  
	   
	       dataset.addValue(200, "����", "����");  
	   
	       dataset.addValue(200, "�Ϻ�", "����");  
	   
	       dataset.addValue(200, "����", "����");  
	   
	       dataset.addValue(300, "����", "����");  
	   
	       dataset.addValue(300, "�Ϻ�", "����");  
	   
	       dataset.addValue(300, "����", "����");  
	   
	       dataset.addValue(400, "����", "�㽶");  
	   
	       dataset.addValue(400, "�Ϻ�", "�㽶");  
	   
	       dataset.addValue(400, "����", "�㽶");  
	   
	       dataset.addValue(500, "����", "��֦");  
	   
	       dataset.addValue(500, "�Ϻ�", "��֦");  
	   
	       dataset.addValue(500, "����", "��֦");  
	       //����������ʽ  
	       StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
	       //���ñ�������  
	       standardChartTheme.setExtraLargeFont(new Font("����",Font.BOLD,20));  
	       //����ͼ��������  
	       standardChartTheme.setRegularFont(new Font("����",Font.PLAIN,15));  
	       //�������������  
	       standardChartTheme.setLargeFont(new Font("����",Font.PLAIN,15));  
	       //Ӧ��������ʽ  
	       ChartFactory.setChartTheme(standardChartTheme);  
	        JFreeChart chart=ChartFactory.createBarChart3D("ˮ������ͼ", "ˮ��", "ˮ��", dataset, PlotOrientation.VERTICAL, true, true, true);  
//	        TextTitle textTitle = chart.getTitle();  
//	      textTitle.setFont(new Font("����", Font.BOLD, 20));  
//	      LegendTitle legend = chart.getLegend();  
//	      if (legend != null) {  
//	          legend.setItemFont(new Font("����", Font.BOLD, 20));  
//	      }  
	       
	       ChartFrame  frame=new ChartFrame ("ˮ������ͼ ",chart,true);  
	       Calendar now=Calendar.getInstance();
	       String fileName=(SAVEPATH+now.getTimeInMillis());
	       //����ͼƬ
	       UtilDipose.saveChartAsFile(chart, fileName, 400, 400);
	       frame.pack();  
	       frame.setVisible(true); 
	}
	
}
