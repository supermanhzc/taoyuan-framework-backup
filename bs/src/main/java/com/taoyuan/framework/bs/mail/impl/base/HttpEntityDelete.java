package com.taoyuan.framework.bs.mail.impl.base;

/**
 * 继承自HttpEntityEnclosingRequestBase，覆盖其中的getMethod方法，使其返回“DELETE”，
 * 使HttpDelete可以发送body信息。
 */
import java.net.URI;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

@NotThreadSafe
public class HttpEntityDelete extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";
    public String getMethod() { return METHOD_NAME; }

    public HttpEntityDelete(final String uri) {
        super();
        setURI(URI.create(uri));
    }
    public HttpEntityDelete(final URI uri) {
        super();
        setURI(uri);
    }
    public HttpEntityDelete() { super(); }
}
