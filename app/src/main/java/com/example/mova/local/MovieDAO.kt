package com.example.mova.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {

    @Insert
    fun addContent(movie: com.example.mova.local.Movie)

    @Query("SELECT * FROM movie_table")
    fun getAllContent(): Flow<List<com.example.mova.local.Movie>>

    @Insert
    fun addListedContentId(listedContent: ListedContent)

    @Delete
    suspend fun deleteListedContentId(listedContent: ListedContent)

    @Query("SELECT COUNT(*) FROM listed_content_table where listed_content_id == :detailId")
    suspend fun isContentInList(detailId: String): Int

    @Query("SELECT * FROM movie_table WHERE movie_title LIKE '%' || :query || '%' ")
    fun searchList(query: String): Flow<List<Movie>>

    @Delete
    suspend fun deleteContent(movie: Movie)
}