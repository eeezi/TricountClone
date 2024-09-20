package com.eeezi.tricountclone.util;

import com.eeezi.tricountclone.model.Member;

public class MemberContext {
    private static final ThreadLocal<Member> memberThreadLocal = new ThreadLocal<>();

    public static void setCurrentMember(Member member) {
        memberThreadLocal.set(member);
    }
    public static Member getCurrentMember() {return memberThreadLocal.get();}
    public static void clear() { memberThreadLocal.remove(); }
}
