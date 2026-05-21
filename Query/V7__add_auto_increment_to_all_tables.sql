use vsusenglish;

SET FOREIGN_KEY_CHECKS = 0;

-- =========================================================================
-- MODULE USER & AUTH
-- =========================================================================
ALTER TABLE account MODIFY account_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE user_profile MODIFY user_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE role MODIFY role_id INT UNSIGNED NOT NULL AUTO_INCREMENT;

-- =========================================================================
-- MODULE LEARNING CONTENT (BÀI HỌC, TỪ VỰNG, ĐỀ THI)
-- =========================================================================
ALTER TABLE category MODIFY category_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE lesson MODIFY lesson_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE vocab MODIFY vocab_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE test MODIFY test_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE question MODIFY question_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE question_option MODIFY option_id INT UNSIGNED NOT NULL AUTO_INCREMENT;

-- PROGRESS & ATTEMPTS (TIẾN TRÌNH HỌC)
ALTER TABLE user_lesson_question_attempt MODIFY attempt_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE user_test_question_attempt MODIFY attempt_id INT UNSIGNED NOT NULL AUTO_INCREMENT;

-- =========================================================================
-- MODULE FORUM (MẠNG XÃ HỘI, BÀI ĐĂNG, BÌNH LUẬN)
-- =========================================================================
ALTER TABLE post MODIFY post_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE user_comment MODIFY comment_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE reaction_type MODIFY reaction_type_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE comment_report MODIFY cmt_report_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE post_report MODIFY post_report_id INT UNSIGNED NOT NULL AUTO_INCREMENT;

-- =========================================================================
-- MODULE CONVERSATION (CHAT, TIN NHẮN)
-- =========================================================================
ALTER TABLE conversation MODIFY conversation_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE message MODIFY message_id INT UNSIGNED NOT NULL AUTO_INCREMENT;

-- =========================================================================
-- MODULE COMPETITION (THI ĐẤU 1VS1, MATCHMAKING)
-- =========================================================================
ALTER TABLE matchmaking_queue MODIFY queue_id INT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE matching MODIFY match_id INT UNSIGNED NOT NULL AUTO_INCREMENT;

-- 2. Bật lại kiểm tra ràng buộc khóa ngoại để bảo toàn tính toàn vẹn dữ liệu
SET FOREIGN_KEY_CHECKS = 1;
