package graduategesign;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.hxtt.sql.br;
import com.mysql.jdbc.Util;
import com.sun.jmx.snmp.SnmpMsg;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import sun.print.PSPrinterJob.PluginPrinter;

public class SVDFilter extends AbstractBufferedImageOp{
	
	private static final double DEFAULT_SINGULAR_PERCENT=0.8;
	private static final double DEFAULT_STEP_PERCENT=0.02;
	private static final int DEFAULT_SINGULAR_NUMBER=5;
	private static final double DEFAULT_STEP_NUMBER=1;
	/**
	 * 将中点以后的奇异值设置为0
	 */
	public static final int SVD_TYPE_CENTER=1;
	/**
	 * 将小于平均值的奇异值设置为0
	 */
	public static final int SVD_TYPE_AVERAGE=0;
	
	private static final String DEFAULT_PICTURE_PATH="C:\\Users\\Administrator\\Desktop\\sf.jpg";
	private static final String DEFAULT_SVAE_PATH="C:\\Users\\Administrator\\Desktop\\save.jpg";
	public static final int SVD_TYPE_NUMBER=1;
	public static final int SVD_TYPE_PERCENT=2;
	private double singularPercent=DEFAULT_SINGULAR_PERCENT;
	private int singularNumber=DEFAULT_SINGULAR_NUMBER;
	
	/*
	 * 处理源图像
	 */
	private BufferedImage srcImage;
	
	/*
	 * SVD分解后的参数
	 */
	/**
	 * 奇异值矩阵
	 */
	private Matrix sM;
	/**
	 * 左正交列矩阵
	 */
	private Matrix uM;
	/**
	 * 右正交列矩阵
	 */
	private Matrix vM;
	/**
	 * 矩阵特征值的秩
	 */
	private int rank;
	/**
	 * 矩阵特征值矩阵 
	 */
	private double[] singularValues;
	/**
	 * 由于使用JAMA的SVD分解无法处理矩阵宽度大于高度的情况
	 * 如果出现上面的情况，那么实际处理的矩阵就是srcImage的灰度矩阵的装置矩阵
	 */
	private boolean isWideMatix=false;
	/**
	 * 图像RGB数组
	 * 实际上处理的是灰度化的图像
	 * 则原图像的RED=GREEN=BLUE
	 */
	private int[] dataPixels;
	private double[][]dataMatrix;
	
	public SVDFilter(BufferedImage srcImage){
		initialize(srcImage);
	}
	public SVDFilter(){
		
	}
	
	/**
	 * 通过传入的参数，设置初始化数据
	 * @param srcImage 处理的带有噪声的源灰度图像
	 */
	protected void initialize(BufferedImage srcImage){
		//System.out.println("init");
		this.srcImage=srcImage;
		int width=srcImage.getWidth();
		int height=srcImage.getHeight();
		dataPixels=new int[width*height];
		dataMatrix=new double[height][width];
		/*
		 * 注意如果为宽图像这里是对转置矩阵矩形SVD分析
		 * 在重新恢复图像时必需再次进行转置操作
		 */
		dataPixels=getRGBArray(srcImage, 0, 0, width, height, dataPixels);
		dataMatrix=UtilDipose.toDoubleMatrix(dataPixels, width, height);
		Matrix matrix;
		if(width>height)
		{
			isWideMatix=true;
			matrix=new Matrix(dataMatrix).transpose();
		}
		else
		{
			matrix=new Matrix(dataMatrix);
		}
		SingularValueDecomposition svd=matrix.svd();
		this.sM=svd.getS();
		this.uM=svd.getU();
		this.singularValues=svd.getSingularValues();
		this.rank=svd.rank();
		this.vM=svd.getV().transpose();
	}
	public BufferedImage getSrcImage() {
		return srcImage;
	}
	public void setSrcImage(BufferedImage srcImage) {
		this.srcImage = srcImage;
	}

	protected int svdType=0;

	public void setSvdType(int svdType){
		this.svdType=svdType;
	}
	public int getSvdType(){
		return this.svdType;
	}
	public double getSingularPercent() {
		return singularPercent;
	}

	public int getSingularNumber() {
		return singularNumber;
	}


	public void setSingularPercent(double singularPercent) {
		this.singularPercent = singularPercent;
	}


	public void setSingularNumber(int singularNumber) {
		this.singularNumber = singularNumber;
	}


	public static void main(String[] args) { 

		 // create M-by-N matrix that doesn't have full rank
         int M = 8, N = 5;
         Matrix B = Matrix.random(5, 3);
         Matrix A = Matrix.random(M, N).times(B).times(B.transpose());
         System.out.print("A = ");
         A.print(9, 6);

         // compute the singular vallue decomposition
         System.out.println("A = U S V^T");
         System.out.println();
         SingularValueDecomposition s = A.svd();
         System.out.print("U = ");
         Matrix U = s.getU();
         U.print(9, 6);
         System.out.print("Sigma = ");
         Matrix S = s.getS();
         S.print(9, 6);
         System.out.print("V = ");
         Matrix V = s.getV();
         V.print(9, 6);
         System.out.println("rank = " + s.rank());
         System.out.println("condition number = " + s.cond());
         System.out.println("2-norm = " + s.norm2());

         // print out singular values
         System.out.print("singular values = ");
         Matrix svalues = new Matrix(s.getSingularValues(), 1);
         svalues.print(9, 6);
         test_3();
         //test_1();
	   }
	/**
	 * 使用svd进行图像处理
	 * @param src
	 * @param dest
	 * @param singularPercent 保留奇异值百分比
	 * @return 处理结果
	 */
	public BufferedImage filter(BufferedImage src,BufferedImage dest,double singularPercent){
		this.singularPercent=singularPercent;
		return filter(src, dest);
	}
	
	/**
	 * 使用svd进行图像处理
	 * @param src
	 * @param dest
	 * @param singularPercent 保留奇异值百分比
	 * @return 处理结果
	 */
	public BufferedImage filter(BufferedImage src,BufferedImage dest,int singularNumber){
		this.singularNumber=singularNumber;
		return filter(src, dest);
	}
	/**
	 * 使用SVD进行图像处理
	 * @param src
	 * @param dest
	 * @return 处理结果
	 */
	public BufferedImage filter(BufferedImage src,BufferedImage dest){
		if(dest==null){
			dest=createCompatibleDestImage(src, null);
		}
		/*
		 * 处理的图像是否已保存的原图像是否相同
		 * 不同，更新原图像
		 */
		if(this.srcImage!=src){
			initialize(src);
		}
		int width=src.getWidth();
		int height=src.getHeight();
//		int[] srcArray=new int[width*height];
//		int[] destArray;
//		getRGBArray(src, 0, 0, width, height, srcArray);
//		Matrix matrix=new Matrix(UtilDipose.toDoubleMatrix(srcArray, width, height));
//		SingularValueDecomposition svd=matrix.svd();
//		Matrix U=svd.getU();
//		Matrix S=svd.getS();
//		Matrix V=svd.getV();
		//取前aRank个奇异值
		int bRank=this.uM.rank();
		int aRank;
		switch (this.svdType) {
		case SVD_TYPE_NUMBER:
			aRank=this.singularNumber;
			break;
		case SVD_TYPE_PERCENT:
			aRank=(int)(bRank*singularPercent);
			break;
		default:
			double sum=0.0;
			for(double sg:singularValues){
				sum+=sg;
			}
			double avg=sum/(singularValues.length);
			//System.out.println(avg);
			int i=singularValues.length-1;
			while(i>0){
				if(singularValues[i]>=avg)
					break;
				i--;
			}
			aRank=i;
			break;
		}
		//System.out.println("before rank========"+bRank+"after rank========"+aRank);
		Matrix temp=this.sM.copy();
		for(int i=aRank;i<bRank;i++)
			temp.set(i, i, 0);
		//重新拼接图片
		Matrix matrix=uM.times(temp).times(vM);
		int []destArray;
		if(this.isWideMatix){
			destArray=UtilDipose.toIntegerArray(matrix.transpose().getArray(),width,height);
		}
		else{
			destArray=UtilDipose.toIntegerArray(matrix.getArray(),width,height);
		}
		setRGBArray(dest, 0, 0, width, height, destArray);
		return dest;
	}
	
	public static void test(){
		int []a={5,10,11,121,34,12,
				20,31,40,51,45,6,
				27,10,17,255,34,56,
				37,85,13,0,11,23,
				55,23,45,34,77,34};
		Matrix aM=new Matrix(UtilDipose.toDoubleMatrix(a, 6, 5));
		System.out.println("src");
		aM.print(9,6);
		System.out.println("src transpose");
		Matrix tM=aM.transpose();
		tM.print(9, 6);
		System.out.println("src transpose transpose");
		
		Matrix ttM=tM.transpose();
		ttM.print(9, 6);
		Matrix temp=tM.copy();
		temp.print(9, 6);
		if(temp==tM)
			System.out.println("Error!");
		SingularValueDecomposition svd=tM.svd();
		System.out.println("U=");
		svd.getU().print(9, 2);
		Matrix s=svd.getS();
		System.out.println("S =");
		s.print(9, 2);
		System.out.println("V=");
		svd.getV().transpose().print(9, 2);
		Matrix bM=svd.getU().times(s).times(svd.getV().transpose());
		System.out.println("SRC:");
		//bM.print(9, 2);
		s.set(4, 4, 0);
		System.out.print("S=");
		s.print(9, 2);
		Matrix xM=svd.getU().times(s).times(svd.getV().transpose());
		System.out.println("T=");
		Matrix td=xM.transpose();
		td.print(9, 2);
		int []b=UtilDipose.toIntegerArray(td.getArray(), 6,5);
		/*for(int i=0;i<6;i++){
			System.out.print((b[i]&0xff)+" ");
		}*/
	}
	
	public static void test_2(){
		int []a={5,10,11,121,34,12,
				20,31,40,51,45,6,
				27,10,17,255,34,56,
				37,85,13,0,11,23,
				55,23,45,34,77,34};
		int []b={5,10,11,121,34,12,
				20,31,40,51,45,6,
				27,10,17,255,34,56,
				37,85,13,0,11,23,
				55,23,45,34,77,34};
		int []c={5,10,11,121,34,12,
				20,31,40,51,45,6,
				27,10,17,255,34,56,
				37,85,13,0,11,23,
				55,233,45,34,77,34};
		int []d={5,110,11,121,34,132,
				20,31,40,51,45,6,
				27,310,17,255,34,56,
				37,85,13,0,11,23,
				55,23,45,34,77,34};
		int [][]pixelArray=new int[4][];
		pixelArray[0]=a;
		pixelArray[1]=b;
		pixelArray[2]=c;
		pixelArray[3]=d;
		double[][][]ddt=UtilDipose.getSVDMODE1Array(pixelArray,6,5,4);
		for(double[][] dt:ddt){
			Matrix matrix=new Matrix(dt);
			matrix.print(9, 2);
		}
	}
    
	public static void test_3(){
		double[][] mat={
				{3,4,5,6,7,8},
				{4,5,6,7,8,9},
				{5,6,7,8,9,10},
				{6,7,8,9,10,11}};
		double[][] mat1={
				{3,4,5,6,7,8},
				{4,5,6,7,8,9},
				{5,6,7,8,9,10},
				{6,7,8,9,10,11}};
		double[][] mat2={
				{3,4,5,6,7,8},
				{4,5,6,7,8,9},
				{5,6,7,8,9,10},
				{6,7,8,9,10,11}};
		double[][] mat3={
				{3,4,5,6,7,8},
				{4,5,6,7,8,9},
				{5,6,7,8,9,10},
				{6,7,8,9,10,11}};
		int[] a0={
				1,2,3,4,5,6,
				2,3,4,5,6,7,
				3,4,5,6,7,8,
				4,5,6,7,8,9,
				5,6,7,8,9,10
				};
		int[] a1={
				1,2,3,4,5,6,
				2,3,4,5,6,7,
				3,4,5,6,7,8,
				4,5,6,7,8,9,
				5,6,7,8,9,10
				};
		int[] a2={
				1,2,3,4,5,6,
				2,3,4,5,6,7,
				3,4,5,6,7,8,
				4,5,6,7,8,9,
				5,6,7,8,9,10
				};
		int[] a3={
				1,2,3,4,5,6,
				2,3,4,5,6,7,
				3,4,5,6,7,8,
				4,5,6,7,8,9,
				5,6,7,8,9,10
				};
		int[][] as=new int[4][];
		as[0]=a0;
		as[1]=a1;
		as[2]=a2;
		as[3]=a3;
		double[][][] mats;
		/*mats=UtilDipose.getSVDMODE1Array(as, 6, 5,5);
		for(double[][] m:mats){
			Matrix matrix=new Matrix(m);
			matrix.print(9, 2);
		}
		as=UtilDipose.toMODE1Array(mats, 6,5,5);
		for(int i=0;i<as.length;i++){
			Matrix matrix=new Matrix(UtilDipose.toDoubleMatrix(as[i],6,5));
			matrix.print(9, 2);
		}*/
		mats=UtilDipose.getSVDMODE2Array(as, 6, 5,1);
		for(double[][] m:mats){
			Matrix matrix=new Matrix(m);
			matrix.print(9, 2);
		}
		int[][]bs=UtilDipose.toMODE2Array(mats, 5, 6, 1);
		for(int[] b:bs){
			Matrix matrix=new Matrix(UtilDipose.toDoubleMatrix(b, 6, 5));
			matrix.print(9, 2);
		}
		/*int[] ss=UtilDipose.rgbTranspose(a1,6,5);
		double[][]ds=UtilDipose.matrixTranspose(mat1, 6,4);
		UtilDipose.printRGB(ss, 5, 6);
		UtilDipose.printMatrix(ds,4, 6);*/
		/*
		mats[0]=mat;
		mats[1]=mat1;
		mats[2]=mat2;
		mats[3]=mat3;
		int[][] pxiels=new int[4][4*6];
		pxiels=UtilDipose.toMODE1Array(mats, 6, 4,1);
		for(int i=0;i<pxiels.length;i++){
			Matrix matrix=new Matrix(UtilDipose.toDoubleMatrix(pxiels[i],6,4));
			matrix.print(9, 2);
		}*/
		//pxiels=UtilDipose.getArray(mat, 2, 0, 2, 4, pxiels, 6, 1, 0, 2,4);
		//Matrix matrix=new Matrix(UtilDipose.toDoubleMatrix(pxiels,6,4));
		//matrix.print(9, 2);
	}
	/*public static void test_1(){
		int[] pixels;
		double[][] matrix;
		try{
			File file=new File(DEFAULT_PICTURE_PATH);
			BufferedImage src=ImageIO.read(file);
			src=UtilDipose.getGrayImage(src,null);
			if(src!=null){
				pixels=new int[src.getWidth()*src.getHeight()];
				UtilDipose.getRGB(src,pixels);
				matrix=UtilDipose.toDoubleMatrix(pixels, src.getWidth(), src.getHeight());
				Matrix mt=new Matrix(matrix);
				SingularValueDecomposition svd=mt.svd();
				Matrix S=svd.getS();
				Matrix U=svd.getU();
				Matrix V=svd.getV();
				BufferedImage dest=UtilDipose.createCompatibleDestImage(src,null);
				Matrix sp=U.times(S).times(V.transpose());
				int destPxies[]=UtilDipose.toIntegerArray(sp.getArray(), src.getWidth(), src.getHeight());
				UtilDipose.setRGB(dest,destPxies);
				UtilDipose.savePictureJPG(dest,"hello1.jpg");
				System.out.println("rank = " + svd.rank());
		        System.out.println("condition number = " + svd.cond());
		        System.out.println("2-norm = " + svd.norm2());
		        System.out.print("singular values = ");
		        Matrix svalues = new Matrix(svd.getSingularValues(), 1);
		        svalues.print(9, 6);
			}
		}catch (IOException e) {
			// TODO: handle exception
		}
	}*/
	
	
	/**
	 * 
	 * @param src 待处理图像
	 * @param number 可变参数
	 * 参数数目		含义
	 *  1 			返回一个保留该图片前percent[0]（数量）奇异值的重组图像
	 *  2			返回一组保留该图片前percent[0]（数量）--percent[1]（数量），步距使用默认值0.1的奇异值的重组图像
	 *  3			返回一组保留该图片前percent[0]（数量）--percent[1]（数量），步距为percent[2]（数量）的奇异值的重组图像
	 *  @return 处理后的图像集合
	 */
	public Map<String,BufferedImage> filters(BufferedImage src,int...number){
		if(srcImage!=src)
		{
			initialize(src);
		}
		Map<String,BufferedImage> dests=new HashMap<String,BufferedImage>();
		int count=0;
		switch (number.length) {
		case 1:
			dests.put("svdPercent:["+count+"]"+number[0],lowRankbyNumber(src, null,number[0]));
			break;
		case 2:
			//处理越界
			if(number[0]>number[1]){
				break;
			}
			//统计数量
			count=0;
			for(int nowNumber=number[0];nowNumber<number[1];nowNumber+=DEFAULT_STEP_NUMBER){
				dests.put("svdPercent["+count+"]"+nowNumber,lowRankbyNumber(src, null,nowNumber));
				count++;
			}
			dests.put("svdPercent:"+number[1],lowRankbyNumber(src, null,number[1]));
			break;
		case 3:
			//处理越界
			if(number[0]>number[1]){
				break;
			}
			//统计数量
			count=0;
			for(int nowNumber=number[0];nowNumber<number[1];nowNumber+=number[2]){
				dests.put("svdPercent["+count+"]"+nowNumber,lowRankbyPercent(src, null,nowNumber));
				count++;
			}
			dests.put("svdPercent:"+number[1],lowRankbyNumber(src, null,number[1]));
			break;
		default:
			break;
		}
		return dests;
	}
	

	/**
	 * 
	 * @param src 待处理图像
	 * @param percent 可变参数
	 *  参数数目		含义
	 *  1 			返回一个保留该图片前percent[0]（比例）奇异值的重组图像
	 *  2			返回一组保留该图片前percent[0]（比例）--percent[1]（比例），步距使用默认值0.1的奇异值的重组图像
	 *  3			返回一组保留该图片前percent[0]（比例）--percent[1]（比例），步距为percent[2]（比例）的奇异值的重组图像
	 * @return 处理后的图像集合
	 */
	public Map<String,BufferedImage> filters(BufferedImage src,double...percent){
		if(srcImage!=src)
			initialize(src);
		int[]number=new int[percent.length];
		for(int i=0;i<percent.length;i++){
			number[i]=(int)percent[i]*uM.rank();
		}
		return filters(src, number);
	}
	
	public BufferedImage lowRankbyNumber(BufferedImage src,BufferedImage dest,int number){
		if(dest==null){
			dest=createCompatibleDestImage(src, null);
		}
		int aRank=number;
		int bRank=sM.rank();
		System.out.printf("before rank:%d afeter rank number:%d\n",bRank,aRank);
		Matrix temp=sM.copy();
		for(int i=aRank;i<bRank;i++)
			temp.set(i, i, 0);
		//重新拼接图片
		Matrix matrix=uM.times(temp).times(vM);
		int height=src.getHeight();
		int width=src.getWidth();
		int []destArray;
		if(this.isWideMatix){
			destArray=UtilDipose.toIntegerArray(matrix.transpose().getArray(),width,height);
		}
		else{
			destArray=UtilDipose.toIntegerArray(matrix.getArray(),width,height);
		}
		setRGBArray(dest, 0, 0, width, height, destArray);
		return dest;
	}
	
	private BufferedImage lowRankbyPercent(BufferedImage src,BufferedImage dest,double percent){
		return lowRankbyNumber(src, dest, (int)(percent*this.uM.rank()));
	}
	
	public int getRank(){
		if(srcImage!=null)
			return this.rank;
		else return -1;
	}
	
	/**
	 * 
	 */
	public BufferedImage filter_(BufferedImage src,BufferedImage dest){
        if(dest==null){
        	dest=UtilDipose.createCompatibleDestImage(src, null);
        }
        int height=src.getHeight();
        int width=src.getWidth();
        int[] dataPixes=UtilDipose.getRGB(src,null);
        //是否是宽图像
        boolean flag=width>height;
        Matrix matrix=new Matrix(UtilDipose.toDoubleMatrix(dataPixes, width, height));
        if(flag)
        	matrix=matrix.transpose();
        SingularValueDecomposition svd=matrix.svd();
        Matrix s=svd.getS();
        double[] singularValues=svd.getSingularValues();
        int index=0;
        switch (svdType) {
		case SVD_TYPE_CENTER:
			//Arrays.sort(singularValues);
			index=singularValues.length/2;
			break;
		case SVD_TYPE_AVERAGE:
			double sum=0.0;
			for(double singular:singularValues){
				sum+=singular;
			}
			double av=sum/singularValues.length;
			for(index=0;index<singularValues.length;index++)
			{
				if(singularValues[index]<av)
					break;
			}
			break;
		default:
			break;
		}
        System.out.println(index);
        for(int i=index;i<svd.rank();i++){
        	s.set(i, i, 0);
        }
        Matrix destMatrix=svd.getU().times(s).times((svd.getV().transpose()));
        if(flag)
        	destMatrix=destMatrix.transpose();
        int[] rgbArray=UtilDipose.toIntegerArray(destMatrix.getArray(), width, height);
        if(dest==null)
        	System.out.println("fuck");
        UtilDipose.setRGB(
        		dest,0,0,width,height,
        		rgbArray);
        return dest;
	}
}
