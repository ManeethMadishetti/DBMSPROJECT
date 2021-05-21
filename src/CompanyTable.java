import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CompanyTable {

	private JPanel p1;
	private JFrame frame;

	private JMenuItem miInsert1,miUpdate1,midelete1,miView1;
	private JLabel lblcid,lblcname,lblemail,lblphone;
	private JTextField txtcid,txtcname,txtemail,txtphone;
	private JButton btn;
	private JTextArea txtmsg;
	//private static Statement stmt;
	private Connection connection;
	private Statement statement;
	public CompanyTable(JPanel p1,JFrame frame,JMenuItem miInsert1,JMenuItem miUpdate1,JMenuItem midelete1,JMenuItem miView1) {
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (Exception e) 
		{
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
		connectToDB();
		this.frame=frame;
		this.p1=p1;
		this.miInsert1=miInsert1;
		this.midelete1=midelete1;
		this.miUpdate1=miUpdate1;
		this.miView1=miView1;
		txtcid=new JTextField(20);
		txtcname=new JTextField(20);
		txtemail=new JTextField(20);
		txtphone=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lblcid=new JLabel("Company Id:");
		lblcname=new JLabel("Company Name:");
		lblemail=new JLabel("Email:");
		lblphone=new JLabel("Phone No:");
		//queryHandler();
	}
	public void connectToDB() 
    {
		try 
		{
		  connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","mydbms","mydbms");
		  statement = connection.createStatement();

		} 
		catch (SQLException connectException) 
		{
		  System.out.println(connectException.getMessage());
		  System.out.println(connectException.getSQLState());
		  System.out.println(connectException.getErrorCode());
		  System.exit(1);
		}
    }
	public void regis_company() {
		miInsert1.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
			//	p1.setBackground(Color.CYAN);
				JPanel p=new JPanel();
				txtcid=new JTextField(20);
				txtcname=new JTextField(20);
				txtemail=new JTextField(20);
				txtphone=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lblcid);
				p.add(txtcid);
				p.add(lblcname);
				p.add(txtcname);
				p.add(lblemail);
				p.add(txtemail);
				p.add(lblphone);
				p.add(txtphone);
				p.setLayout(new GridLayout(4,2));
				
				p1.add(p);
				p1.add(btn);
				p1.add(txtmsg);//msg text area added to panel
				btn.setText("INSERT");
				p1.setLayout(new FlowLayout());
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
				
				//register listener
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try {
							Statement statement = connection.createStatement();
							String query= "INSERT INTO company VALUES(" + txtcid.getText() + ", " + "'" + txtcname.getText() + "'," + "'" + txtemail.getText() + "'," + txtphone.getText() + ")";
							int i = statement.executeUpdate(query);
								txtmsg.append("\nInserted " + i + " rows successfully");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
						//	e1.printStackTrace();
							txtmsg.append(e1.getMessage());
						}  
					}
				});
				
			}
		});
		
		miUpdate1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	p1.setBackground(Color.LIGHT_GRAY);
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtcid=new JTextField(20);
				txtcname=new JTextField(20);
				txtemail=new JTextField(20);
				txtphone=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT cid FROM company");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("cid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblcid);
				p.add(txtcid);
				p.add(lblcname);
				p.add(txtcname);
				p.add(lblemail);
				p.add(txtemail);
				p.add(lblphone);
				p.add(txtphone);
				p.setLayout(new GridLayout(4,2));
				p1.add(p);
				p1.add(btn);
				p1.add(txtmsg);
				btn.setText("Update");
				p1.setLayout(new FlowLayout());
				
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
			
				idlist.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						// TODO Auto-generated method stub
				
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM company where CID ="+idlist.getSelectedItem());
							rs.next();
							txtcid.setText(rs.getString("CID"));
							txtcname.setText(rs.getString("CNAME"));
							txtemail.setText(rs.getString("Email"));
							txtphone.setText(rs.getString("PNO"));
						} 
						catch (SQLException selectException) 
						{
							txtmsg.append(selectException.getMessage());
						}
					}
				});		
				
				
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try 
						{
							Statement statement = connection.createStatement();
							int i = statement.executeUpdate("UPDATE company "
							+ "SET cname='" + txtcname.getText() + "', "
							+ "email='" + txtemail.getText() + "', "
							+ "pno ="+ txtphone.getText() + " WHERE cid = "
							+ idlist.getSelectedItem());
							txtmsg.append("\nUpdated " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
						} 
						catch (SQLException insertException) 
						{
							txtmsg.append(insertException.getMessage());
						}
						
					}
				});
			}
		});


		midelete1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	p1.setBackground(Color.BLUE);
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtcid=new JTextField(20);
				txtcname=new JTextField(20);
				txtemail=new JTextField(20);
				txtphone=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT cid FROM company");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("cid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblcid);
				p.add(txtcid);
				p.add(lblcname);
				p.add(txtcname);
				p.add(lblemail);
				p.add(txtemail);
				p.add(lblphone);
				p.add(txtphone);
				p.setLayout(new GridLayout(4,2));
				p1.add(p);
				p1.add(btn);
				p1.add(txtmsg);
				btn.setText("Delete");
				p1.setLayout(new FlowLayout());
				
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
				
				idlist.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						// TODO Auto-generated method stub
				
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM company where CID ="+idlist.getSelectedItem());
							rs.next();
							txtcid.setText(rs.getString("CID"));
							txtcname.setText(rs.getString("CNAME"));
							txtemail.setText(rs.getString("Email"));
							txtphone.setText(rs.getString("PNO"));
						} 
						catch (SQLException selectException) 
						{
							txtmsg.append(selectException.getMessage());
						}
					}
				});		
				
				
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try 
						{
							Statement statement = connection.createStatement();
							int i = statement.executeUpdate("delete from company where cid=" + idlist.getSelectedItem());
							txtmsg.append("\nDeleted " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
							txtcid.setText(null);
							txtcname.setText(null);
							txtemail.setText(null);
							txtphone.setText(null);
							idlist.removeAll();
						} 
						catch (SQLException insertException) 
						{
							txtmsg.append(insertException.getMessage());
						}
						
					}
				});
			}
		});
		
		miView1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				p1.add(txtmsg);
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
				
				try {
					ResultSet rs=statement.executeQuery("select * from company");
					String s="CID \t CNAME \t EMAIl \t PHONE\n";
					s=s+"\n_________________________________________________________________________________\n";
					while(rs.next())  
						s=s+(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getLong(4))+"\n";
						txtmsg.setText(s);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				
			}
		});
		

	}
}
