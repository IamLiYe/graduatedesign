package graduategesign.UI.View;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import graduategesign.AddNoisingFilter;
import graduategesign.UI.util.GBC;
import javafx.scene.control.RadioButton;
import jdk.internal.org.objectweb.asm.Label;

import javax.swing.JCheckBox;
import javax.swing.JLabel;


public class AddNoisePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String tileLabStr="加噪模块";
	
	private static String singleRabStr="单一噪声";
	private static String mixtureRabStr="混合噪声";
	
	private static String gaussianChbStr="高斯噪声";
	private static String sigmanLabStr="sigma:";
	
	private static String poissonChbStr="泊松噪声";
	private static String lamdaLabStr="lamada:";
	
	private static String spChbStr="椒&盐噪声";
	private static String spPercentLabStr="spPercent：";

	
	
	private static String addNoiseButStr="加噪处理";
	/**
	 * 标题
	 */
	private JLabel tileLab;
	/**
	  * 
	 */
	private JPanel typePanel;
	private LayoutManager typeLayout;
	private JRadioButton singleRab=new JRadioButton(singleRabStr);
	private JRadioButton mixtureRab=new JRadioButton(mixtureRabStr);
	private boolean isSingle=true;
	private ButtonGroup group;
	/**
	 * 高斯
	 */
	JCheckBox gaussianChb;
	JLabel meanLab;
	double sigma;
	JSlider gaussianSld;
	/**
	 * 泊松
	 */
	JCheckBox poissonChb;
	JLabel lambdaLab;
	double lambda;
	JSlider poissonSld;
	/**
	 * 椒盐
	 */
	JCheckBox spChb;
	JLabel spPercentLab;
	double spPercent;
	JSlider spSld;
	/**
	 * 加噪处理
	 */
	JButton addNoiseBut;
	
	LayoutManager layout;

	public AddNoisePanel(){
		this.setBackground(Color.CYAN);
		init();
	}
	
	public void init(){
		tileLab=new JLabel(tileLabStr,SwingConstants.CENTER);
		group=new ButtonGroup();
		//singleRab=new JCheckBox(singleRabStr);
		//mixtureRab=new JCheckBox(mixtureRabStr);
		gaussianChb=new JCheckBox(gaussianChbStr);
		poissonChb=new JCheckBox(poissonChbStr);
		spChb=new JCheckBox(spChbStr);
		addNoiseBut=new JButton(addNoiseButStr);
		layout=new BorderLayout();
		this.setLayout(layout);
		//添加标题
		this.add(tileLab,BorderLayout.NORTH);
		//添加选择混合噪声还是单一噪声
		typePanel=new JPanel(new GridBagLayout());
		typePanel.setBackground(Color.WHITE);
		//typePanel.setPreferredSize(new Dimension(200, 300));
		//typeLayout=new FlowLayout();
		int gridx=0;
		int gridy=0;
		typePanel.add(singleRab,new GBC(0,gridy)
				.setAnchor(GBC.CENTER));
		singleRab.setSelected(true);
		typePanel.add(mixtureRab,new GBC(gridx+1,gridy)
				.setAnchor(GBC.CENTER));
		//singleRab.setLocation(0, 0);
		
		//添加噪声选择
		//高斯
		typePanel.add(gaussianChb,new GBC(gridx,++gridy,2,1)
				.setAnchor(GBC.WEST));
		gaussianSld=new JSlider(5,50,25);
		gaussianSld.setMajorTickSpacing(10);
		gaussianSld.setMinorTickSpacing(1);
		gaussianSld.setPaintTicks(true);
		Hashtable<Integer,Component> labels=new Hashtable<Integer,Component>();
		labels.put(5, new JLabel("5"));
		labels.put(25, new JLabel("25"));
		labels.put(50, new JLabel("50"));
		gaussianSld.setLabelTable(labels);
		gaussianSld.setPaintLabels(true);
		gaussianSld.setEnabled(false);
		typePanel.add(gaussianSld,new GBC(gridx,++gridy,2,1)
				.setAnchor(GBC.WEST));
		//泊松
		typePanel.add(poissonChb,new GBC(gridx,++gridy,2,1)
				.setAnchor(GBC.WEST));
		poissonSld=new JSlider(5,75,25);
		typePanel.add(poissonSld,new GBC(gridx,++gridy,2,1)
				.setAnchor(GBC.WEST));
		//椒盐
		typePanel.add(spChb,new GBC(gridx,++gridy,2,1)
				.setAnchor(GBC.WEST));
		spSld=new JSlider(0, 100,25);
		typePanel.add(spSld,new GBC(gridx,++gridy,2,1)
				.setAnchor(GBC.WEST));
		//处理按钮
		typePanel.add(addNoiseBut,new GBC(gridx+1,++gridy)
				.setInsets(5));
		add(typePanel);
		actionInit();
	}
	
	private class selectActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JCheckBox box=(JCheckBox)e.getSource();
			if(isSingle){
				if(box.isSelected()){
					if(box==gaussianChb){
						poissonChb.setSelected(false);
						spChb.setSelected(false);
					}else if(box==poissonChb){
						gaussianChb.setSelected(false);
						spChb.setSelected(false);
					}else{
						gaussianChb.setSelected(false);
						poissonChb.setSelected(false);	
					}
				}
			}
		}	
		
	}
	
	private class typeActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JRadioButton box=(JRadioButton)e.getSource();
			if(box==singleRab){
				if(box.isSelected()){
					isSingle=true;
					mixtureRab.setSelected(false);
					gaussianChb.setSelected(false);
					poissonChb.setSelected(false);
					spChb.setSelected(false);
				}
				else{
					isSingle=false;
					mixtureRab.setSelected(true);
					gaussianChb.setSelected(false);
					poissonChb.setSelected(false);
					spChb.setSelected(false);
				}
			}else{
				if(box.isSelected()){
					singleRab.setSelected(false);
					isSingle=false;
					gaussianChb.setSelected(false);
					poissonChb.setSelected(false);
					spChb.setSelected(false);
				}
				else{
					isSingle=true;
					singleRab.setSelected(true);
					gaussianChb.setSelected(false);
					poissonChb.setSelected(false);
					spChb.setSelected(false);
				}
			}
		}
		
	}
	
	
	private class typeChangeListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			JRadioButton box=(JRadioButton)e.getSource();
			if(box==singleRab){
				if(box.isSelected()){
					isSingle=true;
					mixtureRab.setSelected(false);
					gaussianChb.setSelected(false);
					poissonChb.setSelected(false);
					spChb.setSelected(false);
				}
				else{
					isSingle=false;
					mixtureRab.setSelected(true);
					gaussianChb.setSelected(false);
					poissonChb.setSelected(false);
					spChb.setSelected(false);
				}
			}else{
				if(box.isSelected()){
					singleRab.setSelected(false);
					isSingle=false;
					gaussianChb.setSelected(false);
					poissonChb.setSelected(false);
					spChb.setSelected(false);
				}
				else{
					isSingle=true;
					singleRab.setSelected(true);
					gaussianChb.setSelected(false);
					poissonChb.setSelected(false);
					spChb.setSelected(false);
				}
			}
		}
		
	}
	private void actionInit(){
		ActionListener select=new selectActionListener();
		ActionListener type=new typeActionListener();
		singleRab.addActionListener(type);
		mixtureRab.addActionListener(type);
		gaussianChb.addActionListener(select);
		poissonChb.addActionListener(select);
		spChb.addActionListener(select);
	}
	
	private void stateInit(){
		isSingle=true;
		singleRab.setSelected(true);
		mixtureRab.setSelected(false);
		gaussianChb.setSelected(false);
		poissonChb.setSelected(false);
		spChb.setSelected(false);
		sigma=AddNoisingFilter.NOISE_FACTOR;
		lambda=AddNoisingFilter.NOISE_FACTOR;
		spPercent=AddNoisingFilter.DEFALUT_SP_PERCENT;
	}
}
