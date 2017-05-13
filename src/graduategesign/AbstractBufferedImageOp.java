package graduategesign;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;

import com.sun.glass.ui.TouchInputSupport;

/**
 * 
 * @author �
 * @version 0.1 2017/4/10
 * @see <a href="https://github.com/IamLiYe/mybook-java-imageprocess/blob/master/src/com/book
 * /chapter/four/AbstractBufferedImageOp.java"></a>
 */
public class AbstractBufferedImageOp implements BufferedImageOp{
	
	public static final int MAX_GRAY_VALUE=255;//���Ҷ�ֵ
	public static final int MIN_GRAY_VALUE=0;//��С�Ҷ�ֵ
	
	public static final double PI=3.141592653;
	
	public static final int COLOSPACE_TYPE_GRAY=0;
	public static final int COLOSPACE_TYPE_RGB=1;
	public static final int COLOSPACE_TYPE_ARGB=2;
	
	/*
	 * ͼ�����ɫ�ռ�
	 */
	private int colorType=COLOSPACE_TYPE_RGB;
    
	public void setColorType(int colorType){
		this.colorType=colorType;
	}
	
	public int getColorType(){
		return this.colorType;
	}
	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle2D getBounds2D(BufferedImage src) {
		// TODO Auto-generated method stub
		return new Rectangle(0,0,src.getWidth(),src.getHeight());
	}

	@Override
	public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
		// TODO Auto-generated method stub
		if(destCM==null){
			destCM=src.getColorModel();
		}
		return new BufferedImage(destCM, 
				destCM.createCompatibleWritableRaster(src.getWidth(),src.getHeight()),
						destCM.isAlphaPremultiplied(),
						null);
	}

	@Override
	public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
		// TODO Auto-generated method stub
		if(dstPt==null){
			dstPt=new Point2D.Double();
		}
		dstPt.setLocation(srcPt);
		return dstPt;
	}

	@Override
	public RenderingHints getRenderingHints() {
		// TODO Auto-generated method stub
		return null;
	}
    
    /**
     * �����ص�Ҷȼ���ȡֵ�̶���[MIN_GRAY_VALUE,MAX_GRAY_VALUE]
     * @param value ��ȡ������Ӧ����ȡ�õ�����ֵ 
     * @return Խ�紦�������ֵ
     */
	public static int clamp(int value){
		return value<MIN_GRAY_VALUE?MIN_GRAY_VALUE:
				(value>MAX_GRAY_VALUE?MAX_GRAY_VALUE:value);
	}
	
	/**
	 * ��RGB��HSLת��
	 * @param rgb[] red,green,blue
	 * @return HSL[] hue,saturation,lightness
	 */
	public static double[] rgbToHSL(int rgb[]){
		double min, max, dif, sum;
        double f1, f2;
        double h, s, l;
        // convert to HSL space
        min = rgb[0];
        if (rgb[1] < min) 
        	min = rgb[1];
        if (rgb[2] < min) 
        	min = rgb[2];
        max = rgb[0];
        f1 = 0.0;
        f2 = rgb[1] - rgb[2];
        if (rgb[1] > max) {
           max = rgb[1];
           f1 = 120.0;
           f2 = rgb[2] - rgb[0];
        }
        if (rgb[2] > max) {
           max = rgb[2];
           f1 = 240.0;
           f2 = rgb[0] - rgb[1];
        }
        dif = max - min;
        sum = max + min;
        l = 0.5 * sum;
        if (dif == 0) {
        	h = 0.0;
        	s = 0.0;
        } 
        else if(l < 127.5) {
        	s = 255.0 * dif / sum;
        }
        else {
        	s = 255.0 * dif / (510.0 - sum);
        }

        h = (f1 + 60.0 * f2 / dif);
        if (h < 0.0) { 
        	h += 360.0;
        }
        if (h >= 360.0) {
        	h -= 360.0;
        }
        
        return new double[]{h, s, l};
	}
	
	/**
	 * ��HSL��RGBת��
	 * @param hsl[0] hue ɫ��
	 * @param hsl[1] saturation ���Ͷ�
	 * @param hsl[2] lightness ����
	 * @return RGB��ɫ
	 */
	public static int[] hslToRGB(int hsl[]){
		double hue=hsl[0];
		double saturation=hsl[1];
		double lightness=hsl[2];
		double r=0,g=0,b=0;
		double q;
		if(lightness<0.5){
			q=lightness+lightness*saturation;
		}
		else{
			q=lightness+saturation-lightness*saturation;
		}
		double p=2*lightness-q;
		
		if(saturation==0){
			r=lightness;
			g=lightness;
			b=lightness;
		}
		else{
			r=toRGB(p, q, hue+120);
			g=toRGB(p, q, hue);
			b=toRGB(p, q, hue-120);
		}
		return new int[]{(int)(r*255),(int)(g*255),(int)(b*255)};
	}
	
	/**
	 * ɫ��ת��ΪRGB
	 * @param hue ɫ��
	 */
	public static double toRGB(double p,double q,double hue){
		if(hue>360){
			hue-=360;
		}
		if(hue<0){
			hue+=360;
		}
		if(hue<60){
			return (p+((q-p)*hue/60));
		}
		else if(hue<180){
			     return (q);
		    }else if(hue<240){
		    	return (p+((q-p)*(240-hue)/60));
		    }
		    else
                 return (p);
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
	 * @return
	 */
	public  int[] getRGBArray(BufferedImage image,int startX,int startY,int width,int height,int[] rgbArray){
		int type=image.getType();
		if(type==BufferedImage.TYPE_INT_ARGB||type==BufferedImage.TYPE_INT_RGB){
			return (int [])image.getRaster().getDataElements(startX,startY, width, height, rgbArray);
		}
		else
			return image.getRGB(startX,startY, width, height, rgbArray,0, width);
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
	public void setRGBArray(BufferedImage image,int startX,int startY,int width,int height,int[] rgbArray){
		int type=image.getType();
		if(type==BufferedImage.TYPE_INT_ARGB||type==BufferedImage.TYPE_INT_RGB){
			image.getRaster().setDataElements(startX,startY, width, height, rgbArray);
		}
		else
			image.setRGB(startX,startY, width, height, rgbArray,0, width);
	}
	
	
	/**
	 * 
	 */
	public BufferedImage getGrayImage(BufferedImage srcImage){
		ColorConvertOp filter=new ColorConvertOp(
				ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		return filter.filter(srcImage, null);
	}
	
}
