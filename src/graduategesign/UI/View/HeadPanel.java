package graduategesign.UI.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.hxtt.b.i;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xml.internal.security.Init;

import graduategesign.UtilDipose;
import graduategesign.UI.Model.ImageModel;
import graduategesign.UI.util.GBC;
import graduategesign.test.TempTest;

public class HeadPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String openFileStr="打开图片";  
	
	private static final String grayingImageStr="灰度化图像";
	
	private JButton openFileBut;
	private JButton grayingImageBut;

	LayoutManager layout;
	
	public JButton getOpenFileBut() {
		return openFileBut;
	}

	public JButton getGrayingImageBut() {
		return grayingImageBut;
	}

	public void setOpenFileBut(JButton openFileBut) {
		this.openFileBut = openFileBut;
	}

	public void setGrayingImageBut(JButton grayingImageBut) {
		this.grayingImageBut = grayingImageBut;
	}

	/**
	 * 构造函数
	 */
	public HeadPanel(){
		super();
		init();
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		setBackground(Color.WHITE);
		int width=getWidth();
		int height=500;
		setBounds(0, 0, width, height);;
		openFileBut=new JButton(openFileStr);
		//grayingImageBut=new JButton(grayingImageStr);
		layout=new GridBagLayout();
		setLayout(layout);
		add(openFileBut
				,new GBC(0,0)
				/*.setFill(GBC.HORIZONTAL)*/
				.setWeitht(100, 100)
				.setAnchor(GBC.NORTH)
				.setInsets(10,10,10,10)
				/*.setIpad(5, 5)*/);
		System.out.println("width:"+getWidth()+"height:"+getHeight());
//		add(grayingImageBut,
//				new GBC(0,1).setFill(GBC.HORIZONTAL).setWeitht(100, 0).setInsets(1));
		actionInit();
	}
	
	public void actionInit(){
		openFileBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser=new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter(
						"JPG&PNG Images","jpg","png"));
				chooser.showOpenDialog(null);
				File file=chooser.getSelectedFile();
				if(file!=null){
					BufferedImage src=null;
					try {
						src = ImageIO.read(file);
						TempTest.sourceImage=
								new ImageModel("原图像",src);
						TempTest.grayImage=
								new ImageModel("灰度图像"
										,UtilDipose.getGrayImage(src, null));
						MainFrame.imagePanel.rePaint();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				System.out.println("XXX");
			}
					
		});
		}
}
