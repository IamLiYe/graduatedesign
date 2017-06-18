package graduategesign.UI.View;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.image.BufferedImage;
import java.awt.Component;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import graduategesign.AddNoisingFilter;
import graduategesign.UtilDipose;
import graduategesign.UI.Model.ImageModel;
import graduategesign.UI.util.GBC;
import graduategesign.UI.util.ViewUtils;
import graduategesign.test.TempTest;
import graduategesign.test.TestFlow;
import javafx.scene.control.RadioButton;

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

	private static String numberLabStr="样本数量";
	
	private static String addNoiseButStr="加噪处理";
	
	/**
	 * slider listener
	 */
	private ChangeListener sliderListener;
	
	/**
	 * text listener
	 */
	private DocumentListener _textListener;
	private FocusListener textListener;
	private ActionListener textEnterListner;
	
	/**
	 * RadioButton listener
	 */
	private ChangeListener rdbListener;
	
	/**
	 * 标题
	 */
	JLabel tileLab;
	/**
	  * 
	 */
	JPanel typePanel;
	LayoutManager typeLayout;
	JRadioButton singleRab=new JRadioButton(singleRabStr);
	JRadioButton mixtureRab=new JRadioButton(mixtureRabStr);
	private boolean isSingle=true;
	ButtonGroup group;
	/**
	 * 高斯
	 */
	JCheckBox gaussianChb;
	JLabel meanLab;
	JTextField siamaTfd;
	double sigma;
	JSlider gaussianSld;
	/**
	 * 泊松
	 */
	JCheckBox poissonChb;
	JLabel lambdaLab;
	JTextField ladmdaTfd;
	double lambda;
	JSlider poissonSld;
	/**
	 * 椒盐
	 */
	JCheckBox spChb;
	JLabel spPercentLab;
	JTextField spTfd;
	double spPercent;
	JSlider spSld;
	
	/**
	 * 样本shul
	 */
	JLabel numberLab;
	int number;
	JTextField numberTfd;
	JSlider numberSld;
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
		//typePanel.setBackground(Color.WHITE);
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
		typePanel.add(gaussianChb,new GBC(gridx,++gridy,1,1)
				.setAnchor(GBC.WEST));
		siamaTfd=new JTextField();
		typePanel.add(siamaTfd,new GBC(gridx+1,gridy,1,1)
				.setAnchor(GBC.CENTER)
				.setFill(GBC.HORIZONTAL)
				.setWeitht(100, 100));
		siamaTfd.setText("25");
		gaussianSld=new JSlider();
		new ViewUtils().setScorll(gaussianSld,0,100,25,
				20,1,0);
		typePanel.add(gaussianSld,new GBC(gridx,++gridy,2,1)
				.setAnchor(GBC.WEST)
				.setFill(GBC.HORIZONTAL)
				.setWeitht(100, 100));
		//泊松
		typePanel.add(poissonChb,new GBC(gridx,++gridy,1,1)
				.setAnchor(GBC.WEST));
		
		ladmdaTfd=new JTextField();
		typePanel.add(ladmdaTfd,new GBC(gridx+1,gridy,1,1)
				.setAnchor(GBC.CENTER)
				.setFill(GBC.HORIZONTAL)
				.setWeitht(100, 100));
		ladmdaTfd.setText("25");
		
		poissonSld=new JSlider();
		new ViewUtils().setScorll(poissonSld,0,100,25,
				20,1,0);
		
		typePanel.add(poissonSld,new GBC(gridx,++gridy,2,1)
				.setAnchor(GBC.WEST)
				.setFill(GBC.HORIZONTAL)
				.setWeitht(100, 100));
		
		//椒盐
		typePanel.add(spChb,new GBC(gridx,++gridy,1,1)
				.setAnchor(GBC.WEST));
		
		spTfd=new JTextField();
		typePanel.add(spTfd,new GBC(gridx+1,gridy,1,1)
				.setAnchor(GBC.CENTER)
				.setFill(GBC.HORIZONTAL)
				.setWeitht(100, 100));	
		spTfd.setText("0.1");
		
		spSld=new JSlider();
		new ViewUtils().setScorll(spSld,0,100,10,
				20,1,1);
		typePanel.add(spSld,new GBC(gridx,++gridy,2,1)
				.setAnchor(GBC.WEST)
				.setFill(GBC.HORIZONTAL)
				.setWeitht(100, 100));
		
		//样本
		numberLab=new JLabel(numberLabStr);
		typePanel.add(numberLab,new GBC(gridx,++gridy,1,1)
				.setAnchor(GBC.CENTER));
				
		numberTfd=new JTextField();
		typePanel.add(numberTfd,new GBC(gridx+1,gridy,1,1)
				.setAnchor(GBC.CENTER)
				.setFill(GBC.HORIZONTAL)
				.setWeitht(100, 100));	
		numberTfd.setText("64");
				
		numberSld=new JSlider();
		new ViewUtils().setScorll(numberSld,0,256,64,
				64,4,0);
		typePanel.add(numberSld,new GBC(gridx,++gridy,2,1)
				.setAnchor(GBC.WEST)
				.setFill(GBC.HORIZONTAL)
				.setWeitht(100, 100));
				
		//处理按钮
		typePanel.add(addNoiseBut,new GBC(gridx+1,++gridy)
				.setInsets(5)
				.setFill(GBC.HORIZONTAL)
				.setWeitht(100, 100));
		add(typePanel);
		actionInit();
		taskInit();
		reset();
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
		
		//RadioButton 事件
		gaussianChb.addActionListener(select);
		poissonChb.addActionListener(select);
		spChb.addActionListener(select);
		rdbListener=new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JCheckBox source=(JCheckBox)e.getSource();
				if(source==gaussianChb){
					if(source.isSelected()){
						gaussianSld.setEnabled(true);
						siamaTfd.setEnabled(true);
					}
					else{
						gaussianSld.setEnabled(false);
						siamaTfd.setEnabled(false);
					}
				}else if(source==poissonChb){
					if(source.isSelected()){
						poissonSld.setEnabled(true);
						ladmdaTfd.setEnabled(true);
					}
					else{
						poissonSld.setEnabled(false);
						ladmdaTfd.setEnabled(false);
					}
				}else{
					if(source.isSelected()){
						spSld.setEnabled(true);
						spTfd.setEnabled(true);
					}
					else{
						spSld.setEnabled(false);
						spTfd.setEnabled(false);
					}
				}
			}
		};
		gaussianChb.addChangeListener(rdbListener);
		poissonChb.addChangeListener(rdbListener);
		spChb.addChangeListener(rdbListener);
		//
		
		//slider 事件
		sliderListener=new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source=(JSlider)e.getSource();
				if(source==gaussianSld){
					siamaTfd.setText(gaussianSld.getValue()+"");
				}else if(source==poissonSld){
					ladmdaTfd.setText(poissonSld.getValue()+"");
				}else if(source==spSld){
					spTfd.setText((spSld.getValue()/100.0)+"");
				}else{
					numberTfd.setText(numberSld.getValue()+"");
				}
			}
		};
		gaussianSld.addChangeListener(sliderListener);
		poissonSld.addChangeListener(sliderListener);
		spSld.addChangeListener(sliderListener);
		numberSld.addChangeListener(sliderListener);
		
		//text 事件
		//addTextListener(numberTfd);
		textListener=new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				JTextField source=(JTextField)e.getSource();
				valiteValue(source);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
			}
		};
		
		textEnterListner=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("ENTER");
				JTextField source=(JTextField)e.getSource();
				valiteValue(source);
			}
		};
		/*TextListener textListener=new TextListener() {
			
			@Override
			public void textValueChanged(TextEvent e) {
				// TODO Auto-generated method stub
				TextField source=(TextField)e.getSource();
				if(source==numberTfd){
					int i=toInt(numberTfd.getText(), 0, 256, 64);
					numberSld.setValue(i);
					//source.setText(i+"");
				}
			}
		};*/
		siamaTfd.addFocusListener(textListener);
		siamaTfd.addActionListener(textEnterListner);
		ladmdaTfd.addFocusListener(textListener);
		ladmdaTfd.addActionListener(textEnterListner);
		spTfd.addFocusListener(textListener);
		spTfd.addActionListener(textEnterListner);
		numberTfd.addFocusListener(textListener);
		numberTfd.addActionListener(textEnterListner);
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
	
	/**
	 * 重置
	 */
	private void reset(){
		
		gaussianChb.setSelected(false);
		gaussianSld.setEnabled(false);
		gaussianSld.setValue((int)AddNoisingFilter.NOISE_FACTOR);
		siamaTfd.setEnabled(false);
		
		poissonChb.setSelected(false);
		poissonSld.setEnabled(false);
		poissonSld.setValue((int)AddNoisingFilter.NOISE_FACTOR);
		ladmdaTfd.setEnabled(false);
		
		spChb.setSelected(false);
		spSld.setEnabled(false);
		spSld.setValue((int)(AddNoisingFilter.DEFALUT_SP_PERCENT*100));
		spTfd.setEnabled(false);
	}
	
	private void valiteValue(JTextField source){

		if(source==siamaTfd){
			int i=toInt(source.getText(), 0, 100,(int)AddNoisingFilter.NOISE_FACTOR);
			siamaTfd.setText(i+"");
			gaussianSld.setValue(i);
		}else if(source==ladmdaTfd){
			int i=toInt(ladmdaTfd.getText(), 0, 100,(int)AddNoisingFilter.NOISE_FACTOR);
			poissonSld.setValue(i);
			ladmdaTfd.setText(i+"");
		}else if(source==spTfd){
			double i=toDouble(spTfd.getText(), 0.0, 1.0, 0.1);
			spSld.setValue((int)(i*100));
			spTfd.setText(i+"");
		}else{
			int i=toInt(numberTfd.getText(), 0, 256, 64);
			numberSld.setValue(i);
			numberTfd.setText(i+"");
		}
	}
	private int toInt(String text,int min,int max,int initValue){
		int i;
		try{
			i=Integer.parseInt(text);
			//System.out.println(i);
			if(i<min||i>max){
				i=initValue;
			}
		}catch (Exception e) {
			// TODO: handle exception
			i=initValue;
		}
		//System.out.println(i);
		return i;
	}
	
	private double toDouble(String text,double min,double max,double initValue){
		double i;
		try{
			i=Double.parseDouble(text);
			//System.out.println(i);
			if(i<min||i>max){
				i=initValue;
			}
		}catch (Exception e) {
			// TODO: handle exception
			i=initValue;
		}
		//System.out.println(i);
		return i;
	}
	
	private void addTextListener(JTextField textField){
		textField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				Document doc=e.getDocument();
				
					new Thread(new Runnable(){
				      
						@Override
						public void run() {
							String str="";
							try {
								str = doc.getText(0, doc.getLength());
							} catch (BadLocationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// TODO Auto-generated method stub
							StringBuffer buffer=new StringBuffer("");
							int count=0;
							for(int i=0;i<str.length();i++){
								char ch=str.charAt(i);
								if(ch<='9'&&ch>='0'){
									buffer.append(ch);
								}
								if(count<1&&ch=='.'){
									buffer.append(ch);
									count++;
								}
							}
							//System.out.println(buffer.toString());
							String temp;
							if(buffer.length()==0)
								temp="0";
							else
								temp=buffer.toString();
							textField.setText(temp);
							//System.out.println(str);
							//System.out.println(buffer.toString());
						}
						
					}).start();
					//textField.setText(str);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				Document doc=e.getDocument();
				//System.out.println("update");
			}
		});
	}
	
	private String message;
	
	
	public String getMessage() {
		return message;
	}

	private void taskInit(){
		
		AddNoisingFilter fileter=new AddNoisingFilter();
		addNoiseBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(TempTest.sourceImage==null){
					return;
				}
				int type=0;
				type+=(spChb.isSelected()?1:0);
				type+=(gaussianChb.isSelected()?2:0);
				type+=(poissonChb.isSelected()?4:0);
				
				if(type==0||numberSld.getValue()==0){
					return;
				}
				
				//清理噪声数据和去噪数据
				TempTest.noises.clear();
				TempTest.denoiseNumber=0;
				//TempTest.noiseNumber=0;
				//TempTest.noiseType=0;
				
				/*if(TempTest.denoises.size()>0)
				{
					for(int i=0;i<TempTest.denoiseNumber;i++){
						TempTest.denoises.get(i).clear();
					}
					TempTest.denoises.clear();
				}*/
				//MainFrame.imagePanel.rePaint();
				
				TempTest.noiseNumber=numberSld.getValue();
				TempTest.noiseType=type;

				double noiseParam;
				if(isSingle){
					if(type==1){
						fileter.setNoiseType(AddNoisingFilter.NOISE_TYPE_SALTANDPEPPER);
						noiseParam=spSld.getValue()/100.0;
						fileter.setSalt_pepperPercent(noiseParam);
					}else if(type==2){
						fileter.setNoiseType(AddNoisingFilter.NOISE_TYPE_GAUSSION);
						noiseParam=gaussianSld.getValue();
						fileter.setNosizeFactor((int)noiseParam);
					}else{
						fileter.setNoiseType(AddNoisingFilter.NOISE_TYPE_POISSON);
						noiseParam=poissonSld.getValue();
						fileter.setNosizeFactor((int)noiseParam);
					}
					for(int i=0;i<TempTest.noiseNumber;i++){
						String str="噪声图-"+(i+1)+"["+type+"]"+"{"+noiseParam+"}";
						TempTest.noises.add(
								new ImageModel(str
										,fileter.filter(TempTest.grayImage.getImage(), null)));
						//System.out.println(str);
					}
				}
				else{
					BufferedImage gray=TempTest.grayImage.getImage();
					BufferedImage temp;
					
					for(int i=0;i<TempTest.noiseNumber;i++){
						temp=UtilDipose.getGrayImage(gray, null);
						String str="噪声图-"+(i+1)+"["+type+"]";
					    if((type&1)>0){
					    	fileter.setNoiseType(AddNoisingFilter.NOISE_TYPE_SALTANDPEPPER);
							noiseParam=spSld.getValue()/100.0;
							fileter.setSalt_pepperPercent(noiseParam);
							temp=fileter.filter(temp, null);
							str+="{"+noiseParam+"}";
					    }
					    if((type&2)>0){
					    	fileter.setNoiseType(AddNoisingFilter.NOISE_TYPE_GAUSSION);
							noiseParam=gaussianSld.getValue();
							fileter.setNosizeFactor((int)noiseParam);
							temp=fileter.filter(temp, null);
							str+="{"+noiseParam+"}";
					    }
					    if((type&4)>0){
					    	fileter.setNoiseType(AddNoisingFilter.NOISE_TYPE_POISSON);
							noiseParam=poissonSld.getValue();
							fileter.setNosizeFactor((int)noiseParam);
							temp=fileter.filter(temp, null);
							str+="{"+noiseParam+"}";
					    }
					    TempTest.noises.add(new ImageModel(str,temp));
						//System.out.println(str);
					}
				}
				
				MainFrame.imagePanel.rePaint();
				//重置
				reset();
			}
		});
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String clikAddNoise(){
		
		return "";
	}
}
