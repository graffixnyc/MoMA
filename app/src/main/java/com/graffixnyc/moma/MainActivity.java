package com.graffixnyc.moma;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.util.Random;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View leftTop=(View)findViewById(R.id.leftTop);
        final LinearLayout leftBottom=(LinearLayout)findViewById(R.id.leftBottom);
        final LinearLayout rightTop=(LinearLayout)findViewById(R.id.rightTop);
        final LinearLayout rightBottom=(LinearLayout)findViewById(R.id.rightBottom);
        SeekBar sbChangeColor=(SeekBar)findViewById(R.id.seekBar);
        final Random rnd = new Random();
        final int startColorRed = getResources().getColor(R.color.red);
        final int startColorYellow = getResources().getColor(R.color.yellow);
        final int startColorGreen = getResources().getColor(R.color.green);
        final int startColorPurple = getResources().getColor(R.color.purple);

        final ArgbEvaluator interpolator = new ArgbEvaluator();

        sbChangeColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            int endColorRed = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            int endColorYellow = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            int endColorGreen = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            int endColorPurple = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                float fraction = (float) progress / 100;

                leftTop.setBackgroundColor((Integer) interpolator.evaluate(fraction, startColorRed, endColorRed));
                leftBottom.setBackgroundColor((Integer) interpolator.evaluate(fraction, startColorYellow, endColorYellow));
                rightTop.setBackgroundColor((Integer) interpolator.evaluate(fraction, startColorGreen, endColorGreen));
                rightBottom.setBackgroundColor((Integer) interpolator.evaluate(fraction, startColorPurple, endColorPurple));

                progress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_moreinfo) {
            dialogShow();
            return true;
        }
        if (id == R.id.action_about) {
            Intent i=new Intent(this,About.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public int color(int color1, int color2, int progress) {
        int red, green, blue; // rgb components
        int deltaR, deltaG, deltaB; // change in rgb components

        deltaR = Color.red(color2) - Color.red(color1);
        deltaG = Color.green(color2) - Color.green(color1);
        deltaB = Color.blue(color2) - Color.blue(color1);

        red = Color.red(color1) + (deltaR * progress / 100);
        green = Color.green(color1) + (deltaG * progress / 100);
        blue = Color.blue(color1) + (deltaB * progress / 100);

        return Color.rgb(red, green, blue);
    }
    public void dialogShow() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(getResources().getString(R.string.app_name));
        builder1.setMessage("Would you like to visit MoMA for more information?");
        builder1.setCancelable(true);

            builder1.setPositiveButton("Visit MoMA",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse("http://www.moma.org/m"));
                            startActivity(intent);

                        }
                    });
        builder1.setNegativeButton("Not Now",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
           }

}

