package graduategesign.UI.Model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImageModel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int IMAGE_WIDTH=100;
	private static final int IMAGE_HEIGHT=200;
	
	int id;
	String name;
	String filePath;
	BufferedImage image;
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
	}
	
	public ImageModel(String name,BufferedImage image){
		this.image=image;
		this.name=name;
	}
	
    @Override
    protected void paintComponent(Graphics g) {
    	// TODO Auto-generated method stub
    	super.paintComponent(g);
    	g.drawImage(image, 0, 0, null);
    	
    }
}
