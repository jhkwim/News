package com.jhkwim.news

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import io.kotest.core.config.AbstractProjectConfig

object MainThreadConfig : AbstractProjectConfig() {

    override fun beforeAll() {
        super.beforeAll()

        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

        })
    }

    override fun afterAll() {
        super.afterAll()
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

}