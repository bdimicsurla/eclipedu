package plugin.swt.dialogs;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.SWT;

import plugin.eclipse.wb.swt.SWTResourceManager;
import plugin.git.JGit;
import plugin.git.JGitMethods;
import plugin.swt.windows.StartTest;

import org.eclipse.swt.widgets.Button;

public class StartDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private String name;
	private String lastname;
	private String brIndex;
	private JGit gitMethods;
	private StartTest startTest;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	
	public StartDialog(Shell parent, int style,StartTest testOb) {
		super(parent, style);
		setText("SWT Dialog");
		startTest=testOb;
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
		gitMethods=new JGit();
		shell = new Shell(getParent(), getStyle());
		shell.setSize(348, 140);
		shell.setText(getText());
		
		Label lblDaLiSte = new Label(shell, SWT.NONE);
		lblDaLiSte.setFont(SWTResourceManager.getFont("Arial", 14, SWT.NORMAL));
		lblDaLiSte.setBounds(10, 10, 327, 31);
		lblDaLiSte.setText("Da li ste sigurni da zelite da zapocnete");
		
		Label lblKolokvijum = new Label(shell, SWT.NONE);
		lblKolokvijum.setFont(SWTResourceManager.getFont("Arial", 14, SWT.NORMAL));
		lblKolokvijum.setBounds(112, 38, 101, 31);
		lblKolokvijum.setText("kolokvijum?");
		
		Button btnNastavi = new Button(shell, SWT.NONE);
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				System.out.println(name+" "+lastname+" "+brIndex);
				try {
					JGit jgm=new JGit();
					jgm.gitPull();
					Properties p=jgm.properties;
					BufferedWriter writer=new BufferedWriter( new FileWriter("D:/Proba/KeyValues.txt"));
					writer.write(name+"_"+lastname+"_"+brIndex);
					writer.close();
					/*IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
				        public String queryOverwrite(String file) { return ALL; }
					};

				String baseDir = "";// location of files to import
				ImportOperation importOperation = new ImportOperation(project.getFullPath(),
				        new File(baseDir), FileSystemStructureProvider.INSTANCE, overwriteQuery);
				importOperation.setCreateContainerStructure(false);
				importOperation.run(new NullProgressMonitor());*/
					shell.dispose();
					startTest.shlPocniKolokvijum.dispose();;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			}
		};

		btnNastavi.addListener(SWT.Selection, listener);
		btnNastavi.setBounds(190, 71, 75, 25);
		btnNastavi.setText("Nastavi");
		
		Listener listenerNazad=new Listener(){

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				shell.dispose();
				startTest.shlPocniKolokvijum.setVisible(true);
			}
			
		};
		
		Button btnNazad = new Button(shell, SWT.NONE);
		btnNazad.addListener(SWT.Selection, listenerNazad);
		btnNazad.setBounds(74, 71, 75, 25);
		btnNazad.setText("Nazad");

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getBrIndex() {
		return brIndex;
	}

	public void setBrIndex(String text) {
		// TODO Auto-generated method stub
		this.brIndex=text;
	}

}
