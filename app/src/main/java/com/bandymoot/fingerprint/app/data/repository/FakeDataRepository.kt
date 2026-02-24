package com.bandymoot.fingerprint.app.data.repository

import com.bandymoot.fingerprint.app.data.local.dao.AttendanceRecordDao
import com.bandymoot.fingerprint.app.data.local.dao.AuditLogDao
import com.bandymoot.fingerprint.app.data.local.dao.AuthenticationLogDao
import com.bandymoot.fingerprint.app.data.local.dao.DeviceDao
import com.bandymoot.fingerprint.app.data.local.dao.FingerprintDao
import com.bandymoot.fingerprint.app.data.local.dao.ManagerDao
import com.bandymoot.fingerprint.app.data.local.dao.OrganizationDao
import com.bandymoot.fingerprint.app.data.local.dao.UserDao
import com.bandymoot.fingerprint.app.data.local.entity.ActionType
import com.bandymoot.fingerprint.app.data.local.entity.AttendanceRecordEntity
import com.bandymoot.fingerprint.app.data.local.entity.AttendanceStatus
import com.bandymoot.fingerprint.app.data.local.entity.AttendanceType
import com.bandymoot.fingerprint.app.data.local.entity.AuditLogEntity
import com.bandymoot.fingerprint.app.data.local.entity.AuthResult
import com.bandymoot.fingerprint.app.data.local.entity.AuthenticationLogEntity
import com.bandymoot.fingerprint.app.data.local.entity.AuthenticationPurpose
import com.bandymoot.fingerprint.app.data.local.entity.DeviceEntity
import com.bandymoot.fingerprint.app.data.local.entity.DeviceStatusEntityEnum
import com.bandymoot.fingerprint.app.data.local.entity.FingerprintEntity
import com.bandymoot.fingerprint.app.data.local.entity.FromEntity
import com.bandymoot.fingerprint.app.data.local.entity.HandType
import com.bandymoot.fingerprint.app.data.local.entity.ManagerEntity
import com.bandymoot.fingerprint.app.data.local.entity.OrganizationEntity
import com.bandymoot.fingerprint.app.data.local.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID
import kotlin.collections.forEach
import kotlin.random.Random

// THIS IS ONLY FOR TESTING OUR APPLICATION!
class FakeDataRepository(
    private val organizationDao: OrganizationDao,
    private val managerDao: ManagerDao,
    private val deviceDao: DeviceDao,
    private val userDao: UserDao,
    private val fingerprintDao: FingerprintDao,
    private val authenticationLogDao: AuthenticationLogDao,
    private val auditLogDao: AuditLogDao,
    private val attendanceRecordDao: AttendanceRecordDao
) {

    // Realistic data generators
    private val firstNames = listOf(
        "James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda",
        "William", "Elizabeth", "David", "Barbara", "Richard", "Susan", "Joseph",
        "Jessica", "Thomas", "Sarah", "Charles", "Karen", "Christopher", "Lisa",
        "Daniel", "Nancy", "Matthew", "Margaret", "Anthony", "Sandra", "Donald",
        "Ashley", "Mark", "Kimberly", "Paul", "Emily", "Steven", "Donna", "Andrew",
        "Michelle", "Kenneth", "Carol", "George", "Amanda", "Joshua", "Melissa"
    )

    private val lastNames = listOf(
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
        "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
        "Thomas", "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson",
        "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson", "Walker",
        "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
        "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell"
    )

    private val departments = listOf(
        "IT Department", "Human Resources", "Finance", "Marketing", "Sales",
        "Operations", "Research & Development", "Customer Support",
        "Administration", "Production", "Quality Assurance", "Logistics"
    )

    private val locations = listOf(
        "Main Building Lobby", "Data Center Entrance", "Server Room",
        "HR Office", "Finance Department", "Marketing Wing",
        "Production Floor", "R&D Lab", "Executive Floor",
        "Warehouse Entrance", "Parking Gate", "Cafeteria"
    )

    private val deviceNames = listOf(
        "Front Door Scanner", "Server Room Access", "Employee Entrance",
        "Parking Terminal", "Admin Building", "Production Line",
        "Warehouse Gate", "Office Main", "Conference Room", "Lab Entrance"
    )

    private val deviceVendors = listOf(
        "BioSec Pro", "FingerTech", "SecureScan", "BioAuth Systems",
        "Digital Guard", "TrueIdentity", "SafeAccess Inc.", "VeriFinger"
    )

    private val purposes = listOf(
        "Check-in", "Check-out", "Access Control", "Time Tracking",
        "Secure Login", "Verification", "Transaction Auth", "Attendance"
    )

    suspend fun seedComprehensiveData(
        organizationName: String = "TechCorp Solutions Inc.",
        managersCount: Int = 8,           // 1 Superior + 7 Regular
        devicesCount: Int = 35,           // 35 devices across locations
        usersCount: Int = 250,            // 250 employees
        fingerprintsPerUser: Int = 2,     // Each user has 2 fingerprints
        authLogsPerDay: Int = 50,         // 50 auth logs per day for 30 days = 1500
        auditLogsCount: Int = 200         // 200 audit logs
    ) = withContext(Dispatchers.IO) {

        println("Starting comprehensive data generation...")

        val orgId = "org-${UUID.randomUUID()}"
        val organization = createOrganization(orgId, organizationName)
        organizationDao.upsert(organization)
        println("Organization created: ${organization.name}")

        val managers = createManagers(orgId, managersCount)
        managers.forEach { managerDao.upsert(it) }
        println("${managers.size} Managers created (1 Superior, ${managers.size - 1} Regular)")

        val devices = createDevices(orgId, devicesCount, managers)
        devices.forEach { deviceDao.upsert(it) }
        println("${devices.size} Devices created across ${locations.size} locations")

        val users = createUsers(orgId, usersCount, managers)
        users.forEach { userDao.upsert(it) }
        println("${users.size} Users created across ${departments.size} departments")

        val fingerprints = createFingerprints(users, devices, fingerprintsPerUser)
        fingerprints.forEach { fingerprintDao.upsert(it) }
        println("${fingerprints.size} Fingerprints enrolled (${fingerprintsPerUser} per user)")

        val authLogs = createAuthenticationLogs(users, devices, fingerprints, authLogsPerDay)
        authLogs.forEach { authenticationLogDao.upsert(it) }
        println("${authLogs.size} Authentication logs created (30 days of activity)")

        val auditLogs = createAuditLogs(orgId, managers, users, devices, auditLogsCount)
        auditLogs.forEach { auditLogDao.upsert(it) }
        println("${auditLogs.size} Audit logs created")

        val attendanceRecord = createAttendanceRecords(orgId, managers, users, devices)
        attendanceRecord.forEach { attendanceRecordDao.upsert(it) }
        println("${attendanceRecord.size} Attendance Record created")

        println("\n🎉 Data Generation Complete!")
        println("📊 Summary:")
        println("   - Organization: 1")
        println("   - Managers: ${managers.size}")
        println("   - Devices: ${devices.size}")
        println("   - Users: ${users.size}")
        println("   - Fingerprints: ${fingerprints.size}")
        println("   - Auth Logs: ${authLogs.size}")
        println("   - Audit Logs: ${auditLogs.size}")
        println("   - Total Records: ${managers.size + devices.size + users.size + fingerprints.size + authLogs.size + auditLogs.size}")
    }

    private fun createOrganization(orgId: String, name: String): OrganizationEntity {
        return OrganizationEntity(
            serverOrganizationId = orgId,
            name = name,
            contactEmail = "contact@${name.toLowerCase().replace(" ", "")}.com",
            licenseKey = "LIC-${UUID.randomUUID().toString().substring(0, 12).toUpperCase()}",
            totalDevices = 50,
            totalUsers = 300,
            isActive = true,
            createdAt = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000) // 30 days ago
        )
    }

    private fun createManagers(orgId: String, count: Int): List<ManagerEntity> {
        val managers = mutableListOf<ManagerEntity>()

        // Superior Manager
        managers.add(
            ManagerEntity(
                serverManagerId = "mgr-superior",
                username = "admin.superior",
                fullName = "Alex Johnson",
                email = "alex.johnson@techcorp.com",
                isActive = true,
                isSuperior = true,
                lastLoginAt = System.currentTimeMillis() - (2 * 60 * 60 * 1000), // 2 hours ago
                createdAt = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000),
                organizationServerId = orgId
            )
        )

        // Regular Managers
        (1 until count).forEach { index ->
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            managers.add(
                ManagerEntity(
                    serverManagerId = "mgr-reg-$index",
                    username = "${firstName.toLowerCase()}.${lastName.toLowerCase().take(3)}",
                    fullName = "$firstName $lastName",
                    email = "${firstName.toLowerCase()}.${lastName.toLowerCase()}@techcorp.com",
                    isActive = Random.Default.nextDouble() > 0.1, // 90% active
                    isSuperior = false,
                    lastLoginAt = System.currentTimeMillis() - Random.Default.nextLong(
                        1,
                        168
                    ) * 60 * 60 * 1000, // 1-168 hours ago
                    createdAt = System.currentTimeMillis() - Random.Default.nextLong(
                        7,
                        30
                    ) * 24 * 60 * 60 * 1000, // 7-30 days ago
                    organizationServerId = orgId
                )
            )
        }

        return managers
    }

    private fun createDevices(orgId: String, count: Int, managers: List<ManagerEntity>): List<DeviceEntity> {
        return (1..count).map { index ->
            val status = when (Random.Default.nextInt(100)) {
                in 0..85 -> DeviceStatusEntityEnum.ACTIVE      // 85% active
                in 86..90 -> DeviceStatusEntityEnum.INACTIVE   // 5% inactive
                in 91..95 -> DeviceStatusEntityEnum.MAINTENANCE // 5% maintenance
                else -> DeviceStatusEntityEnum.OFFLINE  // 5% decommissioned
            }

            val lastSeenOffset = when (status) {
                DeviceStatusEntityEnum.ACTIVE -> Random.Default.nextLong(1, 24) * 60 * 60 * 1000 // 1-24 hours ago
                DeviceStatusEntityEnum.INACTIVE -> Random.Default.nextLong(24, 720) * 60 * 60 * 1000 // 1-30 days ago
                else -> Random.Default.nextLong(168, 720) * 60 * 60 * 1000 // 7-30 days ago
            }

            DeviceEntity(
                serverDeviceId = "dev-$index",
                deviceCode = "FP-${String.format("%03d", index)}-${
                    UUID.randomUUID().toString().substring(0, 6).toUpperCase()
                }",
                secretKey = UUID.randomUUID().toString(),
                name = "${deviceNames.random()} ${if (index % 3 == 0) "#${index / 3}" else ""}",
                location = locations[index % locations.size],
                deviceStatus = status,
                lastSeenAt = System.currentTimeMillis() - lastSeenOffset,
                firmwareVersion = Random.Default.nextLong(1, 4),
                batteryLevel = when (status) {
                    DeviceStatusEntityEnum.ACTIVE -> Random.Default.nextInt(40, 100)
                    else -> Random.Default.nextInt(10, 40)
                },
                enrolledAt = System.currentTimeMillis() - Random.Default.nextLong(
                    15,
                    30
                ) * 24 * 60 * 60 * 1000,
                organizationServerId = orgId,
                enrolledByServerManagerId = managers.random().serverManagerId,
                vendor = deviceVendors.random(),
                supportedAlgorithms = "FP_V${Random.Default.nextInt(1, 3)}",
                templateVersion = "${Random.Default.nextInt(1, 3)}.${Random.Default.nextInt(0, 5)}"
            )
        }
    }

    private fun createUsers(orgId: String, count: Int, managers: List<ManagerEntity>): List<UserEntity> {
        val userCodes = mutableSetOf<String>()

        return (1..count).map { index ->
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            val userCode = "EMP-${String.format("%05d", index)}"
            userCodes.add(userCode)

            UserEntity(
                serverUserId = "usr-$index",
                userCode = userCode,
                fullName = "$firstName $lastName",
                email = "${firstName.toLowerCase()}.${lastName.toLowerCase()}@techcorp.com",
                phone = "+1${String.format("%010d", 5550000000L + index)}",
                department = departments[index % departments.size],
                notes = when (Random.Default.nextInt(20)) {
                    0 -> "Requires special access"
                    1 -> "New employee - training required"
                    2 -> "Temporary access until ${
                        Random.Default.nextInt(
                            1,
                            31
                        )
                    }/${Random.Default.nextInt(1, 13)}"

                    3 -> "Works night shift"
                    4 -> "Has access to restricted areas"
                    else -> null
                },
                isActive = Random.Default.nextDouble() > 0.05, // 95% active
                enrolledAt = System.currentTimeMillis() - Random.Default.nextLong(
                    1,
                    365
                ) * 24 * 60 * 60 * 1000, // 1-365 days ago
                organizationServerId = orgId,
                enrolledByServerManagerId = managers.random().serverManagerId
            )
        }
    }

    private fun createFingerprints(
        users: List<UserEntity>,
        devices: List<DeviceEntity>,
        perUser: Int
    ): List<FingerprintEntity> {
        val fingerprints = mutableListOf<FingerprintEntity>()
        val fingerIndices = listOf(1, 2, 3, 4, 5) // Thumb to Pinky

        users.forEach { user ->
            repeat(perUser) { fingerCount ->
                val fingerIndex = fingerIndices[fingerCount % fingerIndices.size]
                val handType = if (fingerCount % 2 == 0) HandType.RIGHT else HandType.LEFT

                // Simulate some users having poor quality fingerprints
                val qualityScore = when (Random.Default.nextInt(100)) {
                    in 0..85 -> Random.Default.nextInt(75, 100)  // 85% good quality
                    in 86..95 -> Random.Default.nextInt(50, 75)  // 10% medium quality
                    else -> Random.Default.nextInt(30, 50)       // 5% poor quality
                }

                fingerprints.add(
                    FingerprintEntity(
                        serverFingerprintId = "fp-${UUID.randomUUID()}",
                        fingerIndex = fingerIndex,
                        handType = handType,
                        qualityScore = qualityScore,
                        enrolledAt = System.currentTimeMillis() - Random.Default.nextLong(
                            1,
                            90
                        ) * 24 * 60 * 60 * 1000,
                        enrolledOnDeviceCode = devices.random().deviceCode,
                        userServerId = user.serverUserId,
                        deviceServerId = devices.random().serverDeviceId
                    )
                )
            }
        }

        return fingerprints
    }

    private fun createAuthenticationLogs(
        users: List<UserEntity>,
        devices: List<DeviceEntity>,
        fingerprints: List<FingerprintEntity>,
        logsPerDay: Int
    ): List<AuthenticationLogEntity> {
        val authLogs = mutableListOf<AuthenticationLogEntity>()
        val totalDays = 30 // Generate logs for last 30 days
        val activeUsers = users.filter { it.isActive }
        val activeDevices = devices.filter { it.deviceStatus == DeviceStatusEntityEnum.ACTIVE }

        repeat(totalDays) { dayOffset ->
            val dayStart = System.currentTimeMillis() - (dayOffset * 24 * 60 * 60 * 1000)

            // Vary logs per day (more on weekdays, less on weekends)
            val todayLogsCount = if (dayOffset % 7 in 0..4) { // Weekday
                logsPerDay + Random.Default.nextInt(-10, 20)
            } else { // Weekend
                logsPerDay / 4 + Random.Default.nextInt(-5, 10)
            }

            repeat(todayLogsCount) { logIndex ->
                val timestamp = dayStart + Random.Default.nextLong(0, 24 * 60 * 60 * 1000)
                val device = activeDevices.random()
                val user = activeUsers.random()

                // Determine auth result with realistic probabilities
                val result = when (Random.Default.nextInt(100)) {
                    in 0..88 -> AuthResult.SUCCESS         // 89% success rate
                    in 89..93 -> AuthResult.FAILED         // 5% failed
                    in 94..96 -> AuthResult.NO_MATCH       // 3% no match
                    in 97..98 -> AuthResult.LOW_CONFIDENCE // 2% low confidence
                    else -> AuthResult.ERROR               // 1% error
                }

                val confidenceScore = when (result) {
                    AuthResult.SUCCESS -> Random.Default.nextInt(70, 100)
                    AuthResult.LOW_CONFIDENCE -> Random.Default.nextInt(40, 70)
                    else -> Random.Default.nextInt(20, 60)
                }

                val matchedFingerprint = if (result == AuthResult.SUCCESS) {
                    fingerprints.find { it.userServerId == user.serverUserId }?.serverFingerprintId
                } else null

                authLogs.add(
                    AuthenticationLogEntity(
                        serverAuthenticationLogId = "auth-${UUID.randomUUID()}",
                        timestamp = timestamp,
                        result = result,
                        confidenceScore = confidenceScore,
                        attemptDurationMs = when (result) {
                            AuthResult.SUCCESS -> Random.Default.nextLong(200, 800)
                            AuthResult.ERROR -> Random.Default.nextLong(1500, 3000)
                            else -> Random.Default.nextLong(500, 1500)
                        },
                        deviceServerId = device.serverDeviceId,
                        userServerId = user.serverUserId,
                        matchedFingerprintServerId = matchedFingerprint,
                        purpose = AuthenticationPurpose.values().random()
                    )
                )
            }
        }

        return authLogs
    }

    private fun createAuditLogs(
        orgId: String,
        managers: List<ManagerEntity>,
        users: List<UserEntity>,
        devices: List<DeviceEntity>,
        count: Int
    ): List<AuditLogEntity> {
        val auditLogs = mutableListOf<AuditLogEntity>()
        val startTime = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000)

        // Common audit actions with realistic frequencies
        val auditActions = listOf(
            Triple(ActionType.USER_ENROLLED, FromEntity.USER, 15),
            Triple(ActionType.DEVICE_ENROLLED, FromEntity.DEVICE, 10),
            Triple(ActionType.FINGERPRINT_ENROLLED, FromEntity.FINGERPRINT, 25),
            Triple(ActionType.USER_STATUS_CHANGED, FromEntity.USER, 8),
            Triple(ActionType.DEVICE_STATUS_CHANGED, FromEntity.DEVICE, 5),
            Triple(ActionType.LOGIN, FromEntity.MANAGER, 20),
            Triple(ActionType.REPORT_GENERATED, FromEntity.MANAGER, 12),
            Triple(ActionType.SETTINGS_UPDATED, FromEntity.MANAGER, 5)
        )

        val totalWeight = auditActions.sumOf { it.third }

        repeat(count) { index ->
            val randomWeight = Random.Default.nextInt(totalWeight)
            var cumulativeWeight = 0
            var selectedAction: Triple<ActionType, FromEntity, Int>? = null

            for (action in auditActions) {
                cumulativeWeight += action.third
                if (randomWeight < cumulativeWeight) {
                    selectedAction = action
                    break
                }
            }

            val (actionType, entityType, _) = selectedAction ?: auditActions.first()
            val timestamp = startTime + (index * (30L * 24 * 60 * 60 * 1000) / count)
            val manager = managers.random()

            val (description, entityId) = when (actionType) {
                ActionType.USER_ENROLLED -> {
                    val user = users.random()
                    Pair("Enrolled new user: ${user.fullName} (${user.userCode})", user.serverUserId)
                }
                ActionType.DEVICE_ENROLLED -> {
                    val device = devices.random()
                    Pair("Enrolled new device: ${device.name} (${device.deviceCode})", device.serverDeviceId)
                }
                ActionType.FINGERPRINT_ENROLLED -> {
                    val user = users.random()
                    Pair("Enrolled fingerprint for user: ${user.fullName}", user.serverUserId)
                }
                ActionType.USER_STATUS_CHANGED -> {
                    val user = users.random()
                    val status = if (user.isActive) "activated" else "deactivated"
                    Pair("${status.replaceFirstChar { it.uppercase() }} user: ${user.fullName}", user.serverUserId)
                }
                ActionType.DEVICE_STATUS_CHANGED -> {
                    val device = devices.random()
                    Pair("Changed device status to ${device.deviceStatus.name}: ${device.name}", device.serverDeviceId)
                }
                ActionType.LOGIN -> Pair("Manager login: ${manager.fullName}", manager.serverManagerId)
                ActionType.REPORT_GENERATED -> Pair("Generated ${listOf("Monthly", "Weekly", "Daily").random()} report", null)
                ActionType.SETTINGS_UPDATED -> Pair("Updated system settings", null)
                else -> Pair("Performed system action", null)
            }

            auditLogs.add(
                AuditLogEntity(
                    serverAuditLogId = "audit-${UUID.randomUUID()}",
                    timestamp = timestamp,
                    actionType = actionType,
                    entityType = entityType,
                    entityServerId = entityId,
                    description = description,
                    ipAddress = "192.168.${Random.Default.nextInt(1, 255)}.${
                        Random.Default.nextInt(
                            1,
                            255
                        )
                    }",
                    organizationServerId = orgId
                )
            )
        }

        return auditLogs
    }

    // Add this function to your FakeDataRepository.kt

    /**
     * Simple function to create attendance records
     * Creates attendance for the last 30 days for all users
     */
    /**
     * Generate attendance records for the last 6 months (approx 180 days)
     * Includes realistic variations: lower attendance in December, higher in summer, etc.
     */
    private fun createAttendanceRecords(
        orgId: String,
        managers: List<ManagerEntity>,
        users: List<UserEntity>,
        devices: List<DeviceEntity>
    ): List<AttendanceRecordEntity> {
        val attendanceRecords = mutableListOf<AttendanceRecordEntity>()
        val now = System.currentTimeMillis()
        val sixMonthsInMillis = 180L * 24 * 60 * 60 * 1000 // Approx 180 days

        // Generate for the last 180 days
        for (dayOffset in 0 until 180) {
            val currentDate = getStartOfDay(now - (dayOffset * 24 * 60 * 60 * 1000L))
            val calendar = Calendar.getInstance().apply {
                timeInMillis = currentDate
            }
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val month = calendar.get(Calendar.MONTH) // 0 = January, 11 = December

            // Skip weekends (Saturday & Sunday)
            val isWeekend = dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY
            if (isWeekend) continue

            // Adjust attendance probability based on month
            val basePresentProb = when (month) {
                // December holidays -> lower attendance
                11 -> 0.75
                // July/August summer vacation -> slightly lower
                6, 7 -> 0.85
                // January (post-holidays) -> slightly lower
                0 -> 0.85
                else -> 0.92
            }

            users.forEach { user ->
                // Some users might have different patterns, but keep simple
                val isPresent = Random.Default.nextDouble() < basePresentProb

                if (isPresent) {
                    // Regular 9 AM to 5 PM work day with variations
                    val checkInTime = currentDate + (9 * 60 * 60 * 1000) +
                            Random.Default.nextInt(-20, 21) * 60 * 1000 // -20 to +20 minutes

                    val checkOutTime = currentDate + (17 * 60 * 60 * 1000) +
                            Random.Default.nextInt(-30, 91) * 60 * 1000 // -30 to +90 minutes

                    val workingMinutes = ((checkOutTime - checkInTime) / (1000 * 60)).toInt()
                    val breakMinutes = 60
                    val netWorkingMinutes = (workingMinutes - breakMinutes).coerceAtLeast(0)

                    val standardWorkMinutes = 480
                    val overtimeMinutes = (netWorkingMinutes - standardWorkMinutes).coerceAtLeast(0)

                    // Status: 85% present, 15% late (more realistic than 80/20)
                    val status = if (Random.Default.nextDouble() < 0.15) {
                        AttendanceStatus.LATE
                    } else {
                        AttendanceStatus.PRESENT
                    }

                    attendanceRecords.add(
                        AttendanceRecordEntity(
                            serverAttendanceId = "att-${UUID.randomUUID()}",
                            userServerId = user.serverUserId,
                            date = currentDate,
                            checkInTime = checkInTime,
                            checkOutTime = checkOutTime,
                            totalWorkingMinutes = netWorkingMinutes,
                            breakMinutes = breakMinutes,
                            overtimeMinutes = overtimeMinutes,
                            status = status,
                            attendanceType = AttendanceType.REGULAR,
                            remarks = if (status == AttendanceStatus.LATE) "Traffic delay" else null,
                            organizationServerId = orgId,
                            month = month,
                            deviceServerId = devices.random().serverDeviceId
                        )
                    )
                } else {
                    // Absent
                    attendanceRecords.add(
                        AttendanceRecordEntity(
                            serverAttendanceId = "att-${UUID.randomUUID()}",
                            userServerId = user.serverUserId,
                            date = currentDate,
                            status = AttendanceStatus.ABSENT,
                            remarks = when (month) {
                                11 -> "Holiday leave"
                                6,7 -> "Vacation"
                                else -> "Sick leave"
                            },
                            organizationServerId = orgId,
                            month = month,
                            deviceServerId = devices.random().serverDeviceId
                        )
                    )
                }
            }
        }

        println("✅ Generated ${attendanceRecords.size} attendance records for last 6 months")
        return attendanceRecords
    }

    // Helper function to get start of day
    private fun getStartOfDay(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    // Function to generate even larger dataset (500+ records)
    suspend fun seedMassiveData() = seedComprehensiveData(
        organizationName = "Enterprise Corporation Ltd.",
        managersCount = 12,
        devicesCount = 80,
        usersCount = 500,
        fingerprintsPerUser = 2,
        authLogsPerDay = 100,
        auditLogsCount = 500
    )

    // Function to generate specific test scenarios
    suspend fun seedTestScenario(scenario: String) = withContext(Dispatchers.IO) {
        when (scenario) {
            "HIGH_TRAFFIC" -> {
                println("Generating HIGH TRAFFIC scenario...")
                seedComprehensiveData(
                    organizationName = "High Traffic Facility",
                    managersCount = 5,
                    devicesCount = 10,
                    usersCount = 100,
                    fingerprintsPerUser = 1,
                    authLogsPerDay = 500,  // High auth volume
                    auditLogsCount = 50
                )
            }

            "MULTI_LOCATION" -> {
                println("Generating MULTI-LOCATION scenario...")
                seedComprehensiveData(
                    organizationName = "Multi-Location Corporation",
                    managersCount = 15,
                    devicesCount = 100,
                    usersCount = 1000,
                    fingerprintsPerUser = 2,
                    authLogsPerDay = 200,
                    auditLogsCount = 300
                )
            }

            "SECURITY_TEST" -> {
                println("Generating SECURITY TEST scenario...")
                // Create data with various security scenarios
                val orgId = "org-security-test"
                val org = OrganizationEntity(
                    serverOrganizationId = orgId,
                    name = "Security Test Org",
                    contactEmail = "security@test.com",
                    licenseKey = "SEC-TEST-123",
                    totalDevices = 20,
                    totalUsers = 50,
                    isActive = true,
                    createdAt = System.currentTimeMillis()
                )
                organizationDao.upsert(org)

                // Create suspicious patterns
                val suspiciousUser = UserEntity(
                    serverUserId = "usr-suspicious",
                    userCode = "EMP-SUS001",
                    fullName = "Test Suspicious User",
                    email = "suspicious@test.com",
                    phone = "+10000000000",
                    department = "Test",
                    notes = "SUSPICIOUS ACTIVITY - Multiple failed attempts",
                    isActive = false,
                    enrolledAt = System.currentTimeMillis(),
                    organizationServerId = orgId,
                    enrolledByServerManagerId = "mgr-test"
                )
                userDao.upsert(suspiciousUser)

                // Generate failed auth attempts
                repeat(20) {
                    authenticationLogDao.upsert(
                        AuthenticationLogEntity(
                            serverAuthenticationLogId = "auth-fail-$it",
                            timestamp = System.currentTimeMillis() - (it * 30 * 60 * 1000L), // Every 30 mins
                            result = AuthResult.FAILED,
                            confidenceScore = 15,
                            attemptDurationMs = 1500L,
                            deviceServerId = "dev-test",
                            userServerId = suspiciousUser.serverUserId,
                            matchedFingerprintServerId = null,
                            purpose = AuthenticationPurpose.VERIFICATION
                        )
                    )
                }
            }

            else -> seedComprehensiveData()
        }
    }

    // Utility function to clear all data
    suspend fun clearAllData() = withContext(Dispatchers.IO) {
        authenticationLogDao.deleteAll()
        fingerprintDao.deleteAll()
        userDao.deleteAll()
        deviceDao.deleteAll()
        managerDao.deleteAll()
        organizationDao.deleteAll()
        auditLogDao.deleteAll()
        println(" All data cleared")
    }

    // Utility function to get statistics
//    suspend fun getDatabaseStats(): Map<String, Int> = withContext(Dispatchers.IO) {
//        mapOf(
//            "Organizations" to organizationDao.countAll(),
//            "Managers" to managerDao.countAll(),
//            "Devices" to deviceDao.countAll(),
//            "Users" to userDao.countAll(),
//            "Fingerprints" to fingerprintDao.countAll(),
//            "Auth Logs" to authenticationLogDao.countAll(),
//            "Audit Logs" to auditLogDao.countAll()
//        )
//    }
}