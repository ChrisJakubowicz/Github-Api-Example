package com.brightwheel.exercise.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.brightwheel.exercise.R
import com.brightwheel.exercise.ui.github.RepoListFragment

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        supportActionBar?.title = getString(R.string.top_starred_repos)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<RepoListFragment>(R.id.fragment_container_view)
            }
        }
    }
}
