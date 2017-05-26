package com.liuwu.enums;

/**
 * Created by liuyuanzhou on 8/1/16.
 */
public enum MessageType {
    SYSTEM_MSG(0x00,"系统消息"),
    P2P_MSG(0x01, "二人会话消息"),
    GROUP_MSG(0x02, "群消息"),
    MATCH_TEAM_GAME_OVER_MSG(0x03,"游戏结束界面消息"),
    NORMAL_TEAM_MSG(0x04, "普通队伍消息"),
    REFEREE_TEAM_MSG(0x08, "裁判界面队伍消息"),
    NORMAL_ROOM_TEAM_MSG(0x10, "房间队伍消息"),
    PUBLIC_ROOM_MSG(0x20, "房间公共消息"),
    PUBLIC_CHANNEL_MSG(0x30,"頻道公共消息"),
    ANCHOR_CHANNEL_MSG(0x35,"主播频道消息"),
    ROMM_SYSTEM_MSG(0x05, "房间系统消息"),
    CLUB_CHANNEL_MSG(0x06,"俱乐部频道消息"),
    CLUB_TEAM_CHANNEL_MSG(0x07,"战队房间消息"),
    COMMON_MATCH_TEAM_MSG(0X09, "通用赛事队伍消息")
    ;

    MessageType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;

    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
