package br.com.cast.android.aula2.base;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {

	protected static final int REQUESTCODE_INCLUIR = 1;
	protected static final int REQUESTCODE_EDITAR = 2;

	private ProgressDialog progressDialog;

	protected void iniciarLoading() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setIndeterminate(true);
		}
		progressDialog.setMessage("Carregando...");
		progressDialog.show();
	}

	protected void terminarLoading() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
}
