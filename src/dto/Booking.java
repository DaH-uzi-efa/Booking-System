package dto;

import java.time.LocalDateTime;

public record Booking(int id, String userId, String resourceId, LocalDateTime startTime, LocalDateTime endTime,
                      LocalDateTime createdAt) {

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", userId=" + userId +
                ", resourceId=" + resourceId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createdAt=" + createdAt +
                '}';
    }
}
