package indi.ayun.mingwork_all.retrofit2.okhttp;

import indi.ayun.mingwork_all.retrofit2.utils.HttpTool;
import indi.ayun.mingwork_all.retrofit2.utils.Platform;
import indi.ayun.mingwork_all.retrofit2.callback.Callback.ProgressDownCallback;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

public class FileDownloadTask {
    private ProgressDownCallback<Void> callback;
    private Platform platform;
    private Call call;

    public FileDownloadTask(Call call, ProgressDownCallback<Void> callback, Platform platform) {
        this.call = call;
        this.callback = callback;
        this.platform = platform;
    }

    public void start() {
        HttpTool.executor.execute(new Runnable() {
            public void run() {
                try {
                    Response response = FileDownloadTask.this.call.execute();
                    long total = response.body().contentLength();
                    FileDownloadTask.this.saveFile(response);
                } catch (final IOException var4) {
                    var4.printStackTrace();
                    FileDownloadTask.this.platform.execute(new Runnable() {
                        public void run() {
                            FileDownloadTask.this.callback.onError(var4, (String)null, (String)null);
                        }
                    });
                }

            }
        });
    }

    public String saveFile(Response response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        //int len = 0;
        Object fos = null;

        try {
            is = response.body().byteStream();
            long total = response.body().contentLength();
            long sum = 0L;

            int len;
            while((len = is.read(buf)) != -1) {
                sum += (long)len;
                System.out.println(sum + "----" + total);
            }

            Object var10 = null;
            return (String)var10;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException var20) {
                ;
            }

            try {
                if (fos != null) {
                    ((FileOutputStream)fos).close();
                }
            } catch (IOException var19) {
                ;
            }

        }
    }
}
