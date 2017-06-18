package graduategesign.UI.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.sun.prism.Graphics;

import graduategesign.UI.Model.ImageModel;
import graduategesign.UI.util.GBC;
import graduategesign.test.TempTest;
import graduategesign.test.TestFlow;
import graduategesign.test.model.NoiseImageModel;

public class ImagePanel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel panel;
	
	List<ImageModel> images=new ArrayList<ImageModel>();
	
	public ImagePanel(){
		super();
		init();
	}
	
	/**
	 * ≥ı ºªØ
	 * @throws IOException 
	 */
	public void init(){
		setBackground(Color.YELLOW);
		
//		setPreferredSize(getPreferredSize());
//		JScrollPane jsp=new JScrollPane(new JTextArea());
//		add(jsp);
		panel=new JPanel(new GridBagLayout());
		panel.setBackground(Color.ORANGE);
		//panel.setPreferredSize(new Dimension(800, 600));
		/*for(int i=0;i<images.size();i++){
			panel.add(images.get(i),new GBC(0,i)
					.setAnchor(GBC.EAST)
					.setWeitht(100, 100));
		}*/
		//panel.add(new Label("1"));
		//panel.add(new Label("1"));
		//panel.add(new Label("1"));
		//panel.add(new Label("1"));
		setViewportView(panel);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setPreferredSize(getPreferredSize());
		revalidate();
	}
	
	public void paintCompont(Graphics g){
		
	}
	
	/*public static void main(){
		JFrame frame=new JFrame("hello world");
    	frame.setLayout(new FlowLayout());
    	System.out.println(frame.getLayout().getClass());
	}*/
	
	public void test() throws IOException{
		String testPath="D:\\chart\\svd\\gragu\\SVD\\Tsvd";
		BufferedImage image;
		for(int i=0;i<20;i++){
			image=ImageIO.read(new File(testPath+"\\"+i+".jpg"));
			images.add(new ImageModel("Õº"+i, image));
		}
	}
	
	public void rePaint(){
		//panel.setPreferredSize(new Dimension(800, 600));
		//int gridx=0;
		//int gridy=0;
		
		panel.removeAll();
		
		
		if(TempTest.sourceImage!=null){
			if(TempTest.noiseNumber==0){
				panel.add(TempTest.sourceImage,new GBC(0,0)
						.setAnchor(GBC.WEST)
						.setWeitht(100,100));
				panel.add(TempTest.grayImage,new GBC(1,0)
						.setAnchor(GBC.WEST)
						.setWeitht(100,100));
				//System.out.println("---------");
			}
			else{
				if(TempTest.denoiseNumber==0){
					panel.add(TempTest.sourceImage,new GBC(0,0)
							.setAnchor(GBC.WEST)
							.setWeitht(100, 100));
					panel.add(TempTest.grayImage,new GBC(1,0)
							.setAnchor(GBC.WEST)
							.setWeitht(100, 100));
					for(int i=0;i<TempTest.noiseNumber;i++){
						
						panel.add(TempTest.noises.get(i),new GBC(2,i)
								.setAnchor(GBC.WEST)
								.setWeitht(100, 100));
						//System.out.println("+++++++");
					}
				}
				else{
					panel.add(TempTest.sourceImage,new GBC(0,0)
							.setAnchor(GBC.WEST)
							.setWeitht(100, 100));
					panel.add(TempTest.grayImage,new GBC(1,0)
							.setAnchor(GBC.WEST)
							.setWeitht(100, 100));
					for(int i=0;i<TempTest.noiseNumber;i++){
						
						panel.add(TempTest.noises.get(i),new GBC(2,i)
								.setAnchor(GBC.WEST)
								.setWeitht(100, 100));
						for(int j=0;j<TempTest.denoiseNumber;j++){
							panel.add(TempTest.denoises.get(j).get(i),new GBC(j+3,i)
									.setAnchor(GBC.WEST)
									.setWeitht(100, 100));
						}
						//System.out.println("+++---");
					}
				}
			}
		}
		panel.repaint();
		validate();
		
		//panel.add(new Label("1"));
		//panel.add(new Label("1"));
		//panel.add(new Label("1"));
		//panel.add(new Label("1"));
		/*setViewportView(panel);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setPreferredSize(getPreferredSize());*/
		revalidate();
	}
}
