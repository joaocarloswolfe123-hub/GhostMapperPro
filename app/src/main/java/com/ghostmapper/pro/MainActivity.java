package com.ghostmapper.pro;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.view.*;
import android.widget.*;
import rikka.shizuku.Shizuku;

public class MainActivity extends Activity {
    private TextView status;
    private static final int REQUEST_SHIZUKU = 110;
    private final Shizuku.OnRequestPermissionResultListener permissionListener = (requestCode, grantResult) -> runOnUiThread(this::updateStatus);
    private final Shizuku.OnBinderReceivedListener binderListener = () -> runOnUiThread(this::updateStatus);
    private final Shizuku.OnBinderDeadListener deadListener = () -> runOnUiThread(this::updateStatus);
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        Shizuku.addRequestPermissionResultListener(permissionListener);
        Shizuku.addBinderReceivedListenerSticky(binderListener);
        Shizuku.addBinderDeadListener(deadListener);
        LinearLayout root = new LinearLayout(this); root.setOrientation(1); root.setPadding(28,35,28,20); root.setBackgroundColor(Color.rgb(28,26,72));
        TextView title = new TextView(this); title.setText("GHOSTMAPPER PRO"); title.setTextSize(26); title.setTextColor(Color.WHITE); root.addView(title);
        TextView subtitle = new TextView(this); subtitle.setText("Controle de jogos • Projeto inicial 0.1.0"); subtitle.setTextColor(0xffbcb7ff); root.addView(subtitle);
        status = new TextView(this); status.setTextColor(Color.WHITE); status.setPadding(0,30,0,20); root.addView(status);
        addButton(root,"Conectar ao Shizuku", () -> {
            try { if (!Shizuku.pingBinder()) { toast("Inicie o Shizuku primeiro"); return; }
                if (Shizuku.checkSelfPermission() != android.content.pm.PackageManager.PERMISSION_GRANTED) Shizuku.requestPermission(REQUEST_SHIZUKU);
                else updateStatus();
            } catch (Exception e) { toast("Shizuku indisponível: " + e.getMessage()); }
        });
        addButton(root,"Permitir sobreposição", () -> {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName())); startActivity(intent);
        });
        addButton(root,"Abrir editor flutuante", () -> {
            if (!Settings.canDrawOverlays(this)) { toast("Autorize a sobreposição primeiro"); return; }
            startService(new Intent(this, OverlayService.class));
        });
        addButton(root,"Fechar editor", () -> stopService(new Intent(this, OverlayService.class)));
        TextView note = new TextView(this); note.setText("Esta versão contém conexão Shizuku e editor flutuante demonstrativo. Ainda não converte teclas/mouse em toques nos jogos."); note.setTextColor(0xffddd9ff); note.setPadding(0,30,0,0); root.addView(note);
        setContentView(root); updateStatus();
    }
    private void addButton(LinearLayout root, String label, Runnable action) { Button b = new Button(this); b.setText(label); b.setAllCaps(false); b.setOnClickListener(v -> action.run()); root.addView(b); }
    private void updateStatus() {
        try { status.setText(!Shizuku.pingBinder() ? "Shizuku: desligado" : Shizuku.checkSelfPermission() == android.content.pm.PackageManager.PERMISSION_GRANTED ? "Shizuku: autorizado" : "Shizuku: aguardando autorização"); }
        catch (Exception e) { status.setText("Shizuku: indisponível"); }
    }
    private void toast(String s) { Toast.makeText(this,s,Toast.LENGTH_LONG).show(); }
    @Override protected void onDestroy() { Shizuku.removeRequestPermissionResultListener(permissionListener); Shizuku.removeBinderReceivedListener(binderListener); Shizuku.removeBinderDeadListener(deadListener); super.onDestroy(); }
}
