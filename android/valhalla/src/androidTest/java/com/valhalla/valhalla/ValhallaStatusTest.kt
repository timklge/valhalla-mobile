package com.valhalla.valhalla

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.valhalla.config.ValhallaConfigBuilder
import com.valhalla.valhalla.config.ValhallaConfigManager
import com.valhalla.valhalla.files.ValhallaFile
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/** Exercises [Valhalla.statusRaw] against the bundled Andorra tile extract. */
@RunWith(AndroidJUnit4::class)
class ValhallaStatusTest {

  private lateinit var valhalla: Valhalla

  @Before
  fun setUp() {
    val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
    val configManager = ValhallaConfigManager(appContext)
    val tarFile = ValhallaFile.usingAsset(appContext, "valhalla_tiles.tar")
    val config = ValhallaConfigBuilder().withTileExtract(tarFile.absolutePath()).build()

    valhalla = Valhalla(appContext, config, configManager)
  }

  @After
  fun tearDown() {
    valhalla.close()
  }

  @Test
  fun testSuccessfulStatus() {
    val response = JSONObject(valhalla.statusRaw("{}"))

    assertTrue(response.has("version"))
    assertTrue(response.has("available_actions"))
  }
}
