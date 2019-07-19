package com.yf.smarttemplate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.yf.smarttemplate.ui.MainUI
import com.yf.smarttemplate.R
import com.yf.smarttemplate.constants.Params

/**
 * 添加home fragment
 */
internal fun AppCompatActivity.addHomeFragment() {
    supportFragmentManager.beginTransaction()
        .add(
            R.id.fragmentContainer,
            MainFragment.newInstance(MainUI.originTemplateContainer, MainUI.appTitle)
        )
        .commit()
}


/**
 * 替换全局内容fragment，并且加入后退栈
 */
internal fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        addToBackStack(null)
        replace(R.id.fragmentContainer, fragment)
        commitAllowingStateLoss()
    }
}

/**
 * fragment后退
 */
internal fun AppCompatActivity.popFragment() {
    if (supportFragmentManager.backStackEntryCount > 0) {
        supportFragmentManager.popBackStack()
    }
}

internal fun AppCompatActivity.registerFragmentLifecycle() {
    supportFragmentManager.registerFragmentLifecycleCallbacks(MyFragmentLifecycle(), true)
}

/**
 * fragment生命周期回调，目前为了设置toolbar的title
 */
class MyFragmentLifecycle : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)

        (f.context as? AppCompatActivity)?.supportActionBar?.title =
            f.arguments?.getString(Params.KEY_FRAGMENT_TITLE) ?: MainUI.appTitle
    }
}