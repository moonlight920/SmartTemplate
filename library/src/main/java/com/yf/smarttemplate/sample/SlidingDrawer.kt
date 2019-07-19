package com.yf.smarttemplate.sample

import android.content.Context
import androidx.annotation.DrawableRes

class SlidingDrawer {

    /**
     * demo item list
     */
    internal val drawerList: ArrayList<DrawerItem> = ArrayList()

    fun item(closure: DrawerItem.() -> Unit) {
        drawerList.add(DrawerItem().apply {
            itemId = 0x0100 + drawerList.size
            apply(closure)
        })
    }

}

class DrawerItem(
    internal var itemId: Int = 0,
    var groupId: Int = 0,
    @DrawableRes var iconRes: Int? = null,
    var title: String? = null,
    var openClazz: Class<*>? = null,
    var markdownAssetFileName: String? = null
) {
    /**
     * 闭包类型
     */
    internal var execution: ((Context) -> Unit)? = null

    /**
     * DSL 执行这段代码
     */
    fun execute(closure: (Context) -> Unit) {
        execution = closure
    }
}