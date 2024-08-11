package com.bytedesk.core.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.bytedesk.core.util.BDCoreConstant;

/**
 * https://developer.android.com/topic/libraries/architecture/room.html#entities
 * // @Index(value = {"msgid"}, unique = true),
 *
 * @author bytedesk.com
 */
@Entity(tableName = "message", indices = {@Index("current_uid")})
public class MessageEntity {

//    /**
//     * 服务器消息id
//     * TODO: 去除服务器端消息id，替换
//     */
//    @PrimaryKey
//    public Long id;

    /**
     * 客户端本地id
     */
    @ColumnInfo(name = "local_id")
    private String localId;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "mid")
    public String mid;

    /**
     * 消息所属工作组wid
     * 访客接入会话之前，用于通知工作组内的相关人员
     */
    @ColumnInfo(name = "wid")
    private String wid;

    /**
     * 一对一会话接收方的uid
     */
    @ColumnInfo(name = "cid")
    private String cid;

    /**
     * 群组会话工作组的gid
     */
    @ColumnInfo(name = "gid")
    private String gid;

    /**
     * 消息类型
     *
     * TODO：枚举，
     * text/image/voice/video/shortvideo/location/
     * link/event/notification
     */
    @ColumnInfo(name = "by_type")
    private String type;

    /**
     * 会话类型：访客会话thread、联系人一对一会话contact，群组会话group
     */
    @ColumnInfo(name = "session_type")
    private String sessionType;

    /**
     * 来源客户端
     */
    @ColumnInfo(name = "client")
    private String client;

    /**
     * 文本消息
     * FIXME: 长度不够，待调整
     */
    @ColumnInfo(name = "content")
    private String content;

    /**
     * 图片消息
     * 微信pic_url，web版容易引起跨域访问问题，所以要使用image_url
     */
    @ColumnInfo(name = "pic_url")
    private String picUrl;

    /**
     * 存储在自己服务器之后的url
     */
    @ColumnInfo(name = "image_url")
    private String imageUrl;

    /**
     * 发送图片本地路径，仅用于本地存储，首先判断是否为空，非空则可使用
     */
    @ColumnInfo(name = "local_image_path")
    private String localImagePath;

    /**
     * 文件消息类型：文件url
     * 文件类型通过format标示
     */
    @ColumnInfo(name = "file_url")
    private String fileUrl;

    /**
     * 发送文件本地路径，仅用于本地存储，首先判断是否为空，非空则可使用
     */
    @ColumnInfo(name = "local_file_path")
    private String localFilePath;

    /**
     * 文件名
     */
    @ColumnInfo(name = "file_name")
    private String fileName;

    /**
     * 文件大小
     */
    @ColumnInfo(name = "file_size")
    private String fileSize;

    /**
     * 语音消息
     * 图片+语音+视频+短视频 公用字段
     */
    @ColumnInfo(name = "media_id")
    private String mediaId;

    /**
     * 语音格式amr等
     */
    @ColumnInfo(name = "format")
    private String format;

    /**
     * 语音url
     */
    @ColumnInfo(name = "voice_url")
    private String voiceUrl;

    /**
     * 发送语音本地路径，仅用于本地存储，首先判断是否为空，非空则可使用
     */
    @ColumnInfo(name = "local_voice_path")
    private String localVoicePath;

    /**
     * 语音长度
     */
    @ColumnInfo(name = "length")
    private int length = 0;

    /**
     * 是否已经播放过
     */
    @ColumnInfo(name = "is_played")
    private boolean played = false;

    /**
     * 阅后即焚
     */
    @ColumnInfo(name = "destroy_after_reading")
    private boolean destroyAfterReading = false;

    /**
     * n秒后阅后即焚
     */
    @ColumnInfo(name = "destroy_after_length")
    private int destroyAfterLength = 0;

    /**
     * 阅后即焚消息已读时长
     */
    @ColumnInfo(name = "read_length")
    private int readLength = 0;

    /**
     * 视频消息 & 短视频消息
     */
    @ColumnInfo(name = "thumb_media_id")
    private String thumbMediaId;

    /**
     * 视频和短视频 url
     */
    @ColumnInfo(name = "video_or_short_url")
    private String videoOrShortUrl;

    /**
     * 视频和短视频 thumb url
     */
    @ColumnInfo(name = "video_or_short_thumb_url")
    private String videoOrShortThumbUrl;

    /**
     * 地理位置消息
     */
    @ColumnInfo(name = "location_x")
    private double locationX;

    @ColumnInfo(name = "location_y")
    private double locationY;

    @ColumnInfo(name = "scale")
    private double scale;

    @ColumnInfo(name = "label")
    private String label;

    /**
     * 链接消息
     */
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "url")
    private String url;

    /**
     * 消息创建时间
     */
    @ColumnInfo(name = "created_at")
    private String createdAt;

    /**
     * 消息发送状态
     */
    @ColumnInfo(name = "status")
    private String status;

    /**
     * 消息发送人
     */
    private String uid;
    private String username;
    private String nickname;
    private String avatar;

    /**
     * 消息所属会话tid
     */
    @ColumnInfo(name = "thread_tid")
    private String threadTid;

    /**
     * 消息对应会话的visitor uid
     */
    @ColumnInfo(name = "visitor_uid")
    private String visitorUid;

    /**
     * 当前登录用户Uid
     */
    @ColumnInfo(name = "current_uid")
    private String currentUid;

    /**
     * 是否是访客
     */
    private boolean visitor;

    /**
     * 是否标记删除
     */
    @ColumnInfo(name = "is_mark_deleted")
    private boolean markDeleted = false;


    public MessageEntity() {

    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public String getVideoOrShortUrl() {
        return videoOrShortUrl;
    }

    public void setVideoOrShortUrl(String videoOrShortUrl) {
        this.videoOrShortUrl = videoOrShortUrl;
    }

    public String getVideoOrShortThumbUrl() {
        return videoOrShortThumbUrl;
    }

    public void setVideoOrShortThumbUrl(String videoOrShortThumbUrl) {
        this.videoOrShortThumbUrl = videoOrShortThumbUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isVisitor() {
        return visitor;
    }

    public void setVisitor(boolean visitor) {
        this.visitor = visitor;
    }

    public String getCurrentUid() {
        return currentUid;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }

    public String getVisitorUid() {
        return visitorUid;
    }

    public void setVisitorUid(String visitorUid) {
        this.visitorUid = visitorUid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getThreadTid() {
        return threadTid;
    }

    public void setThreadTid(String threadTid) {
        this.threadTid = threadTid;
    }

    public String getLocalImagePath() {
        return localImagePath;
    }

    public void setLocalImagePath(String localImagePath) {
        this.localImagePath = localImagePath;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public String getLocalVoicePath() {
        return localVoicePath;
    }

    public void setLocalVoicePath(String localVoicePath) {
        this.localVoicePath = localVoicePath;
    }

    public boolean isMarkDeleted() {
        return markDeleted;
    }

    public void setMarkDeleted(boolean markDeleted) {
        this.markDeleted = markDeleted;
    }

    public boolean isDestroyAfterReading() {
        return destroyAfterReading;
    }

    public void setDestroyAfterReading(boolean destroyAfterReading) {
        this.destroyAfterReading = destroyAfterReading;
    }

    public int getDestroyAfterLength() {
        return destroyAfterLength;
    }

    public void setDestroyAfterLength(int destroyAfterLength) {
        this.destroyAfterLength = destroyAfterLength;
    }

    public int getReadLength() {
        return readLength;
    }

    public void setReadLength(int readLength) {
        this.readLength = readLength;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    // 暂时不用枚举Enum
    // https://developer.android.com/topic/performance/memory.html#Overhead
    // You should strictly avoid using enums on Android.
    public static final int TYPE_TEXT_ID =  0;
    public static final int TYPE_TEXT_SELF_ID =  1;
    public static final int TYPE_IMAGE_ID = 2;
    public static final int TYPE_IMAGE_SELF_ID = 3;
    public static final int TYPE_VOICE_ID = 4;
    public static final int TYPE_VOICE_SELF_ID = 5;
    public static final int TYPE_VIDEO_ID = 6;
    public static final int TYPE_VIDEO_SELF_ID = 7;
    public static final int TYPE_SHORT_VIDEO_ID = 8;
    public static final int TYPE_SHORT_VIDEO_SELF_ID = 9;
    public static final int TYPE_LOCATION_ID = 10;
    public static final int TYPE_LOCATION_SELF_ID = 11;
    public static final int TYPE_LINK_ID = 12;
    public static final int TYPE_LINK_SELF_ID = 13;
    public static final int TYPE_EVENT_ID = 14;
    public static final int TYPE_EVENT_SELF_ID = 15;
    //
    public static final int TYPE_QUESTIONNAIRE_ID = 16;
    public static final int TYPE_NOTIFICATION_ID = 17;
    public static final int TYPE_COMMODITY_ID = 18;
    //
    public static final int TYPE_RED_PACKET_ID = 19;
    public static final int TYPE_RED_PACKET_SELF_ID = 20;
    //
    public static final int TYPE_FILE_ID =  21;
    public static final int TYPE_FILE_SELF_ID =  22;
    //
    public static final int TYPE_ROBOT_ID =  23;
    public static final int TYPE_ROBOT_SELF_ID =  24;
    //
    public static final int TYPE_CUSTOM_ID = 25;
    //

    //
    public int getTypeId() {
        if (this.type.equals(BDCoreConstant.MESSAGE_TYPE_TEXT) ||
                // 对新会话特殊处理
                this.type.equals(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION_THREAD)) {
            if (isSend())
                return TYPE_TEXT_SELF_ID;
            return TYPE_TEXT_ID;
        }
        else if (this.type.equals(BDCoreConstant.MESSAGE_TYPE_IMAGE)) {
            if (isSend())
                return TYPE_IMAGE_SELF_ID;
            return TYPE_IMAGE_ID;
        }
        else if (this.type.equals(BDCoreConstant.MESSAGE_TYPE_VOICE)) {
            if (isSend())
                return TYPE_VOICE_SELF_ID;
            return TYPE_VOICE_ID;
        }
        else if (this.type.equals(BDCoreConstant.MESSAGE_TYPE_VIDEO)) {
            if (isSend())
                return TYPE_VIDEO_SELF_ID;
            return TYPE_VIDEO_ID;
        }
        else if (this.type.equals(BDCoreConstant.MESSAGE_TYPE_SHORTVIDEO)) {
            if (isSend())
                return TYPE_SHORT_VIDEO_SELF_ID;
            return TYPE_SHORT_VIDEO_ID;
        }
        else if (this.type.equals(BDCoreConstant.MESSAGE_TYPE_LOCATION)) {
            if (isSend())
                return TYPE_LOCATION_SELF_ID;
            return TYPE_LOCATION_ID;
        }
        else if (this.type.equals(BDCoreConstant.MESSAGE_TYPE_LINK)) {
            if (isSend())
                return TYPE_LINK_SELF_ID;
            return TYPE_LINK_ID;
        }
        else if (this.type.equals(BDCoreConstant.MESSAGE_TYPE_EVENT)) {
            if (isSend())
                return TYPE_EVENT_SELF_ID;
            return TYPE_EVENT_ID;
        }
        else if (this.type.equals(BDCoreConstant.MESSAGE_TYPE_QUESTIONNAIRE)) {
            return TYPE_QUESTIONNAIRE_ID;
        }
        else if (this.type.startsWith(BDCoreConstant.MESSAGE_TYPE_NOTIFICATION)) {
            return TYPE_NOTIFICATION_ID;
        }
        else if (this.type.startsWith(BDCoreConstant.MESSAGE_TYPE_COMMODITY)) {
            return TYPE_COMMODITY_ID;
        } else if (this.type.startsWith(BDCoreConstant.MESSAGE_TYPE_RED_PACKET)) {
            if (isSend())
                return TYPE_RED_PACKET_SELF_ID;
            return TYPE_RED_PACKET_ID;
        } else if (this.type.startsWith(BDCoreConstant.MESSAGE_TYPE_CUSTOM)) {
            return TYPE_CUSTOM_ID;
        } else if (this.type.equals(BDCoreConstant.MESSAGE_TYPE_FILE)) {
            if (isSend())
                return TYPE_FILE_SELF_ID;
            return TYPE_FILE_ID;
        } else if (this.type.equals(BDCoreConstant.MESSAGE_TYPE_ROBOT)) {
            if (isSend())
                return TYPE_ROBOT_SELF_ID;
            return TYPE_ROBOT_ID;
        }

        return TYPE_NOTIFICATION_ID;
    }

    /**
     * 判断消息是否为当前登录用户发送
     *
     * @return
     */
    public boolean isSend() {
        if (null != currentUid)
            return currentUid.equals(uid);
        return false;
    }

}

