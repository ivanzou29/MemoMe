/**
 * This is for showing a dialog to user when a background connection is in progress
 */
package cs.hku.hk.memome.ui;

import android.app.AlertDialog;
import android.view.View;

import cs.hku.hk.memome.R;

public class ProcessingDialog
{
    AlertDialog alertDialog;
    View rootView;

    /**
     * Constructor, to build a ProcessingDialog instance for further usage
     * @param rootView The view a processing dialog should be bound to
     */
    public ProcessingDialog(View rootView)
    {
        this.rootView = rootView;
        AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
        builder.setView(View.inflate(rootView.getContext(), R.layout.processing_dialog,null));
        alertDialog = builder.create();
    }

    /**
     * Display the dialog in the context
     */
    public void show()
    {
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    /**
     * Dismiss the dialog
     */
    public void dismiss()
    {
        alertDialog.cancel();
    }

}
