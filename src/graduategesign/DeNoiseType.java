package graduategesign;

public enum DeNoiseType {
	STATISTICAL_HALF(1),
	STATISTICAL_CENTER(2),
	STATISTICAL_MIN_MAX(4),
	MEAN_ARITHMETIC(8),
	MEAN_GEOMETRIC(16),
	MEAN_MEN_HARMONIC(32),
	SVD(64),
	TSVD(128);
	
	private int value; 
	
	private DeNoiseType(int value){
		this.value=value;
	}
}
