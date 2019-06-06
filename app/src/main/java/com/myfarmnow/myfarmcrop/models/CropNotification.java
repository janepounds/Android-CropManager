package com.myfarmnow.myfarmcrop.models;

public class CropNotification {
    public final static String QUERY_KEY_TODAY ="=";
    public final static String QUERY_KEY_OVER_DUE ="<";
    public final static String QUERY_KEY_UPCOMING =">";
    public final static String QUERY_KEY_REPORT_FROM_TODAY ="reportFromToday";
    String id;
    String userId;
    String sourceId;
    String date;
    String message;
    String status;
    String actionDate;
    String type;
    String reportFrom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String  getReportFrom() {
        return reportFrom;
    }

    public void setReportFrom(String reportFrom) {
        this.reportFrom = reportFrom;
    }
}
