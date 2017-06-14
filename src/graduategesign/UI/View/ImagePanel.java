package graduategesign.UI.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sun.prism.Graphics;

import graduategesign.UI.util.GBC;

public class ImagePanel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ImagePanel(){
		super();
		init();
	}
	
	/**
	 * ≥ı ºªØ
	 */
	public void init(){
		setBackground(Color.YELLOW);
//		setPreferredSize(getPreferredSize());
//		JScrollPane jsp=new JScrollPane(new JTextArea());
//		add(jsp);
		JPanel panel=new JPanel(new GridBagLayout());
		panel.setBackground(Color.ORANGE);
		//panel.setPreferredSize(new Dimension(800, 600));
		for(int i=0;i<100;i++){
			Label label=new Label(""+i);
			label.setBackground(Color.GRAY);
			panel.add(label,new GBC(0, i)
					.setAnchor(GBC.WEST));
		}
		//panel.add(new Label("1"));
		//panel.add(new Label("1"));
		//panel.add(new Label("1"));
		//panel.add(new Label("1"));
		setViewportView(panel);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setPreferredSize(getPreferredSize());
		revalidate();
	}
	
	public void paintCompont(Graphics g){
		
	}
	
	/*public static void main(){
		JFrame frame=new JFrame("hello world");
    	frame.setLayout(new FlowLayout());
    	System.out.println(frame.getLayout().getClass());
	}*/
}
