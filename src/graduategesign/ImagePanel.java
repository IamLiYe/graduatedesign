package graduategesign;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.mysql.jdbc.Util;


/**
 * @author Administrator
 *
 */
public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage sourceImage;
	private BufferedImage destImage;
	
	private ArrayList<BufferedImage> imageArray=new ArrayList<BufferedImage>();
	
	private final static int mapInitailCapacity=100;
	
	private Map<String, BufferedImage>imageMap=new HashMap<String,BufferedImage>(mapInitailCapacity); 

	/*
	 * ͼƬ���(��λpx)
	 */
	private final static int IMAGE_ROW_PITCH=10;//�м��
	private final static int IMAGE_COLUMN_PITCH=10;//�м��
	/*
	 * ÿ��ͼƬ������
	 */
	private final static int IMAGE_ROW_NUMBER=6;
	
	public ImagePanel() {
		super();
	}

	public ImagePanel(BufferedImage sourceImage, BufferedImage destImage) {
		super();
		this.sourceImage = sourceImage;
		this.destImage = destImage;
		imageArray.add(sourceImage);
		imageArray.add(destImage);
	}



	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d=(Graphics2D)g;
		g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
		int imageWidth=0;
		int imageHeight=0;
		if(imageArray.size()>0){
			imageWidth=imageArray.get(0).getWidth();
			imageHeight=imageArray.get(0).getHeight();
			for(int i=0;i<imageArray.size();i++){
				//ÿ������չʾͼ������֮�����Ϊ10px;
				int x=(i%IMAGE_ROW_NUMBER)*(imageWidth+IMAGE_ROW_PITCH)+IMAGE_ROW_PITCH;
				int y=(i/IMAGE_ROW_NUMBER)*(imageHeight+IMAGE_COLUMN_PITCH)+IMAGE_COLUMN_PITCH;
				g2d.drawImage(imageArray.get(i),x,y,imageWidth,imageHeight,null);
			}
		}
	}

	//����
	public void process() {
		this.imageArray.clear();
		this.imageArray.add(sourceImage);
		testAddNoise();
		//�ҶȻ�
		//this.imageArray.add(UtilDipose.getGrayImage(sourceImage));
		/*this.imageArray.add(UtilDipose.getGrayImage(sourceImage, null));
	    BufferedImage grayImage_=UtilDipose.getGrayImage(sourceImage,null);
	    double psnr_=UtilDipose.getPSNR(sourceImage, grayImage_);
	    System.out.printf("pnsr2 %f\n",psnr_);
	    BufferedImage grayImage=UtilDipose.getGrayImage(sourceImage);
	    double psnr=UtilDipose.getPSNR(sourceImage, grayImage); 
	    System.out.printf("pnsr1 %f\n",psnr);
	    this.imageArray.add(sourceImage);*/
		
		
		/**
		 * �������ֻҶȻ��Ľ������
		 */
		/*BufferedImage grayImage=UtilDipose.getGrayImage(sourceImage);
		int[] pixels=new int[grayImage.getWidth()*grayImage.getHeight()];
		int[] pixels_=new int[grayImage.getWidth()*grayImage.getHeight()];
		UtilDipose.getRGB(grayImage,pixels);
		grayImage=UtilDipose.getGrayImage(sourceImage, null);
		UtilDipose.getRGB(grayImage,pixels_);
		for(int i=0;i<pixels.length;i++){
			int r=(pixels[i]>>16)&0xff;
			int g=(pixels[i]>>8)&0xff;
			int b=pixels[i]&0xff;
			
			int r_=(pixels_[i]>>16)&0xff;
			int g_=(pixels_[i]>>8)&0xff;
			int b_=pixels_[i]&0xff;
			System.out.printf("r:%3d g:%3d b:%3d  ---  r:%3d g:%3d b:%3d\n", r,g,b,r_,g_,b_);
		}*/
		
		
		//������
//		AddNosingFilter addNosingFilter=new AddNosingFilter();
		//1000����������
		/*addNosingFilter.setNosizeType(AddNosingFilter.NOSIZE_TYPE_IMPULSE);
		addNosingFilter.setNosizeFactor(1000);
		destImage=addNosingFilter.filter(sourceImage, null);
		this.imageArray.add(destImage);
		this.imageMap.put("1000����������", destImage);*/
		
//		//��˹����
//		addNosingFilter.setNosizeType(AddNosingFilter.NOSIZE_TYPE_GAUSSION);
//		addNosingFilter.setNosizeFactor(25);
//		destImage=addNosingFilter.filter(sourceImage, null);
//		this.imageArray.add(destImage);
//		this.imageMap.put("��Ӹ�˹����", destImage);
		//��������
		/*addNosingFilter.setNosizeType(AddNosingFilter.NOSIZE_TYPE_POISSON);
		addNosingFilter.setNosizeFactor(25);
		destImage=addNosingFilter.filter(sourceImage, null);
		this.imageArray.add(destImage);
		this.imageMap.put("��Ӳ�������",destImage);*/
		//ȥ��
//		StatisticalFilter denosingFilter=new StatisticalFilter();
		//MAX
//		denosingFilter.setType(StatisticalFilter.STATISTICAL_TYPE_MAX);
//		this.imageArray.add(denosingFilter.filter(destImage, null));
//		//MIN
//		denosingFilter.setType(StatisticalFilter.STATISTICAL_TYPE_MIN);
//		this.imageArray.add(denosingFilter.filter(destImage, null));
//		
//		denosingFilter.setType(StatisticalFilter.STATISTICAL_TYPE_MIN_MAX);
//		this.imageArray.add(denosingFilter.filter(destImage, null));
//		
//		denosingFilter.setType(StatisticalFilter.STATISTICAL_TYPE_CENTER);
//		this.imageArray.add(denosingFilter.filter(destImage, null));
//		
//		denosingFilter.setType(StatisticalFilter.STATISTICAL_TYPE_HALF);
//		this.imageArray.add(denosingFilter.filter(destImage, null));
		/*StatisticsFilter filter=new StatisticsFilter();
		filter.setType(StatisticsFilter.MAX_FILTER);
		this.imageArray.add(filter.filter(destImage, null));*/
		
//		MeanFilter meanFilter=new MeanFilter();
//		meanFilter.setMeanType(MeanFilter.MEAN_TYPE_HARMONIC);
//		this.imageArray.add(meanFilter.filter(destImage, null));
//		
//		meanFilter.setMeanType(MeanFilter.MEAN_TYPE_ARITHMETIC);
//		this.imageArray.add(meanFilter.filter(destImage, null));
//		
//		meanFilter.setMeanType(MeanFilter.MEAN_TYPE_GEOMETRIC);
//		this.imageArray.add(meanFilter.filter(destImage, null));
		//this.imageMap.put("���ֵȥ��",destImage);
		//denosingFilter.setType(StatisticalFilter.STATISTICAL_TYPE_MIN_MAX);
		//this.imageArray.add(denosingFilter.filter(destImage, null));
		//filter.setType(StatisticsFilter.MIN_MAX_FILTER);
		//this.imageArray.add(filter.filter(destImage, null));
		//this.imageMap.put("�����Сֵȥ��",destImage);
		
		//denosingFilter.setType(StatisticalFilter.STATISTICAL_TYPE_MIN);
		//this.imageArray.add(denosingFilter.filter(destImage, null));
		/*denosingFilter.setType(StatisticalFilter.STATISTICAL_TYPE_MIN);
		destImage=denosingFilter.filter(sourceImage, null);
		this.imageArray.add(destImage);
		this.imageMap.put("��Сֵȥ��",destImage);
		
		denosingFilter.setType(StatisticalFilter.STATISTICAL_TYPE_CENTER);
		destImage=denosingFilter.filter(sourceImage, null);
		this.imageArray.add(destImage);
		this.imageMap.put("�м�ֵȥ��",destImage);
		
		denosingFilter.setType(StatisticalFilter.STATISTICAL_TYPE_HALF);
		destImage=denosingFilter.filter(sourceImage, null);
		this.imageArray.add(destImage);
		this.imageMap.put("��ֵȥ��",destImage);*/
	}
	
	/**
	 * ���Լ���
	 */
	public void testAddNoise(){
		//
		BufferedImage gray=UtilDipose.getGrayImage(sourceImage,null);
		this.imageArray.add(gray);
		AddNoisingFilter filter=new AddNoisingFilter();
		SVDFilter svd=new SVDFilter();
		//��ͼ��ת��Ϊ�Ҷ�ͼ
		filter.setColorType(AbstractBufferedImageOp.COLOSPACE_TYPE_GRAY);
		//��˹����
		filter.setNoiseType(AddNoisingFilter.NOISE_TYPE_GAUSSION);
		BufferedImage dest=filter.filter(gray,null);
		System.out.printf("PSNR: %f SNR: %f EPI %f:\n",
	    		UtilDipose.getPSNR(gray, dest),
	    		UtilDipose.getSNR(gray,dest),
	    		UtilDipose.getEPI(gray, dest));
		 this.imageArray.add(dest);
		UtilDipose.savePictureJPG(dest, "��˹����.jpg");
		//svdȥ��
		dest=svd.filter(dest,null);
		System.out.printf("PSNR: %f SNR: %f EPI %f:\n",
	    		UtilDipose.getPSNR(gray, dest),
	    		UtilDipose.getSNR(gray,dest),
	    		UtilDipose.getEPI(gray, dest));
		//this.imageArray.add(dest);
		UtilDipose.savePictureJPG(dest, "��˹SVD.jpg");
	    //���ɼ���
	    filter.setNoiseType(AddNoisingFilter.NOISE_TYPE_POISSON);
	    dest=filter.filter(gray,null);
	    System.out.printf("PSNR: %f SNR: %f EPI %f:\n",
	    		UtilDipose.getPSNR(gray, dest),
	    		UtilDipose.getSNR(gray,dest),
	    		UtilDipose.getEPI(gray, dest));
	    this.imageArray.add(dest);
	    UtilDipose.savePictureJPG(dest, "���ɼ���.jpg");
	    //svdȥ��
	    dest=svd.filter(dest,null);
  		System.out.printf("PSNR: %f SNR: %f EPI %f:\n",
  	    		UtilDipose.getPSNR(gray, dest),
  	    		UtilDipose.getSNR(gray,dest),
  	    		UtilDipose.getEPI(gray, dest));
  		//this.imageArray.add(dest);
  		UtilDipose.savePictureJPG(dest, "����SVD.jpg");
	    //������ ����0.01
	    filter.setNoiseType(AddNoisingFilter.NOISE_TYPE_PEPPER);
	    filter.setPepperPercent(0.01);
	    dest=filter.filter(gray,null);
	    System.out.printf("PSNR: %f SNR: %f EPI %f:\n",
	    		UtilDipose.getPSNR(gray, dest),
	    		UtilDipose.getSNR(gray,dest),
	    		UtilDipose.getEPI(gray, dest));
	    this.imageArray.add(dest);
	    UtilDipose.savePictureJPG(dest, "���μ���.jpg");
	    //svdȥ��
	    dest=svd.filter(dest,null);
  		System.out.printf("PSNR: %f SNR: %f EPI %f:\n",
  	    		UtilDipose.getPSNR(gray, dest),
  	    		UtilDipose.getSNR(gray,dest),
  	    		UtilDipose.getEPI(gray, dest));
  		//this.imageArray.add(dest);
  		UtilDipose.savePictureJPG(dest, "����SVD.jpg");
	}

	public void refresh() {
		this.repaint();
	}

	public BufferedImage getSourceImage() {
		return sourceImage;
	}

	public BufferedImage getDestImage() {
		return destImage;
	}

	public void setSourceImage(BufferedImage sourceImage) {
		this.sourceImage = sourceImage;
	}

	public void setDestImage(BufferedImage destImage) {
		this.destImage = destImage;
	}
	
	public void addImage(BufferedImage image){
		this.imageArray.add(image);
	}

	
}
