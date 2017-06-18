package graduategesign.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import graduategesign.UtilDipose;
import graduategesign.UI.Model.ImageModel;

public class TempTest {
	public static ImageModel sourceImage;
	public static ImageModel grayImage;
	
	public static List<ImageModel> noises=new ArrayList<ImageModel>();
	public static int noiseType=0;
	public static int noiseNumber=0;
	
	public static List<List<ImageModel>> denoises=new ArrayList<List<ImageModel>>();
	
	public static int denoiseNumber=0;
	public static int deNoiseType=0;
	
	public static PrintStream out=System.out;
	
	public static void clear(){
		
	}
	
	public static void setPSNRandEpi(){
		double[][] ns=new double[3][noises.size()];
		double[][][] ds=new double[denoises.size()][3][noises.size()];
        int count1=0;
        int count2=0;
        out.println("噪声集合");
		//处理噪声图像
		for(ImageModel noise:noises){
		    ns[0][count1]=UtilDipose.getPSNR(grayImage.getImage(), noise.getImage());
			noise.setPsnr(ns[0][count1]);
			
			ns[1][count1]=UtilDipose.getSNR(grayImage.getImage(), noise.getImage());
			noise.setSnr(ns[0][count1]);
			
			ns[2][count1]=UtilDipose.getEPI(grayImage.getImage(), noise.getImage());
			noise.setEpi(ns[0][count1]);
			
			count1++;
		}
		
		UtilDipose.printPE(ns[0], ns[1],ns[2], out);
		//处理图像的psnr
		count1=0;
		for(List<ImageModel> denoise:denoises){
			String str=denoise.get(0).getName();
			str=str.substring(0,str.indexOf('-')).trim();
			out.println(str);
			count2=0;
			for(ImageModel imageModel:denoise){	
				ds[count1][0][count2]=UtilDipose.getPSNR(grayImage.getImage(), imageModel.getImage());
				imageModel.setPsnr(ds[count1][0][count2]);
				
				ds[count1][1][count2]=UtilDipose.getSNR(grayImage.getImage(), imageModel.getImage());
				imageModel.setSnr(ds[count1][1][count2]);
				
				ds[count1][2][count2]=UtilDipose.getEPI(grayImage.getImage(), imageModel.getImage());
				imageModel.setEpi(ds[count1][2][count2]);
				count2++;
			}
			UtilDipose.printPE(ds[count1][0], ds[count1][1],ds[count1][2], out);
			
			count1++;
		}
	}
	
	public static void saveAllDatas(String fileDir){
		File file=new File(fileDir);
		if(file.isDirectory()){
			if(!file.exists())
				file.mkdirs();
		}
	}
	
}
