package com.yf.smarttemplate.sample

import android.app.Activity
import androidx.fragment.app.Fragment

/**
 * demo的包装容器，内存有sample列表和嵌套列表
 *
 * Created by yf.
 * @date 2019-05-26
 */
open class SampleContainer : SampleItem() {

    /**
     * demo item列表
     */
    internal val sampleList: ArrayList<SampleItem> = ArrayList()

    /**
     * 将一个item加入列表
     */

    fun <T : Activity> activityItem(clazz: Class<T>, closure: ActivitySampleItem<T>.() -> Unit) {
        sampleList.add(ActivitySampleItem(clazz).apply(closure))
    }

    /**
     * 使用fragment需注意，默认将fragment加入后退栈
     * 所以在fragment中想后退，不要直接finish，请调用`activity.supportFragmentManager.popBackStack()`
     */
    fun <T : Fragment> fragmentItem(clazz: Class<T>, closure: FragmentSampleItem<T>.() -> Unit) {
        sampleList.add(FragmentSampleItem(clazz).apply(closure))
    }

    /**
     * 直接运行闭包
     */
    fun executionItem(closure: ExecutionSampleItem.() -> Unit) {
        sampleList.add(ExecutionSampleItem().apply(closure))
    }

    /**
     * 添加一个子列表
     */
    fun itemList(container: SampleContainer) {
        sampleList.add(container)
    }

    /**
     * 嵌套列表
     */
    fun itemList(closure: SampleContainer.() -> Unit) {
        val container = SampleContainer().apply(closure)
        itemList(container)
    }
}