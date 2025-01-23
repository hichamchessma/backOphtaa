package com.ophta.entity;

public class ResetPasswordRequest {


        private String token;
        private String newPassword;

        // Default constructor
        public ResetPasswordRequest() {
        }

        // Constructor with parameters
        public ResetPasswordRequest(String token, String newPassword) {
            this.token = token;
            this.newPassword = newPassword;
        }

        // Getter for token
        public String getToken() {
            return token;
        }

        // Setter for token
        public void setToken(String token) {
            this.token = token;
        }

        // Getter for newPassword
        public String getNewPassword() {
            return newPassword;
        }

        // Setter for newPassword
        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
}
