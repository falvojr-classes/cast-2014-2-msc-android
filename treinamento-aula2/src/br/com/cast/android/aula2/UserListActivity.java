package br.com.cast.android.aula2;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import br.com.cast.android.aula2.base.BaseActivity;
import br.com.cast.android.aula2.rest.UserRestClient;
import br.com.cast.android.aula2.rest.entity.User;
import br.com.cast.android.aula2.widget.UserListAdapter;

/**
 * {@link BaseActivity} com a lógica de listagem de usuários, além das chamadas os fluxos de "Incluir", "Alterar" e "Excluir".
 * 
 * @author venilton.junior
 */
@EActivity(R.layout.activity_user_list)
@OptionsMenu(R.menu.user_list)
public class UserListActivity extends BaseActivity {

	@ViewById
	ListView listViewUsuarios;

	@Bean
	UserListAdapter userListAdapter;

	@RestService
	UserRestClient userRestClient;

	@AfterViews
	void init() {
		super.iniciarLoading();
		carregarListView();
		super.registerForContextMenu(listViewUsuarios);
	}

	@Background
	void carregarListView() {
		List<User> usuarios = userRestClient.findAll();
		atualizarListView(usuarios);
	}

	@UiThread
	void atualizarListView(List<User> usuarios) {
		userListAdapter.setUsuarios(usuarios);
		listViewUsuarios.setAdapter(userListAdapter);
		userListAdapter.notifyDataSetChanged();
		super.terminarLoading();
	}

	@OptionsItem(R.id.action_incluir)
	void onIncluir() {
		UserActivity_.intent(this).startForResult(REQUESTCODE_INCLUIR);
	}

	@OptionsItem(R.id.action_refresh)
	void onRefresh() {
		super.iniciarLoading();
		carregarListView();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		super.getMenuInflater().inflate(R.menu.contextual_user_list , menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Recupera o item selecionado:
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final User usuarioSelecionado = userListAdapter.getItem(info.position);
		// Identifica a ação escolhida:
		switch(item.getItemId()){
		case R.id.action_editar:
			Intent intent = UserActivity_.intent(this).get();
			intent.putExtra(UserActivity.CHAVE_USUARIO, usuarioSelecionado);
			startActivityForResult(intent, REQUESTCODE_EDITAR);
			break;
		case R.id.action_excluir:
			OnClickListener listenerSim = new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					UserListActivity.super.iniciarLoading();
					deletarUsuario(usuarioSelecionado);
				}
			};
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(R.string.sim, listenerSim)
			.setNegativeButton(R.string.nao, null)
			.setTitle(R.string.titulo_dialog_confirmacao)
			.setMessage(String.format(getString(R.string.msg_dialog_confirmacao_exclusao), usuarioSelecionado.getFirstName()))
			.show();

			break;
		}
		return super.onContextItemSelected(item);
	}

	@Background
	void deletarUsuario(User usuario) {
		try {
			userRestClient.delete(usuario.getId());
			mostrarToast(R.string.msg_sucesso_exclusao);
			carregarListView();
		} catch (RestClientException excecaoRest) {
			mostrarToast(R.string.msg_erro_rest);
		}
	}

	@OnActivityResult(REQUESTCODE_INCLUIR)
	void onResultIncluir(int resultCode) {
		super.iniciarLoading();
		carregarListView();
		mostrarToastPorResultCode(resultCode, R.string.msg_sucesso_inclusao);
	}

	@OnActivityResult(REQUESTCODE_EDITAR)
	void onResultEditar(int resultCode) {
		super.iniciarLoading();
		carregarListView();
		mostrarToastPorResultCode(resultCode, R.string.msg_sucesso_edicao);
	}

	@UiThread
	void mostrarToast(int idRecurso, Object... parametros) {
		Toast.makeText(this, getString(idRecurso, parametros), Toast.LENGTH_SHORT).show();
	}

	private void mostrarToastPorResultCode(int resultCode, int idMensagemOk, Object... parametros) {
		if (RESULT_OK == resultCode) {
			mostrarToast(idMensagemOk, parametros);
		} else {
			mostrarToast(R.string.msg_erro_rest);
		}
	}
}
