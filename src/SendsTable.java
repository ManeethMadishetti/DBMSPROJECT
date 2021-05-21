import java.awt.BorderLayout;
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
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SendsTable {

	private JPanel p1;
	private JFrame frame;

	private JMenuItem miInsert2,miUpdate2,midelete2,miView2;
	private JLabel lblcid,lblpid,lblday;
	private JTextField txtcid,txtpid,txtday;
	private JButton btn;
	private JTextArea txtmsg;
	//private static Statement stmt;
	private Connection connection;
	private Statement statement;
	String s1;
	String s2;
	public SendsTable(JPanel p1,JFrame frame,JMenuItem miInsert2,JMenuItem miUpdate2,JMenuItem midelete2,JMenuItem miView2) {
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (Exception e) 
		{
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
		//connectToDB();
		this.frame=frame;
		this.p1=p1;
		this.miInsert2=miInsert2;
		this.midelete2=midelete2;
		this.miUpdate2=miUpdate2;
		this.miView2=miView2;
		txtcid=new JTextField(20);
		txtpid=new JTextField(20);
		txtday=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lblcid=new JLabel("Company Id:");
		lblpid=new JLabel("Product Id:");
		lblday=new JLabel("Date:");
		//queryHandler();
	}
	public void connectToDB() 
    {
		try 
		{
		  connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:ORCL","mydbms","mydbms");
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
		
		miInsert2.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				 
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				
				JPanel p=new JPanel();
				txtcid=new JTextField(20);
				txtpid=new JTextField(20);
				txtday=new JTextField(20);
				
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lblcid);
				p.add(txtcid);
				p.add(lblpid);
				p.add(txtpid);
				p.add(lblday);
				p.add(txtday);
				
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
							String query= "INSERT INTO company_sends VALUES(" + txtcid.getText() + ","  + txtpid.getText()  + ",'" + txtday.getText() + "')";
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
		
		miUpdate2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtcid=new JTextField(20);
				txtpid=new JTextField(20);
				txtday=new JTextField(20);
				
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT cid,pid FROM company_sends");
				  while (rs.next()) 
				  {
					  idlist.add("CID="+rs.getString("cid")+"   PID="+rs.getString("PID"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblcid);
				p.add(txtcid);
				p.add(lblpid);
				p.add(txtpid);
				p.add(lblday);
				p.add(txtday);
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
				
						StringTokenizer st =new StringTokenizer(idlist.getSelectedItem());
						s1=st.nextToken();
						s2=st.nextToken();
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM company_sends where "+s1+" and "+s2);
							rs.next();
							txtcid.setText(rs.getString("CID"));
							txtpid.setText(rs.getString("pid"));
							txtday.setText(rs.getString("day"));
							
						} 
						catch (SQLException selectException) 
						{
							txtmsg.append("SELECT * FROM company_sends where"+s1+"and"+s2+"\n"+selectException.getMessage());
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
							int i = statement.executeUpdate("UPDATE company_sends "+ "SET day='" + txtday.getText() + "'"
							+  " WHERE "+s1+" and "+s2);
							txtmsg.append("\nUpdated " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
						} 
						catch (SQLException insertException) 
						{
							txtmsg.append("UPDATE company_sends "+ "SET day='" + txtday.getText() + "' "
									+  " WHERE "+s1+" and "+s2+"\n"+insertException.getMessage());
						}
						
					}
				});
			}
		});


		midelete2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtcid=new JTextField(20);
				txtpid=new JTextField(20);
				txtday=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT cid,pid FROM company_sends");
				  while (rs.next()) 
				  {
					  idlist.add("CID="+rs.getString("cid")+"   PID="+rs.getString("PID"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblcid);
				p.add(txtcid);
				p.add(lblpid);
				p.add(txtpid);
				p.add(lblday);
				p.add(txtday);
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
				
						StringTokenizer st =new StringTokenizer(idlist.getSelectedItem());
						s1=st.nextToken();
						s2=st.nextToken();
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM company_sends where "+s1+" and "+s2);
							rs.next();
							txtcid.setText(rs.getString("CID"));
							txtpid.setText(rs.getString("pid"));
							txtday.setText(rs.getString("day"));
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
							int i = statement.executeUpdate("delete from company_sends where "+s1+" and "+s2);
							txtmsg.append("\nDeleted " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
							txtcid.setText(null);
							txtpid.setText(null);
							txtday.setText(null);
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
		
		miView2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			//	Statement statement=connection.createStatement();
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				p1.add(txtmsg);
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
				txtmsg.setText(null);
				try {
					ResultSet rs=statement.executeQuery("select * from company_sends");
					String s="CID\tPID\tDAY\n";
					s=s+"-----------------------------------------------------\n";
					
					while(rs.next())  
						s=s+(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3))+"\n";
						txtmsg.append(s);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				
			}
		});
	}

}
