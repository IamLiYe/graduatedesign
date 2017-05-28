package graduategesign;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * ��ͳ���˲�
 * @author Administrator
 * @version 0.0.1 2017/4/14
 */
public class StatisticalFilter extends AbstractBufferedImageOp{
	public StatisticalFilter() {
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * �м�ֵ
	 */
	public static final int STATISTICAL_TYPE_HALF=0;
	
	/**
	 * �����Сֵ
	 */
	public static final int STATISTICAL_TYPE_MIN_MAX=1;
	
	/**
	 * ��Сֵ
	 */
	public static final int STATISTICAL_TYPE_MIN=2;
	
	/**
	 * ���ֵ
	 */
	public static final int STATISTICAL_TYPE_MAX=3;
	/**
	 * �м��
	 */
	public static final int STATISTICAL_TYPE_CENTER=4;
	/**
	 * ͳ������
	 */
	private int type=STATISTICAL_TYPE_HALF;//Ĭ�ϼ���ƽ��
	
	public void setType(int type){
		this.type=type;
	}
	
	/*
	 * �����
	 */
	public static int[][] kernel={{1,1,1},{1,1,1},{1,1,1}};//�����
	
	private int kernel_size=3;//Ĭ��Ϊ3
	
	public int getType() {
		return type;
	}
	public int getKernel_size() {
		return kernel_size;
	}
	public void setKernel_size(int kernel_size) {
		this.kernel_size = kernel_size;
	}
	
	public BufferedImage filter(BufferedImage srcImage,BufferedImage destImage){
		if(destImage==null){
			destImage=createCompatibleDestImage(srcImage, null);
		}
		int width=srcImage.getWidth();
		int height=srcImage.getHeight();
		
		int[] srcRGBArray=new int[width*height];
		int[] destRGBArray=new int[width*height];
		//��ò������ؾ���
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
		 * �߽�Ԫ�ز����д���
		 */
		//��
		for(int y=0;y<half_kernel_size;y++){
			for(int x=0;x<width;x++){
				index=y*width+x;
				destRGBArray[index]=srcRGBArray[index];
			}
		}
		//��
		for(int y=height-half_kernel_size-1;y<height;y++){
			for(int x=0;x<width;x++){
				index=y*width+x;
				destRGBArray[index]=srcRGBArray[index];
			}
		}
		//��
		for(int y=half_kernel_size;y<height-half_kernel_size;y++){
			for(int x=0;x<half_kernel_size;x++){
				index=y*width+x;
				destRGBArray[index]=srcRGBArray[index];
			}
		}
		//��
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
				 * ���Ͷȱ��ֲ���
				 */
				
				rgb=getValue(pixelMatrix);
			    int a=(srcRGBArray[index]>>24)&0xff;
			    destRGBArray[index]=(a<<24)|(rgb[0]<<16)|(rgb[1]<<8)|rgb[2];
			}
		}
		
		setRGBArray(destImage, 0, 0, width, height, destRGBArray);
		return destImage;
	}
	private int[] getValue(int[][] pixelMatrix){
		int[] rgb=new int[3];
		int[] rArray=pixelMatrix[0];
		int[] gArray=pixelMatrix[1];
		int[] bArray=pixelMatrix[2];
		//Ĭ����������(ASC)
		Arrays.sort(rArray);
		Arrays.sort(gArray);
		Arrays.sort(bArray);
		int index=kernel_size*kernel_size;
		switch(this.type){
		case STATISTICAL_TYPE_HALF:
			rgb[0]=(rArray[index-1]+rArray[0])/2;
			rgb[1]=(gArray[index-1]+gArray[0])/2;
			rgb[2]=(bArray[index-1]+bArray[0])/2;
			break;
		case STATISTICAL_TYPE_MIN:
			rgb[0]=rArray[0];
			rgb[1]=gArray[0];
			rgb[2]=bArray[0];
			break;
		case STATISTICAL_TYPE_MIN_MAX:
			rgb[0]=rArray[index-1]-rArray[0];
			rgb[1]=gArray[index-1]-gArray[0];
			rgb[2]=bArray[index-1]-bArray[0];
			break;
		case STATISTICAL_TYPE_MAX:
			rgb[0]=rArray[index-1];
			rgb[1]=gArray[index-1];
			rgb[2]=bArray[index-1];
			break;
		case STATISTICAL_TYPE_CENTER:
			rgb[0]=rArray[index/2];
			rgb[1]=gArray[index/2];
			rgb[2]=bArray[index/2];
			break;
		default:break;
		}
		return rgb;
	}
}
