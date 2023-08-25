package com.hermanowicz.pantry.navigation.features.settings.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hermanowicz.pantry.data.model.AppSettings
import com.hermanowicz.pantry.domain.account.DeleteUserAccountUseCase
import com.hermanowicz.pantry.domain.settings.ClearDatabaseUseCase
import com.hermanowicz.pantry.domain.settings.ExportDatabaseToCloudUseCase
import com.hermanowicz.pantry.domain.settings.FetchUserEmailOrUnloggedUseCase
import com.hermanowicz.pantry.domain.settings.ObserveAppSettingsUseCase
import com.hermanowicz.pantry.domain.settings.ReCreateNotificationsForAllProductsUseCase
import com.hermanowicz.pantry.domain.settings.UpdateAppSettingsUseCase
import com.hermanowicz.pantry.domain.settings.ValidateEmailUseCase
import com.hermanowicz.pantry.domain.utils.CheckIsUserLoggedUseCase
import com.hermanowicz.pantry.utils.enums.CameraMode
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.hermanowicz.pantry.utils.enums.EmailValidation
import com.hermanowicz.pantry.utils.enums.QrCodeSize
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SettingsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: SettingsViewModel
    private val mockObserveAppSettingsUseCase: ObserveAppSettingsUseCase = mockk()
    private val mockUpdateAppSettingsUseCase: UpdateAppSettingsUseCase = mockk()
    private val mockClearDatabaseUseCase: ClearDatabaseUseCase = mockk()
    private val mockValidateEmailUseCase: ValidateEmailUseCase = mockk()
    private val mockExportDatabaseToCloudUseCase: ExportDatabaseToCloudUseCase = mockk()
    private val mockFetchUserEmailOrUnloggedUseCase: FetchUserEmailOrUnloggedUseCase = mockk()
    private val mockDeleteUserAccountUseCase: DeleteUserAccountUseCase = mockk()
    private val mockCheckIsUserLoggedUseCase: CheckIsUserLoggedUseCase = mockk()
    private val mockReCreateNotificationsForAllProductsUseCase: ReCreateNotificationsForAllProductsUseCase =
        mockk()

    private val userEmail = "test@example.com"
    private val isUserLogged = true
    private val appSettings = AppSettings(
        databaseMode = DatabaseMode.LOCAL.name,
        cameraMode = CameraMode.REAR.name,
        qrCodeSize = QrCodeSize.BIG.name,
        daysToNotifyBeforeExpiration = 3.0f,
        emailForNotifications = "test@example.com",
        pushNotifications = true,
        emailNotifications = true,
        pushNotificationsChanged = false
    )

    @Before
    fun setup() {
        coEvery { mockObserveAppSettingsUseCase() } returns flowOf(appSettings)
        coEvery { mockFetchUserEmailOrUnloggedUseCase() } returns userEmail
        coEvery { mockUpdateAppSettingsUseCase(any()) } returns Unit
        coEvery { mockValidateEmailUseCase(userEmail) } returns EmailValidation.VALID
        coEvery { mockReCreateNotificationsForAllProductsUseCase(DatabaseMode.LOCAL, DatabaseMode.ONLINE) } returns Unit
        coEvery { mockCheckIsUserLoggedUseCase() } returns isUserLogged
        Dispatchers.setMain(testDispatcher)
        viewModel = SettingsViewModel(
            mockObserveAppSettingsUseCase,
            mockUpdateAppSettingsUseCase,
            mockClearDatabaseUseCase,
            mockValidateEmailUseCase,
            mockExportDatabaseToCloudUseCase,
            mockFetchUserEmailOrUnloggedUseCase,
            mockDeleteUserAccountUseCase,
            mockCheckIsUserLoggedUseCase,
            mockReCreateNotificationsForAllProductsUseCase
        )
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `observeAppSettings updates settingsState`() {
        viewModel.observeAppSettings()

        assertEquals(appSettings.databaseMode, viewModel.settingsState.value.databaseMode)
        assertEquals(appSettings.cameraMode, viewModel.settingsState.value.cameraToScanCodes)
        assertEquals(appSettings.qrCodeSize, viewModel.settingsState.value.sizeQrCodes)
        assertEquals(
            appSettings.daysToNotifyBeforeExpiration,
            viewModel.settingsState.value.daysToNotifyBeforeExpiration
        )
        assertEquals(userEmail, viewModel.settingsState.value.userEmailOrUnlogged)
        assertEquals(
            appSettings.emailNotifications,
            viewModel.settingsState.value.emailNotifications
        )
        assertEquals(
            appSettings.emailForNotifications,
            viewModel.settingsState.value.emailAddressForNotifications
        )
        assertEquals(appSettings.pushNotifications, viewModel.settingsState.value.pushNotifications)
        assertEquals(isUserLogged, viewModel.settingsState.value.isUserLogged)
    }

    @Test
    fun `onChangeDatabaseMode updates databaseMode`() {
        viewModel.onChangeDatabaseMode(DatabaseMode.ONLINE.name)
        assertEquals(DatabaseMode.ONLINE.name, viewModel.settingsState.value.databaseMode)
    }

    @Test
    fun `onChangeCameraMode updates cameraToScanCodes`() {
        viewModel.onChangeCameraMode("NEW_CAMERA_MODE")
        assertEquals("NEW_CAMERA_MODE", viewModel.settingsState.value.cameraToScanCodes)
    }

    @Test
    fun `onChangeQrCodeSizeMode updates sizeQrCodes`() {
        viewModel.onChangeQrCodeSizeMode("NEW_QR_CODE_SIZE")
        assertEquals("NEW_QR_CODE_SIZE", viewModel.settingsState.value.sizeQrCodes)
    }

    @Test
    fun `onChangeDaysToNotifyBeforeExpiration updates daysToNotifyBeforeExpiration`() {
        viewModel.onChangeDaysToNotifyBeforeExpiration(7.0f)
        assertEquals(7.0f, viewModel.settingsState.value.daysToNotifyBeforeExpiration, 0.0f)
    }

    @Test
    fun `onChangePushNotificationsEnabled updates pushNotifications and pushNotificationsStateChanged`() {
        viewModel.onChangePushNotificationsEnabled(true)
        assertEquals(true, viewModel.settingsState.value.pushNotifications)
        assertEquals(true, viewModel.settingsState.value.pushNotificationsStateChanged)
    }

    @Test
    fun `onChangeEmailNotifications updates emailNotifications`() {
        viewModel.onChangeEmailNotifications(true)
        assertEquals(true, viewModel.settingsState.value.emailNotifications)
    }
}
