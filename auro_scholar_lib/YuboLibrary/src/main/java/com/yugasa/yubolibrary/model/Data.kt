package com.yugasa.yubolibrary.model

class Data(
    var userId: String,
    var client_id: String,
    var train: String,
    var unsubsribe: String,
    var text: String,
    var node: String,
    session: Session,
    category: String,
    basic: String,
    update_tree: String,
    update_story: String,
    var typeMsg : String? = "outgoing") {
    private var session: Session
    var category: String
    var update_tree: String
    var update_story: String

    fun getSession(): Session {
        return session
    }

    fun setSession(session: Session) {
        this.session = session
    }

    init {
        this.session = session
        this.category = category
        this.update_tree = update_tree
        this.update_story = update_story
    }
}