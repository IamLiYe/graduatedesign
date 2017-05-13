package graduategesign;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainUI extends JFrame
				implements ActionListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String IMAGE_CMD = "ѡ��ͼ��...";
	public static final String PROCESS_CMD = "����";
	
	private JButton imgBtn;
	private JButton processBtn;
	private ImagePanel imagePanel;
	
	// image
	private BufferedImage srcImage;
	
	public MainUI()
	{
		/*
		 * �����������
		 */
		//int[] array=UtilDipose.randomSort(10, 10, null, null);
		/*
		for(int i:array){
			System.out.println(i);
		}*/
		setTitle("JFrame UI - ��ʾ");
		imgBtn = new JButton(IMAGE_CMD);
		processBtn = new JButton(PROCESS_CMD);
		
		// buttons
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.add(imgBtn);
		btnPanel.add(processBtn);
		
		// filters
		imagePanel = new ImagePanel();
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(imagePanel, BorderLayout.CENTER);
		getContentPane().add(btnPanel, BorderLayout.SOUTH);
		
		// setup listener
		setupActionListener();
		
	}

	private void setupActionListener() {
		imgBtn.addActionListener(this);
		processBtn.addActionListener(this);
	}
//

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isEventDispatchThread())
		{
			System.out.println("Event Dispath Thread!!");
		}
		
		if(srcImage == null)
		{
			JOptionPane.showMessageDialog(this, "����ѡ��ͼ��Դ�ļ�");
			try {
				JFileChooser chooser = new JFileChooser();
				setFileTypeFilter(chooser);
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				if(f != null)
				{
					srcImage = ImageIO.read(f);
					imagePanel.setSourceImage(srcImage);
					imagePanel.addImage(srcImage);
					imagePanel.repaint();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
		if(IMAGE_CMD.equals(e.getActionCommand()))
		{
			try {
				JFileChooser chooser = new JFileChooser();
				setFileTypeFilter(chooser);
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				if(f != null)
				{
					srcImage = ImageIO.read(f);
					imagePanel.setSourceImage(srcImage);
					imagePanel.addImage(srcImage);
					imagePanel.repaint();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			imagePanel.repaint();
		}
		else if(PROCESS_CMD.equals(e.getActionCommand()))
		{
			imagePanel.process();
			imagePanel.repaint();
		}
	}
	
	public void setFileTypeFilter(JFileChooser chooser)
	{
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & PNG Images", "jpg", "png");
		    chooser.setFileFilter(filter);
	}
	
	public void openView()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		MainUI ui = new MainUI();
		ui.openView();
		//testPSNR();
	}

	/**
	 * test PSNR�Ƿ���ȷ
	 */
	public static void testPSNR(){
		int []a={5,10,11,12,
				20,31,40,51,
				27,10,17,45,
				37,85,13,34};
		int []b={5,11,10,13,
				20,31,41,51,
				27,10,17,44,
				37,85,14,35};
		AddNoisingFilter filter=new AddNoisingFilter();
		filter.setSaltPercent(0.25);
		filter.setPepperPercent(0.25);
		//filter.addSPNoise(a, b,new Random());
		filter.setPepperPercent(0.25);
		filter.setNoiseType(AddNoisingFilter.NOISE_TYPE_PEPPER
				|AddNoisingFilter.NOISE_TYPE_POISSON
				|AddNoisingFilter.NOISE_TYPE_GAUSSION);
		filter.addNoise(a, b, new Random());
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				System.out.printf("%3d ",a[i*4+j]);
			}
			System.out.println();
		}
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				System.out.printf("%3d ",b[i*4+j]);
			}
			System.out.println();
		}
		//System.out.println("testPSNR"+UtilDipose.getPSNR(a,b));
		//System.out.println("testPSNR"+UtilDipose.getSNRtoPicturesSimilarity(a, b));
		}
}
