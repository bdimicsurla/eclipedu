package plugin.swt.windows;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import plugin.swt.dialogs.StartDialog;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;

public class StartTest {

	public Shell shlPocniKolokvijum;
	private Text textKolokvijum;
	private Text textGrupa;
	private Text textPrezime;
	private Text textIme;
	private Text textBrojIndexa;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StartTest window = new StartTest();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlPocniKolokvijum.open();
		shlPocniKolokvijum.layout();
		while (!shlPocniKolokvijum.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlPocniKolokvijum = new Shell();
		shlPocniKolokvijum.setSize(450, 300);
		shlPocniKolokvijum.setText("Pocni kolokvijum");
		shlPocniKolokvijum.setLayout(null);
		
		Label lblPredmeti = new Label(shlPocniKolokvijum, SWT.NONE);
		lblPredmeti.setBounds(10, 10, 91, 15);
		lblPredmeti.setText("Predmeti");
		
		Label lblKolokvijum = new Label(shlPocniKolokvijum, SWT.NONE);
		lblKolokvijum.setBounds(10, 45, 91, 15);
		lblKolokvijum.setText("Kolokvijum");
		
		Combo comboPredmeti = new Combo(shlPocniKolokvijum, SWT.NONE);
		comboPredmeti.setBounds(162, 2, 171, 23);
		
		textKolokvijum = new Text(shlPocniKolokvijum, SWT.BORDER);
		textKolokvijum.setBounds(164, 39, 76, 21);
		
		Label lblGrupa = new Label(shlPocniKolokvijum, SWT.NONE);
		lblGrupa.setBounds(10, 83, 91, 15);
		lblGrupa.setText("Grupa");
		
		textGrupa = new Text(shlPocniKolokvijum, SWT.BORDER);
		textGrupa.setBounds(164, 77, 76, 21);
		
		Label lblIme = new Label(shlPocniKolokvijum, SWT.NONE);
		lblIme.setBounds(10, 118, 91, 15);
		lblIme.setText("Ime");
		
		textIme = new Text(shlPocniKolokvijum, SWT.BORDER);
		textIme.setBounds(164, 112, 169, 21);
		
		Label lblPrezime = new Label(shlPocniKolokvijum, SWT.NONE);
		lblPrezime.setBounds(10, 154, 91, 15);
		lblPrezime.setText("Prezime");
		
		textPrezime = new Text(shlPocniKolokvijum, SWT.BORDER);
		textPrezime.setBounds(164, 148, 169, 21);
		
		Label labelBrojIndexa = new Label(shlPocniKolokvijum, SWT.NONE);
		labelBrojIndexa.setText("Broj indexa");
		labelBrojIndexa.setBounds(10, 186, 91, 15);
		
		textBrojIndexa = new Text(shlPocniKolokvijum, SWT.BORDER);
		textBrojIndexa.setBounds(164, 180, 76, 21);
		
		Button btnPocniKolokvijum = new Button(shlPocniKolokvijum, SWT.NONE);
		//btnPocniKolokvijum.add
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				shlPocniKolokvijum.setVisible(false);
				System.out.println(textBrojIndexa.getText());
				// TODO Auto-generated method stub
				// System.out.println("Milos Klincov");
				// Display display = new Display ();
				// Shell shell = new Shell (display,SWT.BORDER);
				StartDialog dp= new StartDialog( new Shell(),1,StartTest.this);
				dp.setName(textIme.getText());
				dp.setLastname(textPrezime.getText());
				dp.setBrIndex(textBrojIndexa.getText());
				dp.open();
				 
			}
		};

		btnPocniKolokvijum.addListener(SWT.Selection, listener);
		btnPocniKolokvijum.setBounds(320, 227, 104, 25);
		btnPocniKolokvijum.setText("Pocni kolokvijum");
		
		Button btnNazad = new Button(shlPocniKolokvijum, SWT.NONE);
		Listener listener2 = new Listener() {
			@Override
			public void handleEvent(Event event) {
				shlPocniKolokvijum.dispose();
			}
		};
		btnNazad.addListener(SWT.Selection, listener2);
		btnNazad.setBounds(210, 227, 104, 25);
		btnNazad.setText("Nazad");

	}
}
