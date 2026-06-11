package com.careerflow.ai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val profileImageUrl: String? = null,
    val graduationYear: Int? = null,
    val skills: List<String>? = null,
    val preferredRoles: List<String>? = null,
    val bio: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val isVerified: Boolean = false
)

@Entity(tableName = "applications")
data class ApplicationEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val companyName: String,
    val jobRole: String,
    val jobDescription: String,
    val applicationUrl: String,
    val appliedDate: LocalDateTime,
    val salaryMin: Int? = null,
    val salaryMax: Int? = null,
    val currency: String = "USD",
    val location: String,
    val notes: String? = null,
    val status: ApplicationStatus = ApplicationStatus.APPLIED,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

@Entity(tableName = "interviews")
data class InterviewEntity(
    @PrimaryKey
    val id: String,
    val applicationId: String,
    val userId: String,
    val roundNumber: Int,
    val roundType: String, // Technical, HR, Manager, etc.
    val scheduledDate: LocalDateTime? = null,
    val completedDate: LocalDateTime? = null,
    val duration: Int? = null, // in minutes
    val interviewer: String? = null,
    val notes: String? = null,
    val result: InterviewResult? = null,
    val status: InterviewStatus = InterviewStatus.SCHEDULED,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

@Entity(tableName = "interview_feedback")
data class InterviewFeedbackEntity(
    @PrimaryKey
    val id: String,
    val interviewId: String,
    val applicationId: String,
    val userId: String,
    val questionsAsked: String,
    val topicsCovered: List<String>?,
    val difficultyLevel: DifficultyLevel,
    val confidenceLevel: Int, // 1-10
    val performanceNotes: String?,
    val strengths: String?,
    val weaknesses: String?,
    val suggestedImprovements: String?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

@Entity(tableName = "resume_analysis")
data class ResumeAnalysisEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val resumeUrl: String,
    val fileName: String,
    val atsScore: Double,
    val keywords: List<String>?,
    val skills: List<String>?,
    val projects: List<String>?,
    val suggestions: String?,
    val formattingIssues: List<String>?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val type: NotificationType,
    val title: String,
    val message: String,
    val relatedApplicationId: String? = null,
    val relatedInterviewId: String? = null,
    val isRead: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

@Entity(tableName = "job_analysis")
data class JobAnalysisEntity(
    @PrimaryKey
    val id: String,
    val applicationId: String,
    val userId: String,
    val requiredSkills: List<String>?,
    val preferredSkills: List<String>?,
    val technicalTopics: List<String>?,
    val softSkills: List<String>?,
    val importantKeywords: List<String>?,
    val atsKeywords: List<String>?,
    val missingSkills: List<String>?,
    val preparationPlan: String?,
    val learningResources: List<String>?,
    val interviewFocusAreas: List<String>?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

@Entity(tableName = "offer")
data class OfferEntity(
    @PrimaryKey
    val id: String,
    val applicationId: String,
    val userId: String,
    val companyName: String,
    val jobRole: String,
    val baseSalary: Int,
    val currency: String = "USD",
    val benefits: String?,
    val startDate: LocalDateTime?,
    val offerReceiveDate: LocalDateTime,
    val offerExpiryDate: LocalDateTime?,
    val status: OfferStatus = OfferStatus.PENDING,
    val notes: String?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

@Entity(tableName = "rejection")
data class RejectionEntity(
    @PrimaryKey
    val id: String,
    val applicationId: String,
    val userId: String,
    val companyName: String,
    val jobRole: String,
    val roundReached: String,
    val questionsAsked: String?,
    val struggledTopics: List<String>?,
    val candidateConfidence: Int?, // 1-10
    val likelyReasons: String?,
    val missingSkills: List<String>?,
    val weakAreas: List<String>?,
    val improvementRoadmap: String?,
    val rejectionDate: LocalDateTime,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

// Enums
enum class ApplicationStatus {
    APPLIED,
    ASSESSMENT,
    TECHNICAL_ROUND_1,
    TECHNICAL_ROUND_2,
    MANAGER_ROUND,
    HR_ROUND,
    OFFER,
    REJECTED,
    WITHDRAWN
}

enum class InterviewStatus {
    SCHEDULED,
    COMPLETED,
    CANCELLED,
    RESCHEDULED
}

enum class InterviewResult {
    PASS,
    FAIL,
    ON_HOLD,
    PENDING
}

enum class DifficultyLevel {
    EASY,
    MEDIUM,
    HARD,
    VERY_HARD
}

enum class NotificationType {
    INTERVIEW_TOMORROW,
    INTERVIEW_TODAY,
    ASSESSMENT_DUE,
    FEEDBACK_REMINDER,
    PREPARATION_REMINDER,
    OFFER_RECEIVED,
    REJECTION_RECEIVED,
    EMAIL_RECEIVED,
    CUSTOM
}

enum class OfferStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    NEGOTIATING
}
