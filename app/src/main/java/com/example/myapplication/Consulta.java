package com.example.myapplication;

import android.os.AsyncTask;

public class Consulta extends AsyncTask<void, void, String> {

    private Consulta listener;

    private static final String URL ="servidor exemplo";

    public  Consulta(consulta listner) {
        this.listener = listner;
    }

    @Override
    protected String doInBackground(void.. params) {

        String resultado = consultaServidor();
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        listener.onConultaConcluida(result);
    }
    public interface Consulta{
        void onConsulta(String situacao);
    }

}
