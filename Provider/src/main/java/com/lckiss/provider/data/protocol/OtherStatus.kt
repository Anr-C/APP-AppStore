package com.lckiss.provider.data.protocol

import zlc.season.rxdownload3.core.Status

/**
 * 扩展RxDownload不存在的状态
 */
class OtherStatus {
    class UnInstall : Status(0, 0, false)

    class Installed : Status(0, 0, false)

    class NeedUpgrade : Status(0, 0, false)

    class Deleted : Status(0, 0, false)

    class Exists : Status(0, 0, false)
}