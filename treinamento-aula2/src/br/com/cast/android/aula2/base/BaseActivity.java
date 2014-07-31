package br.com.cast.android.aula2.base;

import java.lang.reflect.Field;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;

@EActivity
public abstract class BaseActivity extends ActionBarActivity {

	protected static final int REQUESTCODE_INCLUIR = 1;
	protected static final int REQUESTCODE_EDITAR = 2;

	private ProgressDialog progressDialog;

	@UiThread
	protected void iniciarLoading() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setIndeterminate(true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setMessage("Aguarde...");
		}
		progressDialog.show();
	}

	@UiThread
	protected void terminarLoading() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	/**
	 * Adaptação técnica para um bug (java.io.EOFException) já conhecido pelo Spring e Google HTTP Client.
	 * 
	 * @see <a href="http://sapandiwakar.in/eofexception-with-spring-rest-template-android/">Sapan Diwakar</a>
	 */
	@AfterInject
	protected void corrigirRequestFactory() {
		Field[] fields = this.getClass().getSuperclass().getDeclaredFields();
		for (Field field : fields) {
			Object propriedade;
			try {
				field.setAccessible(true);
				propriedade = field.get(this);
				if (propriedade instanceof RestClientSupport) {
					((RestClientSupport) propriedade).getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
