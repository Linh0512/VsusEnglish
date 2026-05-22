package org.Linh0512.module.user.infrastructure.mapper;

import org.Linh0512.module.user.domain.entity.UserProfile;
import org.Linh0512.module.user.presentation.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Map từ UserProfile Entity sang UserResponse DTO
     * 
     * @param userProfile Entity từ database
     * @return UserResponse DTO để trả về Frontend
     */
    @Mapping(source = "username", target = "username")
    @Mapping(source = "fullname", target = "fullname")
    @Mapping(source = "gender", target = "gender", qualifiedByName = "mapGender")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "yob", target = "yob")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "avatarUrl", target = "avatarUrl")
    @Mapping(source = "bio", target = "bio")
    @Mapping(source = "joinDate", target = "joinDate", qualifiedByName = "formatDateTime")
    @Mapping(source = "account.lastLogin", target = "lastLogin", qualifiedByName = "formatDateTime")
    @Mapping(source = "elo", target = "ELO")
    @Mapping(source = "contributionPoint", target = "contributionPoint")
    @Mapping(source = "account.status", target = "status", qualifiedByName = "mapStatus")
    UserResponse toUserResponse(UserProfile userProfile);

    /**
     * Convert Gender Enum to String
     * Gender.Male → "Male"
     */
    @Named("mapGender")
    default String mapGender(UserProfile.Gender gender) {
        return gender != null ? gender.name() : null;
    }
    
    /**
     * Convert Status Enum to String
     * Status.ACTIVE → "ACTIVE"
     */
    @Named("mapStatus")
    default String mapStatus(Object status) {
        return status != null ? status.toString() : null;
    }
    
    /**
     * Format LocalDateTime to String
     * LocalDateTime(2024-12-20 15:45:30) → "2024-12-20 15:45:30"
     */
    @Named("formatDateTime")
    default String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DATE_TIME_FORMATTER);
    }
}