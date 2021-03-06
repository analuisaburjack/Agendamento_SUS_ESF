package br.ufg.es.dsdm.analuisaburjack_nataliamarufuji.agendamentoubs.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import br.ufg.es.dsdm.analuisaburjack_nataliamarufuji.agendamentoubs.BookingActivity;
import br.ufg.es.dsdm.analuisaburjack_nataliamarufuji.agendamentoubs.R;
import br.ufg.es.dsdm.analuisaburjack_nataliamarufuji.agendamentoubs.models.Consult;
import br.ufg.es.dsdm.analuisaburjack_nataliamarufuji.agendamentoubs.web.AsyncResponse;
import br.ufg.es.dsdm.analuisaburjack_nataliamarufuji.agendamentoubs.web.WebTaskLogin;
import br.ufg.es.dsdm.analuisaburjack_nataliamarufuji.agendamentoubs.web.WebTaskPswd2;

public class RememberPswdActivity2 extends AppCompatActivity implements AsyncResponse {

    MaterialDialog dialog;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_password2);

        ImageView buttonClose = findViewById(R.id.button_close);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setupAuth();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setupAuth() {
        Button buttonLogin =
                (Button)findViewById(R.id.button_resetPswd);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAuth();
            }
        });
    }

    private void tryAuth() {
        EditText editTextNewPswd1 = findViewById(R.id.input_newpswd);
        EditText editTextNewPswd2 = findViewById(R.id.input_newpswd2);

        if(!"".equals(editTextNewPswd1.getText().toString())){
            showLoading();
            sendCredentials(editTextNewPswd1.getText().toString(),
                    editTextNewPswd2.getText().toString());
        }else{
            editTextNewPswd1.setError("Preencha o campo 'Nova senha'");
        }

    }

    private void showMsg(){
        dialog = new MaterialDialog.Builder(this)
                .content(getMsg())
                .cancelable(true)
                .show();
    }

    private void sendCredentials(String pass1, String pass2) {
        System.out.println("entrou " + pass1 + pass2);
        WebTaskPswd2 asyncTask  = new WebTaskPswd2(this,
                pass1, pass2);
        asyncTask.delegate = this;
        asyncTask.execute();

    }

    private void showLoading(){
        dialog = new MaterialDialog.Builder(this)
                .content(R.string.label_wait)
                .progress(true,0)
                .cancelable(false)
                .show();
    }

    private void hide(){
        if(dialog != null && dialog.isShowing()){
            dialog.hide();
            dialog = null;
        }
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public void processFinishString(String o) {
        hide();
        setMsg(o);
        showMsg();

        Intent intent = new Intent(RememberPswdActivity2.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void processFinishList(List<Consult> output) {}

    @Override
    public void processFinishInteger(Integer o) {}
}

