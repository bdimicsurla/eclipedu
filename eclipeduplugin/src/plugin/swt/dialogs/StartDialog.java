package plugin.swt.dialogs;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
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
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.Point;

import plugin.eclipse.wb.swt.SWTResourceManager;
import plugin.git.JGit;
import plugin.git.JGitMethods;
import plugin.swt.windows.StartTest;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ProgressBar;

public class StartDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private String name;
	private String lastname;
	private String brIndex;
	private JGit gitMethods;
	private StartTest startTest;
	private ProgressBar progressBar;
	private Display display;
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
		display = getParent().getDisplay();
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
		shell.setSize(348, 161);
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
					progressBar.setVisible(true);
					progressBar.setMinimum(0);
					progressBar.setMaximum(10);
					
					JGit jgm=new JGit();
					jgm.gitPull();
					Properties p=jgm.properties;
					BufferedWriter writer=new BufferedWriter( new FileWriter("D:/Proba/KeyValues.txt"));
					writer.write(name+"_"+lastname+"_"+brIndex);
					writer.close();
					int i=0;
					while(i<10){
						i=i+1;
						try {
							
							progressBar.setSelection(i);
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					/*IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
				        public String queryOverwrite(String file) { return ALL; }
					};
					IPath projectPath = Path.fromOSString("C:/Users/MK/workspace");
					String baseDir = "D:/Projects/Project's Java/Fax/RZK/DI,Timers,Interceptors/BlogJPA";// location of files to import
					ImportOperation importOperation = new ImportOperation(projectPath,
				        new File(baseDir), FileSystemStructureProvider.INSTANCE, overwriteQuery);
					importOperation.setCreateContainerStructure(false);
					importOperation.run(new NullProgressMonitor());*/
					
					/**IProjectDescription description = ResourcesPlugin.getWorkspace().loadProjectDescription(
							new Path("D:/Projects/Project's Java/Fax/RZK/DI,Timers,Interceptors/BlogJPA" + "/.project"));
					System.out.println(description.getName());
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());
					project.create(description, null);
					project.open(null);*/
					
					shell.dispose();
					startTest.shlPocniKolokvijum.dispose();;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}/* catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 */
			}
		};

		btnNastavi.addListener(SWT.Selection, listener);
		btnNastavi.setBounds(184, 108, 75, 25);
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
		btnNazad.setBounds(74, 108, 75, 25);
		btnNazad.setText("Nazad");
		
		progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setBounds(10, 75, 327, 17);
		
		
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
