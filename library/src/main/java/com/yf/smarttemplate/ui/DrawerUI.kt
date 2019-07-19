package com.yf.smarttemplate.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewManager
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.yf.smarttemplate.R
import com.yf.smarttemplate.constants.Params
import com.yf.smarttemplate.fragment.DocumentFragment
import com.yf.smarttemplate.fragment.replaceFragment
import com.yf.smarttemplate.sample.SlidingDrawer
import org.jetbrains.anko.custom.ankoView

/**
 * 初始化左边抽屉控件的内容
 */
internal fun AppCompatActivity.initNavigationView(slidingDrawer: SlidingDrawer, navigationView: NavigationView) {
    // 设置header
    navigationView.inflateHeaderView(R.layout.nav_header)
    // 设置显示的列表内容
    slidingDrawer.drawerList.forEach {
        navigationView.menu.add(it.groupId, it.itemId, 0, it.title)
        // 设置图标
        navigationView.menu.findItem(it.itemId).setIcon(it.iconRes ?: R.drawable.jetpack_logo_small)
    }
    // 设置点击事件
    navigationView.setNavigationItemSelectedListener { menuItem ->
        slidingDrawer.drawerList.first {
            // 找到点击的menu
            it.itemId == menuItem.itemId
        }.let { item ->
            findViewById<DrawerLayout>(R.id.drawer)?.closeDrawers()
            when {
                item.execution != null -> {
                    // 直接运行代码块
                    item.execution!!.invoke(this)
                }
                item.openClazz != null -> {
                    // 开启activity或fragment
                    when {
                        Activity::class.java.isAssignableFrom(item.openClazz!!) -> {
                            startActivity(
                                Intent(this, item.openClazz)
                            )
                        }
                        Fragment::class.java.isAssignableFrom(item.openClazz!!) -> {
                            val fragment = item.openClazz!!.newInstance() as Fragment
                            fragment.arguments = Bundle().apply {
                                putString(Params.KEY_FRAGMENT_TITLE, item.title)
                            }
                            replaceFragment(fragment)
                        }
                    }
                }
                item.markdownAssetFileName != null -> {
                    replaceFragment(DocumentFragment.newInstance(item.markdownAssetFileName, item.title))
                }
            }
        }
        false
    }
}

/**
 * 对NavigationView增加anko支持
 */
internal inline fun ViewManager.navigationView(theme: Int = 0, init: NavigationView.() -> Unit) =
    ankoView({ NavigationView(it) }, theme, init)