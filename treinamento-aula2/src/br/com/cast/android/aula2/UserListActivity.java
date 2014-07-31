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

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import br.com.cast.android.aula2.base.BaseActivity;
import br.com.cast.android.aula2.rest.UserRestClient;
import br.com.cast.android.aula2.rest.entity.User;
import br.com.cast.android.aula2.wicket.UserListAdapter;

/**
 * Activity com a lógica de listagem de usuários, além dos fluxos de "Incluir", "Alterar" e "Excluir".
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
	void afterViews() {
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
		carregarListView();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		super.getMenuInflater().inflate(R.menu.contextual_user_list , menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_editar:
			break;
		case R.id.action_excluir:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@OnActivityResult(REQUESTCODE_INCLUIR)
	protected void onResultIncluir(int resultCode) {

	}

}
