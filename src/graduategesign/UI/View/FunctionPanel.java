package graduategesign.UI.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import graduategesign.UI.util.GBC;

public class FunctionPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HeadPanel headPanel;
	private AddNoisePanel addNoisePanel;
	private DeNoisePanel deNoisePanel;
	private ShowPanel showPanel;
	
	private static int WIDTH=200;
	private static int HEIGHT=0;
	private LayoutManager layout;
	
	public FunctionPanel(){
		super();
		init();
		//this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
	}
	
	/**
	 * ≥ı ºªØ
	 */
	public void init(){
		setBackground(Color.black);
		layout=new GridBagLayout();
		headPanel=new HeadPanel();
		addNoisePanel=new AddNoisePanel();
		deNoisePanel=new DeNoisePanel();
		showPanel=new ShowPanel();
		setLayout(layout);
		add(headPanel,new GBC(0, 0)
				/*.setAnchor(GBC.NORTH)*/
				.setFill(GBC.HORIZONTAL)
				.setWeitht(100, 0)
				/*.setIpad(5, 5)*/);
		add(addNoisePanel,new GBC(0, 1)
				/*.setAnchor(GBC.NORTH)*/
				.setWeitht(100, 0));
		add(deNoisePanel,new GBC(0, 2)
				.setFill(GBC.HORIZONTAL)
				/*.setAnchor(GBC.NORTH)*/
				.setWeitht(100, 0));
		add(showPanel,new GBC(0, 3)
				/*.setAnchor(GBC.SOUTH)*/
				.setFill(GBC.BOTH)
				.setWeitht(100, 100));
	}
}
