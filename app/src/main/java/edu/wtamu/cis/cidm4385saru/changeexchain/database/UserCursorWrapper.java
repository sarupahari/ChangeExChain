package edu.wtamu.cis.cidm4385saru.changeexchain.database;

import android.database.Cursor;
import android.database.CursorWrapper;


import java.util.UUID;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.UserTable;
import edu.wtamu.cis.cidm4385saru.changeexchain.User;

/**
 * Created by sarup on 4/8/2018.
 */

public class UserCursorWrapper extends CursorWrapper {

    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        String uuidString = getString(getColumnIndex(UserTable.Cols.UUID));
        String username = getString(getColumnIndex(UserTable.Cols.USERNAME));
        String password = getString(getColumnIndex(UserTable.Cols.PASSWORD));

        User user = new User(UUID.fromString(uuidString));
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }
}