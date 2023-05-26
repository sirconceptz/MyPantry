package com.hermanowicz.pantry.domain.account

import com.google.firebase.auth.FirebaseAuth
import com.hermanowicz.pantry.di.repository.CategoryRepository
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import javax.inject.Inject

class DeleteUserAccountUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val storageLocationRepository: StorageLocationRepository
) : suspend () -> Unit {
    override suspend fun invoke() {
        val firebaseAuth = FirebaseAuth.getInstance()

        productRepository.deleteAllRemote()
        categoryRepository.deleteAllRemote()
        storageLocationRepository.deleteAllRemote()

        firebaseAuth.currentUser?.delete()
    }
}
