package com.zufar.submision3.api

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {
    @Test
    fun testDoRequest() {
        val apiRepository = mock(ApiRepository::class.java)
        // https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}