package com.supers.basic.domain.entity;

import com.supers.basic.domain.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity   // 告诉JPA这是一个实体类
@Table(name = "iam_user")  // 指定映射具体数据库表
public class IamUser extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_LOGIN = "loginName";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_ORGANIZATION_ID = "organizationId";
    public static final String FIELD_HASH_PASSWORD = "hashPassword";
    public static final String FIELD_REAL_NAME = "realName";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_INTERNATIONAL_TEL_CODE = "internationalTelCode";
    public static final String FIELD_LANGUAGE = "language";
    public static final String FIELD_TIME_ZONE = "timeZone";
    public static final String FIELD_IS_ENABLED = "isEnabled";
    public static final String FIELD_IS_LOCKED = "isLocked";
    public static final String FIELD_USER_TYPE = "userType";

    @Id
    private Long id;

    private String loginName;

    private String email;

    private Long organizationId;

    private String hashPassword;

    private String realName;

    private String phone;

    private String internationalTelCode;

    private String language;

    private String timeZone;

    private Boolean isEnabled;

    private Boolean isLocked;

    private String userType;
}
