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
 * 基本处理类
 * 包括：
 * 1,彩色图像的灰度化，图像处理标准PNSR,SNR,EPI
 * 2,灰度图像转换
 * 3,double[][] & int[]
 * 4,字符串填充
 * 5,图像RGB获取和设置
 * 6,图像创建
 * 7，保存图像和图表
 * @author Administrator
 *
 */
public class UtilDipose {
	
	/*
	 * rgb图像转换为灰度图像后red、green、blue各自所占的百分比
	 */
	//red weight
	private static final int RED_WEIGHT=30;
	//green weight
	private static final int GREEN_WIGHT=59;
	//blue weight
	private static final int BlUE_WEIGHT=11;
	
	/**
	 * 每个灰度值所需的位数
	 */
	private static final int GRARY_BIT=8;
	
	/*
	 * 默认保存地点
	 */
	private static final String PICTURE_SAVE_PATH="D:\\chart\\svd\\pepperandslat\\";
	/**
	 * 将rgb彩色图像转换为灰度图像
	 * @param srcImage 源图像
	 * @param destImage 处理后的图像
	 * @return 处理后的灰度图像，如果destImage==null则根据srcImage创建一个新的BufferedImage对象赋值给destImage
	 * 并且将其返回
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
	 * 获取RGB或ARGB图像的像素一个简单方法
	 * 这里避免了直接使用BufferedImage.getRGB方法需要处理图像，从而带来的系统性能的损耗
	 * @param iamge 源图像
	 * @param startX 获取图像子矩阵的在原图像矩阵的横坐标起点
	 * @param startY 获取图像子矩阵的在原图像矩阵的纵坐标起点
	 * @param width 获取图像子矩阵的宽度
	 * @param height 获取图像子矩阵的高度
	 * @param rgbArray 获取的RGB数组
	 * @return 改变后的图像
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
	 * 获取图像的全rgb数组的全部像素
	 * @param image
	 * @param rgbArray
	 * @return
	 */
	public static int[] getRGB(BufferedImage image,int[] rgbArray){
		return getRGB(image, 0,0,image.getWidth(),image.getHeight(),rgbArray);
	}
	
	/**
	 * 设置RGB或ARGB图像的像素一个简单方法
	 * 这里避免了直接使用BufferedImage.getRGB方法需要处理图像，从而带来的系统性能的损耗
	 * @param iamge 源图像
	 * @param startX 设置图像子矩阵的在原图像矩阵的横坐标起点
	 * @param startY 设置图像子矩阵的在原图像矩阵的纵坐标起点
	 * @param width 设置图像子矩阵的宽度
	 * @param height 设置图像子矩阵的高度
	 * @param rgbArray设置的RGB数组
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
	 * 设置图像的rgb数组的全部像素
	 * @param image
	 * @param rgbArray
	 */
	public static void setRGB(BufferedImage image,int[] rgbArray){
		setRGB(image, 0, 0,image.getWidth(),image.getHeight(), rgbArray);
	}
	
	/**
	 * 使用ColorConvertOp实现图像灰度化处理
	 * @param srcImage
	 * @return
	 */
	public static BufferedImage getGrayImage(BufferedImage srcImage){
		ColorConvertOp filter=new ColorConvertOp(
				ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		return filter.filter(srcImage, null);
	}
	
	/**
	 * 根据原图像和颜色模式创建一个新的图像
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
	 * 从[0,number-1]个整数集合里随机选取size个整数存放在结果数组里，选取的整数不重复
	 * @param number 整数上限为number-1
	 * @param dest 存放结果
	 * @param size 选择的整数个数
	 * @param random 随机数发生器
	 * @return 存放结果数组
	 */
	public static int[] randomSort(int number,int size,int[] dest,Random random){
		
		//安全检测
		if(number<0||number<size){
			return null;
		}
		if(dest==null)
			dest=new int[size];
		if(random==null)
			random=new Random();
		//生成待整数数组集合
		int[]sort=new int[number];
		for(int i=0;i<number;i++)
			sort[i]=i;
		int index;
		for(int i=0;i<size;i++){
			//随机生成选取的坐标
			index=i+random.nextInt(number-i);
			
            //获取下标代表的整数（序列值）将其添加到结果数组中，并将当前所代表的序列添加到选取结合中去
			int temp=sort[i];
			sort[i]=sort[index];
			dest[i]=sort[index];
			sort[index]=temp;
			/*
			 * 可以不交换直接
			 * sort[index]=i
			 */
		}
		return dest;
	}

	/**
	 * 获取去噪后图像的峰值信噪比(Peak Signal to Noise Ratio)
	 * 这里默认输入图像为0位的灰度图
	 * @param srcImage 原图像
	 * @param destImage 处理后的图像
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
	 * 峰值信噪比
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
		 * 这里规定我们获取的图像为8位的灰度图
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
	 * 信噪比
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
	 * Signal to Noise(SNR) 性噪比
	 * SNR的定义方式多种
	 * 度量图像的相识度
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
		
		//水平
		int index1,index2;
		for(int row=0;row<height;row++){
			for(int col=0;col<width-1;col++){
				index1=row*width+col;
				index2=index1+1;
				srcSum+=Math.abs(srcArray[index1]-srcArray[index2]);
				destSum+=Math.abs(destArray[index1]-destArray[index2]);
			}
		}
		//垂直
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
	 * 将一维integer数组转换为二维double数组
	 */
	public static double[][] toDoubleMatrix(int[] srcArray,int width,int height){
		double[][] matrix=new double[height][width];
		for(int row=0;row<height;row++){
			for(int col=0;col<width;col++){
				int index=row*width+col;
				/*
				 * 这里我们规定处理的为8位灰度图像，所以只取低8位（即red通道的值）就可以了
				 */
				matrix[row][col]=(double)(srcArray[index]&0xff);
			}
		}
		return matrix;
	}
	
	/**
	 * 将二维double数组转换为一维integer数组
	 * 这里返回的是一个RGB三个通道相等的int数组
	 * @param destMatrix 待转换的数组
	 * @param width 数组宽
	 * @param height 数组高
	 * @return 
	 */
	public static int[] toIntegerArray(double[][] destMatrix,int width,int height){
		int[] array=new int[width*height];
		for(int row=0;row<height;row++){
			for(int col=0;col<width;col++){
				int index=row*width+col;
				/*
				 * 这里我们获取对应的灰度值，为了能够方便显示，将其它两个通道都填充为相同的值
				 * 注意gray要进行必要截断处理
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
	 * 保存以PNG格式保存图表
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
			//保存为PNG
			ChartUtilities.writeChartAsPNG(outPNG, chart, width, height);
			//保存为JPEG
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
	 * 字符串左填充为定长
	 * @param srcStr 待处理字符串
	 * @param filler 如果源字符串长度小于请求处理后的的长度，用该字符进行填充
	 * @param strlength 填充后或者截取后的的字符串长度
	 * @return 处理后的字符串
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
				//左填充
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
	 * 字符串右填充为定长
	 * @param srcStr 待处理字符串
	 * @param filler 如果源字符串长度小于请求处理后的的长度，用该字符进行填充
	 * @param strlength 填充后或者截取后的的字符串长度
	 * @return 处理后的字符串
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
				//右填充
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
	 * 根据一维int数组设置二维double数组的值
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
	 * 根据二维double数组获取指定位置的值存放在一维数组中
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
	 * 行切片,这里是将原像素转置，用列切片的方法做的
	 * @param pixelArray
	 * @param width
	 * @param height
	 * @param skip
	 * @return
	 */
	public static double[][][] getSVDMODE2Array(int[][]pixelArray,int width,int height,int skip)
	{
		int[][] pixelArray_=new int[pixelArray.length][];
		for(int i=0;i<pixelArray.length;i++){//转置，行列交换
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
		for(int i=0;i<pixelArray.length;i++){//转置，行列交换
			pixelArray[i]=UtilDipose.rgbTranspose(pixelArray_[i], width_, height_);
			//printRGB(pixelArray[i],height_,width_);
		}
		return pixelArray;
	}
	/**
	 * 以列对图像数组进行切片
	 * @param pxiels 图像数组int[][]类型
	 * @param width 每张图像的长度
	 * @param hegiht 每张图像的宽度
	 * @param skip 列的宽度
	 * @return 切片后的矩阵数组double[][][]类型
	 */
	public static double[][][] getSVDMODE1Array(int[][]pixelArray,int width,int height,int skip){
		int colb=width/skip;//前面colb个组为固定宽带skip
		int coll=width%skip;//最后剩下的为零散组,为零则不用理会
		double[][][] array;
		int[] pixels;
		//int count;
		if(coll==0){
			array=new double[colb][height][pixelArray.length*skip];
			/**
			 * 对于每一原始数据，它在转化的矩阵中的位置相同
			 */
			int startX=0;//用于确定切片后的位置
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
			 * 对于每一原始数据，它在转化的矩阵中的位置相同
			 */
			int startX=0;//用于确定切片后的位置
			int lstartX=0;//用于确定最后一片切片的位置
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
	 * 重组被列切片后的数组
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
			for(int i=0;i<matrixs.length;i++){//遍历每一个切面
				count=0;
				//还原元素
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
			for(int i=0;i<matrixs.length-1;i++){//遍历每一个切面
				count=0;
				//还原元素
				int cols=matrixs[i][0].length;
				for(int j=0;j<cols;j+=skip){
					getArray(matrixs[i],j,0,skip, height, 
							pixelArray[count++],width, 
							pstartX,0,skip,height);	
				}
				pstartX+=skip;
			}
			//最后一个切片进行特殊处理
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
	 * 矩阵右边连接
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
	 * RGB数组转置
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
	 * double矩阵数组转置
	 * 转置后实际width和height交换
	 * 源数组不改变
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
	 * 遍历输出RGB数组元素
	 * 转置后实际width和height交换
	 * 源数组不改变
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
	 * 遍历输出矩阵元素
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
