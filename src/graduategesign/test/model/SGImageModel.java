package graduategesign.test.model;

import java.awt.image.BufferedImage;

import graduategesign.UtilDipose;

public class SGImageModel {
	BufferedImage source;
	BufferedImage gray;
	
	public SGImageModel(BufferedImage source){
		this.source=source;
		this.gray=UtilDipose.getGrayImage(source, null);
	}
	
	public SGImageModel(BufferedImage source,BufferedImage gray){
		this.source=source;
		this.gray=gray;
	}
}
