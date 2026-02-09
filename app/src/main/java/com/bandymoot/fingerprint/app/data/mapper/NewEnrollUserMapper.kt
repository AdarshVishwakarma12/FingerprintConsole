package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import org.json.JSONObject

fun NewEnrollUser.toJson(): JSONObject {
    return JSONObject().apply {
        put("name", name)
        put("employeeId", employeeId)
        put("email", email)
        put("phone", phone)
        put("department", department)
        put("customTimezone", "+05:30") // a fixed value!
    }
}