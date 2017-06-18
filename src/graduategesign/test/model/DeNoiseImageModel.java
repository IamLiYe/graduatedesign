package graduategesign.test.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import graduategesign.DeNoiseType;
import graduategesign.UtilDipose;

/**
 * 处理图像模型
 * @author Administrator
 *
 */
public class DeNoiseImageModel {
	Long sourceLd;
	Long id;
	BufferedImage image;
	DeNoiseType deNoiseType;
	
	
	List<NoiseImageModel> denoises=new ArrayList<NoiseImageModel>();
	public DeNoiseImageModel(Long sourceId) {
		//super();
		this.sourceLd= sourceId;
		this.id=UtilDipose.generateID();
	}
	
	public DeNoiseImageModel(Long sourceId, BufferedImage image) {
		//super();
		this.sourceLd = sourceId;
		this.image = image;
		this.id=UtilDipose.generateID();
	}

	public Long getSourceLd() {
		return sourceLd;
	}

	public Long getId() {
		return id;
	}

	public BufferedImage getImage() {
		return image;
	}

	public DeNoiseType getDeNoiseType() {
		return deNoiseType;
	}

	public List<NoiseImageModel> getDenoises() {
		return denoises;
	}

	public void setSourceLd(Long sourceLd) {
		this.sourceLd = sourceLd;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setDeNoiseType(DeNoiseType deNoiseType) {
		this.deNoiseType = deNoiseType;
	}

	public void setDenoises(List<NoiseImageModel> denoises) {
		this.denoises = denoises;
	}
	
	
}
