package com.yf.smarttemplate.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Gravity
import com.yf.smarttemplate.R
import com.yf.smarttemplate.fragment.popFragment
import com.yf.smarttemplate.sample.SampleContainer
import com.yf.smarttemplate.sample.SlidingDrawer
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.support.v4.drawerLayout

/**
 * Created by yf.
 * @date 2019-06-02
 */
object MainUI {
    /**
     * app名称
     */
    internal var appTitle = "origin"

    /**
     * 原始模板入口
     */
    internal lateinit var originTemplateContainer: SampleContainer
    /**
     * 侧滑抽屉容器
     */
    internal var slidingDrawer: SlidingDrawer? = null
}

/**
 * 替换activity内的布局，
 */
internal fun AppCompatActivity.replaceContentView() {
    drawerLayout {
        id = R.id.drawer
        verticalLayout {
            themedToolbar(R.style.SmartToolBar) {
            }.lparams(matchParent, wrapContent).apply {
                initToolbar(this)
                title = MainUI.appTitle
            }
            // fragment 主内容
            frameLayout {
                id = R.id.fragmentContainer
            }.lparams(matchParent, matchParent)
        }
        if (MainUI.slidingDrawer != null) {
            // 初始化可拉出的抽屉控件
            frameLayout {
                navigationView {
                    initNavigationView(MainUI.slidingDrawer!!, this)
                }.lparams(wrapContent, matchParent)
            }.lparams(width = wrapContent, height = matchParent, gravity = Gravity.START)
        }
    }.apply {
        fitsSystemWindows = true
    }
}

internal fun AppCompatActivity.initToolbar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    // 返回按钮点击事件
    toolbar.setNavigationOnClickListener {
        popFragment()
    }
}


/**
 * 设置toolbar中返回键的显示和隐藏
 * fragment栈中count大于0时才显示，否则认为是在首页
 */
internal fun AppCompatActivity.setToolbarBackListener() {
    supportFragmentManager.addOnBackStackChangedListener {
        val stackCount = supportFragmentManager.backStackEntryCount
        setActionBarBackShow(stackCount > 0)
    }
}


/**
 * 设置ActionBar的返回按钮是否显示
 */
internal fun AppCompatActivity.setActionBarBackShow(isShow: Boolean) {
    supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
    supportActionBar?.setHomeButtonEnabled(isShow)
}


