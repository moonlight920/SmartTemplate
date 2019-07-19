package com.yf.smarttemplate.adapter

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.yf.smarttemplate.R
import com.yf.smarttemplate.constants.Params
import com.yf.smarttemplate.fragment.MainFragment
import com.yf.smarttemplate.fragment.replaceFragment
import com.yf.smarttemplate.sample.ActivitySampleItem
import com.yf.smarttemplate.sample.ExecutionSampleItem
import com.yf.smarttemplate.sample.FragmentSampleItem
import com.yf.smarttemplate.sample.SampleContainer

/**
 * 首页RecyclerView的adapter
 *
 * Created by yf on 2019-05-26.
 */
class TemplateAdapter(private val activity: AppCompatActivity, private val sampleContainer: SampleContainer) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflate = LayoutInflater.from(activity)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return object :
            RecyclerView.ViewHolder(layoutInflate.inflate(android.R.layout.simple_list_item_2, parent, false)) {}
    }

    override fun getItemCount(): Int = sampleContainer.sampleList.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = sampleContainer.sampleList[position]
        // 设置涟漪效果
        holder.itemView.setBackgroundResource(R.drawable.ripple_bg)
        holder.itemView.findViewById<TextView>(android.R.id.text1).text = item.title
        holder.itemView.findViewById<TextView>(android.R.id.text2).text = item.desc
        holder.itemView.setOnClickListener {

            val context = it.context

            when (item) {
                is ActivitySampleItem<*> -> {
                    context.startActivity(Intent(context, item.clazz))
                }
                is FragmentSampleItem<*> -> {
                    val fragment = item.clazz.newInstance() as Fragment
                    fragment.arguments = Bundle().apply {
                        putString(Params.KEY_FRAGMENT_TITLE, item.title)
                    }
                    activity.replaceFragment(fragment)
                }
                is ExecutionSampleItem -> {
                    item.execution?.invoke(context)
                }
                is SampleContainer -> {
                    activity.replaceFragment(MainFragment.newInstance(item, item.title))
                }
            }
        }
    }
}