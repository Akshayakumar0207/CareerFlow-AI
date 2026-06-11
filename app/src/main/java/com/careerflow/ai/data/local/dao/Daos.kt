package com.careerflow.ai.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.careerflow.ai.data.local.entity.UserEntity
import com.careerflow.ai.data.local.entity.ApplicationEntity
import com.careerflow.ai.data.local.entity.InterviewEntity
import com.careerflow.ai.data.local.entity.InterviewFeedbackEntity
import com.careerflow.ai.data.local.entity.ResumeAnalysisEntity
import com.careerflow.ai.data.local.entity.NotificationEntity
import com.careerflow.ai.data.local.entity.JobAnalysisEntity
import com.careerflow.ai.data.local.entity.OfferEntity
import com.careerflow.ai.data.local.entity.RejectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserByIdFlow(userId: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Delete
    suspend fun deleteUser(user: UserEntity)
}

@Dao
interface ApplicationDao {
    @Insert
    suspend fun insertApplication(application: ApplicationEntity)

    @Update
    suspend fun updateApplication(application: ApplicationEntity)

    @Query("SELECT * FROM applications WHERE id = :applicationId")
    suspend fun getApplicationById(applicationId: String): ApplicationEntity?

    @Query("SELECT * FROM applications WHERE userId = :userId ORDER BY appliedDate DESC")
    fun getUserApplicationsFlow(userId: String): Flow<List<ApplicationEntity>>

    @Query("SELECT * FROM applications WHERE userId = :userId ORDER BY appliedDate DESC")
    suspend fun getUserApplications(userId: String): List<ApplicationEntity>

    @Query("SELECT * FROM applications WHERE userId = :userId AND companyName LIKE '%' || :query || '%'")
    fun searchApplications(userId: String, query: String): Flow<List<ApplicationEntity>>

    @Query("SELECT * FROM applications WHERE userId = :userId AND status = :status ORDER BY appliedDate DESC")
    fun getApplicationsByStatus(userId: String, status: String): Flow<List<ApplicationEntity>>

    @Query("SELECT COUNT(*) FROM applications WHERE userId = :userId")
    fun getApplicationCount(userId: String): Flow<Int>

    @Delete
    suspend fun deleteApplication(application: ApplicationEntity)
}

@Dao
interface InterviewDao {
    @Insert
    suspend fun insertInterview(interview: InterviewEntity)

    @Update
    suspend fun updateInterview(interview: InterviewEntity)

    @Query("SELECT * FROM interviews WHERE id = :interviewId")
    suspend fun getInterviewById(interviewId: String): InterviewEntity?

    @Query("SELECT * FROM interviews WHERE applicationId = :applicationId ORDER BY scheduledDate ASC")
    fun getApplicationInterviewsFlow(applicationId: String): Flow<List<InterviewEntity>>

    @Query("SELECT * FROM interviews WHERE userId = :userId ORDER BY scheduledDate ASC")
    fun getUserInterviewsFlow(userId: String): Flow<List<InterviewEntity>>

    @Query("SELECT * FROM interviews WHERE userId = :userId AND scheduledDate BETWEEN datetime('now') AND datetime('now', '+1 day')")
    fun getUpcomingInterviews(userId: String): Flow<List<InterviewEntity>>

    @Query("SELECT COUNT(*) FROM interviews WHERE userId = :userId")
    fun getInterviewCount(userId: String): Flow<Int>

    @Delete
    suspend fun deleteInterview(interview: InterviewEntity)
}

@Dao
interface InterviewFeedbackDao {
    @Insert
    suspend fun insertFeedback(feedback: InterviewFeedbackEntity)

    @Update
    suspend fun updateFeedback(feedback: InterviewFeedbackEntity)

    @Query("SELECT * FROM interview_feedback WHERE interviewId = :interviewId")
    suspend fun getFeedbackByInterviewId(interviewId: String): InterviewFeedbackEntity?

    @Query("SELECT * FROM interview_feedback WHERE userId = :userId ORDER BY createdAt DESC")
    fun getUserFeedbackFlow(userId: String): Flow<List<InterviewFeedbackEntity>>

    @Delete
    suspend fun deleteFeedback(feedback: InterviewFeedbackEntity)
}

@Dao
interface ResumeAnalysisDao {
    @Insert
    suspend fun insertAnalysis(analysis: ResumeAnalysisEntity)

    @Update
    suspend fun updateAnalysis(analysis: ResumeAnalysisEntity)

    @Query("SELECT * FROM resume_analysis WHERE userId = :userId ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLatestAnalysis(userId: String): ResumeAnalysisEntity?

    @Query("SELECT * FROM resume_analysis WHERE userId = :userId ORDER BY createdAt DESC")
    fun getUserAnalysisFlow(userId: String): Flow<List<ResumeAnalysisEntity>>

    @Delete
    suspend fun deleteAnalysis(analysis: ResumeAnalysisEntity)
}

@Dao
interface NotificationDao {
    @Insert
    suspend fun insertNotification(notification: NotificationEntity)

    @Update
    suspend fun updateNotification(notification: NotificationEntity)

    @Query("SELECT * FROM notifications WHERE userId = :userId ORDER BY createdAt DESC")
    fun getUserNotificationsFlow(userId: String): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM notifications WHERE userId = :userId AND isRead = 0 ORDER BY createdAt DESC")
    fun getUnreadNotificationsFlow(userId: String): Flow<List<NotificationEntity>>

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :notificationId")
    suspend fun markAsRead(notificationId: String)

    @Query("DELETE FROM notifications WHERE userId = :userId")
    suspend fun clearAllNotifications(userId: String)

    @Delete
    suspend fun deleteNotification(notification: NotificationEntity)
}

@Dao
interface JobAnalysisDao {
    @Insert
    suspend fun insertAnalysis(analysis: JobAnalysisEntity)

    @Update
    suspend fun updateAnalysis(analysis: JobAnalysisEntity)

    @Query("SELECT * FROM job_analysis WHERE applicationId = :applicationId")
    suspend fun getAnalysisByApplicationId(applicationId: String): JobAnalysisEntity?

    @Query("SELECT * FROM job_analysis WHERE applicationId = :applicationId")
    fun getAnalysisByApplicationIdFlow(applicationId: String): Flow<JobAnalysisEntity?>

    @Delete
    suspend fun deleteAnalysis(analysis: JobAnalysisEntity)
}

@Dao
interface OfferDao {
    @Insert
    suspend fun insertOffer(offer: OfferEntity)

    @Update
    suspend fun updateOffer(offer: OfferEntity)

    @Query("SELECT * FROM offer WHERE userId = :userId ORDER BY offerReceiveDate DESC")
    fun getUserOffersFlow(userId: String): Flow<List<OfferEntity>>

    @Query("SELECT COUNT(*) FROM offer WHERE userId = :userId AND status = 'ACCEPTED'")
    fun getAcceptedOfferCount(userId: String): Flow<Int>

    @Delete
    suspend fun deleteOffer(offer: OfferEntity)
}

@Dao
interface RejectionDao {
    @Insert
    suspend fun insertRejection(rejection: RejectionEntity)

    @Update
    suspend fun updateRejection(rejection: RejectionEntity)

    @Query("SELECT * FROM rejection WHERE userId = :userId ORDER BY rejectionDate DESC")
    fun getUserRejectionsFlow(userId: String): Flow<List<RejectionEntity>>

    @Query("SELECT COUNT(*) FROM rejection WHERE userId = :userId")
    fun getRejectionCount(userId: String): Flow<Int>

    @Delete
    suspend fun deleteRejection(rejection: RejectionEntity)
}
