package com.lckiss.baselib.data.net

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.lckiss.baselib.data.protocol.DownloadMission
import zlc.season.rxdownload3.core.Mission
import zlc.season.rxdownload3.core.RealMission
import zlc.season.rxdownload3.database.SQLiteActor

/**
 * RxDownload3下载数据库支持拓展
 */
class DownloadSqlLiteActor(context: Context): SQLiteActor(context) {

    companion object {
        private const val ID = "id"
        private const val ICON = "icon"
        private const val DISPLAYNAME = "displayName"
        private const val PACKAGENAME = "packageName"
        private const val RELEASEKEYHASH = "releaseKeyHash"
        private const val STATUS_FLAG = "status_flag"
        private const val VERSIONNAME = "versionName"
        private const val AUTOSTART = "needAutoStart"
    }

    override fun provideCreateSql(): String {
        val formatStr = "CREATE TABLE %s (\n" +
                "%s TEXT PRIMARY KEY NOT NULL,\n" +
                "%s TEXT NOT NULL,\n" +
                "%s TEXT,\n" +
                "%s TEXT,\n" +
                "%s INTEGER,\n" +
                "%s TEXT,\n" +
                "%s TEXT,\n" +
                "%s INTEGER,\n" +
                //id
                "%s INTEGER,\n" +
                "%s TEXT,\n" +
                "%s TEXT,\n" +
                "%s TEXT,\n" +
                "%s TEXT,\n" +
                "%s TEXT,\n" +
                "%s INTEGER)"

        return String.format(formatStr, TABLE_NAME,
                TAG,
                URL,
                SAVE_NAME,
                SAVE_PATH,
                RANGE_FLAG,
                CURRENT_SIZE,
                TOTAL_SIZE,
                STATUS_FLAG,
                ID,
                ICON,
                DISPLAYNAME,
                PACKAGENAME,
                RELEASEKEYHASH,
                VERSIONNAME,
                AUTOSTART)
    }

    override fun onCreate(mission: RealMission): ContentValues {
        val cv = super.onCreate(mission)
        if (mission.actual is DownloadMission) {
            val downloadMission = mission.actual as DownloadMission
            cv.put(ID, downloadMission.id)
            cv.put(ICON, downloadMission.icon)
            cv.put(DISPLAYNAME, downloadMission.displayName)
            cv.put(PACKAGENAME, downloadMission.packageName)
            cv.put(RELEASEKEYHASH, downloadMission.releaseKeyHash)
            cv.put(VERSIONNAME, downloadMission.versionName)
            cv.put(AUTOSTART, downloadMission.needAutoStart)
        }
        return cv
    }

    override fun onGetAllMission(cursor: Cursor): Mission {
        val mission = super.onGetAllMission(cursor)
        val mMission = DownloadMission(mission)
        mMission.id=cursor.getInt(cursor.getColumnIndexOrThrow(ID))
        mMission.icon=cursor.getString(cursor.getColumnIndexOrThrow(ICON))
        mMission.displayName=cursor.getString(cursor.getColumnIndexOrThrow(DISPLAYNAME))
        mMission.packageName= cursor.getString(cursor.getColumnIndexOrThrow(PACKAGENAME))
        mMission.releaseKeyHash=cursor.getString(cursor.getColumnIndexOrThrow(RELEASEKEYHASH))
        mMission.status=cursor.getInt(cursor.getColumnIndexOrThrow(STATUS_FLAG))
        mMission.versionName=cursor.getString(cursor.getColumnIndexOrThrow(VERSIONNAME))
        mMission.needAutoStart= cursor.getInt(cursor.getColumnIndexOrThrow(AUTOSTART))
        return mMission
    }


}