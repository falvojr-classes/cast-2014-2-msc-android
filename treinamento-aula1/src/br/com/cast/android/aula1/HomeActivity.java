package br.com.cast.android.aula1;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.widget.TextView;

@EActivity(R.layout.activity_home)
public class HomeActivity extends Activity {

	public static final String CHAVE_EMAIL = "CHAVE_EMAIL";

	@ViewById
	TextView lblSaudacao;

	@AfterViews
	public void onReady() {
		lblSaudacao.setText("Bem vindo " + getIntent().getStringExtra(CHAVE_EMAIL));
		getIntent().removeExtra(CHAVE_EMAIL);
	}
}
