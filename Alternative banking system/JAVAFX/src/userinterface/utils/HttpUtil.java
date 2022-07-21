package userinterface.utils;

import okhttp3.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpUtil {
    private final static SimpleAdminCookie simpleCookieAdmin = new SimpleAdminCookie();
    private final static SimpleAdminCookie simpleCookieCustomer = new SimpleAdminCookie();
    private final static OkHttpClient HTTP_CLIENT_ADMIN = new OkHttpClient.Builder()
            .cookieJar(simpleCookieAdmin)
            .followRedirects(false).build();

    private final static OkHttpClient HTTP_CLIENT_CUSTOMER = new OkHttpClient.Builder()
            .cookieJar(simpleCookieCustomer)
            .followRedirects(false).build();

    public static void runAsync(Request request, boolean isAdmin, Callback callback) {
        Call call;
        if(isAdmin) {
            call = HttpUtil.HTTP_CLIENT_ADMIN.newCall(request);

        }
        else {
            call = HttpUtil.HTTP_CLIENT_CUSTOMER.newCall(request);
        }
        call.enqueue(callback);
    }

}
