package plugin.swt.dialogs;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import plugin.git.JGit;

import org.eclipse.swt.widgets.Button;

public class FinishTestDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private JGit jgit;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public FinishTestDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		jgit=new JGit();
		shell = new Shell(getParent(), getStyle());
		shell.setSize(365, 109);
		shell.setText(getText());
		shell.setLayout(null);
		
		Listener listenerDa = new Listener() {
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				try {
					String filePath="D:/Proba/KeyValues.txt";				
					BufferedReader br = new BufferedReader(new FileReader(filePath));
					String branchName=br.readLine();
					br.close();
					System.out.println("Branch name "+branchName);
					System.out.println(jgit.workDir);;
					jgit.setGitRepository();
					
					System.out.println(jgit.gitCreateBranch(branchName));;
					jgit.gitGetAllBranchs();
					jgit.gitCheckout(branchName);
					System.out.println(jgit.gitCommit(branchName+" je omitovao kolokvijum"));;
					System.out.println(jgit.gitPush());;
					
					shell.dispose();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (GitAPIException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			}
		};
		
		Listener listenerNe=new Listener() {
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				 shell.dispose();
			}
		};
		
		Label lblDaLiSte = new Label(shell, SWT.NONE);
		lblDaLiSte.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblDaLiSte.setBounds(10, 10, 339, 31);
		lblDaLiSte.setText("Da li ste sigurni da zelite da zavrsite test");
		
		Button btnNe = new Button(shell, SWT.NONE);
		btnNe.addListener(SWT.Selection, listenerNe);
		btnNe.setBounds(84, 46, 75, 25);
		btnNe.setText("Ne");
		
		Button btnDa = new Button(shell, SWT.NONE);
		btnDa.addListener(SWT.Selection,listenerDa);
		btnDa.setBounds(180, 46, 75, 25);
		btnDa.setText("Da");

	}
}
