import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.photo.CreateAndGetPhotoFileUseCase
import com.hermanowicz.pantry.domain.photo.DecodePhotoFromGalleryUseCase
import com.hermanowicz.pantry.domain.photo.FetchPhotoBitmapUseCase
import com.hermanowicz.pantry.domain.photo.GetPhotoFileNameUseCase
import com.hermanowicz.pantry.domain.photo.SetPhotoFileUseCase
import com.hermanowicz.pantry.domain.product.GetProductListByIdsUseCase
import com.hermanowicz.pantry.domain.product.UpdatePhotoInProductListUseCase
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.navigation.features.addPhoto.ui.AddPhotoViewModel
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

@ExperimentalCoroutinesApi
class AddPhotoViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddPhotoViewModel

    // Mocks
    private val getProductListByIdsUseCase = mockk<GetProductListByIdsUseCase>()
    private val observeDatabaseModeUseCase = mockk<ObserveDatabaseModeUseCase>()
    private val createAndGetPhotoFileUseCase = mockk<CreateAndGetPhotoFileUseCase>()
    private val getPhotoFileNameUseCase = mockk<GetPhotoFileNameUseCase>()
    private val updatePhotoInProductListUseCase = mockk<UpdatePhotoInProductListUseCase>()
    private val decodePhotoFromGalleryUseCase = mockk<DecodePhotoFromGalleryUseCase>()
    private val fetchPhotoBitmapUseCase = mockk<FetchPhotoBitmapUseCase>()
    private val setPhotoFileUseCase = mockk<SetPhotoFileUseCase>()
    private val savedStateHandle = mockk<SavedStateHandle>()

    // Test data
    private val testProductIdList = listOf(1, 2, 3)
    private val testProductList = listOf(
        Product(id = 1, photoName = "photo1"),
        Product(id = 2, photoName = "photo2"),
        Product(id = 3, photoName = "photo3")
    )
    private val testDatabaseMode = DatabaseMode.LOCAL
    private val testBitmap = mockk<Bitmap>()
    private val testPhotoFileName = "test_photo"
    private val testPhotoFile = mockk<File>()

    @Before
    fun setup() {
        coEvery {
            fetchPhotoBitmapUseCase(any(), any())
        } returns testBitmap
        every<String?> { savedStateHandle["productIdList"] } returns testProductIdList.joinToString(
            separator = ";"
        )
        every { observeDatabaseModeUseCase() } returns flowOf(testDatabaseMode)
        coEvery {
            getProductListByIdsUseCase(
                testDatabaseMode,
                testProductIdList
            )
        } returns flowOf(testProductList)
        coEvery { createAndGetPhotoFileUseCase() } returns testPhotoFile
        every { getPhotoFileNameUseCase() } returns testPhotoFileName

        viewModel = AddPhotoViewModel(
            getProductListByIdsUseCase,
            observeDatabaseModeUseCase,
            createAndGetPhotoFileUseCase,
            getPhotoFileNameUseCase,
            updatePhotoInProductListUseCase,
            decodePhotoFromGalleryUseCase,
            fetchPhotoBitmapUseCase,
            setPhotoFileUseCase,
            savedStateHandle
        )
    }

    @After
    fun cleanup() {
        clearMocks(
            getProductListByIdsUseCase,
            observeDatabaseModeUseCase,
            createAndGetPhotoFileUseCase,
            getPhotoFileNameUseCase,
            updatePhotoInProductListUseCase,
            decodePhotoFromGalleryUseCase,
            fetchPhotoBitmapUseCase,
            setPhotoFileUseCase,
            savedStateHandle
        )
    }

    @Test
    fun `fetchProducts should update the UI state with an empty product list and onNavigateBack=true when an exception is thrown`() =
        runBlocking {
            // Given
            coEvery {
                observeDatabaseModeUseCase()
            } throws Exception()

            // When
            viewModel.fetchProducts(testProductIdList)
            val uiState = viewModel.uiState.first()

            // Then
            verify {
                observeDatabaseModeUseCase()
            }
            assert(uiState.productList.isEmpty())
            assert(uiState.onNavigateBack)
        }

    @Test
    fun `createAndGetPhotoFile should return the photo file obtained from the use case`() {
        // Given
        every { createAndGetPhotoFileUseCase() } returns testPhotoFile

        // When
        val result = viewModel.createAndGetPhotoFile()

        // Then
        verify { createAndGetPhotoFileUseCase() }
        assert(result == testPhotoFile)
    }

    @Test
    fun `setPhotoPreview should update the UI state with the provided bitmap`() {
        // When
        viewModel.setPhotoPreview(testBitmap)
        val uiState = viewModel.uiState.value

        // Then
        assert(uiState.photoPreview == testBitmap)
    }

    @Test
    fun `onGoToPermissionSettings should update the UI state with the provided boolean value`() {
        // When
        viewModel.onGoToPermissionSettings(true)
        val uiState = viewModel.uiState.value

        // Then
        assert(uiState.goToPermissionSettings)
    }

    @Test
    fun `onClickAddPhoto should update the UI state with the provided boolean value`() {
        // When
        viewModel.onClickAddPhoto(true)
        val uiState = viewModel.uiState.value

        // Then
        assert(uiState.onClickAddPhoto)
    }

    @Test
    fun `onNavigateBack should update the UI state with the provided boolean value`() {
        // When
        viewModel.onNavigateBack(true)
        val uiState = viewModel.uiState.value

        // Then
        assert(uiState.onNavigateBack)
    }

    @Test
    fun `onPhotoSavedCorrectlyInGallery should set the photo preview with the decoded bitmap`() {
        // Given
        every { decodePhotoFromGalleryUseCase(testPhotoFileName) } returns testBitmap

        // When
        viewModel.onPhotoSavedCorrectlyInGallery(testPhotoFileName)
        val uiState = viewModel.uiState.value

        // Then
        verify { decodePhotoFromGalleryUseCase(testPhotoFileName) }
        assert(uiState.photoPreview == testBitmap)
    }

    @Test
    fun `getPhotoFileName should return the photo file name obtained from the use case`() {
        // Given
        every { getPhotoFileNameUseCase() } returns testPhotoFileName

        // When
        val result = viewModel.getPhotoFileName()

        // Then
        verify { getPhotoFileNameUseCase() }
        assert(result == testPhotoFileName)
    }
}
