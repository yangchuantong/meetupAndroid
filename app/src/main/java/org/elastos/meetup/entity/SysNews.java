package org.elastos.meetup.entity;



import java.io.Serializable;
import java.util.ArrayList;

public class SysNews implements Serializable {
    /**
     * 为了去掉警告 serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private static SysNews sysNews;
    private static ArrayList<SysNews> sysNewsArrayList;
    public static SysNews getInstance(){
        if(sysNews ==null){
            sysNews = new SysNews();
        }
        return sysNews;
    }

    public static SysNews getSysNews() {
        return sysNews;
    }

    public static void setSysNews(SysNews sysNews) {
        SysNews.sysNews = sysNews;
    }

    public static ArrayList<SysNews> getSysNewsArrayList() {
        return sysNewsArrayList;
    }

    public static void setSysNewsArrayList(ArrayList<SysNews> sysNewsArrayList) {
        SysNews.sysNewsArrayList = sysNewsArrayList;
    }

    private String id;

    private String alias;

    private String title;

    private String type;

    private String json;
    private boolean read;

    private long sendTime;

    private String sendStatus;

    private String tenantId;

    private String content;
    private long updateTime;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json == null ? null : json.trim();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
