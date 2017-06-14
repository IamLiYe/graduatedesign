package graduategesign;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
			    AttributedString ats=new AttributedString("ͼ"+i);
			    ats.addAttribute(TextAttribute.FONT,new Font("",0, 16));
			    AttributedCharacterIterator iterator=ats.getIterator();
			    g2d.drawString(iterator,x+10,y+imageHeight+20);
			}
		}
	}

	//����
	public void process() {
		this.imageArray.clear();
		this.imageArray.add(sourceImage);
		//System.out.println("32");
		lastTest();
		//testTSVD();
		//testAddNoise();
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
	 * 
	 */
	public void testSVD(){
		BufferedImage gray=UtilDipose.getGrayImage(sourceImage,null);
		this.imageArray.add(gray);
		AddNoisingFilter noisefilter=new AddNoisingFilter();
		noisefilter.setNoiseType(AddNoisingFilter.NOISE_TYPE_GAUSSION);
		gray=noisefilter.filter(gray, null);
		this.imageArray.add(gray);
		SVDFilter svdFilter=new SVDFilter();
		svdFilter.setSvdType(SVDFilter.SVD_TYPE_AVERAGE);
		BufferedImage dest=svdFilter.filter_(gray, null);
		this.imageArray.add(dest);
		svdFilter.setSvdType(SVDFilter.SVD_TYPE_CENTER);
		dest=svdFilter.filter_(gray, null);
		this.imageArray.add(dest);
	}
	
	/**
	 * 
	 */
	public void testTSVD(){
		BufferedImage gray=UtilDipose.getGrayImage(sourceImage,null);
		this.imageArray.add(gray);
		AddNoisingFilter noisefilter=new AddNoisingFilter();
		noisefilter.setSalt_pepperPercent(0.2);
		//noisefilter.setNoiseType(AddNoisingFilter.NOISE_TYPE_POISSON);
		//noisefilter.setNosizeFactor(50);
		noisefilter.setNoiseType(AddNoisingFilter.NOISE_TYPE_GAUSSION);
		StatisticalFilter statisticalFilter=new StatisticalFilter();
		statisticalFilter.setType(StatisticalFilter.STATISTICAL_TYPE_CENTER);
		List<BufferedImage> noises=new ArrayList<BufferedImage>();
		BufferedImage temp=noisefilter.filter(gray, null);
		//noises.add(temp);
		//noisefilter.setNoiseType(AddNoisingFilter.NOISE_TYPE_POISSON);
		//noisefilter.setNosizeFactor(15);
		//noisefilter.setNosizeFactor(1);
		double[] psnrs=new double[64];
		double[] srr=new double[64];
		double[] epi=new double[64];
		for(int i=0;i<64;i++){
			//
			BufferedImage ted=noisefilter.filter(gray, null);
			psnrs[i]=UtilDipose.getPSNR(gray,ted);
			epi[i]=UtilDipose.getEPI(gray,ted);
			/*System.out.printf("PSNR: %f SNR: %f EPI %f:\n",
		    		UtilDipose.getPSNR(gray,ted),
		    		UtilDipose.getSNR(gray,ted),
		    		UtilDipose.getEPI(gray, ted));*/
			noises.add(ted);
		}
		double sum;
		Arrays.sort(psnrs);
		sum=0.0;
		for(double psnr:psnrs){
			sum+=psnr;
		}
		System.out.printf("MIN: %f MAX: %f AVG %f:\n",psnrs[0],psnrs[1],psnrs[63]);
		Arrays.sort(epi);
		TSVDFilter svdFilter=new TSVDFilter();
		svdFilter.setSvdType(SVDFilter.SVD_TYPE_AVERAGE);
		List<BufferedImage> dests=svdFilter.toMode1(noises, null);
		for(int i=0;i<8;i++){
			//this.imageArray.add(noises.get(i));
			UtilDipose.savePictureJPG(noises.get(i), "dest"+i+".jpg");
		}
		for(int i=0;i<64;i++){
			//this.imageArray.add(dests.get(i));
			UtilDipose.savePictureJPG(dests.get(i), "skip_1_"+i+".jpg");
		}
		for(int i=0;i<64;i++){
			System.out.printf("PSNR: %f SNR: %f EPI %f:\n",
		    		UtilDipose.getPSNR(gray, dests.get(i)),
		    		UtilDipose.getSNR(gray,dests.get(i)),
		    		UtilDipose.getEPI(gray, dests.get(i)));
		}
		/*svdFilter.setSkip(2);
		dests=svdFilter.toMode1(noises, null);
		for(int i=0;i<8;i++){
			//this.imageArray.add(statisticalFilter.filter(dests.get(i),null));
			UtilDipose.savePictureJPG(dests.get(i), "skip_2"+i+".jpg");
		}
		for(int i=0;i<64;i++){
			System.out.printf("PSNR: %f SNR: %f EPI %f:\n",
		    		UtilDipose.getPSNR(gray, dests.get(i)),
		    		UtilDipose.getSNR(gray,dests.get(i)),
		    		UtilDipose.getEPI(gray, dests.get(i)));
		}
		svdFilter.setSkip(4);
		dests=svdFilter.toMode1(noises, null);
		for(int i=0;i<16;i++){
			//this.imageArray.add(statisticalFilter.filter(dests.get(i),null));
			UtilDipose.savePictureJPG(dests.get(i), "skip_4"+i+".jpg");
		}
		for(int i=0;i<64;i++){
			System.out.printf("PSNR: %f SNR: %f EPI %f:\n",
		    		UtilDipose.getPSNR(gray, dests.get(i)),
		    		UtilDipose.getSNR(gray,dests.get(i)),
		    		UtilDipose.getEPI(gray, dests.get(i)));
		}*/
	}
	
	/**
	 * ���Լ���
	 */
	public void testAddNoise(){
		//
		BufferedImage gray=UtilDipose.getGrayImage(sourceImage,null);
		this.imageArray.add(gray);
		AddNoisingFilter filter=new AddNoisingFilter();
		SVDFilter svd=new SVDFilter(sourceImage);
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
		System.out.printf("SVD PSNR:%fSNR:%fEPI%f:\n",
	    		UtilDipose.getPSNR(gray, dest),
	    		UtilDipose.getSNR(gray,dest),
	    		UtilDipose.getEPI(gray, dest));
		
		//this.imageArray.add(dest);
		UtilDipose.savePictureJPG(dest, "��˹SVD.jpg");
		for(int i=10;i<41;i++){
			dest=svd.lowRankbyNumber(dest, null,i);
			String str=String.format("SVD[%d]==PSNR_%dSNR_%dEPI%d_",
					i,
					Math.round(UtilDipose.getPSNR(gray, dest)*100),
		    		Math.round(UtilDipose.getSNR(gray, dest)*100),
		    		Math.round(UtilDipose.getEPI(gray, dest)*100));
			System.out.println(str);
			UtilDipose.savePictureJPG(dest,str+".jpg");
		}
       //ͳ���˲�
		String str;
		StatisticalFilter statisticalFilter=new StatisticalFilter();
		statisticalFilter.setType(StatisticalFilter.STATISTICAL_TYPE_HALF);
		dest=statisticalFilter.filter(gray, null);
		str=String.format("STATISTICAL_HALF[%d]==PSNR_%dSNR_%dEPI%d_",
				StatisticalFilter.STATISTICAL_TYPE_HALF,
				Math.round(UtilDipose.getPSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getEPI(gray, dest)*100));
		UtilDipose.savePictureJPG(dest, str+".jpg");
		
		statisticalFilter.setType(StatisticalFilter.STATISTICAL_TYPE_MIN);
		dest=statisticalFilter.filter(gray, null);
		str=String.format("STATISTICAL_MIN[%d]==PSNR_%dSNR_%dEPI%d_",
				StatisticalFilter.STATISTICAL_TYPE_MIN,
				Math.round(UtilDipose.getPSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getEPI(gray, dest)*100));
		UtilDipose.savePictureJPG(dest, str+".jpg");
		
		statisticalFilter.setType(StatisticalFilter.STATISTICAL_TYPE_MIN_MAX);
		dest=statisticalFilter.filter(gray, null);
		str=String.format("STATISTICAL_MIN_MAX[%d]==PSNR_%dSNR_%dEPI%d_",
				StatisticalFilter.STATISTICAL_TYPE_MIN_MAX,
				Math.round(UtilDipose.getPSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getEPI(gray, dest)*100));
		UtilDipose.savePictureJPG(dest, str+".jpg");
		
		statisticalFilter.setType(StatisticalFilter.STATISTICAL_TYPE_MAX);
		dest=statisticalFilter.filter(gray, null);
		str=String.format("STATISTICAL_MAX[%d]==PSNR_%dSNR_%dEPI%d_",
				StatisticalFilter.STATISTICAL_TYPE_MAX,
				Math.round(UtilDipose.getPSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getEPI(gray, dest)*100));
		UtilDipose.savePictureJPG(dest, str+".jpg");
		
		statisticalFilter.setType(StatisticalFilter.STATISTICAL_TYPE_CENTER);
		dest=statisticalFilter.filter(gray, null);
		str=String.format("STATISTICAL_CENTER[%d]==PSNR_%dSNR_%dEPI%d_",
				StatisticalFilter.STATISTICAL_TYPE_CENTER,
				Math.round(UtilDipose.getPSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getEPI(gray, dest)*100));
		UtilDipose.savePictureJPG(dest, str+".jpg");
		
		/**
		 * ��ֵ�˲�
		 */
		MeanFilter meanFilterr=new MeanFilter();
		statisticalFilter.setType(MeanFilter.MEAN_TYPE_ARITHMETIC);
		dest=statisticalFilter.filter(gray, null);
		str=String.format("MEAN_ARITHMETIC[%d]==PSNR_%dSNR_%dEPI%d_",
				MeanFilter.MEAN_TYPE_ARITHMETIC,
				Math.round(UtilDipose.getPSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getEPI(gray, dest)*100));
		UtilDipose.savePictureJPG(dest, str+".jpg");
		
		statisticalFilter.setType(MeanFilter.MEAN_TYPE_GEOMETRIC);
		dest=statisticalFilter.filter(gray, null);
		str=String.format("MEAN_GEOMETRIC[%d]==PSNR_%dSNR_%dEPI%d_",
				MeanFilter.MEAN_TYPE_GEOMETRIC,
				Math.round(UtilDipose.getPSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getEPI(gray, dest)*100));
		UtilDipose.savePictureJPG(dest, str+".jpg");
		
		statisticalFilter.setType(MeanFilter.MEAN_TYPE_HARMONIC);
		dest=statisticalFilter.filter(gray, null);
		str=String.format("MEAN_HARMONIC[%d]==PSNR_%dSNR_%dEPI%d_",
				MeanFilter.MEAN_TYPE_HARMONIC,
	    		Math.round(UtilDipose.getPSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getSNR(gray, dest)*100),
	    		Math.round(UtilDipose.getEPI(gray, dest)*100));
		UtilDipose.savePictureJPG(dest, str+".jpg");
		
		
		
	   /* //���ɼ���
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
  		UtilDipose.savePictureJPG(dest, "����SVD.jpg");*/
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
	
	/**
	 * ʵ�����
	 */
	public void lastTest(){
		
		BufferedImage gray=UtilDipose.getGrayImage(sourceImage,null);//��ȡ�Ҷ�ͼ��
		this.imageArray.add(gray);//��ͼ����ʾ
		UtilDipose.savePictureJPG(gray, "�Ҷ�ͼ��\\�Ҷ�ͼ��.jpg");
		System.out.println(UtilDipose.getEPI(gray, gray));
		AddNoisingFilter noisefilter=new AddNoisingFilter();//�������
		noisefilter.setNoiseType(AddNoisingFilter.NOISE_TYPE_SALTANDPEPPER);//���ø�˹����
		noisefilter.setSalt_pepperPercent(0.1);
		BufferedImage noise=noisefilter.filter(gray, null);
		this.imageArray.add(noise);
		//���þ�ֵ�˲���
		StatisticalFilter sfilter=new StatisticalFilter();
		sfilter.setType(StatisticalFilter.STATISTICAL_TYPE_CENTER);//��ֵ�˲�
		MeanFilter mfilter=new MeanFilter();
		mfilter.setMeanType(MeanFilter.MEAN_TYPE_HARMONIC);//��ֵ�˲�
		SVDFilter svdFilter=new SVDFilter();//SVD�˲�
		TSVDFilter tsvdFilter=new TSVDFilter();//TSVD�˲�
		
		this.imageArray.add(svdFilter.filter(noise, null));
		
		//svdFilter.setSvdType(SVDFilter.SVD_TYPE_AVERAGE);
		//this.imageArray.add(svdFilter.filter_(noise, null));
		//svdFilter.setSvdType(SVDFilter.SVD_TYPE_CENTER);
		//BufferedImage temp=svdFilter.filter_(noise, null);
		//this.imageArray.add(svdFilter.filter_(noise, null));
		//System.out.println("PSNR"+UtilDipose.getPSNR(gray, temp));
		//�������
		double[] psnrs=new double[64];
		double[] epi=new double[64];
		double[] snr=new double[64];
		List<BufferedImage> noises=new ArrayList<BufferedImage>();
		BufferedImage temp;
		this.imageArray.add(gray);
		for(int i=0;i<64;i++){
			temp=noisefilter.filter(gray, null);
			noises.add(temp);
			UtilDipose.savePictureJPG(temp, "����\\"+i+".jpg");
			psnrs[i]=UtilDipose.getPSNR(gray,temp);
			snr[i]=UtilDipose.getSNR(gray,temp);
			epi[i]=UtilDipose.getEPI(gray,temp);
		}
		System.out.println("��������");
		printPE(psnrs, snr, epi);
		//��ֵ�˲�
		System.out.println("��ֵ����");
		for(int i=0;i<64;i++){
			temp=mfilter.filter(noises.get(i), null);
			//noises.add(temp);
			UtilDipose.savePictureJPG(temp, "��ֵ\\����\\"+i+".jpg");
			psnrs[i]=UtilDipose.getPSNR(gray,temp);
			snr[i]=UtilDipose.getSNR(gray,temp);
			epi[i]=UtilDipose.getEPI(gray,temp);
		}
		printPE(psnrs, snr, epi);
		mfilter.setMeanType(MeanFilter.MEAN_TYPE_ARITHMETIC);
		for(int i=0;i<64;i++){
			temp=mfilter.filter(noises.get(i), null);
			//noises.add(temp);
			UtilDipose.savePictureJPG(temp, "��ֵ\\����\\"+i+".jpg");
			psnrs[i]=UtilDipose.getPSNR(gray,temp);
			snr[i]=UtilDipose.getSNR(gray,temp);
			epi[i]=UtilDipose.getEPI(gray,temp);
		}
		System.out.println("��ֵ_������");
		printPE(psnrs, snr, epi);
		mfilter.setMeanType(MeanFilter.MEAN_TYPE_GEOMETRIC);
		for(int i=0;i<64;i++){
			temp=mfilter.filter(noises.get(i), null);
			//noises.add(temp);
			UtilDipose.savePictureJPG(temp, "��ֵ\\����\\"+i+".jpg");
			psnrs[i]=UtilDipose.getPSNR(gray,temp);
			snr[i]=UtilDipose.getSNR(gray,temp);
			epi[i]=UtilDipose.getEPI(gray,temp);
		}
		System.out.println("��ֵ_���Σ�");
		printPE(psnrs, snr, epi);
		
		//ͳ���˲����
		sfilter.setType(StatisticalFilter.STATISTICAL_TYPE_HALF);
		for(int i=0;i<64;i++){
			temp=sfilter.filter(noises.get(i), null);
			//noises.add(temp);
			UtilDipose.savePictureJPG(temp, "ͳ��\\��ֵ\\"+i+".jpg");
			psnrs[i]=UtilDipose.getPSNR(gray,temp);
			snr[i]=UtilDipose.getSNR(gray,temp);
			epi[i]=UtilDipose.getEPI(gray,temp);
		}
		System.out.println("ͳ����ֵ��");
		printPE(psnrs, snr, epi);
		
		sfilter.setType(StatisticalFilter.STATISTICAL_TYPE_MIN_MAX);
		for(int i=0;i<64;i++){
			temp=sfilter.filter(noises.get(i), null);
			//noises.add(temp);
			UtilDipose.savePictureJPG(temp, "ͳ��\\�����Сֵ\\"+i+".jpg");
			psnrs[i]=UtilDipose.getPSNR(gray,temp);
			snr[i]=UtilDipose.getSNR(gray,temp);
			epi[i]=UtilDipose.getEPI(gray,temp);
		}
		System.out.println("�����Сֵ��");
		printPE(psnrs, snr, epi);
		
		sfilter.setType(StatisticalFilter.STATISTICAL_TYPE_CENTER);
		for(int i=0;i<64;i++){
			temp=sfilter.filter(noises.get(i), null);
			//noises.add(temp);
			UtilDipose.savePictureJPG(temp, "ͳ��\\�м��\\"+i+".jpg");
			psnrs[i]=UtilDipose.getPSNR(gray,temp);
			snr[i]=UtilDipose.getSNR(gray,temp);
			epi[i]=UtilDipose.getEPI(gray,temp);
		}
		System.out.println("�м�㣺");
		printPE(psnrs, snr, epi);
		
		//SVD
		for(int i=0;i<64;i++){
			temp=svdFilter.filter(noises.get(i), null);
			//noises.add(temp);
			UtilDipose.savePictureJPG(temp, "SVD\\��ͳsvd\\"+i+".jpg");
			psnrs[i]=UtilDipose.getPSNR(gray,temp);
			snr[i]=UtilDipose.getSNR(gray,temp);
			epi[i]=UtilDipose.getEPI(gray,temp);
		}
		System.out.println("SVD��");
		printPE(psnrs, snr, epi);
		
		List<BufferedImage> dests=tsvdFilter.toMode1(noises,null);
		for(int i=0;i<64;i++){
			temp=dests.get(i);
			//noises.add(temp);
			UtilDipose.savePictureJPG(temp, "SVD\\Tsvd\\"+i+".jpg");
			psnrs[i]=UtilDipose.getPSNR(gray,temp);
			snr[i]=UtilDipose.getSNR(gray,temp);
			epi[i]=UtilDipose.getEPI(gray,temp);
		}
		System.out.println("TSVD��");
		printPE(psnrs, snr, epi);
		
		/*System.out.println("��ֵ4����");
		for(int i=0;i<64;i++){
			temp=noisefilter.filter(gray, null);
			for(int j=0;j<3;j++){
				temp=mfilter.filter(temp, null);
			}
			UtilDipose.savePictureJPG(temp, "��ֵ\\��ֵ��"+i+".jpg");
			psnrs[i]=UtilDipose.getPSNR(gray,temp);
			snr[i]=UtilDipose.getSNR(gray,temp);
			epi[i]=UtilDipose.getEPI(gray,temp);
		}
		printPE(psnrs, snr, epi);*/
		
		//
	}

	
	public void printPE(double[] psnrs,double[] snr,double[] epi){
		double psum=0,esum=0,ssum=0,pavg=0,savg=0,eavg=0;
		double pMax=0,pMin=0,eMax=0,eMin=0,sMax=0,sMin=0;
		int pMaxi,sMaxi,eMaxi,pMini,sMini,eMini;
		
		//�����ֵ
		psum=0;esum=0;ssum=0;pavg=0;savg=0;eavg=0;
		pMax=0;pMin=0;eMax=0;eMin=0;sMax=0;sMin=0;
		pMaxi=0;sMaxi=0;eMaxi=0;pMini=0;sMini=0;eMini=0;
		for(int i=0;i<64;i++){
			psum+=psnrs[i];
			ssum+=snr[i];
			esum+=epi[i];
			//System.out.println("esum["+i+"]"+":"+epi[i]);
		}
		//System.out.println("psum"+psum+"ssum"+ssum+"esum"+esum);
		pavg=psum/64;
		savg=ssum/64;
		eavg=esum/64;
		pMax=pMin=psnrs[0];
		sMax=sMin=snr[0];
		eMax=eMin=epi[0];
		for(int i=1;i<64;i++){
			//���ֵ
			if(psnrs[i]>pMax){
				pMax=psnrs[i];
				pMaxi=i;
			}
			if(snr[i]>sMax){
				sMax=snr[i];
				sMaxi=i;
			}
			if(epi[i]>eMax){
				eMax=epi[i];
				eMaxi=i;
			}
			//��Сֵ
			if(psnrs[i]<pMin){
				pMin=psnrs[i];
				pMini=i;
			}
			if(snr[i]<sMin){
				sMin=snr[i];
				sMini=i;
			}
			if(epi[i]<eMin){
				eMin=epi[i];
				eMini=i;
			}
		}
		System.out.printf("PSNR :MAX[%d]=%f;MIN[%d]=%f;AVG=%f\n",pMaxi,pMax,pMini,pMin,pavg);
		System.out.printf("_SNR :MAX[%d]=%f;MIN[%d]=%f;AVG=%f\n",sMaxi,sMax,sMini,sMin,savg);
		System.out.printf("_EPI :MAX[%d]=%f;MIN[%d]=%f;AVG=%f\n",eMaxi,eMax,eMini,eMin,eavg);
	}
}
