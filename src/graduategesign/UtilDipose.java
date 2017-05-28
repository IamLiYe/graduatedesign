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

import javax.imageio.ImageIO;import javax.xml.parsers.DocumentBuilder;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import Jama.Matrix;


/**
 * ����������
 * ������
 * 1,��ɫͼ��ĻҶȻ���ͼ�����׼PNSR,SNR,EPI
 * 2,�Ҷ�ͼ��ת��
 * 3,double[][] & int[]
 * 4,�ַ������
 * 5,ͼ��RGB��ȡ������
 * 6,ͼ�񴴽�
 * 7������ͼ���ͼ��
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
	private static final String PICTURE_SAVE_PATH="D:\\chart\\svd\\pepperandslat\\";
	/**
	 * ��rgb��ɫͼ��ת��Ϊ�Ҷ�ͼ��
	 * @param srcImage Դͼ��
	 * @param destImage ������ͼ��
	 * @return �����ĻҶ�ͼ�����destImage==null�����srcImage����һ���µ�BufferedImage����ֵ��destImage
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
	 * @param width ��ȡͼ���Ӿ���Ŀ��
	 * @param height ��ȡͼ���Ӿ���ĸ߶�
	 * @param rgbArray ��ȡ��RGB����
	 * @return �ı���ͼ��
	 */
	public static int[] getRGB(BufferedImage image,int startX,int startY,int width,int height,int[] rgbArray){
		/*if(rgbArray==null)
			rgbArray=new int[width*height];*/
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
	 * @param width ����ͼ���Ӿ���Ŀ��
	 * @param height ����ͼ���Ӿ���ĸ߶�
	 * @param rgbArray���õ�RGB����
	 */
	public static void setRGB(BufferedImage image,int startX,int startY,int width,int height,int[] rgbArray){
		int type=image.getType();
		if(rgbArray==null)
			rgbArray=new int[width*height];
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
			
            //��ȡ�±���������������ֵ��������ӵ���������У�������ǰ�������������ӵ�ѡȡ�����ȥ
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
	 * @param destImage ������ͼ��
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
				 * �������ǹ涨�����Ϊ8λ�Ҷ�ͼ������ֻȡ��8λ����redͨ����ֵ���Ϳ�����
				 */
				matrix[row][col]=(double)(srcArray[index]&0xff);
			}
		}
		return matrix;
	}
	
	/**
	 * ����άdouble����ת��Ϊһάinteger����
	 * ���ﷵ�ص���һ��RGB����ͨ����ȵ�int����
	 * @param destMatrix ��ת��������
	 * @param width �����
	 * @param height �����
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
				int gray=(int)Math.round(destMatrix[row][col]);
				gray=gray>255?255:(gray<0?0:gray);
				array[index]=(0xff<<24)|(gray<<16)|(gray<<8)|gray;
				//int a=(array[index]>>24)&0xff;
				//int r=(array[index]>>16)&0xff;
				//int g=(array[index]>>8)&0xff;
				//int b=array[index]&0xff;
				//System.out.print("r:"+r+"g"+g+"b"+b);
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
	
	/**
	 * �ַ��������Ϊ����
	 * @param srcStr �������ַ���
	 * @param filler ���Դ�ַ�������С���������ĵĳ��ȣ��ø��ַ��������
	 * @param strlength ������߽�ȡ��ĵ��ַ�������
	 * @return �������ַ���
	 */
	public static String lestPad(String srcStr,char filler,int strLength){
		String resultStr="";
		String srcStrTrim=srcStr.trim();
		if(srcStr!=null){
			int length=srcStrTrim.length();
			if(length>strLength){
				resultStr=srcStrTrim.substring(0, strLength);
			}else if(length==strLength){
				resultStr=srcStrTrim;
			}
			else{
				//�����
				StringBuilder temp=new StringBuilder();
				for(int i=0;i<strLength-length;i++){
					temp.append(filler);
				}
				resultStr=temp.append(srcStrTrim).toString();
			}
		}
		return resultStr;
	}
	
	/**
	 * �ַ��������Ϊ����
	 * @param srcStr �������ַ���
	 * @param filler ���Դ�ַ�������С���������ĵĳ��ȣ��ø��ַ��������
	 * @param strlength ������߽�ȡ��ĵ��ַ�������
	 * @return �������ַ���
	 */
	public static String rigntPad(String srcStr,char filler,int strLength){
		String resultStr="";
		String srcStrTrim=srcStr.trim();
		if(srcStr!=null){
			int length=srcStrTrim.length();
			if(length>strLength){
				resultStr=srcStrTrim.substring(0, strLength);
			}else if(length==strLength){
				resultStr=srcStrTrim;
			}
			else{
				//�����
				StringBuilder temp=new StringBuilder(srcStrTrim);
				for(int i=0;i<strLength-length;i++){
					temp.append(filler);
				}
				resultStr=temp.toString();
			}
		}
		return resultStr;
	}
	
	/**
	 * ����һάint�������ö�άdouble�����ֵ
	 * @param matrix
	 * @param startX
	 * @param startY
	 * @param width
	 * @param height
	 * @param pixels
	 */
	public static void setArray(double[][]matrix,
			int startX,int startY,int width,int height,
			int[] pixels){
		int count=0;
		for(int col=startX;col<width+startX;col++){
			for(int row=startY;row<height+startY;row++){
				double gray=pixels[count]&0xff;//blue
				matrix[row][col]=gray;
				count++;
			}
		}
	}
	
	/**
	 * ���ݶ�άdouble�����ȡָ��λ�õ�ֵ�����һά������
	 * @param matrix
	 * @param startX
	 * @param startY
	 * @param width
	 * @param height
	 * @param pixels
	 * @return
	 */
	public static int[] getArray(
			double[][]matrix,
			int startX,int startY,int width,int height,
			int[] pixelArray,
			int imageWidth,
			int pstartX,int pstartY,int pwidth,int pheight){
		if(pixelArray==null)
			pixelArray=new int[width*height];
		int[] pixels=new int[width*height];
		int count=0;
		for(int row=startY;row<startY+height;row++){
			for(int col=startX;col<startX+width;col++){
				int gray=(int)Math.round(matrix[row][col]);
				pixels[count++]=(0xff<<24)|(gray<<16)|(gray<<8)|gray;
			}
		}
		count=0;
		for(int row=pstartY;row<pstartY+pheight;row++){
			for(int col=pstartX;col<pstartX+pwidth;col++){
				int index=row*imageWidth+col;
				pixelArray[index]=pixels[count++];
			}
		}
		return pixelArray;
	}
	
	/**
	 * ����Ƭ,�����ǽ�ԭ����ת�ã�������Ƭ�ķ�������
	 * @param pixelArray
	 * @param width
	 * @param height
	 * @param skip
	 * @return
	 */
	public static double[][][] getSVDMODE2Array(int[][]pixelArray,int width,int height,int skip)
	{
		int[][] pixelArray_=new int[pixelArray.length][];
		for(int i=0;i<pixelArray.length;i++){//ת�ã����н���
			pixelArray_[i]=UtilDipose.rgbTranspose(pixelArray[i], width, height);
			//printRGB(pixelArray_[i],height,width);
		}
		int width_=height;
		int height_=width;
		double[][][] matrixs_=getSVDMODE1Array(pixelArray_, width_, height_, skip);		
		return matrixs_;
	}
	
	public static int[][] toMODE2Array(double[][][] matrixs,int width_,int height_,int skip){
		int[][] pixelArray_=toMODE1Array(matrixs, width_, height_, skip);
		int[][] pixelArray=new int[pixelArray_.length][];
		for(int i=0;i<pixelArray.length;i++){//ת�ã����н���
			pixelArray[i]=UtilDipose.rgbTranspose(pixelArray_[i], width_, height_);
			//printRGB(pixelArray[i],height_,width_);
		}
		return pixelArray;
	}
	/**
	 * ���ж�ͼ�����������Ƭ
	 * @param pxiels ͼ������int[][]����
	 * @param width ÿ��ͼ��ĳ���
	 * @param hegiht ÿ��ͼ��Ŀ��
	 * @param skip �еĿ��
	 * @return ��Ƭ��ľ�������double[][][]����
	 */
	public static double[][][] getSVDMODE1Array(int[][]pixelArray,int width,int height,int skip){
		int colb=width/skip;//ǰ��colb����Ϊ�̶����skip
		int coll=width%skip;//���ʣ�µ�Ϊ��ɢ��,Ϊ���������
		double[][][] array;
		int[] pixels;
		//int count;
		if(coll==0){
			array=new double[colb][height][pixelArray.length*skip];
			/**
			 * ����ÿһԭʼ���ݣ�����ת���ľ����е�λ����ͬ
			 */
			int startX=0;//����ȷ����Ƭ���λ��
			for(int i=0;i<pixelArray.length;i++){
				int count=0;
				for(int j=0;j<width;j+=skip){
					pixels=UtilDipose.getRGB(pixelArray[i],width,j,0,skip,height,null);
					UtilDipose.setArray(array[count++],startX, 0, skip, height, pixels);
				}
				startX+=skip;
			}
		}	
		else
		{
			array=new double[colb+1][][];
			for(int i=0;i<colb;i++)
				array[i]=new double[height][pixelArray.length*skip];
			array[colb]=new double[height][coll*pixelArray.length];
			/**
			 * ����ÿһԭʼ���ݣ�����ת���ľ����е�λ����ͬ
			 */
			int startX=0;//����ȷ����Ƭ���λ��
			int lstartX=0;//����ȷ�����һƬ��Ƭ��λ��
			for(int i=0;i<pixelArray.length;i++){
				int count=0;
				for(int j=0;j<width-coll;j+=skip){
					pixels=UtilDipose.getRGB(pixelArray[i],width,j,0,skip,height,null);
					UtilDipose.setArray(array[count++],startX, 0, skip, height, pixels);
				}
				pixels=UtilDipose.getRGB(pixelArray[i],width,width-coll,0,coll,height,null);
				UtilDipose.setArray(array[count], lstartX,0,coll, height, pixels);
				startX+=skip;
				lstartX+=coll;
			}
		}
			
		return array;
	}
	
	/**
	 * ���鱻����Ƭ�������
	 * @param pixelArray
	 * @param width
	 * @param height
	 * @param skip
	 * @return
	 */
	public static int[][] toMODE1Array(double[][][] matrixs,int width,int height,int skip){
		int colb=matrixs[0][0].length/skip;
		int coll=width%skip;
		System.out.println("colb"+colb);
		System.out.println("coll"+coll);
		int[][] pixelArray;
		//int colb=width/skip;	
		int count;
		if(coll==0){
			pixelArray=new int[colb][width*height];
			int pstartX=0;
			for(int i=0;i<matrixs.length;i++){//����ÿһ������
				count=0;
				//��ԭԪ��
				int cols=matrixs[i][0].length;
				for(int j=0;j<cols;j+=skip){
					getArray(matrixs[i],j,0,skip, height, 
							pixelArray[count++],width, 
							pstartX,0,skip,height);	
				}
				pstartX+=skip;
			}
		}else{
			pixelArray=new int[colb][width*height];
			int pstartX=0;
			for(int i=0;i<matrixs.length-1;i++){//����ÿһ������
				count=0;
				//��ԭԪ��
				int cols=matrixs[i][0].length;
				for(int j=0;j<cols;j+=skip){
					getArray(matrixs[i],j,0,skip, height, 
							pixelArray[count++],width, 
							pstartX,0,skip,height);	
				}
				pstartX+=skip;
			}
			//���һ����Ƭ�������⴦��
			count=0;
			for(int i=0;i<matrixs[matrixs.length-1][0].length;i+=coll){
				getArray(matrixs[matrixs.length-1],i,0,
						coll, height, 
						pixelArray[count++],width, 
						pstartX,0,coll,height);
			}
		}
		return pixelArray;
	}
	public static int[] getRGB(int[] pixels,int imageWidth,int startX,int startY,int width,int height,int[] dest){
		if(dest==null)
			dest=new int[width*height];
		int count=0;
		for(int col=startX;col<width+startX;col++){
			for(int row=startY;row<height+startY;row++){
				dest[count++]=pixels[row*imageWidth+col];
			}
		}
		return dest;
	}
	
	
	/**
	 * �����ұ�����
	 */
	public static double[][] leftJoin(
			double[][] srcMatrix,
			double [][]joinMatrix,
			int startX,int startY,int jwidth,int jheight) {
		int srcWidth=srcMatrix[0].length;
		int joinWidth=joinMatrix[0].length;
		int width=srcWidth+joinWidth;
		int height=srcMatrix.length;
		double[][] result=new double[height][width];
		
		return result;
	}
	
	public static double[][] leftSeparate()
	{
		return null;
	}
	
	/**
	 * RGB����ת��
	 */
	public static int[] rgbTranspose(int[] pixels,int width,int height){
		int[] array=new int[width*height];
		for(int row=0;row<height;row++){
			for(int col=0;col<width;col++){
				array[col*height+row]=pixels[row*width+col];
			}
		}
		return array;
	}
	
	/**
	 * double��������ת��
	 * ת�ú�ʵ��width��height����
	 * Դ���鲻�ı�
	 */
	public static double[][] matrixTranspose(double[][] matrix,int width,int height){
		double[][] mat=new double[width][height];
		for(int row=0;row<height;row++){
			for(int col=0;col<width;col++){
				mat[col][row]=matrix[row][col];
			}
		}
		return mat;
	}
	
	/**
	 * �������RGB����Ԫ��
	 * ת�ú�ʵ��width��height����
	 * Դ���鲻�ı�
	 */
	public static void printRGB(int[] pixels,int width,int height){
		System.out.println("\tRGB===================RGB");
		for(int row=0;row<height;row++){
			for(int col=0;col<width;col++){
				System.out.print("\t"+pixels[row*width+col]);;
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * �����������Ԫ��
	 * @param pixels
	 * @param width
	 * @param height
	 */
	public static void printMatrix(double[][] matrix,int width,int height){
		System.out.println("\tMATRIX===================MATRIX");
		for(int row=0;row<height;row++){
			for(int col=0;col<width;col++){
				System.out.print("\t"+matrix[row][col]);;
			}
			System.out.println();
		}
		System.out.println();
	}
}
