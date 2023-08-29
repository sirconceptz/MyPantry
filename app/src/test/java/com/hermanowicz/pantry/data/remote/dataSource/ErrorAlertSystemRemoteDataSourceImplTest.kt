package com.hermanowicz.pantry.data.remote.dataSource

import com.hermanowicz.pantry.data.model.errorAlertSystem.WPError
import com.hermanowicz.pantry.data.model.errorAlertSystem.WPTitle
import com.hermanowicz.pantry.data.remote.ErrorApiInterface
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ErrorAlertSystemRemoteDataSourceImplTest {

    @Mock
    private lateinit var mockApiInterface: ErrorApiInterface

    private lateinit var dataSource: ErrorAlertSystemRemoteDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dataSource = ErrorAlertSystemRemoteDataSourceImpl(mockApiInterface)
    }

    @Test
    fun `observeAll should return list of errors`() = runBlocking {
        val mockErrorList = listOf(
            WPError(title = WPTitle(rendered = "ERR000001")),
            WPError(title = WPTitle(rendered = "ERR000002"))
        )
        `when`(mockApiInterface.getErrorList()).thenReturn(mockErrorList)

        val result = dataSource.observeAll()
        assert(result == mockErrorList)
    }
}
