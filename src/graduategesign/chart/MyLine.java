package graduategesign.chart;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.hxtt.sql.fi;

import graduategesign.AddNoisingFilter;
import graduategesign.UtilDipose;

public class MyLine {
  public static void main(String[] args) {
	//定义字体
    StandardChartTheme mChartTheme = new StandardChartTheme("CN");
    mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
    mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
    mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
    ChartFactory.setChartTheme(mChartTheme);		
    CategoryDataset mDataset = GetDataset();
    JFreeChart mChart = ChartFactory.createLineChart(
        "折线图",//图名字
        "年份",//横坐标
        "数量",//纵坐标
        mDataset,//数据集
        PlotOrientation.VERTICAL,
        true, // 显示图例
        true, // 采用标准生成器 
        false);// 是否生成超链接
    
    CategoryPlot mPlot = (CategoryPlot)mChart.getPlot();
    mPlot.setBackgroundPaint(Color.WHITE);
    mPlot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线
    mPlot.setOutlinePaint(Color.RED);//边界线
    
    ChartFrame mChartFrame = new ChartFrame("折线图", mChart);
    mChartFrame.pack();
    mChartFrame.setVisible(true);

  }
  public static CategoryDataset GetDataset()
  {
    DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
    File f;
    BufferedImage srcImage=null;
    try{
    	f=new File("E:\\动漫\\ttp.jpg");
    	if(f.isFile()&&f.exists()){
    		System.out.println("!!!!!");
    	}
    	srcImage=ImageIO.read(f);
    }catch (IOException e) {
		// TODO: handle exception
    	e.printStackTrace();
	}
    BufferedImage gray=UtilDipose.getGrayImage(srcImage, null);
    AddNoisingFilter nfilter=new AddNoisingFilter();
    nfilter.setNoiseType(AddNoisingFilter.NOISE_TYPE_GAUSSION);
    int count=64;
    int[] psnrs=new int[count];
    int[] snrs=new int[count];
    int[] epis=new int[count];
    for(int i=0;i<count;i++){
    	mDataset.addValue(UtilDipose.getPSNR(gray,nfilter.filter(gray, null)), "PSNR",(i+1)+"");
    	mDataset.addValue(UtilDipose.getSNR(gray,nfilter.filter(gray, null)), "SNR",(i+1)+"");
    	mDataset.addValue(UtilDipose.getEPI(gray,nfilter.filter(gray, null)), "EPI",(i+1)+"");
    }
   /* mDataset.addValue(1, "First", "2013");
    mDataset.addValue(3, "First", "2014");
    mDataset.addValue(2, "First", "2015");
    mDataset.addValue(6, "First", "2016");
    mDataset.addValue(5, "First", "2017");
    mDataset.addValue(12, "First", "2018");
    mDataset.addValue(14, "Second", "2013");
    mDataset.addValue(13, "Second", "2014");
    mDataset.addValue(12, "Second", "2015");
    mDataset.addValue(9, "Second", "2016");
    mDataset.addValue(5, "Second", "2017");*/
    mDataset.addValue(7, "Second", "2018");
    return mDataset;
  }


}