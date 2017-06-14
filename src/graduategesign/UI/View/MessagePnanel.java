package graduategesign.UI.View;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import graduategesign.UI.util.GBC;

public class MessagePnanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message="111111£¡";
	
	private JLabel messageLab=new JLabel(message);
	public MessagePnanel(){
		super();
		init();
	}
	
	public void init(){
		setBackground(Color.RED);
		setLayout(new GridBagLayout());
		add(messageLab,new GBC(0, 0)
				.setFill(GBC.BOTH)
				.setWeitht(100, 100)
				.setAnchor(GBC.WEST));
	}
}
