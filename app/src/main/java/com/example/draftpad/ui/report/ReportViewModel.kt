package com.example.draftpad.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Report
import com.example.draftpad.network.ReportResponse
import kotlinx.coroutines.launch

enum class ReportApiStatus { LOADING, ERROR, DONE }

class ReportViewModel: ViewModel() {
    private val _status = MutableLiveData<ReportApiStatus>()
    val status: LiveData<ReportApiStatus> = _status

    private val _response = MutableLiveData<ReportResponse>()
    val response: LiveData<ReportResponse> = _response

    init {
        _status.value = ReportApiStatus.LOADING
    }

    private fun reportBook(report: Report) {
        _status.value = ReportApiStatus.LOADING
        viewModelScope.launch {
            try{
                _response.value= ApiClient.retrofitService.report(
                    report.user_id,
                    report.book_id,
                    report.report_type,
                    report.report_reason
                )
                _status.value = ReportApiStatus.DONE
            }
            catch (e: Exception){
                _status.value = ReportApiStatus.ERROR
                _response.value = ReportResponse("error",e.message.toString())
            }
        }
    }

    fun postReport(userId:Int,bookId:Int,reportType:String,reportReason:String){

        val report= Report(
            user_id = userId.toString(),
            book_id = bookId.toString(),
            report_type = reportType,
            report_reason = reportReason
        )
        reportBook(report)
    }

    }
