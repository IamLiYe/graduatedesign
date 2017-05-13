package graduategesign;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;


/**
 * ����������
 * ��������ɫͼ��ĻҶȻ���ͼ������׼PNSR
 * @author Administrator
 *
 */
public class UtilDipose {
	
	/*
	 * rgbͼ��ת��Ϊ�Ҷ�ͼ���red��green��blue������ռ�İٷֱ�
	 */
	//red weight
	private static final int RED_WEIGHT=30;
	//green weight
	private static final int GREEN_WIGHT=59;
	//blue weight
	private static final int BlUE_WEIGHT=11;
	
	/**
	 * ÿ���Ҷ�ֵ�����λ��
	 */
	private static final int GRARY_BIT=8;
	
	/*
	 * Ĭ�ϱ���ص�
	 */
	private static final String PICTURE_SAVE_PATH="D:\\chart\\svd\\";
	/**
	 * ��rgb��ɫͼ��ת��Ϊ�Ҷ�ͼ��
	 * @param srcImage Դͼ��
	 * @param destImage �������ͼ��
	 * @return ������ĻҶ�ͼ�����destImage==null�����srcImage����һ���µ�BufferedImage����ֵ��destImage
	 * ���ҽ��䷵��
	 */
	public static BufferedImage getGrayImage(BufferedImage srcImage,BufferedImage destImage){

		if(destImage==null){
			destImage=createCompatibleDestImage(srcImage, null);
		}
		int width=srcImage.getWidth();
		int height=srcImage.getHeight();
		ColorModel colorModel=srcImage.getColorModel();
		if(destImage==null){
			destImage=new BufferedImage(
					colorModel,
					colorModel.createCompatibleWritableRaster(width, height),
					colorModel.isAlphaPremultiplied(),
					null
					);
		}
		int[] pixels=new int[width*height];
		int[] newPixels=new int[width*height];
		getRGB(srcImage,pixels);
		int a,r,g,b,gray;
		for(int i=0;i<pixels.length;i++){
			a=(pixels[i]>>24)&0xff;
			r=(pixels[i]>>16)&0xff;
		    g=(pixels[i]>>8)&0xff;
		    b=pixels[i]&0xff;
		    gray=(RED_WEIGHT*r+GREEN_WIGHT*g+BlUE_WEIGHT*b)/100;
		    newPixels[i]=(a<<24)|(gray<<16)|(gray<<8)|gray;
		}
		setRGB(destImage, newPixels);
		return destImage;
	}
	
	/**
	 * ��ȡRGB��ARGBͼ�������һ���򵥷���
	 * ���������ֱ��ʹ��BufferedImage.getRGB������Ҫ����ͼ�񣬴Ӷ�������ϵͳ���ܵ����
	 * @param iamge Դͼ��
	 * @param startX ��ȡͼ���Ӿ������ԭͼ�����ĺ��������
	 * @param startY ��ȡͼ���Ӿ������ԭͼ���������������
	 * @param width ��ȡͼ���Ӿ���Ŀ���
	 * @param height ��ȡͼ���Ӿ���ĸ߶�
	 * @param rgbArray ��ȡ��RGB����
	 * @return �ı���ͼ��
	 */
	public static int[] getRGB(BufferedImage image,int startX,int startY,int width,int height,int[] rgbArray){
		int type=image.getType();
		if(type==BufferedImage.TYPE_INT_ARGB||type==BufferedImage.TYPE_INT_RGB){
			return (int [])image.getRaster().getDataElements(startX,startY, width, height, rgbArray);
		}
		else
			return image.getRGB(startX,startY, width, height, rgbArray,0, width);
	}
	
	/**
	 * ��ȡͼ���ȫrgb�����ȫ������
	 * @param image
	 * @param rgbArray
	 * @return
	 */
	public static int[] getRGB(BufferedImage image,int[] rgbArray){
		return getRGB(image, 0,0,image.getWidth(),image.getHeight(),rgbArray);
	}
	
	/**
	 * ����RGB��ARGBͼ�������һ���򵥷���
	 * ���������ֱ��ʹ��BufferedImage.getRGB������Ҫ����ͼ�񣬴Ӷ�������ϵͳ���ܵ����
	 * @param iamge Դͼ��
	 * @param startX ����ͼ���Ӿ������ԭͼ�����ĺ��������
	 * @param startY ����ͼ���Ӿ������ԭͼ���������������
	 * @param width ����ͼ���Ӿ���Ŀ���
	 * @param height ����ͼ���Ӿ���ĸ߶�
	 * @param rgbArray���õ�RGB����
	 */
	public static void setRGB(BufferedImage image,int startX,int startY,int width,int height,int[] rgbArray){
		int type=image.getType();
		if(type==BufferedImage.TYPE_INT_ARGB||type==BufferedImage.TYPE_INT_RGB){
			image.getRaster().setDataElements(startX,startY, width, height, rgbArray);
		}
		else
			image.setRGB(startX,startY, width, height, rgbArray,0, width);
	}
	
	/**
	 * ����ͼ���rgb�����ȫ������
	 * @param image
	 * @param rgbArray
	 */
	public static void setRGB(BufferedImage image,int[] rgbArray){
		setRGB(image, 0, 0,image.getWidth(),image.getHeight(), rgbArray);
	}
	
	/**
	 * ʹ��ColorConvertOpʵ��ͼ��ҶȻ�����
	 * @param srcImage
	 * @return
	 */
	public static BufferedImage getGrayImage(BufferedImage srcImage){
		ColorConvertOp filter=new ColorConvertOp(
				ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		return filter.filter(srcImage, null);
	}
	
	/**
	 * ����ԭͼ�����ɫģʽ����һ���µ�ͼ��
	 * @param srcImage
	 * @param cm
	 * @return
	 */
	public static BufferedImage createCompatibleDestImage(BufferedImage srcImage,ColorModel colorModel){
		if(colorModel==null){
			colorModel=srcImage.getColorModel();
		}
		return new BufferedImage(colorModel,
				colorModel.createCompatibleWritableRaster(srcImage.getWidth(), srcImage.getHeight()),
				colorModel.isAlphaPremultiplied(),
				null);
	}
	
	/**
	 * ��[0,number-1]���������������ѡȡsize����������ڽ�������ѡȡ���������ظ�
	 * @param number ��������Ϊnumber-1
	 * @param dest ��Ž��
	 * @param size ѡ�����������
	 * @param random �����������
	 * @return ��Ž������
	 */
	public static int[] randomSort(int number,int size,int[] dest,Random random){
		
		//��ȫ���
		if(number<0||number<size){
			return null;
		}
		if(dest==null)
			dest=new int[size];
		if(random==null)
			random=new Random();
		//���ɴ��������鼯��
		int[]sort=new int[number];
		for(int i=0;i<number;i++)
			sort[i]=i;
		int index;
		for(int i=0;i<size;i++){
			//�������ѡȡ������
			index=i+random.nextInt(number-i);
			
            //��ȡ�±����������������ֵ���������ӵ���������У�������ǰ���������������ӵ�ѡȡ�����ȥ
			int temp=sort[i];
			sort[i]=sort[index];
			dest[i]=sort[index];
			sort[index]=temp;
			/*
			 * ���Բ�����ֱ��
			 * sort[index]=i
			 */
		}
		return dest;
	}

	/**
	 * ��ȡȥ���ͼ��ķ�ֵ�����(Peak Signal to Noise Ratio)
	 * ����Ĭ������ͼ��Ϊ0λ�ĻҶ�ͼ
	 * @param srcImage ԭͼ��
	 * @param destImage �������ͼ��
	 * @see http://blog.sina.com.cn/s/blog_7e27bd1a0101alq5.html
	 * @return
	 */
	public static double getPSNR(BufferedImage srcImage,BufferedImage destImage){
		int width=srcImage.getWidth();
		int height=srcImage.getHeight();
		int srcPixels[]=new int[width*height];
		int destPixels[]=new int[width*height];
		getRGB(srcImage, srcPixels);
		getRGB(destImage, destPixels);
		return getPSNR(srcPixels, destPixels);
	}
	
	/**
	 * ��ֵ�����
	 * @param srcArray
	 * @param destArray
	 * @return
	 */
	public static double getPSNR(int[] srcArray,int[] destArray){
		double psnr=0.0;
		double mse=0.0;
		double sum=0.0;
		double div=0;
		/*
		 * ����涨���ǻ�ȡ��ͼ��Ϊ8λ�ĻҶ�ͼ
		 */
		for(int i=0;i<srcArray.length;i++){
			div=(double)Math.abs((srcArray[i]&0xff)-(destArray[i]&0xff));
			sum+=(div*div);
		}
		mse=sum/(srcArray.length);
		int peak=(int)(0x1<<GRARY_BIT)-1;
		psnr=Math.log10((peak/Math.sqrt(mse)))*20;
	    return psnr;
	}
	
	/**
	 * �����
	 * @param src
	 * @param dest
	 * @return
	 */
	public static double getSNR(BufferedImage src,BufferedImage dest){
		int[]srcArray=new int[src.getWidth()*src.getHeight()];
		getRGB(src, srcArray);
		int[]destArray=new int[src.getWidth()*src.getHeight()];
		getRGB(dest, destArray);
		return getSNRtoPicturesSimilarity(srcArray, destArray);
	}
	
	/**
	 * Signal to Noise(SNR) �����
	 * SNR�Ķ��巽ʽ����
	 * ����ͼ�����ʶ��
	 */
	public static double getSNRtoPicturesSimilarity(int[] srcArray,int[]destArray){
		double snr=0.0;
		double mse=0.0;
		double sum1=0.0,sum2=0.0;
		double div1,div2;
		for(int i=0;i<srcArray.length;i++){
			div1=srcArray[i]&0xff;
			sum1+=(div1*div1);
			div2=(double)Math.abs((srcArray[i]&0xff)-(destArray[i]&0xff));
			sum2+=(div2*div2);
		}
		mse=sum1/sum2;
		snr=10*Math.log10(mse);
		return snr;
	}
	
	/**
	 * 
	 */
	public static double getSNRtoNosizeStrength(int[] srcArray,int[]destArray){ 
		double snr=0.0;
		return snr;
	}
	
	public static double getEPI(BufferedImage src,BufferedImage dest){
		int[]srcArray=new int[src.getWidth()*src.getHeight()];
		getRGB(src, srcArray);
		int[]destArray=new int[src.getWidth()*src.getHeight()];
		getRGB(dest, destArray);
		return getEPI(srcArray, destArray, src.getWidth(), src.getHeight());
	}
	/**
	 * 
	 */
	public static double getEPI(int[] srcArray,int[]destArray,int width,int height){
		double srcSum=0.0;
		double destSum=0.0;
		
		//ˮƽ
		int index1,index2;
		for(int row=0;row<height;row++){
			for(int col=0;col<width-1;col++){
				index1=row*width+col;
				index2=index1+1;
				srcSum+=Math.abs(srcArray[index1]-srcArray[index2]);
				destSum+=Math.abs(destArray[index1]-destArray[index2]);
			}
		}
		//��ֱ
		for(int row=0;row<height-1;row++){
			for(int col=0;col<width;col++){
				index1=row*width+col;
				index2=index1+width;
				srcSum+=Math.abs(srcArray[index1]-srcArray[index2]);
				destSum+=Math.abs(destArray[index1]-destArray[index2]);
			}
		}
		double epi=destSum/srcSum;
		return epi;
	}
	
	/**
	 * ��һάinteger����ת��Ϊ��άdouble����
	 */
	public static double[][] toDoubleMatrix(int[] srcArray,int width,int height){
		double[][] matrix=new double[height][width];
		for(int row=0;row<height;row++){
			for(int col=0;col<width;col++){
				int index=row*width+col;
				/*
				 * �������ǹ涨������Ϊ8λ�Ҷ�ͼ������ֻȡ��8λ����redͨ����ֵ���Ϳ�����
				 */
				matrix[row][col]=(double)(srcArray[index]&0xff);
			}
		}
		return matrix;
	}
	
	/**
	 * ����άdouble����ת��Ϊһάinteger����
	 * @param destMatrix
	 * @param width
	 * @param height
	 * @return
	 */
	public static int[] toIntegerArray(double[][] destMatrix,int width,int height){
		int[] array=new int[width*height];
		for(int row=0;row<height;row++){
			for(int col=0;col<width;col++){
				int index=row*width+col;
				/*
				 * �������ǻ�ȡ��Ӧ�ĻҶ�ֵ��Ϊ���ܹ�������ʾ������������ͨ�������Ϊ��ͬ��ֵ
				 * ע��grayҪ���б�Ҫ�ضϴ���
				 * (gray,gray,gray)->(red,green,blue)
				 */
				int gray=(int)(destMatrix[row][col]);
				gray=gray>255?255:(gray<0?0:gray);
				array[index]=gray<<16|gray<<8|gray;
			}
		}
		return array;
	}
	
	/**
	 * ������PNG��ʽ����ͼ��
	 */
	public static String saveChartAsFile(
			JFreeChart chart,
			String fileName,
			int width,
			int height){
		FileOutputStream outPNG=null;
		FileOutputStream outJPEG=null;
		try{
			String fileNamePNG=fileName+".png";
			String fileNameJPEG=fileName+".jpeg";
			File outfilePNG=new File(fileNamePNG);
			File outfileJPEG=new File(fileNameJPEG);
			if(!outfilePNG.getParentFile().exists()){
				outfilePNG.getParentFile().mkdirs();
			}
			if(!outfileJPEG.getParentFile().exists()){
				outfileJPEG.getParentFile().mkdirs();
			}
			outPNG=new FileOutputStream(outfilePNG);
			outJPEG=new FileOutputStream(outfileJPEG);
			//����ΪPNG
			ChartUtilities.writeChartAsPNG(outPNG, chart, width, height);
			//����ΪJPEG
			ChartUtilities.writeChartAsJPEG(outJPEG, chart, width, height);
			outPNG.flush();
			outJPEG.flush();
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(outPNG!=null){
				try{
					outPNG.close();
				}catch (IOException e) {
					// TODO: handle exception
					
				}
			}
			if(outJPEG!=null){
				try{
					outJPEG.close();
				}catch (IOException e) {
					// TODO: handle exception
					
				}
			}
		}
		return fileName;
	}
	
	public static void savePictureJPG(BufferedImage srcImage,String srcName){
		File file=new File(PICTURE_SAVE_PATH+srcName);
		if(!file.getParentFile().exists()){
			file.mkdirs();
		}
		try{
			ImageIO.write(srcImage, "jpg", file);
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}