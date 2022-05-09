package com.inc.vr.corp.apps.sppd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.inc.vr.corp.apps.sppd.Adapter.SliderAdapter;
import com.inc.vr.corp.apps.sppd.Adapter.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class HomeActivity extends AppCompatActivity {
    SliderView sliderView;
    Button btnDashboard;
    TextView sambutan;
    private SliderAdapter adapter;
    SharedPreferences sharedPreferences;
    Boolean isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        btnDashboard = findViewById(R.id.btn_dashboard);
        sambutan = findViewById(R.id.sambutan);
        sambutan.setText(kataSambutan());
        //check shared preference
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("isLogin", false);
        //btn click
        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin){
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        //slider
        sliderView = findViewById(R.id.imageSlider);
        adapter = new SliderAdapter(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
        addNewItem(sliderView, "https://sppd.epogame.my.id/assets/assets/img/fotoB.jpg");
        //add new item
        addNewItem(sliderView, "https://sppd.epogame.my.id/assets/assets/img/fotoA.jpeg");
        addNewItem(sliderView, "https://sppd.epogame.my.id/assets/assets/img/fotoC.jpg");
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(View view, String image) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("");
        sliderItem.setImageUrl(image);
        adapter.addItem(sliderItem);
    }

    public String kataSambutan(){
        String xsambutan = "Assalamu,alaikum Wr,Wb\n" +
                "\n" +
                "Selamat datang di website Biro Organisasi Sekretariat Provinsi Jawa Tengah. Ucap syukur atas limpahan rahmat dan taufik kami sampaikan kehadiratNya, serta ucapan terimakasih dan penghargaan kami haturkan kepada berbagai pihak yang telah memberikan berbagai bantuan dan dukungan.\n" +
                "\n" +
                "Otonomi daerah adalah pemberian kewenangan kepada pemerintah daerah untuk membangun dan mengurus pemerintahan sendiri sesuai dengan kemampuan dan potensi yang dimiliki untuk mencapai kesejahtraan dan kemakmuran masyarakat dalam kerangka Negara Kesatuan Republik Indonesia.\n" +
                "\n" +
                "Biro Organisasi Setda Provinsi Jawa Tengah sebagai salah satu komponen Setda Provinsi Jawa Tengah yang bertugas mempunyai tugas melaksanakan pengoordinasian penyusunan kebijakan Daerah, pengoordinasian pelaksanaan tugas Perangkat Daerah, pemantauan dan evaluasi pelaksanaan kebijakan Daerah, pelayanan administratif dan pembinaan sumber daya ASN di bidang kelembagaan, tatalaksana ,dan akuntabilitas kinerja dan reformasi birokrasi.\n" +
                "\n" +
                "Sedangkan Fungsi Biro Organisasi Setda Provinsi Jawa Tengah :\n" +
                "\n" +
                "1. pengoordinasian penyusunan kebijakan Daerah di bidang kelembagaan, tatalaksana, dan akuntabilitas kinerja dan reformasi birokrasi;\n" +
                "\n" +
                "2. pengoordinasian pelaksanaan pelaksanaan tugas Perangkat Daerah di bidang kelembagaan, tatalaksana, dan akunabilitas kinerja dan reformasi birokrasi;\n" +
                "\n" +
                "3. pemantauan dan evaluasi pelaksanaan kebijakan Daerah di bidang kelembagaan, tatalaksana,dan akunabilitas kinerja dan reformasi birokrasi;\n" +
                "\n" +
                "4. pelaksanaan pelayanan administratif dan pembinaan sumber daya ASN di bidang kelembagaan, tatalaksana, dan akunabilitas kinerja dan reformasi birokrasi; dan\n" +
                "\n" +
                "Akhirnya kami berharap semoga situs Biro Organisasi ini dapat memberikan gambaran yang bermanfaat dalam menunjang pembangunan, sehingga dapat meningkatkan kesejahtraanmasyarakat.\n" +
                "\n" +
                "Wassalammu’alaikum Wr, Wb.";
        return xsambutan;
    }
}