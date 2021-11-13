package com.auro.yubolibrary.model

data class ResponseBot(
    val client_id: String,
    val userId: String,
    val default_opt: String,
    val intent: String,
    var train: String,
    var node: String,
    var basic: String,
    var update_tree: String,
    var update_story: String,
    var category: String,
    var unsubsribe: String,
    val possible_conflict: String,
    val replies: List<Reply>?,
    val score: String,
    val session: Session,
    val status: String,
    val text: String,
    val tree: Any?,
    val type_option: Boolean,
    val typeMsg : String? = "incoming") {
    lateinit var strTree: String
    lateinit var objTree: Tree
    data class Reply(
        val link: String,
        val option: String
    )

    data class Tree(
        val default: String,
        val node_id: String,
        val node_name: String,
        val options: List<Option>,
        val text: String,
        val type_of_node: String,
        val type_opt: Boolean) {

    }
}