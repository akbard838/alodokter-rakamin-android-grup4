package com.example.androidgroup4.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.utils.ContextProvider

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), BaseView {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> ViewBinding

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        ContextProvider.initialize(applicationContext)

        initIntent()
        initObservable()
        initUI()
        initAction()
        initProcess()
    }

    override fun setupToolbar(toolbar: Toolbar?, isChild: Boolean, title: String) {
        toolbar?.let {
            setSupportActionBar(toolbar)
        }

        if (supportActionBar != null) {
            supportActionBar!!.title = title
            supportActionBar!!.setDisplayHomeAsUpEnabled(isChild)
        }
    }

    abstract fun initIntent()
    abstract fun initUI()
    abstract fun initAction()
    abstract fun initProcess()
    abstract fun initObservable()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}