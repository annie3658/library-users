package com.library.users.demo.dto;

import com.library.users.demo.enums.Rating;
import com.library.users.demo.enums.Status;
import lombok.Data;

@Data
public class UserStatsDTO {

    private String id;
    private String userId;
    private String bookId;
    private Status status;
    private Rating rating;

    public UserStatsDTO() {

    }

    public static class Builder {
        private String id;
        private String userId;
        private String bookId;
        private Status status;
        private Rating rating;

        public Builder(String id) {
           this.id = id;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withBookId(String bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withRating(Rating rating) {
            this.rating = rating;
            return this;
        }

        public UserStatsDTO build() {
            UserStatsDTO userStatsDTO = new UserStatsDTO();
            userStatsDTO.userId = this.userId;
            userStatsDTO.bookId = this.bookId;
            userStatsDTO.rating = this.rating;
            userStatsDTO.status = this.status;
            userStatsDTO.id = this.id;
            return  userStatsDTO;
        }
    }
}
