package com.yf.smarttemplate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yf.smarttemplate.R
import com.yf.smarttemplate.adapter.TemplateAdapter
import com.yf.smarttemplate.constants.Params
import com.yf.smarttemplate.debugLog
import com.yf.smarttemplate.sample.SampleContainer
import kotlinx.android.synthetic.main.fragment_main.*

private const val ARG_PARAM1 = "param1"

/**
 * Created by yf on 2019-05-24.
 */
class MainFragment : Fragment() {

    private var sampleContainer: SampleContainer? = null

    /**
     * `true`表示HomeFragment
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sampleContainer = it.getSerializable(ARG_PARAM1) as SampleContainer
        }
        sampleContainer ?: throw IllegalArgumentException("SampleContainer is null")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        debugLog("MainFragment onViewCreated")

        val activity = activity as? AppCompatActivity ?: return
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(object : DividerItemDecoration(activity, layoutManager.orientation) {})
        recyclerView.adapter = TemplateAdapter(activity, sampleContainer!!)
    }

    companion object {
        @JvmStatic
        fun newInstance(sampleContainer: SampleContainer,title:String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, sampleContainer)
                    putString(Params.KEY_FRAGMENT_TITLE, title)
                }
            }
    }
}