package graduategesign;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Util;

import Jama.Matrix;
import Jama.SingularValueDecomposition;


/**
 * 一种简单的针对高纬度图像的SVD去噪
 * @author Administrator
 *
 */
public class TSVDFilter  extends SVDFilter{

	/**
	 * 图片分解后数据临时存放位置
	 */
	public static final String tensorFile="tensor.dat"; 
	/**
	 * 列分解
	 */
	public static final int TENSOR_TYPE_MODE1=1;
	/**
	 * 行分解
	 */
	public static final int TENSOR_TYPE_MODE2=2;
	/**
	 * 正面分解
	 */
	public static final int TENSOR_TYPE_MODE3=3;
	
	/**
	 * 初始化
	 */
	protected void initialize(BufferedImage srcImage){
		super.initialize(srcImage);
	}
	
	/**
	 * 
	 */
	public static final int TENSORT_SKIP=1;
	
	private int skip=TENSORT_SKIP;
	
	public void setSkip(int skip){
		this.skip=skip;
	}
	
	public void saveData(){
		
	}
	
	public void readData(){
		
	}
	
	/**
	 * 基于张量暗裂列分块的SVD去噪
	 * @param images 噪声图像集
	 * @param dests 结果图像集
	 * @return 结果图像集
	 */
	public List<BufferedImage> toMode1(List<BufferedImage> images,List<BufferedImage> dests){
		if(dests==null){
			dests=new ArrayList<BufferedImage>();
			for(BufferedImage image:images){
				dests.add(UtilDipose.createCompatibleDestImage(image, null));
			}
		}
		int width=images.get(0).getWidth();
		int height=images.get(0).getHeight();
		int channel=images.size();
		int[][]pixelArray=new int[channel][width*height];
		int count=0;
		for(BufferedImage image:images){
			UtilDipose.getRGB(image,pixelArray[count]);
			count++;
		}
		//System.out.println("chanel:"+channel+"count:"+count);
		double[][][] matrixs=UtilDipose.getSVDMODE1Array(pixelArray, width, height,this.skip);
		for(int i=0;i<matrixs.length;i++){
			Matrix m=new Matrix(matrixs[i]);
			boolean isWidthMatrix=matrixs[i][0].length>matrixs[i].length;
			if(isWidthMatrix){
				m=m.transpose();
			}
			SingularValueDecomposition svd=m.svd();
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
		    //System.out.println(index);
		     for(int j=index;j<svd.rank();j++){
		       s.set(j, j, 0);
		    }
		    Matrix destMatrix=svd.getU().times(s).times((svd.getV().transpose()));
		    if(isWidthMatrix)
		    	matrixs[i]=destMatrix.transpose().getArray();
		    else
		    	matrixs[i]=destMatrix.getArray();
		}
		pixelArray=UtilDipose.toMODE1Array(matrixs, width, height, skip);
		count=0;
		for(BufferedImage image:dests){
			UtilDipose.setRGB(image, pixelArray[count++]);
		}
		return dests;
	}
	
	public int getSkip() {
		return skip;
	}

	public List<BufferedImage> filter(List<BufferedImage> srcs,List<BufferedImage> dests){
		if(srcs==null)
			throw new NullPointerException("srcs is null");
		if(dests==null){
			dests=new ArrayList<BufferedImage>();
			for(BufferedImage image:srcs){
				dests.add(UtilDipose.createCompatibleDestImage(image, null));
			}
		}
		return dests;
	}
}
