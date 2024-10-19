package ProjectSpringBoot.Project.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstants {
    public static final String API_ADMIN = "/api/admin";
    public static final String API_BANKER = "/api/banker";
    public static final String API_CLIENT = "/api/client";
    public static final String BANK_ACCOUNT_CREATE = "/bankaccount/create";
    public static final String CLIENT_CREATE = "/client/create";
    public static final String CLIENT_CARD_CREATE = "/cards/create";
    public static final String UPDATE_REQUESTS = "/requests/update";
    public static final String DELETE_CLIENT = "/client/delete/{id}";
    public static final String UPDATE_CLIENT = "/client/update";
    public static final String BANK_ACCOUNTS_GET = "/bankaccount/get";
    public static final String GET_REQUESTS = "/requests/get";
    public static final String GET_TRANSACTIONS = "/transactions/get";
    public static final String CARD_CREATE = "/cards/create/{bankAccountId}";
    public static final String CREATE_TRANSACTION = "/transaction/create";
    public static final String CREATE_REQUEST = "request/create";
    public static final String BANK_ACCOUNT_GET = "/bankaccount/get/{userId}";
    public static final String CARDS_GET = "/cards/get/{userId}";
    public static final String GET_TRANSACTION= "/transaction/get/{bankAccountId}";
    public static final String GET_REQUEST = "/request/get/{userId}";
    public static final String CREATE_BANKER = "/banker/create";
    public static final String DELETE_BANKER = "/banker/delete/{id}";
    public static final String UPDATE_BANKER = "/banker/update";

}
