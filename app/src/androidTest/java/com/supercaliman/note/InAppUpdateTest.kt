package com.supercaliman.note

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InAppUpdateTest {
    lateinit var fakeAppUpdateManager: FakeAppUpdateManager

    @Before
    fun setup() {
        fakeAppUpdateManager =
            FakeAppUpdateManager(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
    }

    @Test
    fun testImmediateTemplate() {
        fakeAppUpdateManager.setUpdateAvailable(2)
        ActivityScenario.launch(MainActivity::class.java)

        // Validate that flexible update is prompted to the user.
        assert(fakeAppUpdateManager.isConfirmationDialogVisible)

        // Simulate user's and download behavior.
        fakeAppUpdateManager.userAcceptsUpdate()

        fakeAppUpdateManager.downloadStarts()

        fakeAppUpdateManager.downloadCompletes()
    }

}