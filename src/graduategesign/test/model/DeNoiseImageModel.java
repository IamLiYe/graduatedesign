package graduategesign.test.model;

import java.awt.image.BufferedImage;

/**
 * ����ͼ��ģ��
 * @author Administrator
 *
 */
public class DeNoiseImageModel {
	BufferedImage source;
	BufferedImage dest;
	
	public DeNoiseImageModel(BufferedImage source) {
		//super();
		this.source = source;
	}
	
	public DeNoiseImageModel(BufferedImage source, BufferedImage dest) {
		//super();
		this.source = source;
		this.dest = dest;
	}
}
