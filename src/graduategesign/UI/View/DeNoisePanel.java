package graduategesign.UI.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import graduategesign.MeanFilter;
import graduategesign.SVDFilter;
import graduategesign.StatisticalFilter;
import graduategesign.TSVDFilter;
import graduategesign.UI.Model.ImageModel;
import graduategesign.UI.util.GBC;
import graduategesign.test.TempTest;

/**
 * 加噪面板
 * @author Administrator
 *
 */
public class DeNoisePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String tileLabStr="去噪模块";
	private static String allChbStr="全选";
	
	private static String statisticalChbStr="统计滤波:";
	private static String statLabStr="统计\n滤波:";
	private static String statCenterStr="中间点";
	private static String statMinMaxStr="最大最小值";
	private static String statHalfStr="中值";
	
	private static String meanChbStr="均值滤波:";
	private static String meanLabStr="均值滤波:";
	private static String meanARITHMETICStr="算术均值";
	private static String meanGEOMETRICStr="几何均值";
	private static String meanHARMONICStr="调和均值";
	
	private static String svdLabStr="SVD:";
	private static String svdChbStr="传统SVD";
	private static String tsvdChbStr="张量分块SVD";
	private static String deNoiseButStr="去噪处理";
	
	JLabel titleLab;
	JCheckBox allChb;
	//统计
	JLabel statLab;
	JCheckBox statisticalChb;
	JCheckBox statHalfChb;
	JCheckBox statMinMaxChb;
	JCheckBox statCenterChb;
	//均值
	JLabel meanLab;
	JCheckBox meanChb;
	JCheckBox meanGeoChb;
	JCheckBox meanHarChb;
	JCheckBox meanAriChb;
	//SVD
	JLabel svdLab;
	JCheckBox svdChb;
	JCheckBox tsvdChb;
	
	JButton deNoiseBut;
	LayoutManager layout;
	
	//
	private String message;

	//select action listener
	selectActionListener sListener;
	public DeNoisePanel(){
		super();
		init();
	}
	public void init(){
		setBackground(Color.GREEN);
		titleLab=new JLabel(tileLabStr,SwingConstants.CENTER);
		allChb=new JCheckBox(allChbStr);
		//
		statLab=new JLabel(statLabStr);
		statisticalChb=new JCheckBox(statisticalChbStr);
		statCenterChb=new JCheckBox(statCenterStr);
		statHalfChb=new JCheckBox(statHalfStr);
		statMinMaxChb=new JCheckBox(statMinMaxStr);
		//
		meanLab=new JLabel(meanLabStr);
		meanChb=new JCheckBox(meanChbStr);
		meanAriChb=new JCheckBox(meanARITHMETICStr);
		meanGeoChb=new JCheckBox(meanGEOMETRICStr);
		meanHarChb=new JCheckBox(meanHARMONICStr);
	    //
		svdLab=new JLabel(svdLabStr);
		svdChb=new JCheckBox(svdChbStr);
		tsvdChb=new JCheckBox(tsvdChbStr);
		deNoiseBut=new JButton(deNoiseButStr);
		
		/*layout=new GridLayout(7, 1);*/
		layout=new BorderLayout();
		setLayout(layout);
		add(titleLab,BorderLayout.NORTH);
		JPanel panel=new JPanel(new GridBagLayout());
		
		int gridx=0;
		int gridy=0;
		panel.add(allChb,new GBC(gridx+1,gridy)
				.setAnchor(GBC.WEST)
				/*.setFill(GBC.HORIZONTAL)*/
				.setWeitht(100, 100));
		
		
		
		//stat
		panel.add(statLab,new GBC(gridx,++gridy,1,3)
				.setAnchor(GBC.CENTER)
				.setWeitht(100, 100)
				.setFill(GBC.VERTICAL));
		panel.add(statHalfChb,new GBC(gridx+1,gridy)
				.setAnchor(GBC.WEST)
				/*.setFill(GBC.HORIZONTAL)*/
				.setWeitht(100, 100));
		panel.add(statMinMaxChb,new GBC(gridx+1,++gridy)
				.setAnchor(GBC.WEST)
				/*.setFill(GBC.HORIZONTAL)*/
				.setWeitht(100, 100));
		panel.add(statCenterChb,new GBC(gridx+1,++gridy)
				.setAnchor(GBC.WEST)
				/*.setFill(GBC.HORIZONTAL)*/
				.setWeitht(100, 100));
		
		//mean
		panel.add(meanLab,new GBC(gridx,++gridy,1,3)
				.setAnchor(GBC.CENTER)
				.setFill(GBC.VERTICAL)
				.setWeitht(100, 100));
		panel.add(meanAriChb,new GBC(gridx+1,gridy)
				.setAnchor(GBC.WEST)
				/*.setFill(GBC.HORIZONTAL)*/
				.setWeitht(100, 100));
		panel.add(meanGeoChb,new GBC(gridx+1,++gridy)
				.setAnchor(GBC.WEST)
				/*.setFill(GBC.HORIZONTAL)*/
				.setWeitht(100, 100));
		panel.add(meanHarChb,new GBC(gridx+1,++gridy)
				.setAnchor(GBC.WEST)
				/*.setFill(GBC.HORIZONTAL)*/
				.setWeitht(100, 100));
		
		//svd
		panel.add(svdLab,new GBC(gridx,++gridy,1,2)
				.setAnchor(GBC.CENTER)
				/*.setFill(GBC.VERTICAL)*/
				.setWeitht(100, 100));
		panel.add(svdChb,new GBC(gridx+1,gridy)
				.setAnchor(GBC.WEST)
				/*.setFill(GBC.HORIZONTAL)*/
				.setWeitht(100, 100));
		panel.add(tsvdChb,new GBC(gridx+1,++gridy)
				.setAnchor(GBC.WEST)
				/*.setFill(GBC.HORIZONTAL)*/
				.setWeitht(100, 100));
		
		panel.add(deNoiseBut,new GBC(gridx+1,++gridy,2,1)
				.setInsets(5)
				.setFill(GBC.HORIZONTAL)
				.setWeitht(100, 100));
		panel.setBackground(Color.WHITE);
		add(panel,BorderLayout.CENTER);
		actionInit();
		deNoiseInit();
	}
	
	public void actionInit(){
		allChb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reset(allChb.isSelected());
			}
		});
		
		sListener=new selectActionListener();

		statCenterChb.addActionListener(sListener);
		statMinMaxChb.addActionListener(sListener);
		statHalfChb.addActionListener(sListener);

		meanAriChb.addActionListener(sListener);
		meanGeoChb.addActionListener(sListener);
		meanHarChb.addActionListener(sListener);
		
		svdChb.addActionListener(sListener);
		tsvdChb.addActionListener(sListener);
	}
	
	private int chekedNumber=0;
	private class selectActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JCheckBox checkBox=(JCheckBox)e.getSource();
			if(!checkBox.isSelected()){
				chekedNumber--;
				allChb.setSelected(false);
			}
			else{
				chekedNumber++;
				if(chekedNumber>=8){
					allChb.setSelected(true);
				}
			}
		}
		
	}
	
	private void reset(boolean b){
		if(b){
			chekedNumber=8;
		}
		else{
			chekedNumber=0;
		}
		
		allChb.setSelected(b);
		statCenterChb.setSelected(b);
		statMinMaxChb.setSelected(b);
		statHalfChb.setSelected(b);

		meanAriChb.setSelected(b);
		meanGeoChb.setSelected(b);
		meanHarChb.setSelected(b);
		
		svdChb.setSelected(b);
		tsvdChb.setSelected(b);
	}
	
	public String getMessage(){
		return "";
	}
	
	public void deNoiseInit(){
		deNoiseBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int type=0;
				
				type+=(statHalfChb.isSelected()?1:0);
				type+=(statMinMaxChb.isSelected()?2:0);
				type+=(statCenterChb.isSelected()?4:0);

				type+=(meanAriChb.isSelected()?8:0);
				type+=(meanGeoChb.isSelected()?16:0);
				type+=(meanHarChb.isSelected()?32:0);
				
				type+=(svdChb.isSelected()?64:0);
				type+=(tsvdChb.isSelected()?128:0);
				
				StatisticalFilter statFilter=new StatisticalFilter();
				MeanFilter meanFilter=new MeanFilter();
				SVDFilter svdFilter=new SVDFilter();
				TSVDFilter tsvdFilter=new TSVDFilter();
				
				if(TempTest.noises.size()==0){
					return;
				}
				
				if(type<1)
					return;
				//清理数据
				for(int i=0;i<TempTest.denoiseNumber;i++){
					TempTest.denoises.get(i).clear();
				}
				TempTest.denoises.clear();
				
				int count=0;
				BufferedImage srcImage;
				if((type&1)>0){
					TempTest.denoises.add(new ArrayList<ImageModel>());
					statFilter.setType(StatisticalFilter.STATISTICAL_TYPE_HALF);
					for(int i=0;i<TempTest.noiseNumber;i++){
						String str="中值-"+(i+1)+"-"+(count+1)+"[1]"
								+"{"+statFilter.getKernel_size()
								+","+statFilter.getWorkNumber()+"}";
						srcImage=TempTest.noises.get(i).getImage();
						TempTest.denoises.get(count).add(
								new ImageModel(str
										,statFilter.filter(srcImage, null)));
					}
					count++;
				}
				
				if((type&2)>0){
					TempTest.denoises.add(new ArrayList<ImageModel>());
					statFilter.setType(StatisticalFilter.STATISTICAL_TYPE_MIN_MAX);
					for(int i=0;i<TempTest.noiseNumber;i++){
						String str="最大最小值-"+(i+1)+"-"+(count+1)+"[2]"
								+"{"+statFilter.getKernel_size()
								+","+statFilter.getWorkNumber()+"}";
						srcImage=TempTest.noises.get(i).getImage();
						TempTest.denoises.get(count).add(
								new ImageModel(str
										,statFilter.filter(srcImage, null)));
					}
					count++;
				}
				
				if((type&4)>0){
					TempTest.denoises.add(new ArrayList<ImageModel>());
					statFilter.setType(StatisticalFilter.STATISTICAL_TYPE_CENTER);
					for(int i=0;i<TempTest.noiseNumber;i++){
						String str="中间点-"+(i+1)+"-"+(count+1)+"[4]"
								+"{"+statFilter.getKernel_size()
								+","+statFilter.getWorkNumber()+"}";
						srcImage=TempTest.noises.get(i).getImage();
						TempTest.denoises.get(count).add(
								new ImageModel(str
										,statFilter.filter(srcImage, null)));
					}
					count++;
				}
				
				
				if((type&8)>0){
					TempTest.denoises.add(new ArrayList<ImageModel>());
					meanFilter.setMeanType(MeanFilter.MEAN_TYPE_ARITHMETIC);
					for(int i=0;i<TempTest.noiseNumber;i++){
						String str="算术均值-"+(i+1)+"-"+(count+1)+"[8]"
								+"{"+meanFilter.getKernelSize()
								+","+meanFilter.getWorkNumber()+"}";;
						srcImage=TempTest.noises.get(i).getImage();
						TempTest.denoises.get(count).add(
								new ImageModel(str
										,meanFilter.filter(srcImage, null)));
					}
					count++;
				}
				
				if((type&16)>0){
					TempTest.denoises.add(new ArrayList<ImageModel>());
					meanFilter.setMeanType(MeanFilter.MEAN_TYPE_GEOMETRIC);
					for(int i=0;i<TempTest.noiseNumber;i++){
						String str="几何均值-"+(i+1)+"-"+(count+1)+"[16]"
								+"{"+meanFilter.getKernelSize()
								+","+meanFilter.getWorkNumber()+"}";;
						srcImage=TempTest.noises.get(i).getImage();
						TempTest.denoises.get(count).add(
								new ImageModel(str
										,meanFilter.filter(srcImage, null)));
					}
					count++;
				}
				if((type&32)>0){
					TempTest.denoises.add(new ArrayList<ImageModel>());
					meanFilter.setMeanType(MeanFilter.MEAN_TYPE_HARMONIC);
					for(int i=0;i<TempTest.noiseNumber;i++){
						String str="调和均值-"+(i+1)+"-"+(count+1)+"[32]"
								+"{"+meanFilter.getKernelSize()
								+","+meanFilter.getWorkNumber()+"}";
						srcImage=TempTest.noises.get(i).getImage();
						TempTest.denoises.get(count).add(
								new ImageModel(str
										,meanFilter.filter(srcImage, null)));
					}
					count++;
				}
				
				if((type&64)>0){
					TempTest.denoises.add(new ArrayList<ImageModel>());
					for(int i=0;i<TempTest.noiseNumber;i++){
						String str="SVD-"+(i+1)+"-"+(count+1)+"[64]"
					+"{"+svdFilter.getSvdType()+"}";
						srcImage=TempTest.noises.get(i).getImage();
						TempTest.denoises.get(count).add(
								new ImageModel(str
										,svdFilter.filter(srcImage, null)));
					}
					count++;
				}
				
				if((type&128)>0){
					TempTest.denoises.add(new ArrayList<ImageModel>());
					List<BufferedImage> temps=new ArrayList<BufferedImage>();
					for(int i=0;i<TempTest.noiseNumber;i++){
						temps.add(TempTest.noises.get(i).getImage());
					}
					temps=tsvdFilter.toMode1(temps, null);
					for(int i=0;i<TempTest.noiseNumber;i++){
						String str="TSVD-"+(i+1)+"-"+(count+1)+"[128]"+"{"
								+tsvdFilter.getSvdType()+","
								+TempTest.noiseNumber+","+TSVDFilter.TENSOR_TYPE_MODE1+","
								+tsvdFilter.getSkip()
								+"}";
						srcImage=TempTest.noises.get(i).getImage();
						TempTest.denoises.get(count).add(
								new ImageModel(str
										,temps.get(i)));
					}
					count++;
				}
				TempTest.deNoiseType=type;
				TempTest.denoiseNumber=count;
				System.out.println("type:"+type+"count:"+count);
				MainFrame.imagePanel.rePaint();
				
				//输出结果
				TempTest.setPSNRandEpi();
			}
		});
	}
}
