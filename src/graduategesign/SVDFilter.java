package graduategesign;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hxtt.b.s;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class SVDFilter extends AbstractBufferedImageOp{
	private static final double DEFAULT_PERCENT=0.2;
	private static final int DEFAULT_SINGULAR_NUMBER=5;
	
	private static final String DEFAULT_PICTURE_PATH="C:\\Users\\Administrator\\Desktop\\sf.jpg";
	private static final String DEFAULT_SVAE_PATH="C:\\Users\\Administrator\\Desktop\\save.jpg";
	private double singularPercent=DEFAULT_PERCENT;
	private int singularNumber=DEFAULT_SINGULAR_NUMBER;
	
	public static double getDefaultPercent() {
		return DEFAULT_PERCENT;
	}


	public static int getDefaultSingularNumber() {
		return DEFAULT_SINGULAR_NUMBER;
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
         test();
         test_1();
	   }
	
	public BufferedImage filter(BufferedImage src,BufferedImage dest){
		if(dest==null){
			dest=createCompatibleDestImage(src, null);
		}
		int width=src.getWidth();
		int height=src.getHeight();
		int[] srcArray=new int[width*height];
		int[] destArray;
		getRGBArray(src, 0, 0, width, height, srcArray);
		Matrix matrix=new Matrix(UtilDipose.toDoubleMatrix(srcArray, width, height));
		SingularValueDecomposition svd=matrix.svd();
		Matrix U=svd.getU();
		Matrix S=svd.getS();
		Matrix V=svd.getV();
		//取前DEFAULT_PERCENT奇异值
		int bRank=S.rank();
		int aRank=(int)(S.rank()*singularPercent);
		System.out.println("before rank========"+bRank+"after rank========"+aRank);
		for(int i=aRank;i<bRank;i++)
			S.set(i, i, 0);
		//重新拼接图片
		matrix=U.times(S).times(V.transpose());
		destArray=UtilDipose.toIntegerArray(matrix.getArray(), width, height);
		setRGBArray(dest, 0, 0, width, height, destArray);
		return dest;
	}
	
	public static void test(){
		int []a={5,10,11,121,
				20,31,40,51,
				27,10,17,255,
				37,85,13,0,
				55,23,45,34};
		Matrix aM=new Matrix(UtilDipose.toDoubleMatrix(a, 4, 5));
		aM.print(9,6);
		SingularValueDecomposition svd=aM.svd();
		System.out.println("U=");
		svd.getU().print(9, 2);
		Matrix s=svd.getS();
		System.out.println("S =");
		s.print(9, 2);
		System.out.println("S -=");
		s.set(3, 3, 0);
		s.print(9, 2);
		System.out.println("V=");
		svd.getV().transpose().print(9, 2);
		Matrix bM=svd.getU().times(s).times(svd.getV().transpose());
		bM.print(9, 2);
		int []b=UtilDipose.toIntegerArray(aM.getArray(), 4, 5);
		for(int i=0;i<5;i++){
			for(int j=0;j<4;j++){
				System.out.printf("%3d ",b[i*4+j]);
			}
			System.out.println();
		}
	}
	
	public static void test_1(){
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
	}
	
	 
}
