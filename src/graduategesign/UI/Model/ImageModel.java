package graduategesign.UI.Model;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import graduategesign.UI.util.GBC;
import javafx.scene.control.Label;

public class ImageModel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int IMAGE_WIDTH=100;
	private static final int IMAGE_HEIGHT=200;
	
	private static final int STRING_X=10;
	private static final int STRING_Y=5;
	
	private static final int IMAGE_X=0;
	private static final int IMAGE_Y=0;
	
	private static final int FONT_SIZE=16;
	private static final int FONT_TYPE=0;
	private static final String FONT_NAME="";
	
	public ImageModel(){
		super();
		init();
	}
	
	private int id;
	private String name;
	private String filePath;
	private String message;
	private BufferedImage image;
	JLabel nameLab;
	JLabel imageLab;
	private double psnr=0;
	private double snr=0;
	private double epi=1;
	
	
	public double getPsnr() {
		return psnr;
	}
	public double getSnr() {
		return snr;
	}
	public double getEpi() {
		return epi;
	}
	public void setPsnr(double psnr) {
		this.psnr = psnr;
	}
	public void setSnr(double snr) {
		this.snr = snr;
	}
	public void setEpi(double epi) {
		this.epi = epi;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getFilePath() {
		return filePath;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
		init();
	}
	
	public ImageModel(String name,BufferedImage image){
		this.image=image;
		this.name=name;
		init();
	}
	
	public void init(){
		nameLab=new JLabel(name);
		imageLab=new JLabel();
		imageLab.setIcon(new ImageIcon(image));
		setLayout(new GridBagLayout());
		add(imageLab, new GBC(0,0));
		add(nameLab, new GBC(0,1));
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
  /*  @Override
    protected void paintComponent(Graphics g) {
    	// TODO Auto-generated method stub
    	//super.paintComponent(g);
    	
    	Graphics2D g2d=(Graphics2D)g;
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
        int imageWidth=image.getWidth();
        int imageHeight=image.getHeight();
        g2d.drawImage(image,IMAGE_X,IMAGE_Y,imageWidth,imageHeight,null);
        AttributedString ats=new AttributedString(name);
        ats.addAttribute(TextAttribute.FONT,new Font(FONT_NAME,FONT_TYPE,FONT_SIZE));
        g2d.drawString(ats.getIterator()
        		,IMAGE_X+STRING_X, IMAGE_Y+imageHeight+STRING_Y+FONT_SIZE);
        g2d.dispose();
    }*/
}
