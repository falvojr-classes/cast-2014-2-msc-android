package br.com.cast.android.aula1;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainEActivity extends ActionBarActivity {

	@ViewById
	EditText txtEmail, txtSenha;

	@Click(R.id.btnLogar)
	void clickBotaoLogar() {
		validarCampoSenha();
		validarCampoEmail();

		if (txtEmail.getError() == null && txtSenha.getError() == null) {
			Intent intent = new Intent(MainEActivity.this, HomeActivity.class);
			startActivity(intent);
		}
	}

	@OptionsItem(R.id.action_sobre)
	void clickMenuSobre() {
		new AlertDialog.Builder(this)
		.setTitle(this.getString(R.string.msg_sobre))
		.setMessage(getString(R.string.msg_descricao_app)).setIcon(android.R.drawable.ic_dialog_info)
		.setNeutralButton(android.R.string.ok, null)
		.show();
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
}
