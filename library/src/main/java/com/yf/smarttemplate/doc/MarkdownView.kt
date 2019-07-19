package com.yf.smarttemplate.doc

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.WorkerThread
import android.util.AttributeSet
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import java.io.File
import java.util.concurrent.Executors

/**
 * Created by yf.
 * @date 2019-05-26
 */
@SuppressLint("SetJavaScriptEnabled")
class MarkdownView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    companion object {
        private const val ASSERT_URL = "file:///android_asset/"
        private const val FILE_URL = "file:///"
    }

    // 加载html线程
    private val threadExecutor = Executors.newSingleThreadExecutor()


    init {
        //this markdown library needed javascript
        overScrollMode = View.OVER_SCROLL_IF_CONTENT_SCROLLS
        isHorizontalScrollBarEnabled = false
        settings.javaScriptEnabled = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
    }

    /**
     * Loads the given Markdown file to the view
     * @param file
     * - a local store file, It should be a markdown file
     * @param cssUrl
     * - a path to css file. If the file located in the project assets folder
     * then the URL should start with "file:///android_asset/"
     */
    fun loadMarkdownFromFile(file: File, cssUrl: String? = null) {
        // Read file and load markdown
        threadExecutor.execute {
            val text = file.readText()
            // load markdown by text
            loadMarkdown(FILE_URL, text, cssUrl)
        }
    }

    /**
     * Loads the given Markdown file to the view
     * @param file
     * - a local store file, It should be a markdown file
     * @param cssUrl
     * - a path to css file. If the file located in the project assets folder
     * then the URL should start with "file:///android_asset/"
     */
    fun loadMarkdownFromAssets(filePath: String, cssUrl: String? = null) {
        // Read file and load markdown
        threadExecutor.execute {
            val text = context.assets.open(filePath).reader().use { it.readText() }
            loadMarkdown(ASSERT_URL, text, cssUrl)
        }
    }

    /**
     * Load markdown by text,Here we well use marked js convert text to html source
     * @link https://github.com/markedjs/marked
     *
     */
    @WorkerThread
    private fun loadMarkdown(baseUrl: String, text: String?, cssUrl: String? = null) {
        if (text.isNullOrBlank()) {
            post { loadUrl("about:blank") }
        } else {
            // load markdown by text
            val inputStream = context.assets.open("markdown/markdown.html")
            val templateSource = inputStream.bufferedReader().use { it.readText() }
            val html = String.format(templateSource, text)
            post { loadDataWithBaseURL(baseUrl, html, "text/html", "UTF-8", null) }
        }
    }
}