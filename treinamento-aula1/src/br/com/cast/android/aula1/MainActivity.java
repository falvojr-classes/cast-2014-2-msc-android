package br.com.cast.android.aula1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	private EditText txtEmail;
	private EditText txtSenha;
	private Button btnLogar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtSenha = (EditText) findViewById(R.id.txtSenha);
		btnLogar = (Button) findViewById(R.id.btnLogar);

		btnLogar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View botao) {
				validarCampoSenha();
				validarCampoEmail();

				if (txtEmail.getError() == null && txtSenha.getError() == null) {
					Intent intent = new Intent(MainActivity.this, HomeActivity.class);
					startActivity(intent);
				}
			}
		});
	}

	private void validarCampoEmail() {
		txtEmail.setError(null);
		if (TextUtils.isEmpty(txtEmail.getText())) {
			txtEmail.setError(getString(R.string.msg_campo_obrigatorio));
			txtEmail.requestFocus();
		} else if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText()).matches()) {
			txtEmail.setError(getString(R.string.msg_email_invalido));
			txtEmail.requestFocus();
		}
	}

	private void validarCampoSenha() {
		txtSenha.setError(null);
		if (TextUtils.isEmpty(txtSenha.getText())) {
			txtSenha.setError(getString(R.string.msg_campo_obrigatorio));
			txtSenha.requestFocus();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			new AlertDialog.Builder(this)
			.setTitle(this.getString(R.string.msg_sobre))
			.setMessage(getString(R.string.msg_descricao_app)).setIcon(android.R.drawable.ic_dialog_info)
			.setNeutralButton(android.R.string.ok, null)
			.show();
		}
		return super.onOptionsItemSelected(item);
	}
}