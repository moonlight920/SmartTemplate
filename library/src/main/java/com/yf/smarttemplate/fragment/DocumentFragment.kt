package com.yf.smarttemplate.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.yf.smarttemplate.R
import com.yf.smarttemplate.constants.Params
import kotlinx.android.synthetic.main.fragment_document.*

/**
 * Created by yf on 2019-05-24.
 */
internal class DocumentFragment : Fragment() {

    private var mDocumentPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mDocumentPath = it.getString(Params.KEY_DOC_URL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_document, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDocumentPath?.let {
            markdownView.loadMarkdownFromAssets(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(documentPath: String? = "", title: String?) =
            DocumentFragment().apply {
                arguments = Bundle().apply {
                    putString(Params.KEY_DOC_URL, documentPath)
                    putString(Params.KEY_FRAGMENT_TITLE, title)
                }
            }
    }
}