package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import gui.panel.ConfigPanel;
import gui.panel.MainPanel;
import gui.panel.RecoverPanel;
import service.ConfigService;
import util.MysqlUtil;

public class RecoverListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		RecoverPanel panel = RecoverPanel.instance;
		String mysqlPath = new ConfigService().getValueByKey(ConfigService.mysqlPath);
		if(0 == mysqlPath.length()){
			JOptionPane.showMessageDialog(panel, "����ǰ��������mysql�İ�װ·��");
			//��ת�����ý���
			MainPanel.instance.workingPanel.show(ConfigPanel.instance);
			//mysql��������ȡ����
			ConfigPanel.instance.tfMysqlPath.grabFocus();
			return ;
		}
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("hutubill.sql"));
		fc.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return ".sql";
			}
			
			@Override
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".sql");
			}
		});
		
		int returnVal = fc.showSaveDialog(panel);
		File file = fc.getSelectedFile();
//		System.out.println(file);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			if(!file.getName().toLowerCase().endsWith(".sql")){
				file = new File(file.getParent(),file.getName()+".sql");
			}
			
			try{
				MysqlUtil.recover(mysqlPath, file.getAbsolutePath());
				JOptionPane.showMessageDialog(panel,  "�ָ��ɹ�");
			}catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(panel, "�ָ�ʧ��");
			}
		}
		
	}

}