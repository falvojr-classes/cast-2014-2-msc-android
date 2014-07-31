package br.com.cast.android.aula2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioGroup;
import br.com.cast.android.aula2.base.BaseActivity;
import br.com.cast.android.aula2.rest.UserRestClient;
import br.com.cast.android.aula2.rest.entity.Gender;
import br.com.cast.android.aula2.rest.entity.User;

/**
 * {@link BaseActivity} que representa a tela de inclusão e alteração de um {@link User}.
 * 
 * @author venilton.junior
 */
@EActivity(R.layout.activity_user)
public class UserActivity extends BaseActivity {

	public static String CHAVE_USUARIO = "CHAVE_USUARIO";

	@ViewById
	EditText txtNome, txtSobrenome, txtEmail, txtSenha, txtSenhaRepetida;
	@ViewById
	RadioGroup rdoGrpGenero;

	@RestService
	UserRestClient userRestClient;

	private User usuarioEdicao;

	@AfterViews
	public void init() {
		usuarioEdicao = (User) getIntent().getSerializableExtra(CHAVE_USUARIO);
		if (usuarioEdicao != null) {
			txtNome.setText(usuarioEdicao.getFirstName());
			txtSobrenome.setText(usuarioEdicao.getLastName());
			txtEmail.setText(usuarioEdicao.getEmail());
			rdoGrpGenero.check(Gender.M.equals(usuarioEdicao.getGender()) ? R.id.rdoMasculino : R.id.rdoFeminino);
		}
		getIntent().removeExtra(CHAVE_USUARIO);
	}

	@Click(R.id.btnSalvar)
	void onSalvar() {
		boolean isValido = validarCampoObrigatorio(txtNome, txtSobrenome, txtEmail, txtSenha, txtSenhaRepetida);
		//TODO: Validar os campos "E-Mail" e "Senha"
		if (isValido) {
			super.iniciarLoading();
			User user = usuarioEdicao == null ? new User() : usuarioEdicao;
			user.setFirstName(txtNome.getText().toString());
			user.setLastName(txtSobrenome.getText().toString());
			user.setGender(rdoGrpGenero.getCheckedRadioButtonId() == R.id.rdoMasculino ? Gender.M : Gender.F);
			user.setEmail(txtEmail.getText().toString());
			user.setPassword(txtSenha.getText().toString());
			salvarUsuario(user);
		}
	}

	@Background
	void salvarUsuario(User usuarioPersistencia) {
		try {
			if (usuarioEdicao == null) {
				userRestClient.insert(usuarioPersistencia);
			} else {
				userRestClient.update(usuarioPersistencia);
			}
			setResult(RESULT_OK);
		} catch (RestClientException excecaoRest) {
			setResult(RESULT_CANCELED);
		}
		finalizarActivity();
	}

	@UiThread
	void finalizarActivity() {
		super.terminarLoading();
		finish();
	}

	private boolean validarCampoObrigatorio(EditText... campos) {
		boolean isValido = true;
		for (EditText campo : campos) {
			campo.setError(null);
			if (TextUtils.isEmpty(campo.getText())) {
				campo.setError(getString(R.string.msg_campo_obrigatorio));
				campo.requestFocus();
				isValido = false;
			}
		}
		return isValido;
	}
}
