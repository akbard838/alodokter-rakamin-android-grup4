package com.example.androidgroup4.data.article.model

import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.utils.emptyString

data class ArticleResponse(
	val id: Int?,
	val title: String?,
	val body: String?,
	val media_url: String?
) {
	fun toArticle(): Article{
		return Article(
			id = id ?: 0,
			title = title ?: emptyString(),
			description = body ?: emptyString(),
			imageUrl = media_url ?: emptyString()
		)
	}
}
