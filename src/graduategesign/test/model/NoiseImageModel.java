package graduategesign.test.model;

import java.awt.image.BufferedImage;

/**
 * @author Administrator
 *
 */
public class NoiseImageModel {
	BufferedImage source;
	BufferedImage dest;
	public NoiseImageModel(BufferedImage source, BufferedImage dest) {
		//super();
		this.source = source;
		this.dest = dest;
	}
	public NoiseImageModel(BufferedImage source) {
		//super();
		this.source = source;
	}
}
