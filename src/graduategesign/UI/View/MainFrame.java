package graduategesign.UI.View;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import java.awt.Container;

import graduategesign.UI.util.GBC;

public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FunctionPanel functionPanel;
	public static ImagePanel imagePanel;
	
	public static ImagePanel getImagePanel(){
		return imagePanel;
	}
	
	private ObservePanel observePanel=new ObservePanel();
	private MessagePnanel messagePnanel=new MessagePnanel();
	private BufferedImage srcImage;
	private BufferedImage grayImage;
	
	/**
	 * 主布局
	 */
	private LayoutManager mLayout;
	
	/**
	 * 功能布局
	 */
	private LayoutManager fLayout;
	/**
	 * 
	 * @param framName
	 */
    public MainFrame(String framName){
    	super();
    	this.setTitle(framName);
		init();
    }
    
    /**
     * 
     */
    public MainFrame(){
    	super();
		init();
    }
    
    /*
     * 初始化
     */
    public void init(){
    	mLayout=new FlowLayout();
    	Container container=getContentPane();
    	container.setLayout(new GridBagLayout());
    	imagePanel=new ImagePanel();
    	container.add(imagePanel
    			,new GBC(0, 0)
    			.setFill(GBC.BOTH)
    			.setWeitht(100, 100));
    	container.add(observePanel
    			,new GBC(0, 1)
    			.setFill(GBC.HORIZONTAL)
    			.setWeitht(100, 0));
    	container.add(messagePnanel
    			,new GBC(0, 2)
    			.setFill(GBC.HORIZONTAL)
    			.setWeitht(100, 0));
    	functionPanel=new FunctionPanel();
    	container.add(functionPanel,new GBC(1,0,1,3)
    			.setFill(GBC.VERTICAL)
    			.setAnchor(GBC.CENTER)
    			.setWeitht(0, 100));
    	/*if(mLayout==container.getLayout()){
    		System.out.println("11");
    	}
    	else{
    		System.out.println("__");
    		System.out.println(getLayout().getClass());
    		System.out.println(mLayout.getClass());
    	}*/
    }
    
    
    public static void main(String[] args){ 	
    	JFrame frame=new MainFrame("hello world");
    	//frame.setLayout(new FlowLayout());
    	//System.out.println(frame.getLayout().getClass());
    	frame.setVisible(true);
    	frame.pack();
    	
    	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	//frame.validate();
    	//rame.pack();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
