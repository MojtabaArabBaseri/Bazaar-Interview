package ir.millennium.bazaar.data.repository.remote

import ir.millennium.bazaar.data.dataSource.remote.ApiService
import ir.millennium.bazaar.domain.repository.remote.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteRepositoryImpl(private val apiService: ApiService) : RemoteRepository {

}