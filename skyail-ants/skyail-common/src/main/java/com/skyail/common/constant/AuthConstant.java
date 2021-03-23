package com.skyail.common.constant;

public interface AuthConstant {

    String JWT_TOKEN_HEADER = "Authorization";

    String RESOURCE_ROLES_KEY = "RESOURCE_ROLES";

    String AUTHORITY_PREFIX = "ROLE_";

    String AUTHORITY_CLAIM_NAME = "authorities";

    String JWT_USER_ID_KEY = "JWT_USER_ID_KEY";

    String JWT_CLIENT_ID_KEY = "JWT_CLIENT_ID_KEY";

    //注意：字段顺序不能变，必须是这个顺序
    String CLIENT_BASE_SQL = "select client_id,client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove from skyail_client";

    String CLIENT_SELECT_SQL = CLIENT_BASE_SQL + " where client_id = ?";

    String CLIENT_FIND_ALL_SQL = CLIENT_BASE_SQL + " order by client_id";

}
