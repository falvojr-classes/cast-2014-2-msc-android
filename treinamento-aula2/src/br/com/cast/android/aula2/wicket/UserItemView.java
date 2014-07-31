package br.com.cast.android.aula2.wicket;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.cast.android.aula2.R;
import br.com.cast.android.aula2.rest.entity.Gender;
import br.com.cast.android.aula2.rest.entity.User;

@EViewGroup(R.layout.user_item)
public class UserItemView extends LinearLayout {

	@ViewById
	TextView txtNomeCompleto;

	public UserItemView(Context context) {
		super(context);
	}

	public void bind(User person) {
		txtNomeCompleto.setText(person.getFirstName() + " " + person.getLastName());
		int icone = Gender.F.equals(person.getGender()) ? R.drawable.ic_user_female : R.drawable.ic_user_male;
		txtNomeCompleto.setCompoundDrawablesWithIntrinsicBounds(icone, 0, 0, 0);
	}
}
