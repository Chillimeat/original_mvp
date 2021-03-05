开发环境：
java JDK 8 11；
AS版本4.0以上；
全局build.gradle
    buildscript {
        repositories {
            google()
            jcenter()
            maven { url "https://jitpack.io"}
            mavenCentral() // add repository
            maven { url "http://maven.aliyun.com/nexus/content/groups/public/" }
            maven { url "http://maven.aliyun.com/nexus/content/repositories/jcenter/" }
            maven { url "http://dl.bintray.com/lukaville/maven"}

        }
        dependencies {
            classpath 'com.android.tools.build:gradle:4.0.1'
        }
    }
    allprojects {
        repositories {
            maven { url "https://jitpack.io" }
            maven { url "http://maven.aliyun.com/nexus/content/groups/public/" }
            maven { url "http://maven.aliyun.com/nexus/content/repositories/jcenter/" }
            maven { url  "http://dl.bintray.com/lukaville/maven" }
            google()
            jcenter()
        }

        configurations.all{
            resolutionStrategy.cacheDynamicVersionsFor 0, 'seconds'
        }
    }
全局build.gradle
    android {
        compileSdkVersion 29
        buildToolsVersion "29.0.2"
    
        //为了解决部分第三方库重复打包了META-INF的问题
        packagingOptions {
            exclude 'META-INF/LICENSE'
            exclude 'META-INF/NOTICE'
        }
    
        defaultConfig {
            applicationId "com.fingergame.ayun.livingclock"
            minSdkVersion 21
            targetSdkVersion 26
            ...
            testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
            ndk {
                //设置支持的SO库架构
                abiFilters 'armeabi' ,'armeabi-v7a', 'arm64-v8a'//, 'x86_64',  'x86'
                //某些oppo手机是armeabi，某些oppo是v7
                // armeabi: ARM v5 这是相当老旧的一个版本，缺少对浮点数计算的硬件支持，在需要大量计算时有性能瓶颈
                // armeabi-v7a: ARM v7
                // arm64-v8a: 64位支持，目前主流的版本
                //x86 / x86_64: x86 架构的手机都会包含由 Intel 提供的称为 Houdini 的指令集动态转码工具，实现 对 arm .so 的兼容，再考虑 x86 1% 以下的市场占有率，x86 相关的两个 .so 也是可以忽略的
            }
            multiDexEnabled true
            flavorDimensions "versionCode"
        }
    
        applicationVariants.all { variant ->
            variant.outputs.all { output ->
                def outputFile = output.outputFile
                def fileName
                def createTime = new Date().format("YYYY-MM-dd", TimeZone.getTimeZone("GMT+08:00"))
                if (outputFile != null && outputFile.name.endsWith('.apk')) {
                    fileName = "livingclock_v" + defaultConfig.versionName + "_" + createTime + "_" + buildType.name + ".apk"
                    outputFileName = fileName
                }
            }
        }
        lintOptions {
            checkReleaseBuilds false
            // Or, if you prefer, you can continue to check for errors in release builds,
            // but continue the build even when errors are found:
            abortOnError false
        }
        signingConfigs{
            release{
                storeFile file("/xxxx.jks")   //签名文件全路径
                storePassword "xxxx"
                keyAlias "xxxx"
                keyPassword "xxxx"
            }
        }
        buildTypes {
            release {
                //启用signingConfig中的release代码
                signingConfig signingConfigs.release
                //仅对项目的发布版本类型启用代码收缩、模糊处理和优化。
                minifyEnabled true
                //启用资源缩减，这是由Android Gradle插件执行的。R8编译 https://developer.android.com/studio/build/shrink-code#keep-code
                //shrinkResources true
                //包括与Android Gradle插件一起打包的默认ProGuard规则文件。
                proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    
            }
        }
        sourceSets {
            main {
                manifest.srcFile 'src/main/AndroidManifest.xml'
                assets {
                    srcDirs 'src/main/assets', 'src/main/assets/'
                    jniLibs.srcDirs =['libs']
                }
    
            }
        }
        //exo开启java8
        compileOptions {
            targetCompatibility JavaVersion.VERSION_1_8
            sourceCompatibility JavaVersion.VERSION_1_8
        }
        //为了解决Dex 64K 问题，而出现的OOM问题的解决方法
        dexOptions {
            //incremental true
            javaMaxHeapSize "4g"
        }
        viewBinding {
            enabled true
        }
    
        //多渠道
        productFlavors{
            official{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Official"]
            }
            Other{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Other"]
            }
            tencent{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Tencent"]
            }
            Xiaomi{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Xiaomi"]
            }
            Huawei{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Huawei"]
            }
            Oppo{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Oppo"]
            }
            Vivo{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Vivo"]
            }
            Samsung{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Samsung"]
            }
            Sanliuling{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Sanliuling"]
            }
            baidu{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Baidu"]
            }
            Meizu{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Meizu"]
            }
            Ali{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Ali"]
            }
            Kuan{
                manifestPlaceholders = [APP_CHANNEL_VALUE:"Kuan"]
            }
        }
    }
    dependencies {
        implementation fileTree(include: ['*.jar'], exclude:['android-support-v4.jar'], dir: 'libs')
        //implementation 'com.github.ayunmingming:xxxxx:xxx'
        ...
    }
    
gradle-wrapper.properties:
    distributionBase=GRADLE_USER_HOME
    distributionPath=wrapper/dists
    zipStoreBase=GRADLE_USER_HOME
    zipStorePath=wrapper/dists
    distributionUrl=https\://services.gradle.org/distributions/gradle-6.1.1-all.zip
gradle.properties:
    #SmartRefreshLayout如果使用 AndroidX 先在 gradle.properties 中添加，两行都不能少
    android.useAndroidX=true
    android.enableJetifier=true

    #配置虚拟机
    android.enableAapt2=true
    org.gradle.jvmargs=-Xmx2048m

    #vivo打包问题
    android.injected.testOnly=false

    #保活
    org.gradle.daemon=true
权限：
    <!-- 相机权限 -->
    <uses-permission android:name="android.permission.CAMERA" /><!-- 手机摄像头 -->
    <uses-permission-sdk-23 android:name="android.permission.CAMERA" /> <!-- 相机 -->
    <uses-feature android:name="android.hardware.camera" /> <!-- 相机自动对焦 -->
    <uses-feature android:name="android.hardware.camera.autofocus" />
    <!--用户类型权限-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" /><!--获取用户手机信息-->
    <uses-permission android:name="android.permission.READ_PHONE_NUMBERS"/><!--获取用户手机号-->

    <uses-permission android:name="android.permission.READ_LOGS" tools:ignore="ProtectedPermissions" />
    <uses-permission android:name= "android.permission.ACCESS_FINE_LOCATION"/><!--获取用户手机位置信息-->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/><!--增加手机信号强度-->

    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" /><!--安装包的权限-->
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"
        tools:ignore="ProtectedPermissions" />

    <uses-permission android:name="android.permission.ACCESS_NOTIFICATION_POLICY"/><!--通知权限-->

    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/><!--开机启动广播权限-->

    <uses-permission android:name="android.permission.VIBRATE"/><!--手机震动-->

    <uses-permission android:name="android.permission.DISABLE_KEYGUARD"/><!--屏幕保持唤醒状态-->
    <uses-permission android:name="android.permission.WAKE_LOCK"/><!--屏幕保持唤醒状态-->
    <uses-permission android:name="android.permission.USE_FULL_SCREEN_INTENT" /><!--常驻通知的全屏唤醒-->


    <uses-permission android:name="android.permission.DEVICE_POWER"
        tools:ignore="ProtectedPermissions" /><!--访问底层电源-->
    <uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS"/><!--忽略电池优化6.0以上-->

    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/><!--悬浮窗-->

    <uses-permission android:name="android.permission.WRITE_SETTINGS"
        tools:ignore="ProtectedPermissions" /><!--写入系统-->

    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" /><!--前台服务通知-->
    <uses-permission android:name="android.permission.GET_TASKS" />
    <!--    语音权限-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.RECORD_AUDIO"/>
AndroidManifest：application 
    <application
        android:name=".Application"
        android:allowBackup="false"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        android:usesCleartextTraffic="true"
        android:requestLegacyExternalStorage="true"
        tools:replace="android:allowBackup"
        tools:ignore="GoogleAppIndexingWarning">
    </application>
AndroidManifest：exo播放器
    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        package="包名">
        exo播放器
        <uses-feature
                android:glEsVersion="0x00020000"
                android:required="true"/>
    </manifest>

AndroidManifest：receiver
    <receiver
            android:name="indi.ayun.original_mvp.daemon.WakeUpReceiver"
            android:process=":watch">
            <intent-filter>
                <action android:name="android.intent.action.USER_PRESENT"/>
                <action android:name="android.intent.action.ACTION_POWER_CONNECTED"/>
                <action android:name="android.intent.action.ACTION_POWER_DISCONNECTED"/>
                <action android:name="com.xdandroid.hellodaemon.CANCEL_JOB_ALARM_SUB"/>
            </intent-filter>
        </receiver>

        <receiver
            android:name="indi.ayun.original_mvp.daemon.WakeUpReceiver$WakeUpAutoStartReceiver"
            android:process=":watch">
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED"/>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.PACKAGE_ADDED"/>
                <action android:name="android.intent.action.PACKAGE_REMOVED"/>
                <data android:scheme="package"/>
            </intent-filter>
        </receiver>
        
AndroidManifest：service
    <service android:name="indi.ayun.original_mvp.daemon.AbsWorkService$WorkNotificationService"/>

    <service
            android:name="indi.ayun.original_mvp.daemon.JobSchedulerService"
            android:permission="android.permission.BIND_JOB_SERVICE"
            android:enabled="true"
            android:exported="true"
            android:process=":watch"/>

    <service
        android:name="indi.ayun.original_mvp.daemon.WatchDogService"
        android:process=":watch"/>

    <service
        android:name="indi.ayun.original_mvp.daemon.WatchDogService$WatchDogNotificationService"
        android:process=":watch"/>
AndroidManifest：mediaBox
        <activity
            android:name="indi.ayun.original_mvp.media_box.impl.ui.BoxingActivity"
            android:launchMode="singleTop"
            android:screenOrientation="portrait"
            android:theme="@style/Boxing.AppTheme.NoActionBar">
        </activity>
        <activity
            android:name="indi.ayun.original_mvp.media_box.impl.ui.BoxingViewActivity"
            android:launchMode="singleTop"
            android:screenOrientation="portrait"
            android:theme="@style/Boxing.AppTheme.NoActionBar">
        </activity>
        <activity
            android:name="indi.ayun.original_mvp.media_box.impl.ui.BoxingBottomSheetActivity"
            android:screenOrientation="portrait"
            android:theme="@style/Boxing.AppTheme.NoActionBar"/>
        <activity
            android:name="indi.ayun.original_mvp.media_box.impl.ui.IntentFilterActivity"
            android:screenOrientation="portrait"
            android:theme="@style/Boxing.AppTheme.NoActionBar">
            <intent-filter>
                <action android:name="android.intent.action.PICK" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:mimeType="image/*" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.GET_CONTENT" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.OPENABLE" />
                <data android:mimeType="image/*" />
            </intent-filter>
        </activity>
AndroidManifest：ucrop
        <!--        图片剪裁ucrop-->
        <activity-->
            android:name=".ucrop.UCropActivity"
            android:screenOrientation="portrait"
            android:theme="@style/Theme.AppCompat.Light.NoActionBar"/>
AndroidManifest：WebActivity          
    <activity android:name="indi.ayun.original_mvp.ui.web.WebActivity" />
AndroidManifest：多渠道
    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        package="包名">
        <meta-data
                android:name="CHANNEL"
                android:value="${APP_CHANNEL_VALUE}"/>
    </manifest>
    

混淆：
    # -----------------------------基本 -----------------------------
    #

    # 指定代码的压缩级别 0 - 7(指定代码进行迭代优化的次数，在Android里面默认是5，这条指令也只有在可以优化时起作用。)
    -optimizationpasses 5
    # 混淆时不会产生形形色色的类名(混淆时不使用大小写混合类名)
    -dontusemixedcaseclassnames
    # 指定不去忽略非公共的库类(不跳过library中的非public的类)
    -dontskipnonpubliclibraryclasses
    # 指定不去忽略包可见的库类的成员
    -dontskipnonpubliclibraryclassmembers
    #不进行优化，建议使用此选项，
    -dontoptimize
     # 不进行预校验,Android不需要,可加快混淆速度。
    -dontpreverify
    # 屏蔽警告
    -ignorewarnings
    # 指定混淆是采用的算法，后面的参数是一个过滤器
    # 这个过滤器是谷歌推荐的算法，一般不做更改
    -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
    # 保护代码中的Annotation不被混淆
    -keepattributes *Annotation*
    # 避免混淆泛型, 这在JSON实体映射时非常重要
    -keepattributes Signature
    # 抛出异常时保留代码行号
    -keepattributes SourceFile,LineNumberTable
     #优化时允许访问并修改有修饰符的类和类的成员，这可以提高优化步骤的结果。
    # 比如，当内联一个公共的getter方法时，这也可能需要外地公共访问。
    # 虽然java二进制规范不需要这个，要不然有的虚拟机处理这些代码会有问题。当有优化和使用-repackageclasses时才适用。
    #指示语：不能用这个指令处理库中的代码，因为有的类和类成员没有设计成public ,而在api中可能变成public
    -allowaccessmodification
    #当有优化和使用-repackageclasses时才适用。
    -repackageclasses ''
     # 混淆时记录日志(打印混淆的详细信息)
     # 这句话能够使我们的项目混淆后产生映射文件
     # 包含有类名->混淆后类名的映射关系
    -verbose

    #
    # ----------------------------- 默认保留 -----------------------------
    #
    #----------------------------------------------------
    # 保持哪些类不被混淆
    #继承activity,application,service,broadcastReceiver,contentprovider....不进行混淆
    -keep public class * extends android.app.Activity
    -keep public class * extends android.app.Application
    -keep public class * extends android.support.multidex.MultiDexApplication
    -keep public class * extends android.app.Service
    -keep public class * extends android.content.BroadcastReceiver
    -keep public class * extends android.content.ContentProvider
    -keep public class * extends android.app.backup.BackupAgentHelper
    -keep public class * extends android.preference.Preference
    -keep public class * extends android.view.View
    -keep class android.support.** {*;}## 保留support下的所有类及其内部类

    -keep public class com.google.vending.licensing.ILicensingService
    -keep public class com.android.vending.licensing.ILicensingService
    #表示不混淆上面声明的类，最后这两个类我们基本也用不上，是接入Google原生的一些服务时使用的。
    #----------------------------------------------------

    # 保留继承的
    #-keep public class * extends android.support.v4.**
    #-keep public class * extends android.support.v7.**
    #-keep public class * extends android.support.annotation.**

    #这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
    #表示不混淆Activity中参数是View的方法，因为有这样一种用法，在XML中配置android:onClick=”buttonClick”属性，
    #当用户点击该按钮时就会调用Activity中的buttonClick(View view)方法，如果这个方法被混淆的话就找不到了
    -keepclassmembers class * extends android.app.Activity{
        public void *(android.view.View);
    }

    #表示不混淆枚举中的values()和valueOf()方法，枚举我用的非常少，这个就不评论了
    -keepclassmembers enum * {
        public static **[] values();
        public static ** valueOf(java.lang.String);
    }

    #表示不混淆任何一个View中的setXxx()和getXxx()方法，
    #因为属性动画需要有相应的setter和getter的方法实现，混淆了就无法工作了。
    -keep public class * extends android.view.View{
        *** get*();
        void set*(***);
        public <init>(android.content.Context);
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context, android.util.AttributeSet, int);
    }
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context, android.util.AttributeSet, int);
    }

    #表示不混淆Parcelable实现类中的CREATOR字段，
    #毫无疑问，CREATOR字段是绝对不能改变的，包括大小写都不能变，不然整个Parcelable工作机制都会失败。
    -keep class * implements android.os.Parcelable {
      public static final android.os.Parcelable$Creator *;
    }
    # 这指定了继承Serizalizable的类的如下成员不被移除混淆
    -keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
    }
    # 保留R下面的资源
    #-keep class **.R$* {
    # *;
    #}
    #不混淆资源类下static的
    -keepclassmembers class **.R$* {
        public static <fields>;
    }

    # 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
    -keepclassmembers class * {
        void *(**On*Event);
        void *(**On*Listener);
    }

    # 保留我们自定义控件（继承自View）不被混淆
    -keep public class * extends android.view.View{
        *** get*();
        void set*(***);
        public <init>(android.content.Context);
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context, android.util.AttributeSet, int);
    }

    #----------------------------- WebView(项目中没有可以忽略) -----------------------------
    #
    #webView需要进行特殊处理
    -keepclassmembers class fqcn.of.javascript.interface.for.Webview {
       public *;
    }
    -keepclassmembers class * extends android.webkit.WebViewClient {
        public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
        public boolean *(android.webkit.WebView, java.lang.String);
    }
    -keepclassmembers class * extends android.webkit.WebViewClient {
        public void *(android.webkit.WebView, jav.lang.String);
    }
    #在app中与HTML5的JavaScript的交互进行特殊处理
    #我们需要确保这些js要调用的原生方法不能够被混淆，于是我们需要做如下处理：
    -keepclassmembers class * {
        <methods>;
    }

    #
    #---------------------------------实体类---------------------------------
    #--------(实体Model不能混淆，否则找不到对应的属性获取不到值)-----
    #
    -dontwarn com.suchengkeji.android.confusiondemo.md.**
    #对含有反射类的处理
    -keep class com.suchengkeji.android.confusiondemo.md.** { *; }
    #
    # ----------------------------- 其他的 -----------------------------
    #
    # 删除代码中Log相关的代码
    -assumenosideeffects class android.util.Log {
        public static boolean isLoggable(java.lang.String, int);
        public static int v(...);
        public static int i(...);
        public static int w(...);
        public static int d(...);
        public static int e(...);
    }

    # 保持测试相关的代码
    -dontnote junit.framework.**
    -dontnote junit.runner.**
    -dontwarn android.test.**
    -dontwarn android.support.test.**
    -dontwarn org.junit.**
    #----------------------------------project ------------------------------

    -keep public class xxxxxxxx.model.**{*;}
    #-keep public class xxxxxxxx.data.**{*;}
    #---------------------------------------------------------------------------------------------------facebook图片处理器
    # Keep our interfaces so they can be used by other ProGuard rules.
    # See http://sourceforge.net/p/proguard/bugs/466/
    -keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
    -keep,allowobfuscation @interface com.facebook.soloader.DoNotOptimize
    # Do not strip any method/class that is annotated with @DoNotStrip
    -keep @com.facebook.common.internal.DoNotStrip class *
    -keepclassmembers class * {
        @com.facebook.common.internal.DoNotStrip *;
    }
    # Do not strip any method/class that is annotated with @DoNotOptimize
    -keep @com.facebook.soloader.DoNotOptimize class *
    -keepclassmembers class * {
        @com.facebook.soloader.DoNotOptimize *;
    }
    # Do not strip SoLoader class and init method
    -keep public class com.facebook.soloader.SoLoader {
        public static void init(android.content.Context, int);
    }

    -dontwarn okio.**
    -dontwarn com.squareup.okhttp.**
    -dontwarn okhttp3.**
    -dontwarn javax.annotation.**
    -dontwarn com.android.volley.toolbox.**
    -dontwarn com.facebook.infer.**

    #---------------------------------------------------------------------------------------------------ucrop图片剪裁
    #-dontwarn com.yalantis.ucrop**
    #-keep class com.yalantis.ucrop** { *; }
    #-keep interface com.yalantis.ucrop** { *; }
    #--------------------------------------------------------------------------------------------------aria
    -dontwarn com.arialyy.aria.**
    -keep class com.arialyy.aria.**{*;}
    -keep class **$$DownloadListenerProxy{ *; }
    -keep class **$$UploadListenerProxy{ *; }
    -keep class **$$DownloadGroupListenerProxy{ *; }
    -keep class **$$DGSubListenerProxy{ *; }
    -keepclasseswithmembernames class * {
        @Download.* <methods>;
        @Upload.* <methods>;
        @DownloadGroup.* <methods>;
    }

    #-----------------------------------------------------------------------------------------------------------------jxl
    #这里有问题，混淆报错
    #-dontwarn jxl.**
    #-keep class jxl.Sheet.**{*;}
    #-keep class jxl.Workbook.**{*;}
    #-keepattributes EnclosingMethod



