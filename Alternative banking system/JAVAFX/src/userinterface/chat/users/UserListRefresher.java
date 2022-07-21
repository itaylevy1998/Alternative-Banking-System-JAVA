package userinterface.chat.users;


import javafx.beans.property.BooleanProperty;
import okhttp3.*;
import userinterface.utils.HttpUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

import static userinterface.Constants.*;
import static userinterface.Constants.ADMIN_PULL_INFORMATION_RESOURCE;


public class UserListRefresher extends TimerTask {

    private final Consumer<String> httpRequestLoggerConsumer;
    private final Consumer<List<String>> usersListConsumer;
    private int requestNumber;
    private final BooleanProperty shouldUpdate;


    public UserListRefresher(BooleanProperty shouldUpdate, Consumer<String> httpRequestLoggerConsumer, Consumer<List<String>> usersListConsumer) {
        this.shouldUpdate = shouldUpdate;
        this.httpRequestLoggerConsumer = httpRequestLoggerConsumer;
        this.usersListConsumer = usersListConsumer;
        requestNumber = 0;
    }

    @Override
    public void run() {

        if (!shouldUpdate.get()) {
            return;
        }

        final int finalRequestNumber = ++requestNumber;
        httpRequestLoggerConsumer.accept("About to invoke: " + USERS_LIST + " | Users Request # " + finalRequestNumber);
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + USERS_LIST)
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .build();
        HttpUtil.runAsync(request, false, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");
            }

            @Override
            public void onResponse(Call call,  Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonArrayOfUsersNames = response.body().string();
                    httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Response: " + jsonArrayOfUsersNames);
                    String[] usersNames = GSON_INSTANCE.fromJson(jsonArrayOfUsersNames, String[].class);
                    usersListConsumer.accept(Arrays.asList(usersNames));
                }
                response.body().close();
            }
        });
    }
}
