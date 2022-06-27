package com.fedag.internship.domain.util;

/**
 * interface UrlConstants
 *
 * @author damir.iusupov
 * @since 2022-06-23
 */
public interface UrlConstants {
    String MAIN_URL = "/intership/api";
    String VERSION = "/v1.0";

    String ADMIN = "/admin";
    String USER = "/user";
    String DELETED = "/deleted";

    String ID = "/{id}";
    String CONFIRM_URL = "/confirm";

    String CALENDAR_URL = "/calendar";
    String COMMENT_URL = "/comments";
    String COMPANY_URL = "/companies";
    String SEARCH_BY_NAME_URL = "/search-by-name";
    String PROPOSAL_COMPANY_URL = "/proposal-company";
    String PROPOSAL_STATUS_UNDER_REVIEW_URL = "/set-status-under-review";
    String PROPOSAL_STATUS_REFUSED_URL = "/set-status-refused";
    String PROPOSAL_STATUS_APPROVED_URL = "/set-status-approved";
    String FAVOURITE_COMPANY_URL = "/favourite-companies";
    String FAVOURITE_POSITION_URL = "/favourite-trainee-positions";
    String TASK_URL = "/tasks";
    String POSITION_URL = "/positions";
    String SEARCH_BY_COMPANY_URL = "/search-by-company";
    String USER_URL = "/users";

    String AUTH_URL = "/auth";
    String LOGIN_URL = "/login";
    String LOGOUT_URL = "/logout";

    String CHANGE_PWD_URL = "/change-password";
    String REGISTER_URL = "/registration";
}
