package copyLog;

import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.TableColumn;

@SuppressWarnings("serial")
public class MovedFileGUI extends JFrame {
	ArrayList<File> thisArrayList = new ArrayList<File>();

	public MovedFileGUI(ArrayList<File> input) {
		thisArrayList = input;
		intiComponent();
	}

	private void intiComponent() {

		String[] columnNames = { "No.", "文件名" }; // initialize table
		Object[][] obj = new Object[thisArrayList.size()][2];
		for (int i = 0; i < obj.length; i++) {
			obj[i][0] = i+1;
			obj[i][1] = thisArrayList.get(i).getName();
		}

		JTable table = new JTable(obj, columnNames); // establish JTable
		TableColumn column = null; // Set default size
		int colunms = table.getColumnCount();
		for (int i = 0; i < colunms; i++) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(150); // size default width for each column
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // auto resize OFF
		JScrollPane scroll = new JScrollPane(table); // enable scroll
		scroll.setSize(300, 50);

		add(scroll);

		this.setLocation(450, 200);
		this.setTitle("传输文件列表");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}
}
