package indi.ayun.mingwork_all.retrofit2.okhttp;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class ProgressRequestBody extends RequestBody {
    private final RequestBody requestBody;
    private final ProgressRequestListener progressListener;
    private BufferedSink bufferedSink;

    public ProgressRequestBody(RequestBody requestBody, ProgressRequestListener progressListener) {
        this.requestBody = requestBody;
        this.progressListener = progressListener;
    }

    public MediaType contentType() {
        return this.requestBody.contentType();
    }

    public long contentLength() throws IOException {
        return this.requestBody.contentLength();
    }

    public void writeTo(BufferedSink sink) throws IOException {
        if (this.bufferedSink == null) {
            this.bufferedSink = Okio.buffer(this.sink(sink));
        }

        this.requestBody.writeTo(this.bufferedSink);
        this.bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            long bytesWritten = 0L;
            long contentLength = 0L;

            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (this.contentLength == 0L) {
                    this.contentLength = ProgressRequestBody.this.contentLength();
                }

                this.bytesWritten += byteCount;
                ProgressRequestBody.this.progressListener.onRequestProgress(this.bytesWritten, this.contentLength, this.bytesWritten == this.contentLength);
            }
        };
    }
}
