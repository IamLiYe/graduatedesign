package graduategesign;
import java.awt.image.BufferedImage;
import java.util.Random;


/**
 * 添加噪声
 * 0 椒盐噪声
 * 1 盐噪声
 * 2 椒噪声
 * 4 高斯噪声
 * 8 泊松噪声
 * @author 李波
 * @version 0.0.2 2017/5/6
 * @see <a href="http://piperzero.iteye.com/blog/1764541">图像处理之添加高斯与泊松噪声</a>
 */
public class AddNoisingFilter extends AbstractBufferedImageOp {
	public final static double PMEAN_FACTOR = 2.0;
	public final static double GMEAN_FACTOR = 0.0;
	public final static double NOISE_FACTOR = 25;
	public final static int NOISE_TYPE_SALTANDPEPPER=0;
	public final static int NOISE_TYPE_SALT=1;
	public final static int NOISE_TYPE_PEPPER=2;
	public final static int NOISE_TYPE_GAUSSION = 4;
	public final static int NOISE_TYPE_POISSON =8;
	
	//默认椒盐噪声比例
	public final static double DEFALUT_SP_PERCENT=0.01;
	
	private double noiseFactor =NOISE_FACTOR;
	
	private double gMeanFactor=GMEAN_FACTOR;
	
	public void setGMeanFactor(double gMeanFactor){
		this.gMeanFactor=gMeanFactor;
	}
	
	private double pMeanFactor=PMEAN_FACTOR;
	
	public void setPMeanFacor(double pMeanFactor){
		this.pMeanFactor=pMeanFactor;
	}
	/*
	 * 椒噪声比例
	 */
	private double saltPercent=0.0;
	/*
	 * 盐噪声比例
	 */
	private double pepperPercent=0.0;
	/*
	 * 椒盐噪声总比例
	 */
	private double salt_pepperPercent=0.0;

	public void setSaltPercent(double slatPercent) {
		this.saltPercent = slatPercent;
	}

	public void setPepperPercent(double pepperPercent) {
		this.pepperPercent = pepperPercent;
	}

	public void setSalt_pepperPercent(double slat_peperPercent) {
		this.salt_pepperPercent = slat_peperPercent;
	}

	public static void setHasSpare(boolean hasSpare) {
		AddNoisingFilter.hasSpare = hasSpare;
	}

	public void setNosizeFactor(int noiseFactor) {
		this.noiseFactor = noiseFactor;
	}

	private int noiseType = NOISE_TYPE_POISSON; // 默认泊松分布

	public void setNoiseType(int noiseType) {
		this.noiseType = noiseType;	
	}

	/**
	 * 空构造函数
	 */
	public AddNoisingFilter() {

	}

	/**
	 * 构造函数
	 * 
	 * @param nosizeFactor
	 *            阶数
	 * @param nosizeType
	 *            噪声类型
	 */
	public AddNoisingFilter(double noiseFactor, int noiseType) {
		this.noiseFactor = noiseFactor;
		this.noiseType = noiseType;
	}

	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		int width = src.getWidth();
		int height = src.getHeight();
		Random random = new Random();
		if (dest == null) {
			dest = createCompatibleDestImage(src, null);
		}
		int srcArray[] = new int[width * height];
		int destArray[] = new int[width * height];
		UtilDipose.getRGB(src, srcArray);
		addNoise(srcArray, destArray, random);
		//修改处理结果
		setRGBArray(dest, 0, 0, width, height, destArray);
		return dest;
	}
	

	/**
	 * 添加泊松噪声
	 */
	private int addPNoise(int pixel, Random random) {
		// initial
		double l = Math.exp(-noiseFactor * pMeanFactor);
		int k = 0;
		double p = 1;
		do {
			k++;
			// Generate uniform random number u in [0,1] and let p ← p × u.
			p *= random.nextDouble();
		} while (p >= l);
		double retValue = Math.max((pixel + (k - 1) /pMeanFactor - noiseFactor), 0);
		return clamp((int) retValue);
	}

	/**
	 * 添加高斯噪声
	 * 
	 * @param pixel
	 * @param random
	 * @return
	 */
	private int addGNoise(int pixel, Random random) {
		int value, ran;
		for (;;) {
			ran = (int) Math.round(generateGaussianNoise(random) * noiseFactor+gMeanFactor);
			value = pixel + ran;
			//
			if (value >= 0 && value <= 255)
				break;

		}
		return value;
	}

	/**
	 * 这里的3个变量时为了生成高斯分布值减少重复运算
	 * 一次可以获得两个随机量
	 */
	private static boolean hasSpare=false;
	private static double rand1,rand2;
    private static final int MAX_RANDOM=(0x1<<31)-1;
    private static final double TWO_PI=2*Math.PI;
	/**
	 * 生成服从标准高斯分布的随机变量
	 * @see http://blog.csdn.net/lichengyu/article/details/31829787
	 * @return
	 */
	private double generateGaussianNoise(Random random){
		if(hasSpare){
			hasSpare=false;
			return rand1*Math.sin(rand2);
		}
		hasSpare=true;
		/*
		 * 生成[0,1]之间的随机数
		 */
		rand1=(double)random.nextInt(MAX_RANDOM)/(double)(MAX_RANDOM-1);
		rand2=(double)random.nextInt(MAX_RANDOM)/(double)(MAX_RANDOM-1)*TWO_PI;
		if(rand1<1e-10)
			rand1=1e-10;
		rand1=Math.sqrt(-2*Math.log(rand1));
        return rand1*Math.cos(rand2);
	}
	
	
	/**
	 * 添加椒和盐两种噪声
	 * 
	 */
	private void addSaltAndPepperNoise(int[]srcArray,int[] destArray,Random random){
		System.out.println("SlatAndPepper");
		int spn=(int)(srcArray.length*salt_pepperPercent);
		int[] randomIndexs=UtilDipose.randomSort(srcArray.length, spn, null, random);
		for(int i=0;i<spn;i++){
			if(random.nextInt(2)==0){
				//添加盐噪声
				destArray[randomIndexs[i]]=srcArray[randomIndexs[i]]|0xffffff;
			}else{
				//添加椒噪声
				destArray[randomIndexs[i]]=srcArray[randomIndexs[i]]&0xff000000;
			}
		}
	}

	/**
	 * 只添加盐噪声
	 * @param srcArray
	 * @param destArray
	 * @param random
	 */
	private void addSaltNoise(int[]srcArray,int[] destArray,Random random){
		System.out.println("Salt");
		int sn=(int)(srcArray.length*saltPercent);
		int[] randomIndexs=UtilDipose.randomSort(srcArray.length, sn, null, random);
		for(int i=0;i<sn;i++){
			destArray[randomIndexs[i]]=srcArray[randomIndexs[i]]|0xffffff;
		}
	}
	
	/**
	 * 只添加椒噪声
	 * @param srcArray
	 * @param destArray
	 * @param random
	 */
	private void addPepperNoise(int[]srcArray,int[] destArray,Random random){
		System.out.println("Pepper");
		int pn=(int)(srcArray.length*pepperPercent);
		int[] randomIndexs=UtilDipose.randomSort(srcArray.length, pn, null, random);
		for(int i=0;i<pn;i++){
			destArray[randomIndexs[i]]=srcArray[randomIndexs[i]]&0xff000000;
		}
	}
	
	/**
	 * 分别添加指定比例的椒、盐噪声
	 * @param srcArray
	 * @param destArray
	 * @param random
	 */
	private void addSPNoise(int[]srcArray,int[] destArray,Random random){
		System.out.println("SP");
		int sn=(int)(srcArray.length*saltPercent);
		int pn=(int)(srcArray.length*pepperPercent);
		int spn=sn+pn;
		int[] randomIndexs=UtilDipose.randomSort(srcArray.length, spn, null, random);
		//添加盐噪声
		for(int i=0;i<sn;i++){
			destArray[randomIndexs[i]]=srcArray[randomIndexs[i]]|0xffffff;
		}
		//添加椒噪声
		for(int i=sn;i<spn;i++){
			destArray[randomIndexs[i]]=srcArray[randomIndexs[i]]&0xff000000;
		}
	}
	/**
	 * 对灰度图像添加高斯噪声
	 */
	private void addGaussianToGray(int[]srcArray,int[] destArray,Random random){
		for(int i=0;i<srcArray.length;i++){
			int gray=srcArray[i]&0xff;
			gray=addGNoise(gray, random);
			destArray[i]=(srcArray[i]&0xff000000)|(gray<<16)|(gray<<8)|gray;
		}
	}
	
	/**
	 * 对RGB图像添加高斯噪声
	 */
	private void addGaussianToRGB(int[]srcArray,int[]destArray,Random random){
		System.out.println("GaussianRGB");
		for(int i=0;i<srcArray.length;i++){
			int r=srcArray[i]>>16&0xff;
			int g=srcArray[i]>>8&0xff;
			int b=srcArray[i]&0xff;
			r=addGNoise(r, random);
		    g=addGNoise(g, random);
		    b=addGNoise(b, random);
		    //System.out.printf("r%3d g%3d b%3d\n e:%d", r,g,b,(srcArray[i]&0xff000000)|(r<<16)|(b<<8)|b);
			destArray[i]=(srcArray[i]&0xff000000)|(r<<16)|(b<<8)|b;
		}
	}
	
	/**
	 * 对灰度图像添加泊松噪声
	 */
	private void addPoissonToGray(int[]srcArray,int[] destArray,Random random){
		System.out.println("PoissonGRAY");
		for(int i=0;i<srcArray.length;i++){
			int gray=srcArray[i]&0xff;
			//System.out.printf("before: %3d",gray);
			gray=addPNoise(gray, random);
			//System.out.printf("after: %3d\n",gray);
			destArray[i]=(srcArray[i]&0xff000000)|(gray<<16)|(gray<<8)|gray;
		}
	}
	
	/**
	 * 对RGB图像添加泊松噪声
	 */
	private void addPoissonToRGB(int[]srcArray,int[] destArray,Random random){
		System.out.println("PoissonRGB");
		for(int i=0;i<srcArray.length;i++){
			int r=srcArray[i]>>16&0xff;
			int g=srcArray[i]>>8&0xff;
			int b=srcArray[i]&0xff;
			r=addPNoise(r, random);
		    g=addPNoise(g, random);
		    b=addPNoise(b, random);
			destArray[i]=(srcArray[i]&0xff000000)|(r<<16)|(b<<8)|b;
		}
	}
	
	/**
	 * 根据噪声类型添加噪声
	 * @param srcArray
	 * @param destArray
	 * @param random
	 */
	public void addNoise(int[]srcArray,int[] destArray,Random random){
		for(int i=0;i<srcArray.length;i++)
			destArray[i]=srcArray[i];
		switch(this.noiseType){
		case 0:
			addSaltAndPepperNoise(srcArray, destArray, random);
			break;
		case 1:
			addSaltNoise(srcArray, destArray, random);
			break;
		case 2:
			addPepperNoise(srcArray, destArray, random);
			break;
		case 3:
			addSPNoise(srcArray, destArray, random);
			break;
		case 4:
			if(super.getColorType()==COLOSPACE_TYPE_RGB||super.getColorType()==COLOSPACE_TYPE_ARGB){
				addGaussianToRGB(srcArray, destArray, random);
			}
			else{
				addGaussianToGray(srcArray, destArray, random);
			}
			break;
		case 5:
			addSaltNoise(srcArray, destArray, random);
			if(super.getColorType()==COLOSPACE_TYPE_RGB||super.getColorType()==COLOSPACE_TYPE_ARGB){
				addGaussianToRGB(srcArray, destArray, random);
			}
			else{
				addGaussianToGray(srcArray, destArray, random);
			}
			break;
		case 6:
			addPepperNoise(srcArray, destArray, random);
			if(super.getColorType()==COLOSPACE_TYPE_RGB||super.getColorType()==COLOSPACE_TYPE_ARGB){
				addGaussianToRGB(srcArray, destArray, random);
			}
			else{
				addGaussianToGray(srcArray, destArray, random);
			}
			break;
		case 7:
			addSPNoise(srcArray, destArray, random);
			if(super.getColorType()==COLOSPACE_TYPE_RGB||super.getColorType()==COLOSPACE_TYPE_ARGB){
				addGaussianToRGB(srcArray, destArray, random);
			}
			else{
				addGaussianToGray(srcArray, destArray, random);
			}
			break;
		case 8:
			if(super.getColorType()==COLOSPACE_TYPE_RGB||super.getColorType()==COLOSPACE_TYPE_ARGB){
				addPoissonToRGB(srcArray, destArray, random);
			}
			else{
				addPoissonToGray(srcArray, destArray, random);
			}
			break;
		case 9:
			addSaltNoise(srcArray, destArray, random);
			if(super.getColorType()==COLOSPACE_TYPE_RGB||super.getColorType()==COLOSPACE_TYPE_ARGB){
				addPoissonToRGB(srcArray, destArray, random);
			}
			else{
				addPoissonToGray(srcArray, destArray, random);
			}
			break;
		case 10:
			addPepperNoise(srcArray, destArray, random);
			if(super.getColorType()==COLOSPACE_TYPE_RGB||super.getColorType()==COLOSPACE_TYPE_ARGB){
				addPoissonToRGB(srcArray, destArray, random);
			}
			else{
				addPoissonToGray(srcArray, destArray, random);
			}
			break;
		case 11:
			addSPNoise(srcArray, destArray, random);
			if(super.getColorType()==COLOSPACE_TYPE_RGB||super.getColorType()==COLOSPACE_TYPE_ARGB){
				addPoissonToRGB(srcArray, destArray, random);
			}
			else{
				addPoissonToGray(srcArray, destArray, random);
			}
			break;
		case 12:
			addSaltAndPepperNoise(srcArray, destArray, random);
			if(super.getColorType()==COLOSPACE_TYPE_RGB||super.getColorType()==COLOSPACE_TYPE_ARGB){
				addGaussianToRGB(srcArray, destArray, random);
				addPoissonToRGB(srcArray, destArray, random);
			}
			else{
				addGaussianToGray(srcArray, destArray, random);
				addPoissonToGray(srcArray, destArray, random);
			}
			break;
		case 13:
			if(super.getColorType()==COLOSPACE_TYPE_RGB||super.getColorType()==COLOSPACE_TYPE_ARGB){
				addGaussianToRGB(srcArray, destArray, random);
				addPoissonToRGB(srcArray, destArray, random);
			}
			else{
				addGaussianToGray(srcArray, destArray, random);
				addPoissonToGray(srcArray, destArray, random);
			}
			break;
		case 14:
			addPepperNoise(srcArray, destArray, random);
			if(super.getColorType()==COLOSPACE_TYPE_RGB||super.getColorType()==COLOSPACE_TYPE_ARGB){
				addGaussianToRGB(srcArray, destArray, random);
				addPoissonToRGB(srcArray, destArray, random);
			}
			else{
				addGaussianToGray(srcArray, destArray, random);
				addPoissonToGray(srcArray, destArray, random);
			}
			break;
		case 15:
			addSPNoise(srcArray, destArray, random);
			if(super.getColorType()==COLOSPACE_TYPE_RGB||super.getColorType()==COLOSPACE_TYPE_ARGB){
				addGaussianToRGB(srcArray, destArray, random);
				addPoissonToRGB(srcArray, destArray, random);
			}
			else{
				addGaussianToGray(srcArray, destArray, random);
				addPoissonToGray(srcArray, destArray, random);
			}
			break;
		}
	}
}
