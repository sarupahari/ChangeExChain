package edu.wtamu.cis.cidm4385saru.changeexchain.Classes;

import java.util.UUID;

public class UserSetting {
    private int mTimeFormat;
    private String mCurrencyPreference;
    private String mColorMode;

    public UserSetting(){

    }

    public int getTimeFormat() {
        return mTimeFormat;
    }

    public void setTimeFormat(int timeFormat) {
        this.mTimeFormat = timeFormat;
    }

    public String getCurrencyPreference() {
        return mCurrencyPreference;
    }

    public void setCurrencyPreference(String currencyPreference) {
        this.mCurrencyPreference = currencyPreference;
    }

    public String getColorMode() {
        return mColorMode;
    }

    public void setColorMode(String colorMode) {
        this.mColorMode = colorMode;
    }

    public static UserSetting createDefault(){
        UserSetting us = new UserSetting();
        us.setTimeFormat(12);
        us.setColorMode("normal");
        us.setCurrencyPreference("local");

        return us;
    }
}
