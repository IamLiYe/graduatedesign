package graduategesign.Array;

import java.util.HashMap;
import java.util.Map;

/**
 * ��ż�����ͼ����Ϣ
 * @author Administrator
 * @version 2017/5/17 0.0.1
 */
public class NoiseItem {
	/*
	 * �����������
	 */
	private int noizeType;
	/*
	 * ��Ϣ
	 */
	private String message;
	/*
	 * ������Ϣ
	 * ֵ
	 */
	public Map<String,String> paramMap=new HashMap<String,String>();
	
	/**
	 * ��ȡ������Ϣ
	 */
	public String getMessage(){
		this.message="����ͼ��";
		switch (noizeType) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		case 11:
			break;
		case 12:
			break;
		case 13:
			break;
		case 14:
			break;
		case 15:
			break;
		default:
			break;
		}
		return this.message;
	}
}
