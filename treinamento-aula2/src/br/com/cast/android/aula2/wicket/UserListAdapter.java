package br.com.cast.android.aula2.wicket;

import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.cast.android.aula2.rest.entity.User;

@EBean
public class UserListAdapter extends BaseAdapter {

	List<User> usuarios;

	public void setUsuarios(List<User> usuarios) {
		this.usuarios = usuarios;
	}

	@RootContext
	Context context;

	@Override
	public int getCount() {
		return usuarios.size();
	}

	@Override
	public User getItem(int position) {
		return usuarios.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UserItemView personItemView;
		if (convertView == null) {
			personItemView = UserItemView_.build(context);
		} else {
			personItemView = (UserItemView) convertView;
		}
		personItemView.bind(getItem(position));
		return personItemView;
	}
}
