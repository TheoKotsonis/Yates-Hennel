package gui;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;  

public class OpenFile {
	
	JFileChooser fileChooser = new JFileChooser();
	StringBuffer sb = new StringBuffer();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("CMP Files", "cmp");
	
		
	public String pickfile() throws Exception {
				
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setFileFilter(filter);
		
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    return fileChooser.getSelectedFile().getAbsolutePath();
		}
		else
			return null;
		
	
	}
	
}
