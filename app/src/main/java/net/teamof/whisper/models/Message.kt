package net.teamof.whisper.models

import com.squareup.moshi.JsonClass
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.io.Serializable
import java.util.*

@Entity
@JsonClass(generateAdapter = true)
data class Message(
	@Id
	var local_id: Long = 0,
	var id: Long = 0,
	var user_id: Long = 0,
	var to_user_id: Long = 0,
	var content: String = "",
	// this is not for first or second tick , this is just a helper to prevent sending request
	// server for
	var verify_delivery_report: Boolean = false,
	var created_at: Date? = null,
	var updated_at: Date? = null,
) : Serializable

@JsonClass(generateAdapter = true)
data class MessagesArray(
	val messages: List<Message>
)

open class RMessage(
	@PrimaryKey // 2.
	var local_id: String = ObjectId().toHexString(), // 3.
	var id: Long = 0,
	var user_id: Long = 0,
	var to_user_id: Long = 0,
	var content: String = "",
	// this is not for first or second tick , this is just a helper to prevent sending request
	// server for
	var verify_delivery_report: Boolean = false,
	var created_at: Date? = null,
	var updated_at: Date? = null,
) : RealmObject()