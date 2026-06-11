package com.careerflow.ai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.careerflow.ai.data.local.converters.Converters
import com.careerflow.ai.data.local.dao.*
import com.careerflow.ai.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        ApplicationEntity::class,
        InterviewEntity::class,
        InterviewFeedbackEntity::class,
        ResumeAnalysisEntity::class,
        NotificationEntity::class,
        JobAnalysisEntity::class,
        OfferEntity::class,
        RejectionEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class CareerFlowDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun applicationDao(): ApplicationDao
    abstract fun interviewDao(): InterviewDao
    abstract fun interviewFeedbackDao(): InterviewFeedbackDao
    abstract fun resumeAnalysisDao(): ResumeAnalysisDao
    abstract fun notificationDao(): NotificationDao
    abstract fun jobAnalysisDao(): JobAnalysisDao
    abstract fun offerDao(): OfferDao
    abstract fun rejectionDao(): RejectionDao
}
