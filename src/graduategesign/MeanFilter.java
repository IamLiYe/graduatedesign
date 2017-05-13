package graduategesign;
import java.awt.image.BufferedImage;

/**
 * 均值滤波
 * @author Administrator
 * @version 0.0.1 2017/4/14
 */
public class MeanFilter extends AbstractBufferedImageOp{
	public MeanFilter() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * 算术
	 */
	public static final int MEAN_TYPE_ARITHMETIC=0;
	
	/*
	 * 几何
	 */
	public static final int MEAN_TYPE_GEOMETRIC=1;
	
	/*
	 * 调和
	 */
	public static final int MEAN_TYPE_HARMONIC=2;
	
	private int meanType=MEAN_TYPE_ARITHMETIC;//默认几何平均
	
	private int kernel_size=3;//默认为
	
	public void setMeanType(int type){
		this.meanType=type;
	}
	
	/*
	 * 卷积核
	 */
	public static int[][] kernel={{1,1,1},{1,1,1},{1,1,1}};//卷积核
	
	/**
	 * 均值滤波处理
	 * @param srcImage
	 * @param destIMage
	 * @return
	 */
	public BufferedImage filter(BufferedImage srcImage,BufferedImage destImage){
		if(destImage==null){
			destImage=createCompatibleDestImage(srcImage, null);
		}
		int width=srcImage.getWidth();
		int height=srcImage.getHeight();
		
		int[] srcRGBArray=new int[width*height];
		int[] destRGBArray=new int[width*height];
		//获得操作像素矩阵
		getRGBArray(srcImage, 0, 0, width, height, srcRGBArray);
		int index=0;
		int index2=0;
		int[][] pixelMatrix=new int[3][kernel_size*kernel_size];
		
		
		
		int half_kernel_size=kernel_size/2;
		int start_x=half_kernel_size;
		int start_y=half_kernel_size;
		int mWidth=width-2*half_kernel_size;
		int mHeight=height-2*half_kernel_size;
		/*
		 * 边界元素不进行处理
		 */
		//上
		for(int y=0;y<half_kernel_size;y++){
			for(int x=0;x<width;x++){
				index=y*width+x;
				destRGBArray[index]=srcRGBArray[index];
			}
		}
		//下
		for(int y=height-half_kernel_size-1;y<height;y++){
			for(int x=0;x<width;x++){
				index=y*width+x;
				destRGBArray[index]=srcRGBArray[index];
			}
		}
		//左
		for(int y=half_kernel_size;y<height-half_kernel_size;y++){
			for(int x=0;x<half_kernel_size;x++){
				index=y*width+x;
				destRGBArray[index]=srcRGBArray[index];
			}
		}
		//右
		for(int y=half_kernel_size;y<height-half_kernel_size;y++){
			for(int x=width-kernel_size-1;x<width;x++){
				index=y*width+x;
				destRGBArray[index]=srcRGBArray[index];
			}
		}
		int rgb[];
		for(int y=start_y;y<=mHeight;y++){
			for(int x=start_x;x<=mWidth;x++){
				index=y*width+x;
				int count=0;
				for(int row=-half_kernel_size;row<=half_kernel_size;row++){
					for(int col=-half_kernel_size;col<=half_kernel_size;col++){
						index2=index+row*width+col;
						pixelMatrix[0][count]=(srcRGBArray[index2]>>16)&0xff;
						pixelMatrix[1][count]=(srcRGBArray[index2]>>8)&0xff;
						pixelMatrix[2][count]=(srcRGBArray[index2])&0xff;
						count++;
					}
				}
				/*
				 * 饱和度保持不变
				 */
				
				rgb=getValue(pixelMatrix);
			    int a=(srcRGBArray[index]>>24)&0xff;
			    destRGBArray[index]=(a<<24)|(rgb[0]<<16)|(rgb[1]<<8)|rgb[2];
			}
		}
		
		setRGBArray(destImage, 0, 0, width, height, destRGBArray);
		return destImage;
	}
	
	public int[] getValue(int[][] pixelMatrix){
		int []rgb=new int[3];
		double []sum={0,0,0};
		int size=pixelMatrix[0].length;
		double total=size;
		switch(this.meanType){
		case MEAN_TYPE_ARITHMETIC:
			for(int i=0;i<size;i++){
				sum[0]+=(double)pixelMatrix[0][i];
				sum[1]+=(double)pixelMatrix[1][i];
				sum[2]+=(double)pixelMatrix[2][i];
			}
			rgb[0]=(int)(sum[0]/total);
			rgb[1]=(int)(sum[1]/total);
			rgb[2]=(int)(sum[2]/total);
			break;
		case MEAN_TYPE_GEOMETRIC:
			sum[0]=sum[1]=sum[2]=1;
			for(int i=0;i<size;i++){
				sum[0]*=(double)pixelMatrix[0][i];
				sum[1]*=(double)pixelMatrix[1][i];
				sum[2]*=(double)pixelMatrix[2][i];
			}
			rgb[0]=(int)(Math.pow(sum[0], 1.0d/total));
			rgb[1]=(int)(Math.pow(sum[1], 1.0d/total));
			rgb[2]=(int)(Math.pow(sum[2], 1.0d/total));
			break;
		case MEAN_TYPE_HARMONIC:
			for(int i=0;i<size;i++){
				sum[0]+=(1.0d/(double)pixelMatrix[0][i]);
				sum[1]+=(1.0d/(double)pixelMatrix[0][i]);
				sum[2]+=(1.0d/(double)pixelMatrix[0][i]);
			}
			rgb[0]=(int)(total/sum[0]);
			rgb[1]=(int)(total/sum[1]);
			rgb[2]=(int)(total/sum[2]);
			break;
		default:
			break;
		}
		return rgb;
	}
}
