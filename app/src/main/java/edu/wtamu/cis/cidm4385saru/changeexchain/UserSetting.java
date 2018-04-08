package edu.wtamu.cis.cidm4385saru.changeexchain;

import java.util.UUID;

public class UserSetting {
    private UUID mId;
    private String mUserName;
    private int mTimeFormat;
    private String mCurrencyPreference;
    private String mColorMode;

    public UserSetting() {
        this(UUID.randomUUID());
    }

    public UserSetting(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public int getTimeFormat() {
        return mTimeFormat;
    }

    public void setTimeFormat(int mTimeFormat) {
        this.mTimeFormat = mTimeFormat;
    }

    public String getCurrencyPreference() {
        return mCurrencyPreference;
    }

    public void setCurrencyPreference(String mCurrencyPreference) {
        this.mCurrencyPreference = mCurrencyPreference;
    }

    public String getColorMode() {
        return mColorMode;
    }

    public void setColorMode(String mColorMode) {
        this.mColorMode = mColorMode;
    }
}
