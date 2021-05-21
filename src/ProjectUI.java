import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProjectUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu mnucmpy,mnusends,mnuproduct,mnuassess,mnutester,mnucpyfeed,mnufeed;
	private JMenuBar mnuBar;
	private JMenuItem miInsert1,miUpdate1,midelete1,miView1;
	private JMenuItem miInsert2,miUpdate2,midelete2,miView2;
	private JMenuItem miInsert3,miUpdate3,midelete3,miView3;
	private JMenuItem miInsert4,miUpdate4,midelete4,miView4;
	private JMenuItem miInsert5,miUpdate5,midelete5,miView5;
	private JMenuItem miInsert6,miUpdate6,midelete6,miView6;
	private JMenuItem miInsert7,miUpdate7,midelete7,miView7;
	private JTextField txtField;
	
	static JPanel p1;
	
	void initialize()
	{
		//po=new JPanel();
		p1=new JPanel();
		mnucmpy=new JMenu("Company");
		mnuproduct=new JMenu("Product");
		mnusends=new JMenu("Sends");
		mnutester=new JMenu("Tester");
		mnuassess=new JMenu("Assess");
		mnufeed=new JMenu("FeedBack");
		mnucpyfeed=new JMenu("Comp_FeedBack");
		mnuBar=new JMenuBar();
		
		miInsert1=new JMenuItem("Insert");
		miUpdate1=new JMenuItem("Update");
		midelete1=new JMenuItem("Delete");
		miView1=new JMenuItem("View");
	
		miInsert2=new JMenuItem("Insert");
		miUpdate2=new JMenuItem("Update");
		midelete2=new JMenuItem("Delete");
		miView2=new JMenuItem("View");
		
		miInsert3=new JMenuItem("Insert");
		miUpdate3=new JMenuItem("Update");
		midelete3=new JMenuItem("Delete");
		miView3=new JMenuItem("View");
		
		miInsert4=new JMenuItem("Insert");
		miUpdate4=new JMenuItem("Update");
		midelete4=new JMenuItem("Delete");
		miView4=new JMenuItem("View");
		
		miInsert5=new JMenuItem("Insert");
		miUpdate5=new JMenuItem("Update");
		midelete5=new JMenuItem("Delete");
		miView5=new JMenuItem("View");
		
		miInsert6=new JMenuItem("Insert");
		miUpdate6=new JMenuItem("Update");
		midelete6=new JMenuItem("Delete");
		miView6=new JMenuItem("View");
		
		miInsert7=new JMenuItem("Insert");
		miUpdate7=new JMenuItem("Update");
		midelete7=new JMenuItem("Delete");
		miView7=new JMenuItem("View");
		
		
		
		txtField = new JTextField("Air lines quality and information management system"); 
		txtField.setFont(new Font("Serif", Font.PLAIN, 25));
		txtField.setEditable(false);
	//	po.setBackground(Color.MAGENTA);
	}
	void addComponentsToFrame()
	{
		mnucmpy.add(miInsert1);
		mnucmpy.add(miUpdate1);
		mnucmpy.add(midelete1);
		mnucmpy.add(miView1);
		
		
		mnusends.add(miInsert2);
		mnusends.add(miUpdate2);
		mnusends.add(midelete2);
		mnusends.add(miView2);
		
		mnuproduct.add(miInsert3);
		mnuproduct.add(miUpdate3);
		mnuproduct.add(midelete3);
		mnuproduct.add(miView3);
		
		
		
		
		mnuassess.add(miInsert4);
		mnuassess.add(miUpdate4);
		mnuassess.add(midelete4);
		mnuassess.add(miView4);
		
		mnutester.add(miInsert5);
		mnutester.add(miUpdate5);
		mnutester.add(midelete5);
		mnutester.add(miView5);
		
		mnucpyfeed.add(miInsert6);
		mnucpyfeed.add(miUpdate6);
		mnucpyfeed.add(midelete6);
		mnucpyfeed.add(miView6);
		
		mnufeed.add(miInsert7);
		mnufeed.add(miUpdate7);
		mnufeed.add(midelete7);
		mnufeed.add(miView7);
		
		mnuBar.add(mnucmpy);
		mnuBar.add(mnusends);
		mnuBar.add(mnuproduct);
		mnuBar.add(mnuassess);
		mnuBar.add(mnutester);
		mnuBar.add(mnucpyfeed);
		mnuBar.add(mnufeed);
		setJMenuBar(mnuBar);
		
		
		p1.setLayout(new BorderLayout());
		p1.add(txtField,BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		add(p1,BorderLayout.CENTER);
		
	}
	void register()
	{
		CompanyTable t1=new CompanyTable(p1,ProjectUI.this,miInsert1,miUpdate1,midelete1,miView1);
		t1.regis_company();
		SendsTable t2=new SendsTable(p1,ProjectUI.this,miInsert2,miUpdate2,midelete2,miView2);
		t2.regis_company();
		ProductTable t3=new ProductTable(p1,ProjectUI.this,miInsert3,miUpdate3,midelete3,miView3);
		t3.regis_product();
		AssessTable t4=new AssessTable(p1, ProjectUI.this, miInsert4, miUpdate4, midelete4, miView4);
		t4.regis_company();
		TestTable t5=new TestTable(p1,ProjectUI.this,miInsert5,miUpdate5,midelete5,miView5);
		t5.regis_product();
		Comp_FeedTable t6=new Comp_FeedTable(p1, ProjectUI.this, miInsert6,miUpdate6, midelete6, miView6);
		t6.regis_company();
		FeedbackTable t7=new FeedbackTable(p1,ProjectUI.this,miInsert7,miUpdate7,midelete7,miView7);
		t7.regis_product();
		 addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent evt) 
				{
					
						ImageIcon icon = new ImageIcon("src/t21.jpg");//C:\Users\DELL\Desktop\dbms\project\airlines Quality and Inf mgmt system\src
					  int a=JOptionPane.showConfirmDialog(ProjectUI.this,"Are you sure?", "This will close the UI", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);  
					  if(a==JOptionPane.YES_OPTION)
					      ProjectUI.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
						
				}   
			 });

		
	}
	
	
	public ProjectUI()
	{
		initialize();
		addComponentsToFrame();
		register();
		pack();
		setTitle("Air lines quality and information management system");
		setSize(600,500);
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
