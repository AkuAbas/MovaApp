package com.example.mova.local

import javax.inject.Inject

class LocalRepository @Inject constructor(private val dao: MovieDAO) {
    fun addContent(movie: Movie) = dao.addContent(movie)

    fun addContentId(listedContent: ListedContent) = dao.addListedContentId(listedContent)

    suspend fun deleteContentId(listedContent: ListedContent) =
        dao.deleteListedContentId(listedContent)

    suspend fun isContentInList(detailId: String) = dao.isContentInList(detailId)

    fun getAllContent() = dao.getAllContent()

    fun searchList(query: String) = dao.searchList(query)

    suspend fun deleteContent(movie: Movie) = dao.deleteContent(movie)
}