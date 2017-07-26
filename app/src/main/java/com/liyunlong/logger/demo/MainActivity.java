package com.liyunlong.logger.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

import com.liyunlong.logger.Logger;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String url = "http://www.java2s.com:8080/yourpath/fileName.htm?stove=10&path=32&id=4&subid=#harvic";
    private String json = "{'note':{'date':'2017-06-16 18:16:00','to':'George','from':'John','heading':'Reminder','body':'I will go back to see you tomorrow!'}}";
    private String xml = "<note date='2017-06-16 18:16:00'><to>George</to><from>John</from><heading>Reminder</heading><body>I will go back to see you tomorrow!</body></note>";
    private Address address;
    private List<String> list;
    private Map<String, String> map;
    private SparseArray<String> sparseArray;
    private Bundle bundle;
    private Intent intent;
    private SoftReference<Boolean> softReference;
    private Bitmap bitmap;
    private Message message;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initViews();
    }

    private void initDatas() {
        softReference = new SoftReference<>(Boolean.TRUE);

        address = new Address();
        address.setProvince("北京市");
        address.setCity("北京市");
        address.setArea("海淀区");

        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("item" + i);
        }

        map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            map.put("key" + i, "value" + i);
        }

        sparseArray = new SparseArray<>();
        for (int i = 0; i < 5; i++) {
            sparseArray.put(i * 5 + 5, "value" + i);
        }

        bundle = new Bundle();
        bundle.putBoolean("bundleBoolean", true);
        bundle.putParcelable("bundleAddress", address);
        bundle.putStringArrayList("bundleList", (ArrayList<String>) list);

        intent = new Intent(this, TestActivity.class);
        intent.setData(Uri.parse(url));
        intent.putExtra("intentBundle", bundle);

        message = Message.obtain(mHandler);
        message.arg1 = 2;
        message.what = 101;
        message.obj = address;
        message.setData(bundle);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.sample_big_xh);

    }

    private void initViews() {
        findViewById(R.id.print_normal_message).setOnClickListener(this);
        findViewById(R.id.print_normal_object).setOnClickListener(this);
        findViewById(R.id.print_uri_object).setOnClickListener(this);
        findViewById(R.id.print_bundle_object).setOnClickListener(this);
        findViewById(R.id.print_intent_object).setOnClickListener(this);
        findViewById(R.id.print_message_object).setOnClickListener(this);
        findViewById(R.id.print_collection_object).setOnClickListener(this);
        findViewById(R.id.print_map_object).setOnClickListener(this);
        findViewById(R.id.print_sparsearray_object).setOnClickListener(this);
        findViewById(R.id.print_bitmap_object).setOnClickListener(this);
        findViewById(R.id.print_reference_object).setOnClickListener(this);
        findViewById(R.id.print_throwable_object).setOnClickListener(this);
        findViewById(R.id.print_json_message).setOnClickListener(this);
        findViewById(R.id.print_xml_message).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.print_normal_message:
                Logger.v("test message");
                break;
            case R.id.print_normal_object:
                Logger.d(address);
                break;
            case R.id.print_uri_object:
                Logger.w(Uri.parse(url));
                break;
            case R.id.print_bundle_object:
                Logger.i(bundle);
                break;
            case R.id.print_intent_object:
                Logger.w(intent);
                break;
            case R.id.print_message_object:
                Logger.d(message);
                break;
            case R.id.print_collection_object:
                Logger.i(list);
                break;
            case R.id.print_map_object:
                Logger.d(map);
                break;
            case R.id.print_sparsearray_object:
                Logger.i(sparseArray);
                break;
            case R.id.print_bitmap_object:
                Logger.w(bitmap);
                break;
            case R.id.print_reference_object:
                Logger.wtf(softReference);
                break;
            case R.id.print_throwable_object:
                Logger.e(new NullPointerException("this object is null!"));
                break;
            case R.id.print_json_message:
                Logger.json(json);
                break;
            case R.id.print_xml_message:
                Logger.xml(xml);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            bitmap = null;
        }
    }
}
