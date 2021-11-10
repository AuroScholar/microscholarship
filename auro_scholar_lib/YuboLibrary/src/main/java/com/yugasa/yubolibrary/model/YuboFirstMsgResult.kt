package com.yugasa.yubolibrary.model

data class YuboFirstMsgResult(
    val `data`: DataX,
    val message: String,
    val status: String) {
    data class DataX(
        val _id: String,
        val blockStatus: String,
        val btree: Btree,
        val clientId: String,
        val createdAt: String,
        val devType: Any,
        val deviceId: Any,
        val email: String,
        val name: String,
        val otp: Int,
        val password: String,
        val profile_img: String,
        val renderData: RenderData,
        val tree: String,
        val updatedAt: String,
        val userToken: String,
        val username: String) {
        data class Btree(
            val DTree: List<DTree>) {

        }
        data class DTree(
            val default: String,
            val node_id: String,
            val node_name: String,
            val options: List<Option>,
            val text: String,
            val type_of_node: String) {

        }

        data class RenderData(
            val chatLogo: String,
            val chatMsg: String,
            val clientCss: String,
            val clientId: String,
            val logo: String,
            val msg: String,
            val type: String
        )
    }
}