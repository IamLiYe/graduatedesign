package graduategesign.test.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import graduategesign.AddNoisingFilter;
import graduategesign.UtilDipose;

public class SGImageModel {
	BufferedImage source;
	BufferedImage gray;
	Long id;
	List<NoiseImageModel> noises=new ArrayList<NoiseImageModel>();
	AddNoisingFilter noisingFilter=new AddNoisingFilter();
	public SGImageModel(BufferedImage source){
		this.source=source;
		this.gray=UtilDipose.getGrayImage(source, null);
		this.id=UtilDipose.generateID();
	}
	
	public SGImageModel(BufferedImage source,BufferedImage gray){
		this.source=source;
		this.gray=gray;
		this.id=UtilDipose.generateID();
	}

	public BufferedImage getSource() {
		return source;
	}

	public BufferedImage getGray() {
		return gray;
	}

	public List<NoiseImageModel> getNoises() {
		return noises;
	}

	public void setSource(BufferedImage source) {
		this.source = source;
		this.gray=UtilDipose.getGrayImage(source, null);
	}

	public void setGray(BufferedImage gray) {
		this.gray = gray;
	}

	public void setNoises(List<NoiseImageModel> noises) {
		this.noises = noises;
	}
	
	/**
	 * 
	 * @param noiseType
	 * @param param
	 * @param number
	 */
	public void addNoises(int noiseType,double[] params,int number){
		switch (params.length) {
		case 4:
		case 3:
		case 2:
		case 1:
			break;
		default:
			break;
		}
		noisingFilter.setNoiseType(noiseType);
	}
	
	public void addNoise(int NOISE_TYPE,double[] param){
		
	}
	
}
