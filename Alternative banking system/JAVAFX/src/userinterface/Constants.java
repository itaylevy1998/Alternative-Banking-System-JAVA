package userinterface;

import com.google.gson.Gson;

public class Constants {
    public final static String FULL_PATH_DOMAIN = "http://localhost:8080/Server_Web";
    public final static String LOGIN_RESOURCE = "/login";
    public final static String ADMIN_PULL_INFORMATION_RESOURCE = "/Admin-Pull-Information-Servlet";
    public final static String ADMIN_INCREASE_YAZ_RESOURCE = "/Admin-Increase-Yaz-Servlet";
    public final static String ADMIN_ACTIVATE_REWIND_RESOURCE = "/Admin-Activate-Rewind-Servlet";
    public final static String ADMIN_DEACTIVATE_REWIND_RESOURCE = "/Admin-Deactivate-Rewind-Servlet";
    public final static String ADMIN_REWIND_TIME_RESOURCE = "/Admin-Rewind-Time-Servlet";
    public final static String CUSTOMER_PULL_INFORMATION_RESOURCE = "/Customer-Pull-Information-Servlet";
    public final static String CUSTOMER_PULL_CATEGORIES_RESOURCE = "/Categories-Pull-Servlet";
    public final static String CREATE_LOAN_RESOURCE = "/Create-Loan-Servlet";
    public final static String CHECK_INLAY_INPUT_RESOURCE = "/Check-Inlay-Input-Servlet";
    public final static String CUSTOMER_INLAY_FILTER_RESOURCE = "/Customer-Inlay-Filter-Pull-Servlet";
    public final static String CUSTOMER_MAKE_INLAY_RESOURCE = "/Customer-Make-Inlay-Servlet";
    public final static String CUSTOMER_CHARGE_MONEY = "/Customer-Charge-Money-Servlet";
    public final static String CUSTOMER_WITHDRAW_MONEY = "/Customer-Withdraw-Money-Servlet";
    public final static String CUSTOMER_PAYMENT_INFO_RESOURCE = "/Customer-Pull-Payments-Servlet";
    public final static String CUSTOMER_CLOSE_LOAN_RESOURCE = "/Customer-Close-Loan-Servlet";
    public final static String CUSTOMER_MAKE_ACTIVE_PAYMENT_RESOURCE = "/Customer-Make-Active-Payment-Servlet";
    public final static String CUSTOMER_MAKE_RISK_PAYMENT_RESOURCE = "/Customer-Make-Risk-Payment-Servlet";
    public final static String CUSTOMER_BUYSELL_PULL_RESOURCE = "/Customer-BuySell-Pull-Servlet";
    public final static String CUSTOMER_SELL_LOANS_RESOURCE = "/Customer-Sell-Loans-Servlet";
    public final static String CUSTOMER_BUY_LOAN_RESOURCE = "/Customer-Buy-Loan-Servlet";
    public final static String USERS_LIST = "/usersList";
    public final static String SEND_CHAT_LINE = "/pages/chatroom/sendChat";
    public final static String CHAT_LINES_LIST = "/chat";
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");

    public final static Gson GSON_INSTANCE = new Gson();
    public final static String RESPONSE_ERROR = "Unable to connect the server!";

    public static final String CHAT_PARAMETER = "userstring";
    public static final String CHAT_VERSION_PARAMETER = "chatVersion";
    public static final int INT_PARAMETER_ERROR = Integer.MIN_VALUE;
    public final static String CHAT_LINE_FORMATTING = "%tH:%tM:%tS | %.10s: %s%n";
    public final static int REFRESH_RATE = 1000;
    public final static String UPLOAD_FILE = "/upload-file";
    public final static String USERNAME = "userName";
    public final static String AMOUNT = "Amount";
    public final static int INVALID = -1;
    public final static int DIFFERENT = -2;
    public final static String REWIND = "Read Only";
    public final static String NOTREWIND = "Active";




}
