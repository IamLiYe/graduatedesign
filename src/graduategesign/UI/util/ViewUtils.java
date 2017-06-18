package graduategesign.UI.util;

import java.awt.Component;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;

public class ViewUtils {
	
	/**
	 * 
	 * @param slider ������
	 * @param min ���ֵ
	 * @param max ��Сֵ
	 * @param initValue ��ʼֵ
	 * @param majorTrik ��̵�	
	 * @param minTrik С�̵�
	 * @param type 0Ϊ������ʾ��1Ϊ�ٷֱȱ�ʾ
	 */
	public ViewUtils setScorll(JSlider slider,int min,int max,int initValue
			,int majorTrik,int minTrik,int type){
		//���������Сֵ
		slider.setMaximum(max);
		slider.setMinimum(min);
		//���ó�ֵ
		slider.setValue(initValue);
		//��ʾ�̶ȳ�
		slider.setMajorTickSpacing(majorTrik);
		slider.setMinorTickSpacing(minTrik);
		slider.setPaintTicks(true);
		//ǿ�ƶ���̶ȳ�
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
