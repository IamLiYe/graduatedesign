package graduategesign;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar; 
import java.util.Date;

import com.mysql.jdbc.Util;

/**
 * ��������
 * @author Administrator
 * ��ʵ����...
 */

public class TestFlow {
	
	private static final String saveBasePath=System.getProperty("user.dir");
	private static final int TEST_ID_BITS=24;
	private static final String SRC_FILE_NAME="src";
	private static final String GRAY_FILE_NAME="gray";
	private String testId;
	private String pSavePath;	
	private String tSavePath;
	private FileOutputStream testStream;
	/*
	 * ԭͼ��
	 */
	private String srcName=SRC_FILE_NAME;
	private BufferedImage srcImage;
	private String srcMessage;
	/*
	 * ����ͼ��
	 */
	private String grayName=GRAY_FILE_NAME;
	private BufferedImage grayImage;
	private String grayMessage;
	/*
	 * ����ͼ��
	 */
	private AddNoisingFilter addNoisingFilter=new AddNoisingFilter();
	/*
	 * ��ֵ�˲�
	 */
	private MeanFilter meanFilter=new MeanFilter();
	
	/*
	 * ���캯��
	 * @param srcImage ԭͼ��
	 */
	public TestFlow(BufferedImage srcImage){
		intialize(srcImage);
	}
	public String getTestId() {
		return testId;
	}

	public String getpSavePath() {
		return pSavePath;
	}

	public String gettSavePath() {
		return tSavePath;
	}

	public FileOutputStream getTestStream() {
		return testStream;
	}

	public String getSrcName() {
		return srcName;
	}

	public BufferedImage getSrcImage() {
		return srcImage;
	}

	public String getSrcMessage() {
		return srcMessage;
	}

	public String getGrayName() {
		return grayName;
	}

	public BufferedImage getGrayImage() {
		return grayImage;
	}

	public String getGrayMessage() {
		return grayMessage;
	}

	public AddNoisingFilter getAddNoisingFilter() {
		return addNoisingFilter;
	}

	public MeanFilter getMeanFilter() {
		return meanFilter;
	}

	public StatisticalFilter getStatisticalFilter() {
		return statisticalFilter;
	}

	public SVDFilter getSvdFilter() {
		return svdFilter;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public void setpSavePath(String pSavePath) {
		this.pSavePath = pSavePath;
	}

	public void settSavePath(String tSavePath) {
		this.tSavePath = tSavePath;
	}

	public void setTestStream(FileOutputStream testStream) {
		this.testStream = testStream;
	}

	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}

	public void setSrcImage(BufferedImage srcImage) {
		this.srcImage = srcImage;
	}

	public void setSrcMessage(String srcMessage) {
		this.srcMessage = srcMessage;
	}

	public void setGrayName(String grayName) {
		this.grayName = grayName;
	}

	public void setGrayImage(BufferedImage grayImage) {
		this.grayImage = grayImage;
	}

	public void setGrayMessage(String grayMessage) {
		this.grayMessage = grayMessage;
	}

	public void setAddNoisingFilter(AddNoisingFilter addNoisingFilter) {
		this.addNoisingFilter = addNoisingFilter;
	}

	public void setMeanFilter(MeanFilter meanFilter) {
		this.meanFilter = meanFilter;
	}

	public void setStatisticalFilter(StatisticalFilter statisticalFilter) {
		this.statisticalFilter = statisticalFilter;
	}

	public void setSvdFilter(SVDFilter svdFilter) {
		this.svdFilter = svdFilter;
	}

	/*
	 * ͳ���˲�
	 */
	private StatisticalFilter statisticalFilter=new StatisticalFilter();
	/*
	 * SVD�˲�
	 */
	private SVDFilter svdFilter=new SVDFilter();
	
	public void addNoise(int noiseType,double...parameter){
		addNoisingFilter.setNoiseType(noiseType);
		switch (noiseType) {
		case 0:
			this.addNoisingFilter.setSalt_pepperPercent(parameter[0]);
			break;
		default:
			break;
		}
	}
	
	private void intialize(BufferedImage srcImage){
		testId=UtilDipose.lestPad(new Date().getTime()+"",'0',TEST_ID_BITS); 
		pSavePath=saveBasePath+"/"+testId+"/iamges";
		tSavePath=saveBasePath+"/"+testId+"/testconfig.txt";
		File pFile=new File(pSavePath);
		if(!pFile.exists()){ 
			pFile.mkdirs();
		}
		//����Դͼ��
		this.srcImage=srcImage;
		UtilDipose.savePictureJPG(srcImage, srcName);
		//��ȡ�Ҷ�ͼ���ұ���
		this.grayImage=UtilDipose.getGrayImage(srcImage, null);
		UtilDipose.savePictureJPG(srcImage, grayName);
	}
}
