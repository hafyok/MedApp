@file:Suppress("DEPRECATION")

package com.example.workmanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workmanager.data.AppDB
import com.example.workmanager.data.MedicamentDao
import com.example.workmanager.data.MedicamentEntity
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {

    private lateinit var database: AppDB
    private lateinit var dao: MedicamentDao

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDB::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.dao
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun testInsertAndRetrieveData() = runBlocking {
        val medicament = MedicamentEntity(
            0,
            name = "Aspirin",
            amount = 3F,
            1702934859L
        )


        dao.insertMedicament(medicament)

        val retrieved = dao.getMedicaments().blockingObserve()
        assertEquals(1, retrieved?.size)
        assertEquals("Aspirin", retrieved?.get(0)?.name)

    }
}

private fun <T> LiveData<T>.blockingObserve(): T? = runBlocking {
    var value: T? = null
    val latch = CountDownLatch(1)

    val observer = Observer<T> { t ->
        value = t
        latch.countDown()
    }

    // Наблюдение на главном потоке
    withContext(Dispatchers.Main) {
        observeForever(observer)
    }

    latch.await(2, TimeUnit.SECONDS)

    // Удаление наблюдателя
    withContext(Dispatchers.Main) {
        removeObserver(observer)
    }

    return@runBlocking value
}