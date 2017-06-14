package graduategesign.UI.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sun.org.apache.bcel.internal.generic.NEW;

import graduategesign.UI.util.GBC;

/**
 * 加噪面板
 * @author Administrator
 *
 */
public class DeNoisePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String tileLabStr="去噪模块";
	private static String allChbStr="全选";
	private static String statisticalChbStr="统计滤波";
	private static String meanChbStr="均值滤波";
	private static String svdChbStr="SVD";
	private static String tsvdChbStr="分块SVD";
	private static String deNoiseButStr="去噪处理";
	
	JLabel titleLab;
	JCheckBox allChb;
	JCheckBox statisticalChb;
	JCheckBox meanChb;
	JCheckBox svdChb;
	JCheckBox tsvdChb;
	JButton deNoiseBut;
	LayoutManager layout;
	public DeNoisePanel(){
		super();
		init();
	}
	public void init(){
		setBackground(Color.GREEN);
		titleLab=new JLabel(tileLabStr,SwingConstants.CENTER);
		allChb=new JCheckBox(allChbStr);
		statisticalChb=new JCheckBox(statisticalChbStr);
		meanChb=new JCheckBox(meanChbStr);
		svdChb=new JCheckBox(svdChbStr);
		tsvdChb=new JCheckBox(tsvdChbStr);
		deNoiseBut=new JButton(deNoiseButStr);
		/*layout=new GridLayout(7, 1);*/
		layout=new BorderLayout();
		setLayout(layout);
		add(titleLab,BorderLayout.NORTH);
		JPanel panel=new JPanel(new GridBagLayout());
		panel.add(allChb,new GBC(0,GBC.RELATIVE)
				.setAnchor(GBC.WEST));
		panel.add(statisticalChb,new GBC(0,GBC.RELATIVE)
				.setAnchor(GBC.WEST));
		panel.add(meanChb,new GBC(0,GBC.RELATIVE)
				.setAnchor(GBC.WEST));
		panel.add(svdChb,new GBC(0, GBC.RELATIVE)
				.setAnchor(GBC.WEST));
		panel.add(tsvdChb,new GBC(0,GBC.RELATIVE)
				.setAnchor(GBC.WEST));
		panel.add(deNoiseBut,new GBC(1,10)
				.setAnchor(GBC.SOUTH)
				.setInsets(5));
		panel.setBackground(Color.WHITE);
		add(panel,BorderLayout.CENTER);
		actionInit();
	}
	
	public void actionInit(){
		allChb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(allChb.isSelected())
				{
					chekedNumber=4;
					statisticalChb.setSelected(true);
					meanChb.setSelected(true);
					svdChb.setSelected(true);
					tsvdChb.setSelected(true);
					
				}
				else{
					chekedNumber=0;
					statisticalChb.setSelected(false);
					meanChb.setSelected(false);
					svdChb.setSelected(false);
					tsvdChb.setSelected(false);
				}
			}
		});
		
		statisticalChb.addActionListener(new selectActionListener());
		meanChb.addActionListener(new selectActionListener());
		svdChb.addActionListener(new selectActionListener());
		tsvdChb.addActionListener(new selectActionListener());
	}
	
	private int chekedNumber=0;
	private class selectActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JCheckBox checkBox=(JCheckBox)e.getSource();
			if(!checkBox.isSelected()){
				chekedNumber--;
				allChb.setSelected(false);
			}
			else{
				chekedNumber++;
				if(chekedNumber>=4){
					allChb.setSelected(true);
				}
			}
		}
		
	}
}
