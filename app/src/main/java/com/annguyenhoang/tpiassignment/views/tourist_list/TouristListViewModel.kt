package com.annguyenhoang.tpiassignment.views.tourist_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annguyenhoang.tpiassignment.core.network.ApiException
import com.annguyenhoang.tpiassignment.core.network.NetworkChecker
import com.annguyenhoang.tpiassignment.core.network.getStringResFromException
import com.annguyenhoang.tpiassignment.repositories.AttractionsRepository
import com.annguyenhoang.tpiassignment.utils.UiText
import com.annguyenhoang.tpiassignment.views.tourist_list.models.Tourist
import com.annguyenhoang.tpiassignment.views.tourist_list.models.TouristUIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TouristListViewModel(
    private val repository: AttractionsRepository,
    private val networkChecker: NetworkChecker,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: MainCoroutineDispatcher
) : ViewModel() {

    private var currentSelectedLang = "zh-tw"
    private val _uiState = MutableStateFlow(TouristUIState())
    val uiState get() = _uiState.asStateFlow()

    init {
        getAllAttractions(currentSelectedLang)
    }

    fun getAllAttractions(lang: String) = viewModelScope.launch(mainDispatcher) {
        if (currentSelectedLang == lang && _uiState.value.tourists.isNotEmpty()) return@launch
        _uiState.update { it.copy(isLoading = true) }

        if (!networkChecker.isOnline()) {
            _uiState.update {
                it.copy(
                    errorMsgFromServer = null,
                    error = UiText.StringResource(ApiException.NoInternetConnection.getStringResFromException()),
                    isLoading = false
                )
            }
            return@launch
        }

        val response = withContext(ioDispatcher) {
            repository.getAllAttractions(lang)
        } ?: run {
            _uiState.update {
                it.copy(
                    errorMsgFromServer = null,
                    error = UiText.StringResource(ApiException.Other.getStringResFromException()),
                    isLoading = false
                )
            }
            return@launch
        }

        response.fold(
            onSuccess = { touristRes ->
                currentSelectedLang = lang
                _uiState.update {
                    it.copy(
                        tourists = touristRes.map { res ->
                            Tourist(
                                id = res.id ?: -1,
                                touristTitle = res.name ?: "",
                                touristDescription = res.introduction ?: "",
                                imageUrl = res.image?.firstOrNull()?.src ?: ""
                            )
                        },
                        isLoading = false,
                        error = null,
                        errorMsgFromServer = null
                    )
                }
            },
            onFailure = { throwable ->
                val exception = throwable as ApiException
                if (exception is ApiException.ServerError) {
                    _uiState.update {
                        val errorMsg = exception.detail ?: ""
                        it.copy(
                            error = null,
                            errorMsgFromServer = errorMsg,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            errorMsgFromServer = null,
                            error = UiText.StringResource(exception.getStringResFromException()),
                            isLoading = false
                        )
                    }
                }
            }
        )
    }

}