package com.valhalla.valhalla

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.valhalla.config.ValhallaConfigBuilder
import com.valhalla.valhalla.config.ValhallaConfigManager
import com.valhalla.valhalla.files.ValhallaFile
import org.json.JSONArray
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/** Exercises [Valhalla.locateRaw] against the bundled Andorra tile extract. */
@RunWith(AndroidJUnit4::class)
class ValhallaLocateTest {

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
  fun testSuccessfulLocate() {
    val request =
        JSONObject()
            .put("locations", JSONArray().put(JSONObject().put("lat", 42.5063).put("lon", 1.5218)))
            .put("costing", "auto")
            .toString()

    val response = JSONArray(valhalla.locateRaw(request))

    assertEquals(1, response.length())
  }
}
