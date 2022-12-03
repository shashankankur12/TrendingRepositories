package com.example.trendingrepositories.data.pojo

data class RepoDetails(
    val author: String,
    val currentPeriodStars: Int,
    val avatar: String,
    val description: String,
    val forks: Int,
    val language: String,
    val languageColor: String,
    val stars: Int,
    val name: String,
    val url: String,
){
    var isSelected :Boolean= false
}