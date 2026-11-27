package com.jaesung.auction.common;

public final class MaskingUtil {

    private MaskingUtil() {
    }

    /** 이름을 첫 글자만 남기고 가린다. 예: "jaesung" -> "j**" */
    public static String mask(String name) {
        if (name == null) {
            return null;
        }
        if (name.isEmpty()) {
            return "";
        }
        return name.charAt(0) + "**";
    }
}
