# Logger —— 日志打印助手
日志系统，使用打印助手对象维护不同的日志打印助手进行日志输出，可以是 Console、Logcat、文件等打印助手，支持字符串、普通对象、Uri、Bundle、Intent、Message、Collection、Map、SparseArray、Bitmap、Reference、Throwable、JSON、Xml等格式化的输出。

## 功能介绍
* 在Logcat中完美的格式化输出，能很好的过滤手机和其他App的日志信息；
* 打印当前线程的名称、ID、优先级、State、Group信息，可以清楚地看到当前线程的信息；
* 打印调用日志打印方法的类名、方法名、行号等信息，可以清楚地看到日志记录的调用堆栈，支持跳转到源码处；
* 支持格式化输出JSON、XML格式信息；
* 支持List、Set、Map、SparseArray、和数组的格式化输出；
* 支持系统对象如Uri、Bundle、Intent、Message、Reference和Throwable的格式化输出；
* 支持自定义对象的格式化输出；
* 支持字符串格式化后输出；
* 支持自定义对象解析器；
* 支持自定义日志输出树，如输出到文件的树等。

## Download ##
### Gradle ###
```gradle
dependencies {
    implementation 'com.henley.android:logger:1.0.1'
}
```

### APK Demo ###

下载 [APK-Demo](https://github.com/HenleyLee/Logger/raw/master/app/app-release.apk)

## 使用介绍 ##
使用前需要进行日志的配置初始化及打印助手的添加，默认实现了打印到Logcat、文件、Console的打印助手，但需要在应用启动时进行添加，这样才能将日志信息打印到Logcat、文件、Console中。一般需要在自定义Application的OnCreate方法中进行如下配置：
```
    Logger.getLogConfig()                   // 获取配置信息(可重新进行设置)
             .setLogEnabled(true)            // 设置是否启用日志输出
             .setShowFormat(true)            // 设置是否打印排版线条
             .setShowThreadInfo(true)        // 设置是否打印线程信息
             .setShowMethodInfo(true)        // 设置是否打印方法信息
             .setLogMinLevel(Log.VERBOSE);   // 设置日志最小输出级别

    Logger.addPrinter(new ConsolePrinter());    // 添加控制台打印助手(输出日志信息到控制台)
    Logger.addPrinter(new FilePrinter(this));   // 添加文件打印助手(输出日志信息到文件)
    Logger.addPrinter(new LogcatPrinter());     // 添加Logcat打印助手(输出日志信息到Logcat)
```

#### 1、打印字符串信息： ####
```
    Logger.v("test message");
```
![](/screenshots/打印字符串.png)

#### 2、打印普通对象： ####
```
    Address address = new Address();
    address.setProvince("北京市");
    address.setCity("北京市");
    address.setArea("海淀区");
    Logger.d(address);
```
![](/screenshots/打印普通对象.png)

#### 3、打印Collection对象： ####
```
    ArrayList<String> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
        list.add("item" + i);
    }
    Logger.i(list);
```
![](/screenshots/打印Collection对象.png)

#### 4、打印Map对象： ####
```
    Map<String, String> map = new HashMap<>();
    for (int i = 0; i < 5; i++) {
        map.put("key" + i, "value" + i);
    }
    Logger.d(map);
```
![](/screenshots/打印Map对象.png)

#### 5、打印SparseArray对象： ####
```
    SparseArray<String> sparseArray = new SparseArray<>();
    for (int i = 0; i < 5; i++) {
        sparseArray.put(i * 5 + 5, "value" + i);
    }
    Logger.i(sparseArray);
```
![](/screenshots/打印SparseArray对象.png)

#### 6、打印Uri对象： ####
```
    String url = "http://www.java2s.com:8080/yourpath/fileName.htm?stove=10&path=32&id=4&subid=#harvic";
    Logger.w(Uri.parse(url));
```
![](/screenshots/打印Uri对象.png)

#### 7、打印Bundle对象： ####
```
    Bundle bundle = new Bundle();
    bundle.putBoolean("bundleBoolean", true);
    bundle.putParcelable("bundleAddress", address);
    bundle.putStringArrayList("bundleList", list);
    Logger.i(bundle)
```
![](/screenshots/打印Bundle对象.png)

#### 8、打印Intent对象： ####
```
    Intent intent = new Intent(this, TestActivity.class);
    intent.setData(Uri.parse(url));
    intent.putExtra("intentBundle", bundle);
    Logger.w(intent);
```
![](/screenshots/打印Intent对象.png)

#### 9、打印Message对象： ####
```
    Handler handler = new Handler();
    Message message = Message.obtain(handler);
    message.arg1 = 2;
    message.what = 101;
    message.obj = address;
    message.setData(bundle);
    Logger.d(message);
```
![](/screenshots/打印Message对象.png)

#### 10、打印Bitmap对象： ####
```
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.sample_big_xh);
    Logger.w(bitmap);
```
![](/screenshots/打印Bitmap对象.png)

#### 11、打印Reference对象： ####
```
    SoftReference<Boolean> softReference = new SoftReference<>(Boolean.TRUE);
    Logger.wtf(softReference);
```
![](/screenshots/打印Reference对象.png)

#### 12、打印Throwable对象： ####
```
    Throwable throwable = new NullPointerException("this object is null!");
    Logger.e(throwable);
```
![](/screenshots/打印Throwable对象.png)

#### 13、打印JSON： ####
```
    String json = "{'note':{'date':'2017-06-16 18:16:00','to':'George','from':'John','heading':'Reminder','body':'I will go back to see you tomorrow!'}}";
    Logger.json(json);
```
![](/screenshots/打印JSON.png)

#### 14、打印XML： ####
```
    String xml = "<note date='2017-06-16 18:16:00'><to>George</to><from>John</from><heading>Reminder</heading><body>I will go back to see you tomorrow!</body></note>";
    Logger.xml(xml);
```
![](/screenshots/打印XML.png)

## 日志配置详解 ##

| 方法 | 描述 | 取值 | 缺省 |
| --- | ---- | --- | --- |
| setLogEnabled | 是否启用日志输出 | boolean | true |
| setCommonTag | 设置公共标签 | String | "Logger" |
| setShowFormat | 设置是否打印排版线条 | boolean | false |
| setShowThreadInfo | 设置是否打印线程信息 | boolean | false |
| setShowMethodInfo | 设置是否打印方法信息 | boolean | false |
| setLogMinLevel | 设置日志最小输出级别 | int | Log.VERBOSE |
| addParser | 添加解析器 | IParser | 无 |

## 感谢 ##

1、[https://github.com/orhanobut/logger](https://github.com/orhanobut/logger)

2、[https://github.com/xiaoyaoyou1212/ViseLog](https://github.com/xiaoyaoyou1212/ViseLog)
