package plugin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;

import plugin.swt.dialogs.FinishTestDialog;
import plugin.swt.dialogs.StartDialog;
import plugin.swt.windows.StartTest;

public class FinishButtonHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		FinishTestDialog dp= new FinishTestDialog( new Shell(),1);
		dp.open();
		return null;
	}

}
