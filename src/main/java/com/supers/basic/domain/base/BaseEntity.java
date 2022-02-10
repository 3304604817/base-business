package com.supers.basic.domain.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {
    public static final String FIELD_OBJECT_VERSION_NUMBER = "objectVersionNumber";
    public static final String FIELD_CREATE_BY = "createdBy";
    public static final String FIELD_CREATION_DATE = "creationDate";
    public static final String FIELD_LAST_UPDATED_BY = "lastUpdatedBy";
    public static final String FIELD_LAST_UPDATE_BY = "lastUpdateDate";

    private Long objectVersionNumber;

    private Long createdBy;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date creationDate;

    private Long lastUpdatedBy;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date lastUpdateDate;
}
