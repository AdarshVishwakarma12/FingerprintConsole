package com.example.figerprintconsole.app.data.mapper

import com.example.figerprintconsole.app.domain.model.NewEnrollUser
import org.json.JSONObject

fun NewEnrollUser.toJson(): JSONObject {
    return JSONObject().apply {
        put("name", name)
        put("employeeId", employeeId)
        put("email", email)
        put("phone", phone)
        put("department", department)
        put("customTimezone", timeZone)
    }
}