package edu.wtamu.cis.cidm4385saru.changeexchain.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import edu.wtamu.cis.cidm4385saru.changeexchain.Classes.UserSetting;

public class UserSettingsCursorWrapper extends CursorWrapper{

    public UserSettingsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public UserSetting getUserSettings() {
        String uuidString = getString(getColumnIndex(ChangeExChDbSchema.UserSettingsTable.Cols.UUID));
        String username = getString(getColumnIndex(ChangeExChDbSchema.UserSettingsTable.Cols.USERNAME));
        int timeFormat = getInt(getColumnIndex(ChangeExChDbSchema.UserSettingsTable.Cols.TIMEFORMAT));
        String currencyPreference = getString(getColumnIndex(ChangeExChDbSchema.UserSettingsTable.Cols.CURRENCYPREFERENCE));
        String colorMode = getString(getColumnIndex(ChangeExChDbSchema.UserSettingsTable.Cols.COLORMODE));

        UserSetting userSetting = new UserSetting();
        userSetting.setTimeFormat(timeFormat);
        userSetting.setCurrencyPreference(currencyPreference);
        userSetting.setColorMode(colorMode);

        return userSetting;
    }
}
