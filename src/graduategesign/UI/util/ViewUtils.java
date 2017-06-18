package graduategesign.UI.util;

import java.awt.Component;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;

public class ViewUtils {
	
	/**
	 * 
	 * @param slider 滑动条
	 * @param min 最大值
	 * @param max 最小值
	 * @param initValue 初始值
	 * @param majorTrik 大刻点	
	 * @param minTrik 小刻点
	 * @param type 0为整数表示，1为百分比表示
	 */
	public ViewUtils setScorll(JSlider slider,int min,int max,int initValue
			,int majorTrik,int minTrik,int type){
		//设置最大最小值
		slider.setMaximum(max);
		slider.setMinimum(min);
		//设置初值
		slider.setValue(initValue);
		//显示刻度尺
		slider.setMajorTickSpacing(majorTrik);
		slider.setMinorTickSpacing(minTrik);
		slider.setPaintTicks(true);
		//强制对齐刻度尺
		//slider.setSnapToTicks(true);
		
		Hashtable<Integer, Component> labels=new Hashtable<Integer,Component>();
		if(type==0){
			for(int i=min;i<max;i+=majorTrik){
				//String temp=""+i;
				//System.out.println(temp);
				labels.put(i, new JLabel(""+i));
			}
			labels.put(max, new JLabel(""+max));
		}
		else{
			for(int i=min;i<max;i+=majorTrik){
				double percent=i/100.0;
				labels.put(i, new JLabel(""+percent));
			}
			labels.put(max, new JLabel(""+(max/100.0)));
		}
		slider.setLabelTable(labels);
		slider.setPaintLabels(true);
		return this;
	}
}
