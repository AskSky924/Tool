package com.jingqubao.tool;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.jingqubao.tool.drawable.DrawableView;
import com.jingqubao.tool.drawable.DrawableViewConfig;
import com.jingqubao.tool.service.NetTravel;
import com.jingqubao.tool.util.Consts;
import com.jingqubao.tool.util.L;
import com.jingqubao.tool.util.LocationUtils;
import com.jingqubao.tool.util.ScreenUtils;
import com.jingqubao.tool.util.Utils;
import com.jingqubao.tool.view.CircularRevealView;
import com.jingqubao.tool.view.PowerDialog;
import com.jqb.mapsdk.MapLonlat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by AskSky on 2016/1/11.
 * 工具首页
 */
public class MainActivity extends Activity implements DialogInterface.OnDismissListener {
    private LocationUtils mUtil;
    private Handler handler;
    private int maxX, maxY;
    private PowerDialog mDialog;
    private DrawableViewConfig mConfig = new DrawableViewConfig();

    private CircularRevealView revealView;
    private TextView mLongitude;
    private TextView mLatitude;
    private TextView mSceneryName;
    private TextView mPromptInfo;
    private View mMenu;
    RelativeLayout layout;
    private DrawableView mDrawableView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initData() {
        mUtil = new LocationUtils(this);
        mUtil.startLocation(1000, new LocationUtils.OnLocationListener() {
            @Override
            public void onSuccess(final AMapLocation location) {
                MapLonlat lonlat = MapLonlat.gdConvert2WGS((float) location.getLongitude(), (float) location.getLatitude());
                mLongitude.setText(lonlat.longitude + "");
                mLatitude.setText(lonlat.latitude + "");
                mPromptInfo.setTextColor(getResources().getColor(R.color.font_color));
                if (location.getAddress() != null) {
                    mPromptInfo.setText("定位方式：" + getStringByType(location.getLocationType()) + "\n地址：" + location.getAddress());
                }

                NetTravel travel = new NetTravel(MainActivity.this);
                travel.isInScenic(lonlat.latitude + "", lonlat.longitude + "", new NetTravel.RequestCallback() {
                    @Override
                    public void onResult(int errorCode, Object data, String... params) {
                        if (errorCode == Consts.NetErrorCode.ERROR_SUCCESS) {
                            String name = params[0];
                            if (!TextUtils.isEmpty(name)) {
                                mSceneryName.setText("景区名字：" + name);
                            } else {
                                mSceneryName.setText("您现在不在景区！");
                            }
                        } else {
                            mSceneryName.setText("您现在不在景区！");
                        }
                    }
                });

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                Date date = new Date();
                String time = format.format(date);
                String str = "时间：" + time + "\n经度：" + mLongitude.getText().toString() + "\n纬度：" + mLatitude.getText().toString() +
                        "\n" + mSceneryName.getText().toString() + "\n" + mPromptInfo.getText().toString();
                Utils.saveLog(Consts.SYSTEM_LOG, str);
            }

            @Override
            public void onError(int errorCode, String errorInfo, String errorDetail) {
                mPromptInfo.setTextColor(getResources().getColor(R.color.colorAccent));
                mPromptInfo.setText("定位失败！");

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                Date date = new Date();
                String time = format.format(date);
                String str = "时间：" + time + "\n错误码：" + errorCode + "\n错误信息：" + errorInfo + "\n错误详情：" + errorDetail;
                Utils.saveLog(Consts.SYSTEM_LOG, str);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void initView() {
        revealView = (CircularRevealView) findViewById(R.id.main_reveal);
        layout = (RelativeLayout) findViewById(R.id.main_parent);
        mLongitude = (TextView) findViewById(R.id.main_longitude);
        mLatitude = (TextView) findViewById(R.id.main_latitude);
        mSceneryName = (TextView) findViewById(R.id.scenery_name);
        mPromptInfo = (TextView) findViewById(R.id.prompt_info);
        View saveShare = findViewById(R.id.main_save_view_share);
        saveShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bm = ScreenUtils.snapShotWithoutStatusBar(MainActivity.this);
                File file = saveBitmap(System.currentTimeMillis() + "", bm);
                //由文件得到uri
                Uri imageUri = Uri.fromFile(file);
                L.d("share", "uri:" + imageUri);  //输出：file:///storage/emulated/0/test.jpg

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "分享到"));

            }
        });
        View copy = findViewById(R.id.copy_info);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = " 经度：" + mLongitude.getText().toString() + "\n 纬度：" + mLatitude.getText().toString() +
                        "\n " + mSceneryName.getText().toString() + "\n " + mPromptInfo.getText().toString();
                ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setText(str);
                Toast.makeText(MainActivity.this, "已经复制到剪贴板", Toast.LENGTH_LONG).show();
            }
        });
        View saveLog = findViewById(R.id.save_log);
        saveLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                Date date = new Date();
                String time = format.format(date);
                String str = "时间：" + time + "\n经度：" + mLongitude.getText().toString() + "\n纬度：" + mLatitude.getText().toString() +
                        "\n" + mSceneryName.getText().toString() + "\n" + mPromptInfo.getText().toString();
                Utils.saveLog(Consts.MY_LOG, str);
                Toast.makeText(MainActivity.this, "保存成功！在 Tool/LOG/my_log.txt 下", Toast.LENGTH_LONG).show();
            }
        });

        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        maxX = mdispSize.x;
        maxY = mdispSize.y;
        mDialog = new PowerDialog();
        mMenu = findViewById(R.id.main_menu);
        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int color = Color.parseColor("#303F9F");
                final Point p = getLocationInView(revealView, v);

                revealView.reveal(p.x, p.y, color, v.getHeight() / 2, 440, null);

                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showPowerDialog();
                    }
                }, 50);
            }
        });
        int width = ScreenUtils.getScreenWidth(this);
        int height = ScreenUtils.getScreenHeight(this);
        mDrawableView = (DrawableView) findViewById(R.id.main_drawable);
        mConfig.setStrokeColor(getResources().getColor(android.R.color.black));
        mConfig.setShowCanvasBounds(true);
        mConfig.setStrokeWidth(20.0f);
        mConfig.setMinZoom(1.0f);
        mConfig.setMaxZoom(3.0f);
        mConfig.setCanvasHeight(height);
        mConfig.setCanvasWidth(width);
        mDrawableView.setConfig(mConfig);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void showPowerDialog() {
        FragmentManager fm = getFragmentManager();
        mDialog.show(fm, "fragment_power");
    }

    public void revealFromTop() {
        final int color = Color.parseColor("#ffffff");
        final Point p = new Point(maxX / 2, maxY / 2);
        revealView.reveal(p.x, p.y, color, mMenu.getHeight() / 2, 440, null);
    }

    public void back(View view) {
        finish();
    }

    private File saveBitmap(String bitName, Bitmap bitmap) {
        File f = null;
        try {
            f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/" + bitName + ".jpg");
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }

    private String getStringByType(int type) {
        String str;
        switch (type) {
            case 1:
                str = "GPS定位结果";
                break;
            case 2:
                str = "返回上次定位结果";
                break;
            case 4:
                str = "缓存定位结果";
                break;
            case 5:
                str = "Wifi定位结果";
                break;
            case 6:
                str = "基站定位结果";
                break;
            default:
                str = "未知";
                break;
        }
        return str;
    }

    private Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);

        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);

        l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
        l1[1] = l1[1] - l0[1] + target.getHeight() / 2;

        return new Point(l1[0], l1[1]);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUtil.stopLocation();
        mUtil.onDestroy();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        View v = mMenu;
        final Point p = getLocationInView(revealView, v);

        handler = new Handler();
        final int color = Color.parseColor("#00FFFFFF");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                revealView.hide(p.x, p.y, color, 0, 330, null);
            }
        }, 300);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    public void strokesSize(View view) {
        View SeekView = View.inflate(this, R.layout.include_main_strokes_size_popup, null);
        SeekBar seekBar = (SeekBar) SeekView.findViewById(R.id.strokes_size_seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mConfig.setStrokeWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        float stroke = mConfig.getStrokeWidth();
        seekBar.setProgress((int) stroke);

        PopupWindow popupWindow = new PopupWindow(SeekView);
        popupWindow.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAsDropDown(view);
    }

    public void colorChoose(View view) {
        View ColorView = View.inflate(this, R.layout.include_main_color_popup, null);
        GridView gv = (GridView) ColorView.findViewById(R.id.main_color_choose);
        int width = ScreenUtils.getScreenWidth(this);
        int column = width / ScreenUtils.dip2px(this, 27.5f);
        gv.setNumColumns(column);
        gv.setAdapter(new MyAdapter());
        final PopupWindow popupWindow = new PopupWindow(ColorView);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int color = getResources().getColor(colors[position]);
                mConfig.setStrokeColor(color);
//                view.setBackgroundColor(color);
                popupWindow.dismiss();
            }
        });
        popupWindow.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAsDropDown(view);
    }

    public void rubber(View view) {
        mConfig.setStrokeColor(getResources().getColor(R.color.rubber));
    }

    int[] colors = {R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5,
            R.color.color6, R.color.color7, R.color.color8, R.color.color9, R.color.color10,
            R.color.color11, R.color.color12, R.color.color13, R.color.color14, R.color.color15};

    public void goBack(View view) {
        mDrawableView.undo();
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return colors.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = View.inflate(MainActivity.this, R.layout.include_main_color_item, null);
            View button = v.findViewById(R.id.color_item);
            button.setBackgroundColor(getResources().getColor(colors[position]));
            return v;
        }
    }
}
