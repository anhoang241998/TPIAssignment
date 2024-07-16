package com.annguyenhoang.tpiassignment.views.tourist_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annguyenhoang.tpiassignment.core.network.ApiException
import com.annguyenhoang.tpiassignment.core.network.NetworkChecker
import com.annguyenhoang.tpiassignment.core.network.getStringResFromException
import com.annguyenhoang.tpiassignment.models.remote.responses.AttractionResponse
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

    var currentSelectedLang = "zh-tw"
        private set

    private val _uiState = MutableStateFlow(TouristUIState())
    val uiState get() = _uiState.asStateFlow()

    init {
        getAttractionsTourists(currentSelectedLang)
    }

    fun getAttractionsTourists(lang: String, forceRefresh: Boolean = false) = viewModelScope.launch(mainDispatcher) {
        if (currentSelectedLang == lang && _uiState.value.tourists.isNotEmpty() && !forceRefresh) return@launch
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
                        tourists = mapResponseToTouristsUIState(touristRes.data),
                        isLoading = false,
                        currentTotalItems = touristRes.data?.size ?: 0,
                        totalTourist = touristRes.total ?: 0,
                        currentPage = 1,
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
                            isLoading = false,
                        )
                    }
                }
            }
        )
    }

    fun loadMoreTourists() = viewModelScope.launch(mainDispatcher) {
        if (_uiState.value.totalTourist == 0) return@launch
        if (_uiState.value.currentTotalItems >= _uiState.value.totalTourist) return@launch

        _uiState.update { it.copy(isLoadMore = true) }

        val nextPage = _uiState.value.currentPage + 1

        if (!networkChecker.isOnline()) {
            _uiState.update {
                it.copy(
                    errorMsgFromServer = null,
                    error = UiText.StringResource(ApiException.NoInternetConnection.getStringResFromException()),
                    isLoadMore = false
                )
            }
            return@launch
        }

        val response = withContext(ioDispatcher) {
            repository.getAllAttractions(currentSelectedLang, page = nextPage)
        } ?: run {
            _uiState.update {
                it.copy(
                    errorMsgFromServer = null,
                    error = UiText.StringResource(ApiException.Other.getStringResFromException()),
                    isLoadMore = false
                )
            }
            return@launch
        }

        response.fold(
            onSuccess = { touristRes ->
                _uiState.update {
                    val currentTotalItems = (touristRes.data?.size ?: 0) + it.currentTotalItems
                    val newTourists = it.tourists + mapResponseToTouristsUIState(touristRes.data)

                    it.copy(
                        tourists = newTourists,
                        totalTourist = touristRes.total ?: 0,
                        isLoadMore = false,
                        error = null,
                        errorMsgFromServer = null,
                        currentPage = nextPage,
                        currentTotalItems = currentTotalItems
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
                            isLoadMore = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            errorMsgFromServer = null,
                            error = UiText.StringResource(exception.getStringResFromException()),
                            isLoadMore = false
                        )
                    }
                }
            }
        )
    }

    private fun mapResponseToTouristsUIState(touristsRes: List<AttractionResponse>?): List<Tourist> {
        return touristsRes?.map { res ->
            Tourist(
                id = (res.id ?: -1).toString(),
                touristTitle = res.name ?: "",
                touristDescription = res.introduction ?: "",
                imageUrl = res.image?.firstOrNull()?.src ?: ""
            )
        } ?: emptyList()
    }

}