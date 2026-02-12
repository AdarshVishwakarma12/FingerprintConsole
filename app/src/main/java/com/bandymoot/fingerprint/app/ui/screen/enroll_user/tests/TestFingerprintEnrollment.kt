package com.bandymoot.fingerprint.app.ui.screen.enroll_user.tests

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun TestFingerprintEnrollmentScreen() {
//    MaterialTheme {
//        var state by remember { mutableStateOf(EnrollmentState()) }
//
//        LaunchedEffect(key1 = state.isEnrolling) {
//            if (state.isEnrolling && state.enrollmentProgress < 1f) {
//                repeat(4) { step ->
//                    state = state.copy(
//                        enrollmentMessage = "Scanning... ${(step + 1) * 25}%",
//                        currentStep = step + 1,
//                        enrollmentProgress = (step + 1) * 0.25f
//                    )
//                    delay(4000)
//                }
//                state = state.copy(
//                    isCompleted = true,
//                    enrollmentProgress = 1f,
//                    enrollmentMessage = "Enrollment successful!"
//                )
//            }
//        }
//
//        FingerprintEnrollmentScreen(
//            state = state,
//            onStartEnrollment = {
//                state = state.copy(isEnrolling = true)
//            },
//            onRetry = {
//                state = EnrollmentState()
//            },
//            onComplete = {
//                // Handle completion
//            }
//        )
//    }
//}