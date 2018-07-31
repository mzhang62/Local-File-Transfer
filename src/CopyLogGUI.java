package copyLog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

//import java.swing.*;
@SuppressWarnings("serial")
public class CopyLogGUI extends JFrame implements ActionListener{
	JPanel jp1, jp2, jp3, jp4,jp5;
	JLabel jlb1, jlb2, jlb3, jlb4, jlb5;
	JButton transferButton, clearButton;
	JTextField srcPath, dstPath, dayLimit, spaceLimit;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CopyLogGUI windows1 = new CopyLogGUI();
		
	}

	//constructor
	public CopyLogGUI() {

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jp5 = new JPanel();

        jlb1 = new JLabel("源路径：");
        jlb2 = new JLabel("目标路径：");
        jlb3 = new JLabel("空间限制（MB）：");
        jlb4 = new JLabel("日限（天）：");
        

        transferButton = new JButton("开始传输");
        clearButton = new JButton("清除");
        
        transferButton.addActionListener(this);
        clearButton.addActionListener(this);
        
        //setting input textfield
        srcPath = new JTextField(20);
        dstPath = new JTextField(20);
        spaceLimit = new JTextField(5);
        dayLimit = new JTextField(5);
        
        
        //layout
        this.setLayout(new GridLayout(6, 1));

        //adding component
        
        
        jp1.add(jlb1);
        jp1.add(srcPath);

        jp2.add(jlb2);
        jp2.add(dstPath);
        
        jp4.add(jlb3);
        jp4.add(spaceLimit);
        
        jp5.add(jlb4);
        jp5.add(dayLimit);

        jp3.add(transferButton);
        jp3.add(clearButton);

        // adding to JFrame
        this.add(jp1);
        this.add(jp2);
        this.add(jp4);
        this.add(jp5);
        this.add(jp3);
        

        this.setSize(300, 400);
        this.setLocation(450, 200);
        this.setTitle("文件传输");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("开始传输")){
			try {
				fileTransfer();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getActionCommand().equals("清除")) {
			clear();
		}
	}
	
	public void fileTransfer() throws Exception {
        String from = srcPath.getText();
        String to = dstPath.getText();
        int timeLimit = Integer.parseInt(dayLimit.getText());
        int storageLimit = Integer.parseInt(spaceLimit.getText()); 
        if(!validPath(from) || !validPath(to) ) {
        	JOptionPane.showMessageDialog(null,"请输入有效的地址！","信息",JOptionPane.WARNING_MESSAGE); 
        	clear();
        	return;
        }
        core myEntity = new core();
        if(myEntity.copyBegin(from, to, storageLimit, timeLimit)) {
        	JOptionPane.showMessageDialog(null,"传输完成","信息",JOptionPane.WARNING_MESSAGE); 
        	//dispose();
        	clear();
        	new MovedFileGUI(core.myArrayList);
        	core.myArrayList.clear();
        }
        else {
        	JOptionPane.showMessageDialog(null,"无文件需要传输","信息",JOptionPane.WARNING_MESSAGE); 
        	//dispose();
        	clear();
        }
	}
	
	public boolean validPath(String input) {
		File myFile = new File(input);
		if(myFile.isDirectory() || myFile.isFile()) {
			return true;
		}
		return false;
	}
	
	public void clear() {
		srcPath.setText("");
		dstPath.setText("");
		dayLimit.setText("");
		spaceLimit.setText("");
	}
}
