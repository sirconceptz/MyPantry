package com.hermanowicz.pantry.domain.settings

import androidx.core.util.PatternsCompat
import com.hermanowicz.pantry.utils.enums.EmailValidation
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() : (CharSequence) -> EmailValidation {
    override fun invoke(email: CharSequence): EmailValidation {
        return if (email.isEmpty()) {
            EmailValidation.EMPTY
        } else if (PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailValidation.VALID
        } else {
            EmailValidation.INVALID
        }
    }
}
