package userinterface.chat.chatarea;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import okhttp3.*;
import userinterface.chat.chatarea.model.ChatLinesWithVersion;
import userinterface.utils.HttpUtil;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

import static userinterface.Constants.*;

public class ChatAreaRefresher extends TimerTask {

    private final Consumer<String> httpRequestLoggerConsumer;
    private final Consumer<ChatLinesWithVersion> chatlinesConsumer;
    private final IntegerProperty chatVersion;
    private final BooleanProperty shouldUpdate;
    private final boolean isAdmin;
    private int requestNumber;

    public ChatAreaRefresher(IntegerProperty chatVersion, BooleanProperty shouldUpdate, Consumer<String> httpRequestLoggerConsumer, Consumer<ChatLinesWithVersion> chatlinesConsumer, boolean isAdmin) {
        this.httpRequestLoggerConsumer = httpRequestLoggerConsumer;
        this.chatlinesConsumer = chatlinesConsumer;
        this.chatVersion = chatVersion;
        this.shouldUpdate = shouldUpdate;
        requestNumber = 0;
        this.isAdmin = isAdmin;
    }

    @Override
    public void run() {

        if (!shouldUpdate.get()) {
            return;
        }

        final int finalRequestNumber = ++requestNumber;

        //noinspection ConstantConditions
        String finalUrlInformation = HttpUrl
                .parse(FULL_PATH_DOMAIN + CHAT_LINES_LIST)
                .newBuilder()
                .addQueryParameter("chatVersion", String.valueOf(chatVersion.get()))
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .build();

        httpRequestLoggerConsumer.accept("About to invoke: " + finalUrlInformation + " | Chat Request # " + finalRequestNumber);

        HttpUtil.runAsync(request, isAdmin, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpRequestLoggerConsumer.accept("Something went wrong with Chat Request # " + finalRequestNumber);
            }

            @Override
            public void onResponse( Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String rawBody = response.body().string();
                    httpRequestLoggerConsumer.accept("Response of Chat Request # " + finalRequestNumber + ": " + rawBody);
                    ChatLinesWithVersion chatLinesWithVersion = GSON_INSTANCE.fromJson(rawBody, ChatLinesWithVersion.class);
                    chatlinesConsumer.accept(chatLinesWithVersion);
                } else {
                    httpRequestLoggerConsumer.accept("Something went wrong with Request # " + finalRequestNumber + ". Code is " + response.code());
                }
                response.body().close();
            }
        });

    }

}
