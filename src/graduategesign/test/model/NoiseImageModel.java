package graduategesign.test.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import graduategesign.UtilDipose;

/**
 * @author Administrator
 *
 */
public class NoiseImageModel {
	private Long sourceId;
	private Long id;
	private BufferedImage image;
	private String name;
	private int noiseType;
	private double slatPercent=0;
	private double pepperPercent=0;
	private double gaussianFaction=0;
	private double poissonFactir=0;
	
	List<DeNoiseImageModel> deNoises=new ArrayList<DeNoiseImageModel>();
	
	
	public NoiseImageModel(Long sourceId, BufferedImage noise) {
		//super();
		this.sourceId = sourceId;
		this.image = noise;
	}
	public NoiseImageModel(Long sourceId) {
		//super();
		this.sourceId= sourceId;
		this.id=UtilDipose.generateID();
	}
	
	
	public void addDeNoise(){
		
	}
	
	public Long getSourceId() {
		return sourceId;
	}
	public BufferedImage getImage() {
		return image;
	}
	public List<DeNoiseImageModel> getDeNoises() {
		return deNoises;
	}
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public void setDeNoises(List<DeNoiseImageModel> deNoises) {
		this.deNoises = deNoises;
	}
	
	@Override
	public String toString() {
		return "NoiseImageModel [sourceId=" + sourceId + ", image=" + image + ", noiseType=" + noiseType
				+ ", slatPercent=" + slatPercent + ", pepperPercent=" + pepperPercent + ", gaussianFaction="
				+ gaussianFaction + ", poissonFactir=" + poissonFactir + ", deNoises=" + deNoises + "]";
	}
	
	
}
